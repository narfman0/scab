package com.blastedstudios.scab.ui.levelselect.network;

import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.scab.network.Client;
import com.blastedstudios.scab.network.MessageType;
import com.blastedstudios.scab.network.Messages.NetBeing;
import com.blastedstudios.scab.util.ui.ScabTextButton;
import com.blastedstudios.scab.world.being.Being;

public class ClientTable extends Table {
	private Client client;
	
	public ClientTable(Skin skin, Being player){
		super(skin);
		add("Hostname: ");
		final TextField hostnameText = new TextField("127.0.0.1", skin);
		add(hostnameText);
		row();
		TextButton connectButton = new ScabTextButton("Connect", skin, new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				if(client != null){
					Log.error("ClientTable.<init>", "Already connected to a host, aborting");
					return;
				}
				client = new Client(new Client.IClientListener() {
					@Override public void disconnected(Socket socket) {
						client.dispose();
						client = null;
					}
					@Override public void connected(Socket socket) {
						NetBeing.Builder netBeing = NetBeing.newBuilder();
						netBeing.setName(player.getName());
						client.send(MessageType.NAME_UPDATE, netBeing.build());
					}
				});
				client.connect(hostnameText.getText());
			}
		});
		add(connectButton).colspan(2);
	}
	
	public void render(){
		if(client != null)
			client.render();
	}
	
	@Override public boolean remove(){
		if(client != null)
			client.dispose();
		return super.remove();
	}
}
