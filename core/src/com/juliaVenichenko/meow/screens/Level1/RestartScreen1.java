package com.juliaVenichenko.meow.screens.Level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.juliaVenichenko.meow.GameResources;
import com.juliaVenichenko.meow.MyGdxGame;
import com.juliaVenichenko.meow.components.ButtonView;
import com.juliaVenichenko.meow.screens.RestartScreen;

public class RestartScreen1 extends RestartScreen {

    private MyGdxGame myGdxGame;
    private ButtonView restartButton;
    private ButtonView menuButton;


    public RestartScreen1(MyGdxGame myGdxGame) {
        super(358, 60, 90f,79f, 0);
        this.myGdxGame = myGdxGame;
    }

    @Override
    public void show() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        restartButton = new ButtonView(125, 150, 240, 110, myGdxGame.commonWhiteFont, GameResources.BUTTON_UNIVERSAL, "Заново");
        menuButton = new ButtonView(475, 150, 240, 110, myGdxGame.commonWhiteFont, GameResources.BUTTON_UNIVERSAL,"Меню");

        initAnimation();
    }


    @Override
    public void render(float delta) {
        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        drawUI(myGdxGame);
        restartButton.draw(myGdxGame.batch);
        menuButton.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (restartButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.gameScreen);
                myGdxGame.audioManager.gameMusic.play();
            }
            if (menuButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
                myGdxGame.audioManager.menuMusic.play();
            }
        }
    }

    @Override
    public void dispose() {
        restartButton.dispose();
        menuButton.dispose();
        myGdxGame.audioManager.gameMusic.dispose();
        myGdxGame.audioManager.menuMusic.dispose();

        disposeUI();
    }
}
