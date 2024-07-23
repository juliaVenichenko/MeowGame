package com.mygdx.game;

import static com.mygdx.game.GameSettings.SCR_HEIGHT;
import static com.mygdx.game.GameSettings.SCR_WIDTH;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;

public class MyGdxGame extends Game {

	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Vector3 touch;
	public World world;
	private MenuScreen menuScreen;
	public GameScreen gameScreen;
	public BitmapFont commonWhiteFont;
	@Override
	public void create() {
		Box2D.init();
		world = new World(new Vector2(0, 0), true);

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);

		commonWhiteFont = FontBuilder.generate(25, Color.WHITE, GameResources.FONT_PATH);

		setScreen(menuScreen);
	}

	public SpriteBatch getSpriteBatch() {
		return batch;
	}


	@Override
	public void dispose(){
		super.dispose();
		batch.dispose();

		menuScreen.dispose();
		gameScreen.dispose();
	}
}
