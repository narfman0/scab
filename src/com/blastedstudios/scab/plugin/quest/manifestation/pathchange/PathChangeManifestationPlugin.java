package com.blastedstudios.scab.plugin.quest.manifestation.pathchange;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;
import com.blastedstudios.gdxworld.world.quest.ICloneable;

@PluginImplementation
public class PathChangeManifestationPlugin implements IQuestComponentManifestation{
	@Override public String getBoxText() {
		return "Path Change";
	}

	@Override public ICloneable getDefault() {
		return PathChangeManifestation.DEFAULT;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new PathChangeManifestationTable(skin, (PathChangeManifestation) object);
	}
}
