package glorydark.DLevelEventPlus.utils;


import cn.nukkit.Player;
import cn.nukkit.level.Level;
import glorydark.DLevelEventPlus.MainClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Config {
    public static boolean createWorldConfig(String world){
        File file = new File(MainClass.path+"/worlds/"+world+".yml");
        if(!file.exists()) {
            cn.nukkit.utils.Config worldcfg = new cn.nukkit.utils.Config(MainClass.path + "/worlds/" + world + ".yml", cn.nukkit.utils.Config.YAML);
            if (worldcfg.exists("World") && worldcfg.exists("Player") && worldcfg.exists("Entity") && worldcfg.exists("Block")) {
                return false;
            }
            if (!worldcfg.exists("World")) {
                worldcfg.set("World.FarmProtect", true);
                worldcfg.set("World.AllExplodes", true);
                worldcfg.set("World.TntExplodes", true);
                worldcfg.set("World.Pvp", true);
                worldcfg.set("World.KeepInventory", true);
                worldcfg.set("World.KeepXp", true);
            }

            if (!worldcfg.exists("Player")) {
                worldcfg.set("Player.AllowOpenChest", true);
                worldcfg.set("Player.ChestTrustList", new ArrayList<>());
                worldcfg.set("Player.CanUseFishingHook", true);
                worldcfg.set("Player.AllowInteractFrameBlock", true);
                worldcfg.set("Player.AllowUseFlintAndSteel", true);
                worldcfg.set("Player.Sneak", true);
                worldcfg.set("Player.Sprint", true);
                worldcfg.set("Player.Fly", true);
                worldcfg.set("Player.Swim", true);
                worldcfg.set("Player.Glide", true);
                worldcfg.set("Player.Jump", true);
                worldcfg.set("Player.Pick", true);
                worldcfg.set("Player.ConsumeItem", true);
                worldcfg.set("Player.DropItem", true);
                worldcfg.set("Player.BedEnter", true);
                worldcfg.set("Player.Move", true);
                worldcfg.set("Player.EatFood", true);
                worldcfg.set("Player.CommandPreprocess", true);
                worldcfg.set("Player.BanCommands", new ArrayList<>());
                worldcfg.set("Player.GameModeChange", true);
                worldcfg.set("Player.HungerChange", false);
                worldcfg.set("Player.AntiTeleport", false);
            }

            if (!worldcfg.exists("Entity")) {
                worldcfg.set("Entity.Explosion", true);
                worldcfg.set("Entity.PortalEnter", true);
            }

            if (!worldcfg.exists("Block")) {
                worldcfg.set("Block.AllowPlaceBlock", true);
                worldcfg.set("Block.AllowBreakBlock", true);
                worldcfg.set("Block.AntiPlaceBlocks", new ArrayList<>());
                worldcfg.set("Block.AntiBreakBlocks", new ArrayList<>());
                worldcfg.set("Block.Burn", true);
                worldcfg.set("Block.Ignite", true);
                worldcfg.set("Block.Fall", true);
                worldcfg.set("Block.Grow", true);
                worldcfg.set("Block.Spread", true);
                worldcfg.set("Block.Form", true);
                worldcfg.set("Block.DoorToggle", true);
                worldcfg.set("Block.LeavesDecay", true);
                worldcfg.set("Block.LiquidFlow", true);
                worldcfg.set("Block.ItemFrameDropItem", true);
                worldcfg.set("Block.SignChange", true);
                worldcfg.set("Block.BlockRedstone", true);

            }
            worldcfg.save();
            return true;
        }
        return false;
    }

    public static boolean whiteList(int type, String name, String levelname){
        cn.nukkit.utils.Config worldcfg = new cn.nukkit.utils.Config(MainClass.path + "/whitelists.yml", cn.nukkit.utils.Config.YAML);
        switch (type) {
            case 0:
                if (worldcfg.exists(levelname)) {
                    List<String> arrayList = new ArrayList<>(worldcfg.getStringList(levelname));
                    if (!arrayList.contains(name)) {
                        arrayList.add(name);
                        worldcfg.set(levelname, arrayList);
                        worldcfg.save();
                        return true;
                    }
                } else {
                    List<String> arrayList = new ArrayList<>();
                    arrayList.add(name);
                    worldcfg.set(levelname, arrayList);
                    worldcfg.save();
                    return true;
                }
            case 1:
                if (worldcfg.exists(levelname)) {
                    List<String> arrayList = new ArrayList<>(worldcfg.getStringList(levelname));
                    if (arrayList.contains(name)) {
                        arrayList.remove(name);
                        worldcfg.set(levelname, arrayList);
                        worldcfg.save();
                        return true;
                    }
                }
            default:
                MainClass.fixConfig(worldcfg);
                break;
        }
        return false;
    }

    public static boolean adminList(int type, String name){
        cn.nukkit.utils.Config worldcfg = new cn.nukkit.utils.Config(MainClass.path + "/admins.yml", cn.nukkit.utils.Config.YAML);
        switch (type) {
            case 0:
                if (worldcfg.exists("list")) {
                    List<String> arrayList = new ArrayList<>(worldcfg.getStringList("list"));
                    if (!arrayList.contains(name)) {
                        arrayList.add(name);
                        worldcfg.set("list", arrayList);
                        worldcfg.save();
                        return true;
                    }
                } else {
                    List<String> arrayList = new ArrayList<>();
                    arrayList.add(name);
                    worldcfg.set("list", arrayList);
                    worldcfg.save();
                    return true;
                }
            case 1:
                if (worldcfg.exists("list")) {
                    List<String> arrayList = new ArrayList<>(worldcfg.getStringList("list"));
                    if (arrayList.contains(name)) {
                        arrayList.remove(name);
                        worldcfg.set("list", arrayList);
                        worldcfg.save();
                        return true;
                    }
                }
            default:
                MainClass.fixConfig(worldcfg);
                break;
        }
        return false;
    }

    public static boolean operatorList(int type, String name, String levelname){
        cn.nukkit.utils.Config worldcfg = new cn.nukkit.utils.Config(MainClass.path + "/operators.yml", cn.nukkit.utils.Config.YAML);
        switch (type) {
            case 0:
                if (worldcfg.exists(levelname)) {
                    List<String> arrayList = new ArrayList<>(worldcfg.getStringList(levelname));
                    if (!arrayList.contains(name)) {
                        arrayList.add(name);
                        worldcfg.set(levelname, arrayList);
                        worldcfg.save();
                        return true;
                    }
                } else {
                    List<String> arrayList = new ArrayList<>();
                    arrayList.add(name);
                    worldcfg.set(levelname, arrayList);
                    worldcfg.save();
                    return true;
                }
            case 1:
                if (worldcfg.exists(levelname)) {
                    List<String> arrayList = new ArrayList<>(worldcfg.getStringList(levelname));
                    if (arrayList.contains(name)) {
                        arrayList.remove(name);
                        worldcfg.set(levelname, arrayList);
                        worldcfg.save();
                        return true;
                    }
                }
            default:
                MainClass.fixConfig(worldcfg);
                break;
        }
        return false;
    }

    public static boolean isAdmin(Player p) {
        File file = new File(MainClass.path + "/admins.yml");
        if (file.exists()) {
            cn.nukkit.utils.Config worldcfg = new cn.nukkit.utils.Config(MainClass.path + "/admins.yml", cn.nukkit.utils.Config.YAML);
            if(worldcfg.exists("list")){
                if(worldcfg.getStringList("list").contains(p.getName())){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isOperator(Player p, Level level) {
        File file = new File(MainClass.path + "/operators.yml");
        if (file.exists()) {
            cn.nukkit.utils.Config worldcfg = new cn.nukkit.utils.Config(MainClass.path + "/operators.yml", cn.nukkit.utils.Config.YAML);
            if (worldcfg.get(level.getName()) != null) {
                return worldcfg.getStringList(level.getName()).contains(p.getName());
            }
        }
        return false;
    }

    public static boolean isWhiteListed(Player p, Level level){
        File file = new File(MainClass.path+"/whitelists.yml");
        if(file.exists()) {
            cn.nukkit.utils.Config worldcfg = new cn.nukkit.utils.Config(MainClass.path + "/whitelists.yml", cn.nukkit.utils.Config.YAML);
            if (worldcfg.exists(level.getName())) {
                return worldcfg.getStringList(level.getName()).contains(p.getName()) || worldcfg.getStringList(level.getName()).contains("All");
            }
        }
        return true;
    }

    public static String getLang(String text){
        if(MainClass.langConfig.containsKey(text)) {
            return (String) MainClass.langConfig.get(text);
        }else{
            return "Translation Not Found!";
        }
    }

    public static List<String> getLangList(String text){
        if(MainClass.langConfig.containsKey(text)) {
            return (List<String>) MainClass.langConfig.get(text);
        }else{
            return new ArrayList<>();
        }
    }
}
