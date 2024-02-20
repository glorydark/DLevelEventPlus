package glorydark.DLevelEventPlus.event;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.level.WeatherChangeEvent;
import cn.nukkit.event.weather.LightningStrikeEvent;
import cn.nukkit.level.Level;
import glorydark.DLevelEventPlus.api.LevelSettingsAPI;

/**
 * @author glorydark
 * @date {2023/8/11} {11:41}
 */
public class WorldEventListener implements Listener {

    @EventHandler
    public void WeatherChangeEvent(WeatherChangeEvent event) {
        Level level = event.getLevel();
        String levelName = level.getName();
        Object weather = LevelSettingsAPI.getLevelSettingInit(levelName, "World", "Weather");
        if (weather != null && !String.valueOf(weather).equals("")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void LightningStrikeEvent(LightningStrikeEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanInit(event.getLevel().getName(), "World", "LightningStrike");
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }
}
