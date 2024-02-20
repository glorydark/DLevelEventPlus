package glorydark.DLevelEventPlus.gui;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.Level;
import glorydark.DLevelEventPlus.LevelEventPlusMain;
import glorydark.DLevelEventPlus.api.LevelSettingsAPI;
import glorydark.DLevelEventPlus.api.PermissionAPI;
import glorydark.DLevelEventPlus.api.TemplateAPI;
import glorydark.DLevelEventPlus.protection.ProtectionEntryMain;
import glorydark.DLevelEventPlus.protection.rule.BooleanProtectionRuleEntry;
import glorydark.DLevelEventPlus.protection.rule.DropdownProtectionRuleEntry;
import glorydark.DLevelEventPlus.protection.rule.InputProtectionRuleEntry;
import glorydark.DLevelEventPlus.protection.rule.ProtectionRuleEntry;

import java.util.HashMap;

public class FormMain {

    public static final HashMap<Player, HashMap<Integer, FormType>> UI_CACHE = new HashMap<>();

    public static void showFormWindow(Player player, FormWindow window, FormType formType) {
        UI_CACHE.computeIfAbsent(player, i -> new HashMap<>()).put(player.showFormWindow(window), formType);
    }

    //设置选择世界
    public static void showSettingChooseWorldMenu(Player player) {
        FormWindowSimple window = new FormWindowSimple(LevelEventPlusMain.language.translateString("window_chooseWorld_title"), LevelEventPlusMain.language.translateString("window_chooseWorld_content"));
        for (Level level : Server.getInstance().getLevels().values()) {
            window.addButton(new ElementButton(level.getName()));
        }
        window.addButton(new ElementButton(LevelEventPlusMain.language.translateString("window_general_button_returnButton")));
        showFormWindow(player, window, FormType.Edit_ChooseWorld);
    }

    //权限设置主页面
    public static void showPowerMainMenu(Player player) {
        if (PermissionAPI.isAdmin(player)) {
            FormWindowSimple window = new FormWindowSimple(LevelEventPlusMain.language.translateString("window_powerMain_title"), "");
            window.addButton(new ElementButton(LevelEventPlusMain.language.translateString("window_powerMain_button_addPower")));
            window.addButton(new ElementButton(LevelEventPlusMain.language.translateString("window_powerMain_button_removePower")));
            window.addButton(new ElementButton(LevelEventPlusMain.language.translateString("window_general_button_returnButton")));
            showFormWindow(player, window, FormType.Power_Main);
        }
    }

    //主页面
    public static void showMainMenu(Player player) {
        if (PermissionAPI.isAdmin(player)) {
            FormWindowSimple window = new FormWindowSimple(LevelEventPlusMain.language.translateString("window_main_title"), LevelEventPlusMain.language.translateString("window_main_content"));
            window.addButton(new ElementButton(LevelEventPlusMain.language.translateString("window_main_button_manageWorld")));
            window.addButton(new ElementButton(LevelEventPlusMain.language.translateString("window_main_button_managePower")));
            window.addButton(new ElementButton(LevelEventPlusMain.language.translateString("window_main_button_saveInit")));
            window.addButton(new ElementButton(LevelEventPlusMain.language.translateString("window_main_button_reloadInit")));
            window.addButton(new ElementButton(LevelEventPlusMain.language.translateString("window_main_button_manageTemplate")));
            showFormWindow(player, window, FormType.Admin_Main);
        }
        if (PermissionAPI.isOperator(player, player.getLevel())) {
            showEditMenuV2(player, player.getLevel().getName());
        }
    }

    //权限添加
    public static void showPowerAddMenu(Player player) {
        FormWindowCustom window = new FormWindowCustom(LevelEventPlusMain.language.translateString("window_powerMenu_button_addPowerTitle"));
        ElementDropdown powerDropDown = new ElementDropdown(LevelEventPlusMain.language.translateString("window_powerMenu_button_PowerDropdownTitle"));
        powerDropDown.addOption(LevelEventPlusMain.language.translateString("window_powerMenu_dropDown_entry_manager"));
        powerDropDown.addOption(LevelEventPlusMain.language.translateString("window_powerMenu_dropDown_entry_operator"));
        powerDropDown.addOption(LevelEventPlusMain.language.translateString("window_powerMenu_dropDown_entry_whitelist"));
        ElementInput playerNameInput = new ElementInput(LevelEventPlusMain.language.translateString("window_powerMenu_input_playerName"));
        ElementInput worldNameInput = new ElementInput(LevelEventPlusMain.language.translateString("window_powerMenu_input_worldName"));
        window.addElement(powerDropDown);
        window.addElement(playerNameInput);
        window.addElement(worldNameInput);
        showFormWindow(player, window, FormType.Power_Add);
    }

    //取消权限
    public static void showPowerDeleteMenu(Player player) {
        FormWindowCustom window = new FormWindowCustom(LevelEventPlusMain.language.translateString("window_powerMenu_button_delPowerTitle"));
        ElementDropdown powerDropDown = new ElementDropdown(LevelEventPlusMain.language.translateString("window_powerMenu_button_PowerDropdownTitle"));
        powerDropDown.addOption(LevelEventPlusMain.language.translateString("window_powerMenu_dropDown_entry_manager"));
        powerDropDown.addOption(LevelEventPlusMain.language.translateString("window_powerMenu_dropDown_entry_operator"));
        powerDropDown.addOption(LevelEventPlusMain.language.translateString("window_powerMenu_dropDown_entry_whitelist"));
        ElementInput playerNameInput = new ElementInput(LevelEventPlusMain.language.translateString("window_powerMenu_input_playerName"));
        ElementInput worldNameInput = new ElementInput(LevelEventPlusMain.language.translateString("window_powerMenu_input_worldName"));
        window.addElement(powerDropDown);
        window.addElement(playerNameInput);
        window.addElement(worldNameInput);
        showFormWindow(player, window, FormType.Power_Delete);
    }

    //返回窗口
    public static void showReturnWindow(Player player, Boolean success, FormType formType) {
        FormWindowModal window;
        if (success) {
            window = new FormWindowModal(LevelEventPlusMain.language.translateString("window_general_title"), LevelEventPlusMain.language.translateString("window_general_content_saveSuccess"), LevelEventPlusMain.language.translateString("window_general_button_returnButton"), LevelEventPlusMain.language.translateString("window_general_button_quitButton"));
        } else {
            window = new FormWindowModal(LevelEventPlusMain.language.translateString("window_general_title"), LevelEventPlusMain.language.translateString("window_general_content_saveFailed"), LevelEventPlusMain.language.translateString("window_general_button_returnButton"), LevelEventPlusMain.language.translateString("window_general_button_quitButton"));
        }
        showFormWindow(player, window, formType);
    }

    //选择模板系统选项
    public static void showTemplateMainMenu(Player player) {
        FormWindowSimple window = new FormWindowSimple(LevelEventPlusMain.language.translateString("window_templateMain_title"), LevelEventPlusMain.language.translateString("window_templateMain_content"));
        window.addButton(new ElementButton(LevelEventPlusMain.language.translateString("window_templateMain_button_addTemplate")));
        window.addButton(new ElementButton(LevelEventPlusMain.language.translateString("window_templateMain_button_editTemplate")));
        window.addButton(new ElementButton(LevelEventPlusMain.language.translateString("window_general_button_returnButton")));
        showFormWindow(player, window, FormType.Template_Main);
    }

    //设置选择模板 -> 两类 ①选择模板创建世界 ②设置模板
    public static void showSettingChooseTemplateMenu(Player player, FormType type) {
        FormWindowSimple window = new FormWindowSimple(LevelEventPlusMain.language.translateString("window_chooseTemplate_button_title"), LevelEventPlusMain.language.translateString("window_chooseTemplate_button_content"));
        for (String buttonTitle : TemplateAPI.templateCache.keySet()) {
            window.addButton(new ElementButton(buttonTitle));
        }
        window.addButton(new ElementButton(LevelEventPlusMain.language.translateString("window_general_button_returnButton")));
        showFormWindow(player, window, type);
    }

    //添加模板
    public static void showTemplateAddMenu(Player player) {
        FormWindowCustom custom = new FormWindowCustom(LevelEventPlusMain.language.translateString("window_templateAdd_title"));
        custom.addElement(new ElementInput(LevelEventPlusMain.language.translateString("window_templateAdd_input_templateName")));
        showFormWindow(player, custom, FormType.Template_Add);
    }

    public static void showTemplateSettingMenuV2(Player player, String templateName, FormType type) {
        FormWindowCustom formWindowCustom = new FormWindowCustom(LevelEventPlusMain.language.translateString("window_edit_templateTitle"));
        for (ProtectionRuleEntry entry : ProtectionEntryMain.getProtectionRuleEntries()) {
            if (entry instanceof BooleanProtectionRuleEntry) {
                formWindowCustom.addElement(new ElementToggle(entry.getTranslation(), TemplateAPI.getTemplateBooleanSetting(templateName, entry.getCategory(), entry.getEntryName())));
            } else if (entry instanceof DropdownProtectionRuleEntry) {
                Object object = TemplateAPI.getTemplateObjectSetting(templateName, entry.getCategory(), entry.getEntryName());
                formWindowCustom.addElement(new ElementDropdown(entry.getTranslation(), ((DropdownProtectionRuleEntry) entry).getOptions(), object == null ? 0 : Math.max(((DropdownProtectionRuleEntry) entry).getOptions().indexOf(object.toString()), 0)));
            } else if (entry instanceof InputProtectionRuleEntry) {
                Object object = TemplateAPI.getTemplateObjectSetting(templateName, entry.getCategory(), entry.getEntryName());
                formWindowCustom.addElement(new ElementInput(entry.getTranslation(), object == null? "" : object.toString()));
            }
        }
        showFormWindow(player, formWindowCustom, type);
    }

    public static void showEditMenuV2(Player player, String level) {
        FormWindowCustom formWindowCustom = new FormWindowCustom(LevelEventPlusMain.language.translateString("window_edit_chooseWorldTitle"));
        for (ProtectionRuleEntry entry : ProtectionEntryMain.getProtectionRuleEntries()) {
            if (entry instanceof BooleanProtectionRuleEntry) {
                formWindowCustom.addElement(new ElementToggle(entry.getTranslation(), LevelSettingsAPI.getLevelBooleanSetting(level, entry.getCategory(), entry.getEntryName())));
            } else if (entry instanceof DropdownProtectionRuleEntry) {
                Object object = LevelSettingsAPI.getLevelObjectSetting(level, entry.getCategory(), entry.getEntryName());
                formWindowCustom.addElement(new ElementDropdown(entry.getTranslation(), ((DropdownProtectionRuleEntry) entry).getOptions(), object == null ? 0 : Math.max(((DropdownProtectionRuleEntry) entry).getOptions().indexOf(object.toString()), 0)));
            } else if (entry instanceof InputProtectionRuleEntry) {
                Object object = LevelSettingsAPI.getLevelObjectSetting(level, entry.getCategory(), entry.getEntryName());
                formWindowCustom.addElement(new ElementInput(entry.getTranslation(), object == null? "" : object.toString()));
            }
        }
        showFormWindow(player, formWindowCustom, FormType.Edit_Process);
    }
}
