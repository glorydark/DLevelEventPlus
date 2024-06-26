package glorydark.DLevelEventPlus.event;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.ProjectileLaunchEvent;
import cn.nukkit.event.inventory.InventoryPickupItemEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemFishingRod;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.EmotePacket;
import cn.nukkit.network.protocol.PlayerActionPacket;
import glorydark.DLevelEventPlus.api.LevelSettingsAPI;
import glorydark.DLevelEventPlus.api.PermissionAPI;
import glorydark.DLevelEventPlus.utils.ItemUtils;
import glorydark.DLevelEventPlus.utils.LiquidItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlayerEventListener implements Listener {

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }

        Level level = player.getLevel();
        Block block = event.getBlock();
        Item item = player.getInventory().getItemInHand();
        Boolean interact = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), "Player", "Interact");
        if (interact != null && !interact) {
            event.setCancelled(true);
            return;
        }

        if (block != null) {
            if (block.getId() == 389 || block.getId() == -339) {
                Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), "Player", "AllowInteractFrameBlock");
                if (bool != null && !bool) {
                    event.setCancelled(true);
                }
            }

            //玩家导致耕地踩踏
            if (block.getId() == Block.FARMLAND) {
                Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), "World", "FarmProtect");
                if (bool != null && event.getAction() == PlayerInteractEvent.Action.PHYSICAL) {
                    if (bool) {
                        event.setCancelled(true);
                    }
                }
            }

            if (block.getId() == Block.CHEST || block.getId() == Block.ENDER_CHEST) {
                Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), "Player", "AllowOpenChest");
                if (bool != null && !bool) {
                    if (LevelSettingsAPI.getLevelStringListSetting(level.getName(), "Player", "ChestTrustList") == null) {
                        return;
                    }
                    List<String> list = LevelSettingsAPI.getLevelStringListSetting(level.getName(), "Player", "ChestTrustList");
                    List<Position> positionArrayList = new ArrayList<>();
                    for (String str : Objects.requireNonNull(list)) {
                        List<String> strspl = Arrays.asList(str.split(":"));
                        if (strspl.size() == 3) {
                            positionArrayList.add(new Position(Double.parseDouble(strspl.get(0)), Double.parseDouble(strspl.get(1)), Double.parseDouble(strspl.get(2))));
                        }
                    }
                    if (!positionArrayList.contains(block.getLocation())) {
                        event.setCancelled(true);
                    }
                }
            }

            if (LevelSettingsAPI.getLevelStringListSetting(level.getName(), "Player", "BannedInteractBlocks").stream().anyMatch(s -> ItemUtils.isEqual(s, block))) {
                event.setCancelled(true);
            }

            if (LiquidItem.isLiquidItem(item)) {
                Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(block.getLevel().getName(), "Block", "AllowPlaceBlock");
                if (bool == null) {
                    return;
                }
                if (bool) {
                    return;
                }
                event.setCancelled(true);
            }
        }

        if (item != null) {
            if (item instanceof ItemFishingRod) {
                Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), "Player", "CanUseFishingHook");
                if (bool != null && !bool) {
                    event.setCancelled(true);
                }
            }

            List<String> strings = LevelSettingsAPI.getLevelStringListSetting(level.getName(), "Player", "BannedUseItems");
            if (strings.stream().anyMatch(s -> ItemUtils.isEqual(s, item))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerTeleportEvent(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(player.getLevel().getName(), "Player", "AntiTeleport");
        if (bool == null) {
            return;
        }
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        Level toLevel = event.getTo().getLevel();
        if (PermissionAPI.isWhiteListed(player, toLevel)) {
            return;
        }
        if (event.getFrom().getLevel() == event.getTo().getLevel()) {
            if (bool) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerBlockPickEvent(PlayerBlockPickEvent event) {
        if (PermissionAPI.isAdmin(event.getPlayer())) {
            return;
        }
        if (PermissionAPI.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getPlayer().getLevel().getName(), "Player", "Pick");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void InventoryPickupItemEvent(InventoryPickupItemEvent event) {
        for (Player p : event.getViewers()) {
            Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(p.getLevel().getName(), "Player", "Pick");
            if (bool == null) {
                return;
            }
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
    public void PlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        if (PermissionAPI.isAdmin(event.getPlayer())) {
            return;
        }
        if (PermissionAPI.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getPlayer().getLevel().getName(), "Player", "ConsumeItem");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerBedEnterEvent(PlayerBedEnterEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getPlayer().getLevel().getName(), "Player", "BedEnter");
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
        }
    }

    @EventHandler
    public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getPlayer().getLevel().getName(), "Player", "CommandPreprocess");
        if (bool == null) {
            return;
        }
        if (PermissionAPI.isAdmin(event.getPlayer())) {
            return;
        }
        if (PermissionAPI.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        if (event.getPlayer() == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerGameModeChangeEvent(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        Level level = player.getLevel();
        Object forceGameModeObj = LevelSettingsAPI.getLevelObjectSetting(level.getName(), "World", "ForceGameMode");
        if (forceGameModeObj != null) {
            return; // 防止腐竹重复设置同种功能，强制游戏模式开启无需禁用模式改变
        }
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), "Player", "GameModeChange");
        if (bool == null) {
            return;
        }
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, level)) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerEatFoodEvent(PlayerEatFoodEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getPlayer().getLevel().getName(), "Player", "EatFood");
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
        }
    }

    @EventHandler
    public void PlayerFoodLevelChangeEvent(PlayerFoodLevelChangeEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getPlayer().getLevel().getName(), "Player", "HungerChange");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.getPlayer().getFoodData().reset();
        }
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event) {
        Boolean bool1 = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), "World", "KeepXp");
        Boolean bool2 = LevelSettingsAPI.getLevelBooleanSetting(event.getEntity().getLevel().getName(), "World", "KeepInventory");
        if (bool1 != null) {
            event.setKeepExperience(bool1);
        }
        if (bool2 != null) {
            event.setKeepInventory(bool2);
        }
    }

    @EventHandler
    public void ProjectileLaunchEvent(ProjectileLaunchEvent event) {
        Entity entity = event.getEntity().shootingEntity;
        if (entity instanceof Player) {
            Player player = (Player) entity;
            Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(player.getLevel().getName(), "Player", "ConsumeItem");
            if (bool == null) {
                return;
            }
            if (PermissionAPI.isAdmin(player)) {
                return;
            }
            if (PermissionAPI.isOperator(player, event.getEntity().getLevel())) {
                return;
            }
            if (!bool) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerToggleFlightEvent(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(player.getLevel().getName(), "Player", "Fly");
        if (bool == null) {
            return;
        }
        if (!bool & event.isFlying()) {
            player.setPosition(player.getPosition());
            player.setMotion(new Vector3(0, -1, 0));
            player.fall(player.getMotion().getFloorY() + 1);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void DataPacketReceiveEvent(DataPacketReceiveEvent event) {
        Player player = event.getPlayer();
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        if (event.getPacket() instanceof PlayerActionPacket) {
            PlayerActionPacket pk = (PlayerActionPacket) event.getPacket();
            if (pk.action == PlayerActionPacket.ACTION_JUMP) {
                if (event.getPlayer().getGamemode() != 1 && event.getPlayer().getGamemode() != 3) {
                    Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getPlayer().getLevel().getName(), "Player", "Jump");
                    if (bool == null) {
                        return;
                    }
                    if (!bool) {
                        event.getPlayer().teleport(event.getPlayer().getLocation());
                        event.getPlayer().setMotion(new Vector3(0, -1, 0));
                    }
                    return;
                }
            }
        }

        if (event.getPacket() instanceof EmotePacket) {
            Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getPlayer().getLevel().getName(), "Player", "Emote");
            if (bool == null) {
                return;
            }
            if (!bool) {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void PlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(player.getLevel().getName(), "Player", "DropItem");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerToggleGlideEvent(PlayerToggleGlideEvent event) {
        Player player = event.getPlayer();
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(player.getLevel().getName(), "Player", "Glide");
        if (bool == null) {
            return;
        }
        if (!bool && event.isGliding()) {
            player.teleport(event.getPlayer().getLocation(), null);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerToggleSwimEvent(PlayerToggleSwimEvent event) {
        Player player = event.getPlayer();
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(player.getLevel().getName(), "Player", "Swim");
        if (bool == null) {
            return;
        }
        if (!bool && event.isSwimming()) {
            player.teleport(player.getLocation(), null);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(player.getLevel().getName(), "Player", "Sneak");
        if (bool == null) {
            return;
        }
        if (!bool && event.isSneaking()) {
            player.teleport(player.getLocation(), null);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerToggleSprintEvent(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(player.getLevel().getName(), "Player", "Sprint");
        if (bool == null) {
            return;
        }
        if (!bool && event.isSprinting()) {
            player.teleport(player.getLocation(), null);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerAchievementAwardedEvent(PlayerAchievementAwardedEvent event) {
        Player player = event.getPlayer();
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(player.getLevel().getName(), "Player", "Achievement");
        if (bool == null) {
            return;
        }
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerBucketFillEvent(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(player.getLevel().getName(), "Player", "BucketFill");
        if (bool == null) {
            return;
        }
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerBucketEmptyEvent(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(player.getLevel().getName(), "Player", "BucketEmpty");
        if (bool == null) {
            return;
        }
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerItemHeldEvent(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItem();
        if (PermissionAPI.isAdmin(player)) {
            return;
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            return;
        }
        List<String> clearItems = LevelSettingsAPI.getLevelStringListSetting(player.getLevel().getName(), "Player", "ClearItems");
        if (clearItems.stream().anyMatch(s -> ItemUtils.isEqual(s, item))) {
            player.getInventory().remove(item);
        }
    }
}