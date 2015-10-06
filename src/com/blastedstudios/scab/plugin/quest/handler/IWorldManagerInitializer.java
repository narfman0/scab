package com.blastedstudios.scab.plugin.quest.handler;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.blastedstudios.scab.world.WorldManager;

@PluginImplementation
public interface IWorldManagerInitializer extends Plugin{
	void setWorldManager(WorldManager world);
}
