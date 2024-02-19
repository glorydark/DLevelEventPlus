package glorydark.DLevelEventPlus.api;

import glorydark.DLevelEventPlus.annotation.InternalUse;

import java.util.*;

/**
 * @author glorydark
 */
public class LevelSettingsAPI {
    public static HashMap<String, LinkedHashMap<String, Object>> configCache = new HashMap<>();

    @InternalUse(description = "to avoid some problems caused by forcibly conversion")
    public static void setLevelSettingsInit(String levelName, String key, String subKey, Object value) {
        LinkedHashMap<String, Object> map;
        if (configCache.containsKey(levelName)) {
            map = configCache.get(levelName);
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
        configCache.put(levelName, map);
    }

    public static <T> T getLevelSettingInit(String levelName, String key, String subKey, T defaultValue) {
        if (configCache.containsKey(levelName)) {
            Map<String, Object> map = configCache.get(levelName);
            Map<String, Object> keyMap = (Map<String, Object>) map.getOrDefault(key, new LinkedHashMap<>());
            if (keyMap.containsKey(subKey)) {
                return (T) keyMap.get(subKey);
            }
        }
        return defaultValue;
    }

    @InternalUse(description = "to avoid some problems caused by forcibly conversion")
    public static Boolean getLevelBooleanInit(String levelName, String key, String subKey) {
        return getLevelSettingInit(levelName, key, subKey, null);
    }

    @InternalUse(description = "to avoid some problems caused by forcibly conversion")
    public static Boolean getLevelSettingBooleanInit(String levelName, String key, String subKey) {
        return getLevelSettingInit(levelName, key, subKey, false);
    }
    
    @InternalUse(description = "to avoid some problems caused by forcibly conversion")
    public static Object getLevelSettingInit(String levelName, String key, String subKey) {
        return getLevelSettingInit(levelName, key, subKey, null);
    }

    @InternalUse(description = "to avoid some problems caused by forcibly conversion")
    public static List<String> getLevelStringListInit(String levelName, String key, String subKey) {
        return getLevelSettingInit(levelName, key, subKey, new ArrayList<>());
    }
}