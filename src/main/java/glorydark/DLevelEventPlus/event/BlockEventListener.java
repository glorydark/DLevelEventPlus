package glorydark.DLevelEventPlus.event;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.*;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import glorydark.DLevelEventPlus.api.LevelSettingsAPI;
import glorydark.DLevelEventPlus.api.PermissionAPI;
import glorydark.DLevelEventPlus.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class BlockEventListener implements Listener {
    //Block
    //方块放置
    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "AllowPlaceBlock");
        if (bool == null) {
            return;
        }
        if (PermissionAPI.isAdmin(event.getPlayer())) {
            return;
        }
        if (PermissionAPI.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }

        if (!bool) {
            event.setCancelled(true);
        } else {
            Block block = event.getBlock();
            List<String> antiPlaceBlockStrings = new ArrayList<>(LevelSettingsAPI.getLevelStringListSetting(block.getLevel().getName(), "Block", "AntiPlaceBlocks"));
            List<String> canPlaceBlockStrings = new ArrayList<>(LevelSettingsAPI.getLevelStringListSetting(block.getLevel().getName(), "Block", "CanPlaceBlocks"));
            if (antiPlaceBlockStrings.stream().anyMatch(s -> ItemUtils.isEqual(s, block)) && canPlaceBlockStrings.stream().noneMatch(s -> ItemUtils.isEqual(s, block))) {
                event.setCancelled(true);
            }
        }
    }

    //方块破坏
    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "AllowBreakBlock");
        if (bool == null) {
            return;
        }
        if (PermissionAPI.isAdmin(event.getPlayer())) {
            return;
        }
        if (PermissionAPI.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        } else {
            Block block = event.getBlock();
            List<String> antiBreakBlockStrings = new ArrayList<>(LevelSettingsAPI.getLevelStringListSetting(block.getLevel().getName(), "Block", "AntiBreakBlocks"));
            List<String> canBreakBlockStrings = new ArrayList<>(LevelSettingsAPI.getLevelStringListSetting(block.getLevel().getName(), "Block", "CanBreakBlocks"));
            if (antiBreakBlockStrings.stream().anyMatch(s -> ItemUtils.isEqual(s, block)) && canBreakBlockStrings.stream().noneMatch(s -> ItemUtils.isEqual(s, block))) {
                event.setCancelled(true);
            } else {
                if (!isDropItem(event.getPlayer().getLevel(), block)) {
                    event.setDrops(new Item[0]);
                }
                if (!isDropExp(event.getPlayer().getLevel(), block)) {
                    event.setDropExp(0);
                }
            }
        }
    }

    public boolean isDropItem(Level level, Block block) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), "Block", "DropItem");
        if (bool != null && !bool) {
            return LevelSettingsAPI.getLevelStringListSetting(level.getName(), "Block", "DropItemBlocks").stream().anyMatch(s -> ItemUtils.isEqual(s, block));
        }
        return true;
    }

    public boolean isDropExp(Level level, Block block) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), "Block", "DropExp");
        if (bool != null && !bool) {
            return LevelSettingsAPI.getLevelStringListSetting(level.getName(), "Block", "DropExpBlocks").stream().anyMatch(s -> ItemUtils.isEqual(s, block));
        }
        return true;
    }

    @EventHandler
    public void BlockBurnEvent(BlockBurnEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "Burn");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockIgniteEvent(BlockIgniteEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "Ignite");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFallEvent(BlockFallEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "Fall");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockGrowEvent(BlockGrowEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "Grow");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockSpreadEvent(BlockSpreadEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "Spread");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFormEvent(BlockFormEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "Form");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void LeavesDecayEvent(LeavesDecayEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "LeavesDecay");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void LiquidFlowEvent(LiquidFlowEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "LiquidFlow");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void RedstoneUpdateEvent(BlockUpdateEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "BlockRedstone");
        if (bool == null) {
            return;
        }
        if (!bool) {
            if (event.getBlock().isPowerSource()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void ItemFrameDropItemEvent(ItemFrameDropItemEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "ItemFrameDropItem");
        if (bool == null) {
            return;
        }
        if (PermissionAPI.isAdmin(event.getPlayer())) {
            return;
        }
        if (PermissionAPI.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Level level = event.getPlayer().getLevel();
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void SignChangeEvent(SignChangeEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "SignChange");
        if (bool == null) {
            return;
        }
        if (PermissionAPI.isAdmin(event.getPlayer())) {
            return;
        }
        if (PermissionAPI.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Level level = event.getPlayer().getLevel();
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockUpdateEvent(BlockUpdateEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "Update");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFadeEvent(BlockFadeEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "Fade");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockPistonChangeEvent(BlockPistonChangeEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "PistonChange");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFromToEvent(BlockFromToEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "FromToEvent");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void SignColorChangeEvent(SignColorChangeEvent event) {
        Player player = event.getPlayer();
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Block", "SignColorChange");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }
}
