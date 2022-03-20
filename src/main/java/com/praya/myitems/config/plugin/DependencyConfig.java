package com.praya.myitems.config.plugin;

import com.praya.agarthalib.utility.FileUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerConfig;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.enums.main.Dependency;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.configuration.file.FileConfiguration;

public class DependencyConfig extends HandlerConfig {
   private final HashMap<Dependency, Collection<String>> mapDependency = new HashMap();

   public DependencyConfig(MyItems plugin) {
      super(plugin);
   }

   public final Collection<Dependency> getDependencyKeys() {
      return this.mapDependency.keySet();
   }

   public final Collection<String> getDependency(Dependency type) {
      return (Collection)(this.mapDependency.containsKey(type) ? (Collection)this.mapDependency.get(type) : new ArrayList());
   }

   public final boolean hasDependency(Dependency type) {
      return this.mapDependency.containsKey(type);
   }

   public final boolean hasAnyDependency() {
      return !this.mapDependency.isEmpty();
   }

   public final void setup() {
      this.reset();
      this.loadConfig();
   }

   private final void reset() {
      this.mapDependency.clear();
   }

   private final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String path = dataManager.getPath("Path_File_Dependency");
      FileConfiguration config = FileUtil.getFileConfigurationResource(this.plugin, path);
      Iterator var6 = config.getKeys(false).iterator();

      while(true) {
         String key;
         ArrayList dependencies;
         label35:
         do {
            while(var6.hasNext()) {
               key = (String)var6.next();
               if (!key.equalsIgnoreCase("Soft_Dependency") && !key.equalsIgnoreCase("Soft_Dependencies")) {
                  continue label35;
               }

               if (config.isString(key)) {
                  dependencies = new ArrayList();
                  dependencies.add(config.getString(key));
                  this.mapDependency.put(Dependency.SOFT_DEPENDENCY, dependencies);
               } else if (config.isList(key)) {
                  this.mapDependency.put(Dependency.SOFT_DEPENDENCY, config.getStringList(key));
               }
            }

            return;
         } while(!key.equalsIgnoreCase("Hard_Dependency") && !key.equalsIgnoreCase("Hard_Dependencies"));

         if (config.isString(key)) {
            dependencies = new ArrayList();
            dependencies.add(config.getString(key));
            this.mapDependency.put(Dependency.HARD_DEPENDENCY, dependencies);
         } else if (config.isList(key)) {
            this.mapDependency.put(Dependency.HARD_DEPENDENCY, config.getStringList(key));
         }
      }
   }
}
