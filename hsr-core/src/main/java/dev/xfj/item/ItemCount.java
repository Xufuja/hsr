package dev.xfj.item;

public class ItemCount {
    private int itemId;
    private int ItemNumber;

    public ItemCount(int itemId, int itemNumber) {
        this.itemId = itemId;
        this.ItemNumber = itemNumber;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemNumber() {
        return ItemNumber;
    }

    public void setItemNumber(int itemNumber) {
        ItemNumber = itemNumber;
    }
}
