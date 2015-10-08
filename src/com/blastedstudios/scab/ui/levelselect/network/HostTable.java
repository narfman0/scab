package com.blastedstudios.scab.ui.levelselect.network;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.scab.network.ClientStruct;
import com.blastedstudios.scab.network.Host;

public class HostTable extends Table {
	private Host host;
	
	public HostTable(Skin skin){
		super(skin);
		List<String> clients = new List<String>(skin);
		host = new Host(new Host.IHostListener() {
			@Override public void disconnected(ClientStruct struct) {
				if(struct.player != null)
					clients.getItems().removeValue(struct.player.getName(), false);
				clients.getItems().removeValue(struct.socket.getRemoteAddress(), false);
			}
			@Override public void connected(ClientStruct struct) {
				clients.getItems().add(struct.socket.getRemoteAddress());
			}
			@Override public void nameUpdate(ClientStruct struct) {
				clients.getItems().removeValue(struct.socket.getRemoteAddress(), false);
				clients.getItems().add(struct.player.getName());
			}
		});
	}
	
	public void render(){
		host.render();
	}
	
	@Override public boolean remove(){
		host.dispose();
		return super.remove();
	}
}
