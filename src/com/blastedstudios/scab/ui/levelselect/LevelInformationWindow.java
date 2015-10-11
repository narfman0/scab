package com.blastedstudios.scab.ui.levelselect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;
import com.blastedstudios.scab.ui.levelselect.network.NetworkWindow.MultiplayerType;
import com.blastedstudios.scab.util.ui.ScabTextButton;
import com.blastedstudios.scab.util.ui.ScabWindow;

class LevelInformationWindow extends ScabWindow{
	final List<String> levelList;
	
	public LevelInformationWindow(final Skin skin, final GDXWorld world, final LevelSelectScreen screen, MultiplayerType type) {
		super("", skin);
		boolean isControl = type == MultiplayerType.Local || type == MultiplayerType.Host;
		levelList = new List<>(skin);
		levelList.setTouchable(isControl ? Touchable.enabled : Touchable.disabled);
		for(GDXLevel level : world.getLevels())
			levelList.getItems().add(level.getName());
		final Button startButton = new ScabTextButton("Start", skin, new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				screen.levelSelected(levelList.getSelected());
			}
		});
		add(isControl ? "Select level: " : "Level: ");
		row();
		add(levelList);
		if(isControl){
			row();
			add(startButton);
		}
		pack();
		setX(Gdx.graphics.getWidth()/2 - getWidth()/2);
		setY(Gdx.graphics.getHeight()/2 - getHeight()/2);
		setMovable(false);
	}
}
