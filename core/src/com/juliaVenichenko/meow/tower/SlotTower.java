package com.juliaVenichenko.meow.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class SlotTower extends Tower{
    private boolean isFree;
    private boolean isVisible;

    public SlotTower(float x, float y) {
        super(x, y, 100, 100, new Texture("tmp.png"), new Rectangle(x, y, 100, 100));

        isFree = true;
        isVisible = false;
    }

    public boolean isFree() {
        return isFree;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isVisible && isFree){
            batch.draw(texture, x, y, width, height);
        }
    }
}
