package com.juliaVenichenko.meow.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Tower {
    protected final float x;
    protected final float y;
    protected final float width;
    protected final float height;
    protected final Texture texture;
    protected final Rectangle hitBox;


    public Tower(float x, float y, float width, float height, Texture texture, Rectangle hitBox) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.hitBox = hitBox;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void dispose(){
        if (texture != null) texture.dispose();
    }

    public abstract void draw(SpriteBatch batch);
}
