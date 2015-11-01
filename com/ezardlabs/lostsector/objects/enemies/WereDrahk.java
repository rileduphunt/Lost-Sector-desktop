package com.ezardlabs.lostsector.objects.enemies;

import com.ezardlabs.dethsquare.Animation;
import com.ezardlabs.dethsquare.Animation.AnimationListener;
import com.ezardlabs.dethsquare.AnimationType;
import com.ezardlabs.dethsquare.Animator;
import com.ezardlabs.dethsquare.TextureAtlas.Sprite;
import com.ezardlabs.dethsquare.Vector2;
import com.ezardlabs.lostsector.Game;
import com.ezardlabs.lostsector.NavMesh;
import com.ezardlabs.lostsector.objects.warframes.Warframe;

public class WereDrahk extends Enemy {
	Vector2 target;
	private boolean attacking = false;

	public WereDrahk() {
		super("weredrahk", 5);
	}

	@Override
	public void start() {
		super.start();
		gameObject.animator.addAnimations(new Animation("attack",
				new Sprite[]{ta.getSprite("attack0"),
						ta.getSprite("attack1"),
						ta.getSprite("attack2"),
						ta.getSprite("attack3"),
						ta.getSprite("attack4"),
						ta.getSprite("attack5"),
						ta.getSprite("attack6"),
						ta.getSprite("attack7")}, AnimationType.ONE_SHOT, 100,
				new AnimationListener() {
					@Override
					public void onAnimatedStarted(Animator animator) {
					}

					@Override
					public void onFrame(Animator animator, int frameNum) {
						if (frameNum == 1 || frameNum == 4) {
							for (int i = 0; i < Game.players.length; i++) {
								if (Math.abs(transform.position.x -
										Game.players[i].transform.position.x) <= 200) {
									Warframe w = Game.players[i].getComponentOfType(Warframe.class);
									if (w != null) {
										w.removeHealth(10);
										if (w.getHealth() <= 0) {
											gameObject.animator.play("howl");
										}
									}
								}
							}
						}
					}

					@Override
					public void onAnimationFinished(Animator animator) {
						attacking = false;
						gameObject.animator.play("idle");
					}
				}), new Animation("howl", new Sprite[]{ta.getSprite("howl0"),
				ta.getSprite("howl1"),
				ta.getSprite("howl2"),
				ta.getSprite("howl3"),
				ta.getSprite("howl4"),
				ta.getSprite("howl5"),
				ta.getSprite("howl6"),
				ta.getSprite("howl7"),
				ta.getSprite("howl8")}, AnimationType.ONE_SHOT, 100));
		gameObject.renderer.setSize(300, 300);
		gameObject.renderer.setOffsets(0, -100);
	}

	@Override
	public void update() {
		if (dead || frozen || attacking) return;
		int x = 0;
		if (!landing && gameObject.rigidbody.velocity.y == 0) {
			if (Game.players.length > 0 &&
					transform.position.y == Game.players[0].transform.position.y &&
					Math.abs(transform.position.x - Game.players[0].transform.position.x) <=
							(gameObject.renderer.hFlipped ? 100 : 200)) {
				attacking = true;
				gameObject.animator.play("attack");
				if (Game.players[0].transform.position.x < transform.position.x) {
					gameObject.renderer.hFlipped = true;
				} else if (Game.players[0].transform.position.x > transform.position.x) {
					gameObject.renderer.hFlipped = false;
				}
				return;
			} else {
				if ((target == null || transform.position.x == target.x) &&
						Game.players.length > 0) {
					NavMesh.NavPoint[] path = NavMesh
							.getPath(transform, Game.players[0].transform);
					if (path != null && path.length > 0) {
						int pathIndex = 0;
						if (path.length > 1 && path[0] != null &&
								transform.position.x == path[0].position.x) {
							pathIndex = 1;
						}
						target = path[pathIndex].position;
					}
				}
				if (target != null) {
					if (target.x < transform.position.x) {
						gameObject.renderer.hFlipped = true;
					} else if (target.x > transform.position.x) {
						gameObject.renderer.hFlipped = false;
					}
					if (target.y < transform.position.y) {
						if (gameObject.rigidbody.velocity.y >= 0) {
							gameObject.rigidbody.velocity.y = -30f;
						}
					} else {
						if (target.x < transform.position.x) {
							x = -10;
						} else if (target.x > transform.position.x) {
							x = 10;
						}
					}
				}
			}
		}
		transform.translate(x, 0);
		if (landing) {
			gameObject.animator.play("land");
		} else if (gameObject.rigidbody.velocity.y > 0) {
			gameObject.animator.play("fall");
		} else if (x != 0) {
			gameObject.animator.play("run");
		} else {
			gameObject.animator.play("idle");
		}
	}

	@Override
	protected Animation getIdleAnimation() {
		return new Animation("idle", new Sprite[]{ta.getSprite("idle0")}, AnimationType.ONE_SHOT,
				Long.MAX_VALUE);
	}

	@Override
	protected Animation getRunAnimation() {
		return new Animation("run", new Sprite[]{ta.getSprite("run0"),
				ta.getSprite("run1"),
				ta.getSprite("run2"),
				ta.getSprite("run3"),
				ta.getSprite("run4"),
				ta.getSprite("run5")}, AnimationType.LOOP, 100);
	}

	@Override
	protected Animation getLandAnimation() {
		return new Animation("land", new Sprite[]{ta.getSprite("land0")}, AnimationType.ONE_SHOT,
				100);
	}

	@Override
	protected Animation getShootAnimation() {
		return new Animation("shoot", new Sprite[0], AnimationType.ONE_SHOT, 0);
	}

	@Override
	protected Animation getFrozenShatterAnimation() {
		return getDieAnimation("frozen_shatter");
	}

	private Animation getDieAnimation(String name) {
		return new Animation(name, new Sprite[]{ta.getSprite("die0"),
				ta.getSprite("die1"),
				ta.getSprite("die2"),
				ta.getSprite("die3"),
				ta.getSprite("die4"),
				ta.getSprite("die5"),
				ta.getSprite("die6"),
				ta.getSprite("die7"),
				ta.getSprite("die8"),
				ta.getSprite("die9"),
				ta.getSprite("die10"),
				ta.getSprite("die11"),
				ta.getSprite("die12"),
				ta.getSprite("die13"),
				ta.getSprite("die14")}, AnimationType.ONE_SHOT, 100);
	}

	@Override
	protected Animation getDieShootBackAnimation() {
		return getDieAnimation("die_shoot_back");
	}

	@Override
	protected Animation getDieShootFrontAnimation() {
		return getDieAnimation("die_shoot_front");
	}

	@Override
	protected Animation getDieSlashBackAnimation() {
		return getDieAnimation("die_slash_back");
	}

	@Override
	protected Animation getDieSlashFrontAnimation() {
		return getDieAnimation("die_slash_front");
	}

	@Override
	protected Animation getDieKubrowBackAnimation() {
		return getDieAnimation("die_kubrow_front");
	}

	@Override
	protected Animation getDieKubrowFrontAnimation() {
		return getDieAnimation("die_kubrow_front");
	}
}
