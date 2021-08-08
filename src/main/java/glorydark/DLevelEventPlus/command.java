package glorydark.DLevelEventPlus;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.*;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.Level;
import cn.nukkit.utils.TextFormat;
import glorydark.DLevelEventPlus.event.GuiType;
import glorydark.DLevelEventPlus.event.PlayerEventListener;
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
                    MainClass.saveAllLevelConfig();
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
            showMainMenu(p);
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

    public static void showChooseWorldMenu(Player player){
        FormWindowSimple window = new FormWindowSimple(Config.getLang("SelectWorld"),Config.getLang("SelectWorldContent"));
        for(Level level: Server.getInstance().getLevels().values()){
            window.addButton(new ElementButton(level.getName()));
        }
        window.addButton(new ElementButton("返回"));
        PlayerEventListener.showFormWindow(player,window,GuiType.Edit_ChooseWorld);
    }

    public static void showPowerMainMenu(Player player){
        if(Config.isAdmin(player)){
            FormWindowSimple window = new FormWindowSimple("操作系统","选择您需要的功能");
            window.addButton(new ElementButton("添加权限"));
            window.addButton(new ElementButton("删除权限"));
            window.addButton(new ElementButton("返回"));
            PlayerEventListener.showFormWindow(player,window,GuiType.Power_Main);
        }
    }

    public static void showMainMenu(Player player){
        if(Config.isAdmin(player)){
            FormWindowSimple window = new FormWindowSimple("操作系统","选择您需要的功能");
            window.addButton(new ElementButton("管理世界"));
            window.addButton(new ElementButton("管理权限"));
            window.addButton(new ElementButton("保存配置"));
            window.addButton(new ElementButton("重载配置"));
            PlayerEventListener.showFormWindow(player,window,GuiType.ADMIN_Main);
        }else{
            showEditMenu(player,player.getLevel().getName());
        }
    }

    public static void showPowerAddMenu(Player player){
        FormWindowCustom window = new FormWindowCustom("添加权限");
        ElementDropdown powerDropDown = new ElementDropdown("选择添加的权限");
        powerDropDown.addOption("管理员");
        powerDropDown.addOption("操作员");
        powerDropDown.addOption("白名单");
        ElementInput playerNameInput = new ElementInput("玩家名称");
        ElementInput worldNameInput = new ElementInput("世界名(如果是白名单一定要填嗷)");
        window.addElement(powerDropDown);
        window.addElement(playerNameInput);
        window.addElement(worldNameInput);
        PlayerEventListener.showFormWindow(player,window,GuiType.Power_Add);
    }

    public static void showPowerDeleteMenu(Player player){
        FormWindowCustom window = new FormWindowCustom("删除权限");
        ElementDropdown powerDropDown = new ElementDropdown("选择添加的权限");
        powerDropDown.addOption("管理员");
        powerDropDown.addOption("操作员");
        powerDropDown.addOption("白名单");
        ElementInput playerNameInput = new ElementInput("玩家名称");
        ElementInput worldNameInput = new ElementInput("世界名(如果是白名单一定要填嗷)");
        window.addElement(powerDropDown);
        window.addElement(playerNameInput);
        window.addElement(worldNameInput);
        PlayerEventListener.showFormWindow(player,window,GuiType.Power_Delete);
    }
    
    public static void showEditMenu(Player player, String level){
        FormWindowCustom formWindowCustom = new FormWindowCustom(Config.getLang("EditWorld")+" - 【" + level + "】");
        formWindowCustom.addElement(new ElementToggle(Config.getLang("FarmProtect"),MainClass.getLevelSettingBooleanInit(level,"World","FarmProtect")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllExplodes"),MainClass.getLevelSettingBooleanInit(level,"World","AllExplodes")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("TntExplodes"),MainClass.getLevelSettingBooleanInit(level,"World","TntExplodes")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Pvp"),MainClass.getLevelSettingBooleanInit(level,"World","Pvp")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("KeepInventory"),MainClass.getLevelSettingBooleanInit(level,"World","KeepInventory")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("KeepXp"),MainClass.getLevelSettingBooleanInit(level,"World","KeepXp")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowOpenChest"),MainClass.getLevelSettingBooleanInit(level,"Player","AllowOpenChest")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("CanUseFishingHook"),MainClass.getLevelSettingBooleanInit(level,"Player","CanUseFishingHook")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowInteractFrameBlock"),MainClass.getLevelSettingBooleanInit(level,"Player","AllowInteractFrameBlock")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowUseFlintAndSteel"),MainClass.getLevelSettingBooleanInit(level,"Player","AllowUseFlintAndSteel")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Sneak"),MainClass.getLevelSettingBooleanInit(level,"Player","Sneak")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Fly"),MainClass.getLevelSettingBooleanInit(level,"Player","Fly")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Swim"),MainClass.getLevelSettingBooleanInit(level,"Player","Swim")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Glide"),MainClass.getLevelSettingBooleanInit(level,"Player","Glide")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Jump"),MainClass.getLevelSettingBooleanInit(level,"Player","Jump")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Sprint"),MainClass.getLevelSettingBooleanInit(level,"Player","Sprint")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Pick"),MainClass.getLevelSettingBooleanInit(level,"Player","Pick")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("ConsumeItem"),MainClass.getLevelSettingBooleanInit(level,"Player","ConsumeItem")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("DropItem"),MainClass.getLevelSettingBooleanInit(level,"Player","DropItem")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("BedEnter"),MainClass.getLevelSettingBooleanInit(level,"Player","BedEnter")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Move"),MainClass.getLevelSettingBooleanInit(level,"Player","Move")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("EatFood"),MainClass.getLevelSettingBooleanInit(level,"Player","EatFood")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("CommandPreprocess"),MainClass.getLevelSettingBooleanInit(level,"Player","CommandPreprocess")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("GameModeChange"),MainClass.getLevelSettingBooleanInit(level,"Player","GameModeChange")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("BanTeleport"),MainClass.getLevelSettingBooleanInit(level,"Player","AntiTeleport")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Explosion"),MainClass.getLevelSettingBooleanInit(level,"Entity","Explosion")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("PortalEnter"),MainClass.getLevelSettingBooleanInit(level,"Entity","PortalEnter")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowPlaceBlock"),MainClass.getLevelSettingBooleanInit(level,"Block","AllowPlaceBlock")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowBreakBlock"),MainClass.getLevelSettingBooleanInit(level,"Block","AllowBreakBlock")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Burn"),MainClass.getLevelSettingBooleanInit(level,"Block","Burn")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Ignite"),MainClass.getLevelSettingBooleanInit(level,"Block","Ignite")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Fall"),MainClass.getLevelSettingBooleanInit(level,"Block","Fall")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Grow"),MainClass.getLevelSettingBooleanInit(level,"Block","Grow")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Spread"),MainClass.getLevelSettingBooleanInit(level,"Block","Spread")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Form"),MainClass.getLevelSettingBooleanInit(level,"Block","Form")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("DoorToggle"),MainClass.getLevelSettingBooleanInit(level,"Block","DoorToggle")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("LeavesDecay"),MainClass.getLevelSettingBooleanInit(level,"Block","LeavesDecay")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("LiquidFlow"),MainClass.getLevelSettingBooleanInit(level,"Block","LiquidFlow")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("ItemFrameDropItem"),MainClass.getLevelSettingBooleanInit(level,"Block","ItemFrameDropItem")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("SignChange"),MainClass.getLevelSettingBooleanInit(level,"Block","SignChange")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("BlockRedstone"),MainClass.getLevelSettingBooleanInit(level,"Block","BlockRedstone")));
        PlayerEventListener.showFormWindow(player,formWindowCustom, GuiType.Edit_Process);
    }

    public static void showReturnWindow(Player player, Boolean success, GuiType guiType){
        FormWindowModal window;
        if(success) {
            window = new FormWindowModal(Config.getLang("TipsTitle"), Config.getLang("SaveSuccess"), Config.getLang("ReturnButton"), Config.getLang("QuitButton"));
        }else{
            window = new FormWindowModal(Config.getLang("TipsTitle"), Config.getLang("SaveFailed"), Config.getLang("ReturnButton"), Config.getLang("QuitButton"));
        }
        PlayerEventListener.showFormWindow(player,window,guiType);
    }
}