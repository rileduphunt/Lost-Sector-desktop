package com.ezardlabs.lostsector.ai;

import com.ezardlabs.dethsquare.Transform;

public class MeleeBehaviour extends Behaviour {
	private final float meleeRange;

	MeleeBehaviour(float moveSpeed, boolean willPatrol, float visionRange, float meleeRange) {
		super(moveSpeed, willPatrol, visionRange);
		this.meleeRange = meleeRange;
	}

	@Override
	protected CombatState onEnemySighted(Transform self, Transform enemy) {
		if (Math.abs(self.position.x - enemy.position.x) <= meleeRange) {
			return CombatState.ATTACKING;
		} else {
			return CombatState.TRACKING;
		}
	}

	@Override
	protected CombatState attack(Transform target) {
		return null;
	}

	public static class Builder extends Behaviour.Builder<Builder> {
		private float meleeRange;

		public Builder setMeleeRange(float meleeRange) {
			this.meleeRange = meleeRange;
			return this;
		}

		@Override
		public MeleeBehaviour create() {
			return new MeleeBehaviour(moveSpeed, willPatrol, visionRange, meleeRange);
		}
	}
}
