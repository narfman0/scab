package com.blastedstudios.scab.network;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.Properties;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Message;

public class Client {
	private final IClientListener listener;
	private final LinkedList<SendStruct> sendQueue = new LinkedList<>();
	private Socket socket;
	private CodedOutputStream outStream;
	private CodedInputStream inStream;
	
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
		socket = Gdx.net.newClientSocket(Protocol.TCP, host, port, null);
		inStream = CodedInputStream.newInstance(socket.getInputStream());
		outStream = CodedOutputStream.newInstance(socket.getOutputStream());
		Log.debug("Client.<init>", "Connected to server: " + socket.getRemoteAddress());
		listener.connected(socket);
	}
	
	public void render(){
		if(!socket.isConnected()){
			listener.disconnected(socket);
			Log.debug("Client.render", "Disconnected from server: " + (socket == null ? "null" : socket.getRemoteAddress()));
			return;
		}
		for(Iterator<SendStruct> iter = sendQueue.iterator(); iter.hasNext();){
			SendStruct sendStruct = iter.next();
			try {
				outStream.writeSInt32NoTag(sendStruct.messageType.ordinal());
				outStream.writeSInt32NoTag(sendStruct.message.getSerializedSize());
				sendStruct.message.writeTo(outStream);
				Log.debug("Client.render", "Sent message successfully: " + sendStruct.messageType.name());
			} catch (Exception e) {
				e.printStackTrace();
			}
			iter.remove();
		}
		try {
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dispose(){
		socket.dispose();
	}
	
	public interface IClientListener{
		void connected(Socket socket);
		void disconnected(Socket socket);
	}

	public void send(MessageType messageType, Message message) {
		sendQueue.add(new SendStruct(messageType, message));
	}
	
	class SendStruct{
		public MessageType messageType;
		public Message message;
		
		public SendStruct(MessageType messageType, Message message){
			this.messageType = messageType;
			this.message = message;
		}
	}
}
