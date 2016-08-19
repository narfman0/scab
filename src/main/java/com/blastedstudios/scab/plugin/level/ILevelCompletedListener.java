package com.blastedstudios.scab.plugin.level;

import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.scab.world.WorldManager;

import net.xeoh.plugins.base.Plugin;

public interface ILevelCompletedListener extends Plugin{
	 void levelComplete(final boolean success, WorldManager world, GDXLevel level);
}
