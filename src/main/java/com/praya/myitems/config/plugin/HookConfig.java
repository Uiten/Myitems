package com.praya.myitems.config.plugin;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerConfig;
import com.praya.myitems.manager.plugin.DependencyManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.manager.plugin.PluginPropertiesManager;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.builder.plugin.PluginPropertiesResourceBuild;
import core.praya.agarthalib.enums.main.Dependency;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.Bukkit;

public class HookConfig extends HandlerConfig {
   private final HashMap<String, Dependency> mapHook = new HashMap();

   public HookConfig(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getHookKeys() {
      return this.mapHook.keySet();
   }

   public final boolean isHook(String plugin) {
      return this.getTypeDependency(plugin) != null;
   }

   public final Dependency getTypeDependency(String plugin) {
      Iterator var3 = this.getHookKeys().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(plugin)) {
            return (Dependency)this.mapHook.get(key);
         }
      }

      return null;
   }

   public final void removeHook(String plugin) {
      this.mapHook.remove(plugin);
   }

   public final void setup() {
      this.reset();
      this.loadConfig();
   }

   private final void reset() {
      this.mapHook.clear();
   }

   private final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DependencyManager dependencyManager = pluginManager.getDependencyManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      MainConfig mainConfig = MainConfig.getInstance();
      Dependency[] var8;
      int var7 = (var8 = Dependency.values()).length;

      for(int var6 = 0; var6 < var7; ++var6) {
         Dependency type = var8[var6];
         Iterator var10 = dependencyManager.getDependency(type).iterator();

         while(var10.hasNext()) {
            String dependency = (String)var10.next();
            if (Bukkit.getPluginManager().isPluginEnabled(dependency)) {
               this.mapHook.put(dependency, type);
            } else if (dependencyManager.getTypeDependency(dependency).equals(Dependency.HARD_DEPENDENCY)) {
               MessageBuild message = lang.getMessage("Plugin_Lack_Dependency");
               message.sendMessage("dependency", dependency);
               this.plugin.getPluginLoader().disablePlugin(this.plugin);
               return;
            }
         }
      }

      if (mainConfig.isHookMessage()) {
         this.sendMessage();
      }

   }

   private final void sendMessage() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DependencyManager dependencyManager = pluginManager.getDependencyManager();
      if (dependencyManager.hasAnyDependency()) {
         PluginPropertiesManager pluginPropertiesManager = pluginManager.getPluginPropertiesManager();
         PluginPropertiesResourceBuild pluginPropertiesResource = pluginPropertiesManager.getPluginPropertiesResource();
         LanguageManager lang = pluginManager.getLanguageManager();
         HashMap<String, String> map = new HashMap();
         MessageBuild messageHeader = lang.getMessage("Hook_Header");
         MessageBuild messageFooter = lang.getMessage("Hook_Footer");
         map.put("plugin", pluginPropertiesResource.getName());
         messageHeader.sendMessage(map);
         Dependency[] var12;
         int var11 = (var12 = Dependency.values()).length;

         for(int var10 = 0; var10 < var11; ++var10) {
            Dependency type = var12[var10];
            if (dependencyManager.hasDependency(type)) {
               MessageBuild messageListHeader = lang.getMessage(type.equals(Dependency.HARD_DEPENDENCY) ? "Hook_Hard_Dependency_Header" : "Hook_Soft_Dependency_Header");
               messageListHeader.sendMessage();
               Iterator var15 = dependencyManager.getDependency(type).iterator();

               while(var15.hasNext()) {
                  String hook = (String)var15.next();
                  String statusYes = lang.getText("Hook_Status_Hooked");
                  String statusNo = lang.getText("Hook_Status_Not_Hook");
                  MessageBuild messageList = lang.getMessage(type.equals(Dependency.HARD_DEPENDENCY) ? "Hook_Hard_Dependency_Header" : "Hook_Soft_Dependency_List");
                  HashMap<String, String> subMap = new HashMap();
                  subMap.put("hook", hook);
                  subMap.put("status", this.isHook(hook) ? statusYes : statusNo);
                  messageList.sendMessage(subMap);
               }
            }
         }

         messageFooter.sendMessage(map);
      }

   }
}
