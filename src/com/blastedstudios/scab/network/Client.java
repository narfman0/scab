package com.blastedstudios.scab.network;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.blastedstudios.gdxworld.plugin.serializer.json.JSONSerializer;
import com.blastedstudios.gdxworld.util.ISerializer;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.Properties;

public class Client {
	private final ISerializer SERIALIZER = new JSONSerializer();
	private final IClientListener listener;
	private final LinkedList<SendStruct> sendQueue = new LinkedList<>();
	private Socket socket;
	
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
				ByteArrayOutputStream ostream = new ByteArrayOutputStream();
				SERIALIZER.save(ostream, sendStruct.object);
				ByteBuffer buffer = ByteBuffer.allocate(2 + 4 + ostream.size());
				buffer.putShort((short) sendStruct.messageType.ordinal());
				buffer.putInt(ostream.size());
				buffer.put(ostream.toByteArray());
				socket.getOutputStream().write(buffer.array());
				Log.debug("Client.render", "Sent message successfully: " + sendStruct.messageType.name());
			} catch (Exception e) {
				e.printStackTrace();
			}
			iter.remove();
		}
	}
	
	public void dispose(){
		socket.dispose();
	}
	
	public interface IClientListener{
		void connected(Socket socket);
		void disconnected(Socket socket);
	}

	public void send(MessageType messageType, Object object) {
		sendQueue.add(new SendStruct(messageType, object));
	}
	
	class SendStruct{
		public MessageType messageType;
		public Object object;
		
		public SendStruct(MessageType messageType, Object object){
			this.messageType = messageType;
			this.object = object;
		}
	}
}
