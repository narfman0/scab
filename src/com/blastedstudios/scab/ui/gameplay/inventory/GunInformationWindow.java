package com.blastedstudios.scab.ui.gameplay.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.scab.ui.gameplay.GunTable;
import com.blastedstudios.scab.util.ui.ScabTextButton;
import com.blastedstudios.scab.util.ui.ScabWindow;
import com.blastedstudios.scab.world.weapon.Weapon;

public class GunInformationWindow extends ScabWindow {
	public GunInformationWindow(final Skin skin, final Weapon weapon){
		super("", skin);
		Button closeButton = new ScabTextButton("Close", skin, new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				event.getListenerActor().getParent().getParent().remove();
			}
		});
		add(new Label(weapon.getName(), skin));
		row();
		add(new GunTable(skin, weapon));
		row();
		final Table controls = new Table();
		controls.add(closeButton);
		add(controls);
		pack();
		setX(Gdx.graphics.getWidth()/2f - getWidth()/2f);
		setY(80f);	
	}
}
