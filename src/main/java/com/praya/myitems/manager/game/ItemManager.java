package com.praya.myitems.manager.game;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.game.ItemConfig;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.bukkit.inventory.ItemStack;

public class ItemManager extends HandlerManager {
   private final ItemConfig itemConfig;

   protected ItemManager(MyItems plugin) {
      super(plugin);
      this.itemConfig = new ItemConfig(plugin);
   }

   public final ItemConfig getItemConfig() {
      return this.itemConfig;
   }

   public final Collection<String> getItemIDs() {
      return this.getItemConfig().getItemIDs();
   }

   public final ItemStack getItem(String nameid) {
      return this.getItemConfig().getItem(nameid);
   }

   public final Collection<ItemStack> getItems() {
      Collection<ItemStack> items = new ArrayList();
      Iterator var3 = this.getItemIDs().iterator();

      while(var3.hasNext()) {
         String id = (String)var3.next();
         ItemStack item = this.getItem(id);
         if (item != null) {
            items.add(item);
         }
      }

      return items;
   }

   public final boolean isExist(String id) {
      return this.getItem(id) != null;
   }

   public final String getRawName(String nameid) {
      Iterator var3 = this.getItemIDs().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(nameid)) {
            return key;
         }
      }

      return null;
   }
}
