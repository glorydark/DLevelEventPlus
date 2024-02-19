package glorydark.DLevelEventPlus.event;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryOpenEvent;
import cn.nukkit.inventory.Inventory;
import glorydark.DLevelEventPlus.LevelEventPlusMain;
import glorydark.DLevelEventPlus.utils.ConfigUtil;

/**
 * @author glorydark
 */
public class InventoryEventListener implements Listener {

    @EventHandler
    public void InventoryOpenEvent(InventoryOpenEvent event) {
        Player player = event.getPlayer();
        if (ConfigUtil.isAdmin(player)) {
            return;
        }
        if (ConfigUtil.isOperator(player, player.getLevel())) {
            return;
        }
        Inventory inventory = event.getInventory();
        Boolean bool;
        switch (inventory.getType()) {
            case WORKBENCH:
                bool = LevelEventPlusMain.getLevelBooleanInit(player.getLevel().getName(), "Inventory", "CraftingTable");
                if (bool == null) {
                    return;
                }
                if (!bool) {
                    event.setCancelled(true);
                }
                break;
            case ANVIL:
                bool = LevelEventPlusMain.getLevelBooleanInit(player.getLevel().getName(), "Inventory", "AnvilOpen");
                if (bool == null) {
                    return;
                }
                if (!bool) {
                    event.setCancelled(true);
                }
                break;
        }
    }
}
