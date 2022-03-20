package com.praya.myitems.manager.plugin;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class PluginManager extends HandlerManager {
   boolean initialize = false;
   private DataManager dataManager;
   private LanguageManager languageManager;
   private CommandManager commandManager;
   private DependencyManager dependencyManager;
   private HookManager hookManager;
   private PlaceholderManager placeholderManager;
   private PluginPropertiesManager pluginPropertiesManager;
   private MetricsManager metricsManager;

   public PluginManager(MyItems plugin) {
      super(plugin);
   }

   public final void initialize() {
      if (!this.initialize) {
         this.initialize = true;
         this.dataManager = new DataManager(this.plugin);
         this.placeholderManager = new PlaceholderManager(this.plugin);
         this.languageManager = new LanguageManager(this.plugin);
         this.commandManager = new CommandManager(this.plugin);
         this.dependencyManager = new DependencyManager(this.plugin);
         this.hookManager = new HookManager(this.plugin);
         this.pluginPropertiesManager = new PluginPropertiesManager(this.plugin);
         this.metricsManager = new MetricsManager(this.plugin);
      }

   }

   public final CommandManager getCommandManager() {
      return this.commandManager;
   }

   public final DataManager getDataManager() {
      return this.dataManager;
   }

   public final DependencyManager getDependencyManager() {
      return this.dependencyManager;
   }

   public final HookManager getHookManager() {
      return this.hookManager;
   }

   public final LanguageManager getLanguageManager() {
      return this.languageManager;
   }

   public final PlaceholderManager getPlaceholderManager() {
      return this.placeholderManager;
   }

   public final PluginPropertiesManager getPluginPropertiesManager() {
      return this.pluginPropertiesManager;
   }

   public final MetricsManager getMetricsManager() {
      return this.metricsManager;
   }
}
