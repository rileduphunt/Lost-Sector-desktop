package com.ezardlabs.lostsector.objects.weapons;

import com.ezardlabs.dethsquare.Animation;
import com.ezardlabs.dethsquare.Collider;
import com.ezardlabs.dethsquare.GameObject;
import com.ezardlabs.dethsquare.TextureAtlas;
import com.ezardlabs.lostsector.Game;
import com.ezardlabs.lostsector.objects.enemies.Enemy;

public abstract class MeleeWeapon extends Weapon {
	private final Game.DamageType damageType;
	private final GameObject wielder;
	protected String currentAnimation = null;

	public MeleeWeapon(String name, Game.DamageType damageType, GameObject wielder) {
		super(name);
		this.damageType = damageType;
		this.wielder = wielder;
	}

	public abstract Animation[] getAnimations(TextureAtlas ta);

	public abstract String getNextAnimation(int direction);

	public final void reset() {
		currentAnimation = null;
	}

	protected void damageEnemies(int damage, float left, float top, float right, float bottom) {
		Collider c;
		for (GameObject go : GameObject.findAllWithTag("enemy")) {
			if ((c = go.getComponent(Collider.class)) != null && c.bounds.intersects(left, top, right, bottom)) {
				//noinspection ConstantConditions
				go.getComponentOfType(Enemy.class).applyDamage(damage, damageType, wielder.transform.position);
			}
		}
	}
}