package com.blastedstudios.scab.ui.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.util.GDXGame;
import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.util.panner.PannerManager;
import com.blastedstudios.gdxworld.world.GDXWorld;
import com.blastedstudios.scab.input.ActionEnum;
import com.blastedstudios.scab.plugin.quest.handler.ISharedAssetConsumer;
import com.blastedstudios.scab.plugin.quest.handler.manifestation.SoundThematicHandlerPlugin;
import com.blastedstudios.scab.ui.ScabScreen;
import com.blastedstudios.scab.ui.main.MainWindow.IMainWindowListener;
import com.blastedstudios.scab.ui.main.NewCharacterWindow.INewCharacterWindowListener;
import com.blastedstudios.scab.ui.main.OptionsWindow.IOptionsWindowListener;
import com.blastedstudios.scab.util.SaveHelper;

public class MainScreen extends ScabScreen implements IMainWindowListener, INewCharacterWindowListener, IOptionsWindowListener {
	public static final String SKIN_PATH = Properties.get("screen.skin","data/ui/uiskinGame.json");
	public static final FileHandle WORLD_FILE = Gdx.files.internal("data/world/" + Properties.get("world.path", "world.xml"));
	private final GDXWorld gdxWorld;
	private final GDXRenderer gdxRenderer;
	private final AssetManager sharedAssets;
	private final PannerManager panner;
	private NewCharacterWindow newCharacterWindow;
	private OptionsWindow optionsWindow;
	private MainWindow mainWindow;

	public MainScreen(final GDXGame game, AssetManager sharedAssets, GDXWorld gdxWorld){
		super(game);
		this.sharedAssets = sharedAssets;
		this.gdxWorld = gdxWorld;
		gdxRenderer = new GDXRenderer(true, true);
		stage.addActor(mainWindow = new MainWindow(skin, game, this, gdxWorld, WORLD_FILE, gdxRenderer, sharedAssets));
		panner = new PannerManager(gdxWorld, gdxRenderer);
		for(ISharedAssetConsumer handler : PluginUtil.getPlugins(ISharedAssetConsumer.class))
			handler.setAssets(sharedAssets);
		register(ActionEnum.BACK, new AbstractInputHandler() {
			public void down(){
				exit();
			}
		});
	}

	@Override public void render(float delta){
		super.render(delta);
		SoundThematicHandlerPlugin.get().tick(delta);
		sharedAssets.update();
		update(delta);
		stage.draw();
	}

	@Override public void newCharacterButtonClicked() {
		mainWindow.remove();
		stage.addActor(newCharacterWindow = new NewCharacterWindow(skin, game, this, gdxWorld, WORLD_FILE,
				gdxRenderer, sharedAssets));
	}

	@Override public void backButtonClicked() {
		newCharacterWindow.remove();
		stage.addActor(mainWindow = new MainWindow(skin, game, this, gdxWorld, WORLD_FILE, gdxRenderer, sharedAssets));
	}

	@Override public void optionsButtonClicked() {
		mainWindow.remove();
		stage.addActor(optionsWindow = new OptionsWindow(skin, game, this));
	}

	@Override public void optionsClosed() {
		if(optionsWindow != null)
			optionsWindow.remove();
		optionsWindow = null;
		stage.addActor(mainWindow = new MainWindow(skin, game, this, gdxWorld, WORLD_FILE, gdxRenderer, sharedAssets));
	}
	
	private void exit(){
		SaveHelper.saveProperties();
		Gdx.app.exit();
	}

	public void update(float delta) {
		panner.updatePanners(delta);
	}

	public boolean ready() {
		return panner.ready();
	}

	@Override public void exitButtonClicked() {
		exit();
	}
}
