package glorydark.DLevelEventPlus;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.Listener;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import glorydark.DLevelEventPlus.event.*;
import glorydark.DLevelEventPlus.gui.protection.ProtectionEntryMain;
import glorydark.DLevelEventPlus.utils.ConfigUtil;
import glorydark.DLevelEventPlus.utils.DefaultConfigUtils;
import glorydark.DLevelEventPlus.utils.Language;

import java.io.File;
import java.util.*;

public class MainClass extends PluginBase implements Listener {

    public static String path;
    public static MainClass plugin;
    public static HashMap<String, LinkedHashMap<String, Object>> configCache = new HashMap<>();

    public static Language language;
    public static LinkedHashMap<Player, String> selectCache = new LinkedHashMap<>();

    public static DefaultConfigUtils defaultConfigUtils;

    public static boolean show_actionbar_text;

    public static boolean experimental; // Currently not at work

    public final List<String> enabledLanguage = List.of(new String[]{"chs", "eng"});

    @Override
    public void onEnable() {
        path = this.getDataFolder().getPath();
        plugin = this;
        this.saveResource("config.yml", false);
        this.saveResource("default_20230811.yml", false);

        // Loading Language File
        this.saveResource("languages/chs/lang.properties", true);
        this.saveResource("languages/eng/lang.properties", true);
        // Creating Necessary Dictionaries
        File world_folder = new File(path + "/worlds/");
        world_folder.mkdir();
        File template_folder = new File(path + "/templates/");
        template_folder.mkdir();

        Config config = new Config(path + "/config.yml", Config.YAML);
        String defaultLang = config.getString("languages", Server.getInstance().getLanguage().getLang());
        if (!enabledLanguage.contains(defaultLang)) {
            defaultLang = "eng";
        }
        // Loading Language
        language = new Language(new File(MainClass.path + "/languages/" + defaultLang + "/lang.properties"));
        // Then others
        ProtectionEntryMain.loadDefaultEntries();
        this.getLogger().info(language.translateString("tip_alert_defaultFile"));
        show_actionbar_text = config.getBoolean("show_actionbar_text", false);
        experimental = config.getBoolean("experimental", false);
        defaultConfigUtils = new DefaultConfigUtils(new Config(path + "/default_20230811.yml", Config.YAML));
        loadAllLevelConfig();
        loadTemplateConfig();
        // Register Listeners
        this.getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityEventListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockEventListener(), this);
        this.getServer().getPluginManager().registerEvents(new LevelEventListener(), this);
        this.getServer().getCommandMap().register("", new Command("dwp"));
    }

    @Override
    public void onDisable() {
        saveAllConfig();
        configCache.clear();
    }

    public static void loadAllLevelConfig() {
        configCache = new LinkedHashMap<>();
        File file = new File(path + "/worlds/");
        File[] listFiles = file.listFiles();
        plugin.getLogger().info(language.translateString("tip_loading_levelAllConfigs"));
        if (listFiles != null) {
            for (File file1 : listFiles) {
                if (DefaultConfigUtils.isYaml(file1.getName())) {
                    Config config = new Config(file1);
                    defaultConfigUtils.checkAll(file1.getName(), config); // 检测配置
                    String levelName = file1.getName().split("\\.")[0];
                    Level level = Server.getInstance().getLevelByName(levelName);
                    if (level == null) {
                        if (Server.getInstance().loadLevel(levelName)) {
                            level = Server.getInstance().getLevelByName(levelName);
                        } else {
                            plugin.getLogger().warning(language.translateString("tip_generic_levelLoad_failed", levelName));
                            continue;
                        }
                    }
                    plugin.getLogger().info(language.translateString("tip_loading_gameRule", level));
                    configCache.put(levelName, (LinkedHashMap<String, Object>) config.getAll());
                    if (!getLevelSettingBooleanInit(levelName, "World", "TimeFlow")) {
                        level.getGameRules().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                    }
                    Object weather = getLevelSettingInit(levelName, "World", "Weather");
                    if (weather != null) {
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
                    int loadedGameRule = 0;
                    if (gamerules.size() > 0) {
                        for (String item : gamerules.keySet()) {
                            Optional<GameRule> options = GameRule.parseString(item);
                            if (options.isPresent()) {
                                Object object = gamerules.get(item);
                                if (object instanceof Boolean) {
                                    level.getGameRules().setGameRule(options.get(), (Boolean) object);
                                } else if (object instanceof Integer) {
                                    level.getGameRules().setGameRule(options.get(), (Integer) object);
                                } else if (object instanceof Float) {
                                    level.getGameRules().setGameRule(options.get(), (Float) object);
                                }
                                loadedGameRule += 1;
                                plugin.getLogger().info(language.translateString("tip_modifying_gameRule_entry_success", item, object.toString()));
                            } else {
                                plugin.getLogger().info(language.translateString("tip_modifying_gameRule_entry_failed", item));
                            }
                        }
                    }
                    plugin.getLogger().info(language.translateString("tip_loading_gameRule_finish", loadedGameRule));
                    plugin.getLogger().info(language.translateString("tip_loading_levelConfig_success", levelName));
                }
            }
        }
        plugin.getLogger().info(language.translateString("tip_loading_levelAllConfigs_finish", configCache.keySet().size()));
    }

    public static void loadTemplateConfig() {
        ConfigUtil.templateCache = new LinkedHashMap<>();
        File file = new File(path + "/templates/");
        File[] listFiles = file.listFiles();
        plugin.getLogger().info(language.translateString("tip_loading_allTemplates"));
        if (listFiles != null) {
            for (File file1 : listFiles) {
                if (DefaultConfigUtils.isYaml(file1.getName())) {
                    Config config = new Config(file1);
                    defaultConfigUtils.checkAll(file1.getName(), config);
                    String templateName = file1.getName().split("\\.")[0];
                    plugin.getLogger().info(language.translateString("tip_loading_template_success", templateName));
                    ConfigUtil.templateCache.put(templateName, (LinkedHashMap<String, Object>) config.getAll());
                }
            }
        }
        plugin.getLogger().info(language.translateString("tip_loading_allTemplates_finish", ConfigUtil.templateCache.keySet().size()));
    }

    public static void saveAllConfig() {
        plugin.getLogger().info(language.translateString("tip_save_all_configs_start"));
        for (String s : MainClass.configCache.keySet()) {
            Config config = new Config(path + "/worlds/" + s + ".yml", Config.YAML);
            config.setAll(MainClass.configCache.get(s));
            config.save();
            plugin.getLogger().info(language.translateString("tip_save_config_success", s));
        }
        plugin.getLogger().info(language.translateString("tip_save_all_templates_start"));
        for (String s : ConfigUtil.templateCache.keySet()) {
            Config config = new Config(path + "/templates/" + s + ".yml", Config.YAML);
            config.setAll(ConfigUtil.templateCache.get(s));
            config.save();
            plugin.getLogger().info(language.translateString("tip_save_template_success", s));
        }
        plugin.getLogger().alert(language.translateString("tip_save_all_success"));
    }

    public static Boolean getLevelBooleanInit(String LevelName, String key, String subKey) {
        if (configCache.containsKey(LevelName)) {
            Map<String, Object> map = configCache.get(LevelName);
            if (map.containsKey(key)) {
                Map<String, Object> keyMap = (Map<String, Object>) map.get(key);
                if (keyMap.containsKey(subKey)) {
                    return (Boolean) keyMap.get(subKey);
                }
            }
        }
        return null;
    }

    public static Boolean getLevelSettingBooleanInit(String LevelName, String key, String subKey) {
        if (configCache.containsKey(LevelName)) {
            Map<String, Object> map = configCache.get(LevelName); // world的全部配置
            if (map.containsKey(key)) {
                Map<String, Object> keyMap = (Map<String, Object>) map.get(key); //键下的所有配置
                if (keyMap.containsKey(subKey)) {
                    return (Boolean) keyMap.get(subKey);
                }
            }
        }
        return false;
    }

    public static Object getLevelSettingInit(String LevelName, String key, String subKey) {
        if (configCache.containsKey(LevelName)) {
            Map<String, Object> map = configCache.get(LevelName); // world的全部配置
            if (map.containsKey(key)) {
                Map<String, Object> keyMap = (Map<String, Object>) map.get(key); //键下的所有配置
                if (keyMap.containsKey(subKey)) {
                    return keyMap.get(subKey);
                }
            }
        }
        return false;
    }

    public static void setLevelInit(String LevelName, String key, String subKey, Object value) {
        LinkedHashMap<String, Object> map;
        if (configCache.containsKey(LevelName)) {
            map = configCache.get(LevelName);
            LinkedHashMap<String, Object> keyMap;
            if (map.containsKey(key)) {
                keyMap = (LinkedHashMap<String, Object>) map.get(key);
            } else {
                keyMap = new LinkedHashMap<>();
            }
            keyMap.put(subKey, value);
            map.put(key, keyMap);
        } else {
            map = new LinkedHashMap<>();
            LinkedHashMap<String, Object> keyMap = new LinkedHashMap<>();
            keyMap.put(subKey, value);
            map.put(key, keyMap);
        }
        configCache.put(LevelName, map);
    }

    public static List<String> getLevelStringListInit(String LevelName, String key, String subKey) {
        if (configCache.containsKey(LevelName)) {
            Map<String, Object> map = configCache.get(LevelName); // world的全部配置
            if (map.containsKey(key)) {
                Map<String, Object> keyMap = (Map<String, Object>) map.get(key); //键下的所有配置
                if (keyMap.containsKey(subKey)) {
                    return (List<String>) keyMap.get(subKey);
                }
            }
        }
        return new ArrayList<>();
    }
}
