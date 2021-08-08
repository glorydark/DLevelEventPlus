package glorydark.DLevelEventPlus.event;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
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
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlayerActionPacket;
import glorydark.DLevelEventPlus.Command;
import glorydark.DLevelEventPlus.MainClass;
import glorydark.DLevelEventPlus.utils.Config;

import java.util.*;

public class PlayerEventListener implements Listener {
    public static final HashMap<Player, HashMap<Integer, GuiType>> UI_CACHE = new HashMap<>();
    public static void showFormWindow(Player player, FormWindow window, GuiType guiType) {
        UI_CACHE.computeIfAbsent(player, i -> new HashMap<>()).put(player.showFormWindow(window), guiType);
    }
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        Level level = event.getBlock().getLevel();
        Block block = event.getBlock();
        if (block.getId() == Block.ITEM_FRAME_BLOCK) {
            Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","AllowInteractFrameBlock");
            if(bool == null){return;}
            if (!bool) {
                event.getPlayer().sendActionBar(Config.getLang("AntiTouchFlame"));
                event.setCancelled(true);
            }
        }

        //玩家导致耕地踩踏
        if (block.getId() == Block.FARMLAND) {
            Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"World","FarmProtect");
            if(bool == null){return;}
            if(event.getAction() == PlayerInteractEvent.Action.PHYSICAL){
                if (bool) {
                    event.getPlayer().sendActionBar(Config.getLang("AntiTrampleFarmland"));
                    event.setCancelled(true);
                }
            }
        }

        if(event.getPlayer().getInventory().getItemInHand() instanceof ItemFishingRod){
            Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","CanUseFishingHook");
            if(bool == null){return;}
            if (!bool) {
                event.getPlayer().sendActionBar(Config.getLang("AntiUseFishingRod"));
                event.setCancelled(true);
            }
        }

        if(event.getPlayer().getInventory().getItemInHand().getId() == Item.FLINT_AND_STEEL){
            Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","AllowUseFlintAndSteel");
            if(bool == null){return;}
            if (!bool) {
                event.getPlayer().sendActionBar(Config.getLang("AntiUseFlintAndSteel"));
                event.setCancelled(true);
            }
        }

        if(block.getId() == Block.CHEST) {
            Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","AllowOpenChest");
            if(bool == null){return;}
            if (!bool) {
                if(MainClass.getLevelStringListInit(event.getPlayer().getLevel().getName(),"Player","ChestTrustList") == null){return;}
                List<String> list = MainClass.getLevelStringListInit(level.getName(),"Player","ChestTrustList");
                List<Position> positionArrayList = new ArrayList<>();
                for(String str: Objects.requireNonNull(list)){
                    List<String> strspl = Arrays.asList(str.split(":"));
                    if(strspl.size() == 3){
                        positionArrayList.add(new Position(Double.parseDouble(strspl.get(0)),Double.parseDouble(strspl.get(1)),Double.parseDouble(strspl.get(2))));
                    }
                }
                if(!positionArrayList.contains(block.getLocation())) {
                    String title = Config.getLang("AntiUseChest").replace("%position%",String.valueOf(block.getLocation()));
                    event.getPlayer().sendActionBar(title);
                    event.setCancelled(true);
                }
            }
        }
        List<Integer> list = new ArrayList<>();
        list.add(Item.WATER);
        list.add(Item.WATER_LILY);
        list.add(Item.STILL_WATER);
        list.add(Item.LAVA);
        list.add(Item.STILL_LAVA);
        list.add(Item.BUCKET);
        if(list.contains(event.getPlayer().getInventory().getItemInHand().getId())){
            Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","AllowPlaceBlock");
            if(bool == null){return;}
            if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
            if (bool) { return; }
            if(event.getPlayer().isOnline()) {
                event.getPlayer().sendActionBar(Config.getLang("AntiPlaceBlock"));
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerTeleportEvent(PlayerTeleportEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","AntiTeleport");
        if(bool == null){
            return;
        }
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        Level level = event.getTo().getLevel();
        if(Config.isWhiteListed(event.getPlayer(),level)){ return;}
        if(event.getFrom().getLevel() == event.getTo().getLevel()) {
            if (bool) {
                event.getPlayer().sendActionBar(Config.getLang("AntiTeleport").replace("%s", level.getName()));
                event.setCancelled(true);
            }
        }
    }

    //

    @EventHandler
    public void PlayerBlockPickEvent(PlayerBlockPickEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","Pick");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            event.getPlayer().sendActionBar(Config.getLang("AntiPickUpItem"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","ConsumeItem");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            event.getPlayer().sendActionBar(Config.getLang("AntiConsumeItem"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerBedEnterEvent(PlayerBedEnterEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","BedEnter");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            event.getPlayer().sendActionBar(Config.getLang("AntiBedEnter"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerDropItemEvent(PlayerDropItemEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player","DropItem");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            event.getPlayer().sendActionBar(Config.getLang("AntiDropItem"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","CommandPreprocess");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if(event.getPlayer() == null){
            return;
        }
        if (bool) {
            List<String> strings = MainClass.getLevelStringListInit(event.getPlayer().getLevel().getName(), "Player","BanCommands");
            if(strings == null){return;}
            if (strings.size() > 0) {
                String commandtext = event.getMessage();
                commandtext.replace("/","");
                String[] commandsplit = commandtext.split(" ");
                List<String> bancommands = strings;
                for (String str : Objects.requireNonNull(bancommands)){
                    String[] bancommandsplit = str.split(" ");
                    for(int i =1 ; i<=bancommandsplit.length; i++){
                        if(bancommandsplit[i-1].equals(commandsplit[i-1])){
                            if(i == bancommandsplit.length){
                                event.getPlayer().sendActionBar(Config.getLang("AntiCommand"));
                                event.setCancelled(true);
                                return;
                            }
                        }
                    }
                }
            }
        }else{
            event.getPlayer().sendActionBar(Config.getLang("AntiAllCommand"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerGameModeChangeEvent(PlayerGameModeChangeEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","GameModeChange");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            event.getPlayer().sendActionBar(Config.getLang("AntiChangeGamemode"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","Move");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            event.getPlayer().sendActionBar(Config.getLang("AntiMove"));
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void PlayerEatFoodEvent(PlayerEatFoodEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","EatFood");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            event.getPlayer().sendActionBar(Config.getLang("AntiEat"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void DataPacketReceiveEvent(DataPacketReceiveEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","Jump");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if(event.getPacket() instanceof PlayerActionPacket){
            PlayerActionPacket pk = (PlayerActionPacket) event.getPacket();
            if((pk).action == PlayerActionPacket.ACTION_JUMP){
                if (!bool) {
                    event.getPlayer().sendActionBar(Config.getLang("AntiJump"));
                    event.getPlayer().setPosition(event.getPlayer().getPosition());
                    event.getPlayer().setMotion(new Vector3(0,-1,0));
                }
            }
        }
    }

    @EventHandler
    public void PlayerToggleFlightEvent(PlayerToggleFlightEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player","Fly");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            event.getPlayer().sendActionBar(Config.getLang("AntiFly"));
            event.getPlayer().setPosition(event.getPlayer().getPosition());
            event.getPlayer().setMotion(new Vector3(0,-1,0));
            event.getPlayer().fall(event.getPlayer().getMotion().getFloorY()+1);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerFoodLevelChangeEvent(PlayerFoodLevelChangeEvent event){
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","HungerChange");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event){
        Boolean bool1 = MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World","KeepXp");
        Boolean bool2 = MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World","KeepInventory");
        if(bool1 == null){
            bool1 = false;
        }
        if(bool2 == null){
            bool2 = false;
        }
        event.setKeepExperience(bool1);
        event.setKeepInventory(bool2);
    }

    @EventHandler
    public void PlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player","Sneak");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            event.getPlayer().sendActionBar(Config.getLang("AntiSneak"));
            event.getPlayer().setSneaking(false);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerToggleSprintEvent(PlayerToggleSprintEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player","Sprint");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            event.getPlayer().sendActionBar(Config.getLang("AntiSprint"));
            event.getPlayer().setSprinting(false);
            event.getPlayer().setPosition(event.getPlayer().getPosition());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerToggleSwimEvent(PlayerToggleSwimEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player","Swim");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            event.getPlayer().sendActionBar(Config.getLang("AntiSwim"));
            event.getPlayer().setSwimming(false);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerToggleGlideEvent(PlayerToggleGlideEvent event) {
        Boolean bool = MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player","Glide");
        if(bool == null){return;}
        if(Config.isAdmin(event.getPlayer())){ return; }
        if(Config.isOperator(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!bool) {
            event.getPlayer().sendActionBar(Config.getLang("AntiGlide"));
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void PlayerFormRespondedEvent(PlayerFormRespondedEvent event){
        Player p = event.getPlayer();
        FormWindow window = event.getWindow();
        if (p == null || window == null) {
            return;
        }
        GuiType guiType = UI_CACHE.containsKey(p) ? UI_CACHE.get(p).get(event.getFormID()) : null;
        if(guiType == null){
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
        if(window.getResponse() == null){return;}
        switch (guiType){
            case ADMIN_Main:
                switch (window.getResponse().getClickedButtonId()){
                    case 0:
                        Command.showChooseWorldMenu(p);
                        break;
                    case 1:
                        Command.showPowerMainMenu(p);
                        break;
                    case 2:
                        MainClass.saveAllLevelConfig();
                        Command.showReturnWindow(p,true,GuiType.Return_toMainMenu);
                        break;
                    case 3:
                        MainClass.loadAllLevelConfig();
                        MainClass.loadLang();
                        Command.showReturnWindow(p,true,GuiType.Return_toMainMenu);
                        break;
                }
                break;
            case Power_Main:
                switch (window.getResponse().getClickedButtonId()){
                    case 0:
                        Command.showPowerAddMenu(p);
                        break;
                    case 1:
                        Command.showPowerDeleteMenu(p);
                        break;
                    case 2:
                        Command.showMainMenu(p);
                        break;
                }
                break;
            case Edit_ChooseWorld:
                if (!window.getResponse().getClickedButton().getText().equals("")) {
                    if(!window.getResponse().getClickedButton().getText().equals("返回")) {
                        Command.showEditMenu(p, window.getResponse().getClickedButton().getText());
                    }else{
                        Command.showMainMenu(p);
                    }
                }
                break;
        }
    }

    private void formWindowCustomOnClick(Player p, FormWindowCustom window, GuiType guiType) {
        if(window.getResponse() == null){return;}
        switch (guiType){
            case Edit_Process:
                String[] strings = window.getTitle().split("【");
                if (strings.length < 2) {
                    return;
                }
                String levelname = strings[1].replace("】", "");
                FormResponseCustom responses = window.getResponse();
                MainClass.setLevelBooleanInit(levelname, "World", "FarmProtect", responses.getToggleResponse(0));
                MainClass.setLevelBooleanInit(levelname, "World", "AllExplodes", responses.getToggleResponse(1));
                MainClass.setLevelBooleanInit(levelname, "World", "TntExplodes", responses.getToggleResponse(2));
                MainClass.setLevelBooleanInit(levelname, "World", "Pvp", responses.getToggleResponse(3));
                MainClass.setLevelBooleanInit(levelname, "World", "KeepInventory", responses.getToggleResponse(4));
                MainClass.setLevelBooleanInit(levelname, "World", "KeepXp", responses.getToggleResponse(5));
                MainClass.setLevelBooleanInit(levelname, "Player", "AllowOpenChest", responses.getToggleResponse(6));
                MainClass.setLevelBooleanInit(levelname, "Player", "CanUseFishingHook", responses.getToggleResponse(7));
                MainClass.setLevelBooleanInit(levelname, "Player", "AllowInteractFrameBlock", responses.getToggleResponse(8));
                MainClass.setLevelBooleanInit(levelname, "Player", "AllowUseFlintAndSteel", responses.getToggleResponse(9));
                MainClass.setLevelBooleanInit(levelname, "Player", "Sneak", responses.getToggleResponse(10));
                MainClass.setLevelBooleanInit(levelname, "Player", "Fly", responses.getToggleResponse(11));
                MainClass.setLevelBooleanInit(levelname, "Player", "Swim", responses.getToggleResponse(12));
                MainClass.setLevelBooleanInit(levelname, "Player", "Glide", responses.getToggleResponse(13));
                MainClass.setLevelBooleanInit(levelname, "Player", "Jump", responses.getToggleResponse(14));
                MainClass.setLevelBooleanInit(levelname, "Player", "Sprint", responses.getToggleResponse(15));
                MainClass.setLevelBooleanInit(levelname, "Player", "Pick", responses.getToggleResponse(16));
                MainClass.setLevelBooleanInit(levelname, "Player", "ConsumeItem", responses.getToggleResponse(17));
                MainClass.setLevelBooleanInit(levelname, "Player", "DropItem", responses.getToggleResponse(18));
                MainClass.setLevelBooleanInit(levelname, "Player", "BedEnter", responses.getToggleResponse(19));
                MainClass.setLevelBooleanInit(levelname, "Player", "Move", responses.getToggleResponse(20));
                MainClass.setLevelBooleanInit(levelname, "Player", "EatFood", responses.getToggleResponse(21));
                MainClass.setLevelBooleanInit(levelname, "Player", "CommandPreprocess", responses.getToggleResponse(22));
                MainClass.setLevelBooleanInit(levelname, "Player", "GameModeChange", responses.getToggleResponse(23));
                MainClass.setLevelBooleanInit(levelname, "Player", "AntiTeleport", responses.getToggleResponse(24));
                MainClass.setLevelBooleanInit(levelname, "Entity", "Explosion", responses.getToggleResponse(25));
                MainClass.setLevelBooleanInit(levelname, "Entity", "PortalEnter", responses.getToggleResponse(26));
                MainClass.setLevelBooleanInit(levelname, "Block", "AllowPlaceBlock", responses.getToggleResponse(27));
                MainClass.setLevelBooleanInit(levelname, "Block", "AllowBreakBlock", responses.getToggleResponse(28));
                MainClass.setLevelBooleanInit(levelname, "Block", "Burn", responses.getToggleResponse(29));
                MainClass.setLevelBooleanInit(levelname, "Block", "Ignite", responses.getToggleResponse(30));
                MainClass.setLevelBooleanInit(levelname, "Block", "Fall", responses.getToggleResponse(31));
                MainClass.setLevelBooleanInit(levelname, "Block", "Grow", responses.getToggleResponse(32));
                MainClass.setLevelBooleanInit(levelname, "Block", "Spread", responses.getToggleResponse(33));
                MainClass.setLevelBooleanInit(levelname, "Block", "Form", responses.getToggleResponse(34));
                MainClass.setLevelBooleanInit(levelname, "Block", "DoorToggle", responses.getToggleResponse(35));
                MainClass.setLevelBooleanInit(levelname, "Block", "LeavesDecay", responses.getToggleResponse(36));
                MainClass.setLevelBooleanInit(levelname, "Block", "LiquidFlow", responses.getToggleResponse(37));
                MainClass.setLevelBooleanInit(levelname, "Block", "ItemFrameDropItem", responses.getToggleResponse(38));
                MainClass.setLevelBooleanInit(levelname, "Block", "SignChange", responses.getToggleResponse(39));
                MainClass.setLevelBooleanInit(levelname, "Block", "BlockRedstone", responses.getToggleResponse(40));
                Command.showReturnWindow(p,true,GuiType.Return_toMainMenu);
                break;
            case Power_Add:
                responses = window.getResponse();
                String select = responses.getDropdownResponse(0).getElementContent();
                String player = responses.getInputResponse(1);
                String world = responses.getInputResponse(2);
                boolean b = player != null && !player.replace(" ", "").equals("") && world != null && !world.replace(" ", "").equals("");
                switch (select){
                    case "管理员":
                        if(player != null && !player.replace(" ","").equals("")) {
                            Command.showReturnWindow(p,Config.adminList(0, player),GuiType.Return_toPowerMenu);
                        }
                        break;
                    case "操作员":
                        if(b) {
                            Command.showReturnWindow(p,Config.operatorList(0, player,world),GuiType.Return_toPowerMenu);
                        }
                        break;
                    case "白名单":
                        if(b) {
                            Command.showReturnWindow(p,Config.whiteList(0, player,world),GuiType.Return_toPowerMenu);
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
                switch (select){
                    case "管理员":
                        if(player != null && !player.replace(" ","").equals("")) {
                            Command.showReturnWindow(p,Config.adminList(1, player),GuiType.Return_toPowerMenu);
                        }
                        break;
                    case "操作员":
                        if(b) {
                            Command.showReturnWindow(p,Config.operatorList(1, player,world),GuiType.Return_toPowerMenu);
                        }
                        break;
                    case "白名单":
                        if(b) {
                            Command.showReturnWindow(p,Config.whiteList(1, player,world),GuiType.Return_toPowerMenu);
                        }
                        break;
                }
                break;
        }
    }

    private void formWindowModalOnClick(Player p, FormWindowModal window, GuiType guiType) {
        if(window.getResponse() == null){return;}
        switch (guiType){
            case Return_toMainMenu:
                if (window.getResponse().getClickedButtonId() == 0) {
                    if(Config.isAdmin(p)) {
                        Command.showMainMenu(p);
                    }
                }
                break;
            case Return_toPowerMenu:
                if (window.getResponse().getClickedButtonId() == 0) {
                    if(Config.isAdmin(p)) {
                        Command.showPowerMainMenu(p);
                    }
                }
                break;
        }
    }
}