package glorydark.DLevelEventPlus;

import cn.nukkit.Server;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import glorydark.DLevelEventPlus.event.BlockEventListener;
import glorydark.DLevelEventPlus.event.EntityEventListener;
import glorydark.DLevelEventPlus.event.PlayerEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainClass extends PluginBase implements Listener{
    public static Server server;
    public static String path;

    @Override
    public void onEnable(){
        server = this.getServer();
        path = this.getDataFolder().getPath();
        this.getServer().getLogger().info("DGameRulePlus onEnable");
        this.saveResource("lang-new.yml",false);
        this.getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityEventListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockEventListener(), this);
        this.getServer().getCommandMap().register("",new command("dwp"));
        File file = new File(this.getDataFolder().getPath()+"/worlds/");
        for(File file1:file.listFiles()){
            Config config = new Config(file1);
            fixConfig(config);
        }
    }

    public static void fixConfig(Config config){
        if (!config.exists("World.FarmProtect")) {
            config.set("World.FarmProtect", true);
        }
        if (!config.exists("World.AllExplodes")) {
            config.set("World.AllExplodes", true);
        }
        if (!config.exists("World.TntExplodes")) {
            config.set("World.TntExplodes", true);
        }
        if (!config.exists("World.Pvp")) {
            config.set("World.Pvp", true);
        }
        if (!config.exists("World.WhiteList")) {
            List<String> strings = new ArrayList<>();
            strings.add("All");
            config.set("World.WhiteList", strings);
        }
        if (!config.exists("World.OperatorList")) {
            config.set("World.OperatorList", new ArrayList<>());
        }
        if (!config.exists("World.CommandPreprocess")) {
            config.set("World.CommandPreprocess", true);
        }
        if (!config.exists("World.KeepInventory")) {
            config.set("World.KeepInventory", true);
        }
        if (!config.exists("World.KeepXp")) {
            config.set("World.KeepXp", true);
        }
        //world
        if (!config.exists("Player.AllowOpenChest")) {
            config.set("Player.AllowOpenChest", true);
        }
        if (!config.exists("Player.ChestTrustList")) {
            config.set("Player.ChestTrustList", new ArrayList<>());
        }
        if (!config.exists("Player.CanUseFishingHook")) {
            config.set("Player.CanUseFishingHook", true);
        }
        if (!config.exists("Player.AllowInteractFrameBlock")) {
            config.set("Player.AllowInteractFrameBlock", true);
        }
        if (!config.exists("Player.AllowUseFlintAndSteel")) {
            config.set("Player.AllowUseFlintAndSteel", true);
        }
        if (!config.exists("Player.Sneak")) {
            config.set("Player.Sneak", true);
        }
        if (!config.exists("Player.Sprint")) {
            config.set("Player.Sprint", true);
        }
        if (!config.exists("Player.Fly")) {
            config.set("Player.Fly", true);
        }
        if (!config.exists("Player.Swim")) {
            config.set("Player.Swim", true);
        }
        if (!config.exists("Player.Glide")) {
            config.set("Player.Glide", true);
        }
        if (!config.exists("Player.Jump")) {
            config.set("Player.Jump", true);
        }
        if (!config.exists("Player.Pick")) {
            config.set("Player.Pick", true);
        }
        if (!config.exists("Player.ConsumeItem")) {
            config.set("Player.ConsumeItem", true);
        }
        if (!config.exists("Player.DropItem")) {
            config.set("Player.DropItem", true);
        }
        if (!config.exists("Player.BedEnter")) {
            config.set("Player.BedEnter", true);
        }
        if (!config.exists("Player.Move")) {
            config.set("Player.Move", true);
        }
        if (!config.exists("Player.EatFood")) {
            config.set("Player.EatFood", true);
        }
        if (!config.exists("Player.CommandPreprocess")) {
            config.set("Player.CommandPreprocess", true);
        }
        if (!config.exists("Player.BanCommands")) {
            config.set("Player.BanCommands", new ArrayList<>());
        }
        if (!config.exists("Player.GameModeChange")) {
            config.set("Player.GameModeChange", true);
        }
        if (!config.exists("Player.HungerChange")) {
            config.set("Player.HungerChange", true);
        }
        if (!config.exists("Player.AntiTeleport")) {
            config.set("Player.AntiTeleport", true);
        }
        //player
        if (!config.exists("Entity.Explosion")) {
            config.set("Entity.Explosion", true);
        }
        if (!config.exists("Entity.PortalEnter")) {
            config.set("Entity.PortalEnter", true);
        }
        //entity
        if (!config.exists("Block.AllowPlaceBlock")) {
            config.set("Block.AllowPlaceBlock", true);
        }
        if (!config.exists("Block.AllowBreakBlock")) {
            config.set("Block.AllowBreakBlock", true);
        }
        if (!config.exists("Block.Burn")) {
            config.set("Block.Burn", true);
        }
        if (!config.exists("Block.Ignite")) {
            config.set("Block.Ignite", true);
        }
        if (!config.exists("Block.Fall")) {
            config.set("Block.Fall", true);
        }
        if (!config.exists("Block.Grow")) {
            config.set("Block.Grow", true);
        }
        if (!config.exists("Block.Spread")) {
            config.set("Block.Spread", true);
        }
        if (!config.exists("Block.Form")) {
            config.set("Block.Form", true);
        }
        if (!config.exists("Block.DoorToggle")) {
            config.set("Block.DoorToggle", true);
        }
        if (!config.exists("Block.LeavesDecay")) {
            config.set("Block.LeavesDecay", true);
        }
        if (!config.exists("Block.LiquidFlow")) {
            config.set("Block.LiquidFlow", true);
        }
        if (!config.exists("Block.ItemFrameDropItem")) {
            config.set("Block.ItemFrameDropItem", true);
        }
        if (!config.exists("Block.SignChange")) {
            config.set("Block.SignChange", true);
        }
        if (!config.exists("Block.BlockRedstone")) {
            config.set("Block.BlockRedstone", true);
        }
        config.save();
    }

    // 获取世界的配置，无则返回null
    public static Boolean getLevelBooleanInit(String LevelName, String Key){
        File file = new File(path+"/worlds/"+LevelName+".yml");
        if(file.exists()) {
            Config LevelInit = new Config(path + "/worlds/" + LevelName + ".yml", Config.YAML);
            if (LevelInit.exists(Key)) {
                if (LevelInit.get(Key) == "") {
                    return null;
                }
                return LevelInit.getBoolean(Key);
            } else {
                return null;
            }
        }
        return null;
    }

    public static Boolean getLevelSettingBooleanInit(String LevelName, String Key){
        File file = new File(path+"/worlds/"+LevelName+".yml");
        if(file.exists()) {
            Config LevelInit = new Config(path + "/worlds/" + LevelName + ".yml", Config.YAML);
            if (LevelInit.exists(Key)) {
                if (LevelInit.get(Key) == "") {
                    return false;
                }
                return LevelInit.getBoolean(Key);
            } else {
                return false;
            }
        }
        return false;
    }

    public static void setLevelBooleanInit(String LevelName, String Key, Boolean value){
        Config LevelInit = new Config(path+"/worlds/"+LevelName+".yml",Config.YAML);
        LevelInit.set(Key,value);
        LevelInit.save();
        fixConfig(LevelInit);
    }

    public static List<String> getLevelStringlistInit(String LevelName, String Key){
        File file = new File(path+"/worlds/"+LevelName+".yml");
        if(file.exists()) {
            Config LevelInit = new Config(path + "/worlds/" + LevelName + ".yml", Config.YAML);
            if (LevelInit.exists(Key)) {
                if (LevelInit.get(Key) == "") {
                    return null;
                }
                return new ArrayList<>(LevelInit.getStringList(Key));
            } else {
                return null;
            }
        }
        return null;
    }
}
