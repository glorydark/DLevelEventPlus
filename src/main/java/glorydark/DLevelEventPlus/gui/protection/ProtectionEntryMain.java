package glorydark.DLevelEventPlus.gui.protection;

import glorydark.DLevelEventPlus.gui.protection.rule.ProtectionRuleEntries;
import glorydark.DLevelEventPlus.gui.protection.type.InputSaveType;

import java.util.List;

/**
 * @author glorydark
 * @date {2023/12/20} {10:42}
 */
public class ProtectionEntryMain {

    protected static ProtectionRuleEntries entries = new ProtectionRuleEntries();

    public static void loadDefaultEntries() {
        entries.addBooleanProtectionEntry("World", "FarmProtect", "window_edit_label_farmProtect");
        entries.addBooleanProtectionEntry("World", "AllExplodes", "window_edit_label_allExplodes");
        entries.addBooleanProtectionEntry("World", "TntExplodes", "window_edit_label_tntExplodes");
        entries.addBooleanProtectionEntry("World", "PVP", "window_edit_label_pvp");
        entries.addBooleanProtectionEntry("World", "KeepInventory", "window_edit_label_keepInventory");
        entries.addBooleanProtectionEntry("World", "KeepXp", "window_edit_label_keepXp");
        entries.addBooleanProtectionEntry("World", "AntiVoid", "window_edit_label_antiVoid");
        entries.addInputProtectionEntry("World", "VoidHeight", "window_edit_label_voidHeight", InputSaveType.INTEGER);
        entries.addBooleanProtectionEntry("World", "TimeFlow", "window_edit_label_timeFlow");
        entries.addDropdownProtectionEntry("World", "Weather", "window_edit_label_weather", List.of("", "clear", "thunder", "rain"));

        entries.addBooleanProtectionEntry("Player", "AllowOpenChest", "window_edit_label_openChest");
        entries.addBooleanProtectionEntry("Player", "CanUseFishingHook", "window_edit_label_useFishingHook");
        entries.addBooleanProtectionEntry("Player", "AllowInteractFrameBlock", "window_edit_label_interactFrameBlock");
        entries.addBooleanProtectionEntry("Player", "Sneak", "window_edit_label_sneak");
        entries.addBooleanProtectionEntry("Player", "Fly", "window_edit_label_fly");
        entries.addBooleanProtectionEntry("Player", "Swim", "window_edit_label_swim");
        entries.addBooleanProtectionEntry("Player", "Glide", "window_edit_label_glide");
        entries.addBooleanProtectionEntry("Player", "Jump", "window_edit_label_jump");
        entries.addBooleanProtectionEntry("Player", "Sprint", "window_edit_label_sprint");
        entries.addBooleanProtectionEntry("Player", "Pick", "window_edit_label_pick");
        entries.addBooleanProtectionEntry("Player", "ConsumeItem", "window_edit_label_consumeItem");
        entries.addBooleanProtectionEntry("Player", "DropItem", "window_edit_label_playerDropItem");
        entries.addBooleanProtectionEntry("Player", "BedEnter", "window_edit_label_bedEnter");
        entries.addBooleanProtectionEntry("Player", "Move", "window_edit_label_move");
        entries.addBooleanProtectionEntry("Player", "EatFood", "window_edit_label_eatFood");
        entries.addBooleanProtectionEntry("Player", "CommandPreprocess", "window_edit_label_commandPreprocess");
        entries.addBooleanProtectionEntry("Player", "GameModeChange", "window_edit_label_gameModeChange");
        entries.addBooleanProtectionEntry("Player", "AntiTeleport", "window_edit_label_antiTeleport");
        entries.addBooleanProtectionEntry("Player", "Interact", "window_edit_label_interact");
        entries.addBooleanProtectionEntry("Player", "NoFallDamage", "window_edit_label_immuneToFallDamage");

        entries.addBooleanProtectionEntry("Entity", "Explosion", "window_edit_label_entityExplosion");
        entries.addBooleanProtectionEntry("Entity", "PortalEnter", "window_edit_label_portalEnter");

        entries.addBooleanProtectionEntry("Block", "AllowPlaceBlock", "window_edit_label_placeBlock");
        entries.addBooleanProtectionEntry("Block", "AllowBreakBlock", "window_edit_label_breakBlock");
        entries.addBooleanProtectionEntry("Block", "Burn", "window_edit_label_blockBurn");
        entries.addBooleanProtectionEntry("Block", "Ignite", "window_edit_label_blockIgnite");
        entries.addBooleanProtectionEntry("Block", "Fall", "window_edit_label_blockFall");
        entries.addBooleanProtectionEntry("Block", "Grow", "window_edit_label_blockGrow");
        entries.addBooleanProtectionEntry("Block", "Spread", "window_edit_label_blockSpread");
        entries.addBooleanProtectionEntry("Block", "Form", "window_edit_label_blockForm");
        entries.addBooleanProtectionEntry("Block", "LeavesDecay", "window_edit_label_leavesDecay");
        entries.addBooleanProtectionEntry("Block", "LiquidFlow", "window_edit_label_liquidFlow");
        entries.addBooleanProtectionEntry("Block", "ItemFrameDropItem", "window_edit_label_itemFrameDropItem");
        entries.addBooleanProtectionEntry("Block", "SignChange", "window_edit_label_signChange");
        entries.addBooleanProtectionEntry("Block", "BlockRedstone", "window_edit_label_blockRedstone");
        entries.addBooleanProtectionEntry("Block", "DropItem", "window_edit_label_blockDropItem");
        entries.addBooleanProtectionEntry("Block", "DropExp", "window_edit_label_blockDropExp");
        entries.addBooleanProtectionEntry("Block", "Update", "window_edit_label_blockUpdate");
        entries.addBooleanProtectionEntry("Block", "Fade", "window_edit_label_blockFade");
        entries.addBooleanProtectionEntry("Block", "PistonChange", "window_edit_label_blockPistonChange");
        entries.addBooleanProtectionEntry("Block", "FromToEvent", "window_edit_label_blockFromToEvent");
    }

    public static ProtectionRuleEntries getEntries() {
        return entries;
    }
}
