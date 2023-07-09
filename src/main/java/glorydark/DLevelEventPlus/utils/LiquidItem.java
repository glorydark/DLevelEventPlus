package glorydark.DLevelEventPlus.utils;

import cn.nukkit.item.Item;

import java.util.ArrayList;
import java.util.List;

public class LiquidItem {

    public static Boolean isLiquidItem(Item item){
        List<Integer> items = new ArrayList<>();
        items.add(Item.STILL_WATER);
        items.add(Item.WATERLILY);
        items.add(Item.FLOWING_WATER);
        items.add(Item.FLOWING_LAVA);
        items.add(Item.STILL_LAVA);
        items.add(Item.BUCKET);
        return items.contains(item.getId());
    }
}
