package glorydark.DLevelEventPlus;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import glorydark.DLevelEventPlus.gui.GuiMain;
import glorydark.DLevelEventPlus.utils.Config;

public class Command extends cn.nukkit.command.Command {
    public Command(String name) {
        super(name,"§e世界保护插件","/dwp");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!(sender instanceof Player)){
            if(args.length == 4){
                if(args[0].equals("operatorlist")){
                    if(args[1].equals("add")){
                        if(Config.operatorList(0,args[2],args[3])){
                            sender.sendMessage("新增成功!");
                        }else{
                            sender.sendMessage("新增失败!");
                        }
                        return true;
                    }
                    if(args[1].equals("del")){
                        if(Config.operatorList(1,args[2],args[3])){
                            sender.sendMessage("移除成功!");
                        }else{
                            sender.sendMessage("移除失败!");
                        }
                        return true;
                    }
                }
                if(args[0].equals("whitelist")){
                    if(args[1].equals("add")){
                        if(Config.whiteList(0,args[2],args[3])){
                            sender.sendMessage("新增成功!");
                        }else{
                            sender.sendMessage("新增失败!");
                        }
                        return true;
                    }
                    if(args[1].equals("del")){
                        if(Config.whiteList(1,args[2],args[3])){
                            sender.sendMessage("移除成功!");
                        }else{
                            sender.sendMessage("移除失败!");
                        }
                        return true;
                    }
                }
            }
            if(args.length == 3){
                if(args[0].equals("admin")){
                    if(args[1].equals("add")){
                        if(Config.adminList(0,args[2])){
                            sender.sendMessage("新增成功!");
                        }else{
                            sender.sendMessage("新增失败!");
                        }
                        return true;
                    }
                    if(args[1].equals("del")){
                        if(Config.adminList(1,args[2])){
                            sender.sendMessage("移除成功!");
                        }else{
                            sender.sendMessage("移除失败!");
                        }
                        return true;
                    }
                }
            }
            if(args.length == 2){
                if(args[0].equals("addworld")){
                    if(Config.createWorldConfig(args[1])){
                        sender.sendMessage("创建成功!");
                    }else{
                        sender.sendMessage("创建失败!");
                    }
                    return true;
                }
            }
            if(args.length == 1){
                if (args[0].equals("save")) {
                    MainClass.saveAllConfig();
                    return true;
                }
                if (args[0].equals("reload")) {
                    MainClass.loadAllLevelConfig();
                    MainClass.loadLang();
                    return true;
                }
            }
            sendCommandUsage(sender);
        }else{
            Player p = ((Player) sender).getPlayer();
            if(!Config.isAdmin(p) && !Config.isOperator(p,p.getLevel())){
                sender.sendMessage("您没有权限！");
                return true;
            }
            GuiMain.showMainMenu(p);
            return true;
        }
        return true;
    }

    public void sendCommandUsage(CommandSender p){
        //dgamerule operatorlist add/del xx XX
        //dgamerule whitelist add/del xx
        //dgamerule addworld xx
        for(String string: Config.getLangList("Help")) {
            p.sendMessage(TextFormat.YELLOW + string);
        }
    }
}