package com.juliaVenichenko.meow.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class OrthographicCameraWithLeftRightState extends OrthographicCamera {
    private boolean leftState;

    public OrthographicCameraWithLeftRightState (){
        this.leftState = true;
    }

    public  void changeState(){
        leftState = !leftState;
    }

    public boolean isLeftState(){
        return leftState;
    }

    public void moveCameraToRight(float delta){
        position.x += delta;
        leftState = false;
    }

    public void moveCameraToLeft(float delta){
        position.x -= delta;
        leftState = true;
    }
}
