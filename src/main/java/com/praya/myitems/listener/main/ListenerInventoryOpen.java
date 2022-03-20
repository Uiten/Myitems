package com.praya.myitems.listener.main;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemSetManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public class ListenerInventoryOpen extends HandlerEvent implements Listener {
   public ListenerInventoryOpen(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void inventoryOpenEvent(InventoryOpenEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      ItemSetManager itemSetManager = gameManager.getItemSetManager();
      if (!event.isCancelled()) {
         Inventory inventory = event.getInventory();
         HumanEntity human = event.getPlayer();
         if (human instanceof Player) {
            Player player = (Player)human;
            itemSetManager.updateItemSet(player, false, inventory);
         }
      }

   }
}
