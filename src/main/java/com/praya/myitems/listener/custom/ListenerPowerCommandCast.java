package com.praya.myitems.listener.custom;

import api.praya.myitems.builder.event.PowerCommandCastEvent;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.player.PlayerPowerCooldown;
import api.praya.myitems.builder.power.PowerCommandProperties;
import com.praya.agarthalib.utility.CommandUtil;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.PowerCommandManager;
import com.praya.myitems.manager.game.PowerManager;
import com.praya.myitems.manager.player.PlayerManager;
import com.praya.myitems.manager.player.PlayerPowerManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class ListenerPowerCommandCast extends HandlerEvent implements Listener {
   public ListenerPowerCommandCast(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void eventPowerCommandCast(PowerCommandCastEvent event) {
      PlayerManager playerManager = this.plugin.getPlayerManager();
      GameManager gameManager = this.plugin.getGameManager();
      PowerManager powerManager = gameManager.getPowerManager();
      PowerCommandManager powerCommandManager = powerManager.getPowerCommandManager();
      PlayerPowerManager playerPowerManager = playerManager.getPlayerPowerManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      if (!event.isCancelled()) {
         Player player = event.getPlayer();
         ItemStack item = event.getItem();
         String keyCommand = event.getKeyCommand();
         PowerCommandProperties powerCommandProperties = powerCommandManager.getPowerCommandProperties(keyCommand);
         PlayerPowerCooldown powerCooldown = playerPowerManager.getPlayerPowerCooldown(player);
         boolean consume = powerCommandProperties.isConsume();
         double cooldown = event.getCooldown();
         long timeCooldown = MathUtil.convertSecondsToMilis(cooldown);
         int durability = (int)statsManager.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT);
         List<String> commandOP = powerCommandProperties.getCommandOP();
         List<String> commandConsole = powerCommandProperties.getCommandConsole();
         HashMap<String, String> mapPlaceholder = new HashMap();
         mapPlaceholder.put("player", player.getName());
         Iterator var23 = commandOP.iterator();

         String command;
         while(var23.hasNext()) {
            command = (String)var23.next();
            command = TextUtil.placeholder(mapPlaceholder, command);
            command = TextUtil.placeholder(mapPlaceholder, command, "<", ">");
            command = TextUtil.hookPlaceholderAPI(player, command);
            CommandUtil.sudoCommand(player, command, true);
         }

         var23 = commandConsole.iterator();

         while(var23.hasNext()) {
            command = (String)var23.next();
            command = TextUtil.placeholder(mapPlaceholder, command);
            command = TextUtil.placeholder(mapPlaceholder, command, "<", ">");
            command = TextUtil.hookPlaceholderAPI(player, command);
            CommandUtil.consoleCommand(command);
         }

         if (timeCooldown > 0L) {
            powerCooldown.setPowerCommandCooldown(keyCommand, timeCooldown);
         }

         if (consume) {
            int amount = item.getAmount();
            if (amount > 1) {
               EquipmentUtil.setAmount(item, amount - 1);
            } else {
               Bridge.getBridgeEquipment().setEquipment(player, (ItemStack)null, Slot.MAINHAND);
            }
         } else if (!statsManager.durability(player, item, durability, true)) {
            statsManager.sendBrokenCode(player, Slot.MAINHAND);
         }
      }

   }
}
