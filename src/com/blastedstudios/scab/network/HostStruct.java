package com.blastedstudios.scab.network;

import com.badlogic.gdx.net.Socket;
import com.blastedstudios.scab.network.Messages.NetBeing;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

public class HostStruct {
	public final Socket socket;
	public final CodedOutputStream outStream;
	public final CodedInputStream inStream;
	public NetBeing.Builder player;
	
	public HostStruct(Socket socket){
		this.socket = socket;
		inStream = CodedInputStream.newInstance(socket.getInputStream());
		outStream = CodedOutputStream.newInstance(socket.getOutputStream());
	}
	
	public String toString(){
		return player != null && player.hasName() ? player.getName() : socket.getRemoteAddress();
	}
}
