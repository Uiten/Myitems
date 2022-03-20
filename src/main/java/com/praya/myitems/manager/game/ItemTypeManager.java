package com.praya.myitems.manager.game;

import api.praya.myitems.builder.item.ItemType;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.game.ItemTypeConfig;
import java.util.Collection;

public class ItemTypeManager extends HandlerManager {
   private final ItemTypeConfig itemTypeConfig;

   protected ItemTypeManager(MyItems plugin) {
      super(plugin);
      this.itemTypeConfig = new ItemTypeConfig(plugin);
   }

   public final ItemTypeConfig getItemTypeConfig() {
      return this.itemTypeConfig;
   }

   public final Collection<String> getItemTypeIDs() {
      return this.getItemTypeConfig().getItemTypeIDs();
   }

   public final Collection<ItemType> getItemTypes() {
      return this.getItemTypeConfig().getItemTypes();
   }

   public final ItemType getItemType(String id) {
      return this.getItemTypeConfig().getItemType(id);
   }

   public final boolean isItemTypeExists(String id) {
      return this.getItemType(id) != null;
   }
}
