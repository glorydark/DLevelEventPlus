package glorydark.DLevelEventPlus.gui;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.Level;
import glorydark.DLevelEventPlus.MainClass;
import glorydark.DLevelEventPlus.event.PlayerEventListener;
import glorydark.DLevelEventPlus.utils.Config;

public class GuiMain {
    //设置选择世界
    public static void showSettingChooseWorldMenu(Player player){
        FormWindowSimple window = new FormWindowSimple(Config.getLang("SelectWorld"),Config.getLang("SelectWorldContent"));
        for(Level level: Server.getInstance().getLevels().values()){
            window.addButton(new ElementButton(level.getName()));
        }
        window.addButton(new ElementButton("返回"));
        PlayerEventListener.showFormWindow(player,window, GuiType.Edit_ChooseWorld);
    }

    //权限设置主页面
    public static void showPowerMainMenu(Player player){
        if(Config.isAdmin(player)){
            FormWindowSimple window = new FormWindowSimple("操作系统","选择您需要的功能");
            window.addButton(new ElementButton("添加权限"));
            window.addButton(new ElementButton("删除权限"));
            window.addButton(new ElementButton("返回"));
            PlayerEventListener.showFormWindow(player,window,GuiType.Power_Main);
        }
    }

    //主页面
    public static void showMainMenu(Player player){
        if(Config.isAdmin(player)){
            FormWindowSimple window = new FormWindowSimple("操作系统","选择您需要的功能");
            window.addButton(new ElementButton("管理世界"));
            window.addButton(new ElementButton("管理权限"));
            window.addButton(new ElementButton("保存配置"));
            window.addButton(new ElementButton("重载配置"));
            window.addButton(new ElementButton("设置模板"));
            PlayerEventListener.showFormWindow(player,window,GuiType.ADMIN_Main);
        }
        if(Config.isOperator(player,player.getLevel())){
            showEditMenu(player,player.getLevel().getName());
        }
    }

    //权限添加
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

    //取消权限
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

    //编辑世界
    public static void showEditMenu(Player player, String level){
        FormWindowCustom formWindowCustom = new FormWindowCustom(Config.getLang("EditWorld")+" - 【" + level + "】");
        formWindowCustom.addElement(new ElementToggle(Config.getLang("FarmProtect"), MainClass.getLevelSettingBooleanInit(level,"World","FarmProtect")));
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

    //返回窗口
    public static void showReturnWindow(Player player, Boolean success, GuiType guiType){
        FormWindowModal window;
        if(success) {
            window = new FormWindowModal(Config.getLang("TipsTitle"), Config.getLang("SaveSuccess"), Config.getLang("ReturnButton"), Config.getLang("QuitButton"));
        }else{
            window = new FormWindowModal(Config.getLang("TipsTitle"), Config.getLang("SaveFailed"), Config.getLang("ReturnButton"), Config.getLang("QuitButton"));
        }
        PlayerEventListener.showFormWindow(player,window,guiType);
    }

    //选择模板系统选项
    public static void showTemplateMainMenu(Player player) {
        FormWindowSimple window = new FormWindowSimple(Config.getLang("TemplateSystem"),Config.getLang("TemplateSystemContent"));
        window.addButton(new ElementButton("添加模板"));
        window.addButton(new ElementButton("编辑模板"));
        PlayerEventListener.showFormWindow(player,window, GuiType.Template_Main);
    }

    //设置选择模板 -> 两类 ①选择模板创建世界 ②设置模板
    public static void showSettingChooseTemplateMenu(Player player, GuiType type){
        FormWindowSimple window = new FormWindowSimple(Config.getLang("SelectTemplate"),Config.getLang("SelectTemplateContent"));
        for(String buttonTitle: Config.TemplateCache.keySet()){
            window.addButton(new ElementButton(buttonTitle));
        }
        window.addButton(new ElementButton(Config.getLang("ReturnButton")));
        PlayerEventListener.showFormWindow(player,window, type);
    }

    //添加模板
    public static void showTemplateAddMenu(Player player){
        FormWindowCustom custom = new FormWindowCustom(Config.getLang("AddTemplate"));
        custom.addElement(new ElementInput(Config.getLang("TemplateName")));
        PlayerEventListener.showFormWindow(player, custom, GuiType.Template_Add);
    }

    //模板设置
    public static void showTemplateSettingMenu(Player player, String TemplateName, GuiType type){
        FormWindowCustom formWindowCustom = new FormWindowCustom(Config.getLang("EditTemplate") + " - "+ TemplateName);
        formWindowCustom.addElement(new ElementToggle(Config.getLang("FarmProtect"),Config.getTemplateBooleanInit(TemplateName,"World","FarmProtect")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllExplodes"),Config.getTemplateBooleanInit(TemplateName,"World","AllExplodes")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("TntExplodes"),Config.getTemplateBooleanInit(TemplateName,"World","TntExplodes")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Pvp"),Config.getTemplateBooleanInit(TemplateName,"World","Pvp")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("KeepInventory"),Config.getTemplateBooleanInit(TemplateName,"World","KeepInventory")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("KeepXp"),Config.getTemplateBooleanInit(TemplateName,"World","KeepXp")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowOpenChest"),Config.getTemplateBooleanInit(TemplateName,"Player","AllowOpenChest")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("CanUseFishingHook"),Config.getTemplateBooleanInit(TemplateName,"Player","CanUseFishingHook")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowInteractFrameBlock"),Config.getTemplateBooleanInit(TemplateName,"Player","AllowInteractFrameBlock")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowUseFlintAndSteel"),Config.getTemplateBooleanInit(TemplateName,"Player","AllowUseFlintAndSteel")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Sneak"),Config.getTemplateBooleanInit(TemplateName,"Player","Sneak")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Fly"),Config.getTemplateBooleanInit(TemplateName,"Player","Fly")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Swim"),Config.getTemplateBooleanInit(TemplateName,"Player","Swim")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Glide"),Config.getTemplateBooleanInit(TemplateName,"Player","Glide")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Jump"),Config.getTemplateBooleanInit(TemplateName,"Player","Jump")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Sprint"),Config.getTemplateBooleanInit(TemplateName,"Player","Sprint")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Pick"),Config.getTemplateBooleanInit(TemplateName,"Player","Pick")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("ConsumeItem"),Config.getTemplateBooleanInit(TemplateName,"Player","ConsumeItem")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("DropItem"),Config.getTemplateBooleanInit(TemplateName,"Player","DropItem")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("BedEnter"),Config.getTemplateBooleanInit(TemplateName,"Player","BedEnter")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Move"),Config.getTemplateBooleanInit(TemplateName,"Player","Move")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("EatFood"),Config.getTemplateBooleanInit(TemplateName,"Player","EatFood")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("CommandPreprocess"),Config.getTemplateBooleanInit(TemplateName,"Player","CommandPreprocess")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("GameModeChange"),Config.getTemplateBooleanInit(TemplateName,"Player","GameModeChange")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("BanTeleport"),Config.getTemplateBooleanInit(TemplateName,"Player","AntiTeleport")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Explosion"),Config.getTemplateBooleanInit(TemplateName,"Entity","Explosion")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("PortalEnter"),Config.getTemplateBooleanInit(TemplateName,"Entity","PortalEnter")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowPlaceBlock"),Config.getTemplateBooleanInit(TemplateName,"Block","AllowPlaceBlock")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("AllowBreakBlock"),Config.getTemplateBooleanInit(TemplateName,"Block","AllowBreakBlock")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Burn"),Config.getTemplateBooleanInit(TemplateName,"Block","Burn")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Ignite"),Config.getTemplateBooleanInit(TemplateName,"Block","Ignite")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Fall"),Config.getTemplateBooleanInit(TemplateName,"Block","Fall")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Grow"),Config.getTemplateBooleanInit(TemplateName,"Block","Grow")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Spread"),Config.getTemplateBooleanInit(TemplateName,"Block","Spread")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("Form"),Config.getTemplateBooleanInit(TemplateName,"Block","Form")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("DoorToggle"),Config.getTemplateBooleanInit(TemplateName,"Block","DoorToggle")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("LeavesDecay"),Config.getTemplateBooleanInit(TemplateName,"Block","LeavesDecay")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("LiquidFlow"),Config.getTemplateBooleanInit(TemplateName,"Block","LiquidFlow")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("ItemFrameDropItem"),Config.getTemplateBooleanInit(TemplateName,"Block","ItemFrameDropItem")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("SignChange"),Config.getTemplateBooleanInit(TemplateName,"Block","SignChange")));
        formWindowCustom.addElement(new ElementToggle(Config.getLang("BlockRedstone"),Config.getTemplateBooleanInit(TemplateName,"Block","BlockRedstone")));
        PlayerEventListener.showFormWindow(player,formWindowCustom, type);
    }
}
