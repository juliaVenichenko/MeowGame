package com.juliaVenichenko.meow.screens.Level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.juliaVenichenko.meow.GameResources;
import com.juliaVenichenko.meow.GameSettings;
import com.juliaVenichenko.meow.MyGdxGame;
import com.juliaVenichenko.meow.components.ImageView;

public class DemoMessage implements Screen {
    private  MyGdxGame myGdxGame;
    private Texture background;
    private ImageView exitButton;
    private ImageView demoMessage;



    public DemoMessage (MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
    }

    @Override
    public void show() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        background = new Texture(GameResources.BACKGROUND_NOT_MEOW_IMG_PATH);

        exitButton = new ImageView(590, 380, GameResources.BUTTON_EXIT);
        demoMessage = new ImageView(150,60,GameResources.DEMO_MESSAGE);
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (exitButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.victoryScreen1);
                myGdxGame.audioManager.gameMusic.stop();
                myGdxGame.audioManager.clickSound.play(0.2f);
            }
        }
    }

    @Override
    public void render(float delta) {
        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(background, 0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
        demoMessage.draw(myGdxGame.batch);
        exitButton.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        exitButton.dispose();
        demoMessage.dispose();
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
