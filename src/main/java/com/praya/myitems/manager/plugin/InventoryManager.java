package com.praya.myitems.manager.plugin;

import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import core.praya.agarthalib.builder.inventory.InventoryBuild;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryManager extends HandlerManager {
   private final HashMap<UUID, InventoryBuild> mapInventoryBuild = new HashMap();
   private final HashMap<UUID, Long> mapInventoryCooldown = new HashMap();
   private final HashMap<UUID, Long> mapInventoryCloseCooldown = new HashMap();

   protected InventoryManager(MyItems plugin) {
      super(plugin);
   }

   public final HashMap<UUID, InventoryBuild> getMapInventoryBuild() {
      return this.mapInventoryBuild;
   }

   public final HashMap<UUID, Long> getMapInventoryCooldown() {
      return this.mapInventoryCooldown;
   }

   public final HashMap<UUID, Long> getMapInventoryCloseCooldown() {
      return this.mapInventoryCloseCooldown;
   }

   public final long getInventoryCooldown(Player player) {
      return this.hasInventoryCooldown(player) ? (Long)this.getMapInventoryCooldown().get(player.getUniqueId()) : 0L;
   }

   public final long getInventoryCloseCooldown(Player player) {
      return this.hasInventoryCloseCooldown(player) ? (Long)this.getMapInventoryCloseCooldown().get(player.getUniqueId()) : 0L;
   }

   public final InventoryBuild getInventoryBuild(Player player) {
      return this.hasInventoryBuild(player) ? (InventoryBuild)this.getMapInventoryBuild().get(player.getUniqueId()) : null;
   }

   public final void setInventoryCooldown(Player player, long cooldown) {
      long expired = System.currentTimeMillis() + cooldown;
      this.getMapInventoryCooldown().put(player.getUniqueId(), expired);
   }

   public final void setInventoryCloseCooldown(Player player, long cooldown) {
      long expired = System.currentTimeMillis() + cooldown;
      this.getMapInventoryCloseCooldown().put(player.getUniqueId(), expired);
   }

   public final boolean isCooldown(Player player) {
      if (this.hasInventoryCooldown(player)) {
         long expired = this.getInventoryCooldown(player);
         long time = System.currentTimeMillis();
         if (time < expired) {
            return true;
         } else {
            this.removeInventoryCooldown(player);
            return false;
         }
      } else {
         return false;
      }
   }

   public final boolean isCloseCooldown(Player player) {
      if (this.hasInventoryCloseCooldown(player)) {
         long expired = this.getInventoryCloseCooldown(player);
         long time = System.currentTimeMillis();
         if (time < expired) {
            return true;
         } else {
            this.removeInventoryCloseCooldown(player);
            return false;
         }
      } else {
         return false;
      }
   }

   public final void openInventory(Player player, InventoryBuild inventoryBuild) {
      UUID playerID = player.getUniqueId();
      Inventory inventory = inventoryBuild.getInventory();
      if (inventoryBuild.hasSoundOpen()) {
         SenderUtil.playSound(player, inventoryBuild.getSoundOpen());
      }

      this.setInventoryCooldown(player, 50L);
      player.openInventory(inventory);
      this.getMapInventoryBuild().put(playerID, inventoryBuild);
   }

   public final InventoryBuild createInventory(Player player, String title, InventoryType type, int row) {
      return this.createInventory(player, title, type, row, false, false);
   }

   public final InventoryBuild createInventory(Player player, String title, InventoryType type, int row, boolean isEditable, boolean isOwned) {
      Inventory inventory;
      if (type.equals(InventoryType.CHEST)) {
         int size = MathUtil.limitInteger(row, 1, 6) * 9;
         title = title.isEmpty() ? "Menu" : (title.length() > 32 ? title.substring(0, 32) : title);
         title = TextUtil.colorful(title);
         inventory = Bukkit.createInventory(isOwned ? player : null, size, title);
      } else {
         title = TextUtil.colorful(title);
         inventory = Bukkit.createInventory(isOwned ? player : null, type, title);
      }

      return new InventoryBuild(inventory, isEditable);
   }

   public final boolean hasInventoryBuild(Player player) {
      return this.getMapInventoryBuild().containsKey(player.getUniqueId());
   }

   public final boolean hasInventoryCooldown(Player player) {
      return this.getMapInventoryCooldown().containsKey(player.getUniqueId());
   }

   public final boolean hasInventoryCloseCooldown(Player player) {
      return this.getMapInventoryCloseCooldown().containsKey(player.getUniqueId());
   }

   public final void removeInventoryBuild(Player player) {
      this.getMapInventoryBuild().remove(player.getUniqueId());
   }

   public final void removeInventoryCooldown(Player player) {
      this.getMapInventoryCooldown().remove(player.getUniqueId());
   }

   public final void removeInventoryCloseCooldown(Player player) {
      this.getMapInventoryCloseCooldown().remove(player.getUniqueId());
   }
}
