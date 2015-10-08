package com.blastedstudios.scab.network;

import com.badlogic.gdx.net.Socket;
import com.blastedstudios.scab.world.being.Being;

public class ClientStruct {
	public Socket socket;
	public Being player;
	
	public ClientStruct(){}
	
	public ClientStruct(Socket socket){
		this.socket = socket;
	}
	
	public ClientStruct(Socket socket, Being player){
		this.socket = socket;
		this.player = player;
	}
}
