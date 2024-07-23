package com.mygdx.game.shop;

public class Price {
    private final int gold;
    private final int wood;
    private final int ore;

    public Price(int gold, int wood, int ore) {
        this.gold = gold;
        this.wood = wood;
        this.ore = ore;
    }

    public int getGold() {
        return gold;
    }

    public int getWood() {
        return wood;
    }

    public int getOre() {
        return ore;
    }
}
