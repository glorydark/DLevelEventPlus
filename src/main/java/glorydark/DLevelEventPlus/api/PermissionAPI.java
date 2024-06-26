package glorydark.DLevelEventPlus.api;


import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import glorydark.DLevelEventPlus.LevelEventPlusMain;

import java.io.File;
import java.util.*;

public class PermissionAPI {

    public static ConfigSection admins;

    public static ConfigSection operators;

    public static ConfigSection whitelists;

    public static void whiteList(CommandSender sender, OperatePermissionType type, String playerName, String levelname) {
        Config whitelistsConfig = new Config(LevelEventPlusMain.path + "/whitelists.yml", Config.YAML);
        List<String> arrayList = new ArrayList<>(whitelistsConfig.getStringList(levelname));
        Player player = Server.getInstance().getPlayer(playerName);
        switch (type) {
            case ADD:
                if (!arrayList.contains(playerName)) {
                    arrayList.add(playerName);
                    whitelistsConfig.set(levelname, arrayList);
                    whitelistsConfig.save();
                    whitelists = whitelistsConfig.getRootSection();
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
                    whitelistsConfig.set(levelname, arrayList);
                    whitelistsConfig.save();
                    whitelists = whitelistsConfig.getRootSection();
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
        Config adminConfig = new Config(LevelEventPlusMain.path + "/admins.yml", Config.YAML);
        Player player = Server.getInstance().getPlayer(playerName);
        List<String> arrayList = new ArrayList<>(adminConfig.getStringList("list"));
        switch (type) {
            case ADD:
                if (!arrayList.contains(playerName)) {
                    arrayList.add(playerName);
                    adminConfig.set("list", arrayList);
                    adminConfig.save();
                    admins = adminConfig.getRootSection();
                    if (player != null) {
                        Level level = player.getLevel();
                        Object forceGameModeObj = LevelSettingsAPI.getLevelObjectSetting(level.getName(), "World", "ForceGameMode");
                        int forceGamemode = -1;
                        if (forceGameModeObj != null) {
                            forceGamemode = Server.getGamemodeFromString(forceGameModeObj.toString());
                        }
                        if (player.getGamemode() == forceGamemode) {
                            player.setGamemode(Server.getInstance().getDefaultGamemode());
                        }
                        player.sendMessage(LevelEventPlusMain.language.translateString("tip_admin_add_success_receiver", playerName));
                    }
                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_admin_add_success", playerName));
                } else {
                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_admin_add_failed", playerName));
                }
                break;
            case REMOVE:
                if (adminConfig.exists("list")) {
                    if (arrayList.contains(playerName)) {
                        arrayList.remove(playerName);
                        adminConfig.set("list", arrayList);
                        adminConfig.save();
                        admins = adminConfig.getRootSection();
                        if (player != null) {
                            Level level = player.getLevel();
                            Object forceGameModeObj = LevelSettingsAPI.getLevelObjectSetting(level.getName(), "World", "ForceGameMode");
                            int forceGamemode = -1;
                            if (forceGameModeObj != null) {
                                forceGamemode = Server.getGamemodeFromString(forceGameModeObj.toString());
                            }
                            player.setGamemode(forceGamemode);
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
        Config operatorConfig = new Config(LevelEventPlusMain.path + "/operators.yml", Config.YAML);
        List<String> arrayList = new ArrayList<>(operatorConfig.getStringList(levelname));
        switch (type) {
            case ADD:
                if (!arrayList.contains(playerName)) {
                    arrayList.add(playerName);
                    operatorConfig.set(levelname, arrayList);
                    operatorConfig.save();
                    operators = operatorConfig.getRootSection();
                    if (player != null) {
                        Level level = player.getLevel();
                        Object forceGameModeObj = LevelSettingsAPI.getLevelObjectSetting(level.getName(), "World", "ForceGameMode");
                        int forceGamemode = -1;
                        if (forceGameModeObj != null) {
                            forceGamemode = Server.getGamemodeFromString(forceGameModeObj.toString());
                        }
                        if (player.getGamemode() == forceGamemode) {
                            player.setGamemode(Server.getInstance().getDefaultGamemode());
                        }
                        player.sendMessage(LevelEventPlusMain.language.translateString("tip_operator_add_success_receiver", playerName, levelname));
                    }
                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_operator_add_success", playerName, levelname));
                } else {
                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_operator_add_failed", playerName, levelname));
                }
                break;
            case REMOVE:
                if (operatorConfig.exists(levelname)) {
                    if (arrayList.contains(playerName)) {
                        arrayList.remove(playerName);
                        operatorConfig.set(levelname, arrayList);
                        operatorConfig.save();
                        operators = operatorConfig.getRootSection();
                        if (player != null) {
                            Level level = player.getLevel();
                            Object forceGameModeObj = LevelSettingsAPI.getLevelObjectSetting(level.getName(), "World", "ForceGameMode");
                            int forceGamemode = -1;
                            if (forceGameModeObj != null) {
                                forceGamemode = Server.getGamemodeFromString(forceGameModeObj.toString());
                            }
                            player.setGamemode(forceGamemode);
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
        return new ArrayList<>(admins.getStringList("list")).contains(p.getName());
    }

    public static boolean isOperator(Player p, Level level) {
        if (p == null) {
            return false;
        }
        if (level == null) {
            return false;
        }
        return new ArrayList<>(operators.getStringList(level.getName())).contains(p.getName());
    }

    public static boolean isWhiteListed(Player p, Level level) {
        if (p == null) {
            return false;
        }
        List<String> list = new ArrayList<>(whitelists.getStringList(level.getName()));
        return list.size() == 0 || list.contains(p.getName());
    }

    public enum OperatePermissionType{
        ADD,
        REMOVE
    }
}
