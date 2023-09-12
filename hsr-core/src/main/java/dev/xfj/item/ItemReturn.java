package dev.xfj.item;

public class ItemReturn {
    private int itemId;
    private int ItemNumber;

    public ItemReturn(int itemId, int itemNumber) {
        this.itemId = itemId;
        ItemNumber = itemNumber;
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
