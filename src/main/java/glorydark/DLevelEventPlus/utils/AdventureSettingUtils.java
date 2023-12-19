package glorydark.DLevelEventPlus.utils;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.level.Level;
import glorydark.DLevelEventPlus.MainClass;

/**
 * @author glorydark
 * @date {2023/10/4} {16:30}
 */
public class AdventureSettingUtils {

    /*
        Special thanks to <lt-name> for formulating these changes,
        which definitely plummet the redundant packet
        while we are modifying players' adventure settings.
     */
    public static void setAllowInteract(Player player, boolean bool) {
        boolean change = false;
        if (!bool == player.getAdventureSettings().get(AdventureSettings.Type.WORLD_IMMUTABLE)) {
            player.getAdventureSettings().set(AdventureSettings.Type.WORLD_IMMUTABLE, bool);
            change = true;
        }
        if (!bool == player.getAdventureSettings().get(AdventureSettings.Type.DOORS_AND_SWITCHED)) {
            player.getAdventureSettings().set(AdventureSettings.Type.DOORS_AND_SWITCHED, bool);
            change = true;
        }
        if (!bool == player.getAdventureSettings().get(AdventureSettings.Type.OPEN_CONTAINERS)) {
            player.getAdventureSettings().set(AdventureSettings.Type.OPEN_CONTAINERS, bool);
            change = true;
        }
        if (change) {
            player.getAdventureSettings().update();
        }
    }

    public static void setAllowModifyWorld(Player player, boolean bool) {
        boolean change = false;
        if (!bool == player.getAdventureSettings().get(AdventureSettings.Type.WORLD_IMMUTABLE)) {
            player.getAdventureSettings().set(AdventureSettings.Type.WORLD_IMMUTABLE, bool);
            change = true;
        }
        if (!bool == player.getAdventureSettings().get(AdventureSettings.Type.BUILD)) {
            player.getAdventureSettings().set(AdventureSettings.Type.BUILD, bool);
            change = true;
        }
        if (!bool == player.getAdventureSettings().get(AdventureSettings.Type.WORLD_BUILDER)) {
            player.getAdventureSettings().set(AdventureSettings.Type.WORLD_BUILDER, bool);
            change = true;
        }
        if (change) {
            player.getAdventureSettings().update();
        }
    }

    public static void updatePlayerAdventureSettings(Player player, Level level) {
        if (ConfigUtil.isAdmin(player)) {
            AdventureSettingUtils.setAllowInteract(player, true);
            AdventureSettingUtils.setAllowModifyWorld(player, true);
            return;
        }
        if (ConfigUtil.isOperator(player, level)) {
            AdventureSettingUtils.setAllowInteract(player, true);
            AdventureSettingUtils.setAllowModifyWorld(player, true);
            return;
        }
        Boolean interact = MainClass.getLevelBooleanInit(level.getName(), "Player", "Interact");
        Boolean placeB = MainClass.getLevelBooleanInit(level.getName(), "Block", "AllowPlaceBlock");
        Boolean breakB = MainClass.getLevelBooleanInit(level.getName(), "Block", "AllowBreakBlock");
        boolean final1 = interact != null && !interact; // 是否不可放置
        boolean final2 = !final1 && placeB != null && breakB != null && !placeB && !breakB;
        AdventureSettingUtils.setAllowInteract(player, !final1);
        AdventureSettingUtils.setAllowModifyWorld(player, !final2);
    }
}
