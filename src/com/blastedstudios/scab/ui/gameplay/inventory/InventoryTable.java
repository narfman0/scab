package com.blastedstudios.scab.ui.gameplay.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.scab.input.ActionEnum;
import com.blastedstudios.scab.ui.gameplay.inventory.GunButton.IButtonClicked;
import com.blastedstudios.scab.ui.gameplay.inventory.GunInformationWindow.IWeaponInfoListener;
import com.blastedstudios.scab.util.ui.UIHelper;
import com.blastedstudios.scab.world.being.Being;
import com.blastedstudios.scab.world.weapon.Weapon;

public class InventoryTable extends Table implements IButtonClicked, IWeaponInfoListener {
	private static final int GUNS_PER_ROW = 4;
	private final Sprite noGun;
	private final Table gunsTable, inventoryTable;
	final List<Weapon> guns, inventory;
	private final AssetManager sharedAssets;
	private final Skin skin;
	private Weapon lastClicked = null;
	private final Being being;
	private final IButtonClicked informationWindowListener;
	
	public InventoryTable(final Skin skin, final Being being, final ChangeListener listener,
			final AssetManager sharedAssets, IButtonClicked informationWindowListener){
		try{
			setColor(UIHelper.getColor(skin, "table-background", "background", "primary"));
		}catch(Exception e){}
		this.sharedAssets = sharedAssets;
		this.skin = skin;
		this.being = being;
		this.informationWindowListener = informationWindowListener;
		noGun = GunButton.createGunSprite(sharedAssets, "nogun");
		gunsTable = new Table();
		inventoryTable = new Table();
		guns = new ArrayList<>(being.getGuns());
		inventory = new ArrayList<>(being.getInventory());
		rebuildGunTables();
		add(new Label("Inventory", skin)).colspan(2);
		row();
		add(new Label("To swap, click a gun in the active column,\nthen shift click a gun in the inventory column", skin)).colspan(2);
		row();
		Table activeGuns = new Table();
		activeGuns.add(new Label("Active", skin));
		activeGuns.row();
		activeGuns.add(gunsTable);
		Table inventoryGuns = new Table();
		inventoryGuns.add(new Label("Inventory", skin));
		inventoryGuns.row();
		inventoryGuns.add(inventoryTable);
		add(activeGuns);
		add(inventoryGuns);
		pack();
	}

	private void rebuildGunTables(){
		addGunTables(skin);
		addInventoryTables(skin);
	}

	private void addGunTables(final Skin skin){
		gunsTable.clear();
		for(int i=0; i<GUNS_PER_ROW; i++){
			if(guns.size() > i)
				gunsTable.add(new GunButton(skin, sharedAssets, guns.get(i), this));
			else
				gunsTable.add(new ImageButton(new SpriteDrawable(noGun)));
			gunsTable.row();
		}
	}

	private void addInventoryTables(final Skin skin){
		inventoryTable.clear();
		for(int y=0; y<GUNS_PER_ROW; y++){
			for(int x=0; x < GUNS_PER_ROW; x++){
				if(inventory.size() > x+y*GUNS_PER_ROW){
					final Weapon gun = inventory.get(x+y*GUNS_PER_ROW);
					inventoryTable.add(new GunButton(skin, sharedAssets, gun, this));
				}else
					inventoryTable.add(new ImageButton(new SpriteDrawable(noGun)));
			}
			inventoryTable.row();
		}
	}
	
	private void swap(Weapon first, Weapon second){
		List<Weapon> firstList = guns.contains(first) ? guns : inventory,
			secondList = guns.contains(second) ? guns : inventory;
		int firstIndex = firstList.indexOf(first),
			secondIndex = secondList.indexOf(second);
		if(firstList == secondList)
			Collections.swap(firstList, firstIndex, secondIndex);
		else{
			firstList.remove(first);
			secondList.remove(second);
			firstList.add(firstIndex, second);
			secondList.add(secondIndex, first);
		}
		Log.log("InventoryWindow.swap", "Weapon swap, first: " + first + " second: " + second);
	}

	@Override public void gunButtonClicked(Weapon weapon) {
		informationWindowListener.gunButtonClicked(weapon);
		if(lastClicked != null && ActionEnum.MODIFIER.isPressed()){
			swap(lastClicked, weapon);
			lastClicked = null;
			rebuildGunTables();
		}else
			lastClicked = weapon;
	}
	
	@Override public void deleteWeapon(Weapon weapon) {
		boolean removed = guns.remove(weapon);
		inventory.remove(weapon);
		if(removed && !inventory.isEmpty()){
			Weapon newWeapon = inventory.get(0); 
			guns.add(newWeapon); 
			inventory.remove(newWeapon);
		}
		rebuildGunTables();
	}

	@Override public void sellWeapon(Weapon weapon) {
		being.addCash((int)(weapon.getCost() * Properties.getFloat("weapon.resell.scalar", .25f)));
		deleteWeapon(weapon);
		accept();
	}
	
	public void accept(){
		List<Weapon> guns = new LinkedList<Weapon>();
		for(Actor actor : gunsTable.getChildren())
			if(actor instanceof GunButton)
				guns.add(((GunButton)actor).gun);
		List<Weapon> inventory = new LinkedList<Weapon>();
		for(Actor actor : inventoryTable.getChildren())
			if(actor instanceof GunButton)
				inventory.add(((GunButton)actor).gun);
		being.setGuns(guns);
		being.setInventory(inventory);
	}

	@Override public void buyWeapon(Weapon weapon) {/*can't buy weapons here*/}
}
