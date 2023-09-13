package dev.xfj.item;

public class ItemExp extends Item {
    private int expProvide;
    private int coinCost;

    public int getExpProvide() {
        return expProvide;
    }

    public void setExpProvide(int expProvide) {
        this.expProvide = expProvide;
    }

    public int getCoinCost() {
        return coinCost;
    }

    public void setCoinCost(int coinCost) {
        this.coinCost = coinCost;
    }
}
