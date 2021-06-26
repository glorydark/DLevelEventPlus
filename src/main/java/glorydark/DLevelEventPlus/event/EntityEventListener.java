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
            if(MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World.FarmProtect") == null){return;}
            if (MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"World.FarmProtect")) {
                event.setCancelled(true);
            }
        }

        if (event.getBlock().getId() == Block.ITEM_FRAME_BLOCK) {
            if(MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World.AllowInteractFrameBlock") == null){return;}
            if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Player.AllowInteractFrameBlock")) {
                event.setCancelled(true);
            }
        }
    }

    //TNT爆炸
    @EventHandler
    public void EntityExplodeEvent(EntityExplodeEvent event){
        Entity entity = event.getEntity();
        if(MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World.TntExplodes") == null){return;}
        if(MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World.AllExplodes") == null){return;}
        if(entity instanceof EntityMinecartTNT || entity instanceof EntityPrimedTNT) {
            if (!MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(), "World.TntExplodes")) {
                entity.despawnFromAll();
                MainClass.server.getLogger().info(Config.getLang("AntiTntExplode"));
                event.setCancelled(true);
            }
        }
        if (!MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(), "World.AllExplodes")) {
            entity.despawnFromAll();
            MainClass.server.getLogger().info(Config.getLang("AntiAllExplode"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityExplosionPrimeEvent(EntityExplosionPrimeEvent event){
        if(MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World.AllExplodes") == null){return;}
        if (!MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(), "World.AllExplodes")) {
            MainClass.server.getLogger().info(Config.getLang("AntiTouchFlame"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent event){
        if(MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World.Pvp") == null){return;}
        Entity entity = event.getEntity();
        if(entity instanceof Player) {
            if (!MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(), "World.Pvp")) {
                ((Player) entity).sendActionBar(Config.getLang("AntiPvp"));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void EntityPortalEnterEvent(EntityPortalEnterEvent event){
        if(MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"Entity.PortalEnter") == null){return;}
        Entity entity = event.getEntity();
        if(entity instanceof Player) {
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
            if (!MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(), "Entity.VehicleEnter")) {
                ((Player) entity).sendActionBar(Config.getLang("AntiVehicleEnter"));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void EntityLevelChangeEvent(EntityLevelChangeEvent event){
        if(!(event.getEntity() instanceof Player)){return;}
        Player p = (Player) event.getEntity();
        if(Config.isOperateListed(p)){ return; }
        if(Config.isWhiteListed(p)){ return;}
        Level level = event.getTarget();
        p.sendActionBar(Config.getLang("AntiTeleport").replace("%s",level.getName()));
        event.setCancelled(true);
    }
}
