package com.juliaVenichenko.meow.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.juliaVenichenko.meow.GameResources;
import com.juliaVenichenko.meow.GameSettings;
import com.juliaVenichenko.meow.camera.OrthographicCameraWithLeftRightState;

public class GameUserInterface {
    ImageButton buttonRight;
    ImageButton buttonLeft;
    private final OrthographicCameraWithLeftRightState camera;
    private final Stage stage;

    public GameUserInterface(OrthographicCameraWithLeftRightState camera){
        this.camera = camera;

        Drawable button_to_left = new TextureRegionDrawable(new Texture(GameResources.BUTTON_LEFT_IMG_PATH));
        Drawable button_to_right = new TextureRegionDrawable(new Texture(GameResources.BUTTON_RIGHT_IMG_PATH));

        buttonRight = new ImageButton(button_to_right);
        buttonLeft = new ImageButton(button_to_left);

        buttonRight.setPosition(GameSettings.SCR_WIDTH - buttonRight.getWidth() - 24, GameSettings.SCR_HEIGHT- buttonRight.getHeight() - 50);
        buttonLeft.setPosition(GameSettings.SCR_WIDTH + 24, GameSettings.SCR_HEIGHT - buttonLeft.getHeight() - 50);

        buttonRight.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (camera.position.x < GameSettings.WORLD_WIDTH - GameSettings.SCR_WIDTH / 2f);
                camera.moveCameraToRight(GameSettings.SCR_WIDTH);

            }
        });
        buttonLeft.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (camera.position.x > GameSettings.SCR_WIDTH / 2f);
                camera.moveCameraToLeft(GameSettings.SCR_WIDTH);
            }
        });

        Viewport viewport = new StretchViewport(GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        stage.addActor(buttonRight);
        stage.addActor(buttonLeft);
    }

    public void drawUI(){
        stage.act();
        stage.draw();
    }

    public void dispose(){
        stage.dispose();
    }
}
