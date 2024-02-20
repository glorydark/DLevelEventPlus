package glorydark.DLevelEventPlus;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.Task;
import glorydark.DLevelEventPlus.api.LevelSettingsAPI;
import glorydark.DLevelEventPlus.api.PermissionAPI;
import glorydark.DLevelEventPlus.utils.ItemUtils;

import java.util.List;
import java.util.Map;

public class CheckTask extends Task {

    @Override
    public void onRun(int i) {
        for (Level level : Server.getInstance().getLevels().values()) {
            List<String> clearItems = LevelSettingsAPI.getLevelStringListSetting(level.getName(), "Player", "ClearItems");
            Boolean movable = LevelSettingsAPI.getLevelBooleanSetting(level.getName(), "World", "Move");
            Object forceGameModeObj = LevelSettingsAPI.getLevelObjectSetting(level.getName(), "World", "ForceGameMode");
            int forceGamemode = -1;
            if (forceGameModeObj != null) {
                forceGamemode = Server.getGamemodeFromString(forceGameModeObj.toString());
            }
            for (Player player : level.getPlayers().values()) {
                // anti void
                Boolean antiVoid = LevelSettingsAPI.getLevelBooleanSetting(player.getLevel().getName(), "World", "AntiVoid");
                if (antiVoid != null) {
                    if (antiVoid) {
                        Object voidHeight = LevelSettingsAPI.getLevelObjectSetting(player.getLevel().getName(), "World", "VoidHeight");
                        if (voidHeight != null) {
                            if (player.getFloorY() <= Integer.parseInt(voidHeight.toString())) {
                                player.teleport(player.getLevel().getSpawnLocation().getLocation());
                            }
                        }
                    }
                }
                if (PermissionAPI.isAdmin(player)) {
                    player.setGamemode(Server.getInstance().getDefaultGamemode());
                    if (player.isImmobile()) {
                        player.setImmobile(false);
                    }
                    return;
                }
                if (PermissionAPI.isOperator(player, player.getLevel())) {
                    player.setGamemode(Server.getInstance().getDefaultGamemode());
                    if (player.isImmobile()) {
                        player.setImmobile(false);
                    }
                    return;
                }
                // force gamemode

                // check invalid items
                for (Map.Entry<Integer, Item> entry : player.getInventory().getContents().entrySet()) {
                    Item check = entry.getValue();
                    if (clearItems.stream().anyMatch(s -> ItemUtils.isEqual(s, check))) {
                        player.getInventory().remove(check);
                    }
                }
                // check moveable
                if (movable != null) {
                    // If they are in contrast. Immobile -> movable
                    // This aims at reducing packet sending
                    if (player.isImmobile() == movable) {
                        player.setImmobile(!movable);
                    }
                }
                if (forceGamemode != -1) {
                    player.setGamemode(forceGamemode);
                } else {
                    player.setGamemode(Server.getInstance().getDefaultGamemode());
                }
            }
        }
    }
}