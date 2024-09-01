package glorydark.DLevelEventPlus.event;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityMinecartTNT;
import cn.nukkit.entity.item.EntityPrimedTNT;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.*;
import cn.nukkit.level.Level;
import glorydark.DLevelEventPlus.api.LevelSettingsAPI;
import glorydark.DLevelEventPlus.api.PermissionAPI;
import glorydark.DLevelEventPlus.protection.NameMapping;

public class EntityEventListener implements Listener {

    // Entity interactions
    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        Entity entity = event.getEntity();
        Block block = event.getBlock();

        // Prevent non-players from trampling farmland
        if (block.getId() == Block.FARMLAND && !(entity instanceof Player)) {
            Boolean protectFarm = LevelSettingsAPI.getLevelBooleanSetting(entity.getLevel().getName(), NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_FARM_PROTECT);
            if (protectFarm != null && protectFarm) {
                event.setCancelled(true);
            }
        }

        // Prevent non-players from interacting with item frames
        if (block.getId() == Block.ITEM_FRAME_BLOCK && !(entity instanceof Player)) {
            Boolean allowInteractFrame = LevelSettingsAPI.getLevelBooleanSetting(entity.getLevel().getName(), NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_ALLOW_INTERACT_FRAME_BLOCK);
            if (allowInteractFrame != null && allowInteractFrame) {
                event.setCancelled(true);
            }
        }
    }

    // TNT explosion events
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        Level level = entity.getLevel();

        Boolean tntExplodes = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_TNT_EXPLODES);
        Boolean allExplodes = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_ALL_EXPLODES);
        Boolean entityExplosion = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_EXPLOSION);

        if (tntExplodes == null || allExplodes == null || entityExplosion == null) {
            return;
        }

        if (entity instanceof EntityMinecartTNT || entity instanceof EntityPrimedTNT) {
            if (!tntExplodes) {
                entity.despawnFromAll();
                event.setCancelled(true);
            }
        }

        if (!entityExplosion || !allExplodes) {
            entity.despawnFromAll();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplosionPrime(EntityExplosionPrimeEvent event) {
        Boolean allExplodes = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_ALL_EXPLODES);
        if (allExplodes != null && !allExplodes) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        Level level = entity.getLevel();

        Boolean antiVoid = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_ANTI_VOID);
        if (antiVoid != null && antiVoid && event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            entity.teleport(level.getSpawnLocation().getLocation());
            event.setCancelled(true);
        }

        Boolean noFallDamage = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_NO_FALL_DAMAGE);
        if (noFallDamage != null && noFallDamage && entity instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Boolean pvp = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_PVP);
        if (pvp == null) {
            return;
        }

        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (entity instanceof Player && damager instanceof Player) {
            Player playerDamager = (Player) damager;
            if (PermissionAPI.isAdmin(playerDamager) || PermissionAPI.isOperator(playerDamager, entity.getLevel())) {
                return;
            }

            if (!pvp) {
                event.setCancelled();
            }
        }
    }

    @EventHandler
    public void onEntityPortalEnter(EntityPortalEnterEvent event) {
        Boolean portalEnter = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_PORTAL_ENTER);
        if (portalEnter == null) {
            return;
        }

        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (PermissionAPI.isAdmin(player) || PermissionAPI.isOperator(player, player.getLevel())) {
                return;
            }
        }

        if (!portalEnter) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityVehicleEnter(EntityVehicleEnterEvent event) {
        Boolean vehicleEnter = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_VEHICLE_ENTER);
        if (vehicleEnter == null) {
            return;
        }

        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (PermissionAPI.isAdmin(player) || PermissionAPI.isOperator(player, player.getLevel())) {
                return;
            }
            if (!vehicleEnter) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityLevelChange(EntityLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        Level targetLevel = event.getTarget();

        if (PermissionAPI.isAdmin(player) || PermissionAPI.isOperator(player, targetLevel)) {
            return;
        }

        if (!PermissionAPI.isWhiteListed(player, targetLevel)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityBlockChange(EntityBlockChangeEvent event) {
        Boolean blockChange = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_BLOCK_CHANGE);
        if (blockChange != null && !blockChange) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityCombustByBlock(EntityCombustByBlockEvent event) {
        Boolean combustByBlock = LevelSettingsAPI.getLevelBooleanSetting(event.getCombuster().getLevel().getName(), NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_COMBUST_BY_BLOCK);
        if (combustByBlock !=null && !combustByBlock) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityCombustByEntity(EntityCombustByEntityEvent event) {
        Boolean combustByEntity = LevelSettingsAPI.getLevelBooleanSetting(event.getCombuster().getLevel().getName(), NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_COMBUST_BY_ENTITY);
        if (combustByEntity != null && !combustByEntity) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageBlocked(EntityDamageBlockedEvent event) {
        Boolean damageBlocked = LevelSettingsAPI.getLevelBooleanSetting(event.getAttacker().getLevel().getName(), NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_DAMAGE_BLOCKED);
        if (damageBlocked != null && !damageBlocked) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityLightning) {
            Boolean lightningOnFire = LevelSettingsAPI.getLevelBooleanSetting(entity.getLevel().getName(), NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_LIGHTNING_ON_FIRE);
            if (lightningOnFire != null && !lightningOnFire) {
                ((EntityLightning) entity).setEffect(false);
            }
        }
    }
}
