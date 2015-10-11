package com.blastedstudios.scab.ui.levelselect.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.blastedstudios.scab.network.BaseNetwork;
import com.blastedstudios.scab.util.ui.ScabWindow;
import com.blastedstudios.scab.world.being.Being;

public class NetworkWindow extends ScabWindow{
	public enum MultiplayerType{
		Local, Host, Client
	}
	
	private final Table multiplayerTypeParentTable;
	private final SelectBox<MultiplayerType> multiplayerTypeSelect;
	private HostTable hostTable = null;
	private ClientTable clientTable = null;

	public NetworkWindow(Skin skin, Being player, INetworkWindowListener listener) {
		super("", skin);
		multiplayerTypeParentTable = new Table(skin);
		multiplayerTypeSelect = new SelectBox<>(skin);
		multiplayerTypeSelect.setItems(MultiplayerType.values());
		multiplayerTypeSelect.addListener(new ChangeListener() {
			@Override public void changed(ChangeEvent event, Actor actor) {
				multiplayerTypeParentTable.clear();
				if(hostTable != null)
					hostTable.remove();
				if(clientTable != null)
					clientTable.remove();
				switch(multiplayerTypeSelect.getSelected()){
				case Host:
					hostTable = new HostTable(skin, player);
					multiplayerTypeParentTable.add(hostTable);
					break;
				case Client:
					clientTable = new ClientTable(skin, player);
					multiplayerTypeParentTable.add(clientTable);
					break;
				case Local:
					break;
				}
				listener.networkSelected(multiplayerTypeSelect.getSelected());
			}
		});
		add("Network");
		add(multiplayerTypeSelect);
		row();
		add(multiplayerTypeParentTable).colspan(2);
		setY(Gdx.graphics.getHeight()/2 - getHeight()/2);
		setSize(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/3f);
		setMovable(false);
	}
	
	public void render(){
		if(hostTable != null)
			hostTable.render();
		if(clientTable != null)
			clientTable.render();
	}
	
	public BaseNetwork getSource(){
		if(hostTable != null)
			return hostTable.getHost();
		if(clientTable != null)
			return clientTable.getClient();
		return null;
	}
	
	public MultiplayerType getMultiplayerType(){
		return multiplayerTypeSelect.getSelected();
	}
	
	public interface INetworkWindowListener{
		void networkSelected(MultiplayerType type);
	}
}
