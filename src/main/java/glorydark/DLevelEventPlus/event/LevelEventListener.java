package glorydark.DLevelEventPlus.event;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.level.WeatherChangeEvent;
import cn.nukkit.level.Level;
import glorydark.DLevelEventPlus.MainClass;

/**
 * @author glorydark
 * @date {2023/8/11} {11:41}
 */
public class LevelEventListener implements Listener {

    @EventHandler
    public void WeatherChangeEvent(WeatherChangeEvent event) {
        Level level = event.getLevel();
        String levelName = level.getName();
        Object weather = MainClass.getLevelSettingInit(levelName, "World", "Weather");
        if(weather != null) {
            event.setCancelled(true);
        }
    }
}
