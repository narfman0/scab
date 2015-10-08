package com.blastedstudios.scab.network;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.Socket;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.scab.network.Messages.NetBeing;
import com.google.protobuf.CodedInputStream;

public class Host {
	private final List<ClientStruct> clients = Collections.synchronizedList(new LinkedList<ClientStruct>());
	private final IHostListener listener;
	private ServerSocket serverSocket;
	private Timer timer;
	
	public Host(IHostListener listener){
		this.listener = listener;
		serverSocket = Gdx.net.newServerSocket(Protocol.TCP, Properties.getInt("network.port"), null);
		timer = new Timer("Server accept thread");
		timer.schedule(new TimerTask() {
			@Override public void run() {
				if(serverSocket == null){
					// Network has shut down, dispose of everything
					this.cancel();
					return;
				}
				Socket socket = serverSocket.accept(null);
				ClientStruct client = new ClientStruct(socket); 
				clients.add(client);
				Log.debug("Host.<init>", "Added client: " + socket.getRemoteAddress());
				listener.connected(client);
			}
		}, 0, 100);
		Log.debug("Host.<init>", "Network created, listening for conenctions");
	}
	
	public void dispose(){
		serverSocket.dispose();
		serverSocket = null;
		for(ClientStruct client : clients)
			client.socket.dispose();
		timer.cancel();
	}
	
	public void render(){
		for(Iterator<ClientStruct> iter = clients.iterator(); iter.hasNext();){
			ClientStruct client = iter.next();
			Socket socket = client.socket;
			if(!socket.isConnected()){
				listener.disconnected(client);
				Log.debug("Host.render", "Disconnecting client: " + socket.getRemoteAddress());
				iter.remove();
			}else{
				CodedInputStream stream = CodedInputStream.newInstance(socket.getInputStream()); 
				try {
					while(!stream.isAtEnd() && socket.isConnected()){
						MessageType messageType = MessageType.values()[stream.readSInt32()];
						byte[] buffer =  stream.readRawBytes(stream.readSInt32());
						switch(messageType){
						case CLIENT_BEING:
							client.player = NetBeing.parseFrom(buffer);
							Log.debug("Host.render", "Received player: " + socket.getRemoteAddress());
							listener.clientBeing(client);
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public interface IHostListener{
		void connected(ClientStruct struct);
		void disconnected(ClientStruct struct);
		void clientBeing(ClientStruct struct);
	}
}
