package glorydark.DLevelEventPlus;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.Task;
import glorydark.DLevelEventPlus.utils.ConfigUtil;
import glorydark.DLevelEventPlus.utils.ItemUtils;

import java.util.List;
import java.util.Map;

public class CheckTask extends Task {

    @Override
    public void onRun(int i) {
        for (Level level : Server.getInstance().getLevels().values()) {
            List<String> clearItems = LevelEventPlusMain.getLevelStringListInit(level.getName(), "Player", "ClearItems");
            for (Player player : level.getPlayers().values()) {
                if (ConfigUtil.isAdmin(player)) {
                    return;
                }
                if (ConfigUtil.isOperator(player, player.getLevel())) {
                    return;
                }
                for (Map.Entry<Integer, Item> entry : player.getInventory().getContents().entrySet()) {
                    Item check = entry.getValue();
                    if (clearItems.stream().anyMatch(s -> ItemUtils.isEqual(s, check))) {
                        player.getInventory().remove(check);
                    }
                }
            }
        }
    }
}