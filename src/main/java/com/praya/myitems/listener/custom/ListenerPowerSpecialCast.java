package com.praya.myitems.listener.custom;

import api.praya.myitems.builder.event.PowerSpecialCastEvent;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.player.PlayerPowerCooldown;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.abs.SpecialPower;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.player.PlayerPowerManager;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class ListenerPowerSpecialCast extends HandlerEvent implements Listener {
   public ListenerPowerSpecialCast(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void eventPowerSpecialCast(PowerSpecialCastEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      PlayerPowerManager playerPowerManager = this.plugin.getPlayerManager().getPlayerPowerManager();
      if (!event.isCancelled()) {
         Player player = event.getPlayer();
         ItemStack item = event.getItem();
         PowerSpecialEnum special = event.getSpecial();
         SpecialPower specialPower = SpecialPower.getSpecial(special);
         double cooldown = event.getCooldown();
         long timeCooldown = MathUtil.convertSecondsToMilis(cooldown);
         int durability = (int)statsManager.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT);
         PlayerPowerCooldown powerCooldown = playerPowerManager.getPlayerPowerCooldown(player);
         specialPower.cast(player);
         if (timeCooldown > 0L) {
            powerCooldown.setPowerSpecialCooldown(special, timeCooldown);
         }

         if (!statsManager.durability(player, item, durability, true)) {
            statsManager.sendBrokenCode(player, Slot.MAINHAND);
         }
      }

   }
}
