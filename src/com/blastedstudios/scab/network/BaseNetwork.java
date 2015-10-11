package com.blastedstudios.scab.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.net.Socket;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.scab.network.Messages.BeingRespawn;
import com.blastedstudios.scab.network.Messages.LevelLoad;
import com.blastedstudios.scab.network.Messages.NetBeing;
import com.blastedstudios.scab.network.Messages.Text;
import com.blastedstudios.scab.network.Messages.TextRequest;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Message;

public abstract class BaseNetwork {
	protected final LinkedList<MessageStruct> sendQueue = new LinkedList<>();
	private final HashMap<MessageType, HashSet<IMessageListener>> listeners = new HashMap<>();
	private final UUID uuid;
	
	public BaseNetwork(){
		this.uuid = UUID.randomUUID();
		for(MessageType messageType : MessageType.values())
			listeners.put(messageType, new HashSet<>());
	}
	
	/**
	 * Distribute message to all listeners
	 * a.k.a. receive, heed, execute, send
	 */
	public void receiveMessage(MessageType messageType, Object message){
		for(IMessageListener listener : new ArrayList<>(listeners.get(messageType)))
			listener.receive(messageType, message);
	}

	/**
	 * Send a network message of the given type to connected host(s)
	 */
	public void send(MessageType messageType, Message message) {
		sendQueue.add(new MessageStruct(messageType, message));
	}
	
	public void addListener(MessageType messageType, IMessageListener listener){
		HashSet<IMessageListener> messageListeners = listeners.get(messageType);
		messageListeners.add(listener);
	}
	
	public void removeListener(MessageType messageType, IMessageListener listener){
		listeners.get(messageType).remove(listener);
	}
	
	public void removeListener(IMessageListener listener){
		for(HashSet<IMessageListener> messageListeners : listeners.values())
			messageListeners.remove(listener);
	}
	
	public void clearListeners(){
		for(HashSet<IMessageListener> messageListeners : listeners.values())
			messageListeners.clear();
	}

	public abstract void dispose();
	public abstract boolean isConnected();
	public abstract void render();
	
	public UUID getUUID(){
		return uuid;
	}

	protected static void sendMessages(List<MessageStruct> messages, CodedOutputStream stream){
		for(MessageStruct sendStruct : messages){
			try {
				stream.writeSInt32NoTag(sendStruct.messageType.ordinal());
				stream.writeSInt32NoTag(sendStruct.message.getSerializedSize());
				sendStruct.message.writeTo(stream);
				Log.debug("Client.render", "Sent message successfully: " + sendStruct.messageType.name());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			stream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected static List<MessageStruct> receiveMessages(CodedInputStream stream, Socket socket){
		List<MessageStruct> messages = new LinkedList<>();
		try {
			while(socket.getInputStream().available() > 0 && socket.isConnected()){
				MessageType messageType = MessageType.values()[stream.readSInt32()];
				byte[] buffer =  stream.readRawBytes(stream.readSInt32());
				switch(messageType){
				case NAME_UPDATE:
					messages.add(new MessageStruct(messageType, NetBeing.parseFrom(buffer)));
					break;
				case TEXT:
					messages.add(new MessageStruct(messageType, Text.parseFrom(buffer)));
					break;
				case TEXT_REQUEST:
					messages.add(new MessageStruct(messageType, TextRequest.parseFrom(buffer)));
					break;
				case LEVEL_LOAD:
					messages.add(new MessageStruct(messageType, LevelLoad.parseFrom(buffer)));
					break;
				case PLAYER_UPDATE:
					messages.add(new MessageStruct(messageType, NetBeing.parseFrom(buffer)));
					break;
				case BEING_RESPAWN:
					messages.add(new MessageStruct(messageType, BeingRespawn.parseFrom(buffer)));
					break;
				case CONNECTED:
				case DISCONNECTED:
					//Do nothing intentionally!
					break;
				}
				Log.debug("Host.render", "Received " + messageType.name() + " from " + socket.getRemoteAddress());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messages;
	}
}
