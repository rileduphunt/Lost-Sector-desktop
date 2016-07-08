package com.ezardlabs.lostsector.levels;

import com.ezardlabs.dethsquare.*;
import com.ezardlabs.lostsector.Game;
import com.ezardlabs.lostsector.MapManager;
import com.ezardlabs.lostsector.SurvivalManager;
import com.ezardlabs.lostsector.objects.CameraMovement;
import com.ezardlabs.lostsector.objects.Player;
import com.ezardlabs.lostsector.objects.hud.HUD;
import com.ezardlabs.lostsector.objects.warframes.Frost;

public class SurvivalLevel extends Level {
	private static CameraMovement cm = new CameraMovement();
	public SurvivalManager survivalManager = null;

	@Override
	public void onLoad() {
		MapManager.loadMap("Tiny_Sur");

		HUD.init();

		createPlayer();

		AudioSource as = new AudioSource();
		as.play(new AudioSource.AudioClip("audio/theme.ogg"));
		survivalManager = new SurvivalManager(MapManager.enemySpawns);
		GameObject.instantiate(new GameObject("Camera", new Camera(true), cm, as), new Vector2());
		GameObject.instantiate(new GameObject("SurvivalManager", survivalManager), new Vector2());

		TextureAtlas fontTA = new TextureAtlas("fonts/atlas.png", "fonts/atlas.txt");
		GameObject.instantiate(
				new GameObject(
						"MainMenuWIP",
						new GuiText("DEV BUILD : WORK IN PROGRESS!", fontTA, 30)
				),
				new Vector2(10, 10)
		);
	}

	public static void createPlayer() {
		Game.players = new GameObject[]{new GameObject("Player", new Player(), new Renderer(),
				new Animator(), new Frost(), new Collider(200, 200), new Rigidbody())};
		GameObject.instantiate(Game.players[0], MapManager.playerSpawn);

		cm.smoothFollow(Game.players[0].transform);
	}
}