package glorydark.DLevelEventPlus.event;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryOpenEvent;
import cn.nukkit.event.player.CraftingTableOpenEvent;
import cn.nukkit.inventory.AnvilInventory;
import cn.nukkit.inventory.Inventory;
import glorydark.DLevelEventPlus.LevelEventPlusMain;
import glorydark.DLevelEventPlus.utils.ConfigUtil;

/**
 * @author glorydark
 */
public class InventoryEventListener implements Listener {

    @EventHandler
    public void CraftingTableOpenEvent(CraftingTableOpenEvent event) {
        Player player = event.getPlayer();
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(player.getLevel().getName(), "Inventory", "CraftingTableOpen");
        if (bool == null) {
            return;
        }
        if (ConfigUtil.isAdmin(player)) {
            return;
        }
        if (ConfigUtil.isOperator(player, player.getLevel())) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

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
        if (inventory instanceof AnvilInventory) {
            Boolean bool = LevelEventPlusMain.getLevelBooleanInit(player.getLevel().getName(), "Inventory", "AnvilOpen");
            if (bool == null) {
                return;
            }
            if (!bool) {
                event.setCancelled(true);
            }
        }
    }
}