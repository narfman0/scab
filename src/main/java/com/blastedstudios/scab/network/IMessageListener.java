package com.blastedstudios.scab.network;

import com.blastedstudios.scab.network.Messages.MessageType;

public interface IMessageListener {
	void receive(MessageType messageType, Object object);
}
