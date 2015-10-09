package com.blastedstudios.scab.ui.levelselect.network;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class ChatTable extends Table {
	private final TextArea chatText;
	private final TextField sendText;
	
	public ChatTable(Skin skin){
		super(skin);
		chatText = new TextArea("", skin);
		sendText = new TextField("", skin);
		add(chatText);
		row();
		add(sendText);
	}

	public TextArea getChatText() {
		return chatText;
	}

	public TextField getSendText() {
		return sendText;
	}
}
