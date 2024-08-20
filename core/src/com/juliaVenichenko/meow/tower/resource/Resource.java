package com.juliaVenichenko.meow.tower.resource;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.juliaVenichenko.meow.tower.Tower;

public class Resource extends Tower {
    private final Rectangle workBox;
    private final Texture textureDebug;
    private ResourceType type;

    public Resource(Texture texture, float x, float y, float width, float height, ResourceType type) {
        super(x, y, width, height, texture, new Rectangle(x, y, width, height));

        this.type = type;

        workBox = new Rectangle(x + width - 20, y, 15f, 15f);
        textureDebug = new Texture("tmp.png");
    }


    public Rectangle getWorkBox() {
        return workBox;
    }

    public boolean contains(float x, float y) {
        return x >= this.x && x <= this.x + width
                && y >= this.y && y <= this.y + height;
    }


    public ResourceType getType() {
        return type;
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, x, y, width, height);

        //debug
//        batch.draw(textureDebug, workBox.x, workBox.y, workBox.width, workBox.height);
    }

}
