package glorydark.DLevelEventPlus.utils;

import cn.nukkit.utils.Config;

import java.io.File;
import java.util.Map;

public class Language {

    public Map<String, Object> lang;

    public Language(File file) {
        lang = new Config(file, Config.PROPERTIES).getAll();
    }

    public String translateString(String key, Object... params) {
        String originText = (String) lang.getOrDefault(key, "Translation Not Found");
        for(int i= 1; i<=params.length; i++) {
            originText = originText.replaceAll("%"+i+"%", params[i-1].toString());
        }
        return originText;
    }
}
