package glorydark.DLevelEventPlus;

import cn.nukkit.Server;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import glorydark.DLevelEventPlus.event.BlockEventListener;
import glorydark.DLevelEventPlus.event.EntityEventListener;
import glorydark.DLevelEventPlus.event.PlayerEventListener;

import java.io.File;
import java.util.*;

public class MainClass extends PluginBase implements Listener{
    public static Server server;
    public static String path;
    public static MainClass plugin;
    public static HashMap<String, LinkedHashMap<String, Object>> configCache = new HashMap<>();
    public static Map<String, Object> langConfig = new TreeMap<>();

    @Override
    public void onEnable(){
        server = this.getServer();
        path = this.getDataFolder().getPath();
        plugin = this;
        this.getServer().getLogger().info("DLevelEventPlus onEnable");
        this.saveResource("lang-new.yml",false);
        this.getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityEventListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockEventListener(), this);
        this.getServer().getCommandMap().register("",new Command("dwp"));
        File file = new File(path+"/worlds/");
        file.mkdir();
        File[] listFiles = file.listFiles();
        if(listFiles != null) {
            for (File file1 : listFiles) {
                Config config = new Config(file1);
                fixConfig(config);
            }
        }
        UpdateEngine.updateConfig();
        //加载配置
        loadLang();
        loadAllLevelConfig();
    }

    @Override
    public void onDisable() {
        saveAllLevelConfig();
        configCache.clear();
    }

    public static void fixConfig(Config config){
        if(config == null){ return;}
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
        if (!config.exists("Block.AntiPlaceBlocks")) {
            config.set("Block.AntiPlaceBlocks", new ArrayList<>());
        }
        if (!config.exists("Block.AntiBreakBlocks")) {
            config.set("Block.AntiBreakBlocks", new ArrayList<>());
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

    public static void loadAllLevelConfig(){
        configCache.clear();
        File file = new File(path+"/worlds/");
        File[] listFiles = file.listFiles();
        plugin.getLogger().info("开始加载地图配置");
        if(listFiles != null) {
            for (File file1 : listFiles) {
                Config config = new Config(file1);
                String levelName = file1.getName().split("\\.")[0];
                plugin.getLogger().info("加载世界【"+levelName+"】配置成功");
                configCache.put(levelName,(LinkedHashMap<String, Object>) config.getAll());
            }
        }
        plugin.getLogger().info("加载地图配置完成！");
    }

    public static void loadLang(){
        plugin.getLogger().info("开始加载语言配置");
        langConfig.clear();
        cn.nukkit.utils.Config langCfg = new cn.nukkit.utils.Config(MainClass.path + "/lang-new.yml", cn.nukkit.utils.Config.YAML);
        langConfig = langCfg.getAll();
        plugin.getLogger().info("加载语言完成！");
    }

    public static void saveAllLevelConfig(){
        File file = new File(path+"/worlds/");
        File[] listFiles = file.listFiles();
        plugin.getLogger().alert("开始保存配置");
        if(listFiles != null) {
            for (File file1 : listFiles) {
                Config config = new Config(file1);
                String levelName = file1.getName().split("\\.")[0];
                plugin.getLogger().alert("保存世界【"+levelName+"】配置成功");
                config.setAll(configCache.get(levelName));
                config.save();
            }
        }
        plugin.getLogger().alert("保存完成！");
    }

    // 获取世界的配置，无则返回null
    public static Boolean getLevelBooleanInit(String LevelName, String key, String subKey){
        if(configCache.containsKey(LevelName)){
            Map<String, Object> map = configCache.get(LevelName);
            if(map.containsKey(key)){
                Map<String, Object> keyMap = (Map<String, Object>) map.get(key);
                if(keyMap.containsKey(subKey)) {
                    return (Boolean) keyMap.get(subKey);
                }
            }
        }
        return null;
    }

    public static Boolean getLevelSettingBooleanInit(String LevelName, String key, String subKey){
        if(configCache.containsKey(LevelName)){
            Map<String, Object> map = configCache.get(LevelName); // world的全部配置
            if(map.containsKey(key)){
                Map<String, Object> keyMap = (Map<String, Object>) map.get(key); //键下的所有配置
                if(keyMap.containsKey(subKey)) {
                    return (Boolean) keyMap.get(subKey);
                }
            }
        }
        return false;
    }

    public static void setLevelBooleanInit(String LevelName, String key, String subKey, Boolean value){
        Map<String, Object> map;
        if(configCache.containsKey(LevelName)){
            map = configCache.get(LevelName);
            Map<String, Object> keyMap;
            if(map.containsKey(key)){
                keyMap = (Map<String, Object>) map.get(key);
            }else{
                keyMap = new TreeMap<>();
            }
            keyMap.put(subKey, value);
            map.put(key, keyMap);
        }else{
            map = new TreeMap<>();
            Map<String, Object> keyMap = new TreeMap<>();
            keyMap.put(subKey,value);
            map.put(key,keyMap);
        }
        configCache.put(LevelName,(LinkedHashMap<String, Object>) map);
    }

    public static List<String> getLevelStringListInit(String LevelName, String key, String subKey){
        if(configCache.containsKey(LevelName)){
            Map<String, Object> map = configCache.get(LevelName); // world的全部配置
            if(map.containsKey(key)){
                Map<String, Object> keyMap = (Map<String, Object>) map.get(key); //键下的所有配置
                if(keyMap.containsKey(subKey)) {
                    return (List<String>) keyMap.get(subKey);
                }
            }
        }
        return new ArrayList<>();
    }
}
