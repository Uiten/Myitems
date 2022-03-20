package com.praya.myitems.listener.custom;

import api.praya.myitems.builder.event.PowerShootCastEvent;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.player.PlayerPowerCooldown;
import com.praya.agarthalib.utility.CombatUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.player.PlayerPowerManager;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class ListenerPowerShootCast extends HandlerEvent implements Listener {
   public ListenerPowerShootCast(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void eventPowerShootCast(PowerShootCastEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      PlayerPowerManager playerPowerManager = this.plugin.getPlayerManager().getPlayerPowerManager();
      if (!event.isCancelled()) {
         Player player = event.getPlayer();
         ItemStack item = event.getItem();
         ProjectileEnum projectile = event.getProjectile();
         double cooldown = event.getCooldown();
         double speed = event.getSpeed();
         long timeCooldown = MathUtil.convertSecondsToMilis(cooldown);
         int durability = (int)statsManager.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT);
         PlayerPowerCooldown powerCooldown = playerPowerManager.getPlayerPowerCooldown(player);
         CombatUtil.launchProjectile(player, projectile, speed, false, true);
         if (timeCooldown > 0L) {
            powerCooldown.setPowerShootCooldown(projectile, timeCooldown);
         }

         if (!statsManager.durability(player, item, durability, true)) {
            statsManager.sendBrokenCode(player, Slot.MAINHAND);
         }
      }

   }
}
