package glorydark.DLevelEventPlus.event;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.ProjectileLaunchEvent;
import cn.nukkit.event.inventory.InventoryPickupItemEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemFishingRod;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlayerActionPacket;
import cn.nukkit.utils.Config;
import glorydark.DLevelEventPlus.MainClass;
import glorydark.DLevelEventPlus.gui.GuiMain;
import glorydark.DLevelEventPlus.gui.GuiType;
import glorydark.DLevelEventPlus.utils.AdventureSettingUtils;
import glorydark.DLevelEventPlus.utils.ConfigUtil;
import glorydark.DLevelEventPlus.utils.ItemUtils;
import glorydark.DLevelEventPlus.utils.LiquidItem;

import java.util.*;

import static cn.nukkit.network.protocol.PlayerActionPacket.ACTION_JUMP;

public class PlayerEventListener implements Listener {

    public static final HashMap<Player, HashMap<Integer, GuiType>> UI_CACHE = new HashMap<>();

    public static void showFormWindow(Player player, FormWindow window, GuiType guiType) {
        UI_CACHE.computeIfAbsent(player, i -> new HashMap<>()).put(player.showFormWindow(window), guiType);
    }

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Player player = event.getPlayer();
        Level level = event.getPlayer().getLevel();
        Block block = event.getBlock();
        Item item = event.getPlayer().getInventory().getItemInHand();
        Boolean interact = MainClass.getLevelBooleanInit(level.getName(), "Player", "Interact");
        if (interact != null && !interact) {
            if (MainClass.show_actionbar_text) {
                player.sendActionBar(ConfigUtil.getLang("Tips", "AntiInteract"));
            }
            event.setCancelled(true);
            return;
        }

        if (block != null) {
            if (block.getId() == 389 || block.getId() == -339) {
                Boolean bool = MainClass.getLevelBooleanInit(level.getName(), "Player", "AllowInteractFrameBlock");
                if (bool != null && !bool) {
                    if (MainClass.show_actionbar_text) {
                        player.sendActionBar(ConfigUtil.getLang("Tips", "AntiTouchFlame"));
                    }
                    event.setCancelled(true);
                }
            }

            //玩家导致耕地踩踏
            if (block.getId() == Block.FARMLAND) {
                Boolean bool = MainClass.getLevelBooleanInit(level.getName(), "World", "FarmProtect");
                if (bool != null && event.getAction() == PlayerInteractEvent.Action.PHYSICAL) {
                    if (bool) {
                        if (MainClass.show_actionbar_text) {
                            player.sendActionBar(ConfigUtil.getLang("Tips", "AntiTrampleFarmland"));
                        }
                        event.setCancelled(true);
                    }
                }
            }

            if (block.getId() == Block.CHEST) {
                Boolean bool = MainClass.getLevelBooleanInit(level.getName(), "Player", "AllowOpenChest");
                if (bool != null && !bool) {
                    if (MainClass.getLevelStringListInit(level.getName(), "Player", "ChestTrustList") == null) {
                        return;
                    }
                    List<String> list = MainClass.getLevelStringListInit(level.getName(), "Player", "ChestTrustList");
                    List<Position> positionArrayList = new ArrayList<>();
                    for (String str : Objects.requireNonNull(list)) {
                        List<String> strspl = Arrays.asList(str.split(":"));
                        if (strspl.size() == 3) {
                            positionArrayList.add(new Position(Double.parseDouble(strspl.get(0)), Double.parseDouble(strspl.get(1)), Double.parseDouble(strspl.get(2))));
                        }
                    }
                    if (!positionArrayList.contains(block.getLocation())) {
                        if (MainClass.show_actionbar_text) {
                            String title = ConfigUtil.getLang("Tips", "AntiUseChest").replace("%position%", String.valueOf(block.getLocation()));
                            player.sendActionBar(title);
                        }
                        event.setCancelled(true);
                    }
                }
            }

            if (MainClass.getLevelStringListInit(level.getName(), "Player", "BannedInteractBlocks").stream().anyMatch(s -> ItemUtils.isEqual(s, block))) {
                if (MainClass.show_actionbar_text) {
                    player.sendActionBar(ConfigUtil.getLang("Tips", "BlockInteractBanned"));
                }
                event.setCancelled(true);
            }

            if (LiquidItem.isLiquidItem(item)) {
                Boolean bool = MainClass.getLevelBooleanInit(block.getLevel().getName(), "Block", "AllowPlaceBlock");
                if (bool == null) {
                    return;
                }
                if (bool) {
                    return;
                }
                if (MainClass.show_actionbar_text) {
                    event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiPlaceBlock"));
                }
                event.setCancelled(true);
            }
        }

        if (item != null) {
            if (item instanceof ItemFishingRod) {
                Boolean bool = MainClass.getLevelBooleanInit(level.getName(), "Player", "CanUseFishingHook");
                if (bool != null && !bool) {
                    if (MainClass.show_actionbar_text) {
                        player.sendActionBar(ConfigUtil.getLang("Tips", "AntiUseFishingRod"));
                    }
                    event.setCancelled(true);
                }
            }

            List<String> strings = MainClass.getLevelStringListInit(level.getName(), "Player", "BannedUseItems");
            if (strings.stream().anyMatch(s -> ItemUtils.isEqual(s, item))) {
                if (MainClass.show_actionbar_text) {
                    player.sendActionBar(ConfigUtil.getLang("Tips", "ItemBanned"));
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerTeleportEvent(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Boolean bool = MainClass.getLevelBooleanInit(player.getLevel().getName(), "Player", "AntiTeleport");
        if (bool == null) {
            return;
        }
        if (ConfigUtil.isAdmin(player)) {
            return;
        }
        if (ConfigUtil.isOperator(player, player.getLevel())) {
            return;
        }
        Level toLevel = event.getTo().getLevel();
        if (ConfigUtil.isWhiteListed(player, toLevel)) {
            return;
        }
        if (event.getFrom().getLevel() == event.getTo().getLevel()) {
            if (bool) {
                if (MainClass.show_actionbar_text) {
                    event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiTeleport").replace("%s", toLevel.getName()));
                }
                event.setCancelled(true);
            }
        } else {
            AdventureSettingUtils.updatePlayerAdventureSettings(player, toLevel);
        }
    }

    @EventHandler
    public void PlayerBlockPickEvent(PlayerBlockPickEvent event) {
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player", "Pick");
        if (bool == null) {
            return;
        }
        if (!bool) {
            if (MainClass.show_actionbar_text) {
                event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiPickUpItem"));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void InventoryPickupItemEvent(InventoryPickupItemEvent event) {
        for (Player p : event.getViewers()) {
            Boolean bool = MainClass.getLevelBooleanInit(p.getLevel().getName(), "Player", "Pick");
            if (bool == null) {
                return;
            }
            if (ConfigUtil.isAdmin(p)) {
                return;
            }
            if (ConfigUtil.isOperator(p, p.getLevel())) {
                return;
            }
            if (!bool) {
                if (MainClass.show_actionbar_text) {
                    p.sendActionBar(ConfigUtil.getLang("Tips", "AntiPickUpItem"));
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player", "ConsumeItem");
        if (bool == null) {
            return;
        }
        if (!bool) {
            if (MainClass.show_actionbar_text) {
                event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiConsumeItem"));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerBedEnterEvent(PlayerBedEnterEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player", "BedEnter");
        if (bool == null) {
            return;
        }
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        if (!bool) {
            if (MainClass.show_actionbar_text) {
                event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiBedEnter"));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player", "CommandPreprocess");
        if (bool == null) {
            return;
        }
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        if (event.getPlayer() == null) {
            return;
        }
        if (bool) {
            List<String> strings = MainClass.getLevelStringListInit(event.getPlayer().getLevel().getName(), "Player", "BanCommands");
            if (strings == null) {
                return;
            }
            if (strings.size() > 0) {
                String commandtext = event.getMessage();
                for (String verify : strings) {
                    if (commandtext.startsWith("/" + verify)) {
                        if (MainClass.show_actionbar_text) {
                            event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiCommand"));
                        }
                        event.setCancelled(true);
                    }
                }
            }
        } else {
            if (MainClass.show_actionbar_text) {
                event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiAllCommand"));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerGameModeChangeEvent(PlayerGameModeChangeEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player", "GameModeChange");
        if (bool == null) {
            return;
        }
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        if (!bool) {
            if (MainClass.show_actionbar_text) {
                event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiChangeGamemode"));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (ConfigUtil.isAdmin(player)) {
            return;
        }
        if (ConfigUtil.isOperator(player, player.getLevel())) {
            return;
        }
        Boolean bool = MainClass.getLevelBooleanInit(player.getLevel().getName(), "Player", "Move");
        if (bool != null) {
            if (!bool) {
                if (MainClass.show_actionbar_text) {
                    player.sendActionBar(ConfigUtil.getLang("Tips", "AntiMove"));
                }
                event.setCancelled(true);
            }
        }

        Boolean bool1 = MainClass.getLevelBooleanInit(player.getLevel().getName(), "World", "AntiVoid");
        if (bool1 != null) {
            if (bool1) {
                Object o = MainClass.getLevelSettingInit(player.getLevel().getName(), "World", "VoidHeight");
                if (o != null) {
                    int voidHeight = Integer.parseInt((String) MainClass.getLevelSettingInit(player.getLevel().getName(), "World", "VoidHeight"));
                    if (event.getTo().getFloorY() <= voidHeight) {
                        if (MainClass.show_actionbar_text) {
                            player.sendActionBar(ConfigUtil.getLang("Tips", "AntiVoid"));
                        }
                        event.setTo(player.getLevel().getSpawnLocation().getLocation());
                    }
                }
            }
        }

        if (MainClass.experimental) {
            Boolean bool2 = MainClass.getLevelBooleanInit(player.getLevel().getName(), "Player", "Fly");
            if (bool2 == null) {
                return;
            }
            if (!bool2) {
                Location location = player.getLocation();
                boolean isFlying = true;
                for (int i = 1; i <= 4; i++) {
                    Block block = player.getLevel().getBlock(location.add(0, -i, 0));
                    if (!(block instanceof BlockAir)) {
                        isFlying = false;
                    }
                }
                if (isFlying) {
                    if (MainClass.show_actionbar_text) {
                        player.sendActionBar(ConfigUtil.getLang("Tips", "AntiFly"));
                    }
                    event.getPlayer().setMotion(new Vector3(0, -1, 0));
                }
            }
        }
    }


    @EventHandler
    public void PlayerEatFoodEvent(PlayerEatFoodEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player", "EatFood");
        if (bool == null) {
            return;
        }
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        if (!bool) {
            if (MainClass.show_actionbar_text) {
                event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiEat"));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerFoodLevelChangeEvent(PlayerFoodLevelChangeEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player", "HungerChange");
        if (bool == null) {
            return;
        }
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        if (!bool) {
            event.getPlayer().getFoodData().reset();
        }
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event) {
        Boolean bool1 = MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(), "World", "KeepXp");
        Boolean bool2 = MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(), "World", "KeepInventory");
        if (bool1 != null) {
            event.setKeepExperience(bool1);
        }
        if (bool2 != null) {
            event.setKeepInventory(bool2);
        }
    }

    @EventHandler
    public void PlayerFormRespondedEvent(PlayerFormRespondedEvent event) {
        Player p = event.getPlayer();
        FormWindow window = event.getWindow();
        if (p == null || window == null) {
            return;
        }
        GuiType guiType = UI_CACHE.containsKey(p) ? UI_CACHE.get(p).get(event.getFormID()) : null;
        if (guiType == null) {
            return;
        }
        UI_CACHE.get(p).remove(event.getFormID());
        if (event.getResponse() == null) {
            return;
        }
        if (window instanceof FormWindowSimple) {
            this.formWindowSimpleOnClick(p, (FormWindowSimple) window, guiType);
        }
        if (window instanceof FormWindowCustom) {
            this.formWindowCustomOnClick(p, (FormWindowCustom) window, guiType);
        }
        if (window instanceof FormWindowModal) {
            this.formWindowModalOnClick(p, (FormWindowModal) window, guiType);
        }
    }

    private void formWindowSimpleOnClick(Player p, FormWindowSimple window, GuiType guiType) {
        if (window.getResponse() == null) {
            return;
        }
        switch (guiType) {
            case ADMIN_Main:
                switch (window.getResponse().getClickedButtonId()) {
                    case 0:
                        GuiMain.showSettingChooseWorldMenu(p);
                        break;
                    case 1:
                        GuiMain.showPowerMainMenu(p);
                        break;
                    case 2:
                        MainClass.saveAllConfig();
                        GuiMain.showReturnWindow(p, true, GuiType.Return_toMainMenu);
                        break;
                    case 3:
                        MainClass.loadAllLevelConfig();
                        MainClass.loadTemplateConfig();
                        MainClass.loadLang();
                        GuiMain.showReturnWindow(p, true, GuiType.Return_toMainMenu);
                        break;
                    case 4:
                        GuiMain.showTemplateMainMenu(p);
                        break;
                }
                break;
            case Power_Main:
                switch (window.getResponse().getClickedButtonId()) {
                    case 0:
                        GuiMain.showPowerAddMenu(p);
                        break;
                    case 1:
                        GuiMain.showPowerDeleteMenu(p);
                        break;
                    case 2:
                        GuiMain.showMainMenu(p);
                        break;
                }
                break;
            case Edit_ChooseWorld:
                String text = window.getResponse().getClickedButton().getText();
                if (!text.equals("")) {
                    if (!text.equals("返回")) {
                        String worldName = window.getResponse().getClickedButton().getText();
                        if (MainClass.configCache.containsKey(text)) {
                            GuiMain.showEditMenu(p, worldName);
                        } else {
                            GuiMain.showSettingChooseTemplateMenu(p, GuiType.Template_ChooseTemplateForNewConfig);
                        }
                        MainClass.selectCache.put(p, text);
                    } else {
                        GuiMain.showMainMenu(p);
                    }
                }
                break;
            case Template_ChooseTemplateForEdit:
                text = window.getResponse().getClickedButton().getText();
                if (!text.equals("")) {
                    if (!text.equals("返回")) {
                        GuiMain.showTemplateSettingMenu(p, text, GuiType.Template_EditProcess);
                        MainClass.selectCache.put(p, text);
                    } else {
                        GuiMain.showMainMenu(p);
                    }
                }
                break;
            case Template_ChooseTemplateForNewConfig:
                text = window.getResponse().getClickedButton().getText();
                if (!text.equals("")) {
                    if (!text.equals("返回")) {
                        GuiMain.showTemplateSettingMenu(p, text, GuiType.Template_CreateConfigProcess);
                    } else {
                        GuiMain.showMainMenu(p);
                    }
                }
                break;
            case Template_Main:
                switch (window.getResponse().getClickedButtonId()) {
                    case 0:
                        GuiMain.showTemplateAddMenu(p);
                        break;
                    case 1:
                        GuiMain.showSettingChooseTemplateMenu(p, GuiType.Template_ChooseTemplateForEdit);
                        break;
                    case 2:
                        GuiMain.showMainMenu(p);
                        break;
                }
                break;
        }
    }

    private void formWindowCustomOnClick(Player p, FormWindowCustom window, GuiType guiType) {
        if (window.getResponse() == null) {
            return;
        }
        FormResponseCustom responses = window.getResponse();
        switch (guiType) {
            case Edit_Process:
            case Template_CreateConfigProcess:
                String levelname = MainClass.selectCache.get(p);
                if (levelname == null) {
                    MainClass.plugin.getLogger().warning("Error: Can not find " + p.getName() + "'s selected world.");
                    return;
                }
                if (!MainClass.configCache.containsKey(levelname)) {
                    MainClass.configCache.put(levelname, new LinkedHashMap<>());
                }
                MainClass.setLevelInit(levelname, "World", "FarmProtect", responses.getToggleResponse(0));
                MainClass.setLevelInit(levelname, "World", "AllExplodes", responses.getToggleResponse(1));
                MainClass.setLevelInit(levelname, "World", "TntExplodes", responses.getToggleResponse(2));
                MainClass.setLevelInit(levelname, "World", "PVP", responses.getToggleResponse(3));
                MainClass.setLevelInit(levelname, "World", "KeepInventory", responses.getToggleResponse(4));
                MainClass.setLevelInit(levelname, "World", "KeepXp", responses.getToggleResponse(5));
                MainClass.setLevelInit(levelname, "Player", "AllowOpenChest", responses.getToggleResponse(6));
                MainClass.setLevelInit(levelname, "Player", "CanUseFishingHook", responses.getToggleResponse(7));
                MainClass.setLevelInit(levelname, "Player", "AllowInteractFrameBlock", responses.getToggleResponse(8));
                MainClass.setLevelInit(levelname, "Player", "Sneak", responses.getToggleResponse(9));
                MainClass.setLevelInit(levelname, "Player", "Fly", responses.getToggleResponse(10));
                MainClass.setLevelInit(levelname, "Player", "Swim", responses.getToggleResponse(11));
                MainClass.setLevelInit(levelname, "Player", "Glide", responses.getToggleResponse(12));
                MainClass.setLevelInit(levelname, "Player", "Jump", responses.getToggleResponse(13));
                MainClass.setLevelInit(levelname, "Player", "Sprint", responses.getToggleResponse(14));
                MainClass.setLevelInit(levelname, "Player", "Pick", responses.getToggleResponse(15));
                MainClass.setLevelInit(levelname, "Player", "ConsumeItem", responses.getToggleResponse(16));
                MainClass.setLevelInit(levelname, "Player", "DropItem", responses.getToggleResponse(17));
                MainClass.setLevelInit(levelname, "Player", "BedEnter", responses.getToggleResponse(18));
                MainClass.setLevelInit(levelname, "Player", "Move", responses.getToggleResponse(19));
                MainClass.setLevelInit(levelname, "Player", "EatFood", responses.getToggleResponse(20));
                MainClass.setLevelInit(levelname, "Player", "CommandPreprocess", responses.getToggleResponse(21));
                MainClass.setLevelInit(levelname, "Player", "GameModeChange", responses.getToggleResponse(22));
                MainClass.setLevelInit(levelname, "Player", "AntiTeleport", responses.getToggleResponse(23));
                MainClass.setLevelInit(levelname, "Player", "Interact", responses.getToggleResponse(24));
                MainClass.setLevelInit(levelname, "Player", "NoFallDamage", responses.getToggleResponse(25));
                MainClass.setLevelInit(levelname, "Entity", "Explosion", responses.getToggleResponse(26));
                MainClass.setLevelInit(levelname, "Entity", "PortalEnter", responses.getToggleResponse(27));
                MainClass.setLevelInit(levelname, "Block", "AllowPlaceBlock", responses.getToggleResponse(28));
                MainClass.setLevelInit(levelname, "Block", "AllowBreakBlock", responses.getToggleResponse(29));
                MainClass.setLevelInit(levelname, "Block", "Burn", responses.getToggleResponse(30));
                MainClass.setLevelInit(levelname, "Block", "Ignite", responses.getToggleResponse(31));
                MainClass.setLevelInit(levelname, "Block", "Fall", responses.getToggleResponse(32));
                MainClass.setLevelInit(levelname, "Block", "Grow", responses.getToggleResponse(33));
                MainClass.setLevelInit(levelname, "Block", "Spread", responses.getToggleResponse(34));
                MainClass.setLevelInit(levelname, "Block", "Form", responses.getToggleResponse(35));
                MainClass.setLevelInit(levelname, "Block", "LeavesDecay", responses.getToggleResponse(36));
                MainClass.setLevelInit(levelname, "Block", "LiquidFlow", responses.getToggleResponse(37));
                MainClass.setLevelInit(levelname, "Block", "ItemFrameDropItem", responses.getToggleResponse(38));
                MainClass.setLevelInit(levelname, "Block", "SignChange", responses.getToggleResponse(39));
                MainClass.setLevelInit(levelname, "Block", "BlockRedstone", responses.getToggleResponse(40));
                MainClass.setLevelInit(levelname, "Block", "DropItem", responses.getToggleResponse(41));
                MainClass.setLevelInit(levelname, "Block", "DropExp", responses.getToggleResponse(42));
                MainClass.setLevelInit(levelname, "Block", "Update", responses.getToggleResponse(43));
                MainClass.setLevelInit(levelname, "Block", "Fade", responses.getToggleResponse(44));
                MainClass.setLevelInit(levelname, "Block", "PistonChange", responses.getToggleResponse(45));
                MainClass.setLevelInit(levelname, "Block", "FromToEvent", responses.getToggleResponse(46));
                MainClass.setLevelInit(levelname, "World", "AntiVoid", responses.getToggleResponse(47));
                MainClass.setLevelInit(levelname, "World", "VoidHeight", responses.getInputResponse(48));
                boolean timeFlow = responses.getToggleResponse(49);
                if (!timeFlow) {
                    Level level = Server.getInstance().getLevelByName(levelname);
                    if (level != null) {
                        level.stopTime();
                    }
                }
                MainClass.setLevelInit(levelname, "World", "TimeFlow", timeFlow);
                int weatherIndex = responses.getDropdownResponse(50).getElementID();
                if (weatherIndex != 0) {
                    MainClass.setLevelInit(levelname, "World", "Weather", responses.getDropdownResponse(50).getElementContent());
                }
                Config config = new Config(MainClass.path + "/worlds/" + levelname + ".yml", Config.YAML);
                config.setAll(MainClass.configCache.getOrDefault(levelname, new LinkedHashMap<>()));
                config.save();
                GuiMain.showReturnWindow(p, true, GuiType.Return_toMainMenu);
                break;
            case Template_EditProcess:
                responses = window.getResponse();
                String select = MainClass.selectCache.get(p);
                ConfigUtil.setTemplateInit(select, "World", "FarmProtect", responses.getToggleResponse(0));
                ConfigUtil.setTemplateInit(select, "World", "AllExplodes", responses.getToggleResponse(1));
                ConfigUtil.setTemplateInit(select, "World", "TntExplodes", responses.getToggleResponse(2));
                ConfigUtil.setTemplateInit(select, "World", "PVP", responses.getToggleResponse(3));
                ConfigUtil.setTemplateInit(select, "World", "KeepInventory", responses.getToggleResponse(4));
                ConfigUtil.setTemplateInit(select, "World", "KeepXp", responses.getToggleResponse(5));
                ConfigUtil.setTemplateInit(select, "Player", "AllowOpenChest", responses.getToggleResponse(6));
                ConfigUtil.setTemplateInit(select, "Player", "CanUseFishingHook", responses.getToggleResponse(7));
                ConfigUtil.setTemplateInit(select, "Player", "AllowInteractFrameBlock", responses.getToggleResponse(8));
                ConfigUtil.setTemplateInit(select, "Player", "Sneak", responses.getToggleResponse(9));
                ConfigUtil.setTemplateInit(select, "Player", "Fly", responses.getToggleResponse(10));
                ConfigUtil.setTemplateInit(select, "Player", "Swim", responses.getToggleResponse(11));
                ConfigUtil.setTemplateInit(select, "Player", "Glide", responses.getToggleResponse(12));
                ConfigUtil.setTemplateInit(select, "Player", "Jump", responses.getToggleResponse(13));
                ConfigUtil.setTemplateInit(select, "Player", "Sprint", responses.getToggleResponse(14));
                ConfigUtil.setTemplateInit(select, "Player", "Pick", responses.getToggleResponse(15));
                ConfigUtil.setTemplateInit(select, "Player", "ConsumeItem", responses.getToggleResponse(16));
                ConfigUtil.setTemplateInit(select, "Player", "DropItem", responses.getToggleResponse(17));
                ConfigUtil.setTemplateInit(select, "Player", "BedEnter", responses.getToggleResponse(18));
                ConfigUtil.setTemplateInit(select, "Player", "Move", responses.getToggleResponse(19));
                ConfigUtil.setTemplateInit(select, "Player", "EatFood", responses.getToggleResponse(20));
                ConfigUtil.setTemplateInit(select, "Player", "CommandPreprocess", responses.getToggleResponse(21));
                ConfigUtil.setTemplateInit(select, "Player", "GameModeChange", responses.getToggleResponse(22));
                ConfigUtil.setTemplateInit(select, "Player", "AntiTeleport", responses.getToggleResponse(23));
                ConfigUtil.setTemplateInit(select, "Player", "Interact", responses.getToggleResponse(24));
                ConfigUtil.setTemplateInit(select, "Player", "NoFallDamage", responses.getToggleResponse(25));
                ConfigUtil.setTemplateInit(select, "Entity", "Explosion", responses.getToggleResponse(26));
                ConfigUtil.setTemplateInit(select, "Entity", "PortalEnter", responses.getToggleResponse(27));
                ConfigUtil.setTemplateInit(select, "Block", "AllowPlaceBlock", responses.getToggleResponse(28));
                ConfigUtil.setTemplateInit(select, "Block", "AllowBreakBlock", responses.getToggleResponse(29));
                ConfigUtil.setTemplateInit(select, "Block", "Burn", responses.getToggleResponse(30));
                ConfigUtil.setTemplateInit(select, "Block", "Ignite", responses.getToggleResponse(31));
                ConfigUtil.setTemplateInit(select, "Block", "Fall", responses.getToggleResponse(32));
                ConfigUtil.setTemplateInit(select, "Block", "Grow", responses.getToggleResponse(33));
                ConfigUtil.setTemplateInit(select, "Block", "Spread", responses.getToggleResponse(34));
                ConfigUtil.setTemplateInit(select, "Block", "Form", responses.getToggleResponse(35));
                ConfigUtil.setTemplateInit(select, "Block", "LeavesDecay", responses.getToggleResponse(36));
                ConfigUtil.setTemplateInit(select, "Block", "LiquidFlow", responses.getToggleResponse(37));
                ConfigUtil.setTemplateInit(select, "Block", "ItemFrameDropItem", responses.getToggleResponse(38));
                ConfigUtil.setTemplateInit(select, "Block", "SignChange", responses.getToggleResponse(39));
                ConfigUtil.setTemplateInit(select, "Block", "BlockRedstone", responses.getToggleResponse(40));
                ConfigUtil.setTemplateInit(select, "Block", "DropItem", responses.getToggleResponse(41));
                ConfigUtil.setTemplateInit(select, "Block", "DropExp", responses.getToggleResponse(42));
                ConfigUtil.setTemplateInit(select, "Block", "Update", responses.getToggleResponse(43));
                ConfigUtil.setTemplateInit(select, "Block", "Fade", responses.getToggleResponse(44));
                ConfigUtil.setTemplateInit(select, "Block", "PistonChange", responses.getToggleResponse(45));
                ConfigUtil.setTemplateInit(select, "Block", "FromToEvent", responses.getToggleResponse(46));
                ConfigUtil.setTemplateInit(select, "World", "AntiVoid", responses.getToggleResponse(47));
                ConfigUtil.setTemplateInit(select, "World", "VoidHeight", responses.getInputResponse(48));
                ConfigUtil.setTemplateInit(select, "World", "TimeFlow", responses.getToggleResponse(49));
                weatherIndex = responses.getDropdownResponse(50).getElementID();
                if (weatherIndex != 0) {
                    ConfigUtil.setTemplateInit(select, "World", "Weather", responses.getDropdownResponse(50).getElementContent());
                }
                config = new Config(MainClass.path + "/templates/" + select + ".yml", Config.YAML);
                config.setAll(ConfigUtil.TemplateCache.getOrDefault(select, new LinkedHashMap<>()));
                config.save();
                GuiMain.showReturnWindow(p, true, GuiType.Return_toMainMenu);
                break;
            case Template_Add:
                String text = responses.getInputResponse(0);
                if (!text.replace(" ", "").equals("")) {
                    if (!ConfigUtil.TemplateCache.containsKey(text)) {
                        MainClass.defaultConfigUtils.writeAll(1, text);
                        ConfigUtil.TemplateCache.put(text, (LinkedHashMap<String, Object>) new Config(MainClass.path + "/templates/" + text + ".yml", Config.YAML).getAll());
                        GuiMain.showReturnWindow(p, true, GuiType.Return_toMainMenu);
                    } else {
                        GuiMain.showReturnWindow(p, false, GuiType.Return_toMainMenu);
                    }
                }
                break;
            case Power_Add:
                responses = window.getResponse();
                select = responses.getDropdownResponse(0).getElementContent();
                String player = responses.getInputResponse(1);
                String world = responses.getInputResponse(2);
                boolean b = player != null && !player.replace(" ", "").equals("") && world != null && !world.replace(" ", "").equals("");
                switch (select) {
                    case "管理员":
                        if (player != null && !player.replace(" ", "").equals("")) {
                            GuiMain.showReturnWindow(p, true, GuiType.Return_toPowerMenu);
                        }
                        break;
                    case "操作员":
                    case "白名单":
                        if (b) {
                            GuiMain.showReturnWindow(p, true, GuiType.Return_toPowerMenu);
                        }
                        break;
                }
                break;
            case Power_Delete:
                responses = window.getResponse();
                select = responses.getDropdownResponse(0).getElementContent();
                player = responses.getInputResponse(1);
                world = responses.getInputResponse(2);
                b = player != null && !player.replace(" ", "").equals("") && world != null && !world.replace(" ", "").equals("");
                switch (select) {
                    case "管理员":
                        if (player != null && !player.replace(" ", "").equals("")) {
                            ConfigUtil.adminList(p, 1, player);
                            GuiMain.showReturnWindow(p, true, GuiType.Return_toPowerMenu);
                        }
                        break;
                    case "操作员":
                        if (b) {
                            ConfigUtil.operatorList(p, 1, player, world);
                            GuiMain.showReturnWindow(p, true, GuiType.Return_toPowerMenu);
                        }
                        break;
                    case "白名单":
                        if (b) {
                            ConfigUtil.whiteList(p, 1, player, world);
                            GuiMain.showReturnWindow(p, true, GuiType.Return_toPowerMenu);
                        }
                        break;
                }
                break;
        }
    }

    private void formWindowModalOnClick(Player p, FormWindowModal window, GuiType guiType) {
        if (window.getResponse() == null) {
            return;
        }
        switch (guiType) {
            case Return_toMainMenu:
                if (window.getResponse().getClickedButtonId() == 0) {
                    if (ConfigUtil.isAdmin(p)) {
                        GuiMain.showMainMenu(p);
                    }
                }
                break;
            case Return_toPowerMenu:
                if (window.getResponse().getClickedButtonId() == 0) {
                    if (ConfigUtil.isAdmin(p)) {
                        GuiMain.showPowerMainMenu(p);
                    }
                }
                break;
        }
    }

    @EventHandler
    public void ProjectileLaunchEvent(ProjectileLaunchEvent event) {
        Entity entity = event.getEntity().shootingEntity;
        if (entity instanceof Player) {
            Player player = (Player) entity;
            Boolean bool = MainClass.getLevelBooleanInit(player.getLevel().getName(), "Player", "ConsumeItem");
            if (bool == null) {
                return;
            }
            if (ConfigUtil.isAdmin(player)) {
                return;
            }
            if (ConfigUtil.isOperator(player, event.getEntity().getLevel())) {
                return;
            }
            if (!bool) {
                if (MainClass.show_actionbar_text) {
                    player.sendActionBar(ConfigUtil.getLang("Tips", "AntiConsumeItem"));
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerToggleFlightEvent(PlayerToggleFlightEvent event) {
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player", "Fly");
        if (bool == null) {
            return;
        }
        if (!bool & event.isFlying()) {
            if (MainClass.show_actionbar_text) {
                event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiFly"));
            }
            event.getPlayer().setPosition(event.getPlayer().getPosition());
            event.getPlayer().setMotion(new Vector3(0, -1, 0));
            event.getPlayer().fall(event.getPlayer().getMotion().getFloorY() + 1);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void DataPacketReceiveEvent(DataPacketReceiveEvent event) {
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        if (event.getPacket() instanceof PlayerActionPacket) {
            PlayerActionPacket pk = (PlayerActionPacket) event.getPacket();
            if (pk.action == ACTION_JUMP) {
                if (event.getPlayer().getGamemode() != 1 && event.getPlayer().getGamemode() != 3) {
                    Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player", "Jump");
                    if (bool == null) {
                        return;
                    }
                    if (!bool) {
                        if (MainClass.show_actionbar_text) {
                            event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiJump"));
                        }
                        event.getPlayer().teleport(event.getPlayer().getLocation());
                        event.getPlayer().setMotion(new Vector3(0, -1, 0));
                    }
                }
            }
        }
    }

    @EventHandler
    public void PlayerDropItemEvent(PlayerDropItemEvent event) {
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player", "DropItem");
        if (bool == null) {
            return;
        }
        if (!bool) {
            if (MainClass.show_actionbar_text) {
                event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiDropItem"));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerToggleGlideEvent(PlayerToggleGlideEvent event) {
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player", "Glide");
        if (bool == null) {
            return;
        }
        if (!bool && event.isGliding()) {
            if (MainClass.show_actionbar_text) {
                event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiGlide"));
            }
            event.getPlayer().teleport(event.getPlayer().getLocation(), null);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerToggleSwimEvent(PlayerToggleSwimEvent event) {
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player", "Swim");
        if (bool == null) {
            return;
        }
        if (!bool && event.isSwimming()) {
            if (MainClass.show_actionbar_text) {
                event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiSwim"));
            }
            event.getPlayer().teleport(event.getPlayer().getLocation(), null);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player", "Sneak");
        if (bool == null) {
            return;
        }
        if (!bool && event.isSneaking()) {
            if (MainClass.show_actionbar_text) {
                event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiSneak"));
            }
            event.getPlayer().teleport(event.getPlayer().getLocation(), null);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerToggleSprintEvent(PlayerToggleSprintEvent event) {
        if (ConfigUtil.isAdmin(event.getPlayer())) {
            return;
        }
        if (ConfigUtil.isOperator(event.getPlayer(), event.getPlayer().getLevel())) {
            return;
        }
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player", "Sprint");
        if (bool == null) {
            return;
        }
        if (!bool && event.isSprinting()) {
            if (MainClass.show_actionbar_text) {
                event.getPlayer().sendActionBar(ConfigUtil.getLang("Tips", "AntiSprint"));
            }
            event.getPlayer().teleport(event.getPlayer().getLocation(), null);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerLocallyInitializedEvent(PlayerLocallyInitializedEvent event) {
        Player player = event.getPlayer();
        AdventureSettingUtils.updatePlayerAdventureSettings(player, player.getLevel());
    }
}