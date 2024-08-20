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
import com.juliaVenichenko.meow.components.ImageView;
import com.juliaVenichenko.meow.util.AnimationUtil;

public abstract class VictoryScreen implements Screen {
    protected final float catX;
    protected final float catY;
    protected final float catWidth;
    protected final float catHeight;
    protected Texture background;
    protected ImageView font;
    private Animation<TextureRegion> cat;
    protected Array<TextureAtlas> textureAtlasArray;
    protected float curTime;


    public VictoryScreen(float catX, float catY, float catWidth, float catHeight, float curTime) {
        this.catX = catX;
        this.catY = catY;
        this.catWidth = catWidth;
        this.catHeight = catHeight;
        this.curTime = curTime;

        background = new Texture(GameResources.BACKGROUND_NOT_MEOW_IMG_PATH);
        font = new ImageView(170, 150, GameResources.VICTORY_IMG_PATH);
    }

    protected void initAnimation() {
        textureAtlasArray = new Array<>();

        TextureAtlas atlas = new TextureAtlas("catcarrysupersmall.atlas.txt");
        cat = AnimationUtil.getAnimationFromAtlas(
                atlas,
                0.75f
        );
        textureAtlasArray.add(atlas);

    }

    protected void drawUI(MyGdxGame myGdxGame){
        myGdxGame.batch.draw(background, 0, 0, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);

        float deltaTime = Gdx.graphics.getDeltaTime();
        curTime += deltaTime;
        myGdxGame.batch.draw(cat.getKeyFrame(curTime, true), catX, catY, catWidth,catHeight);

        font.draw(myGdxGame.batch);
    }

    protected void disposeUI(){

        background.dispose();
        font.dispose();

        for (TextureAtlas atlas : textureAtlasArray) {
            atlas.dispose();
        }
    }

    @Override
    public void dispose() {

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
}
