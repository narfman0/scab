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
	private final IClientListener listener;
	private HostStruct hostStruct;
	
	public Client(IClientListener listener){
		this.listener = listener;
	}
	
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
		listener.connected(socket);
	}
	
	public void render(){
		if(!hostStruct.socket.isConnected()){
			listener.disconnected(hostStruct.socket);
			Log.debug("Client.render", "Disconnected from server: " + (hostStruct.socket == null ? "null" : hostStruct.socket.getRemoteAddress()));
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
	
	public interface IClientListener{
		void connected(Socket socket);
		void disconnected(Socket socket);
	}

	public void send(MessageType messageType, Message message) {
		sendQueue.add(new MessageStruct(messageType, message));
	}
}
