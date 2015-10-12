package com.blastedstudios.scab.ui.gameplay;

import java.util.UUID;

import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.scab.network.BaseNetwork;
import com.blastedstudios.scab.network.IMessageListener;
import com.blastedstudios.scab.network.Messages.BeingDead;
import com.blastedstudios.scab.network.Messages.BeingRespawn;
import com.blastedstudios.scab.network.Messages.MessageType;
import com.blastedstudios.scab.network.Messages.NPCState;
import com.blastedstudios.scab.network.Messages.NetBeing;
import com.blastedstudios.scab.network.Messages.PlayerState;
import com.blastedstudios.scab.ui.levelselect.network.NetworkWindow.MultiplayerType;
import com.blastedstudios.scab.util.UUIDConvert;
import com.blastedstudios.scab.world.WorldManager;
import com.blastedstudios.scab.world.being.Being;
import com.blastedstudios.scab.world.being.NPC;

public class GameplayNetReceiver implements IMessageListener{
	private final WorldManager worldManager;
	public final MultiplayerType type;
	public final BaseNetwork network;
	
	public GameplayNetReceiver(WorldManager worldManager, MultiplayerType type, BaseNetwork network){
		this.worldManager = worldManager;
		this.type = type;
		this.network = network;
		if(type != MultiplayerType.Local){
			worldManager.getPlayer().setUuid(network.getUUID());
			network.addListener(MessageType.BEING_DEAD, this);
			network.addListener(MessageType.BEING_RESPAWN, this);
			network.addListener(MessageType.PLAYER_STATE, this);
			network.addListener(MessageType.NPC_STATE, this);
		}
		worldManager.setSimulate(type != MultiplayerType.Client);
	}

	@Override public void receive(MessageType messageType, Object object) {
		switch(messageType){
		case BEING_DEAD:{
			// nothing is triggering this yet
			BeingDead message = (BeingDead) object;
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
		}case BEING_RESPAWN:{
			// nothing is triggering this yet
			BeingRespawn message = (BeingRespawn) object;
			UUID uuid = UUID.fromString(message.getUuid());
			Being existing = worldManager.getRemotePlayer(uuid);
			if(existing != null)
				existing.respawn(worldManager.getWorld(), message.getPosX(), message.getPosY());
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

	public void send(MessageType messageType, BeingDead message) {
		if(network != null)
			network.send(messageType, message);
	}
}
