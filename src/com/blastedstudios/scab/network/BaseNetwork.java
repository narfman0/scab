package com.blastedstudios.scab.network;

import gatech.mmpm.util.Pair;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.net.Socket;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.scab.network.Messages.MessageType;
import com.blastedstudios.scab.network.Messages.NetBeing;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Message;

public abstract class BaseNetwork {
	private static final HashMap<MessageType, Pair<Class<?>, Method>> DESERIALIZERS = new HashMap<>();
	protected final LinkedList<MessageStruct> sendQueue = new LinkedList<>();
	private final HashMap<MessageType, HashSet<IMessageListener>> listeners = new HashMap<>();
	private final UUID uuid;
	
	static{
		// cache all deserializers once in beginning of program
		for(Class<?> clazz : Messages.class.getClasses())
			for(MessageType messageType : MessageType.values()){
				if(clazz.getSimpleName().toUpperCase().equals(messageType.name().replace("_", ""))){
					try {
						Method parseMethod = clazz.getMethod("parseFrom", byte[].class);
						DESERIALIZERS.put(messageType, new Pair<Class<?>, Method>(clazz, parseMethod));
					} catch (Exception e) {
						Log.error("BaseNetwork.<init>", "Exception caching parse method for: " + messageType);
					}
				}
			}
	}
	
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
				Log.debug("BaseNetwork.render", "Sent message successfully: " + sendStruct.messageType.name());
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
				case PLAYER_UPDATE:
					messages.add(new MessageStruct(messageType, NetBeing.parseFrom(buffer)));
					break;
				case CONNECTED:
				case DISCONNECTED:
					//Do nothing intentionally!
					break;
				default:
					Pair<Class<?>, Method> pair = DESERIALIZERS.get(messageType);
					Message message = (Message)pair.getSecond().invoke(pair.getFirst(), buffer);
					messages.add(new MessageStruct(messageType, message));
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
