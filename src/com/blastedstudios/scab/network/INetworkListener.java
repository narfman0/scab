package com.blastedstudios.scab.network;

public interface INetworkListener {
	void connected(HostStruct struct);
	void disconnected(HostStruct struct);
	void nameUpdate(HostStruct struct);
	void textMessage(HostStruct struct, String text);
}
