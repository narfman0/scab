package com.blastedstudios.scab.util;

import net.xeoh.plugins.base.Plugin;

import com.blastedstudios.scab.ui.gameplay.GameplayScreen;
import com.blastedstudios.scab.world.WorldManager;

public interface IConsoleCommand extends Plugin {
	void initialize(final WorldManager world, final GameplayScreen screen);
	String[] getMatches();
	void execute(String[] tokens);
	String getHelp();
}
