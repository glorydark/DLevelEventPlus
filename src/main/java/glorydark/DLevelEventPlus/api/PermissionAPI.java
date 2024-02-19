package glorydark.DLevelEventPlus.api;


import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;
import glorydark.DLevelEventPlus.LevelEventPlusMain;

import java.io.File;
import java.util.*;

public class PermissionAPI {
    public static void whiteList(CommandSender sender, OperatePermissionType type, String playerName, String levelname) {
        Config worldcfg = new Config(LevelEventPlusMain.path + "/whitelists.yml", Config.YAML);
        List<String> arrayList = new ArrayList<>(worldcfg.getStringList(levelname));
        Player player = Server.getInstance().getPlayer(playerName);
        switch (type) {
            case ADD:
                if (!arrayList.contains(playerName)) {
                    arrayList.add(playerName);
                    worldcfg.set(levelname, arrayList);
                    worldcfg.save();
                    if (player != null) {
                        player.sendMessage(LevelEventPlusMain.language.translateString("tip_whitelist_add_success_receiver", playerName, levelname));
                    }
                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_whitelist_add_success", playerName, levelname));
                } else {
                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_whitelist_add_failed", playerName, levelname));
                }
                break;
            case REMOVE:
                if (arrayList.contains(playerName)) {
                    arrayList.remove(playerName);
                    worldcfg.set(levelname, arrayList);
                    worldcfg.save();
                    if (player != null) {
                        player.sendMessage(LevelEventPlusMain.language.translateString("tip_whitelist_del_success_receiver", playerName, levelname));
                    }
                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_whitelist_del_success", playerName, levelname));
                } else {
                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_whitelist_del_failed", playerName, levelname));
                }
                break;
        }
    }

    public static void adminList(CommandSender sender, OperatePermissionType type, String playerName) {
        Config worldcfg = new Config(LevelEventPlusMain.path + "/admins.yml", Config.YAML);
        Player player = Server.getInstance().getPlayer(playerName);
        List<String> arrayList = new ArrayList<>(worldcfg.getStringList("list"));
        switch (type) {
            case ADD:
                if (!arrayList.contains(playerName)) {
                    arrayList.add(playerName);
                    worldcfg.set("list", arrayList);
                    worldcfg.save();
                    if (player != null) {
                        player.sendMessage(LevelEventPlusMain.language.translateString("tip_admin_add_success_receiver", playerName));
                    }
                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_admin_add_success", playerName));
                } else {
                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_admin_add_failed", playerName));
                }
                break;
            case REMOVE:
                if (worldcfg.exists("list")) {
                    if (arrayList.contains(playerName)) {
                        arrayList.remove(playerName);
                        worldcfg.set("list", arrayList);
                        worldcfg.save();
                        if (player != null) {
                            player.sendMessage(LevelEventPlusMain.language.translateString("tip_admin_del_success_receiver", playerName));
                        }
                        sender.sendMessage(LevelEventPlusMain.language.translateString("tip_admin_del_success", playerName));
                    }
                } else {
                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_admin_del_failed", playerName));
                }
                break;
        }
    }

    public static void operatorList(CommandSender sender, OperatePermissionType type, String playerName, String levelname) {
        Player player = Server.getInstance().getPlayer(playerName);
        Config worldcfg = new Config(LevelEventPlusMain.path + "/operators.yml", Config.YAML);
        List<String> arrayList = new ArrayList<>(worldcfg.getStringList(levelname));
        switch (type) {
            case ADD:
                if (!arrayList.contains(playerName)) {
                    arrayList.add(playerName);
                    worldcfg.set(levelname, arrayList);
                    worldcfg.save();
                    if (player != null) {
                        player.sendMessage(LevelEventPlusMain.language.translateString("tip_operator_add_success_receiver", playerName, levelname));
                    }
                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_operator_add_success", playerName, levelname));
                } else {
                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_operator_add_failed", playerName, levelname));
                }
                break;
            case REMOVE:
                if (worldcfg.exists(levelname)) {
                    if (arrayList.contains(playerName)) {
                        arrayList.remove(playerName);
                        worldcfg.set(levelname, arrayList);
                        worldcfg.save();
                        if (player != null) {
                            player.sendMessage(LevelEventPlusMain.language.translateString("tip_operator_del_success_receiver", playerName, levelname));
                        }
                        sender.sendMessage(LevelEventPlusMain.language.translateString("tip_operator_del_success", playerName, levelname));
                    } else {
                        sender.sendMessage(LevelEventPlusMain.language.translateString("tip_operator_del_failed", playerName, levelname));
                    }
                }
                break;
        }
    }

    public static boolean isAdmin(Player p) {
        if (p == null) {
            return false;
        }
        File file = new File(LevelEventPlusMain.path + "/admins.yml");
        if (file.exists()) {
            Config worldcfg = new Config(LevelEventPlusMain.path + "/admins.yml", Config.YAML);
            if (worldcfg.exists("list")) {
                return worldcfg.getStringList("list").contains(p.getName());
            }
        }
        return false;
    }

    public static boolean isOperator(Player p, Level level) {
        if (p == null) {
            return false;
        }
        if (level == null) {
            return false;
        }
        File file = new File(LevelEventPlusMain.path + "/operators.yml");
        if (file.exists()) {
            Config worldcfg = new Config(LevelEventPlusMain.path + "/operators.yml", Config.YAML);
            if (worldcfg.get(level.getName()) != null) {
                return worldcfg.getStringList(level.getName()).contains(p.getName());
            }
        }
        return false;
    }

    public static boolean isWhiteListed(Player p, Level level) {
        if (p == null) {
            return false;
        }
        if (level == null) {
            return false;
        }
        File file = new File(LevelEventPlusMain.path + "/whitelists.yml");
        if (file.exists()) {
            Config worldcfg = new Config(LevelEventPlusMain.path + "/whitelists.yml", Config.YAML);
            if (worldcfg.exists(level.getName())) {
                return worldcfg.getStringList(level.getName()).contains(p.getName());
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public enum OperatePermissionType{
        ADD,
        REMOVE
    }
}
