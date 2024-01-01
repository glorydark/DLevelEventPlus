package glorydark.DLevelEventPlus.event;

import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.*;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import glorydark.DLevelEventPlus.LevelEventPlusMain;
import glorydark.DLevelEventPlus.utils.ConfigUtil;
import glorydark.DLevelEventPlus.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class BlockEventListener implements Listener {
    //Block
    //方块放置
    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "AllowPlaceBlock");
        if (bool == null) {
            return;
        }
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }

        if (!bool) {
            if (LevelEventPlusMain.show_actionbar_text) {
                event.getPlayer().sendActionBar(LevelEventPlusMain.language.translateString("tip_placeBlock"));
            }
            event.setCancelled(true);
        } else {
            Block block = event.getBlock();
            List<String> antiPlaceBlockStrings = new ArrayList<>(LevelEventPlusMain.getLevelStringListInit(block.getLevel().getName(), "Block", "AntiPlaceBlocks"));
            List<String> canPlaceBlockStrings = new ArrayList<>(LevelEventPlusMain.getLevelStringListInit(block.getLevel().getName(), "Block", "CanPlaceBlocks"));
            if (antiPlaceBlockStrings.stream().anyMatch(s -> ItemUtils.isEqual(s, block)) && canPlaceBlockStrings.stream().noneMatch(s -> ItemUtils.isEqual(s, block))) {
                if (LevelEventPlusMain.show_actionbar_text) {
                    event.getPlayer().sendActionBar(LevelEventPlusMain.language.translateString("tip_placeSpecificBlock"));
                }
                event.setCancelled(true);
            }
        }
    }

    //方块破坏
    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "AllowBreakBlock");
        if (bool == null) {
            return;
        }
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        if (!bool) {
            if (LevelEventPlusMain.show_actionbar_text) {
                event.getPlayer().sendActionBar(LevelEventPlusMain.language.translateString("tip_breakBlock"));
            }
            event.setCancelled(true);
        } else {
            Block block = event.getBlock();
            List<String> antiBreakBlockStrings = new ArrayList<>(LevelEventPlusMain.getLevelStringListInit(block.getLevel().getName(), "Block", "AntiBreakBlocks"));
            List<String> canBreakBlockStrings = new ArrayList<>(LevelEventPlusMain.getLevelStringListInit(block.getLevel().getName(), "Block", "CanBreakBlocks"));
            if (antiBreakBlockStrings.stream().anyMatch(s -> ItemUtils.isEqual(s, block)) && canBreakBlockStrings.stream().noneMatch(s -> ItemUtils.isEqual(s, block))) {
                if (LevelEventPlusMain.show_actionbar_text) {
                    event.getPlayer().sendActionBar(LevelEventPlusMain.language.translateString("tip_breakSpecificBlock"));
                }
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
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(level.getName(), "Block", "DropItem");
        if (bool != null && !bool) {
            return LevelEventPlusMain.getLevelStringListInit(level.getName(), "Block", "DropItemBlocks").stream().anyMatch(s -> ItemUtils.isEqual(s, block));
        }
        return true;
    }

    public boolean isDropExp(Level level, Block block) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(level.getName(), "Block", "DropExp");
        if (bool != null && !bool) {
            return LevelEventPlusMain.getLevelStringListInit(level.getName(), "Block", "DropExpBlocks").stream().anyMatch(s -> ItemUtils.isEqual(s, block));
        }
        return true;
    }

    @EventHandler
    public void BlockBurnEvent(BlockBurnEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "Burn");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockIgniteEvent(BlockIgniteEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "Ignite");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFallEvent(BlockFallEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "Fall");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockGrowEvent(BlockGrowEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "Grow");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockSpreadEvent(BlockSpreadEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "Spread");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFormEvent(BlockFormEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "Form");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void LeavesDecayEvent(LeavesDecayEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "LeavesDecay");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void LiquidFlowEvent(LiquidFlowEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "LiquidFlow");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void RedstoneUpdateEvent(BlockUpdateEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "BlockRedstone");
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
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "ItemFrameDropItem");
        if (bool == null) {
            return;
        }
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Level level = event.getPlayer().getLevel();
        if (!bool) {
            if (LevelEventPlusMain.show_actionbar_text) {
                event.getPlayer().sendActionBar(LevelEventPlusMain.language.translateString("tip_destroyFrameBlock", level.getName()));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void SignChangeEvent(SignChangeEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "SignChange");
        if (bool == null) {
            return;
        }
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Level level = event.getPlayer().getLevel();
        if (!bool) {
            if (LevelEventPlusMain.show_actionbar_text) {
                event.getPlayer().sendActionBar(LevelEventPlusMain.language.translateString("tip_changeSignText", level.getName()));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockUpdateEvent(BlockUpdateEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "Update");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFadeEvent(BlockFadeEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "Fade");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockPistonChangeEvent(BlockPistonEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "PistonChange");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFromToEvent(BlockFromToEvent event) {
        Boolean bool = LevelEventPlusMain.getLevelBooleanInit(event.getBlock().getLevel().getName(), "Block", "FromToEvent");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }
}
