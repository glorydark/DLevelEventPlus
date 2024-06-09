package glorydark.DLevelEventPlus.utils;

import cn.nukkit.item.Item;

import java.util.ArrayList;
import java.util.List;

public class LiquidItem {

    public static List<Integer> items = new ArrayList<>();

    static {
        items.add(Item.WATERLILY);
        items.add(Item.STILL_WATER);
        items.add(Item.WATER);
        items.add(Item.STILL_LAVA);
        items.add(Item.LAVA);
        items.add(Item.BUCKET);
    }

    public static Boolean isLiquidItem(Item item) {
        return items.contains(item.getId());
    }
}
