package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.screens.MenuScreen;

public class MyGdxGame extends Game {

	SpriteBatch batch;
	private MenuScreen menuScreen;
	@Override
	public void create() {
		batch = new SpriteBatch();
		menuScreen = new MenuScreen(this);

		this.setScreen(menuScreen);
	}
	public SpriteBatch getBatch(){
		return batch;
	}

	@Override
	public void dispose(){
		super.dispose();
		batch.dispose();

		menuScreen.dispose();
	}
}
