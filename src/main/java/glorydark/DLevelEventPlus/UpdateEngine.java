package glorydark.DLevelEventPlus;

import cn.nukkit.utils.Config;

import java.io.File;
import java.util.Map;

public class UpdateEngine {
    public static void updateConfig(){
        File file = new File(MainClass.path+"/worlds/");
        File[] listFiles = file.listFiles();
        if(listFiles != null) {
            for (File file1 : listFiles) {
                if(isYaml(file1.getName())) {
                    Config config = new Config(file1, Config.YAML);
                    String fileName = file1.getName().split("\\.")[0];
                    if (config.exists("World.WhiteList")) {
                        for (String player : config.getStringList("World.WhiteList")) {
                            glorydark.DLevelEventPlus.utils.Config.whiteList(0, player, fileName);
                        }
                        Map<String, Object> map = (Map<String, Object>) config.get("World");
                        map.remove("WhiteList");
                        config.set("World", map);
                        config.save();
                        MainClass.plugin.getLogger().info("Update [" + fileName + "] 's whitelist successfully!");
                    }
                    if (config.exists("World.OperatorList")) {
                        for (String player : config.getStringList("World.OperatorList")) {
                            glorydark.DLevelEventPlus.utils.Config.operatorList(0, player, fileName);
                        }
                        Map<String, Object> map = (Map<String, Object>) config.get("World");
                        map.remove("OperatorList");
                        config.set("World", map);
                        config.save();
                        MainClass.plugin.getLogger().info("Update [" + fileName + "] 's operators list successfully!");
                    }
                }
            }
        }

        Config config = new Config(MainClass.path+ "/lang.yml",Config.YAML);
        if(config.getInt("Version") < 2022010101) {
            MainClass.plugin.saveResource("lang.yml", true);
        }
    }

    public static Boolean isYaml(String name){
        String[] formatSplit = name.split("\\.");
        if(formatSplit.length > 1){
            if(formatSplit[1].equals("yml")){
                return true;
            }
        }
        return false;
    }
}
