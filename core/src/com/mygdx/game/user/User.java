package com.mygdx.game.user;

import com.mygdx.game.shop.Item;
import com.mygdx.game.shop.Price;

public class User {
    public int gold;
    public int wood;
    public int ore;
    public int hp = 100;
    private static User instance;


    private User(){}

    public static User getInstance(){
        if (instance == null){
            instance = new User();
        }
        return instance;
    }

    public String fullInfo(){
        return "Hp : " + hp + "\n" +
                "Ore : " + ore + "\n" +
                "Gold : " + gold + "\n" +
                "Wood : " + wood + "\n";
    }

    public void getDmg(int dmg){
        User.getInstance().hp -= dmg;
    }


    public void incOre(int ore){
        User.getInstance().ore += ore;
    }

    public void incGold(int gold){
        User.getInstance().gold += gold;
    }

    public void incWood(int wood){
        User.getInstance().wood += wood;
    }

    public boolean canBuy(Price price){
        return price.getWood() <= wood && price.getGold() <= gold && price.getOre() <= ore;
    }

    public void buyItem(Item curChoice){
        Price price = curChoice.getPrice();
        gold -= price.getGold();
        ore -= price.getOre();
        wood -= price.getWood();
    }
}
