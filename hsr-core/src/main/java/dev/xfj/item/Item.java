package dev.xfj.item;

import dev.xfj.Image;

public abstract class Item {
    private int itemId;
    private String mainType;
    private String subType;
    private int inventoryDisplayTag;
    private String rarity;
    private String name;
    private String backgroundDescription;
    private String description;
    private Image itemIcon;
    private int stackLimit;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public int getInventoryDisplayTag() {
        return inventoryDisplayTag;
    }

    public void setInventoryDisplayTag(int inventoryDisplayTag) {
        this.inventoryDisplayTag = inventoryDisplayTag;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackgroundDescription() {
        return backgroundDescription;
    }

    public void setBackgroundDescription(String backgroundDescription) {
        this.backgroundDescription = backgroundDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(Image itemIcon) {
        this.itemIcon = itemIcon;
    }

    public int getStackLimit() {
        return stackLimit;
    }

    public void setStackLimit(int stackLimit) {
        this.stackLimit = stackLimit;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", mainType='" + mainType + '\'' +
                ", subType='" + subType + '\'' +
                ", inventoryDisplayTag=" + inventoryDisplayTag +
                ", rarity='" + rarity + '\'' +
                ", name='" + name + '\'' +
                ", backgroundDescription='" + backgroundDescription + '\'' +
                ", description='" + description + '\'' +
                ", itemIcon=" + itemIcon +
                ", stackLimit=" + stackLimit +
                '}';
    }
}
