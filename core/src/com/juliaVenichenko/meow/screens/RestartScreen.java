package com.juliaVenichenko.meow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.juliaVenichenko.meow.GameResources;
import com.juliaVenichenko.meow.GameSettings;
import com.juliaVenichenko.meow.MyGdxGame;
import com.juliaVenichenko.meow.util.AnimationUtil;

public class RestartScreen implements Screen {

    protected final float catX;
    protected final float catY;
    protected final float catWidth;
    protected final float catHeight;
    protected Texture background;
    private Animation<TextureRegion> cat;
    protected Array<TextureAtlas> textureAtlasArray;
    protected float curTime;

    public RestartScreen(float catX, float catY, float catWidth, float catHeight, float curTime) {
        this.catX = catX;
        this.catY = catY;
        this.catWidth = catWidth;
        this.catHeight = catHeight;
        this.curTime = curTime;

        background = new Texture(GameResources.BACKGROUND_MENU_IMG_PATH);
    }

    protected void initAnimation() {
        textureAtlasArray = new Array<>();

        TextureAtlas atlas = new TextureAtlas("catsleep.atlas.txt");
        cat = AnimationUtil.getAnimationFromAtlas(
                atlas,
                4f
        );
        textureAtlasArray.add(atlas);

    }

    protected void drawUI(MyGdxGame myGdxGame){
        myGdxGame.batch.draw(background, 0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);

        float deltaTime = Gdx.graphics.getDeltaTime();
        curTime += deltaTime;
        myGdxGame.batch.draw(cat.getKeyFrame(curTime, true), catX, catY, catWidth,catHeight);

    }

    protected void disposeUI(){

        background.dispose();

        for (TextureAtlas atlas : textureAtlasArray) {
            atlas.dispose();
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    @Override
    public void dispose() {

    }
}
