package com.blastedstudios.scab.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.physics.PhysicsHelper;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.scab.world.being.Being;
import com.blastedstudios.scab.world.weapon.AmmoTypeEnum;
import com.blastedstudios.scab.world.weapon.Gun;
import com.blastedstudios.scab.world.weapon.Melee;
import com.blastedstudios.scab.world.weapon.Weapon;
import com.blastedstudios.scab.world.weapon.WeaponFactory;

public class DropManager {
	public static final String GUN_DROP_PROPERTY = "gun.drop.probability";
	private final Random random = new Random();
	private final Map<Body,Weapon> droppedGuns = new HashMap<>();
	private final Map<Body,Integer> droppedCash = new HashMap<>();
	private final Map<Body,AmmoDropStruct> droppedAmmo = new HashMap<>();

	public void render(float dt, boolean paused, Being player, World world,
			Batch batch, GDXRenderer renderer, AssetManager sharedAssets){
		float impulseDistance = Properties.getFloat("drop.impulse.distance", 100f);
		float impulseScale = Properties.getFloat("drop.impulse.scalar", .03f);
		{
		List<Body> gunDropRemoveList = new ArrayList<>();
		for(Entry<Body, Weapon> entry : droppedGuns.entrySet()){
			Body dropBody = entry.getKey();
			float distance = player.getPosition().dst2(dropBody.getPosition());
			String gunPath = "data/textures/weapons/" + entry.getValue().getResource() + ".png";
			try{
				WorldManager.drawTexture(batch, dropBody, gunPath, Properties.getFloat("ragdoll.custom.scale"), sharedAssets);
			}catch(Exception e){
				e.printStackTrace();
			}
			if(distance < Properties.getFloat("drop.pickup.distance", .5f)){
				player.receive(entry.getValue(), world);
				world.destroyBody(dropBody);
				gunDropRemoveList.add(dropBody);
				Log.log("WorldManager.render","Player picked up " + entry.getValue());
			}else if(!paused && distance < impulseDistance){
				Vector2 impulse = player.getPosition().cpy().sub(dropBody.getWorldCenter());
				impulse.nor().scl(impulseScale);
				dropBody.applyLinearImpulse(impulse, dropBody.getWorldCenter(), true);
			}
		}
		for(Body removeBody : gunDropRemoveList)
			droppedGuns.remove(removeBody);
		}{
		List<Body> cashDropRemoveList = new ArrayList<>();
		for(Entry<Body, Integer> entry : droppedCash.entrySet()){
			Body dropBody = entry.getKey();
			float distance = player.getPosition().dst2(dropBody.getPosition());
			WorldManager.drawTexture(batch, dropBody, "data/textures/money.png", 
					Properties.getFloat("gameplay.camera.zoom"), sharedAssets);
			if(distance < Properties.getFloat("drop.pickup.distance", .5f)){
				player.addCash(entry.getValue());
				sharedAssets.get("data/sounds/chaching.mp3", Sound.class).play(Properties.getFloat("sound.volume", 1f));
				world.destroyBody(dropBody);
				cashDropRemoveList.add(dropBody);
			}else if(!paused && distance < impulseDistance){
				Vector2 impulse = player.getPosition().cpy().sub(dropBody.getWorldCenter());
				impulse.nor().scl(impulseScale);
				dropBody.applyLinearImpulse(impulse, dropBody.getWorldCenter(), true);
			}
		}
		for(Body removeBody : cashDropRemoveList)
			droppedCash.remove(removeBody);
		}{
		List<Body> ammoDropRemoveList = new ArrayList<>();
		for(Entry<Body, AmmoDropStruct> entry : droppedAmmo.entrySet()){
			Body dropBody = entry.getKey();
			float distance = player.getPosition().dst2(dropBody.getPosition());
			String ammoPath = "data/textures/ammo/" + entry.getValue().type.textureName + ".png";
			WorldManager.drawTexture(batch, dropBody, ammoPath, Properties.getFloat("gameplay.camera.zoom"), sharedAssets);
			if(distance < Properties.getFloat("drop.pickup.distance", .5f)){
				player.addAmmo(entry.getValue().type, entry.getValue().amount);
				world.destroyBody(dropBody);
				sharedAssets.get("data/sounds/guns/ammoPickup.mp3", Sound.class).play(Properties.getFloat("sound.volume", 1f));
				ammoDropRemoveList.add(dropBody);
			}else if(!paused && distance < impulseDistance){
				Vector2 impulse = player.getPosition().cpy().sub(dropBody.getWorldCenter());
				impulse.nor().scl(impulseScale);
				dropBody.applyLinearImpulse(impulse, dropBody.getWorldCenter(), true);
			}
		}
		for(Body removeBody : ammoDropRemoveList)
			droppedAmmo.remove(removeBody);
		}
	}
	
	public void generateDrop(Being being, World world){
		generateGuns(world, being);
		if(Properties.getBool("drops.ammo.enable", false))
			generateAmmo(world, being);
		if(Properties.getBool("drops.cash.enable", false))
			generateCash(world, being);
	}
	
	private void generateAmmo(World world, Being being) {
		if(being.getEquippedWeapon() != null && !(being.getEquippedWeapon() instanceof Melee) &&
				random.nextFloat() < Properties.getFloat("ammo.drop.probability", .2f)){
			int amount = being.getEquippedWeapon().getRoundsPerClip()/4 + random.nextInt(5);
			AmmoDropStruct struct = new AmmoDropStruct(amount, ((Gun)being.getEquippedWeapon()).getAmmoType());
			droppedAmmo.put(generateDropBody(world, being.getPosition(), struct), struct);
			Log.log("WorldManager.generateAmmo", "Generated " + struct);
		}
	}

	private void generateGuns(World world, Being being){
		if(random.nextFloat() < Properties.getFloat(GUN_DROP_PROPERTY, .1f)){
			Weapon weapon = WeaponFactory.generateGun(WeaponFactory.generateILevel(being.getLevel()), being.getLevel());
			dropGun(world, weapon, being.getPosition());
			Log.log("WorldManager.generateGuns", "Generated " + weapon);
		}
	}
	
	private void generateCash(World world, Being being){
		int amount = 0;
		if(random.nextFloat() < Properties.getFloat("cash.drop.probability", .4f))
			amount = being.getLevel()*(4+random.nextInt(2)) + random.nextInt(15);
		else if(random.nextFloat() < Properties.getFloat("cash.drop.probability") + .5f)
			amount = being.getLevel()*(10+random.nextInt(4)) + random.nextInt(50);
		droppedCash.put(generateDropBody(world, being.getPosition(), amount), amount);
		Log.log("WorldManager.generateCash", "Generated $" + amount);
	}
	
	private Body generateDropBody(World world, Vector2 position, Object userData){
		float radius = Properties.getFloat("drop.pickup.distance", .5f)-.25f;
		Body body = PhysicsHelper.createCircle(world, radius, position, BodyType.DynamicBody, .2f, .5f, 1);
		body.setFixedRotation(true);
		body.setUserData(userData);
		return body;
	}
	
	public void dropGun(World world, Weapon weapon, Vector2 position){
		droppedGuns.put(generateDropBody(world, position, weapon), weapon);
	}
	
	private static class AmmoDropStruct{
		public final int amount;
		public final AmmoTypeEnum type;
		
		public AmmoDropStruct(int amount, AmmoTypeEnum type){
			this.amount = amount;
			this.type = type;
		}
		
		@Override public String toString(){
			return "[AmmoDropStruct amount:" + amount + " type:" + type.prettyName + "]";
		}
	}
}
