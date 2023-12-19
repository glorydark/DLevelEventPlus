package glorydark.DLevelEventPlus;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.Task;
import glorydark.DLevelEventPlus.utils.ConfigUtil;

public class CheckTask extends Task {
    @Override
    public void onRun(int i) {
        for (Level level : Server.getInstance().getLevels().values()) {
            if (level.getPlayers().size() > 0) {
                Boolean bool = MainClass.getLevelBooleanInit(level.getName(), "Player", "Interact");
                Boolean bool1 = MainClass.getLevelBooleanInit(level.getName(), "Block", "AllowPlaceBlock");
                Boolean bool2 = MainClass.getLevelBooleanInit(level.getName(), "Block", "AllowBreakBlock");
                boolean final1 = bool != null && !bool; // 是否不可放置
                boolean final2 = !final1 && bool1 != null && bool2 != null && !bool1 && !bool2;
                level.getPlayers().values().forEach(player -> {
                    if (ConfigUtil.isAdmin(player)) {
                        player.setAllowInteract(true);
                        player.setAllowModifyWorld(true);
                        return;
                    }
                    if (ConfigUtil.isOperator(player, level)) {
                        player.setAllowInteract(true);
                        player.setAllowModifyWorld(true);
                        return;
                    }
                    player.setAllowInteract(!final1);
                    player.setAllowModifyWorld(!final2);
                    player.sendCommandData();
                });
            }
        }
    }
}
