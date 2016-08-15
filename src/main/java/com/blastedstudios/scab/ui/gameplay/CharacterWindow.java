package com.blastedstudios.scab.ui.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.scab.util.ui.ScabWindow;
import com.blastedstudios.scab.world.being.Being;

public class CharacterWindow extends ScabWindow {
	public CharacterWindow(final Skin skin, Being being){
		super("", skin);
		add(being.getName());
		row();
		add("Attack: " + being.getAttack());
		row();
		add("Defense: " + being.getDefense());
		row();
		add("Cash: " + being.getCash());
		row();
		add("Level: " + being.getLevel());
		row();
		add("XP: " + being.getXp() + "/" + Being.xpToLevel(being.getLevel()+1));
		row();
		add("HP: " + (int)being.getHp() + "/" + (int)being.getMaxHp());
		pack();
		setY(Gdx.graphics.getHeight()/2 - getHeight()/2);
		setMovable(false);
	}
}
