package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.ImageView;
import com.mygdx.game.components.TextView;
import com.mygdx.game.managers.AudioManager;
import com.mygdx.game.util.AnimationUtil;

public class MenuScreen implements Screen {

    private  final MyGdxGame myGdxGame;
    private Texture background;
    private ImageView startButton;
    private Animation<TextureRegion> cat, enemy;
    protected Array<TextureAtlas> textureAtlasArray;
    private float curTime;


    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        background = new Texture(GameResources.BACKGROUND_MENU_IMG_PATH);
        startButton = new ImageView(255, 40, GameResources.BUTTON_START_IMG_PATH);
        initAnimation();
        curTime = 0;
    }

    @Override
    public void show() {

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

    }

    public void initAnimation() {
        textureAtlasArray = new Array<>();

        TextureAtlas atlas = new TextureAtlas("enemyatack.atlas.txt");
        enemy = AnimationUtil.getAnimationFromAtlas(
                atlas,
                4f
        );
        textureAtlasArray.add(atlas);

        atlas = new TextureAtlas("catflysupersmall.atlas.txt");
        cat = AnimationUtil.getAnimationFromAtlas(
                atlas,
                4f
        );
        textureAtlasArray.add(atlas);

    }

    @Override
    public void render(float delta) {

        handleInput();

        ScreenUtils.clear(Color.CLEAR);

        float dTime = Gdx.graphics.getDeltaTime(); //Получаем delta
        curTime += dTime; //Теперь знаем текущее время игры и можем передавать его для прорисовки спрайтов анимации (фреймов)

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(background, 0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);

        myGdxGame.batch.draw(cat.getKeyFrame(curTime, true), 100, 100, 80f, 200f);
        myGdxGame.batch.draw(enemy.getKeyFrame(curTime, true), GameSettings.SCR_WIDTH - 220, 100, 120, 120);

        startButton.draw(myGdxGame.batch);


        myGdxGame.batch.end();


    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (startButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.levelsScreen);
                myGdxGame.audioManager.clickSound.play(0.2f);

            }
//            if (exitButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
//                Gdx.app.exit();
//            }
        }
    }

    @Override
    public void dispose() {

        background.dispose();
        startButton.dispose();
        myGdxGame.audioManager.clickSound.dispose();

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
