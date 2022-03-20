package com.praya.myitems.listener.support;

import api.praya.lifeessence.builder.event.PlayerHealthRegenChangeEvent;
import api.praya.lifeessence.builder.event.PlayerHealthRegenChangeEvent.HealthRegenModifierEnum;
import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.SocketManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ListenerPlayerHealthRegenChange extends HandlerEvent implements Listener {
   public ListenerPlayerHealthRegenChange(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void eventPlayerHealthRegenChange(PlayerHealthRegenChangeEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      SocketManager socketManager = gameManager.getSocketManager();
      if (!event.isCancelled()) {
         Player player = event.getPlayer();
         HealthRegenModifierEnum healthRegenType = HealthRegenModifierEnum.BONUS;
         LoreStatsArmor statsBuild = statsManager.getLoreStatsArmor((LivingEntity)player);
         SocketGemsProperties socketBuild = socketManager.getSocketProperties((LivingEntity)player);
         double healthRegenStats = statsBuild.getHealthRegen();
         double healthRegenSocket = socketBuild.getHealthRegen();
         double healthRegenBase = event.getOriginalHealthRegen(healthRegenType);
         double healthRegenResult = healthRegenStats + healthRegenSocket + healthRegenBase;
         event.setHealthRegen(healthRegenType, healthRegenResult);
      }

   }
}
