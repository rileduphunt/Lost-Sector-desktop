package com.ezardlabs.lostsector.objects.hud;

import com.ezardlabs.dethsquare.GameObject;
import com.ezardlabs.dethsquare.GuiRenderer;
import com.ezardlabs.dethsquare.Renderer;
import com.ezardlabs.dethsquare.Screen;
import com.ezardlabs.dethsquare.TextureAtlas;
import com.ezardlabs.dethsquare.Vector2;

public class StatusIndicator {
	private TextureAtlas ta;
	private GuiRenderer energy;
	private GuiRenderer health;
	private GuiRenderer shields;

	void init(TextureAtlas ta) {
		this.ta = ta;
		GameObject.instantiate(new GameObject("HUD", new GuiRenderer(ta, ta.getSprite("status"), 556.25f, 225)), new Vector2((Screen.width - 556.25f * Screen.scale) / Screen.scale, 12));
		GameObject.instantiate(new GameObject("Energy Indicator", energy = new GuiRenderer(ta, ta.getSprite("energy_100"), 200, 200)), new Vector2((Screen.width - 513f * Screen.scale) / Screen.scale, 25));
		GameObject.instantiate(new GameObject("Health Indicator", health = new GuiRenderer(ta, ta.getSprite("health_100"), 200, 200)), new Vector2((Screen.width - 513f * Screen.scale) / Screen.scale, 25));
		GameObject.instantiate(new GameObject("Shield Indicator", shields = new GuiRenderer(ta, ta.getSprite("shield_2"), 200, 200)), new Vector2((Screen.width - 513f * Screen.scale) / Screen.scale, 25));
	}

	public void update(int health, int energy) {
		setHealth(health);
		setEnergy(energy);
	}

	private void setHealth(int health) {
		this.health.setSprite(ta.getSprite("health_" + health));
	}

	private void setEnergy(int energy) {
		if (energy == 0) {
			this.energy.setSize(0, 0);
		} else {
			this.energy.setSize(200, 200);
			this.energy.setSprite(ta.getSprite("energy_" + energy));
		}
	}

	public void spawnGravestone(Vector2 position) {
		GameObject.instantiate(new GameObject("Tombstone", new Renderer(ta, ta.getSprite("gravestone"), 200, 200).setzIndex(5)), position.offset(0, 25));
	}
}
