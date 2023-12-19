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
import glorydark.DLevelEventPlus.utils.ConfigUtil;

import java.util.ArrayList;
import java.util.List;

public class GuiMain {
    //设置选择世界
    public static void showSettingChooseWorldMenu(Player player) {
        FormWindowSimple window = new FormWindowSimple(MainClass.language.translateString("window_chooseWorld_title"), MainClass.language.translateString("window_chooseWorld_content"));
        for (Level level : Server.getInstance().getLevels().values()) {
            window.addButton(new ElementButton(level.getName()));
        }
        window.addButton(new ElementButton(MainClass.language.translateString("window_general_button_returnButton")));
        PlayerEventListener.showFormWindow(player, window, GuiType.Edit_ChooseWorld);
    }

    //权限设置主页面
    public static void showPowerMainMenu(Player player) {
        if (ConfigUtil.isAdmin(player)) {
            FormWindowSimple window = new FormWindowSimple(MainClass.language.translateString("window_powerMain_title"), "");
            window.addButton(new ElementButton(MainClass.language.translateString("window_powerMain_button_addPower")));
            window.addButton(new ElementButton(MainClass.language.translateString("window_powerMain_button_removePower")));
            window.addButton(new ElementButton(MainClass.language.translateString("window_general_button_returnButton")));
            PlayerEventListener.showFormWindow(player, window, GuiType.Power_Main);
        }
    }

    //主页面
    public static void showMainMenu(Player player) {
        if (ConfigUtil.isAdmin(player)) {
            FormWindowSimple window = new FormWindowSimple(MainClass.language.translateString("window_main_title"), MainClass.language.translateString("window_main_content"));
            window.addButton(new ElementButton(MainClass.language.translateString("window_main_button_manageWorld")));
            window.addButton(new ElementButton(MainClass.language.translateString("window_main_button_managePower")));
            window.addButton(new ElementButton(MainClass.language.translateString("window_main_button_saveInit")));
            window.addButton(new ElementButton(MainClass.language.translateString("window_main_button_reloadInit")));
            window.addButton(new ElementButton(MainClass.language.translateString("window_main_button_manageTemplate")));
            PlayerEventListener.showFormWindow(player, window, GuiType.ADMIN_Main);
        }
        if (ConfigUtil.isOperator(player, player.getLevel())) {
            showEditMenu(player, player.getLevel().getName());
        }
    }

    //权限添加
    public static void showPowerAddMenu(Player player) {
        FormWindowCustom window = new FormWindowCustom(MainClass.language.translateString("window_powerMenu_button_addPowerTitle"));
        ElementDropdown powerDropDown = new ElementDropdown(MainClass.language.translateString("window_powerMenu_button_PowerDropdownTitle"));
        powerDropDown.addOption(MainClass.language.translateString("window_powerMenu_dropDown_entry_manager"));
        powerDropDown.addOption(MainClass.language.translateString("window_powerMenu_dropDown_entry_operator"));
        powerDropDown.addOption(MainClass.language.translateString("window_powerMenu_dropDown_entry_whitelist"));
        ElementInput playerNameInput = new ElementInput(MainClass.language.translateString("window_powerMenu_input_playerName"));
        ElementInput worldNameInput = new ElementInput(MainClass.language.translateString("window_powerMenu_input_worldName"));
        window.addElement(powerDropDown);
        window.addElement(playerNameInput);
        window.addElement(worldNameInput);
        PlayerEventListener.showFormWindow(player, window, GuiType.Power_Add);
    }

    //取消权限
    public static void showPowerDeleteMenu(Player player) {
        FormWindowCustom window = new FormWindowCustom(MainClass.language.translateString("window_powerMenu_button_delPowerTitle"));
        ElementDropdown powerDropDown = new ElementDropdown(MainClass.language.translateString("window_powerMenu_button_PowerDropdownTitle"));
        powerDropDown.addOption(MainClass.language.translateString("window_powerMenu_dropDown_entry_manager"));
        powerDropDown.addOption(MainClass.language.translateString("window_powerMenu_dropDown_entry_operator"));
        powerDropDown.addOption(MainClass.language.translateString("window_powerMenu_dropDown_entry_whitelist"));
        ElementInput playerNameInput = new ElementInput(MainClass.language.translateString("window_powerMenu_input_playerName"));
        ElementInput worldNameInput = new ElementInput(MainClass.language.translateString("window_powerMenu_input_worldName"));
        window.addElement(powerDropDown);
        window.addElement(playerNameInput);
        window.addElement(worldNameInput);
        PlayerEventListener.showFormWindow(player, window, GuiType.Power_Delete);
    }

    //编辑世界
    public static void showEditMenu(Player player, String level) {
        FormWindowCustom formWindowCustom = new FormWindowCustom(MainClass.language.translateString("window_edit_chooseWorldTitle"));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_farmProtect"), MainClass.getLevelSettingBooleanInit(level, "World", "FarmProtect")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_allExplodes"), MainClass.getLevelSettingBooleanInit(level, "World", "AllExplodes")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_tntExplodes"), MainClass.getLevelSettingBooleanInit(level, "World", "TntExplodes")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_pvp"), MainClass.getLevelSettingBooleanInit(level, "World", "PVP")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_keepInventory"), MainClass.getLevelSettingBooleanInit(level, "World", "KeepInventory")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_keepXp"), MainClass.getLevelSettingBooleanInit(level, "World", "KeepXp")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_openChest"), MainClass.getLevelSettingBooleanInit(level, "Player", "AllowOpenChest")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_useFishingHook"), MainClass.getLevelSettingBooleanInit(level, "Player", "CanUseFishingHook")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_interactFrameBlock"), MainClass.getLevelSettingBooleanInit(level, "Player", "AllowInteractFrameBlock")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_sneak"), MainClass.getLevelSettingBooleanInit(level, "Player", "Sneak")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_fly"), MainClass.getLevelSettingBooleanInit(level, "Player", "Fly")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_swim"), MainClass.getLevelSettingBooleanInit(level, "Player", "Swim")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_glide"), MainClass.getLevelSettingBooleanInit(level, "Player", "Glide")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_jump"), MainClass.getLevelSettingBooleanInit(level, "Player", "Jump")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_sprint"), MainClass.getLevelSettingBooleanInit(level, "Player", "Sprint")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_pick"), MainClass.getLevelSettingBooleanInit(level, "Player", "Pick")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_consumeItem"), MainClass.getLevelSettingBooleanInit(level, "Player", "ConsumeItem")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_playerDropItem"), MainClass.getLevelSettingBooleanInit(level, "Player", "DropItem")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_bedEnter"), MainClass.getLevelSettingBooleanInit(level, "Player", "BedEnter")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_move"), MainClass.getLevelSettingBooleanInit(level, "Player", "Move")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_eatFood"), MainClass.getLevelSettingBooleanInit(level, "Player", "EatFood")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_commandPreprocess"), MainClass.getLevelSettingBooleanInit(level, "Player", "CommandPreprocess")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_gameModeChange"), MainClass.getLevelSettingBooleanInit(level, "Player", "GameModeChange")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_antiTeleport"), MainClass.getLevelSettingBooleanInit(level, "Player", "AntiTeleport")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_interact"), MainClass.getLevelSettingBooleanInit(level, "Player", "Interact")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_immuneToFallDamage"), MainClass.getLevelSettingBooleanInit(level, "Player", "NoFallDamage")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_entityExplosion"), MainClass.getLevelSettingBooleanInit(level, "Entity", "Explosion")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_portalEnter"), MainClass.getLevelSettingBooleanInit(level, "Entity", "PortalEnter")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_placeBlock"), MainClass.getLevelSettingBooleanInit(level, "Block", "AllowPlaceBlock")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_breakBlock"), MainClass.getLevelSettingBooleanInit(level, "Block", "AllowBreakBlock")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockBurn"), MainClass.getLevelSettingBooleanInit(level, "Block", "Burn")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockIgnite"), MainClass.getLevelSettingBooleanInit(level, "Block", "Ignite")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockFall"), MainClass.getLevelSettingBooleanInit(level, "Block", "Fall")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockGrow"), MainClass.getLevelSettingBooleanInit(level, "Block", "Grow")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockSpread"), MainClass.getLevelSettingBooleanInit(level, "Block", "Spread")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockForm"), MainClass.getLevelSettingBooleanInit(level, "Block", "Form")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_leavesDecay"), MainClass.getLevelSettingBooleanInit(level, "Block", "LeavesDecay")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_liquidFlow"), MainClass.getLevelSettingBooleanInit(level, "Block", "LiquidFlow")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_itemFrameDropItem"), MainClass.getLevelSettingBooleanInit(level, "Block", "ItemFrameDropItem")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_signChange"), MainClass.getLevelSettingBooleanInit(level, "Block", "SignChange")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockRedstone"), MainClass.getLevelSettingBooleanInit(level, "Block", "BlockRedstone")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockDropItem"), MainClass.getLevelSettingBooleanInit(level, "Block", "DropItem")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockDropExp"), MainClass.getLevelSettingBooleanInit(level, "Block", "DropExp")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockUpdate"), MainClass.getLevelSettingBooleanInit(level, "Block", "Update")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockFade"), MainClass.getLevelSettingBooleanInit(level, "Block", "Fade")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockPistonChange"), MainClass.getLevelSettingBooleanInit(level, "Block", "PistonChange")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockFromToEvent"), MainClass.getLevelSettingBooleanInit(level, "Block", "FromToEvent")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_antiVoid"), MainClass.getLevelSettingBooleanInit(level, "World", "AntiVoid")));
        formWindowCustom.addElement(new ElementInput(MainClass.language.translateString("window_edit_label_voidHeight"), String.valueOf(MainClass.getLevelSettingInit(level, "World", "VoidHeight")), String.valueOf(MainClass.getLevelSettingInit(level, "World", "VoidHeight"))));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_timeFlow"), MainClass.getLevelSettingBooleanInit(level, "World", "TimeFlow")));
        Object object = MainClass.getLevelSettingInit(level, "World", "Weather");
        List<String> weatherList = new ArrayList<>();
        weatherList.add("none");
        weatherList.add("clear");
        weatherList.add("thunder");
        weatherList.add("rain");
        if (object != null) {
            int index = weatherList.indexOf(String.valueOf(object));
            formWindowCustom.addElement(new ElementDropdown(MainClass.language.translateString("window_edit_label_weather"), weatherList, Math.max(index, 0)));
        }
        PlayerEventListener.showFormWindow(player, formWindowCustom, GuiType.Edit_Process);
    }

    //返回窗口
    public static void showReturnWindow(Player player, Boolean success, GuiType guiType) {
        FormWindowModal window;
        if (success) {
            window = new FormWindowModal(MainClass.language.translateString("window_general_title"), MainClass.language.translateString("window_general_content_saveSuccess"), MainClass.language.translateString("window_general_button_returnButton"), MainClass.language.translateString("window_general_button_quitButton"));
        } else {
            window = new FormWindowModal(MainClass.language.translateString("window_general_title"), MainClass.language.translateString("window_general_content_saveFailed"), MainClass.language.translateString("window_general_button_returnButton"), MainClass.language.translateString("window_general_button_quitButton"));
        }
        PlayerEventListener.showFormWindow(player, window, guiType);
    }

    //选择模板系统选项
    public static void showTemplateMainMenu(Player player) {
        FormWindowSimple window = new FormWindowSimple(MainClass.language.translateString("window_templateMain_title"), MainClass.language.translateString("window_templateMain_content"));
        window.addButton(new ElementButton(MainClass.language.translateString("window_templateMain_button_addTemplate")));
        window.addButton(new ElementButton(MainClass.language.translateString("window_templateMain_button_editTemplate")));
        window.addButton(new ElementButton(MainClass.language.translateString("window_general_button_returnButton")));
        PlayerEventListener.showFormWindow(player, window, GuiType.Template_Main);
    }

    //设置选择模板 -> 两类 ①选择模板创建世界 ②设置模板
    public static void showSettingChooseTemplateMenu(Player player, GuiType type) {
        FormWindowSimple window = new FormWindowSimple(MainClass.language.translateString("window_chooseTemplate_button_title"), MainClass.language.translateString("window_chooseTemplate_button_content"));
        for (String buttonTitle : ConfigUtil.templateCache.keySet()) {
            window.addButton(new ElementButton(buttonTitle));
        }
        window.addButton(new ElementButton(MainClass.language.translateString("window_general_button_returnButton")));
        PlayerEventListener.showFormWindow(player, window, type);
    }

    //添加模板
    public static void showTemplateAddMenu(Player player) {
        FormWindowCustom custom = new FormWindowCustom(MainClass.language.translateString("window_templateAdd_title"));
        custom.addElement(new ElementInput(MainClass.language.translateString("window_templateAdd_input_templateName")));
        PlayerEventListener.showFormWindow(player, custom, GuiType.Template_Add);
    }

    //模板设置
    public static void showTemplateSettingMenu(Player player, String templateName, GuiType type) {
        FormWindowCustom formWindowCustom = new FormWindowCustom(MainClass.language.translateString("window_edit_templateTitle", templateName));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_farmProtect"), ConfigUtil.getTemplateBooleanInit(templateName, "World", "FarmProtect")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_allExplodes"), ConfigUtil.getTemplateBooleanInit(templateName, "World", "AllExplodes")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_tntExplodes"), ConfigUtil.getTemplateBooleanInit(templateName, "World", "TntExplodes")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_pvp"), ConfigUtil.getTemplateBooleanInit(templateName, "World", "PVP")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_keepInventory"), ConfigUtil.getTemplateBooleanInit(templateName, "World", "KeepInventory")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_keepXp"), ConfigUtil.getTemplateBooleanInit(templateName, "World", "KeepXp")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_openChest"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "AllowOpenChest")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_useFishingHook"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "CanUseFishingHook")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_interactFrameBlock"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "AllowInteractFrameBlock")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_sneak"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "Sneak")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_fly"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "Fly")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_swim"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "Swim")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_glide"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "Glide")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_jump"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "Jump")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_sprint"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "Sprint")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_pick"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "Pick")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_consumeItem"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "ConsumeItem")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_playerDropItem"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "DropItem")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_bedEnter"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "BedEnter")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_move"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "Move")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_eatFood"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "EatFood")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_commandPreprocess"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "CommandPreprocess")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_gameModeChange"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "GameModeChange")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_antiTeleport"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "AntiTeleport")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_interact"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "Interact")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_immuneToFallDamage"), ConfigUtil.getTemplateBooleanInit(templateName, "Player", "NoFallDamage")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_entityExplosion"), ConfigUtil.getTemplateBooleanInit(templateName, "Entity", "Explosion")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_portalEnter"), ConfigUtil.getTemplateBooleanInit(templateName, "Entity", "PortalEnter")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_placeBlock"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "AllowPlaceBlock")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_breakBlock"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "AllowBreakBlock")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockBurn"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "Burn")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockIgnite"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "Ignite")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockFall"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "Fall")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockGrow"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "Grow")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockSpread"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "Spread")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockForm"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "Form")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_leavesDecay"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "LeavesDecay")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_liquidFlow"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "LiquidFlow")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_itemFrameDropItem"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "ItemFrameDropItem")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_signChange"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "SignChange")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockRedstone"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "BlockRedstone")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockDropItem"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "DropItem")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockDropExp"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "DropExp")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockUpdate"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "Update")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockFade"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "Fade")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockPistonChange"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "PistonChange")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_blockFromToEvent"), ConfigUtil.getTemplateBooleanInit(templateName, "Block", "FromToEvent")));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_antiVoid"), ConfigUtil.getTemplateBooleanInit(templateName, "World", "AntiVoid")));
        formWindowCustom.addElement(new ElementInput(MainClass.language.translateString("window_edit_label_voidHeight"), String.valueOf(ConfigUtil.getTemplateInit(templateName, "World", "VoidHeight")), String.valueOf(ConfigUtil.getTemplateInit(templateName, "World", "VoidHeight"))));
        formWindowCustom.addElement(new ElementToggle(MainClass.language.translateString("window_edit_label_timeFlow"), ConfigUtil.getTemplateBooleanInit(templateName, "World", "TimeFlow")));
        Object object = ConfigUtil.getTemplateInit(templateName, "World", "Weather");
        List<String> weatherList = new ArrayList<>();
        weatherList.add("none");
        weatherList.add("clear");
        weatherList.add("thunder");
        weatherList.add("rain");
        if (object != null) {
            int index = weatherList.indexOf(String.valueOf(object));
            formWindowCustom.addElement(new ElementDropdown(MainClass.language.translateString("window_edit_label_weather"), weatherList, Math.max(index, 0)));
        }
        PlayerEventListener.showFormWindow(player, formWindowCustom, type);
    }
}
