package glorydark.DLevelEventPlus;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.level.Level;
import glorydark.DLevelEventPlus.utils.Config;

public class command extends Command {
    public command(String name) {
        super(name,"§e世界保护插件","/dwp");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!(sender instanceof Player)){
            //dgamerule operatorlist add/del xx XX
            //dgamerule whitelist add/del xx
            //dgamerule addworld xx
            if(args.length == 4){
                if(args[0].equals("operatorlist")){
                    if(args[1].equals("add")){
                        if(Config.OperatorList(0,args[2],args[3])){
                            sender.sendMessage("新增成功!");
                        }else{
                            sender.sendMessage("新增失败!");
                        }
                    }
                    if(args[1].equals("del")){
                        if(Config.OperatorList(1,args[2],args[3])){
                            sender.sendMessage("新增成功!");
                        }else{
                            sender.sendMessage("新增失败!");
                        }
                    }
                }
                if(args[0].equals("whitelist")){
                    if(args[1].equals("add")){
                        if(Config.WhiteList(0,args[2],args[3])){
                            sender.sendMessage("新增成功!");
                        }else{
                            sender.sendMessage("新增失败!");
                        }
                    }
                    if(args[1].equals("del")){
                        if(Config.WhiteList(1,args[2],args[3])){
                            sender.sendMessage("新增成功!");
                        }else{
                            sender.sendMessage("新增失败!");
                        }
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
                }
            }
        }else{
            if(args.length == 2){
                if(args[0].equals("edit")){
                    if(Server.getInstance().getLevelByName(args[1]) != null){
                        Level level = Server.getInstance().getLevelByName(args[1]);
                        FormWindowCustom formWindowCustom = new FormWindowCustom("编辑世界 - 【" + level.getName() + "】");
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("FarmProtect"),MainClass.getLevelBooleanInit(level.getName(),"World.FarmProtect")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllExplodes"),MainClass.getLevelBooleanInit(level.getName(),"World.AllExplodes")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("TntExplodes"),MainClass.getLevelBooleanInit(level.getName(),"World.TntExplodes")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Pvp"),MainClass.getLevelBooleanInit(level.getName(),"World.Pvp")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("KeepInventory"),MainClass.getLevelBooleanInit(level.getName(),"World.KeepInventory")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("KeepXp"),MainClass.getLevelBooleanInit(level.getName(),"World.KeepXp")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowOpenChest"),MainClass.getLevelBooleanInit(level.getName(),"Player.AllowOpenChest")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("CanUseFishingHook"),MainClass.getLevelBooleanInit(level.getName(),"Player.CanUseFishingHook")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowInteractFrameBlock"),MainClass.getLevelBooleanInit(level.getName(),"Player.AllowInteractFrameBlock")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowUseFlintAndSteel"),MainClass.getLevelBooleanInit(level.getName(),"Player.AllowUseFlintAndSteel")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Sneak"),MainClass.getLevelBooleanInit(level.getName(),"Player.Sneak")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Fly"),MainClass.getLevelBooleanInit(level.getName(),"Player.Fly")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Swim"),MainClass.getLevelBooleanInit(level.getName(),"Player.Swim")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Glide"),MainClass.getLevelBooleanInit(level.getName(),"Player.Glide")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Jump"),MainClass.getLevelBooleanInit(level.getName(),"Player.Jump")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Sprint"),MainClass.getLevelBooleanInit(level.getName(),"Player.Sprint")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Pick"),MainClass.getLevelBooleanInit(level.getName(),"Player.Pick")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("ConsumeItem"),MainClass.getLevelBooleanInit(level.getName(),"Player.ConsumeItem")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("DropItem"),MainClass.getLevelBooleanInit(level.getName(),"Player.DropItem")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("BedEnter"),MainClass.getLevelBooleanInit(level.getName(),"Player.BedEnter")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Move"),MainClass.getLevelBooleanInit(level.getName(),"Player.Move")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("EatFood"),MainClass.getLevelBooleanInit(level.getName(),"Player.EatFood")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("CommandPreprocess"),MainClass.getLevelBooleanInit(level.getName(),"Player.CommandPreprocess")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("GameModeChange"),MainClass.getLevelBooleanInit(level.getName(),"Player.GameModeChange")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Explosion"),MainClass.getLevelBooleanInit(level.getName(),"Entity.Explosion")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("PortalEnter"),MainClass.getLevelBooleanInit(level.getName(),"Entity.PortalEnter")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowPlaceBlock"),MainClass.getLevelBooleanInit(level.getName(),"Block.AllowPlaceBlock")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowBreakBlock"),MainClass.getLevelBooleanInit(level.getName(),"Block.AllowBreakBlock")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Burn"),MainClass.getLevelBooleanInit(level.getName(),"Block.Burn")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Ignite"),MainClass.getLevelBooleanInit(level.getName(),"Block.Ignite")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Fall"),MainClass.getLevelBooleanInit(level.getName(),"Block.Fall")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Grow"),MainClass.getLevelBooleanInit(level.getName(),"Block.Grow")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Spread"),MainClass.getLevelBooleanInit(level.getName(),"Block.Spread")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("Form"),MainClass.getLevelBooleanInit(level.getName(),"Block.Form")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("DoorToggle"),MainClass.getLevelBooleanInit(level.getName(),"Block.DoorToggle")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("LeavesDecay"),MainClass.getLevelBooleanInit(level.getName(),"Block.LeavesDecay")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("LiquidFlow"),MainClass.getLevelBooleanInit(level.getName(),"Block.LiquidFlow")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("ItemFrameDropItem"),MainClass.getLevelBooleanInit(level.getName(),"Block.ItemFrameDropItem")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("SignChange"),MainClass.getLevelBooleanInit(level.getName(),"Block.SignChange")));
                        formWindowCustom.addElement(new ElementToggle(Config.getLang("BlockRedstone"),MainClass.getLevelBooleanInit(level.getName(),"Block.BlockRedstone")));
                        ((Player) sender).showFormWindow(formWindowCustom,181314);
                    }else{
                        sender.sendMessage("世界不存在！");
                    }
                }
            }
        }
        return true;
    }
}