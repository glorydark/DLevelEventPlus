package glorydark.DLevelEventPlus;

import cn.nukkit.Server;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import glorydark.DLevelEventPlus.event.BlockEventListener;
import glorydark.DLevelEventPlus.event.EntityEventListener;
import glorydark.DLevelEventPlus.event.PlayerEventListener;

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
    }

    // 获取世界的配置，无则返回null
    public static Boolean getLevelBooleanInit(String LevelName, String Key){
        Config LevelInit = new Config(path+"/worlds/"+LevelName+".yml",Config.YAML);
        if (LevelInit.exists(Key)) {
            if(LevelInit.get(Key) == ""){ return null; }
            return LevelInit.getBoolean(Key);
        }else{
            return null;
        }
    }

    public static void setLevelBooleanInit(String LevelName, String Key, Boolean value){
        Config LevelInit = new Config(path+"/worlds/"+LevelName+".yml",Config.YAML);
        LevelInit.set(Key,value);
        LevelInit.save();
    }

    public static List<String> getLevelStringlistInit(String LevelName, String Key){
        Config LevelInit = new Config(path+"/worlds/"+LevelName+".yml",Config.YAML);
        if (LevelInit.exists(Key)) {
            if(LevelInit.get(Key) == ""){ return null; }
            return new ArrayList<>(LevelInit.getStringList(Key));
        }else{
            return null;
        }
    }
}
