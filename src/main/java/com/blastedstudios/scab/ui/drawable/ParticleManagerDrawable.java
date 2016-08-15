package com.blastedstudios.scab.ui.drawable;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.drawable.Drawable;
import com.blastedstudios.scab.ui.gameplay.particles.ParticleManager;
import com.blastedstudios.scab.world.WorldManager;

public class ParticleManagerDrawable extends Drawable {
	private final ParticleManager particleManager;
	private final WorldManager worldManager;
	
	public ParticleManagerDrawable(ParticleManager particleManager, WorldManager worldManager){
		this.particleManager = particleManager;
		this.worldManager = worldManager;
	}

	@Override public void render(float dt, AssetManager assetManager, Batch batch, Camera camera, GDXRenderer renderer) {
		particleManager.render(dt, camera, worldManager, batch);
	}
}