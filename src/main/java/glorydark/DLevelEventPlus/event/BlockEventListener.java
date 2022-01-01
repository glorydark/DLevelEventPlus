package glorydark.DLevelEventPlus.event;

import cn.nukkit.Server;
import cn.nukkit.block.*;
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
        Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","AllowPlaceBlock");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            if(event.getPlayer().isOnline()) {
                event.getPlayer().sendActionBar(Config.getLang("AntiPlaceBlock"));
            }
            event.setCancelled(true);
        }else{
            Block block = event.getBlock();
            String blockString = block.getId()+":"+block.getDamage();
            if(MainClass.getLevelStringListInit(block.getLevel().getName(),"Block","AntiPlaceBlocks").contains(blockString)){
                if(event.getPlayer().isOnline()) {
                    event.getPlayer().sendActionBar(Config.getLang("AntiPlaceSpecificBlock"));
                }
                event.setCancelled(true);
            }
        }
    }

    //方块破坏
    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","AllowBreakBlock");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            if(event.getPlayer().isOnline()) {
                event.getPlayer().sendActionBar(Config.getLang("AntiBreakBlock"));
            }
            event.setCancelled(true);
        }else{
            Block block = event.getBlock();
            String blockString = block.getId()+":"+block.getDamage();
            if(MainClass.getLevelStringListInit(block.getLevel().getName(),"Block","AntiBreakBlocks").contains(blockString)){
                if(event.getPlayer().isOnline()) {
                    event.getPlayer().sendActionBar(Config.getLang("AntiBreakSpecificBlock"));
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BlockBurnEvent(BlockBurnEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","Burn");
        if(bool == null){return;}
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockIgniteEvent(BlockIgniteEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","Ignite");
        if(bool == null){return;}
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFallEvent(BlockFallEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","Fall");
        if(bool == null){return;}
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockGrowEvent(BlockGrowEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","Grow");
        if(bool == null){return;}
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockSpreadEvent(BlockSpreadEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","Spread");
        if(bool == null){return;}
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void BlockFormEvent(BlockFormEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","Form");
        if(bool == null){return;}
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void LeavesDecayEvent(LeavesDecayEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","LeavesDecay");
        if(bool == null){return;}
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void LiquidFlowEvent(LiquidFlowEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","LiquidFlow");
        if(bool == null){return;}
        if (!bool) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void RedstoneUpdateEvent(BlockUpdateEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","BlockRedstone");
        if(bool == null){return;}
        if (!bool) {
            if(event.getBlock().isPowerSource()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void ItemFrameDropItemEvent(ItemFrameDropItemEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","ItemFrameDropItem");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        Level level = event.getPlayer().getLevel();
        if (!bool) {
            event.getPlayer().sendActionBar(Config.getLang("AntiDestroyFlameBlock").replace("%s",level.getName()));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void SignChangeEvent(SignChangeEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","SignChange");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        Level level = event.getPlayer().getLevel();
        if (!bool) {
            event.getPlayer().sendActionBar(Config.getLang("AntiChangeSignText").replace("%s",level.getName()));
            event.setCancelled(true);
        }
    }
}
