package com.blastedstudios.scab.world.weapon;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.scab.world.being.Being;
import com.blastedstudios.scab.world.weapon.shot.Flame;
import com.blastedstudios.scab.world.weapon.shot.GunShot;

public class Flamethrower extends Gun {
	private static final long serialVersionUID = 1L;

	@Override public String toString(){
		return "[Flamethrower name:" + name + " dmg: " + getDamage() + "]";
	}
	
	@Override public GunShot createGunShot(Being origin, Vector2 dir){
		return new Flame(origin, dir, this);
	}
}
