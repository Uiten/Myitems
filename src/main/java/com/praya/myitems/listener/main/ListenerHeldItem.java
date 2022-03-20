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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ListenerHeldItem extends HandlerEvent implements Listener {
   public ListenerHeldItem(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void playerItemHeldEvent(PlayerItemHeldEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
      final ItemSetManager itemSetManager = gameManager.getItemSetManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (!event.isCancelled()) {
         final Player player = event.getPlayer();
         int slotPrevious = event.getPreviousSlot();
         int slotAfter = event.getNewSlot();
         Inventory inventory = player.getInventory();
         final ItemStack itemBefore = inventory.getItem(slotPrevious);
         final ItemStack itemAfter = inventory.getItem(slotAfter);
         final boolean enableItemUniversal = mainConfig.isStatsEnableItemUniversal();
         final boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
         final boolean isSolidBefore = EquipmentUtil.isSolid(itemBefore);
         final boolean isSolidAfter = EquipmentUtil.isSolid(itemAfter);
         if (isSolidBefore || isSolidAfter) {
            (new BukkitRunnable() {
               public void run() {
                  boolean isShieldBefore = isSolidBefore ? itemBefore.getType().toString().equalsIgnoreCase("SHIELD") : false;
                  boolean isShieldAfter = isSolidAfter ? itemAfter.getType().toString().equalsIgnoreCase("SHIELD") : false;
                  if (enableItemUniversal || isShieldBefore || isShieldAfter) {
                     TriggerSupportUtil.updateSupport(player);
                  }

                  if (itemSetManager.isItemSet(itemBefore) || itemSetManager.isItemSet(itemAfter)) {
                     itemSetManager.updateItemSet(player);
                  }

                  Collection passiveEffectAfter;
                  if (isSolidBefore) {
                     passiveEffectAfter = passiveEffectManager.getPassiveEffects(itemBefore);
                     passiveEffectManager.reloadPassiveEffect(player, passiveEffectAfter, enableGradeCalculation);
                  }

                  if (isSolidAfter) {
                     passiveEffectAfter = passiveEffectManager.getPassiveEffects(itemAfter);
                     passiveEffectManager.reloadPassiveEffect(player, passiveEffectAfter, enableGradeCalculation);
                  }

               }
            }).runTaskLater(this.plugin, 0L);
         }
      }

   }
}
