package glorydark.DLevelEventPlus.api;

import glorydark.DLevelEventPlus.annotation.InternalUse;

import java.util.*;

/**
 * @author glorydark
 */
public class LevelSettingsAPI {
    public static HashMap<String, LinkedHashMap<String, Object>> configCache = new HashMap<>();

    @InternalUse(description = "to avoid some problems caused by forcibly conversion")
    public static void setLevelSetting(String levelName, String key, String subKey, Object value) {
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

    public static <T> T getLevelSetting(String levelName, String key, String subKey, T defaultValue) {
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
    public static Boolean getLevelBooleanSetting(String levelName, String key, String subKey) {
        return getLevelSetting(levelName, key, subKey, null);
    }
    
    @InternalUse(description = "to avoid some problems caused by forcibly conversion")
    public static Object getLevelObjectSetting(String levelName, String key, String subKey) {
        return getLevelSetting(levelName, key, subKey, null);
    }

    @InternalUse(description = "to avoid some problems caused by forcibly conversion")
    public static List<String> getLevelStringListSetting(String levelName, String key, String subKey) {
        return getLevelSetting(levelName, key, subKey, new ArrayList<>());
    }
}
