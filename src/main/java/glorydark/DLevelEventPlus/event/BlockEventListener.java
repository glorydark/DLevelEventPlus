package glorydark.DLevelEventPlus.event;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.*;
import cn.nukkit.event.redstone.RedstoneUpdateEvent;
import cn.nukkit.level.Level;
import glorydark.DLevelEventPlus.MainClass;
import glorydark.DLevelEventPlus.utils.Config;

public class BlockEventListener implements Listener {
    //Block
    //方块放置
    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.AllowPlaceBlock") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.AllowPlaceBlock")) {
            if(event.getPlayer().isOnline()) {
                event.getPlayer().sendActionBar(Config.getLang("AntiPlaceBlock"));
            }
            event.setCancelled(true);
        }
    }

    //方块破坏
    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.AllowBreakBlock") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.AllowBreakBlock")) {
            if(event.getPlayer().isOnline()) {
                event.getPlayer().sendActionBar(Config.getLang("AntiBreakBlock"));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockBurnEvent(BlockBurnEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.Burn") == null){return;}
        if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.Burn")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockIgniteEvent(BlockIgniteEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.Ignite") == null){return;}
        if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.Ignite")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFallEvent(BlockFallEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.Fall") == null){return;}
        if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.Fall")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockGrowEvent(BlockGrowEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.Grow") == null){return;}
        if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.Grow")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockSpreadEvent(BlockSpreadEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.Spread") == null){return;}
        if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.Spread")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFormEvent(BlockFormEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.Form") == null){return;}
        if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.Form")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void DoorToggleEvent(DoorToggleEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.DoorToggle") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.DoorToggle")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void LeavesDecayEvent(LeavesDecayEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.LeavesDecay") == null){return;}
        if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.LeavesDecay")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void LiquidFlowEvent(LiquidFlowEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.LiquidFlow") == null){return;}
        if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.LiquidFlow")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockRedstoneEvent(BlockRedstoneEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.BlockRedstone") == null){return;}
        if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.BlockRedstone")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void RedstoneUpdateEvent(RedstoneUpdateEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.BlockRedstone") == null){return;}
        if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.BlockRedstone")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void ItemFrameDropItemEvent(ItemFrameDropItemEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.ItemFrameDropItem") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        Level level = event.getPlayer().getLevel();
        if (!MainClass.getLevelBooleanInit(level.getName(),"Block.ItemFrameDropItem")) {
            event.getPlayer().sendActionBar(Config.getLang("AntiDestroyFlameBlock").replace("%s",level.getName()));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void SignChangeEvent(SignChangeEvent event){
        if(MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block.SignChange") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        Level level = event.getPlayer().getLevel();
        if (!MainClass.getLevelBooleanInit(level.getName(),"Block.SignChange")) {
            event.getPlayer().sendActionBar(Config.getLang("AntiChangeSignText").replace("%s",level.getName()));
            event.setCancelled(true);
        }
    }
}
