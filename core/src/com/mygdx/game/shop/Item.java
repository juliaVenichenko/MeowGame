package com.mygdx.game.shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Item {
    private float x, y;
    private final Texture texture;
    private Rectangle hitBox;
    private final Price price;

    public Item(Texture texture, Price price) {
        this.texture = texture;
        this.price = price;
    }

    public void setSlot(float x, float y){
        hitBox = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        this.x = x;
        this.y = y;

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public Price getPrice() {
        return price;
    }
}
