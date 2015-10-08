package com.blastedstudios.scab.network;

import com.badlogic.gdx.net.Socket;
import com.blastedstudios.scab.network.Messages.NetBeing;

public class ClientStruct {
	public Socket socket;
	public NetBeing player;
	
	public ClientStruct(){}
	
	public ClientStruct(Socket socket){
		this.socket = socket;
	}
	
	public ClientStruct(Socket socket, NetBeing player){
		this.socket = socket;
		this.player = player;
	}
}
