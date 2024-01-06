package glorydark.DLevelEventPlus;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.Task;

import java.util.List;
import java.util.Map;

public class CheckTask extends Task {
    
    @Override
    public void onRun(int i) {
        for (Level level : Server.getInstance().getLevels().values()) {
            List<String> clearItems = LevelEventPlusMain.getLevelStringListInit(level.getName(), "Player", "ClearItems");
            for (Player player : level.getPlayers().values()) {
                for (Map.Entry<Integer, Item> entry : player.getInventory().getContents().entrySet()) {
                    Item check = entry.getValue();
                    if (clearItems.contains(check.getId() + ":" + check.getDamage())) {
                        player.getInventory().remove(check);
                    }
                }
            }
        }
    }
}
