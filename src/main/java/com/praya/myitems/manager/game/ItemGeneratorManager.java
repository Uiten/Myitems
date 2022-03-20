package com.praya.myitems.manager.game;

import api.praya.myitems.builder.item.ItemGenerator;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.game.ItemGeneratorConfig;
import java.util.Collection;

public class ItemGeneratorManager extends HandlerManager {
   private final ItemGeneratorConfig itemGeneratorConfig;

   protected ItemGeneratorManager(MyItems plugin) {
      super(plugin);
      this.itemGeneratorConfig = new ItemGeneratorConfig(plugin);
   }

   public final ItemGeneratorConfig getItemGeneratorConfig() {
      return this.itemGeneratorConfig;
   }

   public final Collection<String> getItemGeneratorIDs() {
      return this.getItemGeneratorConfig().getItemGeneratorIDs();
   }

   public final Collection<ItemGenerator> getItemGenerators() {
      return this.getItemGeneratorConfig().getItemGenerators();
   }

   public final ItemGenerator getItemGenerator(String nameid) {
      return this.getItemGeneratorConfig().getItemGenerator(nameid);
   }

   public final boolean isItemGeneratorExists(String nameid) {
      return this.getItemGenerator(nameid) != null;
   }
}
