package com.blastedstudios.scab.world.being.component.levelupsound;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.scab.plugin.quest.handler.ISharedAssetConsumer;
import com.blastedstudios.scab.world.being.component.AbstractComponent;

@PluginImplementation
public class LevelUpSoundComponent extends AbstractComponent implements ISharedAssetConsumer{
	private AssetManager assets;
	
	@Override public void levelUp(){
		assets.get("data/sounds/levelup.mp3", Sound.class).play(Properties.getFloat("sound.volume", 1f));
	}

	@Override public void setAssets(AssetManager assets) {
		this.assets = assets;
	}
}
