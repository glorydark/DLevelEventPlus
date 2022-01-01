package glorydark.DLevelEventPlus.event;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockDoor;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.form.element.ElementToggle;
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
import glorydark.DLevelEventPlus.gui.GuiType;
import glorydark.DLevelEventPlus.MainClass;
import glorydark.DLevelEventPlus.gui.GuiMain;
import glorydark.DLevelEventPlus.utils.Config;
import glorydark.DLevelEventPlus.utils.LiquidItem;

import java.util.*;

public class PlayerEventListener implements Listener {
    public static final HashMap<Player, HashMap<Integer, GuiType>> UI_CACHE = new HashMap<>();
    public static void showFormWindow(Player player, FormWindow window, GuiType guiType) {
        UI_CACHE.computeIfAbsent(player, i -> new HashMap<>()).put(player.showFormWindow(window), guiType);
    }
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        /*
        if(event.getPlayer().getInventory().getItemInHand().getId() == Item.BUCKET){
            ItemBucket item = (ItemBucket) event.getPlayer().getInventory().getItemInHand();
            if(item.getDamage() == 0) {
                if(event.getBlock() instanceof BlockWater) {
                    Server.getInstance().broadcastMessage("检测收回" + event.getBlock().getLocation().toString());
                }
            }
            if(item.getDamage() != 0) {
                if(!(event.getBlock() instanceof BlockWater) && !(event.getBlock() instanceof BlockAir)) {
                    Server.getInstance().broadcastMessage("检测放置" + event.getBlock().getLocation().toString());
                }
            }
        }
        
         */

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

        if(block instanceof BlockDoor){
            Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","DoorToggle");
            if(bool == null){return;}
            if (bool) { return; }
            event.setCancelled(true);
        }

        if(LiquidItem.isLiquidItem(event.getPlayer().getInventory().getItemInHand())){
            Boolean bool = MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Block","AllowPlaceBlock");
            if(bool == null){return;}
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
                for (String str : Objects.requireNonNull(strings)){
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
                        GuiMain.showSettingChooseWorldMenu(p);
                        break;
                    case 1:
                        GuiMain.showPowerMainMenu(p);
                        break;
                    case 2:
                        MainClass.saveAllConfig();
                        GuiMain.showReturnWindow(p,true,GuiType.Return_toMainMenu);
                        break;
                    case 3:
                        MainClass.loadAllLevelConfig();
                        MainClass.loadLang();
                        GuiMain.showReturnWindow(p,true,GuiType.Return_toMainMenu);
                        break;
                    case 4:
                        GuiMain.showTemplateMainMenu(p);
                        break;
                }
                break;
            case Power_Main:
                switch (window.getResponse().getClickedButtonId()){
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
                    if(!text.equals("返回")) {
                        String worldName = window.getResponse().getClickedButton().getText();
                        if(MainClass.configCache.containsKey(text)){
                            GuiMain.showEditMenu(p, worldName);
                        }else{
                            GuiMain.showSettingChooseTemplateMenu(p, GuiType.Template_ChooseTemplateForNewConfig);
                        }
                        MainClass.selectCache.put(p, text);
                    }else{
                        GuiMain.showMainMenu(p);
                    }
                }
                break;
            case Template_ChooseTemplateForEdit:
                text = window.getResponse().getClickedButton().getText();
                if (!text.equals("")) {
                    if(!text.equals("返回")) {
                        GuiMain.showTemplateSettingMenu(p, text, GuiType.Template_EditProcess);
                        MainClass.selectCache.put(p, text);
                    }else{
                        GuiMain.showMainMenu(p);
                    }
                }
                break;
            case Template_ChooseTemplateForNewConfig:
                text = window.getResponse().getClickedButton().getText();
                if (!text.equals("")) {
                    if(!text.equals("返回")) {
                        GuiMain.showTemplateSettingMenu(p, text, GuiType.Template_CreateConfigProcess);
                    }else{
                        GuiMain.showMainMenu(p);
                    }
                }
                break;
            case Template_Main:
                switch (window.getResponse().getClickedButtonId()){
                    case 0:
                        GuiMain.showTemplateAddMenu(p);
                        break;
                    case 1:
                        GuiMain.showSettingChooseTemplateMenu(p, GuiType.Template_ChooseTemplateForEdit);
                        break;
                }
                break;
        }
    }

    private void formWindowCustomOnClick(Player p, FormWindowCustom window, GuiType guiType) {
        if(window.getResponse() == null){return;}
        FormResponseCustom responses = window.getResponse();
        switch (guiType) {
            case Edit_Process:
            case Template_CreateConfigProcess:
                String levelname = MainClass.selectCache.get(p);
                if(levelname == null){
                    MainClass.plugin.getLogger().warning("Error: Can not find "+ p.getName() +"'s selected world.");
                    return;
                }
                if(!MainClass.configCache.containsKey(levelname)){
                    MainClass.configCache.put(levelname, new LinkedHashMap<String, Object>());
                }
                MainClass.setLevelBooleanInit(levelname, "World", "FarmProtect", responses.getToggleResponse(0));
                MainClass.setLevelBooleanInit(levelname, "World", "AllExplodes", responses.getToggleResponse(1));
                MainClass.setLevelBooleanInit(levelname, "World", "TntExplodes", responses.getToggleResponse(2));
                MainClass.setLevelBooleanInit(levelname, "World", "PVP", responses.getToggleResponse(3));
                MainClass.setLevelBooleanInit(levelname, "World", "KeepInventory", responses.getToggleResponse(4));
                MainClass.setLevelBooleanInit(levelname, "World", "KeepXp", responses.getToggleResponse(5));
                MainClass.setLevelBooleanInit(levelname, "World", "AllowOpenChest", responses.getToggleResponse(6));
                MainClass.setLevelBooleanInit(levelname, "World", "CanUseFishingHook", responses.getToggleResponse(7));
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
                GuiMain.showReturnWindow(p,true,GuiType.Return_toMainMenu);
                break;
            case Template_EditProcess:
                responses = window.getResponse();
                String select = MainClass.selectCache.get(p);
                Config.setTemplateBooleanInit(select, "World", "FarmProtect", responses.getToggleResponse(0));
                Config.setTemplateBooleanInit(select, "World", "AllExplodes", responses.getToggleResponse(1));
                Config.setTemplateBooleanInit(select, "World", "TntExplodes", responses.getToggleResponse(2));
                Config.setTemplateBooleanInit(select, "World", "PVP", responses.getToggleResponse(3));
                Config.setTemplateBooleanInit(select, "World", "KeepInventory", responses.getToggleResponse(4));
                Config.setTemplateBooleanInit(select, "World", "KeepXp", responses.getToggleResponse(5));
                Config.setTemplateBooleanInit(select, "World", "AllowOpenChest", responses.getToggleResponse(6));
                Config.setTemplateBooleanInit(select, "World", "CanUseFishingHook", responses.getToggleResponse(7));
                Config.setTemplateBooleanInit(select, "Player", "AllowInteractFrameBlock", responses.getToggleResponse(8));
                Config.setTemplateBooleanInit(select, "Player", "AllowUseFlintAndSteel", responses.getToggleResponse(9));
                Config.setTemplateBooleanInit(select, "Player", "Sneak", responses.getToggleResponse(10));
                Config.setTemplateBooleanInit(select, "Player", "Fly", responses.getToggleResponse(11));
                Config.setTemplateBooleanInit(select, "Player", "Swim", responses.getToggleResponse(12));
                Config.setTemplateBooleanInit(select, "Player", "Glide", responses.getToggleResponse(13));
                Config.setTemplateBooleanInit(select, "Player", "Jump", responses.getToggleResponse(14));
                Config.setTemplateBooleanInit(select, "Player", "Sprint", responses.getToggleResponse(15));
                Config.setTemplateBooleanInit(select, "Player", "Pick", responses.getToggleResponse(16));
                Config.setTemplateBooleanInit(select, "Player", "ConsumeItem", responses.getToggleResponse(17));
                Config.setTemplateBooleanInit(select, "Player", "DropItem", responses.getToggleResponse(18));
                Config.setTemplateBooleanInit(select, "Player", "BedEnter", responses.getToggleResponse(19));
                Config.setTemplateBooleanInit(select, "Player", "Move", responses.getToggleResponse(20));
                Config.setTemplateBooleanInit(select, "Player", "EatFood", responses.getToggleResponse(21));
                Config.setTemplateBooleanInit(select, "Player", "CommandPreprocess", responses.getToggleResponse(22));
                Config.setTemplateBooleanInit(select, "Player", "GameModeChange", responses.getToggleResponse(23));
                Config.setTemplateBooleanInit(select, "Player", "AntiTeleport", responses.getToggleResponse(24));
                Config.setTemplateBooleanInit(select, "Entity", "Explosion", responses.getToggleResponse(25));
                Config.setTemplateBooleanInit(select, "Entity", "PortalEnter", responses.getToggleResponse(26));
                Config.setTemplateBooleanInit(select, "Block", "AllowPlaceBlock", responses.getToggleResponse(27));
                Config.setTemplateBooleanInit(select, "Block", "AllowBreakBlock", responses.getToggleResponse(28));
                Config.setTemplateBooleanInit(select, "Block", "Burn", responses.getToggleResponse(29));
                Config.setTemplateBooleanInit(select, "Block", "Ignite", responses.getToggleResponse(30));
                Config.setTemplateBooleanInit(select, "Block", "Fall", responses.getToggleResponse(31));
                Config.setTemplateBooleanInit(select, "Block", "Grow", responses.getToggleResponse(32));
                Config.setTemplateBooleanInit(select, "Block", "Spread", responses.getToggleResponse(33));
                Config.setTemplateBooleanInit(select, "Block", "Form", responses.getToggleResponse(34));
                Config.setTemplateBooleanInit(select, "Block", "DoorToggle", responses.getToggleResponse(35));
                Config.setTemplateBooleanInit(select, "Block", "LeavesDecay", responses.getToggleResponse(36));
                Config.setTemplateBooleanInit(select, "Block", "LiquidFlow", responses.getToggleResponse(37));
                Config.setTemplateBooleanInit(select, "Block", "ItemFrameDropItem", responses.getToggleResponse(38));
                Config.setTemplateBooleanInit(select, "Block", "SignChange", responses.getToggleResponse(39));
                Config.setTemplateBooleanInit(select, "Block", "BlockRedstone", responses.getToggleResponse(40));
                GuiMain.showReturnWindow(p,true,GuiType.Return_toMainMenu);
                break;
            case Template_Add:
                String text = responses.getInputResponse(0);
                if(!text.replace(" ","").equals("")){
                    if(!Config.TemplateCache.containsKey(text)){
                        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                        //World
                        Map<String, Object> typeWorldSetting = new HashMap<>();
                        typeWorldSetting.put("FarmProtect", true);
                        typeWorldSetting.put("AllExplodes", true);
                        typeWorldSetting.put("TntExplodes", true);
                        typeWorldSetting.put("Pvp", true);
                        typeWorldSetting.put("KeepInventory", true);
                        typeWorldSetting.put("KeepXp", true);
                        //Player
                        Map<String, Object> typePlayerSetting = new HashMap<>();
                        typePlayerSetting.put("AllowOpenChest", true);
                        typePlayerSetting.put("ChestTrustList", new ArrayList<>());
                        typePlayerSetting.put("CanUseFishingHook", true);
                        typePlayerSetting.put("AllowInteractFrameBlock", true);
                        typePlayerSetting.put("AllowUseFlintAndSteel", true);
                        typePlayerSetting.put("Sneak", true);
                        typePlayerSetting.put("Sprint", true);
                        typePlayerSetting.put("Fly", true);
                        typePlayerSetting.put("Swim", true);
                        typePlayerSetting.put("Glide", true);
                        typePlayerSetting.put("Jump", true);
                        typePlayerSetting.put("Pick", true);
                        typePlayerSetting.put("ConsumeItem", true);
                        typePlayerSetting.put("DropItem", true);
                        typePlayerSetting.put("BedEnter", true);
                        typePlayerSetting.put("Move", true);
                        typePlayerSetting.put("EatFood", true);
                        typePlayerSetting.put("CommandPreprocess", true);
                        typePlayerSetting.put("BanCommands", new ArrayList<>());
                        typePlayerSetting.put("GameModeChange", true);
                        typePlayerSetting.put("HungerChange", false);
                        typePlayerSetting.put("AntiTeleport", false);
                        //Entity
                        Map<String, Object> typeEntitySetting = new HashMap<>();
                        typePlayerSetting.put("Explosion", false);
                        typePlayerSetting.put("PortalEnter", false);

                        //Block
                        Map<String, Object> typeBlockSetting = new HashMap<>();
                        typeBlockSetting.put("AllowPlaceBlock", true);
                        typeBlockSetting.put("AllowBreakBlock", true);
                        typeBlockSetting.put("AntiPlaceBlocks", new ArrayList<>());
                        typeBlockSetting.put("AntiBreakBlocks", new ArrayList<>());
                        typeBlockSetting.put("Burn", true);
                        typeBlockSetting.put("Ignite", true);
                        typeBlockSetting.put("Fall", true);
                        typeBlockSetting.put("Grow", true);
                        typeBlockSetting.put("Spread", true);
                        typeBlockSetting.put("Form", true);
                        typeBlockSetting.put("DoorToggle", true);
                        typeBlockSetting.put("LeavesDecay", true);
                        typeBlockSetting.put("LiquidFlow", true);
                        typeBlockSetting.put("ItemFrameDropItem", true);
                        typeBlockSetting.put("SignChange", true);
                        typeBlockSetting.put("BlockRedstone", true);

                        map.put("World", typeWorldSetting);
                        map.put("Block", typeBlockSetting);
                        map.put("Entity", typeEntitySetting);
                        map.put("Player", typePlayerSetting);
                        Config.TemplateCache.put(text, map);
                        GuiMain.showReturnWindow(p,true,GuiType.Return_toMainMenu);
                    }else{
                        GuiMain.showReturnWindow(p,false,GuiType.Return_toMainMenu);
                    }
                }
                break;
            case Power_Add:
                responses = window.getResponse();
                select = responses.getDropdownResponse(0).getElementContent();
                String player = responses.getInputResponse(1);
                String world = responses.getInputResponse(2);
                boolean b = player != null && !player.replace(" ", "").equals("") && world != null && !world.replace(" ", "").equals("");
                switch (select){
                    case "管理员":
                        if(player != null && !player.replace(" ","").equals("")) {
                            GuiMain.showReturnWindow(p,Config.adminList(0, player),GuiType.Return_toPowerMenu);
                        }
                        break;
                    case "操作员":
                        if(b) {
                            GuiMain.showReturnWindow(p,Config.operatorList(0, player,world),GuiType.Return_toPowerMenu);
                        }
                        break;
                    case "白名单":
                        if(b) {
                            GuiMain.showReturnWindow(p,Config.whiteList(0, player,world),GuiType.Return_toPowerMenu);
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
                            GuiMain.showReturnWindow(p,Config.adminList(1, player),GuiType.Return_toPowerMenu);
                        }
                        break;
                    case "操作员":
                        if(b) {
                            GuiMain.showReturnWindow(p,Config.operatorList(1, player,world),GuiType.Return_toPowerMenu);
                        }
                        break;
                    case "白名单":
                        if(b) {
                            GuiMain.showReturnWindow(p,Config.whiteList(1, player,world),GuiType.Return_toPowerMenu);
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
                        GuiMain.showMainMenu(p);
                    }
                }
                break;
            case Return_toPowerMenu:
                if (window.getResponse().getClickedButtonId() == 0) {
                    if(Config.isAdmin(p)) {
                        GuiMain.showPowerMainMenu(p);
                    }
                }
                break;
        }
    }
}