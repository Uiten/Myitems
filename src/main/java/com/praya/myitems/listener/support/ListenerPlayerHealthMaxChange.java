package com.praya.myitems.listener.support;

import api.praya.agarthalib.builder.event.PlayerHealthMaxChangeEvent;
import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.SocketManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ListenerPlayerHealthMaxChange extends HandlerEvent implements Listener {
   public ListenerPlayerHealthMaxChange(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void eventPlayerHealthMaxChange(PlayerHealthMaxChangeEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      SocketManager socketManager = gameManager.getSocketManager();
      MainConfig mainConfig = MainConfig.getInstance();
      boolean enableMaxHealth = mainConfig.isStatsEnableMaxHealth();
      if (!event.isCancelled() && enableMaxHealth) {
         Player player = event.getPlayer();
         LoreStatsArmor statsBuild = statsManager.getLoreStatsArmor((LivingEntity)player);
         SocketGemsProperties socketBuild = socketManager.getSocketProperties((LivingEntity)player);
         double healthStats = statsBuild.getHealth();
         double healthSocket = socketBuild.getHealth();
         double healthBase = event.getMaxHealth();
         double healthResult = healthStats + healthSocket + healthBase;
         event.setMaxHealth(healthResult);
      }

   }
}
