package glorydark.DLevelEventPlus.event;

import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.level.WeatherChangeEvent;
import cn.nukkit.event.weather.LightningStrikeEvent;
import cn.nukkit.level.Level;
import glorydark.DLevelEventPlus.api.LevelSettingsAPI;
import glorydark.DLevelEventPlus.protection.NameMapping;

/**
 * @author glorydark
 * @date {2023/8/11} {11:41}
 */
public class WorldEventListener implements Listener {

    @EventHandler
    public void LightningStrikeEvent(LightningStrikeEvent event) {
        Boolean bool = LevelSettingsAPI.getLevelBooleanSetting(event.getLevel().getName(), NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_LIGHTNING_STRIKE);
        if (bool == null) {
            return;
        }
        if (!bool) {
            event.setCancelled(true);
        }
    }
}
