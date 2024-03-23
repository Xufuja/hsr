package dev.xfj.item;

public class ItemNormal extends Item {
    private int purposeType;

    public int getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(int purposeType) {
        this.purposeType = purposeType;
    }

    @Override
    public String toString() {
        return super.toString() + "\r\nItemNormal{" +
                "purposeType=" + purposeType +
                '}';
    }
}
