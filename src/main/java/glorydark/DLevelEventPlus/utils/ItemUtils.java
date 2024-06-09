package glorydark.DLevelEventPlus.utils;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;

/**
 * @author glorydark
 * @date {2023/10/2} {14:50}
 */
public class ItemUtils {

    /*
        Special thanks to <lt-name> for guiding these changes!
        These two methods can be applied in NukkitX, PowernukkitX and MOT,
        which greatly reduce the workload while trying to make this plugin
        compatible with these server software.
    */
    public static boolean isEqual(String itemString, Item item) {
        if (item == null) {
            return false;
        }
        return itemString.equals(item.getNamespaceId())
                || ("minecraft:" + itemString).equals(item.getNamespaceId())
                || Item.fromString(itemString).equals(item);
    }

    public static boolean isEqual(String itemString, Block block) {
        if (block == null) {
            return false;
        }
        Item item = block.toItem();
        return itemString.equals(item.getNamespaceId())
                || ("minecraft:" + itemString).equals(item.getNamespaceId())
                || Item.fromString(itemString).equals(item);
    }
}
