package com.ezardlabs.lostsector;

import com.ezardlabs.dethsquare.Animator;
import com.ezardlabs.dethsquare.Collider;
import com.ezardlabs.dethsquare.GameObject;
import com.ezardlabs.dethsquare.LevelManager;
import com.ezardlabs.dethsquare.Renderer;
import com.ezardlabs.dethsquare.Rigidbody;
import com.ezardlabs.dethsquare.TextureAtlas;
import com.ezardlabs.dethsquare.multiplayer.NetworkAnimator;
import com.ezardlabs.dethsquare.multiplayer.NetworkRenderer;
import com.ezardlabs.dethsquare.multiplayer.NetworkTransform;
import com.ezardlabs.dethsquare.prefabs.PrefabManager;
import com.ezardlabs.dethsquare.util.BaseGame;
import com.ezardlabs.lostsector.levels.ExploreLevel;
import com.ezardlabs.lostsector.levels.MainMenuLevel;
import com.ezardlabs.lostsector.levels.MultiplayerLevel;
import com.ezardlabs.lostsector.levels.MultiplayerLobbyLevel;
import com.ezardlabs.lostsector.levels.ProceduralLevel;
import com.ezardlabs.lostsector.levels.SurvivalLevel;
import com.ezardlabs.lostsector.objects.Player;
import com.ezardlabs.lostsector.objects.enemies.corpus.crewmen.DeraCrewman;
import com.ezardlabs.lostsector.objects.enemies.corpus.crewmen.ProvaCrewman;
import com.ezardlabs.lostsector.objects.enemies.corpus.crewmen.SupraCrewman;
import com.ezardlabs.lostsector.objects.environment.Door;
import com.ezardlabs.lostsector.objects.environment.LaserDoor;
import com.ezardlabs.lostsector.objects.environment.Locker;
import com.ezardlabs.lostsector.objects.hud.HUD;
import com.ezardlabs.lostsector.objects.projectiles.LankaBeam;
import com.ezardlabs.lostsector.objects.warframes.Frost;
import com.ezardlabs.lostsector.objects.warframes.abilities.frost.Freeze;
import com.ezardlabs.lostsector.objects.warframes.abilities.frost.IceWave;

public class Game extends BaseGame {
	public static GameObject[] players;

	public enum DamageType {
		NORMAL,
		SLASH,
		COLD,
		KUBROW
	}

	@Override
	public void create() {
		LevelManager.registerLevel("explore", new ExploreLevel());
		LevelManager.registerLevel("survival", new SurvivalLevel());
		LevelManager.registerLevel("procedural", new ProceduralLevel());
		LevelManager.registerLevel("multiplayer_lobby", new MultiplayerLobbyLevel());
		LevelManager.registerLevel("multiplayer", new MultiplayerLevel());
		LevelManager.registerLevel("main_menu", new MainMenuLevel());

		registerPlayerPrefabs();
		registerWarframeAbilityPrefabs();
		registerProjectilePrefabs();
		registerDoorPrefabs();
		registerLockerPrefabs();
		registerEnemyPrefabs();

		LevelManager.loadLevel("main_menu");
	}

	private void registerPlayerPrefabs() {
		PrefabManager.addPrefab("player",
				() -> new GameObject("Player", "player", new Player(), new HUD(), new Renderer(),
						new Animator(), new Frost(), new Collider(200, 200), new Rigidbody(),
						new NetworkTransform(), new NetworkRenderer(), new NetworkAnimator()),
				() -> new GameObject("Other Player", "player", new Renderer(), new Animator(),
						new Frost(), new Collider(200, 200), new NetworkTransform(),
						new NetworkRenderer(), new NetworkAnimator()));
	}

	private void registerWarframeAbilityPrefabs() {
		PrefabManager.addPrefab("freeze",
				() -> new GameObject("Freeze", new Renderer(), new Animator(),
						new Collider(100, 100, true), new Freeze(),
						new NetworkTransform(), new NetworkAnimator()));
		PrefabManager.addPrefab("ice_wave",
				() -> new GameObject("Ice Wave", new Renderer(),
						new Animator(), new IceWave(), new NetworkAnimator()));
	}

	private void registerProjectilePrefabs() {
		PrefabManager.addPrefab("lanka_beam",
				() -> new GameObject("Lanka Beam", new Renderer("images/blue.png", 0, 0),
						new LankaBeam(500), new NetworkTransform(), new NetworkRenderer()),
				() -> new GameObject("Lanka Beam", new Renderer("images/blue.png", 0, 0),
						new NetworkTransform(), new NetworkRenderer()));
	}

	private void registerDoorPrefabs() {
		PrefabManager.addPrefab("door", () -> new GameObject("Door", true, new Door(
				new TextureAtlas("images/environment/atlas.png", "images/environment/atlas.txt")),
				new Renderer(), new Animator(), new Collider(100, 500, true)));
		PrefabManager.addPrefab("laser_door", () -> new GameObject("Laser Door", true,
				new LaserDoor(new TextureAtlas("images/environment/atlas.png",
						"images/environment/atlas.txt")), new Renderer(), new Animator(),
				new Collider(100, 500, true)));
	}

	private void registerLockerPrefabs() {
		PrefabManager.addPrefab("locker_locked",
				() -> new GameObject("Locker", true, new Renderer(), new Locker(true,
						new TextureAtlas("images/environment/atlas.png",
								"images/environment/atlas.txt"))));
		PrefabManager.addPrefab("locker_unlocked",
				() -> new GameObject("Locker", true, new Renderer(), new Locker(false,
						new TextureAtlas("images/environment/atlas.png",
								"images/environment/atlas.txt")), new Collider(100, 200, true),
						new Animator(), new NetworkAnimator()),
				() -> new GameObject("Locker", true, new Renderer(), new Locker(false,
						new TextureAtlas("images/environment/atlas.png",
								"images/environment/atlas.txt")), new Animator(),
						new NetworkAnimator()));
	}

	private void registerEnemyPrefabs() {
		PrefabManager.addPrefab("dera_crewman",
				() -> new GameObject("Dera Crewman", new Renderer(), new Animator(),
						new Collider(200, 200), new Rigidbody(), new DeraCrewman(),
						new NetworkTransform(), new NetworkRenderer(), new NetworkAnimator()),
				() -> new GameObject("Dera Crewman", new Renderer(), new Animator(),
						new Collider(200, 200), new NetworkTransform(), new NetworkRenderer(),
						new NetworkAnimator()));
		PrefabManager.addPrefab("prova_crewman",
				() -> new GameObject("Prova Crewman", new Renderer(), new Animator(),
						new Collider(200, 200), new Rigidbody(), new ProvaCrewman(),
						new NetworkTransform(), new NetworkRenderer(), new NetworkAnimator()),
				() -> new GameObject("Prova Crewman", new Renderer(), new Animator(),
						new Collider(200, 200), new NetworkTransform(), new NetworkRenderer(),
						new NetworkAnimator()));
		PrefabManager.addPrefab("supra_crewman",
				() -> new GameObject("Supra Crewman", new Renderer(), new Animator(),
						new Collider(200, 200), new Rigidbody(), new SupraCrewman(),
						new NetworkTransform(), new NetworkRenderer(), new NetworkAnimator()),
				() -> new GameObject("Supra Crewman", new Renderer(), new Animator(),
						new Collider(200, 200), new NetworkTransform(), new NetworkRenderer(),
						new NetworkAnimator()));
	}
}