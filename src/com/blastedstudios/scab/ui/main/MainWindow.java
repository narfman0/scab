package com.blastedstudios.scab.ui.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.util.GDXGame;
import com.blastedstudios.gdxworld.util.panner.PannerManager;
import com.blastedstudios.gdxworld.world.GDXWorld;
import com.blastedstudios.scab.ui.levelselect.LevelSelectScreen;
import com.blastedstudios.scab.util.SaveHelper;
import com.blastedstudios.scab.util.ui.ScabTextButton;
import com.blastedstudios.scab.util.ui.ScabWindow;
import com.blastedstudios.scab.world.being.Player;

class MainWindow extends ScabWindow{
	public MainWindow(final Skin skin, final GDXGame game, final IMainWindowListener listener, 
			final GDXWorld gdxWorld, final FileHandle worldFile, final GDXRenderer gdxRenderer,
			final AssetManager sharedAssets, PannerManager panner) {
		super("", skin);
		final Button newButton = new ScabTextButton("Create New", skin, new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				listener.newCharacterButtonClicked();
			}
		});
		final Button optionsButton = new ScabTextButton("Options", skin, new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				listener.optionsButtonClicked();
			}
		});
		final Button exitButton = new ScabTextButton("Exit", skin, new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				listener.exitButtonClicked();
			}
		});
		add(new Label("Scab", skin));
		row();
		add(newButton).fillX();
		row();
		for(final Player being : SaveHelper.load()){
			final Button savedCharacterButton = new ScabTextButton(being.getName(), skin, new ClickListener() {
				@Override public void clicked(InputEvent event, float x, float y) {
					LevelSelectScreen screen = new LevelSelectScreen(game, being, 
							gdxWorld, worldFile, gdxRenderer, sharedAssets, panner); 
					game.pushScreen(screen);
				}
			});
			add(savedCharacterButton).fillX();
			row();
		}
		add(optionsButton).fillX();
		row();
		add(exitButton).fillX();
		pack();
		setX(Gdx.graphics.getWidth()/2 - getWidth()/2);
		setY(Gdx.graphics.getHeight()/2 - getHeight()/2);
		setMovable(false);
	}
	
	interface IMainWindowListener{
		void newCharacterButtonClicked();
		void optionsButtonClicked();
		void exitButtonClicked();
	}
}