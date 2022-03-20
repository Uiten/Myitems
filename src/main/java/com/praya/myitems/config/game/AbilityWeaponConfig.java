package com.praya.myitems.config.game;

import api.praya.myitems.builder.ability.AbilityWeaponProperties;
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

public class AbilityWeaponConfig extends HandlerConfig {
   private final HashMap<String, AbilityWeaponProperties> mapAbilityWeaponProperties = new HashMap();

   public AbilityWeaponConfig(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getAbilityWeaponPropertiesIDs() {
      return this.mapAbilityWeaponProperties.keySet();
   }

   public final Collection<AbilityWeaponProperties> getAllAbilityWeaponProperties() {
      return this.mapAbilityWeaponProperties.values();
   }

   public final AbilityWeaponProperties getAbilityWeaponProperties(String ability) {
      if (ability != null) {
         Iterator var3 = this.getAbilityWeaponPropertiesIDs().iterator();

         while(var3.hasNext()) {
            String key = (String)var3.next();
            if (key.equalsIgnoreCase(ability)) {
               return (AbilityWeaponProperties)this.mapAbilityWeaponProperties.get(key);
            }
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
      this.mapAbilityWeaponProperties.clear();
   }

   private final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String path = dataManager.getPath("Path_File_Ability_Weapon");
      File file = FileUtil.getFile(this.plugin, path);
      if (!file.exists()) {
         FileUtil.saveResource(this.plugin, path);
      }

      for(int t = 0; t < 2; ++t) {
         FileConfiguration config;
         if (t == 0) {
            config = FileUtil.getFileConfigurationResource(this.plugin, path);
         } else {
            config = FileUtil.getFileConfiguration(file);
         }

         Iterator var8 = config.getKeys(false).iterator();

         while(var8.hasNext()) {
            String key = (String)var8.next();
            ConfigurationSection mainDataSection = config.getConfigurationSection(key);
            int maxGrade = 1;
            int baseDurationEffect = 1;
            int scaleDurationEffect = 1;
            double scaleBaseBonusDamage = 0.0D;
            double scaleBasePercentDamage = 0.0D;
            double scaleCastBonusDamage = 0.0D;
            double scaleCastPercentDamage = 0.0D;
            Iterator var22 = mainDataSection.getKeys(false).iterator();

            while(var22.hasNext()) {
               String keySection = (String)var22.next();
               if (keySection.equalsIgnoreCase("Max_Grade")) {
                  maxGrade = mainDataSection.getInt(keySection);
               } else if (keySection.equalsIgnoreCase("Base_Duration_Effect")) {
                  baseDurationEffect = mainDataSection.getInt(keySection);
               } else if (keySection.equalsIgnoreCase("Scale_Duration_Effect")) {
                  scaleDurationEffect = mainDataSection.getInt(keySection);
               } else if (keySection.equalsIgnoreCase("Scale_Base_Bonus_Damage")) {
                  scaleBaseBonusDamage = mainDataSection.getDouble(keySection);
               } else if (keySection.equalsIgnoreCase("Scale_Base_Percent_Damage")) {
                  scaleBasePercentDamage = mainDataSection.getDouble(keySection);
               } else if (keySection.equalsIgnoreCase("Scale_Cast_Bonus_Damage")) {
                  scaleCastBonusDamage = mainDataSection.getDouble(keySection);
               } else if (keySection.equalsIgnoreCase("Scale_Cast_Percent_Damage")) {
                  scaleCastPercentDamage = mainDataSection.getDouble(keySection);
               }
            }

            maxGrade = Math.max(1, maxGrade);
            baseDurationEffect = Math.max(0, baseDurationEffect);
            scaleDurationEffect = Math.max(0, scaleDurationEffect);
            AbilityWeaponProperties abilityWeaponProperties = new AbilityWeaponProperties(maxGrade, baseDurationEffect, scaleDurationEffect, scaleBaseBonusDamage, scaleBasePercentDamage, scaleCastBonusDamage, scaleCastPercentDamage);
            this.mapAbilityWeaponProperties.put(key, abilityWeaponProperties);
         }
      }

   }

   private final void moveOldFile() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathSource = "Configuration/ability.yml";
      String pathTarget = dataManager.getPath("Path_File_Ability_Weapon");
      File fileSource = FileUtil.getFile(this.plugin, "Configuration/ability.yml");
      File fileTarget = FileUtil.getFile(this.plugin, pathTarget);
      if (fileSource.exists()) {
         FileUtil.moveFileSilent(fileSource, fileTarget);
      }

   }
}
