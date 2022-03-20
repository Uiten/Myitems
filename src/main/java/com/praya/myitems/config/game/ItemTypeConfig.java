package com.praya.myitems.config.game;

import api.praya.myitems.builder.item.ItemType;
import api.praya.myitems.builder.item.ItemTypeNBT;
import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.lorestats.LoreStatsModifier;
import api.praya.myitems.builder.lorestats.LoreStatsUniversal;
import api.praya.myitems.builder.lorestats.LoreStatsWeapon;
import com.praya.agarthalib.utility.EnchantmentUtil;
import com.praya.agarthalib.utility.FileUtil;
import com.praya.agarthalib.utility.MaterialUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerConfig;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.enums.main.TagsAttribute;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;

public class ItemTypeConfig extends HandlerConfig {
   private final HashMap<String, ItemType> mapType = new HashMap();

   public ItemTypeConfig(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getItemTypeIDs() {
      return this.mapType.keySet();
   }

   public final Collection<ItemType> getItemTypes() {
      return this.mapType.values();
   }

   public final ItemType getItemType(String id) {
      Iterator var3 = this.getItemTypeIDs().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(id)) {
            return (ItemType)this.mapType.get(key);
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
      this.mapType.clear();
   }

   private final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathDefault = dataManager.getPath("Path_File_Item_Type");
      String pathFolder = dataManager.getPath("Path_Folder_Item_Type");
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
            HashMap<Enchantment, Integer> mapEnchantment = new HashMap();
            HashMap<Slot, ItemTypeNBT> mapNBT = new HashMap();
            Material material = null;
            boolean shiny = false;
            short data = 0;
            LoreStatsModifier statsModifier = new LoreStatsModifier();
            Iterator var22 = mainDataSection.getKeys(false).iterator();

            while(true) {
               label155:
               while(var22.hasNext()) {
                  String mainData = (String)var22.next();
                  if (mainData.equalsIgnoreCase("Material")) {
                     material = MaterialUtil.getMaterial(mainDataSection.getString(mainData));
                  } else if (mainData.equalsIgnoreCase("Data")) {
                     data = (short)mainDataSection.getInt(mainData);
                  } else if (mainData.equalsIgnoreCase("Shiny")) {
                     shiny = mainDataSection.getBoolean(mainData);
                  } else {
                     ConfigurationSection modifierDataSection;
                     Iterator var25;
                     String nbtData;
                     if (mainData.equalsIgnoreCase("Enchantment")) {
                        modifierDataSection = mainDataSection.getConfigurationSection(mainData);
                        var25 = modifierDataSection.getKeys(false).iterator();

                        while(var25.hasNext()) {
                           nbtData = (String)var25.next();
                           Enchantment enchantment = EnchantmentUtil.getEnchantment(nbtData);
                           if (enchantment != null) {
                              int grade = modifierDataSection.getInt(nbtData);
                              mapEnchantment.put(enchantment, grade);
                           }
                        }
                     } else {
                        double tagsValue;
                        if (mainData.equalsIgnoreCase("NBT")) {
                           modifierDataSection = mainDataSection.getConfigurationSection(mainData);
                           var25 = modifierDataSection.getKeys(false).iterator();

                           while(true) {
                              Slot slot;
                              do {
                                 if (!var25.hasNext()) {
                                    continue label155;
                                 }

                                 nbtData = (String)var25.next();
                                 slot = Slot.get(nbtData);
                              } while(slot == null);

                              ConfigurationSection tagsDataSection = modifierDataSection.getConfigurationSection(nbtData);
                              HashMap<TagsAttribute, Double> mapTagsAttribute = new HashMap();
                              Iterator var76 = tagsDataSection.getKeys(false).iterator();

                              while(var76.hasNext()) {
                                 String tagsData = (String)var76.next();
                                 TagsAttribute tagsAttribute = TagsAttribute.getTagsAttribute(tagsData);
                                 if (tagsAttribute != null) {
                                    tagsValue = tagsDataSection.getDouble(tagsData);
                                    mapTagsAttribute.put(tagsAttribute, tagsValue);
                                 }
                              }

                              ItemTypeNBT itemTypeNBT = new ItemTypeNBT(mapTagsAttribute);
                              mapNBT.put(slot, itemTypeNBT);
                           }
                        } else if (mainData.equalsIgnoreCase("Modifier")) {
                           modifierDataSection = mainDataSection.getConfigurationSection(mainData);
                           double damage = 1.0D;
                           double penetration = 1.0D;
                           double pvpDamage = 1.0D;
                           double pveDamage = 1.0D;
                           tagsValue = 1.0D;
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
                           Iterator var67 = modifierDataSection.getKeys(false).iterator();

                           while(var67.hasNext()) {
                              String modifierData = (String)var67.next();
                              if (modifierData.equalsIgnoreCase("Damage")) {
                                 damage = modifierDataSection.getDouble(modifierData);
                              } else if (modifierData.equalsIgnoreCase("Penetration")) {
                                 penetration = modifierDataSection.getDouble(modifierData);
                              } else if (modifierData.equalsIgnoreCase("PvP_Damage")) {
                                 pvpDamage = modifierDataSection.getDouble(modifierData);
                              } else if (modifierData.equalsIgnoreCase("PvE_Damage")) {
                                 pveDamage = modifierDataSection.getDouble(modifierData);
                              } else if (modifierData.equalsIgnoreCase("Critical_Chance")) {
                                 tagsValue = modifierDataSection.getDouble(modifierData);
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

                           LoreStatsWeapon weaponModifier = new LoreStatsWeapon(damage, penetration, pvpDamage, pveDamage, attackAoERadius, attackAoEDamage, tagsValue, criticalDamage, hitRate);
                           LoreStatsArmor armorModifier = new LoreStatsArmor(defense, pvpDefense, pveDefense, health, healthRegen, staminaMax, staminaRegen, blockAmount, blockRate, dodgeRate);
                           LoreStatsUniversal universalModifier = new LoreStatsUniversal(durability, level);
                           statsModifier = new LoreStatsModifier(weaponModifier, armorModifier, universalModifier);
                        }
                     }
                  }
               }

               if (material != null) {
                  ItemType itemType = new ItemType(key, material, data, shiny, statsModifier, mapEnchantment, mapNBT);
                  this.mapType.put(key, itemType);
               }
               break;
            }
         }
      }

   }

   private final void moveOldFile() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathSource_1 = "item_type.yml";
      String pathSource_2 = "Configuration/item_type.yml";
      String pathTarget = dataManager.getPath("Path_File_Item_Type");
      File fileSource_1 = FileUtil.getFile(this.plugin, "item_type.yml");
      File fileSource_2 = FileUtil.getFile(this.plugin, "Configuration/item_type.yml");
      File fileTarget = FileUtil.getFile(this.plugin, pathTarget);
      if (fileSource_1.exists()) {
         FileUtil.moveFileSilent(fileSource_1, fileTarget);
      } else if (fileSource_2.exists()) {
         FileUtil.moveFileSilent(fileSource_2, fileTarget);
      }

   }
}
