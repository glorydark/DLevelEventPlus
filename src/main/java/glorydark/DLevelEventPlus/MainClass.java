package glorydark.DLevelEventPlus;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.Listener;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import glorydark.DLevelEventPlus.event.BlockEventListener;
import glorydark.DLevelEventPlus.event.EntityEventListener;
import glorydark.DLevelEventPlus.event.LevelEventListener;
import glorydark.DLevelEventPlus.event.PlayerEventListener;
import glorydark.DLevelEventPlus.utils.ConfigUtil;
import glorydark.DLevelEventPlus.utils.DefaultConfigUtils;
import glorydark.DLevelEventPlus.utils.NukkitTypeUtils;

import java.io.File;
import java.util.*;

public class MainClass extends PluginBase implements Listener{
    public static Server server;
    public static String path;
    public static MainClass plugin;
    public static HashMap<String, LinkedHashMap<String, Object>> configCache = new HashMap<>();
    public static Map<String, Object> langConfig = new LinkedHashMap<>();
    public static LinkedHashMap<Player, String> selectCache = new LinkedHashMap<>();

    public static DefaultConfigUtils defaultConfigUtils;

    public static boolean show_actionbar_text;

    public static boolean experimental;

    public static boolean compatibleMot;

    @Override
    public void onEnable() {
        server = this.getServer();
        path = this.getDataFolder().getPath();
        plugin = this;
        this.saveResource("config.yml", false);
        this.saveResource("default_20230811.yml", false);
        this.getLogger().info("§a default.yml为默认模板文件，请勿删除世界保护的default.yml，否则后果自负！");
        this.saveResource("lang.yml", false);
        File world_folder = new File(path + "/worlds/");
        world_folder.mkdir();
        File template_folder = new File(path + "/templates/");
        template_folder.mkdir();

        Config config = new Config(path + "/config.yml", Config.YAML);
        show_actionbar_text = config.getBoolean("show_actionbar_text", false);
        experimental = config.getBoolean("experimental", false);
        compatibleMot = NukkitTypeUtils.getNukkitType().equals(NukkitTypeUtils.NukkitType.MOT) || NukkitTypeUtils.getNukkitType().equals(NukkitTypeUtils.NukkitType.PM1E);

        defaultConfigUtils = new DefaultConfigUtils(new Config(path+ "/default_20230811.yml",Config.YAML));

        if (!compatibleMot) {
            Server.getInstance().getScheduler().scheduleRepeatingTask(this, new CheckTask(), 20);
        }
        //加载配置
        loadLang();
        loadAllLevelConfig();
        loadTemplateConfig();
        //注册监听器、指令
        this.getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityEventListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockEventListener(), this);
        this.getServer().getPluginManager().registerEvents(new LevelEventListener(), this);
        this.getServer().getCommandMap().register("",new Command("dwp"));
        this.getLogger().info("DLevelEventPlus onEnable");
    }

    @Override
    public void onDisable() {
        saveAllConfig();
        configCache.clear();
    }

    public static void loadAllLevelConfig() {
        configCache = new LinkedHashMap<>();
        File file = new File(path+"/worlds/");
        File[] listFiles = file.listFiles();
        plugin.getLogger().info("开始加载地图配置");
        if(listFiles != null) {
            for (File file1 : listFiles) {
                if(DefaultConfigUtils.isYaml(file1.getName())) {
                    Config config = new Config(file1);
                    defaultConfigUtils.checkAll(file1.getName(), config); // 检测配置
                    String levelName = file1.getName().split("\\.")[0];
                    Level level = Server.getInstance().getLevelByName(levelName);
                    if(level == null) {
                        if(Server.getInstance().loadLevel(levelName)) {
                            level = Server.getInstance().getLevelByName(levelName);
                        }else{
                            plugin.getLogger().warning("Can not load level for "+levelName);
                            continue;
                        }
                    }
                    plugin.getLogger().info("Loading protection rules for the level [" + levelName + "]");
                    configCache.put(levelName, (LinkedHashMap<String, Object>) config.getAll());
                    if(!getLevelSettingBooleanInit(levelName, "World", "TimeFlow")) {
                        level.getGameRules().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                    }
                    Object weather = getLevelSettingInit(levelName, "World", "Weather");
                    if(weather != null) {
                        switch (String.valueOf(weather).toLowerCase()) {
                            case "clear":
                                level.setRaining(false);
                                level.setThundering(false);
                                level.setRainTime(12000 * 20);
                                level.setThunderTime(12000 * 20);
                                break;
                            case "rain":
                                level.setRaining(true);
                                level.setRainTime(12000 * 20);
                                break;
                            case "thunder":
                                level.setThundering(true);
                                level.setRainTime(12000 * 20);
                                level.setThunderTime(12000 * 20);
                                break;
                        }
                    }
                    LinkedHashMap<String, Object> gamerules = (LinkedHashMap<String, Object>) configCache.get(levelName).getOrDefault("GameRule", new LinkedHashMap<>());
                    if(gamerules.size() > 0) {
                        for(String item: gamerules.keySet()) {
                            Optional<GameRule> options = GameRule.parseString(item);
                            if(options.isPresent()) {
                                Object object = gamerules.get(item);
                                if(object instanceof Boolean) {
                                    level.getGameRules().setGameRule(options.get(), (Boolean) object);
                                }else if (object instanceof Integer) {
                                    level.getGameRules().setGameRule(options.get(), (Integer) object);
                                }else if (object instanceof Float) {
                                    level.getGameRules().setGameRule(options.get(), (Float) object);
                                }
                                plugin.getLogger().info("§aSetting gamerule §e"+item+"§a to §e"+ object.toString() +"§a!");
                            }else{
                                plugin.getLogger().info("§aSetting gamerule §e"+item+"§a failed. Caused by wrong format of gamerule name!");
                            }
                        }
                        plugin.getLogger().info("§aSuccessfully loaded all gamerule!");
                    }else{
                        plugin.getLogger().info("§aSuccessfully loaded §e0§a gamerule!");
                    }
                    plugin.getLogger().info("加载世界【" + levelName + "】配置成功");
                }
            }
        }
        plugin.getLogger().info("加载地图配置完成！");
    }

    public static void loadTemplateConfig() {
        ConfigUtil.TemplateCache = new LinkedHashMap<>();
        File file = new File(path+"/templates/");
        File[] listFiles = file.listFiles();
        plugin.getLogger().info("开始加载模板配置");
        if(listFiles != null) {
            for (File file1 : listFiles) {
                if(DefaultConfigUtils.isYaml(file1.getName())) {
                    Config config = new Config(file1);
                    defaultConfigUtils.checkAll(file1.getName(), config);
                    String templateName = file1.getName().split("\\.")[0];
                    plugin.getLogger().info("加载模板【" + templateName + "】配置成功");
                    ConfigUtil.TemplateCache.put(templateName, (LinkedHashMap<String, Object>) config.getAll());
                }
            }
        }
        plugin.getLogger().info("加载模板配置完成！");
    }

    public static void loadLang() {
        plugin.getLogger().info("开始加载语言配置");
        langConfig.clear();
        Config langCfg = new Config(MainClass.path + "/lang.yml", Config.YAML);
        langConfig = langCfg.getAll();
        plugin.getLogger().info("加载语言完成！");
    }

    public static void saveAllConfig() {
        plugin.getLogger().alert("开始保存配置");
        for(String s:MainClass.configCache.keySet()) {
            Config config = new Config(path+"/worlds/"+s+".yml",Config.YAML);
            config.setAll(MainClass.configCache.get(s));
            config.save();
            plugin.getLogger().info("保存世界【"+s+"】配置成功");
        }

        for(String s: ConfigUtil.TemplateCache.keySet()) {
            Config config = new Config(path+"/templates/"+s+".yml",Config.YAML);
            config.setAll(ConfigUtil.TemplateCache.get(s));
            config.save();
            plugin.getLogger().info("保存模板【"+s+"】配置成功");
        }
        plugin.getLogger().alert("保存完成！");
    }

    // 获取世界的配置，无则返回null
    public static Boolean getLevelBooleanInit(String LevelName, String key, String subKey) {
        if(configCache.containsKey(LevelName)) {
            Map<String, Object> map = configCache.get(LevelName);
            if(map.containsKey(key)) {
                Map<String, Object> keyMap = (Map<String, Object>) map.get(key);
                if(keyMap.containsKey(subKey)) {
                    return (Boolean) keyMap.get(subKey);
                }
            }
        }
        return null;
    }

    public static Boolean getLevelSettingBooleanInit(String LevelName, String key, String subKey) {
        if(configCache.containsKey(LevelName)) {
            Map<String, Object> map = configCache.get(LevelName); // world的全部配置
            if(map.containsKey(key)) {
                Map<String, Object> keyMap = (Map<String, Object>) map.get(key); //键下的所有配置
                if(keyMap.containsKey(subKey)) {
                    return (Boolean) keyMap.get(subKey);
                }
            }
        }
        return false;
    }

    public static Object getLevelSettingInit(String LevelName, String key, String subKey) {
        if(configCache.containsKey(LevelName)) {
            Map<String, Object> map = configCache.get(LevelName); // world的全部配置
            if(map.containsKey(key)) {
                Map<String, Object> keyMap = (Map<String, Object>) map.get(key); //键下的所有配置
                if(keyMap.containsKey(subKey)) {
                    return keyMap.get(subKey);
                }
            }
        }
        return false;
    }

    public static void setLevelInit(String LevelName, String key, String subKey, Object value) {
        LinkedHashMap<String, Object> map;
        if(configCache.containsKey(LevelName)) {
            map = configCache.get(LevelName);
            LinkedHashMap<String, Object> keyMap;
            if(map.containsKey(key)) {
                keyMap = (LinkedHashMap<String, Object>) map.get(key);
            }else{
                keyMap = new LinkedHashMap<>();
            }
            keyMap.put(subKey, value);
            map.put(key, keyMap);
        }else{
            map = new LinkedHashMap<>();
            LinkedHashMap<String, Object> keyMap = new LinkedHashMap<>();
            keyMap.put(subKey,value);
            map.put(key,keyMap);
        }
        configCache.put(LevelName, map);
    }

    public static List<String> getLevelStringListInit(String LevelName, String key, String subKey) {
        if(configCache.containsKey(LevelName)) {
            Map<String, Object> map = configCache.get(LevelName); // world的全部配置
            if(map.containsKey(key)) {
                Map<String, Object> keyMap = (Map<String, Object>) map.get(key); //键下的所有配置
                if(keyMap.containsKey(subKey)) {
                    return (List<String>) keyMap.get(subKey);
                }
            }
        }
        return new ArrayList<>();
    }
}
