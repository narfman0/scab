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
import com.blastedstudios.scab.ui.levelselect.network.NetworkWindow;
import com.blastedstudios.scab.world.being.Player;

public class LevelSelectScreen extends ScabScreen{
	private final AssetManager sharedAssets;
	private final PannerManager panner;
	private final NetworkWindow networkWindow;

	public LevelSelectScreen(final GDXGame game, Player player, final GDXWorld gdxWorld, final FileHandle worldFile,
			GDXRenderer gdxRenderer, AssetManager sharedAssets, PannerManager panner){
		super(game);
		this.sharedAssets = sharedAssets;
		this.panner = panner;
		Log.log("LevelSelect.<init>", "Loaded world successfully");
		sharedAssets.finishLoading();
		stage.addActor(new LevelInformationWindow(skin, 
				game, player, gdxWorld, worldFile, gdxRenderer, sharedAssets, this));
		networkWindow = new NetworkWindow(skin, player);
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
