package glorydark.DLevelEventPlus.event;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemFishingRod;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlayerActionPacket;
import glorydark.DLevelEventPlus.MainClass;
import glorydark.DLevelEventPlus.utils.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerEventListener implements Listener {
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        Level level = event.getBlock().getLevel();
        Block block = event.getBlock();
        if (block.getId() == Block.ITEM_FRAME_BLOCK) {
            if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.AllowInteractFrameBlock") == null){return;}
            if (!MainClass.getLevelBooleanInit(level.getName(), "Player.AllowInteractFrameBlock")) {
                event.getPlayer().sendActionBar(Config.getLang("AntiTouchFlame"));
                event.setCancelled(true);
            }
        }

        //玩家导致耕地踩踏
        if (block.getId() == Block.FARMLAND) {
            if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"World.FarmProtect") == null){return;}
            if(event.getAction() == PlayerInteractEvent.Action.PHYSICAL){
                if (MainClass.getLevelBooleanInit(level.getName(),"World.FarmProtect")) {
                    event.getPlayer().sendActionBar(Config.getLang("AntiTrampleFarmland"));
                    event.setCancelled(true);
                }
            }
        }

        if(event.getPlayer().getInventory().getItemInHand() instanceof ItemFishingRod){
            if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.CanUseFishingHook") == null){return;}
            if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Player.CanUseFishingHook")) {
                event.getPlayer().sendActionBar(Config.getLang("AntiUseFishingRod"));
                event.setCancelled(true);
            }
        }

        if(event.getPlayer().getInventory().getItemInHand().getId() == Item.FLINT_AND_STEEL){
            if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.AllowUseFlintAndSteel") == null){return;}
            if (!MainClass.getLevelBooleanInit(event.getBlock().getLevel().getName(),"Player.AllowUseFlintAndSteel")) {
                event.getPlayer().sendActionBar(Config.getLang("AntiUseFlintAndSteel"));
                event.setCancelled(true);
            }
        }

        if(block.getId() == Block.CHEST) {
            if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.AllowOpenChest") == null){return;}
            if (!MainClass.getLevelBooleanInit(level.getName(), "Player.AllowOpenChest")) {
                if(MainClass.getLevelStringlistInit(event.getPlayer().getLevel().getName(),"Player.ChestTrustList") == null){return;}
                List<String> list = MainClass.getLevelStringlistInit(level.getName(),"Player.ChestTrustList");
                List<Position> positionArrayList = new ArrayList<>();
                for(String str: list){
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
    }

    @EventHandler
    public void PlayerTeleportEvent(PlayerTeleportEvent event){
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.AntiTeleport") == null){
            return;
        }
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        Level level = event.getTo().getLevel();
        if(Config.isWhiteListed(event.getPlayer(),level)){ return;}
        if(event.getFrom().getLevel() == event.getTo().getLevel()) {
            if (MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.AntiTeleport")) {
                event.getPlayer().sendActionBar(Config.getLang("AntiTeleport").replace("%s", level.getName()));
                event.setCancelled(true);
            }
        }
    }

    //

    @EventHandler
    public void PlayerBlockPickEvent(PlayerBlockPickEvent event) {
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.Pick") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.Pick")) {
            event.getPlayer().sendActionBar(Config.getLang("AntiPickUpItem"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.ConsumeItem") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.ConsumeItem")) {
            event.getPlayer().sendActionBar(Config.getLang("AntiConsumeItem"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerBedEnterEvent(PlayerBedEnterEvent event) {
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.BedEnter") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.BedEnter")) {
            event.getPlayer().sendActionBar(Config.getLang("AntiBedEnter"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerDropItemEvent(PlayerDropItemEvent event) {
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.DropItem") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.DropItem")) {
            event.getPlayer().sendActionBar(Config.getLang("AntiDropItem"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.CommandPreprocess") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if(!(event.getPlayer() instanceof Player)){
            return;
        }
        if (MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.CommandPreprocess")) {
            if(MainClass.getLevelStringlistInit(event.getPlayer().getLevel().getName(),"Player.BanCommands") == null){return;}
            if (MainClass.getLevelStringlistInit(event.getPlayer().getLevel().getName(), "Player.BanCommands").size() > 0) {
                String commandtext = event.getMessage();
                commandtext.replace("/","");
                String[] commandsplit = commandtext.split(" ");
                List<String> bancommands = MainClass.getLevelStringlistInit(event.getPlayer().getLevel().getName(), "Player.BanCommands");
                for (String str :bancommands){
                    String[] bancommandsplit = str.split(" ");
                    for(int i =1 ; i<=bancommandsplit.length; i++){
                        if(bancommandsplit[i-1].equals(commandsplit[i-1])){
                            if(i == bancommandsplit.length){
                                event.getPlayer().sendActionBar(Config.getLang("AntiCommand"));
                                event.setCancelled(true);
                                return;
                            }
                        }else{
                            continue;
                        }
                    }
                }
            }else{
                return;
            }
        }else{
            event.getPlayer().sendActionBar(Config.getLang("AntiAllCommand"));
            event.setCancelled(true);
        }
        return;
    }

    @EventHandler
    public void PlayerGameModeChangeEvent(PlayerGameModeChangeEvent event) {
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.GameModeChange") == null){return;}
        if (!MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.GameModeChange")) {
            event.getPlayer().sendActionBar(Config.getLang("AntiChangeGamemode"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.Move") == null){return;}
        if (!MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.Move")) {
            event.getPlayer().sendActionBar(Config.getLang("AntiMove"));
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void PlayerEatFoodEvent(PlayerEatFoodEvent event) {
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.EatFood") == null){return;}
        if (!MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.EatFood")) {
            event.getPlayer().sendActionBar(Config.getLang("AntiEat"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void DataPacketReceiveEvent(DataPacketReceiveEvent event){
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.Jump") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if(event.getPacket() instanceof PlayerActionPacket){
            PlayerActionPacket pk = (PlayerActionPacket) event.getPacket();
            if((pk).action == PlayerActionPacket.ACTION_JUMP){
                if (!MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.Jump")) {
                    event.getPlayer().sendActionBar(Config.getLang("AntiJump"));
                    event.getPlayer().setPosition(event.getPlayer().getPosition());
                    event.getPlayer().setMotion(new Vector3(0,-1,0));
                }
            }
        }
    }

    @EventHandler
    public void PlayerToggleFlightEvent(PlayerToggleFlightEvent event) {
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.Fly") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.Fly")) {
            event.getPlayer().sendActionBar(Config.getLang("AntiFly"));
            event.getPlayer().setPosition(event.getPlayer().getPosition());
            event.getPlayer().setMotion(new Vector3(0,-1,0));
            event.getPlayer().fall(event.getPlayer().getMotion().getFloorY()+1);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerFoodLevelChangeEvent(PlayerFoodLevelChangeEvent event){
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.HungerChange") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.HungerChange")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event){
        if(MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World.KeepXp") == null){
            event.setKeepExperience(true);
            return;
        }
        if(MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(),"World.KeepInventory") == null){
            event.setKeepInventory(true);
            return;
        }
        Boolean keepXp = MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(), "World.KeepXp");
        Boolean keepInv = MainClass.getLevelBooleanInit(event.getEntity().getLevel().getName(), "World.KeepInventory");
        event.setKeepExperience(keepXp);
        event.setKeepInventory(keepInv);
        event.setDrops(new Item[0]);
    }

    @EventHandler
    public void PlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.Sneak") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.Sneak")) {
            event.getPlayer().sendActionBar(Config.getLang("AntiSneak"));
            event.getPlayer().setSneaking(false);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerToggleSprintEvent(PlayerToggleSprintEvent event) {
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.Sprint") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.Sprint")) {
            event.getPlayer().sendActionBar(Config.getLang("AntiSprint"));
            event.getPlayer().setSprinting(false);
            event.getPlayer().setPosition(event.getPlayer().getPosition());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerToggleSwimEvent(PlayerToggleSwimEvent event) {
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.Swim") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.Swim")) {
            event.getPlayer().sendActionBar(Config.getLang("AntiSwim"));
            event.getPlayer().setSwimming(false);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerToggleGlideEvent(PlayerToggleGlideEvent event) {
        if(MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(),"Player.Glide") == null){return;}
        if(Config.isOperateListed(event.getPlayer(), event.getPlayer().getLevel())){ return; }
        if (!MainClass.getLevelBooleanInit(event.getPlayer().getLevel().getName(), "Player.Glide")) {
            event.getPlayer().sendActionBar(Config.getLang("AntiGlide"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerFormRespondedEvent(PlayerFormRespondedEvent event){
        if(event.getResponse() == null){return;}
        if(event.getWindow() instanceof FormWindowCustom){
            FormWindowCustom formWindowCustom = (FormWindowCustom) event.getWindow();
            String[] strings = formWindowCustom.getTitle().split("【");
            if(strings.length<2){return;}
            if(!strings[0].equals("编辑世界 - ")){return;}
            String levelname = strings[1].replace("】","");
            FormResponseCustom responses = (FormResponseCustom) event.getResponse();
            MainClass.setLevelBooleanInit(levelname,"World.FarmProtect",responses.getToggleResponse(0));
            MainClass.setLevelBooleanInit(levelname,"World.AllExplodes",responses.getToggleResponse(1));
            MainClass.setLevelBooleanInit(levelname,"World.TntExplodes",responses.getToggleResponse(2));
            MainClass.setLevelBooleanInit(levelname,"World.Pvp",responses.getToggleResponse(3));
            MainClass.setLevelBooleanInit(levelname,"World.KeepInventory",responses.getToggleResponse(4));
            MainClass.setLevelBooleanInit(levelname,"World.KeepXp",responses.getToggleResponse(5));
            MainClass.setLevelBooleanInit(levelname,"Player.AllowOpenChest",responses.getToggleResponse(6));
            MainClass.setLevelBooleanInit(levelname,"Player.CanUseFishingHook",responses.getToggleResponse(7));
            MainClass.setLevelBooleanInit(levelname,"Player.AllowInteractFrameBlock",responses.getToggleResponse(8));
            MainClass.setLevelBooleanInit(levelname,"Player.AllowUseFlintAndSteel",responses.getToggleResponse(9));
            MainClass.setLevelBooleanInit(levelname,"Player.Sneak",responses.getToggleResponse(10));
            MainClass.setLevelBooleanInit(levelname,"Player.Fly",responses.getToggleResponse(11));
            MainClass.setLevelBooleanInit(levelname,"Player.Swim",responses.getToggleResponse(12));
            MainClass.setLevelBooleanInit(levelname,"Player.Glide",responses.getToggleResponse(13));
            MainClass.setLevelBooleanInit(levelname,"Player.Jump",responses.getToggleResponse(14));
            MainClass.setLevelBooleanInit(levelname,"Player.Sprint",responses.getToggleResponse(15));
            MainClass.setLevelBooleanInit(levelname,"Player.Pick",responses.getToggleResponse(16));
            MainClass.setLevelBooleanInit(levelname,"Player.ConsumeItem",responses.getToggleResponse(17));
            MainClass.setLevelBooleanInit(levelname,"Player.DropItem",responses.getToggleResponse(18));
            MainClass.setLevelBooleanInit(levelname,"Player.BedEnter",responses.getToggleResponse(19));
            MainClass.setLevelBooleanInit(levelname,"Player.Move",responses.getToggleResponse(20));
            MainClass.setLevelBooleanInit(levelname,"Player.EatFood",responses.getToggleResponse(21));
            MainClass.setLevelBooleanInit(levelname,"Player.CommandPreprocess",responses.getToggleResponse(22));
            MainClass.setLevelBooleanInit(levelname,"Player.GameModeChange",responses.getToggleResponse(23));
            MainClass.setLevelBooleanInit(levelname,"Entity.Explosion",responses.getToggleResponse(24));
            MainClass.setLevelBooleanInit(levelname,"Entity.PortalEnter",responses.getToggleResponse(25));
            MainClass.setLevelBooleanInit(levelname,"Block.AllowPlaceBlock",responses.getToggleResponse(26));
            MainClass.setLevelBooleanInit(levelname,"Block.AllowBreakBlock",responses.getToggleResponse(27));
            MainClass.setLevelBooleanInit(levelname,"Block.Burn",responses.getToggleResponse(28));
            MainClass.setLevelBooleanInit(levelname,"Block.Ignite",responses.getToggleResponse(29));
            MainClass.setLevelBooleanInit(levelname,"Block.Fall",responses.getToggleResponse(30));
            MainClass.setLevelBooleanInit(levelname,"Block.Grow",responses.getToggleResponse(31));
            MainClass.setLevelBooleanInit(levelname,"Block.Spread",responses.getToggleResponse(32));
            MainClass.setLevelBooleanInit(levelname,"Block.Form",responses.getToggleResponse(33));
            MainClass.setLevelBooleanInit(levelname,"Block.DoorToggle",responses.getToggleResponse(34));
            MainClass.setLevelBooleanInit(levelname,"Block.LeavesDecay",responses.getToggleResponse(35));
            MainClass.setLevelBooleanInit(levelname,"Block.LiquidFlow",responses.getToggleResponse(36));
            MainClass.setLevelBooleanInit(levelname,"Block.ItemFrameDropItem",responses.getToggleResponse(37));
            MainClass.setLevelBooleanInit(levelname,"Block.SignChange",responses.getToggleResponse(38));
            MainClass.setLevelBooleanInit(levelname,"Block.BlockRedstone",responses.getToggleResponse(39));
            event.getPlayer().sendMessage("修改成功！");
        }
    }
}