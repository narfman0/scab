package com.blastedstudios.scab.plugin.ragdoll.garbageman;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.scab.physics.ragdoll.IRagdoll;
import com.blastedstudios.scab.physics.ragdoll.IRagdoll.IRagdollPlugin;
import com.blastedstudios.scab.world.being.Being;

@PluginImplementation
public class RagdollGarbagemanPlugin implements IRagdollPlugin{
	@Override public boolean canCreate(String resource) {
		return resource != null && resource.equalsIgnoreCase("garbageman.xml");
	}

	@Override public IRagdoll create(World world, float x, float y, Being being, TextureAtlas atlas, FileHandle file) {
		return new RagdollGarbageman(world, x, y, being, atlas, file);
	}
}
