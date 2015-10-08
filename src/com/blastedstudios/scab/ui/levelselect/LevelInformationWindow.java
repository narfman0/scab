package com.blastedstudios.scab.ui.levelselect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.util.GDXGame;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;
import com.blastedstudios.scab.ui.loading.GameplayLoadingWindowExecutor;
import com.blastedstudios.scab.ui.loading.LoadingWindow;
import com.blastedstudios.scab.util.ui.ScabTextButton;
import com.blastedstudios.scab.util.ui.ScabWindow;
import com.blastedstudios.scab.world.being.Player;

class LevelInformationWindow extends ScabWindow{
	public LevelInformationWindow(final Skin skin, 
			final GDXGame game, final Player player, final GDXWorld world, 
			final FileHandle selectedFile, final GDXRenderer gdxRenderer,
			final AssetManager sharedAssets, final LevelSelectScreen screen) {
		super("", skin);
		final List<String> levelList = new List<>(skin);
		for(GDXLevel level : world.getLevels())
			levelList.getItems().add(level.getName());
		final Button startButton = new ScabTextButton("Start", skin, new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				GDXLevel level = world.getLevel(levelList.getSelected());
				screen.getStage().addActor(new LoadingWindow(skin, 
						new GameplayLoadingWindowExecutor(game, player, level, world, selectedFile, gdxRenderer, sharedAssets)));
			}
		});
		add("Select level:");
		row();
		add(levelList);
		row();
		add(startButton);
		pack();
		setX(Gdx.graphics.getWidth()/2 - getWidth()/2);
		setY(Gdx.graphics.getHeight()/2 - getHeight()/2);
		setMovable(false);
	}
}
