package com.blastedstudios.scab.plugin.console;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.scab.world.DropManager;
import com.blastedstudios.scab.world.weapon.Weapon;
import com.blastedstudios.scab.world.weapon.WeaponFactory;

@PluginImplementation
public class GunDropConsole extends AbstractConsole{
	@Override public String[] getMatches() {
		return new String[]{"gun"};
	}

	@Override public void execute(String[] tokens) {
		if(tokens[1].equalsIgnoreCase("drop")){
			if(tokens.length == 2)
				Log.log("GunDropConsole.execute", "Gun drop probability: " + 
						Properties.getFloat(DropManager.GUN_DROP_PROPERTY, .05f));
			else if(tokens.length == 3){
				try{
					Properties.set(DropManager.GUN_DROP_PROPERTY, "" + Float.parseFloat(tokens[2]));
					Log.log("GunDropConsole.execute", "Gun drop probability set to: " + 
							Properties.getFloat(DropManager.GUN_DROP_PROPERTY));
				}catch(NumberFormatException e){
					String weaponName = tokens[2];
					final Weapon weapon;
					if(weaponName.equalsIgnoreCase("random")){
						int iLevel = WeaponFactory.generateILevel(world.getPlayer().getLevel());
						weapon = WeaponFactory.generateGun(iLevel, world.getPlayer().getLevel());
					}else
						weapon = WeaponFactory.getWeapon(weaponName);
					world.getPlayer().getGuns().add(0, weapon);
					world.getPlayer().setCurrentWeapon(0, world.getWorld(), true);
					Log.log("GunDropConsole.execute", "Generated weapon: " + weapon + " for player");
				}
			}
		}
	}

	@Override public String getHelp() {
		return "Gun drop command displays rate and drops guns. No argument shows current gun drop rate, while 'random' or <gunname>"
				+ " spawns a random weapon or a particular named weapon, respectively. Usage:\n"
				+ "gun drop <gunname>, drop the weapon with name <gunname>, e.g. glock\n"
				+ "gun drop, shows probability to drop a gun";
	}
}
