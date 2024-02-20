package glorydark.DLevelEventPlus.event;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryOpenEvent;
import cn.nukkit.inventory.Inventory;
import glorydark.DLevelEventPlus.api.LevelSettingsAPI;
import glorydark.DLevelEventPlus.api.PermissionAPI;

/**
 * @author glorydark
 */
public class InventoryEventListener implements Listener {

    @EventHandler
    public void InventoryOpenEvent(InventoryOpenEvent event) {
        Player player = event.getPlayer();
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        Inventory inventory = event.getInventory();
        Boolean bool;
        switch (inventory.getType()) {
            case WORKBENCH:
                bool = LevelSettingsAPI.getLevelBooleanInit(player.getLevel().getName(), "Inventory", "CraftingTable");
                if (bool == null) {
                    return;
                }
                if (!bool) {
                    event.setCancelled(true);
                }
                break;
            case ANVIL:
                bool = LevelSettingsAPI.getLevelBooleanInit(player.getLevel().getName(), "Inventory", "AnvilOpen");
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
