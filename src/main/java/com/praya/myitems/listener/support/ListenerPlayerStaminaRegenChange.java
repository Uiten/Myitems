package com.praya.myitems.listener.support;

import api.praya.combatstamina.builder.event.PlayerStaminaRegenChangeEvent;
import api.praya.combatstamina.builder.event.PlayerStaminaRegenChangeEvent.StaminaRegenModifierEnum;
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

public class ListenerPlayerStaminaRegenChange extends HandlerEvent implements Listener {
   public ListenerPlayerStaminaRegenChange(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void eventPlayerStaminaMaxChange(PlayerStaminaRegenChangeEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      SocketManager socketManager = gameManager.getSocketManager();
      if (!event.isCancelled()) {
         Player player = event.getPlayer();
         StaminaRegenModifierEnum staminaRegenType = StaminaRegenModifierEnum.BONUS;
         LoreStatsArmor statsBuild = statsManager.getLoreStatsArmor((LivingEntity)player);
         SocketGemsProperties socketBuild = socketManager.getSocketProperties((LivingEntity)player);
         double staminaRegenStats = statsBuild.getStaminaRegen();
         double staminaRegenSocket = socketBuild.getStaminaRegen();
         double staminaRegenBase = event.getOriginalStaminaRegen(staminaRegenType);
         double staminaRegenResult = staminaRegenStats + staminaRegenSocket + staminaRegenBase;
         event.setStaminaRegen(staminaRegenType, staminaRegenResult);
      }

   }
}
