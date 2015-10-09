package com.blastedstudios.scab.ui.levelselect.network;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.scab.network.HostStruct;
import com.blastedstudios.scab.network.Host;
import com.blastedstudios.scab.network.IMessageListener;
import com.blastedstudios.scab.network.MessageType;

public class HostTable extends Table {
	private Host host;
	
	public HostTable(Skin skin){
		super(skin);
		List<String> clients = new List<String>(skin);
		host = new Host();
		host.addListener(MessageType.CONNECTED, new IMessageListener() {
			@Override public void receive(Object object) {
				HostStruct struct = (HostStruct) object;
				clients.getItems().add(struct.socket.getRemoteAddress());
			}
		});
		host.addListener(MessageType.DISCONNECTED, new IMessageListener() {
			@Override public void receive(Object object) {
				HostStruct struct = (HostStruct) object;
				if(struct.player != null)
					clients.getItems().removeValue(struct.player.getName(), false);
				clients.getItems().removeValue(struct.socket.getRemoteAddress(), false);
			}
		});
		host.addListener(MessageType.NAME_UPDATE, new IMessageListener() {
			@Override public void receive(Object object) {
				HostStruct struct = (HostStruct) object;
				// got name, append to ip
				for(int i=0; i<clients.getItems().size; i++)
					if(clients.getItems().get(i).equals(struct.socket.getRemoteAddress()))
						clients.getItems().set(i, clients.getItems().get(i) + " " + struct.player.getName());
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
