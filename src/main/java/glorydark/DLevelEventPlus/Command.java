package glorydark.DLevelEventPlus;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import glorydark.DLevelEventPlus.gui.FormMain;
import glorydark.DLevelEventPlus.protection.ProtectionEntryMain;
import glorydark.DLevelEventPlus.utils.ConfigUtil;

import java.io.File;

public class Command extends cn.nukkit.command.Command {
    public Command(String name) {
        super(name, "§eDLevelEventPlus", "/dwp");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender.isPlayer()) || (sender.isPlayer() && (ConfigUtil.isAdmin((Player) sender) || ConfigUtil.isOperator((Player) sender, ((Player) sender).getLevel())))) {
            switch (args.length) {
                case 0:
                    if (sender.isPlayer()) {
                        FormMain.showMainMenu((Player) sender);
                    } else {
                        sender.sendMessage(LevelEventPlusMain.language.translateString("tip_generic_useInGame"));
                    }
                    return true;
                case 1:
                    if (sender.isPlayer() && !ConfigUtil.isAdmin((Player) sender)) {
                        sender.sendMessage(LevelEventPlusMain.language.translateString("tip_generic_noPermission"));
                        return false;
                    }
                    switch (args[0]) {
                        case "save":
                            LevelEventPlusMain.saveAllConfig();
                            return true;
                        case "reload":
                            LevelEventPlusMain.loadAllLevelConfig();
                            LevelEventPlusMain.loadTemplateConfig();
                            return true;
                        case "fixall":
                            ProtectionEntryMain.updateFiles();
                            sender.sendMessage(LevelEventPlusMain.language.translateString("tip_fix_config_success"));
                            return true;
                    }
                    break;
                case 2:
                    if (sender.isPlayer() && !ConfigUtil.isAdmin((Player) sender)) {
                        sender.sendMessage(LevelEventPlusMain.language.translateString("tip_generic_noPermission"));
                        return false;
                    }
                    if (args[0].equals("addworld")) {
                        if (ProtectionEntryMain.createFile(new File(LevelEventPlusMain.path + "/worlds/" + args[1]))) {
                            sender.sendMessage(LevelEventPlusMain.language.translateString("tip_addWorld_success"));
                        } else {
                            sender.sendMessage(LevelEventPlusMain.language.translateString("tip_addWorld_failed"));
                        }
                        return true;
                    }
                    break;
                case 3:
                    if (sender.isPlayer()) {
                        sender.sendMessage(LevelEventPlusMain.language.translateString("tip_generic_noPermission"));
                        return false;
                    }
                    if (args[0].equals("admin")) {
                        switch (args[1]) {
                            case "add":
                                if (Server.getInstance().lookupName(args[2]).isPresent()) {
                                    ConfigUtil.adminList(sender, 0, args[2]);
                                } else {
                                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_generic_playerNotFound"));
                                }
                                return true;
                            case "del":
                                if (Server.getInstance().lookupName(args[2]).isPresent()) {
                                    ConfigUtil.adminList(sender, 1, args[2]);
                                } else {
                                    sender.sendMessage(LevelEventPlusMain.language.translateString("tip_generic_playerNotFound"));
                                }
                                return true;
                        }
                    }
                    break;
                case 4:
                    if (sender.isPlayer() && !ConfigUtil.isAdmin((Player) sender)) {
                        sender.sendMessage(LevelEventPlusMain.language.translateString("tip_generic_noPermission"));
                        return false;
                    }
                    switch (args[0]) {
                        case "operatorlist":
                            switch (args[1]) {
                                case "add":
                                    if (Server.getInstance().lookupName(args[2]).isPresent()) {
                                        ConfigUtil.operatorList(sender, 0, args[2], args[3]);
                                    } else {
                                        sender.sendMessage(LevelEventPlusMain.language.translateString("tip_generic_playerNotFound"));
                                    }
                                    return true;
                                case "del":
                                    if (Server.getInstance().lookupName(args[2]).isPresent()) {
                                        ConfigUtil.operatorList(sender, 1, args[2], args[3]);
                                    } else {
                                        sender.sendMessage(LevelEventPlusMain.language.translateString("tip_generic_playerNotFound"));
                                    }
                                    return true;
                            }
                        case "whitelist":
                            switch (args[1]) {
                                case "add":
                                    ConfigUtil.whiteList(sender, 0, args[2], args[3]);
                                    return true;
                                case "del":
                                    ConfigUtil.whiteList(sender, 1, args[2], args[3]);
                                    return true;
                            }
                            break;
                    }
                    break;
            }
            sendCommandUsage(sender);
        }
        return true;
    }

    public void sendCommandUsage(CommandSender sender) {
        sender.sendMessage(LevelEventPlusMain.language.translateString("tip_help").replace("\\n", "\n"));
    }
}