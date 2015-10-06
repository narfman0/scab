package com.blastedstudios.scab.ui.overworld;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Cubic;

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

public class OverworldScreen extends ScabScreen{
	private static final float OFFSET_SCALAR = Properties.getFloat("world.level.offset.scalar", 20);
	private final SpriteBatch spriteBatch = new SpriteBatch();
	private final Sprite backgroundSprite, levelSprite, levelCurrentSprite;
	private final Player player;
	private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	private LevelInformationWindow levelInfo;
	private final Vector2 upperLeft = new Vector2(), lowerRight = new Vector2();
	private final GDXWorld gdxWorld;
	private final FileHandle worldFile;
	private final GDXRenderer gdxRenderer;
	private final TweenManager tweenManager;
	private final AssetManager sharedAssets;

	public OverworldScreen(final GDXGame game, Player player, final GDXWorld gdxWorld, final FileHandle worldFile,
			GDXRenderer gdxRenderer, AssetManager sharedAssets){
		super(game);
		this.player = player;
		this.gdxWorld = gdxWorld;
		this.worldFile = worldFile;
		this.gdxRenderer = gdxRenderer;
		this.sharedAssets = sharedAssets;
		Log.log("OverworldScreen.<init>", "Loaded world successfully");
		levelSprite = UIHelper.getSprite(skin, "overworld-level", "circle");
		try{
			levelSprite.setColor(UIHelper.getColor(skin, "overworld-level", "secondary"));
		}catch(Exception e){}
		levelCurrentSprite = new Sprite(UIHelper.getSprite(skin, "overworld-level-current", "overworld-level", "circle"));
		try{
			levelCurrentSprite.setColor(UIHelper.getColor(skin, "overworld-level-current", "overworld-level", "primary"));
		}catch(Exception e){}
		String backgroundPath = "data/textures/" + gdxWorld.getWorldProperties().get("background");
		sharedAssets.finishLoading();
		backgroundSprite = new Sprite(sharedAssets.get(backgroundPath, Texture.class));
		backgroundSprite.setPosition(backgroundSprite.getWidth()/-2f, backgroundSprite.getHeight()/-2f);
		for(GDXLevel level : gdxWorld.getLevels())
			if(level.getCoordinates().x < upperLeft.x)
				upperLeft.x = level.getCoordinates().x;
			else if(level.getCoordinates().y > upperLeft.y)
				upperLeft.y = level.getCoordinates().y;
			else if(level.getCoordinates().x > lowerRight.x)
				lowerRight.x = level.getCoordinates().x;
			else if(level.getCoordinates().y < lowerRight.y)
				lowerRight.y = level.getCoordinates().y;
		GDXLevel firstLevel = gdxWorld.getLevels().get(0);
		stage.addActor(levelInfo = new LevelInformationWindow(skin, 
				firstLevel, game, player, gdxWorld, worldFile, gdxRenderer, sharedAssets, this));
		levelCurrentSprite.setPosition(firstLevel.getCoordinates().x*OFFSET_SCALAR, firstLevel.getCoordinates().y*OFFSET_SCALAR);
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteTweenAccessor());
		camera.update();
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
		tweenManager.update(delta);
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		backgroundSprite.draw(spriteBatch);
		for(GDXLevel level : gdxWorld.getLevels()){
			if(player.isLevelAvailable(gdxWorld, level)){
				Vector2 position = level.getCoordinates().cpy().scl(OFFSET_SCALAR);
				levelSprite.setPosition(position.x, position.y);
				levelSprite.draw(spriteBatch);
			}
		}
		levelCurrentSprite.draw(spriteBatch);
		spriteBatch.end();
		stage.draw();
	}

	@Override public boolean touchDown(int x, int y, int ptr, int button) {
		super.touchDown(x, y, ptr, button);
		if(!levelInfo.contains(x, y)){
			Vector2 coordinates = GDXRenderer.toWorldCoordinates(camera, new Vector2(x,y));
			coordinates.scl(1f/OFFSET_SCALAR);
			GDXLevel level = gdxWorld.getClosestLevel(coordinates.x,coordinates.y);
			if(Math.abs(level.getCoordinates().x - coordinates.x) < levelSprite.getWidth()/2 &&
					Math.abs(level.getCoordinates().y - coordinates.y) < levelSprite.getHeight()/2 &&
					player.isLevelAvailable(gdxWorld, level)){
				if(levelInfo != null)
					levelInfo.remove();
				stage.addActor(levelInfo = new LevelInformationWindow(skin, 
						level, game, player, gdxWorld, worldFile, gdxRenderer, sharedAssets, this));
				float duration = Properties.getFloat("overworld.tween.duration", .5f);
				Tween.to(levelCurrentSprite, SpriteTweenAccessor.POSITION_XY, duration).
					target(level.getCoordinates().x*OFFSET_SCALAR, level.getCoordinates().y*OFFSET_SCALAR).
					ease(Cubic.INOUT).start(tweenManager);
				Gdx.audio.newSound(Gdx.files.internal("data/sounds/ui/slide.mp3")).play(Properties.getFloat("sound.volume", 1f));
			}
		}
		return false;
	}
}
