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
import glorydark.DLevelEventPlus.gui.protection.rule.ProtectionRuleEntries;
import glorydark.DLevelEventPlus.gui.protection.rule.BooleanProtectionRuleEntry;
import glorydark.DLevelEventPlus.gui.protection.rule.DropdownProtectionRuleEntry;
import glorydark.DLevelEventPlus.gui.protection.rule.InputProtectionRuleEntry;
import glorydark.DLevelEventPlus.gui.protection.rule.ProtectionRuleEntry;
import glorydark.DLevelEventPlus.utils.ConfigUtil;

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
            PlayerEventListener.showFormWindow(player, window, GuiType.Admin_Main);
        }
        if (ConfigUtil.isOperator(player, player.getLevel())) {
            showEditMenuV2(player, player.getLevel().getName());
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

    public static void showTemplateSettingMenuV2(Player player, String templateName, GuiType type) {
        FormWindowCustom formWindowCustom = new FormWindowCustom(MainClass.language.translateString("window_edit_chooseWorldTitle"));
        for (ProtectionRuleEntry entry : ProtectionRuleEntries.getEntries()) {
            if (entry instanceof BooleanProtectionRuleEntry) {
                formWindowCustom.addElement(new ElementToggle(entry.getTranslation(), ConfigUtil.getTemplateBooleanInit(templateName, entry.getCategory(), entry.getEntryName())));
            } else if (entry instanceof DropdownProtectionRuleEntry) {
                Object object = ConfigUtil.getTemplateBooleanInit(templateName, entry.getCategory(), entry.getEntryName());
                formWindowCustom.addElement(new ElementDropdown(entry.getTranslation(), ((DropdownProtectionRuleEntry) entry).getOptions(), object == null? 0: Math.max(((DropdownProtectionRuleEntry) entry).getOptions().indexOf(object.toString()), 0)));
            } else if (entry instanceof InputProtectionRuleEntry) {
                formWindowCustom.addElement(new ElementInput(entry.getTranslation(), ConfigUtil.getTemplateInit(templateName, entry.getCategory(), entry.getEntryName()).toString()));
            }
        }
        PlayerEventListener.showFormWindow(player, formWindowCustom, type);
    }

    public static void showEditMenuV2(Player player, String level) {
        FormWindowCustom formWindowCustom = new FormWindowCustom(MainClass.language.translateString("window_edit_chooseWorldTitle"));
        for (ProtectionRuleEntry entry : ProtectionRuleEntries.getEntries()) {
            if (entry instanceof BooleanProtectionRuleEntry) {
                formWindowCustom.addElement(new ElementToggle(entry.getTranslation(), MainClass.getLevelSettingBooleanInit(level, entry.getCategory(), entry.getEntryName())));
            } else if (entry instanceof DropdownProtectionRuleEntry) {
                Object object = MainClass.getLevelSettingInit(level, entry.getCategory(), entry.getEntryName());
                formWindowCustom.addElement(new ElementDropdown(entry.getTranslation(), ((DropdownProtectionRuleEntry) entry).getOptions(), object == null? 0: Math.max(((DropdownProtectionRuleEntry) entry).getOptions().indexOf(object.toString()), 0)));
            } else if (entry instanceof InputProtectionRuleEntry) {
                formWindowCustom.addElement(new ElementInput(entry.getTranslation(), MainClass.getLevelSettingInit(level, entry.getCategory(), entry.getEntryName()).toString()));
            }
        }
        PlayerEventListener.showFormWindow(player, formWindowCustom, GuiType.Edit_Process);
    }
}
