package com.ezardlabs.lostsector.ai;

import com.ezardlabs.dethsquare.Script;

public abstract class Behaviour extends Script {
	private float moveSpeed;
	private final boolean willPatrol;
	private final float visionRange;
	private State state = State.IDLE;

	protected enum State {
		IDLE,
		PATROLLING,
		SEARCHING,
		TRACKING,
		ATTACKING
	}

	Behaviour(float moveSpeed, boolean willPatrol, float visionRange) {
		this.moveSpeed = moveSpeed;
		this.willPatrol = willPatrol;
		this.visionRange = visionRange;
	}

	@SuppressWarnings("unchecked")
	private abstract static class Builder<T extends Builder> {
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

	public static class MeleeBuilder extends Builder<MeleeBuilder> {
		private float meleeRange;

		public MeleeBuilder setMeleeRange(float meleeRange) {
			this.meleeRange = meleeRange;
			return this;
		}

		@Override
		public MeleeBehaviour create() {
			return new MeleeBehaviour(moveSpeed, willPatrol, visionRange,
					meleeRange);
		}
	}

	public static class RangedBuilder extends Builder<RangedBuilder> {
		private float range = 1500;

		public RangedBuilder setRange(float range) {
			this.range = range;
			return this;
		}

		@Override
		public RangedBehaviour create() {
			return new RangedBehaviour(moveSpeed, willPatrol, visionRange,
					range);
		}
	}

/*	private final float moveSpeed;
	private NavPoint lastTargetNavPoint;
	private ArrayList<NavPoint> path;
	protected Transform target;
	private GameObject demo;

	public Behaviour(float moveSpeed) {
		this.moveSpeed = moveSpeed;
		demo = new GameObject("Demo", new Renderer("images/pink.png", 10, 10));
	}

	@Override
	public void start() {
		GameObject.instantiate(demo, new Vector2());
	}

	public void update() {
		NavPoint temp;
		if (target != null && (temp = NavMesh.getNavPoint(target.position)) != lastTargetNavPoint) {
			if (lastTargetNavPoint != null) {
				if (path != null) {
					path.addAll(Arrays.asList(NavMesh.getPath(lastTargetNavPoint.position, temp.position)));
				} else {
					path = new ArrayList<>(Arrays.asList(NavMesh.getPath(lastTargetNavPoint.position, temp.position)));
				}
			}
			lastTargetNavPoint = temp;
		}
	}

	public Transform getTarget() {
		return target;
	}

	public final void setTarget(Transform target) {
		this.target = target;
		setPath(NavMesh.getPath(transform, target));
	}

	public final void setTarget(Vector2 target) {
		setPath(NavMesh.getPath(transform.position, target));
	}

	private void setPath(NavPoint[] path) {
		this.path = new ArrayList<>(Arrays.asList(path));
	}

	protected final void move() {
		if (path.size() > 0) {
			Vector2 target = path.get(0).position;
			if (target.x == transform.position.x) {
				path.remove(0);
				if (path.size() == 0) {
					return;
				}
				target = path.get(0).position;
			}
			demo.transform.position = target;

			if (target.x < transform.position.x) {
				gameObject.renderer.hFlipped = true;
			} else if (target.x > transform.position.x) {
				gameObject.renderer.hFlipped = false;
			}

			if (target.y < transform.position.y && gameObject.rigidbody.velocity.y >= 0) {
				gameObject.rigidbody.velocity.y = -30f;
			}

//			System.out.println("Before: " + transform.position + " -> " + target);

			if (target.x > transform.position.x) {
				transform.translate(moveSpeed, 0);
				if (target.x <= transform.position.x) {
					path.remove(0);
				}
			} else {
				transform.translate(-moveSpeed, 0);
				if (target.x >= transform.position.x) {
					path.remove(0);
				}
			}

//			System.out.println("After: " + transform.position + " -> " + target);
		}
	}*/
}