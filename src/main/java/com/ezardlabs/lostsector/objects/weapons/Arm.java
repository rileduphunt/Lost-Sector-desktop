package com.ezardlabs.lostsector.objects.weapons;

import com.ezardlabs.dethsquare.animation.AnimationType;
import com.ezardlabs.dethsquare.Script;
import com.ezardlabs.lostsector.objects.warframes.Warframe;

public class Arm extends Script {
	private final Warframe warframe;
	private String animationName;
	private AnimationType animationType;
	private boolean setAnimationType = false;

	public Arm(Warframe warframe) {
		this.warframe = warframe;
	}

	@Override
	public void start() {
		transform.setParent(warframe.transform);
		gameObject.renderer.setDepth(2);
	}

	@Override
	public void update() {
		if (transform.getParent() != null) {
			transform.scale.x = transform.getParent().scale.x;
		}
		if (setAnimationType) {
			//noinspection ConstantConditions
			gameObject.animator.getAnimation(animationName).setAnimationType(animationType);
			setAnimationType = false;
		}
	}

	public void setAnimationType(String animationName, AnimationType animationType) {
		this.animationName = animationName;
		this.animationType = animationType;
		this.setAnimationType = true;
	}
}
