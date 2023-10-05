package dev.xfj.item;

import dev.xfj.database.Database;

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

    public String getName() {
        return Database.getNormalItems().get(itemId).getName();
    }
}
