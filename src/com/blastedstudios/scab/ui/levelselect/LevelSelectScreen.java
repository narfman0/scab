package com.blastedstudios.scab.ui.levelselect;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.worldeditor.WorldEditorScreen;
import com.blastedstudios.gdxworld.util.GDXGame;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.panner.PannerManager;
import com.blastedstudios.gdxworld.world.GDXWorld;
import com.blastedstudios.scab.input.ActionEnum;
import com.blastedstudios.scab.plugin.quest.handler.manifestation.SoundThematicHandlerPlugin;
import com.blastedstudios.scab.ui.ScabScreen;
import com.blastedstudios.scab.ui.levelselect.network.ChatWindow;
import com.blastedstudios.scab.ui.levelselect.network.NetworkWindow;
import com.blastedstudios.scab.ui.levelselect.network.NetworkWindow.INetworkWindowListener;
import com.blastedstudios.scab.ui.levelselect.network.NetworkWindow.MultiplayerType;
import com.blastedstudios.scab.world.being.Player;

public class LevelSelectScreen extends ScabScreen{
	private final AssetManager sharedAssets;
	private final PannerManager panner;
	private final NetworkWindow networkWindow;
	private LevelInformationWindow levelWindow;
	private ChatWindow chat;

	public LevelSelectScreen(final GDXGame game, final Player player, final GDXWorld gdxWorld, final FileHandle worldFile,
			final GDXRenderer gdxRenderer, final AssetManager sharedAssets, final PannerManager panner){
		super(game);
		this.sharedAssets = sharedAssets;
		this.panner = panner;
		Log.log("LevelSelect.<init>", "Loaded world successfully");
		sharedAssets.finishLoading();
		levelWindow = new LevelInformationWindow(skin, 
				game, player, gdxWorld, worldFile, gdxRenderer, sharedAssets, this, true, false);
		stage.addActor(levelWindow);
		networkWindow = new NetworkWindow(skin, player, new INetworkWindowListener() {
			@Override public void networkSelected(MultiplayerType type) {
				levelWindow.remove();
				if(chat != null)
					chat.remove();
				chat = null;
				switch(type){
				case Client:
					levelWindow = new LevelInformationWindow(skin, 
						game, player, gdxWorld, worldFile, gdxRenderer, sharedAssets, LevelSelectScreen.this, false, false);
					chat = new ChatWindow(skin, networkWindow.getSource());
					break;
				case Host:
					levelWindow = new LevelInformationWindow(skin, 
						game, player, gdxWorld, worldFile, gdxRenderer, sharedAssets, LevelSelectScreen.this, false, true);
					chat = new ChatWindow(skin, networkWindow.getSource());
					break;
				case Local:
					levelWindow = new LevelInformationWindow(skin, 
						game, player, gdxWorld, worldFile, gdxRenderer, sharedAssets, LevelSelectScreen.this, true, false);
					break;
				}
				stage.addActor(levelWindow);
				if(chat != null)
					stage.addActor(chat);
			}
		});
		stage.addActor(networkWindow);
		register(ActionEnum.BACK, new AbstractInputHandler() {
			public void down(){
				game.popScreen();
			}
		});
		register(ActionEnum.ACTION, new AbstractInputHandler() {
			public void down(){
				if(ActionEnum.MODIFIER.isPressed())
					game.pushScreen(new WorldEditorScreen(game, gdxWorld, worldFile));
			}
		});
	}

	@Override public void render(float delta){
		super.render(delta);
		networkWindow.render();
		SoundThematicHandlerPlugin.get().tick(delta);
		sharedAssets.update();
		panner.updatePanners(delta);
		stage.draw();
	}
}
