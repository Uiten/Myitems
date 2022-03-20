package com.praya.myitems.listener.main;

import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemSetManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ListenerInventoryDrag extends HandlerEvent implements Listener {
   public ListenerInventoryDrag(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void inventoryDragEvent(InventoryDragEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      final ItemSetManager itemSetManager = gameManager.getItemSetManager();
      if (!event.isCancelled()) {
         final Player player = PlayerUtil.parse(event.getWhoClicked());
         final HashMap<Slot, ItemStack> mapItemSlot = new HashMap();
         Slot[] var9;
         int var8 = (var9 = Slot.values()).length;

         for(int var7 = 0; var7 < var8; ++var7) {
            Slot slot = var9[var7];
            ItemStack itemSlot = Bridge.getBridgeEquipment().getEquipment(player, slot);
            mapItemSlot.put(slot, itemSlot);
         }

         (new BukkitRunnable() {
            public void run() {
               InventoryView inventoryView = player.getOpenInventory();
               InventoryType inventoryType = inventoryView.getType();
               Inventory inventory = !inventoryType.equals(InventoryType.CREATIVE) ? inventoryView.getTopInventory() : null;
               Slot[] var7;
               int var6 = (var7 = Slot.values()).length;

               for(int var5 = 0; var5 < var6; ++var5) {
                  Slot slot = var7[var5];
                  ItemStack itemBefore = (ItemStack)mapItemSlot.get(slot);
                  ItemStack itemAfter = Bridge.getBridgeEquipment().getEquipment(player, slot);
                  boolean isSolidBefore = itemBefore != null;
                  boolean isSolidAfter = itemAfter != null;
                  if (isSolidBefore && isSolidAfter) {
                     if (itemBefore.equals(itemAfter)) {
                        continue;
                     }
                  } else if (isSolidBefore == isSolidAfter) {
                     continue;
                  }

                  if (itemSetManager.isItemSet(itemBefore) || itemSetManager.isItemSet(itemAfter)) {
                     itemSetManager.updateItemSet(player, true, inventory);
                     break;
                  }
               }

            }
         }).runTaskLater(this.plugin, 0L);
      }

   }
}
