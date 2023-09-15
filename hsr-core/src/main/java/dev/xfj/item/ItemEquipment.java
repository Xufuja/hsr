package dev.xfj.item;

import java.util.List;

public class ItemEquipment extends Item {
    private boolean isSellable;
    private List<ItemCount> returnList;

    public boolean isSellable() {
        return isSellable;
    }

    public void setSellable(boolean sellable) {
        isSellable = sellable;
    }

    public List<ItemCount> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<ItemCount> returnList) {
        this.returnList = returnList;
    }
}
