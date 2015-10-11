package com.blastedstudios.scab.network;

public interface IMessageListener {
	void receive(MessageType messageType, Object object);
}
