package com.mygdx.game.screens.Level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.ImageView;
import com.mygdx.game.screens.VictoryScreen;

public class VictoryScreen1 extends VictoryScreen {
    protected  MyGdxGame myGdxGame;
    private ButtonView resumeButton;
    private ButtonView menuButton;


    public VictoryScreen1(MyGdxGame myGdxGame) {
        super(
                350, 10, 90f,79f, 0
        );
        this.myGdxGame = myGdxGame;
    }

    @Override
    public void show() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        resumeButton = new ButtonView(125, 100, 240, 110, myGdxGame.commonWhiteFont, GameResources.BUTTON_UNIVERSAL_GRAY, "Продолжить");
        menuButton = new ButtonView(475, 100, 240, 110, myGdxGame.commonWhiteFont, GameResources.BUTTON_UNIVERSAL,"Меню");

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
        menuButton.draw(myGdxGame.batch);
        resumeButton.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

//            if (resumeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
//                myGdxGame.setScreen(myGdxGame.gameScreen);
//                myGdxGame.audioManager.gameMusic.play();
//            }

            if (menuButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
                myGdxGame.audioManager.menuMusic.play();
                myGdxGame.audioManager.clickSound.play(0.2f);
            }

        }
    }


    @Override
    public void dispose() {
        menuButton.dispose();
        resumeButton.dispose();

        myGdxGame.audioManager.menuMusic.dispose();
        myGdxGame.audioManager.gameMusic.dispose();

        disposeUI();
    }
}
