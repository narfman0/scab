package com.blastedstudios.scab.plugin.console;

import com.blastedstudios.scab.ui.gameplay.GameplayScreen;
import com.blastedstudios.scab.util.IConsoleCommand;
import com.blastedstudios.scab.world.WorldManager;

public abstract class AbstractConsole implements IConsoleCommand{
	protected WorldManager world;
	protected GameplayScreen screen;
	
	@Override public void initialize(final WorldManager world, final GameplayScreen screen){
		this.world = world;
		this.screen = screen;
	}
}
