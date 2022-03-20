package com.praya.myitems.listener.main;

import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import java.util.Collection;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ListenerPlayerSwapHandItems extends HandlerEvent implements Listener {
   public ListenerPlayerSwapHandItems(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void triggerEquipmentChangeEvent(PlayerSwapHandItemsEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      final ItemSetManager itemSetManager = gameManager.getItemSetManager();
      if (!event.isCancelled()) {
         final Player player = event.getPlayer();
         ItemStack itemMainHand = event.getMainHandItem();
         ItemStack itemOffHand = event.getOffHandItem();
         if (itemSetManager.isItemSet(itemMainHand) || itemSetManager.isItemSet(itemOffHand)) {
            (new BukkitRunnable() {
               public void run() {
                  itemSetManager.updateItemSet(player);
               }
            }).runTaskLater(this.plugin, 0L);
         }
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void playerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (!event.isCancelled()) {
         final Player player = event.getPlayer();
         ItemStack itemMainHand = event.getMainHandItem();
         ItemStack itemOffHand = event.getOffHandItem();
         boolean enableItemUniversal = mainConfig.isStatsEnableItemUniversal();
         boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
         boolean isShieldMainHand;
         Material materialOffHand;
         Collection passiveEffectsOffHand;
         if (EquipmentUtil.isSolid(itemMainHand)) {
            materialOffHand = itemMainHand.getType();
            passiveEffectsOffHand = passiveEffectManager.getPassiveEffects(itemMainHand);
            isShieldMainHand = materialOffHand.toString().equalsIgnoreCase("SHIELD");
            passiveEffectManager.reloadPassiveEffect(player, passiveEffectsOffHand, enableGradeCalculation);
         } else {
            isShieldMainHand = false;
         }

         boolean isShieldOffHand;
         if (EquipmentUtil.isSolid(itemOffHand)) {
            materialOffHand = itemOffHand.getType();
            passiveEffectsOffHand = passiveEffectManager.getPassiveEffects(itemOffHand);
            isShieldOffHand = materialOffHand.toString().equalsIgnoreCase("SHIELD");
            passiveEffectManager.reloadPassiveEffect(player, passiveEffectsOffHand, enableGradeCalculation);
         } else {
            isShieldOffHand = false;
         }

         if (enableItemUniversal || isShieldMainHand || isShieldOffHand) {
            (new BukkitRunnable() {
               public void run() {
                  TriggerSupportUtil.updateSupport(player);
               }
            }).runTaskLater(this.plugin, 0L);
         }
      }

   }
}
