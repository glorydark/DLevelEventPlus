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

    public static void loadDefaultEntries() {
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_INVENTORY, NameMapping.ENTRY_INVENTORY_CRAFTING_TABLE_OPEN, "window_edit_label_inventory_craftingTableOpen", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_INVENTORY, NameMapping.ENTRY_INVENTORY_ANVIL_OPEN, "window_edit_label_inventory_anvilOpen", true);

        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_FARM_PROTECT, "window_edit_label_world_farmProtect", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_ALL_EXPLODES, "window_edit_label_world_allExplodes", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_TNT_EXPLODES, "window_edit_label_world_tntExplodes", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_PVP, "window_edit_label_world_pvp", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_KEEP_INVENTORY, "window_edit_label_world_keepInventory", false);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_KEEP_XP, "window_edit_label_world_keepXp", false);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_ANTI_VOID, "window_edit_label_world_antiVoid", false);
        entries.addInputProtectionEntry(NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_VOID_HEIGHT, "window_edit_label_world_voidHeight", InputSaveType.INTEGER, 0);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_TIME_FLOW, "window_edit_label_world_timeFlow", true);
        entries.addDropdownProtectionEntry(NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_WEATHER, "window_edit_label_world_weather", List.of("", "clear", "thunder", "rain"), "");
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_LIGHTNING_STRIKE, "window_edit_label_world_lightningStrike", true);
        entries.addDropdownProtectionEntry(NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_WORLD_FORCE_GAMEMODE, "window_edit_label_world_forceGameMode", List.of("", "Survival", "Creative", "Adventure", "Spectator"), "");
        entries.addCustomProtectionEntry(NameMapping.CATEGORY_WORLD, NameMapping.ENTRY_PLAYER_CHEST_TRUST_LIST, "", new ArrayList<>());

        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_ALLOW_OPEN_CHEST, "window_edit_label_player_openChest", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_ALLOW_INTERACT_FRAME_BLOCK, "window_edit_label_player_interactFrameBlock", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_SNEAK, "window_edit_label_player_sneak", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_FLY, "window_edit_label_player_fly", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_SWIM, "window_edit_label_player_swim", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_GLIDE, "window_edit_label_player_glide", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_JUMP, "window_edit_label_player_jump", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_SPRINT, "window_edit_label_player_sprint", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_PICK, "window_edit_label_player_pick", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_CONSUME_ITEM, "window_edit_label_player_consumeItem", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_DROP_ITEM, "window_edit_label_player_playerDropItem", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_BED_ENTER, "window_edit_label_player_bedEnter", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_MOVE, "window_edit_label_player_move", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_EAT_FOOD, "window_edit_label_player_eatFood", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_COMMAND_PREPROCESS, "window_edit_label_player_commandPreprocess", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_GAME_MODE_CHANGE, "window_edit_label_player_gameModeChange", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_ANTI_TELEPORT, "window_edit_label_player_antiTeleport", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_INTERACT, "window_edit_label_player_interact", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_NO_FALL_DAMAGE, "window_edit_label_player_immuneToFallDamage", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_ACHIEVEMENT, "window_edit_label_player_achievement", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_BUCKET_FILL, "window_edit_label_player_bucketFill", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_BUCKET_EMPTY, "window_edit_label_player_bucketEmpty", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_HUNGER_CHANGE, "window_edit_label_player_hungerChange", true);
        entries.addCustomProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_ALLOWED_INTERACT_BLOCKS, "", new ArrayList<>());
        entries.addCustomProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_ALLOWED_USE_ITEMS, "", new ArrayList<>());
        entries.addCustomProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_BANNED_INTERACT_BLOCKS, "", new ArrayList<>());
        entries.addCustomProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_BANNED_USE_ITEMS, "", new ArrayList<>());
        entries.addCustomProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_CLEAR_ITEMS, "", new ArrayList<>());

        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_EXPLOSION, "window_edit_label_entity_entityExplosion", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_PORTAL_ENTER, "window_edit_label_entity_portalEnter", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_VEHICLE_ENTER, "window_edit_label_entity_vehicleEnter", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_BLOCK_CHANGE, "window_edit_label_entity_blockChange", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_COMBUST_BY_BLOCK, "window_edit_label_entity_combustByBlock", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_COMBUST_BY_ENTITY, "window_edit_label_entity_combustByEntity", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_LIGHTNING_ON_FIRE, "window_edit_label_entity_lightning_on_fire", true);

        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_ALLOW_PLACE_BLOCK, "window_edit_label_block_placeBlock", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_ALLOW_BREAK_BLOCK, "window_edit_label_block_breakBlock", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_BURN, "window_edit_label_block_blockBurn", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_IGNITE, "window_edit_label_block_blockIgnite", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_FALL, "window_edit_label_block_blockFall", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_GROW, "window_edit_label_block_blockGrow", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_SPREAD, "window_edit_label_block_blockSpread", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_FORM, "window_edit_label_block_blockForm", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_LEAVES_DECAY, "window_edit_label_block_leavesDecay", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_LIQUID_FLOW, "window_edit_label_block_liquidFlow", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_ITEM_FRAME_DROP_ITEM, "window_edit_label_block_itemFrameDropItem", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_SIGN_CHANGE, "window_edit_label_block_signChange", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_BLOCK_REDSTONE, "window_edit_label_block_blockRedstone", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_DROP_ITEM, "window_edit_label_block_blockDropItem", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_DROP_EXP, "window_edit_label_block_blockDropExp", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_UPDATE, "window_edit_label_block_blockUpdate", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_FADE, "window_edit_label_block_blockFade", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_PISTON_CHANGE, "window_edit_label_block_blockPistonChange", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_FROM_TO_EVENT, "window_edit_label_block_blockFromToEvent", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_SIGN_COLOR_CHANGE, "window_edit_label_block_signColorChange", true);
        entries.addCustomProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_DROP_ITEM_BLOCKS, "", new ArrayList<>());
        entries.addCustomProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_DROP_EXP_BLOCKS, "", new ArrayList<>());
        entries.addCustomProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_ALLOWED_PLACE_BLOCKS, "", new ArrayList<>());
        entries.addCustomProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_BANNED_PLACE_BLOCKS, "", new ArrayList<>());
        entries.addCustomProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_ALLOWED_BREAK_BLOCKS, "", new ArrayList<>());
        entries.addCustomProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_BANNED_BREAK_BLOCKS, "", new ArrayList<>());

        // MOT dedicated entries
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_CRAWL, "window_edit_label_player_crawl", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_PLAYER, NameMapping.ENTRY_PLAYER_EMOTE, "window_edit_label_player_emote", true);

        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_ENTITY, NameMapping.ENTRY_ENTITY_DAMAGE_BLOCKED, "window_edit_label_entity_damageBlocked", true);

        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_BELL_RING, "window_edit_label_block_bellRing", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_BLOCK_EXPLODE, "window_edit_label_block_blockExplode", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_LECTERN_DROP_BOOK, "window_edit_label_block_lecternDropBook", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_LECTERN_PAGE_CHANGE, "window_edit_label_block_lecternPageChange", true);
        entries.addBooleanProtectionEntry(NameMapping.CATEGORY_BLOCK, NameMapping.ENTRY_BLOCK_WATER_FROST, "window_edit_label_block_waterFrost", true);
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
                if (category.equals(NameMapping.CATEGORY_GAMERULE)) {
                    continue;
                }
                ConfigSection mapSection = config.getSection(category);
                for (Map.Entry<String, Object> objectEntry : mapSection.entrySet()) {
                    String entryName = objectEntry.getKey();
                    if (!ProtectionEntryMain.hasProtectionEntry(category, entryName)) {
                        // 删除前保存部分有效信息
                        if (category.equals(NameMapping.CATEGORY_PLAYER)) {
                            if (entryName.equals("CraftingTableOpen")) { // v1.2.0.9 Update
                                config.set(NameMapping.CATEGORY_INVENTORY + ".CraftingTableOpen", objectEntry.getValue());
                            }
                            if (entryName.equals("CanUseFishingHook")) {
                                boolean bool = mapSection.getBoolean("CanUseFishingHook");
                                String id = "minecraft:fishing_rod";
                                if (bool) {
                                    List<String> allowedItems = new ArrayList<>(mapSection.getStringList(NameMapping.ENTRY_PLAYER_ALLOWED_USE_ITEMS));
                                    if (!allowedItems.contains(id)) {
                                        allowedItems.add(id);
                                    }
                                    mapSection.set(NameMapping.ENTRY_PLAYER_ALLOWED_USE_ITEMS, allowedItems);
                                } else {
                                    List<String> bannedItems = new ArrayList<>(mapSection.getStringList(NameMapping.ENTRY_PLAYER_BANNED_USE_ITEMS));
                                    if (!bannedItems.contains(id)) {
                                        bannedItems.add(id);
                                    }
                                    mapSection.set(NameMapping.ENTRY_PLAYER_BANNED_USE_ITEMS, bannedItems);
                                }
                            }
                            if (entryName.equals("AllowPlaceBlocks")) {
                                mapSection.set(NameMapping.ENTRY_BLOCK_ALLOWED_PLACE_BLOCKS, mapSection.get(entryName));
                            }
                            if (entryName.equals("AllowBreakBlocks")) {
                                mapSection.set(NameMapping.ENTRY_BLOCK_ALLOWED_BREAK_BLOCKS, mapSection.get(entryName));
                            }
                            if (entryName.equals("AllowInteractBlocks")) {
                                mapSection.set(NameMapping.ENTRY_PLAYER_ALLOWED_INTERACT_BLOCKS, mapSection.get(entryName));
                            }
                            config.set(category, mapSection);
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
            config.save();
            return true;
        }
        return false;
    }
}
