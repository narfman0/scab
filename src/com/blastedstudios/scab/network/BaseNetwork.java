package com.blastedstudios.scab.network;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import com.google.protobuf.Message;

public abstract class BaseNetwork {
	protected final LinkedList<MessageStruct> sendQueue = new LinkedList<>();
	private final HashMap<MessageType, HashSet<IMessageListener>> listeners = new HashMap<>();
	
	public BaseNetwork(){
		for(MessageType messageType : MessageType.values())
			listeners.put(messageType, new HashSet<>());
	}
	
	/**
	 * Distribute message to all listeners
	 * a.k.a. receive, heed, execute, send
	 */
	public void receiveMessage(MessageType messageType, Object message){
		for(IMessageListener listener : listeners.get(messageType))
			listener.receive(message);
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
		listeners.remove(listener);
	}
	
	public void removeListener(IMessageListener listener){
		for(HashSet<IMessageListener> messageListeners : listeners.values())
			messageListeners.remove(listener);
	}

	public abstract void dispose();
}
