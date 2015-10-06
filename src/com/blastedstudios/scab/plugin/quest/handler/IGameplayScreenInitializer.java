package com.blastedstudios.scab.plugin.quest.handler;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.blastedstudios.scab.ui.gameplay.GameplayScreen;

@PluginImplementation
public interface IGameplayScreenInitializer extends Plugin{
	void setGameplayScreen(GameplayScreen screen);
}
