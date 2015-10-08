package com.blastedstudios.scab.network.message;

import com.blastedstudios.scab.world.being.Being;

public class ClientBeing {
	private Being player;
	
	public ClientBeing(){}
	
	public ClientBeing(Being player){
		this.player = player;
	}

	public Being getPlayer() {
		return player;
	}

	public void setPlayer(Being player) {
		this.player = player;
	}
}
