package com.blastedstudios.scab.plugin.quest.manifestation.factionchange;

import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;
import com.blastedstudios.scab.world.QuestManifestationExecutor;
import com.blastedstudios.scab.world.being.FactionEnum;

public class FactionChangeManifestation extends AbstractQuestManifestation {
	private static final long serialVersionUID = 1L;
	public static final FactionChangeManifestation DEFAULT = new FactionChangeManifestation("", FactionEnum.FRIEND);
	private String being = "";
	private FactionEnum faction = FactionEnum.FRIEND;
	
	public FactionChangeManifestation(){}
	public FactionChangeManifestation(String being, FactionEnum faction){
		this.being = being;
		this.faction = faction;
	}
	
	@Override public CompletionEnum execute(float dt) {
		return ((QuestManifestationExecutor)executor).factionChange(being, faction);
	}

	@Override public AbstractQuestManifestation clone() {
		return new FactionChangeManifestation(being, faction);
	}

	@Override public String toString() {
		return "[FactionChangeManifestation being:" + being + " faction:" + faction + "]";
	}

	public String getBeing() {
		return being;
	}

	public void setBeing(String being) {
		this.being = being;
	}

	public FactionEnum getFaction() {
		return faction;
	}

	public void setFaction(FactionEnum faction) {
		this.faction = faction;
	}

}
