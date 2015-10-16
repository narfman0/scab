package com.blastedstudios.scab.ui.main;

import java.util.ArrayList;
import java.util.EnumSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.util.GDXGame;
import com.blastedstudios.gdxworld.util.panner.PannerManager;
import com.blastedstudios.gdxworld.world.GDXWorld;
import com.blastedstudios.scab.network.Messages.NetBeing.FactionEnum;
import com.blastedstudios.scab.ui.levelselect.LevelSelectScreen;
import com.blastedstudios.scab.util.ui.ScabTextButton;
import com.blastedstudios.scab.util.ui.ScabWindow;
import com.blastedstudios.scab.util.ui.UIHelper;
import com.blastedstudios.scab.world.Stats;
import com.blastedstudios.scab.world.being.NPCData;
import com.blastedstudios.scab.world.being.Player;
import com.blastedstudios.scab.world.weapon.Weapon;
import com.blastedstudios.scab.world.weapon.WeaponFactory;

class NewCharacterWindow extends ScabWindow{
	public NewCharacterWindow(final Skin skin, final GDXGame game, 
			final INewCharacterWindowListener listener, final GDXWorld gdxWorld, 
			final FileHandle worldFile, final GDXRenderer gdxRenderer,
			final AssetManager sharedAssets, final PannerManager panner) {
		super("", skin);
		final TextField nameField = new TextField("", skin);
		try{
			nameField.setColor(UIHelper.getColor(skin, "new-name-field", "textfield", "secondary"));
		}catch(Exception e){}
		nameField.setMessageText("<name>");
		nameField.setMaxLength(12);
		final Button createButton = new ScabTextButton("Create", skin, new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				if(nameField.getText().isEmpty())
					return;
				NPCData npcData = NPCData.parse("player");
				Player player = new Player(nameField.getText(), 
						WeaponFactory.getGuns(npcData.get("Weapons")), new ArrayList<Weapon>(), 
						Stats.parseNPCData(npcData),
						0,0,1,0, FactionEnum.FRIEND, EnumSet.of(FactionEnum.FRIEND), 
						npcData.get("Resource"));
				LevelSelectScreen screen = new LevelSelectScreen(game, player, 
						gdxWorld, worldFile, gdxRenderer, sharedAssets, panner);
				game.pushScreen(screen);
			}
		});
		final Button backButton = new ScabTextButton("Back", skin, new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				listener.backButtonClicked();
			}
		});
		add(new Label("New Character", skin));
		row();
		add(nameField).fillX();
		row();
		add(createButton).fillX();
		row();
		add(backButton).fillX();
		setX(Gdx.graphics.getWidth()/2 - getWidth()/2);
		setY(Gdx.graphics.getHeight()/2 - getHeight()/2);
		setMovable(false);
		pack();
	}
	
	interface INewCharacterWindowListener{
		void backButtonClicked();
	}
}