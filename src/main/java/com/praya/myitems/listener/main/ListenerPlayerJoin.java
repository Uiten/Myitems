package com.praya.myitems.listener.main;

import api.praya.myitems.builder.passive.PassiveEffectTypeEnum;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.utility.main.CustomEffectUtil;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ListenerPlayerJoin extends HandlerEvent implements Listener {
   public ListenerPlayerJoin(MyItems plugin) {
      super(plugin);
   }

   @EventHandler
   public void eventPlayerJoin(PlayerJoinEvent event) {
      final Player player = event.getPlayer();
      (new BukkitRunnable() {
         public void run() {
            if (player.isOnline() && player.getWalkSpeed() < 0.05F) {
               player.setWalkSpeed(0.2F);
            }

         }
      }).runTaskLater(this.plugin, 1L);
      (new BukkitRunnable() {
         public void run() {
            if (!PlayerUtil.isOnline(player)) {
               this.cancel();
            } else {
               if (CustomEffectUtil.isRunCustomEffect(player, PassiveEffectTypeEnum.ROOTS)) {
                  Vector vector = player.getVelocity().setY(-1);
                  player.setVelocity(vector);
               }

            }
         }
      }).runTaskTimer(this.plugin, 0L, 1L);
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void triggerSupport(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      TriggerSupportUtil.updateSupport(player);
   }
}
