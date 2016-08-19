package com.blastedstudios.scab;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.world.GDXWorld;
import com.blastedstudios.scab.network.Host;
import com.blastedstudios.scab.ui.gameplay.GameplayNetReceiver;
import com.blastedstudios.scab.ui.levelselect.network.NetworkWindow.MultiplayerType;
import com.blastedstudios.scab.ui.main.MainScreen;
import com.blastedstudios.scab.util.SaveHelper;
import com.blastedstudios.scab.world.WorldManager;

import net.xeoh.plugins.base.util.uri.ClassURI;

public class ScabServer extends ApplicationAdapter{
	private Host host;
	private GDXWorld gdxWorld;
	private WorldManager worldManager;
	private GameplayNetReceiver receiver;
	
	@Override public void create () {
		SaveHelper.loadProperties();
		PluginUtil.initialize(ClassURI.CLASSPATH);
		host = new Host(null);
		gdxWorld = GDXWorld.load(MainScreen.WORLD_FILE);
		worldManager = new WorldManager(null, gdxWorld.getLevels().get(0), null);
		receiver = new GameplayNetReceiver(null, worldManager, MultiplayerType.DedicatedServer, host);
	}
	
	@Override public void render() {
		float dt = Gdx.graphics.getDeltaTime();
		worldManager.update(dt);
		receiver.render(dt);
		try {
			Thread.sleep((long)dt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[] argv) {
		new HeadlessApplication(new ScabServer());
	}
}
