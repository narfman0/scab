package com.blastedstudios.scab.ui.gameplay;

import java.util.UUID;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.scab.network.BaseNetwork;
import com.blastedstudios.scab.network.IMessageListener;
import com.blastedstudios.scab.network.Messages.Attack;
import com.blastedstudios.scab.network.Messages.Pause;
import com.blastedstudios.scab.network.Messages.Reload;
import com.blastedstudios.scab.network.Messages.Respawn;
import com.blastedstudios.scab.network.Messages.Dead;
import com.blastedstudios.scab.network.Messages.MessageType;
import com.blastedstudios.scab.network.Messages.NPCState;
import com.blastedstudios.scab.network.Messages.NetBeing;
import com.blastedstudios.scab.network.Messages.PlayerState;
import com.blastedstudios.scab.network.Messages.Text;
import com.blastedstudios.scab.ui.gameplay.console.History;
import com.blastedstudios.scab.ui.levelselect.network.NetworkWindow.MultiplayerType;
import com.blastedstudios.scab.util.UUIDConvert;
import com.blastedstudios.scab.world.WorldManager;
import com.blastedstudios.scab.world.being.Being;
import com.blastedstudios.scab.world.being.NPC;
import com.blastedstudios.scab.world.being.Player;
import com.google.protobuf.Message;

public class GameplayNetReceiver implements IMessageListener{
	private final WorldManager worldManager;
	public final MultiplayerType type;
	public final BaseNetwork network;
	private final GameplayScreen screen;
	
	public GameplayNetReceiver(GameplayScreen screen, WorldManager worldManager, MultiplayerType type, BaseNetwork network){
		this.screen = screen;
		this.worldManager = worldManager;
		this.type = type;
		this.network = network;
		if(type != MultiplayerType.Local){
			worldManager.getPlayer().setUuid(network.getUUID());
			network.addListener(MessageType.ATTACK, this);
			network.addListener(MessageType.DEAD, this);
			network.addListener(MessageType.NPC_STATE, this);
			network.addListener(MessageType.PAUSE, this);
			network.addListener(MessageType.PLAYER_STATE, this);
			network.addListener(MessageType.RELOAD, this);
			network.addListener(MessageType.RESPAWN, this);
			network.addListener(MessageType.TEXT, this);
		}
		worldManager.setSimulate(type != MultiplayerType.Client);
	}

	@Override public void receive(MessageType messageType, Object object) {
		switch(messageType){
		case ATTACK:{
			Attack message = (Attack) object;
			Being existing = null;
			if(message.hasUuid())
				existing = worldManager.getRemotePlayer(UUIDConvert.convert(message.getUuid()));
			else
				for(Being being : worldManager.getAllBeings())
					if(being.getName().equals(message.getName()))
						existing = being;
			if(existing != null)
				existing.attack(new Vector2(message.getPosX(), message.getPosY()), worldManager);
			break;
		}case DEAD:{
			Dead message = (Dead) object;
			Being existing = null;
			if(message.hasUuid())
				existing = worldManager.getRemotePlayer(UUIDConvert.convert(message.getUuid()));
			else
				for(Being being : worldManager.getAllBeings())
					if(being.getName().equals(message.getName()))
						existing = being;
			if(existing != null)
				existing.death(worldManager);
			break;
		}case PAUSE:{
			Pause message = (Pause) object;
			screen.handlePause(message.getPause());
			break;
		}case RELOAD:{
			Reload message = (Reload) object;
			UUID uuid = UUIDConvert.convert(message.getUuid());
			Being existing = worldManager.getRemotePlayer(uuid);
			if(existing != null)
				existing.reload();
			break;
		}case RESPAWN:{
			Respawn message = (Respawn) object;
			UUID uuid = UUIDConvert.convert(message.getUuid());
			Being existing = worldManager.getRemotePlayer(uuid);
			if(existing != null)
				existing.respawn(worldManager.getWorld(), message.getPosX(), message.getPosY());
			else if(UUIDConvert.convert(message.getUuid()).equals(network.getUUID())){
				Player self = worldManager.getPlayer();
				self.respawn(worldManager.getWorld(), self.getPosition().x, self.getPosition().y);
			}
			break;
		}case PLAYER_STATE:{
			PlayerState message = (PlayerState)object;
			for(NetBeing netBeing : message.getPlayersList()){
				if(netBeing.getName().equals(worldManager.getPlayer().getName()))
					// don't want to make a new player with my name! should refactor to use ids somehow in future
					break;
				UUID uuid = UUIDConvert.convert(netBeing.getUuid());
				Being remotePlayer = worldManager.getRemotePlayer(uuid);
				if(remotePlayer == null){
					remotePlayer = Being.fromMessage(netBeing);
					worldManager.getRemotePlayers().add(remotePlayer);
					remotePlayer.respawn(worldManager.getWorld(), netBeing.getPosX(), netBeing.getPosY());
					Log.log("GameplayScreen.receive", "Received first player update: " + netBeing.getName());
				}else if(remotePlayer.getPosition() != null && netBeing.hasPosX())
					remotePlayer.updateFromMessage(netBeing);
			}
			if(type == MultiplayerType.Host)
				network.send(messageType, message);
			break;
		}case NPC_STATE:{
			NPCState message = (NPCState) object;
			for(NetBeing updateNPC : message.getNpcsList())
				for(NPC npc : worldManager.getNpcs())
					if(!npc.isDead() && npc.getName().equals(updateNPC.getName()))
						npc.updateFromMessage(updateNPC);
			break;
		}case TEXT:{
			Text message = (Text) object;
			screen.handlePause(true);
			if(!message.getOrigin().equals(worldManager.getPlayer().getName()))
				History.add(message.getContent(), Color.BLACK);
			break;
		}default:
			break;
		}
	}
	
	public void render(float dt){
		if(type != MultiplayerType.Local){
			network.render();
			if(!worldManager.getPlayer().isDead()){
				PlayerState.Builder builder = PlayerState.newBuilder();
				builder.addPlayers(worldManager.getPlayer().buildMessage(true));
				network.send(MessageType.PLAYER_STATE, builder.build());
			}
		}
		if(type == MultiplayerType.Host){
			NPCState.Builder builder = NPCState.newBuilder(); 
			for(NPC npc : worldManager.getNpcs())
				builder.addNpcs(npc.buildMessage(false));
			network.send(MessageType.NPC_STATE, builder.build());
		}
	}
	
	public void dispose(){
		if(type != MultiplayerType.Local)
			network.removeListener(this);
	}

	public void send(MessageType messageType, Message message) {
		if(network != null)
			network.send(messageType, message);
	}
}
