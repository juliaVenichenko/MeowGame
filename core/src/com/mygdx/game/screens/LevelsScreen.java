package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;

public class LevelsScreen implements Screen {
    private  final MyGdxGame myGdxGame;
    private Texture background;
    private ButtonView level1;
    private ButtonView level2;
    private ButtonView level3;

    public LevelsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

    }

    @Override
    public void show() {

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        background = new Texture(GameResources.BACKGROUND_MENU_IMG_PATH);
        level1 = new ButtonView(275, 200, 70, 70, myGdxGame.commonWhiteFont, GameResources.BUTTON_UNIVERSAL, "1");
        level2 = new ButtonView(375, 200, 70, 70, myGdxGame.commonWhiteFont, GameResources.BUTTON_UNIVERSAL,"2");
        level3 = new ButtonView(475, 200, 70, 70, myGdxGame.commonWhiteFont, GameResources.BUTTON_UNIVERSAL,"3");
    }


    @Override
    public void render(float delta) {
        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(background, 0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
        level1.draw(myGdxGame.batch);
        level2.draw(myGdxGame.batch);
        level3.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (level1.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }

            if ((level1.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) || (level2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) || (level3.isHit(myGdxGame.touch.x, myGdxGame.touch.y))){
                myGdxGame.audioManager.menuMusic.stop();
                myGdxGame.audioManager.gameMusic.play();
                myGdxGame.audioManager.clickSound.play(0.2f);
            }
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        level1.dispose();
        level2.dispose();
        level3.dispose();
        myGdxGame.audioManager.menuMusic.dispose();
        myGdxGame.audioManager.gameMusic.dispose();
        myGdxGame.audioManager.clickSound.dispose();
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
