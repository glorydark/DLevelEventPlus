package glorydark.DLevelEventPlus.api;

import glorydark.DLevelEventPlus.LevelEventPlusMain;
import glorydark.DLevelEventPlus.annotation.InternalUse;

import java.util.*;

/**
 * @author glorydark
 */
public class TemplateAPI {
    public static HashMap<String, LinkedHashMap<String, Object>> templateCache = new HashMap<>();

    public static void setTemplateSetting(String configName, String key, String subKey, Object value) {
        if (templateCache.containsKey(configName)) {
            LinkedHashMap<String, Object> keyMap = templateCache.get(configName);
            if (keyMap != null && keyMap.containsKey(key)) {
                Map<String, Object> obj = (Map<String, Object>) keyMap.get(key);
                if (obj != null) {
                    obj.put(subKey, value);
                    keyMap.put(key, obj);
                    templateCache.put(configName, keyMap);
                } else {
                    LevelEventPlusMain.plugin.getLogger().warning(LevelEventPlusMain.language.translateString("tip_save_template_failed", subKey));
                }
            }
        }
    }

    public static <T> T getTemplateSetting(String configName, String key, String subKey, T defaultValue) {
        if (templateCache.containsKey(configName)) {
            Map<String, Object> templates = templateCache.get(configName);
            if (templates != null && templates.containsKey(key)) {
                Map<String, Object> templateDataMap = (Map<String, Object>) templates.get(key); //键下的所有配置
                if (templateDataMap.containsKey(subKey)) {
                    return (T) templateDataMap.get(subKey);
                }
            }
        }
        return defaultValue;
    }

    @InternalUse(description = "to avoid some problems caused by forcibly conversion")
    public static Object getTemplateSetting(String configName, String key, String subKey) {
        return getTemplateSetting(configName, key, subKey, "");
    }

    @InternalUse(description = "to avoid some problems caused by forcibly conversion")
    public static Boolean getTemplateBooleanSetting(String configName, String key, String subKey) {
        return getTemplateSetting(configName, key, subKey, false);
    }

    @InternalUse(description = "to avoid some problems caused by forcibly conversion")
    public static List<String> getTemplateListSetting(String configName, String key, String subKey) {
        return getTemplateSetting(configName, key, subKey, new ArrayList<>());
    }
}
