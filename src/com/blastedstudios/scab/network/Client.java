package com.blastedstudios.scab.network;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.Properties;
import com.google.protobuf.Message;

public class Client {
	private final LinkedList<MessageStruct> sendQueue = new LinkedList<>();
	private final LinkedList<INetworkListener> listeners = new LinkedList<>();
	private HostStruct hostStruct;
	
	public void connect(String host){
		int port = Properties.getInt("network.port");
		// if host is something like 1.2.3.4:8888 try to split up host,port to component parts and connect that way
		if(host.contains(":")){
			host = host.split(":")[0];
			port = Integer.parseInt(host.split(":")[1]);
		}
		Socket socket = Gdx.net.newClientSocket(Protocol.TCP, host, port, null);
		hostStruct = new HostStruct(socket);
		Log.debug("Client.<init>", "Connected to server: " + socket.getRemoteAddress());
		for(INetworkListener listener : listeners)
			listener.connected(hostStruct);
	}
	
	public void render(){
		if(!hostStruct.socket.isConnected()){
			for(INetworkListener listener : listeners)
				listener.disconnected(hostStruct);
			String target = hostStruct == null || hostStruct.socket == null ? "null" : hostStruct.socket.getRemoteAddress();
			Log.debug("Client.render", "Disconnected from server: " + target);
			return;
		}
		List<MessageStruct> messages = Shared.receiveMessages(hostStruct.inStream, hostStruct.socket);
		for(MessageStruct message : messages){
			switch(message.messageType){
			default:
				Log.log("Client.render", "Unknown message received: " + message.messageType + " contents: " + message.message);
				break;
			}
		}
		Shared.sendMessages(sendQueue, hostStruct.outStream);
	}
	
	public void dispose(){
		hostStruct.socket.dispose();
	}

	public void send(MessageType messageType, Message message) {
		sendQueue.add(new MessageStruct(messageType, message));
	}
	
	public void addListener(INetworkListener listener){
		listeners.add(listener);
	}
	
	public void removeListener(INetworkListener listener){
		listeners.remove(listener);
	}
}
