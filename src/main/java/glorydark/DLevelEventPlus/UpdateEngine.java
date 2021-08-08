package glorydark.DLevelEventPlus;

import cn.nukkit.utils.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateEngine {
    public static void updateConfig(){
        File file = new File(MainClass.path+"/worlds/");
        File[] listFiles = file.listFiles();
        if(listFiles != null) {
            for (File file1 : listFiles) {
                Config config = new Config(file1,Config.YAML);
                String fileName = file1.getName().split("\\.")[0];
                if(config.exists("World.WhiteList")){
                    for(String player: config.getStringList("World.WhiteList")){
                        glorydark.DLevelEventPlus.utils.Config.whiteList(0,player,fileName);
                    }
                    Map<String, Object> map = (Map<String, Object>) config.get("World");
                    map.remove("WhiteList");
                    config.set("World",map);
                    config.save();
                    MainClass.plugin.getLogger().info("Update ["+fileName+"] 's whitelist successfully!");
                }
                if(config.exists("World.OperatorList")){
                    for(String player: config.getStringList("World.OperatorList")){
                        glorydark.DLevelEventPlus.utils.Config.operatorList(0,player,fileName);
                    }
                    Map<String, Object> map = (Map<String, Object>) config.get("World");
                    map.remove("OperatorList");
                    config.set("World",map);
                    config.save();
                    MainClass.plugin.getLogger().info("Update ["+fileName+"] 's operators list successfully!");
                }
            }
        }

        Config config = new Config(MainClass.path+"/lang-new.yml",Config.YAML);
        if(config.getInt("Version") < 20210808){
            MainClass.plugin.getLogger().info("Updating: lang-new.yml[Ver:20210802]");
            if(!config.exists("TipsTitle")){
                config.set("TipsTitle","提示");
            }
            if(!config.exists("SelectWorld")){
                config.set("SelectWorld","选择世界");
            }
            if(!config.exists("SelectWorldContent")){
                config.set("SelectWorldContent","请选择您要编辑的世界!");
            }
            if(!config.exists("SaveSuccess")){
                config.set("SaveSuccess","操作成功!");
            }
            if(!config.exists("ReturnButton")){
                config.set("ReturnButton","返回");
            }
            if(!config.exists("QuitButton")){
                config.set("QuitButton","退出");
            }
            if(!config.exists("EditWorld")){
                config.set("EditWorld","编辑世界");
            }
            if(!config.exists("Help")){
                List<String> stringList = new ArrayList<>();
                stringList.add("DLevelEventPlus 使用指南");
                stringList.add("新增世界配置文件 /dwp addworld world");
                stringList.add("新增管理员 /dwp admin add/del player world");
                stringList.add("新增操作员 /dwp operatorlist add/del player world");
                stringList.add("新增白名单 /dwp whitelist add/del player world");
                stringList.add("打开编辑配置菜单 /dwp edit");
                stringList.add("保存配置(不包含语言文件) /dwp save");
                stringList.add("重载世界配置(包含语言文件) /dwp reload");
                config.set("Help",stringList);
            }
            config.set("Version",20210808);
            config.save();
            MainClass.plugin.getLogger().info("Update lang-new.yml successfully!");
        }
        if(config.getString("Version").equals("20210808")){
            MainClass.plugin.getLogger().info("Updating: lang-new.yml[Ver:2021080801]");
            if(!config.exists("AntiPlaceSpecificBlock")){
                config.set("AntiPlaceSpecificBlock","您所在世界禁止放置该方块!");
            }
            if(!config.exists("AntiBreakSpecificBlock")){
                config.set("AntiBreakSpecificBlock","您所在世界禁止破坏该方块!");
            }
            if(!config.exists("BanTeleport")){
                config.set("BanTeleport","禁止传送");
            }
            config.set("Version",2021080801);
            config.save();
        }
        if(config.getString("Version").equals("2021080801")) {
            MainClass.plugin.getLogger().info("Updating: lang-new.yml[Ver:2021080802]");
            config.set("AntiBreakSpecificBlock","您所在世界禁止破坏该方块!");
            config.set("Version",2021080802);
            config.save();
        }
        if(config.getString("Version").equals("2021080802")) {
            MainClass.plugin.getLogger().info("Updating: lang-new.yml[Ver:20210809]");
            config.set("SaveFailed","操作失败！!");
            config.set("Version",20210809);
            config.save();
        }
    }
}
