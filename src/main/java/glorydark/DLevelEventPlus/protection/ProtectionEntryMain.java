package glorydark.DLevelEventPlus.protection;

import cn.nukkit.utils.Config;
import glorydark.DLevelEventPlus.LevelEventPlusMain;
import glorydark.DLevelEventPlus.protection.rule.ProtectionRuleEntries;
import glorydark.DLevelEventPlus.protection.rule.ProtectionRuleEntry;
import glorydark.DLevelEventPlus.protection.type.InputSaveType;

import java.io.File;
import java.util.List;

/**
 * @author glorydark
 * @date {2023/12/20} {10:42}
 */
public class ProtectionEntryMain {

    protected static ProtectionRuleEntries entries = new ProtectionRuleEntries();

    public static void loadDefaultEntries() {
        entries.addBooleanProtectionEntry("World", "FarmProtect", "window_edit_label_farmProtect", true);
        entries.addBooleanProtectionEntry("World", "AllExplodes", "window_edit_label_allExplodes", true);
        entries.addBooleanProtectionEntry("World", "TntExplodes", "window_edit_label_tntExplodes", true);
        entries.addBooleanProtectionEntry("World", "PVP", "window_edit_label_pvp", true);
        entries.addBooleanProtectionEntry("World", "KeepInventory", "window_edit_label_keepInventory", false);
        entries.addBooleanProtectionEntry("World", "KeepXp", "window_edit_label_keepXp", false);
        entries.addBooleanProtectionEntry("World", "AntiVoid", "window_edit_label_antiVoid", false);
        entries.addInputProtectionEntry("World", "VoidHeight", "window_edit_label_voidHeight", InputSaveType.INTEGER, 0);
        entries.addBooleanProtectionEntry("World", "TimeFlow", "window_edit_label_timeFlow", true);
        entries.addDropdownProtectionEntry("World", "Weather", "window_edit_label_weather", List.of("", "clear", "thunder", "rain"), "");

        entries.addBooleanProtectionEntry("Player", "AllowOpenChest", "window_edit_label_openChest", true);
        entries.addBooleanProtectionEntry("Player", "CanUseFishingHook", "window_edit_label_useFishingHook", true);
        entries.addBooleanProtectionEntry("Player", "AllowInteractFrameBlock", "window_edit_label_interactFrameBlock", true);
        entries.addBooleanProtectionEntry("Player", "Sneak", "window_edit_label_sneak", true);
        entries.addBooleanProtectionEntry("Player", "Fly", "window_edit_label_fly", true);
        entries.addBooleanProtectionEntry("Player", "Swim", "window_edit_label_swim", true);
        entries.addBooleanProtectionEntry("Player", "Glide", "window_edit_label_glide", true);
        entries.addBooleanProtectionEntry("Player", "Jump", "window_edit_label_jump", true);
        entries.addBooleanProtectionEntry("Player", "Sprint", "window_edit_label_sprint", true);
        entries.addBooleanProtectionEntry("Player", "Pick", "window_edit_label_pick", true);
        entries.addBooleanProtectionEntry("Player", "ConsumeItem", "window_edit_label_consumeItem", true);
        entries.addBooleanProtectionEntry("Player", "DropItem", "window_edit_label_playerDropItem", true);
        entries.addBooleanProtectionEntry("Player", "BedEnter", "window_edit_label_bedEnter", true);
        entries.addBooleanProtectionEntry("Player", "Move", "window_edit_label_move", true);
        entries.addBooleanProtectionEntry("Player", "EatFood", "window_edit_label_eatFood", true);
        entries.addBooleanProtectionEntry("Player", "CommandPreprocess", "window_edit_label_commandPreprocess", true);
        entries.addBooleanProtectionEntry("Player", "GameModeChange", "window_edit_label_gameModeChange", true);
        entries.addBooleanProtectionEntry("Player", "AntiTeleport", "window_edit_label_antiTeleport", true);
        entries.addBooleanProtectionEntry("Player", "Interact", "window_edit_label_interact", true);
        entries.addBooleanProtectionEntry("Player", "NoFallDamage", "window_edit_label_immuneToFallDamage", true);

        entries.addBooleanProtectionEntry("Entity", "Explosion", "window_edit_label_entityExplosion", true);
        entries.addBooleanProtectionEntry("Entity", "PortalEnter", "window_edit_label_portalEnter", true);

        entries.addBooleanProtectionEntry("Block", "AllowPlaceBlock", "window_edit_label_placeBlock", true);
        entries.addBooleanProtectionEntry("Block", "AllowBreakBlock", "window_edit_label_breakBlock", true);
        entries.addBooleanProtectionEntry("Block", "Burn", "window_edit_label_blockBurn", true);
        entries.addBooleanProtectionEntry("Block", "Ignite", "window_edit_label_blockIgnite", true);
        entries.addBooleanProtectionEntry("Block", "Fall", "window_edit_label_blockFall", true);
        entries.addBooleanProtectionEntry("Block", "Grow", "window_edit_label_blockGrow", true);
        entries.addBooleanProtectionEntry("Block", "Spread", "window_edit_label_blockSpread", true);
        entries.addBooleanProtectionEntry("Block", "Form", "window_edit_label_blockForm", true);
        entries.addBooleanProtectionEntry("Block", "LeavesDecay", "window_edit_label_leavesDecay", true);
        entries.addBooleanProtectionEntry("Block", "LiquidFlow", "window_edit_label_liquidFlow", true);
        entries.addBooleanProtectionEntry("Block", "ItemFrameDropItem", "window_edit_label_itemFrameDropItem", true);
        entries.addBooleanProtectionEntry("Block", "SignChange", "window_edit_label_signChange", true);
        entries.addBooleanProtectionEntry("Block", "BlockRedstone", "window_edit_label_blockRedstone", true);
        entries.addBooleanProtectionEntry("Block", "DropItem", "window_edit_label_blockDropItem", true);
        entries.addBooleanProtectionEntry("Block", "DropExp", "window_edit_label_blockDropExp", true);
        entries.addBooleanProtectionEntry("Block", "Update", "window_edit_label_blockUpdate", true);
        entries.addBooleanProtectionEntry("Block", "Fade", "window_edit_label_blockFade", true);
        entries.addBooleanProtectionEntry("Block", "PistonChange", "window_edit_label_blockPistonChange", true);
        entries.addBooleanProtectionEntry("Block", "FromToEvent", "window_edit_label_blockFromToEvent", true);
    }

    public static List<ProtectionRuleEntry> getProtectionRuleEntries() {
        return entries.getEntries();
    }

    public static void updateFiles() {
        File dict = new File(LevelEventPlusMain.path + "/worlds/");
        if (dict.isDirectory()) {
            File[] files = dict.listFiles();
            if (files != null) {
                for (File file : files) {
                    updateFile(file);
                }
            }
        }

        dict = new File(LevelEventPlusMain.path + "/templates/");
        if (dict.isDirectory()) {
            File[] files = dict.listFiles();
            if (files != null) {
                for (File file : files) {
                    updateFile(file);
                }
            }
        }
    }

    public static void updateFile(File file) {
        if (file.isFile()) {
            Config config = new Config(file, Config.YAML);
            for (ProtectionRuleEntry protectionRuleEntry : getProtectionRuleEntries()) {
                String key = protectionRuleEntry.getCategory() + "." + protectionRuleEntry.getEntryName();
                if (!config.exists(key)) {
                    config.set(key, protectionRuleEntry.getDefaultValue());
                }
            }
            config.save();
            // todo: add checks for deprecated keys
        }
    }

    public static boolean createFile(File file) {
        if (!file.exists()) {
            Config config = new Config(file, Config.YAML);
            for (ProtectionRuleEntry protectionRuleEntry : getProtectionRuleEntries()) {
                String key = protectionRuleEntry.getCategory() + "." + protectionRuleEntry.getEntryName();
                config.set(key, protectionRuleEntry.getDefaultValue());
            }
            config.save();
            return true;
        }
        return false;
    }
}
