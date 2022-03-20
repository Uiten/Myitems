package com.praya.myitems.config.game;

import api.praya.myitems.builder.power.PowerSpecialEnum;
import api.praya.myitems.builder.power.PowerSpecialProperties;
import com.praya.agarthalib.utility.FileUtil;
import com.praya.agarthalib.utility.MathUtil;
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

public class PowerSpecialConfig extends HandlerConfig {
   private final HashMap<PowerSpecialEnum, PowerSpecialProperties> mapPowerSpecial = new HashMap();

   public PowerSpecialConfig(MyItems plugin) {
      super(plugin);
   }

   public final Collection<PowerSpecialEnum> getPowerSpecialIDs() {
      return this.mapPowerSpecial.keySet();
   }

   public final Collection<PowerSpecialProperties> getPowerSpecialPropertyBuilds() {
      return this.mapPowerSpecial.values();
   }

   public final PowerSpecialProperties getPowerSpecialProperties(PowerSpecialEnum id) {
      Iterator var3 = this.getPowerSpecialIDs().iterator();

      while(var3.hasNext()) {
         PowerSpecialEnum key = (PowerSpecialEnum)var3.next();
         if (key.equals(id)) {
            return (PowerSpecialProperties)this.mapPowerSpecial.get(key);
         }
      }

      return null;
   }

   public final void setup() {
      this.moveOldFile();
      this.reset();
      this.loadConfig();
   }

   private final void reset() {
      this.mapPowerSpecial.clear();
   }

   private final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String path = dataManager.getPath("Path_File_Power_Special");
      File file = FileUtil.getFile(this.plugin, path);
      if (!file.exists()) {
         FileUtil.saveResource(this.plugin, path);
      }

      label50:
      for(int t = 0; t < 2; ++t) {
         FileConfiguration config = t == 0 ? FileUtil.getFileConfigurationResource(this.plugin, path) : FileUtil.getFileConfiguration(file);
         Iterator var8 = config.getKeys(false).iterator();

         while(true) {
            String key;
            PowerSpecialEnum special;
            do {
               if (!var8.hasNext()) {
                  continue label50;
               }

               key = (String)var8.next();
               special = PowerSpecialEnum.get(key);
            } while(special == null);

            ConfigurationSection section = config.getConfigurationSection(key);
            int durationEffect = 1;
            double baseAdditionalDamage = 0.0D;
            double basePercentDamage = 100.0D;
            Iterator var17 = section.getKeys(false).iterator();

            while(var17.hasNext()) {
               String keySection = (String)var17.next();
               if (keySection.equalsIgnoreCase("Duration_Effect")) {
                  durationEffect = section.getInt(keySection);
               } else if (keySection.equalsIgnoreCase("Base_Additional_Damage")) {
                  baseAdditionalDamage = section.getDouble(keySection);
               } else if (keySection.equalsIgnoreCase("Base_Percent_Damage")) {
                  basePercentDamage = section.getDouble(keySection);
               }
            }

            durationEffect = MathUtil.limitInteger(durationEffect, 1, durationEffect);
            baseAdditionalDamage = MathUtil.limitDouble(baseAdditionalDamage, 0.0D, baseAdditionalDamage);
            basePercentDamage = MathUtil.limitDouble(basePercentDamage, 0.0D, basePercentDamage);
            PowerSpecialProperties powerSpecialProperties = new PowerSpecialProperties(durationEffect, baseAdditionalDamage, basePercentDamage);
            this.mapPowerSpecial.put(special, powerSpecialProperties);
         }
      }

   }

   private final void moveOldFile() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathSource = "specialpower.yml";
      String pathTarget = dataManager.getPath("Path_File_Power_Special");
      File fileSource = FileUtil.getFile(this.plugin, "specialpower.yml");
      File fileTarget = FileUtil.getFile(this.plugin, pathTarget);
      if (fileSource.exists()) {
         FileUtil.moveFileSilent(fileSource, fileTarget);
      }

   }
}
