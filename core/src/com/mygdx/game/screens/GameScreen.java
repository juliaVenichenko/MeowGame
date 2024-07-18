package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.camera.OrthographicCameraWithLeftRightState;
import com.mygdx.game.components.GameUserInterface;

public class GameScreen implements Screen {

    private  final MyGdxGame myGdxGame;
    private OrthographicCameraWithLeftRightState camera;

    private Texture background;
    private GameUserInterface gameUserInterface;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
    }

    @Override
    public void show() {

        camera = new OrthographicCameraWithLeftRightState();
        camera.setToOrtho(false, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
        background = new Texture(GameResources.BACKGROUND_GAME_IMG_PATH);
        gameUserInterface = new GameUserInterface(camera);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        camera.update();
        myGdxGame.batch.setProjectionMatrix(camera.combined);

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(background, 0, 0, GameSettings.WORLD_WIDTH, GameSettings.WORLD_HEIGHT);

        myGdxGame.batch.end();

        gameUserInterface.drawUI();
    }


    @Override
    public void dispose() {
        background.dispose();
        gameUserInterface.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
