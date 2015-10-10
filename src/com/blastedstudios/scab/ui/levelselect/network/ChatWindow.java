package com.blastedstudios.scab.ui.levelselect.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.scab.network.BaseNetwork;
import com.blastedstudios.scab.network.IMessageListener;
import com.blastedstudios.scab.network.MessageType;
import com.blastedstudios.scab.network.Messages.Text;
import com.blastedstudios.scab.network.Messages.TextRequest;
import com.blastedstudios.scab.util.ui.ScabTextButton;
import com.blastedstudios.scab.util.ui.ScabWindow;

public class ChatWindow extends ScabWindow implements IMessageListener {
	private final TextArea chatText;
	private final TextField sendText;
	private final BaseNetwork network;
	
	public ChatWindow(Skin skin, BaseNetwork network){
		super("", skin);
		this.network = network;
		chatText = new TextArea("", skin);
		chatText.setPrefRows(5);
		chatText.setWidth(800f);
		chatText.setTouchable(Touchable.disabled);
		sendText = new TextField("", skin);
		if(network.isConnected()){
			network.addListener(MessageType.TEXT, this);
		}else
			network.addListener(MessageType.CONNECTED, new IMessageListener() {
				@Override public void receive(Object object) {
					network.removeListener(MessageType.CONNECTED, this);
					network.addListener(MessageType.TEXT, ChatWindow.this);
				}
			});
		final Button sendButton = new ScabTextButton("Send", skin, new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				if(network != null){
					TextRequest.Builder textRequest = TextRequest.newBuilder();
					textRequest.setContent(sendText.getText());
					network.send(MessageType.TEXT_REQUEST, textRequest.build());
				}
				sendText.setText("");
			}
		});
		sendText.setWidth(chatText.getWidth() - sendButton.getWidth() - 8f);
		add("Chat");
		row();
		add(chatText).colspan(2);
		row();
		add(sendText);
		add(sendButton);

		pack();
		setX(Gdx.graphics.getWidth()/2 - getWidth()/2);
		setMovable(false);
	}
	
	@Override public boolean remove(){
		if(network != null)
			network.removeListener(MessageType.TEXT, this);
		return super.remove();
	}

	@Override public void receive(Object object) {
		Text text = (Text) object;
		String appended = chatText.getText().equals("") ? "" : "\n";
		chatText.setText(chatText.getText() + appended + text.getContent());
		chatText.setCursorPosition(chatText.getText().length()-1);
	}
}
