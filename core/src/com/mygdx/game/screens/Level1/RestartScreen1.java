package com.mygdx.game.screens.Level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.util.AnimationUtil;

public class RestartScreen1 implements Screen {

    private MyGdxGame myGdxGame;
    private Texture background;
    private ButtonView restartButton;
    private ButtonView menuButton;
    private Animation<TextureRegion> cat;
    protected Array<TextureAtlas> textureAtlasArray;
    private float curTime;

    public RestartScreen1(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        curTime = 0;
    }

    @Override
    public void show() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        background = new Texture(GameResources.BACKGROUND_MENU_IMG_PATH);
        restartButton = new ButtonView(125, 150, 240, 110, myGdxGame.commonWhiteFont, GameResources.BUTTON_UNIVERSAL, "Заново");
        menuButton = new ButtonView(475, 150, 240, 110, myGdxGame.commonWhiteFont, GameResources.BUTTON_UNIVERSAL,"Меню");

        initAnimation();
    }

    private void initAnimation() {
        textureAtlasArray = new Array<>();

        TextureAtlas atlas = new TextureAtlas("catsleep.atlas.txt");
        cat = AnimationUtil.getAnimationFromAtlas(
                atlas,
                4f
        );
        textureAtlasArray.add(atlas);

    }

    @Override
    public void render(float delta) {
        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        float deltaTime = Gdx.graphics.getDeltaTime();
        curTime += deltaTime;

        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(background, 0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);
        restartButton.draw(myGdxGame.batch);
        menuButton.draw(myGdxGame.batch);

        myGdxGame.batch.draw(cat.getKeyFrame(curTime, true), 358, 60, 90f,79f); // 90 and 79

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
        background.dispose();
        restartButton.dispose();
        menuButton.dispose();
        myGdxGame.audioManager.gameMusic.dispose();
        myGdxGame.audioManager.menuMusic.dispose();

        for (TextureAtlas atlas : textureAtlasArray) {
            atlas.dispose();
        }
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
