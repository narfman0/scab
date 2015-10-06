package com.blastedstudios.scab.ui.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.scab.util.ui.ScabTextButton;
import com.blastedstudios.scab.util.ui.ScabWindow;

public class BackWindow extends ScabWindow {
	public BackWindow(final Skin skin, final GameplayScreen screen) {
		super("", skin);
		Button button = new ScabTextButton(Properties.get("ui.back.button.text", "Exit to Map"), skin, new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				screen.levelComplete(false, null);
			}
		});
		add(button);
		pack();
		setX(Gdx.graphics.getWidth()/2 - getWidth()/2);
		setMovable(false);
	}
}
