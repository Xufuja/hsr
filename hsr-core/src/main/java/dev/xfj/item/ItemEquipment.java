package dev.xfj.item;

import java.util.List;

public class ItemEquipment extends Item {
    private boolean isSellable;
    private List<ItemReturn> returnList;

    public boolean isSellable() {
        return isSellable;
    }

    public void setSellable(boolean sellable) {
        isSellable = sellable;
    }

    public List<ItemReturn> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<ItemReturn> returnList) {
        this.returnList = returnList;
    }
}
