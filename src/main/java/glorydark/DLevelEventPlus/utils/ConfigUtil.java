package glorydark.DLevelEventPlus.utils;


import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;
import glorydark.DLevelEventPlus.MainClass;

import java.io.File;
import java.util.*;

public class ConfigUtil {
    public static HashMap<String, LinkedHashMap<String, Object>> templateCache = new HashMap<>();

    public static void whiteList(CommandSender sender, int type, String playerName, String levelname) {
        if (Server.getInstance().lookupName(playerName).isPresent()) {
            sender.sendMessage(MainClass.language.translateString("tip_generic_playerNotFound", playerName));
            return;
        }
        Config worldcfg = new Config(MainClass.path + "/whitelists.yml", Config.YAML);
        List<String> arrayList = new ArrayList<>(worldcfg.getStringList(levelname));
        Player player = Server.getInstance().getPlayer(playerName);
        switch (type) {
            case 0:
                if (!arrayList.contains(playerName)) {
                    arrayList.add(playerName);
                    worldcfg.set(levelname, arrayList);
                    worldcfg.save();
                    if (player != null) {
                        player.sendMessage(MainClass.language.translateString("tip_whitelist_add_success_receiver", playerName, levelname));
                    }
                    sender.sendMessage(MainClass.language.translateString("tip_whitelist_add_success", playerName, levelname));
                } else {
                    sender.sendMessage(MainClass.language.translateString("tip_whitelist_add_failed", playerName, levelname));
                }
                break;
            case 1:
                if (arrayList.contains(playerName)) {
                    arrayList.remove(playerName);
                    worldcfg.set(levelname, arrayList);
                    worldcfg.save();
                    if (player != null) {
                        player.sendMessage(MainClass.language.translateString("tip_whitelist_del_success_receiver", playerName, levelname));
                    }
                    sender.sendMessage(MainClass.language.translateString("tip_whitelist_del_success", playerName, levelname));
                } else {
                    sender.sendMessage(MainClass.language.translateString("tip_whitelist_del_failed", playerName, levelname));
                }
                break;
        }
    }

    public static void adminList(CommandSender sender, int type, String playerName) {
        if (Server.getInstance().lookupName(playerName).isPresent()) {
            sender.sendMessage("§c[DLevelEventPlus] Can not find player: " + playerName);
            return;
        }
        Config worldcfg = new Config(MainClass.path + "/admins.yml", Config.YAML);
        Player player = Server.getInstance().getPlayer(playerName);
        List<String> arrayList = new ArrayList<>(worldcfg.getStringList("list"));
        switch (type) {
            case 0:
                if (!arrayList.contains(playerName)) {
                    arrayList.add(playerName);
                    worldcfg.set("list", arrayList);
                    worldcfg.save();
                    if (player != null) {
                        player.sendMessage(MainClass.language.translateString("tip_admin_add_success_receiver", playerName));
                    }
                    sender.sendMessage(MainClass.language.translateString("tip_admin_add_success", playerName));
                } else {
                    sender.sendMessage(MainClass.language.translateString("tip_admin_add_failed", playerName));
                }
                break;
            case 1:
                if (worldcfg.exists("list")) {
                    if (arrayList.contains(playerName)) {
                        arrayList.remove(playerName);
                        worldcfg.set("list", arrayList);
                        worldcfg.save();
                        if (player != null) {
                            player.sendMessage(MainClass.language.translateString("tip_admin_del_success_receiver", playerName));
                        }
                        sender.sendMessage(MainClass.language.translateString("tip_admin_del_success", playerName));
                    }
                } else {
                    sender.sendMessage(MainClass.language.translateString("tip_admin_del_failed", playerName));
                }
                break;
        }
    }

    public static void operatorList(CommandSender sender, int type, String playerName, String levelname) {
        if (Server.getInstance().lookupName(playerName).isPresent()) {
            sender.sendMessage("§c[DLevelEventPlus] Can not find player: " + playerName);
            return;
        }
        Player player = Server.getInstance().getPlayer(playerName);
        Config worldcfg = new Config(MainClass.path + "/operators.yml", Config.YAML);
        List<String> arrayList = new ArrayList<>(worldcfg.getStringList(levelname));
        switch (type) {
            case 0:
                if (!arrayList.contains(playerName)) {
                    arrayList.add(playerName);
                    worldcfg.set(levelname, arrayList);
                    worldcfg.save();
                    if (player != null) {
                        player.setAllowModifyWorld(true);
                        player.sendMessage(MainClass.language.translateString("tip_operator_add_success_receiver", playerName, levelname));
                    }
                    sender.sendMessage(MainClass.language.translateString("tip_operator_add_success", playerName, levelname));
                } else {
                    sender.sendMessage(MainClass.language.translateString("tip_operator_add_failed", playerName, levelname));
                }
                break;
            case 1:
                if (worldcfg.exists(levelname)) {
                    if (arrayList.contains(playerName)) {
                        arrayList.remove(playerName);
                        worldcfg.set(levelname, arrayList);
                        worldcfg.save();
                        if (player != null) {
                            player.sendMessage(MainClass.language.translateString("tip_operator_del_success_receiver", playerName, levelname));
                        }
                        sender.sendMessage(MainClass.language.translateString("tip_operator_del_success", playerName, levelname));
                    } else {
                        sender.sendMessage(MainClass.language.translateString("tip_operator_del_failed", playerName, levelname));
                    }
                }
                break;
        }
    }

    public static boolean isAdmin(Player p) {
        if (p == null) {
            return false;
        }
        File file = new File(MainClass.path + "/admins.yml");
        if (file.exists()) {
            Config worldcfg = new Config(MainClass.path + "/admins.yml", Config.YAML);
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
        File file = new File(MainClass.path + "/operators.yml");
        if (file.exists()) {
            Config worldcfg = new Config(MainClass.path + "/operators.yml", Config.YAML);
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
        File file = new File(MainClass.path + "/whitelists.yml");
        if (file.exists()) {
            Config worldcfg = new Config(MainClass.path + "/whitelists.yml", Config.YAML);
            if (worldcfg.exists(level.getName())) {
                return worldcfg.getStringList(level.getName()).contains(p.getName());
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static void setTemplateInit(String ConfigName, String key, String subKey, Object value) {
        if (templateCache.containsKey(ConfigName)) {
            LinkedHashMap<String, Object> keyMap = templateCache.get(ConfigName);
            if (keyMap != null && keyMap.containsKey(key)) {
                Map<String, Object> obj = (Map<String, Object>) keyMap.get(key);
                if (obj != null) {
                    obj.put(subKey, value);
                    keyMap.put(key, obj);
                    templateCache.put(ConfigName, keyMap);
                } else {
                    MainClass.plugin.getLogger().warning(MainClass.language.translateString("tip_save_template_failed", subKey));
                }
            }
        }
    }

    public static Object getTemplateInit(String ConfigName, String key, String subKey) {
        if (templateCache.containsKey(ConfigName)) {
            Map<String, Object> keyMap = templateCache.get(ConfigName);
            if (keyMap != null && keyMap.containsKey(key)) {
                Map<String, Object> subkeyMap = (Map<String, Object>) keyMap.get(key); //键下的所有配置
                if (subkeyMap.containsKey(subKey)) {
                    return subkeyMap.get(subKey);
                }
            }
        }
        return false;
    }

    public static Boolean getTemplateBooleanInit(String ConfigName, String key, String subKey) {
        if (templateCache.containsKey(ConfigName)) {
            Map<String, Object> keyMap = templateCache.get(ConfigName);
            if (keyMap != null && keyMap.containsKey(key)) {
                Map<String, Object> subkeyMap = (Map<String, Object>) keyMap.get(key); //键下的所有配置
                if (subkeyMap.containsKey(subKey)) {
                    return (Boolean) subkeyMap.get(subKey);
                }
            }
        }
        return false;
    }

    public static List<String> getTemplateListInit(String ConfigName, String key, String subKey) {
        if (templateCache.containsKey(ConfigName)) {
            Map<String, Object> keyMap = templateCache.get(ConfigName);
            if (keyMap != null && keyMap.containsKey(key)) {
                Map<String, Object> subkeyMap = (Map<String, Object>) keyMap.get(key); //键下的所有配置
                if (subkeyMap.containsKey(subKey)) {
                    return (List<String>) subkeyMap.get(subKey);
                }
            }
        }
        return new ArrayList<>();
    }
}
