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
import glorydark.DLevelEventPlus.api.LevelSettingsAPI;
import glorydark.DLevelEventPlus.api.PermissionAPI;

public class EntityEventListener implements Listener {
    //Entity
    @EventHandler
    public void EntityInteractEvent(EntityInteractEvent event) {
        //实体导致耕地踩踏
        Entity entity = event.getEntity();
        if (event.getBlock().getId() == Block.FARMLAND && !(entity instanceof Player)) {
            Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), "World", "FarmProtect");
            if (bool != null && bool) {
                event.setCancelled(true);
            }
        }


        if (event.getBlock().getId() == Block.ITEM_FRAME_BLOCK && !(entity instanceof Player)) {
            if (LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), "World", "AllowInteractFrameBlock") == null) {
                return;
            }
            Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getBlock().getLevel().getName(), "Player", "AllowInteractFrameBlock");
            if (bool != null && bool) {
                event.setCancelled(true);
            }
        }
    }

    //TNT爆炸
    @EventHandler
    public void EntityExplodeEvent(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        Boolean bool1 = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), "World", "TntExplodes");
        Boolean bool2 = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), "World", "AllExplodes");
        Boolean bool3 = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), "Entity", "Explosion");
        if (bool1 == null) {
            return;
        }
        if (bool2 == null) {
            return;
        }
        if (bool3 == null) {
            return;
        }
        if (entity instanceof EntityMinecartTNT || entity instanceof EntityPrimedTNT) {
            if (!bool1) {
                entity.despawnFromAll();
                event.setCancelled(true);
            }
        }
        if (!bool3) {
            entity.despawnFromAll();
            event.setCancelled(true);
            return;
        }
        if (!bool2) {
            entity.despawnFromAll();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityExplosionPrimeEvent(EntityExplosionPrimeEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), "World", "AllExplodes");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityDamageEvent(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        Boolean antiVoid = LevelSettingsAPI.getLevelBooleanSetting(entity.getLevel().getName(), "World", "AntiVoid");
        if (antiVoid != null) {
            if (antiVoid) {
                if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    entity.teleport(entity.getLevel().getSpawnLocation().getLocation());
                    event.setCancelled(true);
                }
            }
        }
        Boolean noFallDamage = LevelSettingsAPI.getLevelBooleanSetting(entity.getLevel().getName(), "Player", "NoFallDamage");
        if (noFallDamage != null) {
            if (event.getEntity() instanceof Player && event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                event.setCancelled(noFallDamage);
            }
        }
    }

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), "World", "PVP");
        if (bool == null) {
            return;
        }
        Entity entity = event.getEntity();
        if (entity instanceof Player && event.getDamager() instanceof Player) {
            Player p = (Player) event.getDamager();
            if (PermissionAPI.isAdmin(p)) {
                return;
            }
            if (PermissionAPI.isOperator(p, event.getEntity().getLevel())) {
                return;
            }
            if (!bool) {
                event.setCancelled();
            }
        }
    }

    @EventHandler
    public void EntityPortalEnterEvent(EntityPortalEnterEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), "Entity", "PortalEnter");
        if (bool == null) {
            return;
        }
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player p = (Player) event.getEntity();
            if (PermissionAPI.isAdmin(p)) {
                return;
            }
            if (PermissionAPI.isOperator(p, p.getLevel())) {
                return;
            }
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityVehicleEnterEvent(EntityVehicleEnterEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), "Entity", "VehicleEnter");
        if (bool == null) {
            return;
        }
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player p = (Player) event.getEntity();
            if (PermissionAPI.isAdmin(p)) {
                return;
            }
            if (PermissionAPI.isOperator(p, p.getLevel())) {
                return;
            }
            if (!bool) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void EntityLevelChangeEvent(EntityLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player p = ((Player) event.getEntity()).getPlayer();
        Level level = event.getTarget();
        if (PermissionAPI.isAdmin(p)) {
            return;
        }
        if (PermissionAPI.isOperator(p, level)) {
            return;
        }
        boolean bool = PermissionAPI.isWhiteListed(p, level);
        if (bool) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void EntityBlockChangeEvent(EntityBlockChangeEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), "Entity", "BlockChange");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityCombustByBlockEvent(EntityCombustByBlockEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getCombuster().getLevel().getName(), "Entity", "CombustByBlock");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityCombustByEntityEvent(EntityCombustByEntityEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getCombuster().getLevel().getName(), "Entity", "CombustByEntity");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityDamageBlockedEvent(EntityDamageBlockedEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getAttacker().getLevel().getName(), "Entity", "DamageBlocked");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }
}