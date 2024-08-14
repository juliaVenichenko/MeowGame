package com.mygdx.game;

import static com.mygdx.game.GameSettings.SCR_HEIGHT;
import static com.mygdx.game.GameSettings.SCR_WIDTH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.managers.AudioManager;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.LevelsScreen;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.Level1.RestartScreen1;
import com.mygdx.game.screens.Level1.VictoryScreen1;

public class MyGdxGame extends Game {

	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Vector3 touch;
	public World world;
	public MenuScreen menuScreen;
	public GameScreen gameScreen;
	public LevelsScreen levelsScreen;
	public RestartScreen1 restartScreen1;
	public VictoryScreen1 victoryScreen1;
	public BitmapFont commonWhiteFont;
	public AudioManager audioManager;
	@Override
	public void create() {
		Box2D.init();
		world = new World(new Vector2(0, 0), true);

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		levelsScreen = new LevelsScreen(this);
		restartScreen1 = new RestartScreen1(this);
		victoryScreen1 = new VictoryScreen1(this);
		audioManager = new AudioManager();

		commonWhiteFont = FontBuilder.generate(25, Color.WHITE, GameResources.FONT_PATH);
		audioManager.menuMusic.play();

		setScreen(menuScreen);

	}



	@Override
	public void dispose(){
		super.dispose();
		batch.dispose();

		menuScreen.dispose();
		gameScreen.dispose();
		levelsScreen.dispose();
		restartScreen1.dispose();
		victoryScreen1.dispose();

	}
}
