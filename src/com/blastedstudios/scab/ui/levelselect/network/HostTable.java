package com.blastedstudios.scab.ui.levelselect.network;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.scab.network.HostStruct;
import com.blastedstudios.scab.network.Host;
import com.blastedstudios.scab.network.INetworkListener;

public class HostTable extends Table {
	private Host host;
	
	public HostTable(Skin skin){
		super(skin);
		List<String> clients = new List<String>(skin);
		host = new Host();
		host.addListener(new INetworkListener() {
			@Override public void disconnected(HostStruct struct) {
				if(struct.player != null)
					clients.getItems().removeValue(struct.player.getName(), false);
				clients.getItems().removeValue(struct.socket.getRemoteAddress(), false);
			}
			@Override public void connected(HostStruct struct) {
				clients.getItems().add(struct.socket.getRemoteAddress());
			}
			@Override public void nameUpdate(HostStruct struct) {
				// got name, append to ip
				for(int i=0; i<clients.getItems().size; i++)
					if(clients.getItems().get(i).equals(struct.socket.getRemoteAddress()))
						clients.getItems().set(i, clients.getItems().get(i) + " " + struct.player.getName());
			}
			@Override public void textMessage(HostStruct struct, String text) {
				// TODO Auto-generated method stub
			}
		});
		add(clients);
	}
	
	public void render(){
		host.render();
	}
	
	@Override public boolean remove(){
		host.dispose();
		return super.remove();
	}
}
