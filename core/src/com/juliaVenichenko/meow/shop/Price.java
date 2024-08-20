package com.juliaVenichenko.meow.shop;

public class Price {
    private final int gold;
    private final int wood;
    private final int ore;

    public Price(int gold, int ore, int wood) {
        this.gold = gold;
        this.ore = ore;
        this.wood = wood;
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
