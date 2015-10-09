package com.blastedstudios.scab.network;

import java.util.ArrayList;
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
import com.google.protobuf.Message;

public class Host {
	private final LinkedList<MessageStruct> sendQueue = new LinkedList<>();
	private final List<HostStruct> clients = Collections.synchronizedList(new LinkedList<HostStruct>());
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
				HostStruct client = new HostStruct(socket); 
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
		for(HostStruct client : clients)
			client.socket.dispose();
		timer.cancel();
	}
	
	public void render(){
		// Build new list of messages to send this frame. Grab messages initially, don't check queue again!
		ArrayList<MessageStruct> currentQueue = new ArrayList<>(sendQueue);
		// "but jrob, thats a queue that could be modified between copying and clearing, you should iterate..."
		// GTFO /uninstall /uninstall /uninstall
		// no but you're right... *shrugs*
		sendQueue.clear();
		
		for(Iterator<HostStruct> iter = clients.iterator(); iter.hasNext();){
			HostStruct client = iter.next();
			if(!client.socket.isConnected()){
				listener.disconnected(client);
				Log.debug("Host.render", "Disconnecting client: " + client.socket.getRemoteAddress());
				iter.remove();
			}else{
				List<MessageStruct> messages = Shared.receiveMessages(client.inStream, client.socket);
				for(MessageStruct message : messages){
					switch(message.messageType){
					case NAME_UPDATE:
						NetBeing being = (NetBeing) message.message;
						if(client.player == null)
							client.player = NetBeing.newBuilder(being);
						else
							client.player.setName(being.getName());
						listener.nameUpdate(client);
						break;
					}
				}
				Shared.sendMessages(currentQueue, client.outStream);
			}
		}
	}
	
	public interface IHostListener{
		void connected(HostStruct struct);
		void disconnected(HostStruct struct);
		void nameUpdate(HostStruct struct);
	}

	public void send(MessageType messageType, Message message) {
		sendQueue.add(new MessageStruct(messageType, message));
	}
}
