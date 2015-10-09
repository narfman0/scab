package com.blastedstudios.scab.ui.levelselect.network;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.scab.network.Client;
import com.blastedstudios.scab.network.HostStruct;
import com.blastedstudios.scab.network.INetworkListener;
import com.blastedstudios.scab.network.MessageType;
import com.blastedstudios.scab.network.Messages.NetBeing;
import com.blastedstudios.scab.util.ui.ScabTextButton;
import com.blastedstudios.scab.world.being.Being;

public class ClientTable extends Table {
	private Client client;
	private ChatTable chatTable;
	
	public ClientTable(Skin skin, Being player){
		super(skin);
		final Table clientTable = new Table(skin);
		final TextField hostnameText = new TextField("127.0.0.1", skin);
		TextButton connectButton = new ScabTextButton("Connect", skin, new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				if(client != null){
					Log.error("ClientTable.<init>", "Already connected to a host, aborting");
					return;
				}
				client = new Client();
				client.addListener(new INetworkListener() {
					@Override public void disconnected(HostStruct struct) {
						client.dispose();
						client = null;
						chatTable.remove();
						chatTable = null;
					}
					@Override public void connected(HostStruct struct) {
						clientTable.remove();
						chatTable = new ChatTable(skin);
						clientTable.add(chatTable);
						// send minimal information - name!
						NetBeing.Builder netBeing = NetBeing.newBuilder();
						netBeing.setName(player.getName());
						client.send(MessageType.NAME_UPDATE, netBeing.build());
					}
					@Override public void nameUpdate(HostStruct struct) {}
					@Override public void textMessage(HostStruct struct, String text) {
						if(chatTable != null)
							chatTable.getChatText().appendText("\n" + text);
					}
				});
				client.connect(hostnameText.getText());
			}
		});
		clientTable.add("Hostname: ");
		clientTable.add(hostnameText);
		clientTable.row();
		clientTable.add(connectButton).colspan(2);
		add(clientTable);
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
