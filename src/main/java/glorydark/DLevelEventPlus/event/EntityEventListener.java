package glorydark.DLevelEventPlus.event;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityMinecartTNT;
import cn.nukkit.entity.item.EntityPrimedTNT;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.*;
import cn.nukkit.level.Level;
import glorydark.DLevelEventPlus.MainClass;
import glorydark.DLevelEventPlus.utils.Config;

public class EntityEventListener implements Listener {
    //Entity
    @EventHandler
    public void EntityInteractEvent(EntityInteractEvent event) {
        //实体导致耕地踩踏
        Entity entity = event.getEntity();
        if (event.getBlock().getId() == Block.FARMLAND && !(entity instanceof Player)) {
            Boolean test = MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(), "World.FarmProtect");
            if (test != null && test) {
                event.setCancelled(true);
            }
        }


        if (event.getBlock().getId() == Block.ITEM_FRAME_BLOCK && !(entity instanceof Player)) {
            if(MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World.AllowInteractFrameBlock") == null){return;}
            Boolean test = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Player.AllowInteractFrameBlock");
            if (test != null && test) {
                event.setCancelled(true);
            }
        }
    }

    //TNT爆炸
    @EventHandler
    public void EntityExplodeEvent(EntityExplodeEvent event){
        Entity entity = event.getEntity();
        Boolean test1 = MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World.TntExplodes");
        Boolean test2 = MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World.AllExplodes");
        if(test1 == null){return;}
        if(test2 == null){return;}
        if(entity instanceof EntityMinecartTNT || entity instanceof EntityPrimedTNT) {
            if (!test1) {
                entity.despawnFromAll();
                MainClass.server.getLogger().info(Config.getLang("AntiTntExplode"));
                event.setCancelled(true);
            }
        }
        if (!test2) {
            entity.despawnFromAll();
            MainClass.server.getLogger().info(Config.getLang("AntiAllExplode"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityExplosionPrimeEvent(EntityExplosionPrimeEvent event){
        Boolean test1 = MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World.AllExplodes");
        if(test1 == null){return;}
        if (!test1) {
            MainClass.server.getLogger().info(Config.getLang("AntiTntExplode"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent event){
        Boolean test1 = MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World.Pvp");
        if(test1 == null){return;}
        Entity entity = event.getEntity();
        if(entity instanceof Player && event.getDamager() instanceof Player) {
            if(Config.isOperateListed((Player) event.getEntity(), event.getEntity().getLevel())){ return; }
            if (!test1) {
                ((Player) entity).sendActionBar(Config.getLang("AntiPvp"));
                ((Player) event.getDamager()).sendActionBar(Config.getLang("AntiPvp"));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void EntityPortalEnterEvent(EntityPortalEnterEvent event){
        if(MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"Entity.PortalEnter") == null){return;}
        Entity entity = event.getEntity();
        if(entity instanceof Player) {
            if(Config.isOperateListed((Player) event.getEntity(), event.getEntity().getLevel())){ return; }
            if (!MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(), "Entity.PortalEnter")) {
                ((Player) entity).sendActionBar(Config.getLang("AntiPortalEnter"));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void EntityVehicleEnterEvent(EntityVehicleEnterEvent event){
        if(MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"Entity.VehicleEnter") == null){return;}
        Entity entity = event.getEntity();
        if(entity instanceof Player) {
            if(Config.isOperateListed((Player) event.getEntity(), event.getEntity().getLevel())){ return; }
            if (!MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(), "Entity.VehicleEnter")) {
                ((Player) entity).sendActionBar(Config.getLang("AntiVehicleEnter"));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void EntityLevelChangeEvent(EntityLevelChangeEvent event){
        if(!(event.getEntity() instanceof Player)){return;}
        if(Config.isOperateListed((Player) event.getEntity(), event.getEntity().getLevel())){ return; }
        Player p = (Player) event.getEntity();
        Level level = event.getTarget();
        if(Config.isWhiteListed(p,level)){ return;}
        p.sendActionBar(Config.getLang("AntiTeleport").replace("%s",level.getName()));
        event.setCancelled(true);
    }
}
