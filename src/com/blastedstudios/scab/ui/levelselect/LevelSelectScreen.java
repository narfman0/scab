package com.blastedstudios.scab.ui.levelselect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.worldeditor.WorldEditorScreen;
import com.blastedstudios.gdxworld.util.GDXGame;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;
import com.blastedstudios.scab.input.ActionEnum;
import com.blastedstudios.scab.plugin.quest.handler.manifestation.SoundThematicHandlerPlugin;
import com.blastedstudios.scab.ui.ScabScreen;
import com.blastedstudios.scab.util.SpriteTweenAccessor;
import com.blastedstudios.scab.util.ui.UIHelper;
import com.blastedstudios.scab.world.being.Player;

public class LevelSelectScreen extends ScabScreen{
	private static final float OFFSET_SCALAR = Properties.getFloat("world.level.offset.scalar", 20);
	private final SpriteBatch spriteBatch = new SpriteBatch();
	private final Player player;
	private LevelInformationWindow levelInfo;
	private final GDXWorld gdxWorld;
	private final FileHandle worldFile;
	private final GDXRenderer gdxRenderer;
	private final AssetManager sharedAssets;

	public LevelSelectScreen(final GDXGame game, Player player, final GDXWorld gdxWorld, final FileHandle worldFile,
			GDXRenderer gdxRenderer, AssetManager sharedAssets){
		super(game);
		this.player = player;
		this.gdxWorld = gdxWorld;
		this.worldFile = worldFile;
		this.gdxRenderer = gdxRenderer;
		this.sharedAssets = sharedAssets;
		Log.log("LevelSelect.<init>", "Loaded world successfully");
		sharedAssets.finishLoading();
		GDXLevel firstLevel = gdxWorld.getLevels().get(0);
		stage.addActor(levelInfo = new LevelInformationWindow(skin, 
				firstLevel, game, player, gdxWorld, worldFile, gdxRenderer, sharedAssets, this));
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
		SoundThematicHandlerPlugin.get().tick(delta);
		sharedAssets.update();
		stage.draw();
	}
}
