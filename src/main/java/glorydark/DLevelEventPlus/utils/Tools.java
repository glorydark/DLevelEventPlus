package glorydark.DLevelEventPlus.utils;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;

/**
 * @author glorydark
 * @date {2023/10/2} {14:50}
 */
public class Tools {

    public static String getItemString(Item item) {
        return item.getId()+":"+item.getDamage();
    }

    public static String getItemString(Block block) {
        return block.getId()+":"+block.getDamage();
    }
}
