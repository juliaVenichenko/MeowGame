package com.mygdx.game.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;

public class CoreTower extends Tower {

    private final Rectangle storageBox;
    private final Texture textureDebug;


    public CoreTower(float width, float height){
        super(GameSettings.SCR_WIDTH - width * 2, GameSettings.SCR_HEIGHT / 2f - height / 4, width, height, new Texture(GameResources.CORE_TOWER), new Rectangle(GameSettings.SCR_WIDTH - width * 2 + width - 30, GameSettings.SCR_HEIGHT / 2f - height / 4 + height / 5, 25, 25));

        textureDebug = new Texture("tmp.png");
        storageBox = new Rectangle(x - 10, y + height / 6, 25, 25);
    }


    public Rectangle getStorageBox() {
        return storageBox;
    }


    public void draw(SpriteBatch batch){
        batch.draw(texture, x, y, width, height);

        //debug
//        batch.draw(textureDebug, hitBox.x, hitBox.y, hitBox.width, hitBox.height);
//        batch.draw(textureDebug, storageBox.x, storageBox.y, storageBox.width, storageBox.height);
    }
}
