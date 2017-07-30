package com.ezardlabs.lostsector.ai;

import com.ezardlabs.dethsquare.Mathf;
import com.ezardlabs.dethsquare.Physics;
import com.ezardlabs.dethsquare.Physics.RaycastHit;
import com.ezardlabs.dethsquare.Transform;
import com.ezardlabs.dethsquare.Vector2;
import com.ezardlabs.lostsector.NavMesh;
import com.ezardlabs.lostsector.NavMesh.NavPoint;

public abstract class Behaviour {
	private float moveSpeed;
	private final boolean willPatrol;
	private final float visionRange;
	private State state = State.IDLE;
	private CombatState combatState = CombatState.IDLE;
	private Transform target = null;
	private NavPoint[] path = null;
	private int pathIndex = 0;
	private NavPoint currentNavPoint = null;
	private NavPoint currentTargetNavPoint = null;

	public enum State {
		IDLE,
		MOVING,
		JUMPING,
		FALLING,
		LANDING,
		FROZEN,
		ATTACKING
	}

	protected enum CombatState {
		IDLE,
		PATROLLING,
		TRACKING,
		SEARCHING,
		ATTACKING
	}

	Behaviour(float moveSpeed, boolean willPatrol, float visionRange) {
		this.moveSpeed = moveSpeed;
		this.willPatrol = willPatrol;
		this.visionRange = visionRange;
	}

	public final void update(Transform transform) {
		Transform sightedEnemy = visionCheck(transform);
		if (sightedEnemy != null) {
			combatState = onEnemySighted(transform, sightedEnemy);
			switch (combatState) {
				case IDLE:
				case PATROLLING:
					target = null;
					break;
				case TRACKING:
				case SEARCHING:
				case ATTACKING:
					target = sightedEnemy;
					break;
			}
		}
		switch (combatState) {
			case IDLE:
				break;
			case PATROLLING:
				// TODO implement patrolling
				break;
			case TRACKING:
				NavPoint self = NavMesh.getNavPoint(transform.position.x, transform.position.y);
				NavPoint targetNavPoint = NavMesh.getNavPoint(target.position);
				if ((!self.equals(currentNavPoint) || !targetNavPoint.equals(currentTargetNavPoint)) && !self.links.isEmpty()) {
					currentNavPoint = self;
					currentTargetNavPoint = targetNavPoint;
					path = NavMesh.getPath(currentNavPoint, currentTargetNavPoint);
				}
				pathIndex = 0;
				break;
			case SEARCHING:
				break;
			case ATTACKING:
				combatState = attack(transform, target);
				break;
		}
		switch (combatState) {
			case IDLE:
				state = State.IDLE;
				break;
			case PATROLLING:
			case TRACKING:
			case SEARCHING:
				state = move(transform);
				break;
			case ATTACKING:
				state = State.ATTACKING;
				break;
		}
	}

	private State move(Transform transform) {
		if (path != null && path.length > 0) {
			if (pathIndex < path.length - 1) {
				float direction = Mathf.clamp(path[pathIndex + 1].position.x - path[pathIndex].position.x, -1, 1);
				transform.translate(moveSpeed * direction, 0);
				transform.scale.x = direction;

				if (transform.gameObject.rigidbody.velocity.y > transform.gameObject.rigidbody.gravity) {
					return State.FALLING;
				} else if (path[pathIndex + 1].position.y < path[pathIndex].position.y) {
					if (state != State.JUMPING) {
						jump(transform, Math.abs(path[pathIndex + 1].position.y - path[pathIndex].position.y));
					}
					return State.JUMPING;
				} else if (direction == 0) {
					return State.IDLE;
				} else {
					return State.MOVING;
				}
			}
		}
		return State.IDLE;
	}

	private void jump(Transform transform, float distance) {
		transform.gameObject.rigidbody.velocity.y = (float) -Math.sqrt(2.5 * distance) - 2;
	}

	public State getState() {
		return state;
	}

	protected abstract CombatState onEnemySighted(Transform self, Transform enemy);

	protected abstract CombatState attack(Transform self, Transform target);

	private Transform visionCheck(Transform transform) {
		RaycastHit hit = Physics.raycast(transform.position.offset(transform.scale.x > 0 ? 200 : 0, 100),
				new Vector2(transform.scale.x, 0), visionRange, "player");
		if (hit == null) {
			return null;
		} else {
			return hit.transform;
		}
	}

	@SuppressWarnings("unchecked")
	protected abstract static class Builder<T extends Builder> {
		float moveSpeed = 10;
		float visionRange = 2000;
		boolean willPatrol = false;

		public T setMoveSpeed(float moveSpeed) {
			this.moveSpeed = moveSpeed;
			return (T) this;
		}

		public T setWillPatrol(boolean willPatrol) {
			this.willPatrol = willPatrol;
			return (T) this;
		}

		public T setVisionRange(float visionRange) {
			this.visionRange = visionRange;
			return (T) this;
		}

		public abstract Behaviour create();
	}
}
