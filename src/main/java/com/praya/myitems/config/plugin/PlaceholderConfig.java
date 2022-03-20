package com.praya.myitems.config.plugin;

import com.praya.agarthalib.utility.FileUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerConfig;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class PlaceholderConfig extends HandlerConfig {
   private final HashMap<String, String> mapPlaceholder = new HashMap();

   public PlaceholderConfig(MyItems plugin) {
      super(plugin);
      this.setup();
   }

   public final Collection<String> getPlaceholderIDs() {
      return this.mapPlaceholder.keySet();
   }

   public final Collection<String> getPlaceholders() {
      return this.mapPlaceholder.values();
   }

   public final String getPlaceholder(String id) {
      Iterator var3 = this.getPlaceholderIDs().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(id)) {
            return (String)this.mapPlaceholder.get(key);
         }
      }

      return null;
   }

   public final HashMap<String, String> getPlaceholderCopy() {
      return new HashMap(this.mapPlaceholder);
   }

   public final void setup() {
      this.moveOldFile();
      this.reset();
      this.loadConfig();
   }

   private final void reset() {
      this.mapPlaceholder.clear();
   }

   private final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String path = dataManager.getPath("Path_File_Placeholder");
      File file = FileUtil.getFile(this.plugin, path);
      if (!file.exists()) {
         FileUtil.saveResource(this.plugin, path);
      }

      label40:
      for(int t = 0; t < 2; ++t) {
         FileConfiguration config = t == 0 ? FileUtil.getFileConfigurationResource(this.plugin, path) : FileUtil.getFileConfiguration(file);
         Iterator var8 = config.getKeys(false).iterator();

         while(true) {
            String key;
            do {
               if (!var8.hasNext()) {
                  continue label40;
               }

               key = (String)var8.next();
            } while(!key.equalsIgnoreCase("Placeholder"));

            ConfigurationSection placeholderSection = config.getConfigurationSection(key);
            Iterator var11 = placeholderSection.getKeys(false).iterator();

            while(var11.hasNext()) {
               String placeholder = (String)var11.next();
               this.mapPlaceholder.put(placeholder, placeholderSection.getString(placeholder));
            }
         }
      }

   }

   private final void moveOldFile() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathSource = "placeholder.yml";
      String pathTarget = dataManager.getPath("Path_File_Placeholder");
      File fileSource = FileUtil.getFile(this.plugin, "placeholder.yml");
      File fileTarget = FileUtil.getFile(this.plugin, pathTarget);
      if (fileSource.exists()) {
         FileUtil.moveFileSilent(fileSource, fileTarget);
      }

   }
}
