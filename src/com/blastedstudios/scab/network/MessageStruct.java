package com.blastedstudios.scab.network;

import com.google.protobuf.Message;

public class MessageStruct{
	public final MessageType messageType;
	public final Message message;
	
	public MessageStruct(MessageType messageType, Message message){
		this.messageType = messageType;
		this.message = message;
	}
}