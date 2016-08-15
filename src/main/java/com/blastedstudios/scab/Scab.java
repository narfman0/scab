package com.blastedstudios.scab;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.blastedstudios.gdxworld.GDXWorldEditor;
import com.blastedstudios.gdxworld.util.GDXGame;
import com.blastedstudios.scab.ui.loading.MainLoadingScreen;
import com.blastedstudios.scab.util.SaveHelper;

public class Scab extends GDXGame {
	@Override public void create () {
		pushScreen(new MainLoadingScreen(this));
	}
	
	public static void main (String[] argv) {
		SaveHelper.loadProperties();
		new LwjglApplication(new Scab(), GDXWorldEditor.generateConfiguration("Scab"));
	}
}
