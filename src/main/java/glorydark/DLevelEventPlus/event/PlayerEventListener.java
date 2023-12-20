package glorydark.DLevelEventPlus.event;

import cn.nukkit.Player;
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
import glorydark.DLevelEventPlus.gui.protection.rule.ProtectionRuleEntries;
import glorydark.DLevelEventPlus.gui.protection.rule.BooleanProtectionRuleEntry;
import glorydark.DLevelEventPlus.gui.protection.rule.DropdownProtectionRuleEntry;
import glorydark.DLevelEventPlus.gui.protection.rule.InputProtectionRuleEntry;
import glorydark.DLevelEventPlus.gui.protection.rule.ProtectionRuleEntry;
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
                player.sendActionBar(MainClass.language.translateString("tip_interact"));
            }
            event.setCancelled(true);
            return;
        }

        if (block != null) {
            if (block.getId() == 389 || block.getId() == -339) {
                Boolean bool = MainClass.getLevelBooleanInit(level.getName(), "Player", "AllowInteractFrameBlock");
                if (bool != null && !bool) {
                    if (MainClass.show_actionbar_text) {
                        player.sendActionBar(MainClass.language.translateString("tip_touchFlame"));
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
                            player.sendActionBar(MainClass.language.translateString("tip_trampleFarmland"));
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
                            String title = MainClass.language.translateString("tip_useChest", String.valueOf(block.getLocation()));
                            player.sendActionBar(title);
                        }
                        event.setCancelled(true);
                    }
                }
            }

            if (MainClass.getLevelStringListInit(level.getName(), "Player", "BannedInteractBlocks").stream().anyMatch(s -> ItemUtils.isEqual(s, block))) {
                if (MainClass.show_actionbar_text) {
                    player.sendActionBar(MainClass.language.translateString("tip_blockInteractBanned"));
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
                    event.getPlayer().sendActionBar(MainClass.language.translateString("tip_placeBlock"));
                }
                event.setCancelled(true);
            }
        }

        if (item != null) {
            if (item instanceof ItemFishingRod) {
                Boolean bool = MainClass.getLevelBooleanInit(level.getName(), "Player", "CanUseFishingHook");
                if (bool != null && !bool) {
                    if (MainClass.show_actionbar_text) {
                        player.sendActionBar(MainClass.language.translateString("tip_useFishingRod"));
                    }
                    event.setCancelled(true);
                }
            }

            List<String> strings = MainClass.getLevelStringListInit(level.getName(), "Player", "BannedUseItems");
            if (strings.stream().anyMatch(s -> ItemUtils.isEqual(s, item))) {
                if (MainClass.show_actionbar_text) {
                    player.sendActionBar(MainClass.language.translateString("tip_itemBanned"));
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
                    event.getPlayer().sendActionBar(MainClass.language.translateString("tip_teleport", toLevel.getName()));
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
                event.getPlayer().sendActionBar(MainClass.language.translateString("tip_pickUpItem"));
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
                    p.sendActionBar(MainClass.language.translateString("tip_pickUpItem"));
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
                event.getPlayer().sendActionBar(MainClass.language.translateString("tip_consumeItem"));
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
                event.getPlayer().sendActionBar(MainClass.language.translateString("tip_enterBed"));
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
        if (!bool) {
            if (MainClass.show_actionbar_text) {
                event.getPlayer().sendActionBar(MainClass.language.translateString("tip_executeCommand"));
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
                event.getPlayer().sendActionBar(MainClass.language.translateString("tip_changeGamemode"));
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
                    player.sendActionBar(MainClass.language.translateString("tip_move"));
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
                            player.sendActionBar(MainClass.language.translateString("tip_fallIntoVoid"));
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
                        player.sendActionBar(MainClass.language.translateString("tip_fly"));
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
                event.getPlayer().sendActionBar(MainClass.language.translateString("tip_eat"));
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
            case Admin_Main:
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
                            GuiMain.showEditMenuV2(p, worldName);
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
                        GuiMain.showTemplateSettingMenuV2(p, text, GuiType.Template_EditProcess);
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
                        GuiMain.showTemplateSettingMenuV2(p, text, GuiType.Edit_Process);
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
                String levelname = MainClass.selectCache.get(p);
                if (levelname == null) {
                    MainClass.plugin.getLogger().warning("Error: Can not find " + p.getName() + "'s selected world.");
                    return;
                }
                if (!MainClass.configCache.containsKey(levelname)) {
                    MainClass.configCache.put(levelname, new LinkedHashMap<>());
                }
                int id = 0;
                for (ProtectionRuleEntry entry : ProtectionRuleEntries.getEntries()) {
                    if (entry instanceof BooleanProtectionRuleEntry) {
                        MainClass.setLevelInit(levelname, entry.getCategory(), entry.getEntryName(), responses.getToggleResponse(id));
                    } else if (entry instanceof DropdownProtectionRuleEntry) {
                        MainClass.setLevelInit(levelname, entry.getCategory(), entry.getEntryName(), responses.getDropdownResponse(id).getElementContent());
                    } else if (entry instanceof InputProtectionRuleEntry) {
                        switch (((InputProtectionRuleEntry) entry).getSaveType()) {
                            case STRING:
                                MainClass.setLevelInit(levelname, entry.getCategory(), entry.getEntryName(), responses.getInputResponse(id));
                                break;
                            case INTEGER:
                                try {
                                    MainClass.setLevelInit(levelname, entry.getCategory(), entry.getEntryName(), Integer.parseInt(responses.getInputResponse(id)));
                                } catch (NumberFormatException e) {
                                    p.sendMessage("Wrong Number Format in the entry " + entry.getEntryName() + "in the category " + entry.getCategory());
                                }
                                break;
                        }
                    }
                    id++;
                }
                Config config = new Config(MainClass.path + "/worlds/" + levelname + ".yml", Config.YAML);
                config.setAll(MainClass.configCache.getOrDefault(levelname, new LinkedHashMap<>()));
                config.save();
                GuiMain.showReturnWindow(p, true, GuiType.Return_toMainMenu);
                break;
            case Template_EditProcess:
                responses = window.getResponse();
                String select = MainClass.selectCache.get(p);
                id = 0;
                for (ProtectionRuleEntry entry : ProtectionRuleEntries.getEntries()) {
                    if (entry instanceof BooleanProtectionRuleEntry) {
                        ConfigUtil.setTemplateInit(select, entry.getCategory(), entry.getEntryName(), responses.getToggleResponse(id));
                    } else if (entry instanceof DropdownProtectionRuleEntry) {
                        ConfigUtil.setTemplateInit(select, entry.getCategory(), entry.getEntryName(), responses.getDropdownResponse(id).getElementContent());
                    } else if (entry instanceof InputProtectionRuleEntry) {
                        switch (((InputProtectionRuleEntry) entry).getSaveType()) {
                            case STRING:
                                ConfigUtil.setTemplateInit(select, entry.getCategory(), entry.getEntryName(), responses.getInputResponse(id));
                                break;
                            case INTEGER:
                                try {
                                    ConfigUtil.setTemplateInit(select, entry.getCategory(), entry.getEntryName(), Integer.parseInt(responses.getInputResponse(id)));
                                } catch (NumberFormatException e) {
                                    p.sendMessage("Wrong Number Format in the entry " + entry.getEntryName() + "in the category " + entry.getCategory());
                                }
                                break;
                        }
                    }
                    id++;
                }
                config = new Config(MainClass.path + "/templates/" + select + ".yml", Config.YAML);
                config.setAll(ConfigUtil.templateCache.getOrDefault(select, new LinkedHashMap<>()));
                config.save();
                GuiMain.showReturnWindow(p, true, GuiType.Return_toMainMenu);
                break;
            case Template_Add:
                String text = responses.getInputResponse(0);
                if (!text.replace(" ", "").equals("")) {
                    if (!ConfigUtil.templateCache.containsKey(text)) {
                        MainClass.defaultConfigUtils.writeAll(1, text);
                        ConfigUtil.templateCache.put(text, (LinkedHashMap<String, Object>) new Config(MainClass.path + "/templates/" + text + ".yml", Config.YAML).getAll());
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
                    player.sendActionBar(MainClass.language.translateString("tip_consumeItem"));
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
                event.getPlayer().sendActionBar(MainClass.language.translateString("tip_fly"));
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
                            event.getPlayer().sendActionBar(MainClass.language.translateString("tip_jump"));
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
                event.getPlayer().sendActionBar(MainClass.language.translateString("tip_dropItem"));
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
                event.getPlayer().sendActionBar(MainClass.language.translateString("tip_glide"));
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
                event.getPlayer().sendActionBar(MainClass.language.translateString("tip_swim"));
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
                event.getPlayer().sendActionBar(MainClass.language.translateString("tip_sneak"));
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
                event.getPlayer().sendActionBar(MainClass.language.translateString("tip_sprint"));
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