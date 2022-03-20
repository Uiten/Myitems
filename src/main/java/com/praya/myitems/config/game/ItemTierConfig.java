package com.praya.myitems.config.game;

import api.praya.myitems.builder.item.ItemTier;
import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.lorestats.LoreStatsModifier;
import api.praya.myitems.builder.lorestats.LoreStatsUniversal;
import api.praya.myitems.builder.lorestats.LoreStatsWeapon;
import com.praya.agarthalib.utility.FileUtil;
import com.praya.agarthalib.utility.TextUtil;
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

public class ItemTierConfig extends HandlerConfig {
   private final HashMap<String, ItemTier> mapTier = new HashMap();

   public ItemTierConfig(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getItemTierIDs() {
      return this.mapTier.keySet();
   }

   public final Collection<ItemTier> getItemTiers() {
      return this.mapTier.values();
   }

   public final ItemTier getItemTier(String id) {
      Iterator var3 = this.getItemTierIDs().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(id)) {
            return (ItemTier)this.mapTier.get(key);
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
      this.mapTier.clear();
   }

   private final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathDefault = dataManager.getPath("Path_File_Item_Tier");
      String pathFolder = dataManager.getPath("Path_Folder_Item_Tier");
      File fileDefault = FileUtil.getFile(this.plugin, pathDefault);
      File fileFolder = FileUtil.getFile(this.plugin, pathFolder);
      if (!fileDefault.exists()) {
         FileUtil.saveResource(this.plugin, pathDefault);
      }

      File[] var10;
      int var9 = (var10 = fileFolder.listFiles()).length;

      for(int var8 = 0; var8 < var9; ++var8) {
         File file = var10[var8];
         FileConfiguration config = FileUtil.getFileConfiguration(file);
         Iterator var13 = config.getKeys(false).iterator();

         while(var13.hasNext()) {
            String key = (String)var13.next();
            ConfigurationSection mainDataSection = config.getConfigurationSection(key);
            String name = "";
            String prefix = "";
            LoreStatsModifier statsModifier = new LoreStatsModifier();
            Iterator var19 = mainDataSection.getKeys(false).iterator();

            while(true) {
               while(var19.hasNext()) {
                  String mainData = (String)var19.next();
                  if (mainData.equalsIgnoreCase("Name")) {
                     name = TextUtil.colorful(mainDataSection.getString(mainData));
                  } else if (mainData.equalsIgnoreCase("Prefix")) {
                     prefix = TextUtil.colorful(mainDataSection.getString(mainData));
                  } else if (mainData.equalsIgnoreCase("Modifier")) {
                     ConfigurationSection modifierDataSection = mainDataSection.getConfigurationSection(mainData);
                     double damage = 1.0D;
                     double penetration = 1.0D;
                     double pvpDamage = 1.0D;
                     double pveDamage = 1.0D;
                     double criticalChance = 1.0D;
                     double criticalDamage = 1.0D;
                     double hitRate = 1.0D;
                     double defense = 1.0D;
                     double pvpDefense = 1.0D;
                     double pveDefense = 1.0D;
                     double health = 1.0D;
                     double healthRegen = 1.0D;
                     double staminaMax = 1.0D;
                     double staminaRegen = 1.0D;
                     double attackAoERadius = 1.0D;
                     double attackAoEDamage = 1.0D;
                     double blockAmount = 1.0D;
                     double blockRate = 1.0D;
                     double dodgeRate = 1.0D;
                     double durability = 1.0D;
                     double level = 1.0D;
                     Iterator var64 = modifierDataSection.getKeys(false).iterator();

                     while(var64.hasNext()) {
                        String modifierData = (String)var64.next();
                        if (modifierData.equalsIgnoreCase("Damage")) {
                           damage = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Penetration")) {
                           penetration = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("PvP_Damage")) {
                           pvpDamage = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("PvE_Damage")) {
                           pveDamage = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Critical_Chance")) {
                           criticalChance = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Critical_Damage")) {
                           criticalDamage = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Hit_Rate")) {
                           hitRate = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Defense")) {
                           defense = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("PvP_Defense")) {
                           pvpDefense = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("PvE_Defense")) {
                           pveDefense = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Health")) {
                           health = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Health_Regen")) {
                           healthRegen = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Stamina_Max")) {
                           staminaMax = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Stamina_Regen")) {
                           staminaRegen = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Attack_AoE_Radius")) {
                           attackAoERadius = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Attack_AoE_Damage")) {
                           attackAoEDamage = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Block_Amount")) {
                           blockAmount = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Block_Rate")) {
                           blockRate = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Dodge_Rate")) {
                           dodgeRate = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Durability")) {
                           durability = modifierDataSection.getDouble(modifierData);
                        } else if (modifierData.equalsIgnoreCase("Level")) {
                           level = modifierDataSection.getDouble(modifierData);
                        }
                     }

                     LoreStatsWeapon weaponModifier = new LoreStatsWeapon(damage, penetration, pvpDamage, pveDamage, attackAoERadius, attackAoEDamage, criticalChance, criticalDamage, hitRate);
                     LoreStatsArmor armorModifier = new LoreStatsArmor(defense, pvpDefense, pveDefense, health, healthRegen, staminaMax, staminaRegen, blockAmount, blockRate, dodgeRate);
                     LoreStatsUniversal universalModifier = new LoreStatsUniversal(durability, level);
                     statsModifier = new LoreStatsModifier(weaponModifier, armorModifier, universalModifier);
                  }
               }

               ItemTier itemTier = new ItemTier(key, name, prefix, statsModifier);
               this.mapTier.put(key, itemTier);
               break;
            }
         }
      }

   }

   private final void moveOldFile() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathSource_1 = "item_tier.yml";
      String pathSource_2 = "Configuration/item_tier.yml";
      String pathTarget = dataManager.getPath("Path_File_Item_Tier");
      File fileSource_1 = FileUtil.getFile(this.plugin, "item_tier.yml");
      File fileSource_2 = FileUtil.getFile(this.plugin, "Configuration/item_tier.yml");
      File fileTarget = FileUtil.getFile(this.plugin, pathTarget);
      if (fileSource_1.exists()) {
         FileUtil.moveFileSilent(fileSource_1, fileTarget);
      } else if (fileSource_2.exists()) {
         FileUtil.moveFileSilent(fileSource_2, fileTarget);
      }

   }
}
