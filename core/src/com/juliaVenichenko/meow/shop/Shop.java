package com.juliaVenichenko.meow.shop;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Shop {
    private float x, y;
    private float xNext;
    private final Array<Item> items;
    private boolean isActive;
    private Item curChoice;
    private final BitmapFont font;

    public Shop(float x, float y){
        this.x = x;
        this.y = y;

        isActive = false;

        xNext = x;
        font = new BitmapFont();

        items = new Array<>();
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Array<Item> getItems() {
        return items;
    }

    public boolean isActive() {
        return isActive;
    }

    public Item getCurChoice() {
        return curChoice;
    }

    public void setCurChoice(Item curChoice) {
        this.curChoice = curChoice;
    }

    public void addItem(Item item){
        item.setSlot(xNext, y);
        xNext += item.getHitBox().width;
        items.add(item);
    }

    public void draw(SpriteBatch batch){
        for (Item item : items) {
            batch.draw(item.getTexture(), item.getX(), item.getY());

            //TODO: попробовать реализовать со шрифтом
            font.draw(batch, "Need :" + "\n" +
                    "Gold:" + item.getPrice().getGold() + "\n" +
                    "Ore:" + item.getPrice().getOre() + "\n" +
                    "Wood:" + item.getPrice().getWood(), item.getX(), item.getY() + item.getHitBox().height + 70f);
        }
    }

    public void dispose(){
        font.dispose();
        for (Item item: items) {
            item.getTexture().dispose();
        }
    }
}
