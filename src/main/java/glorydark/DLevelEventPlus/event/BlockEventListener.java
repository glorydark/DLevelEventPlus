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
import glorydark.DLevelEventPlus.protection.NameMapping;
import glorydark.DLevelEventPlus.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class BlockEventListener implements Listener {
    // Block
    // 方块放置
    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_ALLOW_PLACE_BLOCK);
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
            List<String> antiPlaceBlockStrings = new ArrayList<>(LevelSettingsAPI.getLevelStringListSetting(block.getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_BANNED_PLACE_BLOCKS));
            List<String> canPlaceBlockStrings = new ArrayList<>(LevelSettingsAPI.getLevelStringListSetting(block.getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_ALLOWED_PLACE_BLOCKS));
            if (antiPlaceBlockStrings.stream().anyMatch(s -> ItemUtils.isEqual(s, block)) && canPlaceBlockStrings.stream().noneMatch(s -> ItemUtils.isEqual(s, block))) {
                event.setCancelled(true);
            }
        }
    }

    // 方块破坏
    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_ALLOW_BREAK_BLOCK);
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
            List<String> antiBreakBlockStrings = new ArrayList<>(LevelSettingsAPI.getLevelStringListSetting(block.getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_BANNED_BREAK_BLOCKS));
            List<String> canBreakBlockStrings = new ArrayList<>(LevelSettingsAPI.getLevelStringListSetting(block.getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_ALLOWED_BREAK_BLOCKS));
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

    @EventHandler
    public void BlockBurnEvent(BlockBurnEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_BURN);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockIgniteEvent(BlockIgniteEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_IGNITE);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFallEvent(BlockFallEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_FALL);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockGrowEvent(BlockGrowEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_GROW);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockSpreadEvent(BlockSpreadEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_SPREAD);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFormEvent(BlockFormEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_FORM);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void LeavesDecayEvent(LeavesDecayEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_LEAVES_DECAY);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void LiquidFlowEvent(LiquidFlowEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_LIQUID_FLOW);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void RedstoneUpdateEvent(BlockUpdateEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_BLOCK_REDSTONE);
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
    public void ItemFrameDropItemEvent(ItemFrameUseEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_ITEM_FRAME_DROP_ITEM);
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
        if (PermissionAPI.isAdmin(event.getPlayer())) {
            return;
        }
        if (PermissionAPI.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_SIGN_CHANGE);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockUpdateEvent(BlockUpdateEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_UPDATE);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFadeEvent(BlockFadeEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_FADE);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockPistonChangeEvent(BlockPistonEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_PISTON_CHANGE);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFromToEvent(BlockFromToEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_FROM_TO_EVENT);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BellRingEvent(BellRingEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (PermissionAPI.isAdmin(player)) {
                return;
            }
            if (PermissionAPI.isOperator(player, player.getLevel())) {
                return;
            }
        }
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_BELL_RING);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockExplosionEvent(BlockExplosionPrimeEvent event) {
        Boolean allExplode = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_ALL_EXPLODES);
        Boolean blockExplode = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_BLOCK_EXPLODE);
        if (allExplode != null && allExplode) {
            event.setCancelled(true);
            return;
        }
        if (blockExplode == null) {
            return;
        }
        if (!blockExplode) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void LecternDropBookEvent(LecternDropBookEvent event) {
        Player player = event.getPlayer();
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_LECTERN_DROP_BOOK);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void LecternPageChangeEvent(LecternPageChangeEvent event) {
        Player player = event.getPlayer();
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_LECTERN_PAGE_CHANGE);
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
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_SIGN_COLOR_CHANGE);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void WaterFrostEvent(WaterFrostEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_WATER_FROST);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    public boolean isDropItem(Level level, Block block) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_DROP_ITEM);
        if (bool != null && !bool) {
            return LevelSettingsAPI.getLevelStringListSetting(level.getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_DROP_ITEM_BLOCKS).stream().anyMatch(s -> ItemUtils.isEqual(s, block));
        }
        return true;
    }

    public boolean isDropExp(Level level, Block block) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_DROP_EXP);
        if (bool != null && !bool) {
            return LevelSettingsAPI.getLevelStringListSetting(level.getName(), NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_DROP_EXP_BLOCKS).stream().anyMatch(s -> ItemUtils.isEqual(s, block));
        }
        return true;
    }
}
