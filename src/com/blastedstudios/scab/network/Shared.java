package com.blastedstudios.scab.network;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.net.Socket;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.scab.network.Messages.NetBeing;
import com.blastedstudios.scab.network.Messages.Text;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

public class Shared {
	public static void sendMessages(List<MessageStruct> currentQueue, CodedOutputStream stream){
		for(MessageStruct sendStruct : currentQueue){
			try {
				stream.writeSInt32NoTag(sendStruct.messageType.ordinal());
				stream.writeSInt32NoTag(sendStruct.message.getSerializedSize());
				sendStruct.message.writeTo(stream);
				Log.debug("Client.render", "Sent message successfully: " + sendStruct.messageType.name());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			stream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<MessageStruct> receiveMessages(CodedInputStream stream, Socket socket){
		List<MessageStruct> messages = new LinkedList<>();
		try {
			while(socket.getInputStream().available() > 0 && socket.isConnected()){
				MessageType messageType = MessageType.values()[stream.readSInt32()];
				byte[] buffer =  stream.readRawBytes(stream.readSInt32());
				switch(messageType){
				case NAME_UPDATE:
					messages.add(new MessageStruct(messageType, NetBeing.parseFrom(buffer)));
					break;
				case TEXT_MESSAGE:
					messages.add(new MessageStruct(messageType, Text.parseFrom(buffer)));
					break;
				case CONNECTED:
				case DISCONNECTED:
					//Do nothing intentionally!
					break;
				}
				Log.debug("Host.render", "Received " + messageType.name() + " from " + socket.getRemoteAddress());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messages;
	}
}
