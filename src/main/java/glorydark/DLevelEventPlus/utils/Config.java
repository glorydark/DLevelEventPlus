package glorydark.DLevelEventPlus.utils;


import cn.nukkit.Player;
import glorydark.DLevelEventPlus.MainClass;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public static boolean createWorldConfig(String world){
        cn.nukkit.utils.Config worldcfg = new cn.nukkit.utils.Config(MainClass.path + "/worlds/" + world + ".yml", cn.nukkit.utils.Config.YAML);
        if(worldcfg.exists("World") && worldcfg.exists("Player") && worldcfg.exists("Entity") && worldcfg.exists("Block")){ return false; }
        if(!worldcfg.exists("World")){
            worldcfg.set("World.FarmProtect", true);
            worldcfg.set("World.AllExplodes", true);
            worldcfg.set("World.TntExplodes", true);
            worldcfg.set("World.Pvp", true);
            worldcfg.set("World.WhiteList", new ArrayList<>());
            worldcfg.set("World.OperatorList", new ArrayList<>());
            worldcfg.set("World.CommandPreprocess", true);
            worldcfg.set("World.KeepInventory", true);
            worldcfg.set("World.KeepXp", true);
        }

        if(!worldcfg.exists("Player")){
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

        if(!worldcfg.exists("Entity")){
            worldcfg.set("Entity.Explosion", true);
            worldcfg.set("Entity.PortalEnter", true);
        }

        if(!worldcfg.exists("Block")){
            worldcfg.set("Block.AllowPlaceBlock", true);
            worldcfg.set("Block.AllowBreakBlock", true);
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

    public static boolean WhiteList(int type,String name,String levelname){
        cn.nukkit.utils.Config worldcfg = new cn.nukkit.utils.Config(MainClass.path + "/worlds/" + levelname + ".yml", cn.nukkit.utils.Config.YAML);
        switch (type) {
            case 0:
                if (worldcfg.exists("World.WhiteList")) {
                    List<String> arrayList = new ArrayList<>(worldcfg.getStringList("World.WhiteList"));
                    if(!arrayList.contains(name)) {
                        arrayList.add(name);
                        worldcfg.set("World.WhiteList", arrayList);
                        worldcfg.save();
                        return true;
                    }
                }else{
                    List<String> arrayList = new ArrayList<>();
                    arrayList.add(name);
                    worldcfg.set("World.WhiteList",arrayList);
                    worldcfg.save();
                    return true;
                }
            case 1:
                if (worldcfg.exists("World.WhiteList")) {
                    List<String> arrayList = new ArrayList<>(worldcfg.getStringList("World.WhiteList"));
                    if(arrayList.contains(name)) {
                        arrayList.remove(name);
                        worldcfg.set("World.WhiteList", arrayList);
                        worldcfg.save();
                        return true;
                    }
                }
        }
        return false;
    }

    public static boolean OperatorList(int type,String name,String levelname){
        cn.nukkit.utils.Config worldcfg = new cn.nukkit.utils.Config(MainClass.path + "/worlds/" + levelname + ".yml", cn.nukkit.utils.Config.YAML);
        switch (type) {
            case 0:
                if (worldcfg.exists("World.OperatorList")) {
                    List<String> arrayList = new ArrayList<>(worldcfg.getStringList("World.OperatorList"));
                    if(!arrayList.contains(name)) {
                        arrayList.add(name);
                        worldcfg.set("World.OperatorList", arrayList);
                        worldcfg.save();
                        return true;
                    }
                }else{
                    List<String> arrayList = new ArrayList<>();
                    worldcfg.set("World.OperatorList",arrayList.add(name));
                    worldcfg.save();
                    return true;
                }
            case 1:
                if (worldcfg.exists("World.OperatorList")) {
                    List<String> arrayList = new ArrayList<>(worldcfg.getStringList("World.OperatorList"));
                    if (arrayList.contains(name)) {
                        arrayList.remove(name);
                        worldcfg.set("World.OperatorList", arrayList);
                        worldcfg.save();
                        return true;
                    }
                }
        }
        return false;
    }

    public static boolean isOperateListed(Player p){
        cn.nukkit.utils.Config worldcfg = new cn.nukkit.utils.Config(MainClass.path + "/worlds/" + p.getLevel().getName() + ".yml", cn.nukkit.utils.Config.YAML);
        if (worldcfg.get("World.OperatorList") != null) {
            if(worldcfg.getStringList("World.OperatorList").contains(p.getName())){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public static boolean isWhiteListed(Player p){
        cn.nukkit.utils.Config worldcfg = new cn.nukkit.utils.Config(MainClass.path + "/worlds/" + p.getLevel().getName() + ".yml", cn.nukkit.utils.Config.YAML);
        if (worldcfg.exists("World.WhiteList")) {
            if(worldcfg.getStringList("World.WhiteList").contains(p.getName()) || worldcfg.getStringList("World.WhiteList").contains("All")){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public static String getLang(String text){
        cn.nukkit.utils.Config langcfg = new cn.nukkit.utils.Config(MainClass.path + "/lang-new.yml", cn.nukkit.utils.Config.YAML);
        return langcfg.getString(text);
    }
}
