package com.blastedstudios.scab.ui.gameplay.hud;

import java.util.Iterator;
import java.util.LinkedList;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.scab.world.being.NPC;
import com.blastedstudios.scab.world.being.Player;

@PluginImplementation
public class BossHP extends AbstractHUDElement{
	private final LinkedList<NPC> npcs = new LinkedList<>();
	private HPComponent hpBar;

	@Override public AbstractHUDElement initialize(Skin skin, BitmapFont font, Player player){
		super.initialize(skin, font, player);
		npcs.clear();
		hpBar = new HPComponent(skin);
		return this;
	}

	@Override public void render(SpriteBatch spriteBatch) {
		int i=0;
		for(Iterator<NPC> iter = npcs.iterator(); iter.hasNext();){
			NPC npc = iter.next();
			float percentHP = npc.getHp()/npc.getMaxHp();
			Vector2 position = new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()-64f*(i+1));
			hpBar.render(spriteBatch, percentHP, position);
			if(npc.isDead())
				iter.remove();
			i++;
		}
	}
	
	@Override public void npcAdded(NPC npc){
		if(npc.isBoss())
			npcs.add(npc);
	}
}
