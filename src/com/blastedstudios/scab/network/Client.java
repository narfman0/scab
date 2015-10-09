package com.blastedstudios.scab.network;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.Properties;

public class Client extends BaseNetwork {
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
		receiveMessage(MessageType.CONNECTED, hostStruct);
	}
	
	public void render(){
		if(!hostStruct.socket.isConnected()){
			receiveMessage(MessageType.DISCONNECTED, hostStruct);
			String target = hostStruct == null || hostStruct.socket == null ? "null" : hostStruct.socket.getRemoteAddress();
			Log.debug("Client.render", "Disconnected from server: " + target);
			return;
		}
		List<MessageStruct> messages = receiveMessages(hostStruct.inStream, hostStruct.socket);
		for(MessageStruct message : messages){
			switch(message.messageType){
			default:
				Log.log("Client.render", "Unknown message received: " + message.messageType + " contents: " + message.message);
				break;
			}
		}
		sendMessages(sendQueue, hostStruct.outStream);
	}
	
	@Override public void dispose(){
		hostStruct.socket.dispose();
	}
}
