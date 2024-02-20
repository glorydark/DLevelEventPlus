package glorydark.DLevelEventPlus.protection;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import glorydark.DLevelEventPlus.LevelEventPlusMain;
import glorydark.DLevelEventPlus.protection.rule.ProtectionRuleEntries;
import glorydark.DLevelEventPlus.protection.rule.ProtectionRuleEntry;
import glorydark.DLevelEventPlus.protection.type.InputSaveType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author glorydark
 * @date {2023/12/20} {10:42}
 */
public class ProtectionEntryMain {

    protected static ProtectionRuleEntries entries = new ProtectionRuleEntries();

    public static final List<String> supplementListEntry = List.of("Block.AntiPlaceBlocks", "Block.AntiBreakBlocks",
            "Block.CanBreakBlocks", "Block.CanPlaceBlocks",
            "Block.DropItemBlocks", "Block.DropExpBlocks",
            "Player.ChestTrustList",
            "Player.BannedUseItems", "Player.BannedInteractBlocks",
            "Player.ClearItems");

    public static void loadDefaultEntries() {
        entries.addBooleanProtectionEntry("Inventory", "CraftingTableOpen", "window_edit_label_inventory_craftingTableOpen", true);
        entries.addBooleanProtectionEntry("Inventory", "AnvilOpen", "window_edit_label_inventory_anvilOpen", true);

        entries.addBooleanProtectionEntry("World", "FarmProtect", "window_edit_label_world_farmProtect", true);
        entries.addBooleanProtectionEntry("World", "AllExplodes", "window_edit_label_world_allExplodes", true);
        entries.addBooleanProtectionEntry("World", "TntExplodes", "window_edit_label_world_tntExplodes", true);
        entries.addBooleanProtectionEntry("World", "PVP", "window_edit_label_world_pvp", true);
        entries.addBooleanProtectionEntry("World", "KeepInventory", "window_edit_label_world_keepInventory", false);
        entries.addBooleanProtectionEntry("World", "KeepXp", "window_edit_label_world_keepXp", false);
        entries.addBooleanProtectionEntry("World", "AntiVoid", "window_edit_label_world_antiVoid", false);
        entries.addInputProtectionEntry("World", "VoidHeight", "window_edit_label_world_voidHeight", InputSaveType.INTEGER, 0);
        entries.addBooleanProtectionEntry("World", "TimeFlow", "window_edit_label_world_timeFlow", true);
        entries.addDropdownProtectionEntry("World", "Weather", "window_edit_label_world_weather", List.of("", "clear", "thunder", "rain"), "");
        entries.addBooleanProtectionEntry("World", "LightningStrike", "window_edit_label_world_lightningStrike", true);
        entries.addDropdownProtectionEntry("World", "ForceGameMode", "window_edit_label_world_forceGameMode", List.of("", "Survival", "Creative", "Adventure", "Spectator"), "");

        entries.addBooleanProtectionEntry("Player", "AllowOpenChest", "window_edit_label_player_openChest", true);
        entries.addBooleanProtectionEntry("Player", "CanUseFishingHook", "window_edit_label_player_useFishingHook", true);
        entries.addBooleanProtectionEntry("Player", "AllowInteractFrameBlock", "window_edit_label_player_interactFrameBlock", true);
        entries.addBooleanProtectionEntry("Player", "Sneak", "window_edit_label_player_sneak", true);
        entries.addBooleanProtectionEntry("Player", "Fly", "window_edit_label_player_fly", true);
        entries.addBooleanProtectionEntry("Player", "Swim", "window_edit_label_player_swim", true);
        entries.addBooleanProtectionEntry("Player", "Glide", "window_edit_label_player_glide", true);
        entries.addBooleanProtectionEntry("Player", "Jump", "window_edit_label_player_jump", true);
        entries.addBooleanProtectionEntry("Player", "Sprint", "window_edit_label_player_sprint", true);
        entries.addBooleanProtectionEntry("Player", "Pick", "window_edit_label_player_pick", true);
        entries.addBooleanProtectionEntry("Player", "ConsumeItem", "window_edit_label_player_consumeItem", true);
        entries.addBooleanProtectionEntry("Player", "DropItem", "window_edit_label_player_playerDropItem", true);
        entries.addBooleanProtectionEntry("Player", "BedEnter", "window_edit_label_player_bedEnter", true);
        entries.addBooleanProtectionEntry("Player", "Move", "window_edit_label_player_move", true);
        entries.addBooleanProtectionEntry("Player", "EatFood", "window_edit_label_player_eatFood", true);
        entries.addBooleanProtectionEntry("Player", "CommandPreprocess", "window_edit_label_player_commandPreprocess", true);
        entries.addBooleanProtectionEntry("Player", "GameModeChange", "window_edit_label_player_gameModeChange", true);
        entries.addBooleanProtectionEntry("Player", "AntiTeleport", "window_edit_label_player_antiTeleport", true);
        entries.addBooleanProtectionEntry("Player", "Interact", "window_edit_label_player_interact", true);
        entries.addBooleanProtectionEntry("Player", "NoFallDamage", "window_edit_label_player_immuneToFallDamage", true);
        entries.addBooleanProtectionEntry("Player", "Achievement", "window_edit_label_player_achievement", true);
        entries.addBooleanProtectionEntry("Player", "BucketFill", "window_edit_label_player_bucketFill", true);
        entries.addBooleanProtectionEntry("Player", "BucketEmpty", "window_edit_label_player_bucketEmpty", true);

        entries.addBooleanProtectionEntry("Entity", "Explosion", "window_edit_label_entity_entityExplosion", true);
        entries.addBooleanProtectionEntry("Entity", "PortalEnter", "window_edit_label_entity_portalEnter", true);
        entries.addBooleanProtectionEntry("Entity", "BlockChange", "window_edit_label_entity_blockChange", true);
        entries.addBooleanProtectionEntry("Entity", "CombustByBlock", "window_edit_label_entity_combustByBlock", true);
        entries.addBooleanProtectionEntry("Entity", "CombustByEntity", "window_edit_label_entity_combustByEntity", true);

        entries.addBooleanProtectionEntry("Block", "AllowPlaceBlock", "window_edit_label_block_placeBlock", true);
        entries.addBooleanProtectionEntry("Block", "AllowBreakBlock", "window_edit_label_block_breakBlock", true);
        entries.addBooleanProtectionEntry("Block", "Burn", "window_edit_label_block_blockBurn", true);
        entries.addBooleanProtectionEntry("Block", "Ignite", "window_edit_label_block_blockIgnite", true);
        entries.addBooleanProtectionEntry("Block", "Fall", "window_edit_label_block_blockFall", true);
        entries.addBooleanProtectionEntry("Block", "Grow", "window_edit_label_block_blockGrow", true);
        entries.addBooleanProtectionEntry("Block", "Spread", "window_edit_label_block_blockSpread", true);
        entries.addBooleanProtectionEntry("Block", "Form", "window_edit_label_block_blockForm", true);
        entries.addBooleanProtectionEntry("Block", "LeavesDecay", "window_edit_label_block_leavesDecay", true);
        entries.addBooleanProtectionEntry("Block", "LiquidFlow", "window_edit_label_block_liquidFlow", true);
        entries.addBooleanProtectionEntry("Block", "ItemFrameDropItem", "window_edit_label_block_itemFrameDropItem", true);
        entries.addBooleanProtectionEntry("Block", "SignChange", "window_edit_label_block_signChange", true);
        entries.addBooleanProtectionEntry("Block", "BlockRedstone", "window_edit_label_block_blockRedstone", true);
        entries.addBooleanProtectionEntry("Block", "DropItem", "window_edit_label_block_blockDropItem", true);
        entries.addBooleanProtectionEntry("Block", "DropExp", "window_edit_label_block_blockDropExp", true);
        entries.addBooleanProtectionEntry("Block", "Update", "window_edit_label_block_blockUpdate", true);
        entries.addBooleanProtectionEntry("Block", "Fade", "window_edit_label_block_blockFade", true);
        entries.addBooleanProtectionEntry("Block", "PistonChange", "window_edit_label_block_blockPistonChange", true);
        entries.addBooleanProtectionEntry("Block", "FromToEvent", "window_edit_label_block_blockFromToEvent", true);
        entries.addBooleanProtectionEntry("Block", "SignColorChange", "window_edit_label_block_signColorChange", true);

        // MOT dedicated entries
        entries.addBooleanProtectionEntry("Player", "Crawl", "window_edit_label_player_crawl", true);
        entries.addBooleanProtectionEntry("Player", "Emote", "window_edit_label_player_emote", true);

        entries.addBooleanProtectionEntry("Entity", "DamageBlocked", "window_edit_label_entity_damageBlocked", true);

        entries.addBooleanProtectionEntry("Block", "BellRing", "window_edit_label_block_bellRing", true);
        entries.addBooleanProtectionEntry("Block", "BlockExplode", "window_edit_label_block_blockExplode", true);
        entries.addBooleanProtectionEntry("Block", "LecternDropBook", "window_edit_label_block_lecternDropBook", true);
        entries.addBooleanProtectionEntry("Block", "LecternPageChange", "window_edit_label_block_lecternPageChange", true);
        entries.addBooleanProtectionEntry("Block", "WaterFrost", "window_edit_label_block_waterFrost", true);
    }

    public static List<ProtectionRuleEntry> getProtectionRuleEntries() {
        return entries.getEntries();
    }

    public static boolean hasProtectionEntry(String category, String entryName) {
        return entries.getEntries().stream().anyMatch(protectionRuleEntry ->
                protectionRuleEntry.getCategory().equals(category) && protectionRuleEntry.getEntryName().equals(entryName));
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
            // add checks for deprecated keys
            for (String category : config.getKeys(false)) {
                if (category.equals("GameRule")) {
                    continue;
                }
                ConfigSection mapSection = config.getSection(category);
                for (Map.Entry<String, Object> objectEntry : mapSection.entrySet()) {
                    String entryName = objectEntry.getKey();
                    if (!ProtectionEntryMain.hasProtectionEntry(category, entryName) && !supplementListEntry.contains(category + "." + entryName)) {
                        if (category.equals("Player") && entryName.equals("CraftingTableOpen")) { // v1.2.0.9 Update
                            config.set("Inventory.CraftingTableOpen", objectEntry.getValue());
                        }
                        config.remove(category + "." + entryName);
                    }
                }
            }
            // add missing keys
            for (ProtectionRuleEntry protectionRuleEntry : getProtectionRuleEntries()) {
                String key = protectionRuleEntry.getCategory() + "." + protectionRuleEntry.getEntryName();
                if (!config.exists(key)) {
                    config.set(key, protectionRuleEntry.getDefaultValue());
                }
            }
            for (String key : supplementListEntry) {
                if (!config.exists(key)) {
                    config.set(key, new ArrayList<>());
                }
            }
            config.save();
        }
    }

    public static boolean createFile(File file) {
        if (!file.exists()) {
            Config config = new Config(file, Config.YAML);
            for (ProtectionRuleEntry protectionRuleEntry : getProtectionRuleEntries()) {
                String key = protectionRuleEntry.getCategory() + "." + protectionRuleEntry.getEntryName();
                config.set(key, protectionRuleEntry.getDefaultValue());
            }
            for (String key : supplementListEntry) {
                config.set(key, new ArrayList<>());
            }
            config.save();
            return true;
        }
        return false;
    }
}
