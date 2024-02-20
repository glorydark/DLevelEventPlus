package glorydark.DLevelEventPlus.gui;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import glorydark.DLevelEventPlus.LevelEventPlusMain;
import glorydark.DLevelEventPlus.api.LevelSettingsAPI;
import glorydark.DLevelEventPlus.api.PermissionAPI;
import glorydark.DLevelEventPlus.api.TemplateAPI;
import glorydark.DLevelEventPlus.protection.ProtectionEntryMain;
import glorydark.DLevelEventPlus.protection.rule.BooleanProtectionRuleEntry;
import glorydark.DLevelEventPlus.protection.rule.DropdownProtectionRuleEntry;
import glorydark.DLevelEventPlus.protection.rule.InputProtectionRuleEntry;
import glorydark.DLevelEventPlus.protection.rule.ProtectionRuleEntry;

import java.io.File;
import java.util.LinkedHashMap;

/**
 * @author glorydark
 * @date {2024/1/1} {18:22}
 */
public class FormEventListener implements Listener {

    @EventHandler
    public void PlayerFormRespondedEvent(PlayerFormRespondedEvent event) {
        Player p = event.getPlayer();
        FormWindow window = event.getWindow();
        if (p == null || window == null) {
            return;
        }
        FormType formType = FormMain.UI_CACHE.containsKey(p) ? FormMain.UI_CACHE.get(p).get(event.getFormID()) : null;
        if (formType == null) {
            return;
        }
        FormMain.UI_CACHE.get(p).remove(event.getFormID());
        if (event.getResponse() == null) {
            return;
        }
        if (window instanceof FormWindowSimple) {
            this.formWindowSimpleOnClick(p, (FormWindowSimple) window, formType);
        }
        if (window instanceof FormWindowCustom) {
            this.formWindowCustomOnClick(p, (FormWindowCustom) window, formType);
        }
        if (window instanceof FormWindowModal) {
            this.formWindowModalOnClick(p, (FormWindowModal) window, formType);
        }
    }

    private void formWindowSimpleOnClick(Player p, FormWindowSimple window, FormType formType) {
        if (window.getResponse() == null) {
            return;
        }
        switch (formType) {
            case Admin_Main:
                switch (window.getResponse().getClickedButtonId()) {
                    case 0:
                        FormMain.showSettingChooseWorldMenu(p);
                        break;
                    case 1:
                        FormMain.showPowerMainMenu(p);
                        break;
                    case 2:
                        LevelEventPlusMain.saveAllConfig();
                        FormMain.showReturnWindow(p, true, FormType.Return_toMainMenu);
                        break;
                    case 3:
                        LevelEventPlusMain.loadAllLevelConfig();
                        LevelEventPlusMain.loadTemplateConfig();
                        FormMain.showReturnWindow(p, true, FormType.Return_toMainMenu);
                        break;
                    case 4:
                        FormMain.showTemplateMainMenu(p);
                        break;
                }
                break;
            case Power_Main:
                switch (window.getResponse().getClickedButtonId()) {
                    case 0:
                        FormMain.showPowerAddMenu(p);
                        break;
                    case 1:
                        FormMain.showPowerDeleteMenu(p);
                        break;
                    case 2:
                        FormMain.showMainMenu(p);
                        break;
                }
                break;
            case Edit_ChooseWorld:
                String text = window.getResponse().getClickedButton().getText();
                if (!text.equals("")) {
                    if (!text.equals("返回")) {
                        String worldName = window.getResponse().getClickedButton().getText();
                        if (LevelSettingsAPI.configCache.containsKey(text)) {
                            FormMain.showEditMenuV2(p, worldName);
                        } else {
                            FormMain.showSettingChooseTemplateMenu(p, FormType.Template_ChooseTemplateForNewConfig);
                        }
                        LevelEventPlusMain.selectCache.put(p, text);
                    } else {
                        FormMain.showMainMenu(p);
                    }
                }
                break;
            case Template_ChooseTemplateForEdit:
                text = window.getResponse().getClickedButton().getText();
                if (!text.equals("")) {
                    if (!text.equals("返回")) {
                        FormMain.showTemplateSettingMenuV2(p, text, FormType.Template_EditProcess);
                        LevelEventPlusMain.selectCache.put(p, text);
                    } else {
                        FormMain.showMainMenu(p);
                    }
                }
                break;
            case Template_ChooseTemplateForNewConfig:
                text = window.getResponse().getClickedButton().getText();
                if (!text.equals("")) {
                    if (!text.equals("返回")) {
                        FormMain.showTemplateSettingMenuV2(p, text, FormType.Edit_Process);
                    } else {
                        FormMain.showMainMenu(p);
                    }
                }
                break;
            case Template_Main:
                switch (window.getResponse().getClickedButtonId()) {
                    case 0:
                        FormMain.showTemplateAddMenu(p);
                        break;
                    case 1:
                        FormMain.showSettingChooseTemplateMenu(p, FormType.Template_ChooseTemplateForEdit);
                        break;
                    case 2:
                        FormMain.showMainMenu(p);
                        break;
                }
                break;
        }
    }

    private void formWindowCustomOnClick(Player p, FormWindowCustom window, FormType formType) {
        if (window.getResponse() == null) {
            return;
        }
        FormResponseCustom responses = window.getResponse();
        switch (formType) {
            case Edit_Process:
                String levelname = LevelEventPlusMain.selectCache.get(p);
                if (levelname == null) {
                    LevelEventPlusMain.plugin.getLogger().warning("Error: Can not find " + p.getName() + "'s selected world.");
                    return;
                }
                if (!LevelSettingsAPI.configCache.containsKey(levelname)) {
                    LevelSettingsAPI.configCache.put(levelname, new LinkedHashMap<>());
                }
                int id = 0;
                for (ProtectionRuleEntry entry : ProtectionEntryMain.getProtectionRuleEntries()) {
                    if (entry instanceof BooleanProtectionRuleEntry) {
                        LevelSettingsAPI.setLevelSetting(levelname, entry.getCategory(), entry.getEntryName(), responses.getToggleResponse(id));
                    } else if (entry instanceof DropdownProtectionRuleEntry) {
                        LevelSettingsAPI.setLevelSetting(levelname, entry.getCategory(), entry.getEntryName(), responses.getDropdownResponse(id).getElementContent());
                    } else if (entry instanceof InputProtectionRuleEntry) {
                        switch (((InputProtectionRuleEntry) entry).getSaveType()) {
                            case STRING:
                                LevelSettingsAPI.setLevelSetting(levelname, entry.getCategory(), entry.getEntryName(), responses.getInputResponse(id));
                                break;
                            case INTEGER:
                                if (!responses.getInputResponse(id).isEmpty()) {
                                    try {
                                        LevelSettingsAPI.setLevelSetting(levelname, entry.getCategory(), entry.getEntryName(), Integer.parseInt(responses.getInputResponse(id)));
                                    } catch (NumberFormatException e) {
                                        p.sendMessage("Wrong Number Format in the entry " + entry.getEntryName() + "in the category " + entry.getCategory());
                                    }
                                }
                                break;
                        }
                    }
                    id++;
                }
                Config config = new Config(LevelEventPlusMain.path + "/worlds/" + levelname + ".yml", Config.YAML);
                config.setAll(LevelSettingsAPI.configCache.getOrDefault(levelname, new LinkedHashMap<>()));
                config.save();
                FormMain.showReturnWindow(p, true, FormType.Return_toMainMenu);
                break;
            case Template_EditProcess:
                responses = window.getResponse();
                String select = LevelEventPlusMain.selectCache.get(p);
                id = 0;
                for (ProtectionRuleEntry entry : ProtectionEntryMain.getProtectionRuleEntries()) {
                    if (entry instanceof BooleanProtectionRuleEntry) {
                        TemplateAPI.setTemplateSetting(select, entry.getCategory(), entry.getEntryName(), responses.getToggleResponse(id));
                    } else if (entry instanceof DropdownProtectionRuleEntry) {
                        TemplateAPI.setTemplateSetting(select, entry.getCategory(), entry.getEntryName(), responses.getDropdownResponse(id).getElementContent());
                    } else if (entry instanceof InputProtectionRuleEntry) {
                        switch (((InputProtectionRuleEntry) entry).getSaveType()) {
                            case STRING:
                                TemplateAPI.setTemplateSetting(select, entry.getCategory(), entry.getEntryName(), responses.getInputResponse(id));
                                break;
                            case INTEGER:
                                if (!responses.getInputResponse(id).isEmpty()) {
                                    try {
                                        TemplateAPI.setTemplateSetting(select, entry.getCategory(), entry.getEntryName(), Integer.parseInt(responses.getInputResponse(id)));
                                    } catch (NumberFormatException e) {
                                        p.sendMessage("Wrong Number Format in the entry " + entry.getEntryName() + "in the category " + entry.getCategory());
                                    }
                                }
                                break;
                        }
                    }
                    id++;
                }
                config = new Config(LevelEventPlusMain.path + "/templates/" + select + ".yml", Config.YAML);
                config.setAll(TemplateAPI.templateCache.getOrDefault(select, new LinkedHashMap<>()));
                config.save();
                FormMain.showReturnWindow(p, true, FormType.Return_toMainMenu);
                break;
            case Template_Add:
                String text = responses.getInputResponse(0);
                if (!text.replace(" ", "").equals("")) {
                    if (!TemplateAPI.templateCache.containsKey(text)) {
                        File file = new File(LevelEventPlusMain.path + "/templates/" + text + ".yml");
                        ProtectionEntryMain.createFile(file);
                        TemplateAPI.templateCache.put(text, (LinkedHashMap<String, Object>) new Config(file, Config.YAML).getAll());
                        FormMain.showReturnWindow(p, true, FormType.Return_toMainMenu);
                    } else {
                        FormMain.showReturnWindow(p, false, FormType.Return_toMainMenu);
                    }
                }
                break;
            case Power_Add:
                responses = window.getResponse();
                select = responses.getDropdownResponse(0).getElementContent();
                String player = responses.getInputResponse(1);
                String world = responses.getInputResponse(2);
                boolean isValidForOperatorOrWhitelist = !player.isEmpty() && !player.trim().equals("") && world != null && !world.trim().equals("");
                switch (select) {
                    case "管理员":
                        if (!player.trim().equals("")) {
                            PermissionAPI.adminList(p, PermissionAPI.OperatePermissionType.ADD, player);
                            FormMain.showReturnWindow(p, true, FormType.Return_toPowerMenu);
                        }
                        break;
                    case "操作员":
                        if (isValidForOperatorOrWhitelist) {
                            PermissionAPI.operatorList(p, PermissionAPI.OperatePermissionType.ADD, player, p.getLevel().getName());
                            FormMain.showReturnWindow(p, true, FormType.Return_toPowerMenu);
                        }
                        break;
                    case "白名单":
                        if (isValidForOperatorOrWhitelist) {
                            PermissionAPI.whiteList(p, PermissionAPI.OperatePermissionType.ADD, player, p.getLevel().getName());
                            FormMain.showReturnWindow(p, true, FormType.Return_toPowerMenu);
                        }
                        break;
                }
                break;
            case Power_Delete:
                responses = window.getResponse();
                select = responses.getDropdownResponse(0).getElementContent();
                player = responses.getInputResponse(1);
                world = responses.getInputResponse(2);
                isValidForOperatorOrWhitelist = player != null && !player.replace(" ", "").equals("") && world != null && !world.replace(" ", "").equals("");
                switch (select) {
                    case "管理员":
                        if (player != null && !player.replace(" ", "").equals("")) {
                            PermissionAPI.adminList(p, PermissionAPI.OperatePermissionType.REMOVE, player);
                            FormMain.showReturnWindow(p, true, FormType.Return_toPowerMenu);
                        }
                        break;
                    case "操作员":
                        if (isValidForOperatorOrWhitelist) {
                            PermissionAPI.operatorList(p, PermissionAPI.OperatePermissionType.REMOVE, player, world);
                            FormMain.showReturnWindow(p, true, FormType.Return_toPowerMenu);
                        }
                        break;
                    case "白名单":
                        if (isValidForOperatorOrWhitelist) {
                            PermissionAPI.whiteList(p, PermissionAPI.OperatePermissionType.REMOVE, player, world);
                            FormMain.showReturnWindow(p, true, FormType.Return_toPowerMenu);
                        }
                        break;
                }
                break;
        }
    }

    private void formWindowModalOnClick(Player p, FormWindowModal window, FormType formType) {
        if (window.getResponse() == null) {
            return;
        }
        switch (formType) {
            case Return_toMainMenu:
                if (window.getResponse().getClickedButtonId() == 0) {
                    if (PermissionAPI.isAdmin(p)) {
                        FormMain.showMainMenu(p);
                    }
                }
                break;
            case Return_toPowerMenu:
                if (window.getResponse().getClickedButtonId() == 0) {
                    if (PermissionAPI.isAdmin(p)) {
                        FormMain.showPowerMainMenu(p);
                    }
                }
                break;
        }
    }
}
