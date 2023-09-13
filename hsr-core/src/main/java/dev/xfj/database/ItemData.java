package dev.xfj.database;

import dev.xfj.item.*;
import dev.xfj.jsonschema2pojo.itemconfig.ItemConfigJson;
import dev.xfj.jsonschema2pojo.itemconfigequipment.ItemConfigEquipmentJson;
import dev.xfj.jsonschema2pojo.itemconfigequipment.ReturnItemID;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemData {
    private static Map<String, ItemConfigJson> itemConfig;
    private static Map<String, ItemConfigEquipmentJson> itemConfigEquipment;

    private ItemData() {
    }

    public static void init() {
        try {
            itemConfig = Loader.loadJSON(ItemConfigJson.class);
            itemConfigEquipment = Loader.loadJSON(ItemConfigEquipmentJson.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    protected static Map<Integer, Item> loadNormalItems() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Map<Integer, Item> items = new HashMap<>();

        for (Map.Entry<String, ItemConfigJson> entry : itemConfig.entrySet()) {
            ItemNormal item = createBaseItem(ItemNormal.class, ItemConfigJson.class, entry.getValue());
            item.setPurposeType(entry.getValue().getPurposeType());
            items.put(item.getItemId(), item);
        }

        return items;
    }

    protected static Map<Integer, ItemEquipment> loadEquipmentItems() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Map<Integer, ItemEquipment> items = new HashMap<>();

        for (Map.Entry<String, ItemConfigEquipmentJson> entry : itemConfigEquipment.entrySet()) {
            ItemEquipment item = createBaseItem(ItemEquipment.class, ItemConfigEquipmentJson.class, entry.getValue());
            item.setSellable(entry.getValue().isIsSellable());
            List<ItemReturn> returns = new ArrayList<>();

            for (ReturnItemID itemEntry : entry.getValue().getReturnItemIDList()) {
                ItemReturn itemReturn = new ItemReturn(itemEntry.getItemID(), itemEntry.getItemNum());
                returns.add(itemReturn);
            }

            item.setReturnList(returns);

            items.put(item.getItemId(), item);
        }

        return items;
    }

    protected static Map<Integer, ItemExp> loadExpItems() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Map<Integer, ItemExp> items = new HashMap<>();

        for (String key : EquipmentData.getEquipmentExpItemConfigJson().keySet()) {
            ItemExp item = createBaseItem(ItemExp.class, ItemConfigJson.class, itemConfig.get(key));
            item.setExpProvide(EquipmentData.getEquipmentExpItemConfigJson().get(key).getExpProvide());
            item.setCoinCost(EquipmentData.getEquipmentExpItemConfigJson().get(key).getCoinCost());
            items.put(item.getItemId(), item);
        }

        return items;
    }

    private static <T, U> U createBaseItem(Class<U> returnType, Class<T> clazz, Object itemConfig) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Constructor<U> constructor = returnType.getConstructor();
        Object item = constructor.newInstance();
        //I suppose it works...
        item.getClass().getMethod("setItemId", int.class).invoke(item, clazz.getMethod("getId").invoke(itemConfig));
        item.getClass().getMethod("setMainType", String.class).invoke(item, clazz.getMethod("getItemMainType").invoke(itemConfig));
        item.getClass().getMethod("setSubType", String.class).invoke(item, clazz.getMethod("getItemSubType").invoke(itemConfig));
        item.getClass().getMethod("setInventoryDisplayTag", int.class).invoke(item, clazz.getMethod("getInventoryDisplayTag").invoke(itemConfig));
        item.getClass().getMethod("setRarity", String.class).invoke(item, clazz.getMethod("getRarity").invoke(itemConfig));

        Object name = clazz.getMethod("getItemName").invoke(itemConfig);
        item.getClass().getMethod("setName", String.class).invoke(item, TextMapData.getTranslation((Integer) name.getClass().getMethod("getHash").invoke(name)));

        Object bg = clazz.getMethod("getItemBGDesc").invoke(itemConfig);
        item.getClass().getMethod("setBackgroundDescription", String.class).invoke(item, TextMapData.getTranslation((Integer) bg.getClass().getMethod("getHash").invoke(bg)));

        Object desc = clazz.getMethod("getItemDesc").invoke(itemConfig);
        item.getClass().getMethod("setDescription", String.class).invoke(item, TextMapData.getTranslation((Integer) desc.getClass().getMethod("getHash").invoke(desc)));

        item.getClass().getMethod("setStackLimit", int.class).invoke(item, clazz.getMethod("getPileLimit").invoke(itemConfig));

        return returnType.cast(item);
    }
}
