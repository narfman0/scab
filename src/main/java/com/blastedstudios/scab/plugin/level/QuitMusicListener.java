package com.blastedstudios.scab.plugin.level;

import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.scab.plugin.quest.handler.manifestation.SoundThematicHandlerPlugin;
import com.blastedstudios.scab.world.WorldManager;

import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class QuitMusicListener implements ILevelCompletedListener{
	@Override public void levelComplete(boolean success, String nextLevelName, WorldManager world, GDXLevel level) {
		if(!success)
			SoundThematicHandlerPlugin.get().applyMusic(SoundThematicHandlerPlugin.getMainMusic());
	}
}
