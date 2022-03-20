package com.praya.myitems.listener.support;

import api.praya.combatstamina.builder.event.PlayerStaminaMaxChangeEvent;
import api.praya.combatstamina.builder.event.PlayerStaminaMaxChangeEvent.StaminaMaxModifierEnum;
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

public class ListenerPlayerStaminaMaxChange extends HandlerEvent implements Listener {
   public ListenerPlayerStaminaMaxChange(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void eventPlayerStaminaMaxChange(PlayerStaminaMaxChangeEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      SocketManager socketManager = gameManager.getSocketManager();
      if (!event.isCancelled()) {
         Player player = event.getPlayer();
         StaminaMaxModifierEnum staminaMaxType = StaminaMaxModifierEnum.BONUS;
         LoreStatsArmor statsBuild = statsManager.getLoreStatsArmor((LivingEntity)player);
         SocketGemsProperties socketBuild = socketManager.getSocketProperties((LivingEntity)player);
         double staminaMaxStats = statsBuild.getStaminaMax();
         double staminaMaxSocket = socketBuild.getStaminaMax();
         double staminaMaxBase = event.getOriginalMaxStamina(staminaMaxType);
         double staminaMaxResult = staminaMaxStats + staminaMaxSocket + staminaMaxBase;
         event.setMaxStamina(staminaMaxType, staminaMaxResult);
      }

   }
}
