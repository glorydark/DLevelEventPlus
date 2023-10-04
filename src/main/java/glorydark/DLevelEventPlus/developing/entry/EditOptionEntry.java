package glorydark.DLevelEventPlus.developing.entry;

import glorydark.DLevelEventPlus.developing.Developing;

/**
 * @author glorydark
 * @date {2023/10/4} {16:02}
 */
@Developing
public enum EditOptionEntry {

    FarmProtect("World", "FarmProtect", EntryType.BOOLEAN),
    AllExplodes("World", "AllExplodes", EntryType.BOOLEAN),
    TntExplodes("World", "TntExplodes", EntryType.BOOLEAN),
    PVP("World", "PVP", EntryType.BOOLEAN),
    KeepInventory("World", "KeepInventory", EntryType.BOOLEAN),
    KeepXp("World", "KeepXp", EntryType.BOOLEAN),
    AllowOpenChest("Player", "AllowOpenChest", EntryType.BOOLEAN),
    CanUseFishingHook("Player", "CanUseFishingHook", EntryType.BOOLEAN),
    AllowInteractFrameBlock("Player", "AllowInteractFrameBlock", EntryType.BOOLEAN),
    Sneak("Player", "Sneak", EntryType.BOOLEAN),
    Fly("Player", "Fly", EntryType.BOOLEAN),
    Swim("Player", "Swim", EntryType.BOOLEAN),
    Glide("Player", "Glide", EntryType.BOOLEAN),
    Jump("Player", "Jump", EntryType.BOOLEAN),
    Sprint("Player", "Sprint", EntryType.BOOLEAN),
    Pick("Player", "Pick", EntryType.BOOLEAN),
    ConsumeItem("Player", "ConsumeItem", EntryType.BOOLEAN),
    DropItem("Player", "DropItem", EntryType.BOOLEAN),
    BedEnter("Player", "BedEnter", EntryType.BOOLEAN),
    Move("Player", "Move", EntryType.BOOLEAN),
    EatFood("Player", "EatFood", EntryType.BOOLEAN),
    CommandPreprocess("Player", "CommandPreprocess", EntryType.BOOLEAN),
    GameModeChange("Player", "GameModeChange", EntryType.BOOLEAN),
    AntiTeleport("Player", "AntiTeleport", EntryType.BOOLEAN),
    Interact("Player", "Interact", EntryType.BOOLEAN),
    NoFallDamage("Player", "NoFallDamage", EntryType.BOOLEAN),
    Explosion("Entity", "Explosion", EntryType.BOOLEAN),
    PortalEnter("Entity", "PortalEnter", EntryType.BOOLEAN),
    AllowPlaceBlock("Block", "AllowPlaceBlock", EntryType.BOOLEAN),
    AllowBreakBlock("Block", "AllowBreakBlock", EntryType.BOOLEAN),
    BlockBurn("Block", "Burn", EntryType.BOOLEAN),
    BlockIgnite("Block", "Ignite", EntryType.BOOLEAN),
    BlockFall("Block", "Fall", EntryType.BOOLEAN),
    BlockGrow("Block", "Grow", EntryType.BOOLEAN),
    BlockSpread("Block", "Spread", EntryType.BOOLEAN),
    BlockForm("Block", "Form", EntryType.BOOLEAN),
    LeavesDecay("Block", "LeavesDecay", EntryType.BOOLEAN),
    LiquidFlow("Block", "LiquidFlow", EntryType.BOOLEAN),
    ItemFrameDropItem("Block", "ItemFrameDropItem", EntryType.BOOLEAN),
    SignChange("Block", "SignChange", EntryType.BOOLEAN),
    BlockRedStone("Block", "BlockRedstone", EntryType.BOOLEAN),
    BlockDropItem("Block", "DropItem", EntryType.BOOLEAN),
    BlockDropExp("Block", "DropExp", EntryType.BOOLEAN),
    BlockUpdate("Block", "Update", EntryType.BOOLEAN),
    BlockFade("Block", "Fade", EntryType.BOOLEAN),
    BlockPistonChange("Block", "PistonChange", EntryType.BOOLEAN),
    BlockFromToEvent("Block", "FromToEvent", EntryType.BOOLEAN),
    AntiVoid("World", "AntiVoid", EntryType.BOOLEAN),
    VoidHeight("Block", "VoidHeight", EntryType.STRING);

    final String key;

    final String subKey;

    final EntryType type;

    EditOptionEntry(String key, String subKey, EntryType type) {
        this.key = key;
        this.subKey = subKey;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public String getSubKey() {
        return subKey;
    }

    public EntryType getType() {
        return type;
    }
}
