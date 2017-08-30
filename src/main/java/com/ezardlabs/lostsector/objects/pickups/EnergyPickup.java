package com.ezardlabs.lostsector.objects.pickups;

import com.ezardlabs.dethsquare.Collider;
import com.ezardlabs.dethsquare.multiplayer.Network;
import com.ezardlabs.lostsector.objects.warframes.Warframe;

public class EnergyPickup extends Pickup {
	private static final int ENERGY = 2;

	@Override
	protected String getAtlasPath() {
		return "images/pickups/energy";
	}

	@Override
	protected String getAnimationPath() {
		return "animations/pickups/energy";
	}

	@Override
	public void onTriggerEnter(Collider other) {
		Warframe w = other.gameObject.getComponent(Warframe.class);
		if (w != null && w.addEnergy(ENERGY)) {
			Network.destroy(gameObject);
		}
	}
}