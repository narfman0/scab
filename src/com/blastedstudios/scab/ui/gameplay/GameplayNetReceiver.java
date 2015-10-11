package com.blastedstudios.scab.ui.gameplay;

import java.util.UUID;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.scab.network.BaseNetwork;
import com.blastedstudios.scab.network.IMessageListener;
import com.blastedstudios.scab.network.MessageType;
import com.blastedstudios.scab.network.Messages.BeingRespawn;
import com.blastedstudios.scab.network.Messages.NetBeing;
import com.blastedstudios.scab.ui.levelselect.network.NetworkWindow.MultiplayerType;
import com.blastedstudios.scab.world.WorldManager;
import com.blastedstudios.scab.world.being.Being;
import com.blastedstudios.scab.world.being.Being.BodyPart;

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
			network.addListener(MessageType.BEING_RESPAWN, this);
			network.addListener(MessageType.PLAYER_UPDATE, this);
		}
	}

	@Override public void receive(MessageType messageType, Object object) {
		switch(messageType){
		case BEING_RESPAWN:{
			// nothing is triggering this yet
			BeingRespawn message = (BeingRespawn) object;
			UUID uuid = UUID.fromString(message.getUuid());
			Being existing = worldManager.getRemotePlayer(uuid);
			if(existing != null)
				existing.respawn(worldManager.getWorld(), message.getPosX(), message.getPosY());
			break;
		}
		case PLAYER_UPDATE:{
			NetBeing message = (NetBeing) object;
			if(message.getName().equals(worldManager.getPlayer().getName()))
				// don't want to make a new player with my name! should refactor to use ids somehow in future
				break;
			UUID uuid = UUID.fromString(message.getUuid());
			Being existing = worldManager.getRemotePlayer(uuid);
			if(existing == null){
				Being being = Being.fromMessage(message);
				worldManager.getRemotePlayers().add(being);
				being.respawn(worldManager.getWorld(), message.getPosX(), message.getPosY());
				Log.log("GameplayScreen.receive", "Received first player update: " + message.getName());
			}else if(existing.getPosition() != null && message.hasPosX()){
				existing.getRagdoll().getBodyPart(BodyPart.torso).setTransform(message.getPosX(), message.getPosY(), 0);
				existing.setVelocity(new Vector2(message.getVelX(), message.getVelY()));
				existing.aim(message.getAim());
			}
			if(type == MultiplayerType.Host)
				network.send(messageType, message);
			break;
		}default:
			break;
		}
	}
	
	public void render(float dt){
		if(type != MultiplayerType.Local){
			network.render();
			if(!worldManager.getPlayer().isDead())
				network.send(MessageType.PLAYER_UPDATE, worldManager.getPlayer().buildMessage(true));
		}
	}
	
	public void dispose(){
		if(type != MultiplayerType.Local)
			network.removeListener(this);
	}
}
