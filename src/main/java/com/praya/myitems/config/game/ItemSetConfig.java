package com.praya.myitems.config.game;

import api.praya.myitems.builder.ability.AbilityItemWeapon;
import api.praya.myitems.builder.item.ItemSet;
import api.praya.myitems.builder.item.ItemSetBonus;
import api.praya.myitems.builder.item.ItemSetBonusEffect;
import api.praya.myitems.builder.item.ItemSetBonusEffectAbilityWeapon;
import api.praya.myitems.builder.item.ItemSetBonusEffectStats;
import api.praya.myitems.builder.item.ItemSetComponent;
import api.praya.myitems.builder.item.ItemSetComponentItem;
import com.praya.agarthalib.utility.EnchantmentUtil;
import com.praya.agarthalib.utility.FileUtil;
import com.praya.agarthalib.utility.MaterialUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerConfig;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.enums.main.Slot;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;

public class ItemSetConfig extends HandlerConfig {
   private final HashMap<String, ItemSet> mapItemSet = new HashMap();

   public ItemSetConfig(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getItemSetIDs() {
      return this.mapItemSet.keySet();
   }

   public final Collection<ItemSet> getAllItemSet() {
      return this.mapItemSet.values();
   }

   public final ItemSet getItemSet(String id) {
      Iterator var3 = this.getItemSetIDs().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(id)) {
            return (ItemSet)this.mapItemSet.get(key);
         }
      }

      return null;
   }

   public final void setup() {
      this.reset();
      this.loadConfig();
   }

   private final void reset() {
      this.mapItemSet.clear();
   }

   private final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathDefault = dataManager.getPath("Path_File_Item_Set");
      String pathFolder = dataManager.getPath("Path_Folder_Item_Set");
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
            HashMap<Integer, ItemSetBonus> mapBonus = new HashMap();
            HashMap<String, ItemSetComponent> mapComponent = new HashMap();
            String name = key.replace("_", " ");
            Iterator var19 = mainDataSection.getKeys(false).iterator();

            while(true) {
               label334:
               while(var19.hasNext()) {
                  String mainData = (String)var19.next();
                  if (mainData.equalsIgnoreCase("Name")) {
                     name = mainDataSection.getString(mainData);
                  } else {
                     ConfigurationSection bonusAmountSection;
                     String bonusAmount;
                     Iterator var22;
                     ConfigurationSection componentDataSection;
                     ArrayList lores;
                     String descriptionLine;
                     String componentData;
                     Iterator var36;
                     ConfigurationSection enchantmentDataSection;
                     if (mainData.equalsIgnoreCase("Bonus")) {
                        bonusAmountSection = mainDataSection.getConfigurationSection(mainData);
                        var22 = bonusAmountSection.getKeys(false).iterator();

                        label272:
                        while(true) {
                           do {
                              if (!var22.hasNext()) {
                                 continue label334;
                              }

                              bonusAmount = (String)var22.next();
                           } while(!MathUtil.isNumber(bonusAmount));

                           componentDataSection = bonusAmountSection.getConfigurationSection(bonusAmount);
                           int amountID = MathUtil.parseInteger(bonusAmount);
                           lores = new ArrayList();
                           ItemSetBonusEffectStats bonusEffectStats = new ItemSetBonusEffectStats();
                           ItemSetBonusEffectAbilityWeapon bonusEffectAbilityWeapon = new ItemSetBonusEffectAbilityWeapon();
                           Iterator var84 = componentDataSection.getKeys(false).iterator();

                           while(true) {
                              while(true) {
                                 label257:
                                 while(var84.hasNext()) {
                                    String bonusData = (String)var84.next();
                                    if (bonusData.equalsIgnoreCase("Description")) {
                                       if (componentDataSection.isList(bonusData)) {
                                          Iterator var88 = componentDataSection.getStringList(bonusData).iterator();

                                          while(var88.hasNext()) {
                                             descriptionLine = (String)var88.next();
                                             lores.add(ChatColor.stripColor(TextUtil.colorful(descriptionLine)));
                                          }
                                       } else if (componentDataSection.isString(bonusData)) {
                                          lores.add(ChatColor.stripColor(TextUtil.colorful(componentDataSection.getString(bonusData))));
                                       }
                                    } else if (bonusData.equalsIgnoreCase("Effect")) {
                                       ConfigurationSection effectDataSection = componentDataSection.getConfigurationSection(bonusData);
                                       Iterator var89 = effectDataSection.getKeys(false).iterator();

                                       while(true) {
                                          label213:
                                          while(true) {
                                             if (!var89.hasNext()) {
                                                continue label257;
                                             }

                                             String effectData = (String)var89.next();
                                             ConfigurationSection effectStatsSection;
                                             double chance;
                                             if (effectData.equalsIgnoreCase("Stats")) {
                                                effectStatsSection = effectDataSection.getConfigurationSection(effectData);
                                                double additionalDamage = 0.0D;
                                                double percentDamage = 0.0D;
                                                chance = 0.0D;
                                                double pvpDamage = 0.0D;
                                                double pveDamage = 0.0D;
                                                double additionalDefense = 0.0D;
                                                double percentDefense = 0.0D;
                                                double health = 0.0D;
                                                double healthRegen = 0.0D;
                                                double staminaMax = 0.0D;
                                                double staminaRegen = 0.0D;
                                                double attackAoERadius = 0.0D;
                                                double attackAoEDamage = 0.0D;
                                                double pvpDefense = 0.0D;
                                                double pveDefense = 0.0D;
                                                double criticalChance = 0.0D;
                                                double criticalDamage = 0.0D;
                                                double blockAmount = 0.0D;
                                                double blockRate = 0.0D;
                                                double hitRate = 0.0D;
                                                double dodgeRate = 0.0D;
                                                Iterator var77 = effectStatsSection.getKeys(false).iterator();

                                                while(true) {
                                                   while(true) {
                                                      while(var77.hasNext()) {
                                                         String effectStats = (String)var77.next();
                                                         if (!effectStats.equalsIgnoreCase("Additional_Damage") && !effectStats.equalsIgnoreCase("Damage")) {
                                                            if (effectStats.equalsIgnoreCase("Percent_Damage")) {
                                                               percentDamage = effectStatsSection.getDouble(effectStats);
                                                            } else if (effectStats.equalsIgnoreCase("Penetration")) {
                                                               chance = effectStatsSection.getDouble(effectStats);
                                                            } else if (effectStats.equalsIgnoreCase("PvP_Damage")) {
                                                               pvpDamage = effectStatsSection.getDouble(effectStats);
                                                            } else if (effectStats.equalsIgnoreCase("PvE_Damage")) {
                                                               pveDamage = effectStatsSection.getDouble(effectStats);
                                                            } else if (!effectStats.equalsIgnoreCase("Additional_Defense") && !effectStats.equalsIgnoreCase("Defense")) {
                                                               if (effectStats.equalsIgnoreCase("Percent_Defense")) {
                                                                  percentDefense = effectStatsSection.getDouble(effectStats);
                                                               } else if (effectStats.equalsIgnoreCase("Health")) {
                                                                  health = effectStatsSection.getDouble(effectStats);
                                                               } else if (!effectStats.equalsIgnoreCase("Health_Regen") && !effectStats.equalsIgnoreCase("Regen") && !effectStats.equalsIgnoreCase("Regeneration")) {
                                                                  if (!effectStats.equalsIgnoreCase("Stamina_Max") && !effectStats.equalsIgnoreCase("Max_Stamina")) {
                                                                     if (!effectStats.equalsIgnoreCase("Stamina_Regen") && !effectStats.equalsIgnoreCase("Regen_Stamina")) {
                                                                        if (effectStats.equalsIgnoreCase("Attack_AoE_Radius")) {
                                                                           attackAoERadius = effectStatsSection.getDouble(effectStats);
                                                                        } else if (effectStats.equalsIgnoreCase("Attack_AoE_Damage")) {
                                                                           attackAoEDamage = effectStatsSection.getDouble(effectStats);
                                                                        } else if (effectStats.equalsIgnoreCase("PvP_Defense")) {
                                                                           pvpDefense = effectStatsSection.getDouble(effectStats);
                                                                        } else if (effectStats.equalsIgnoreCase("PvE_Defense")) {
                                                                           pveDefense = effectStatsSection.getDouble(effectStats);
                                                                        } else if (effectStats.equalsIgnoreCase("Critical_Chance")) {
                                                                           criticalChance = effectStatsSection.getDouble(effectStats);
                                                                        } else if (effectStats.equalsIgnoreCase("Critical_Damage")) {
                                                                           criticalDamage = effectStatsSection.getDouble(effectStats);
                                                                        } else if (effectStats.equalsIgnoreCase("Block_Amount")) {
                                                                           blockAmount = effectStatsSection.getDouble(effectStats);
                                                                        } else if (effectStats.equalsIgnoreCase("Block_Rate")) {
                                                                           blockRate = effectStatsSection.getDouble(effectStats);
                                                                        } else if (effectStats.equalsIgnoreCase("Hit_Rate")) {
                                                                           hitRate = effectStatsSection.getDouble(effectStats);
                                                                        } else if (effectStats.equalsIgnoreCase("Dodge_Rate")) {
                                                                           dodgeRate = effectStatsSection.getDouble(effectStats);
                                                                        }
                                                                     } else {
                                                                        staminaRegen = effectStatsSection.getDouble(effectStats);
                                                                     }
                                                                  } else {
                                                                     staminaMax = effectStatsSection.getDouble(effectStats);
                                                                  }
                                                               } else {
                                                                  healthRegen = effectStatsSection.getDouble(effectStats);
                                                               }
                                                            } else {
                                                               additionalDefense = effectStatsSection.getDouble(effectStats);
                                                            }
                                                         } else {
                                                            additionalDamage = effectStatsSection.getDouble(effectStats);
                                                         }
                                                      }

                                                      bonusEffectStats = new ItemSetBonusEffectStats(additionalDamage, percentDamage, chance, pvpDamage, pveDamage, additionalDefense, percentDefense, health, healthRegen, staminaMax, staminaRegen, attackAoERadius, attackAoEDamage, pvpDefense, pveDefense, criticalChance, criticalDamage, blockAmount, blockRate, hitRate, dodgeRate);
                                                      continue label213;
                                                   }
                                                }
                                             } else if (effectData.equalsIgnoreCase("Ability_Weapon")) {
                                                effectStatsSection = effectDataSection.getConfigurationSection(effectData);
                                                HashMap<String, AbilityItemWeapon> mapAbilityItem = new HashMap();
                                                var36 = effectStatsSection.getKeys(false).iterator();

                                                while(var36.hasNext()) {
                                                   componentData = (String)var36.next();
                                                   enchantmentDataSection = effectStatsSection.getConfigurationSection(componentData);
                                                   chance = 100.0D;
                                                   int grade = 0;
                                                   Iterator var42 = enchantmentDataSection.getKeys(false).iterator();

                                                   while(var42.hasNext()) {
                                                      String effectAbilityWeaponData = (String)var42.next();
                                                      if (effectAbilityWeaponData.equalsIgnoreCase("Chance")) {
                                                         chance = MathUtil.limitDouble(enchantmentDataSection.getDouble(effectAbilityWeaponData), 0.0D, 100.0D);
                                                      } else if (effectAbilityWeaponData.equalsIgnoreCase("Grade")) {
                                                         grade = enchantmentDataSection.getInt(effectAbilityWeaponData);
                                                      }
                                                   }

                                                   if (grade > 0) {
                                                      AbilityItemWeapon abilityItemWeapon = new AbilityItemWeapon(componentData, grade, chance);
                                                      mapAbilityItem.put(componentData, abilityItemWeapon);
                                                   }
                                                }

                                                bonusEffectAbilityWeapon = new ItemSetBonusEffectAbilityWeapon(mapAbilityItem);
                                             }
                                          }
                                       }
                                    }
                                 }

                                 ItemSetBonusEffect itemSetBonusEffect = new ItemSetBonusEffect(bonusEffectStats, bonusEffectAbilityWeapon);
                                 ItemSetBonus itemSetBonus = new ItemSetBonus(amountID, lores, itemSetBonusEffect);
                                 mapBonus.put(amountID, itemSetBonus);
                                 continue label272;
                              }
                           }
                        }
                     } else if (mainData.equalsIgnoreCase("Component")) {
                        bonusAmountSection = mainDataSection.getConfigurationSection(mainData);
                        var22 = bonusAmountSection.getKeys(false).iterator();

                        while(var22.hasNext()) {
                           bonusAmount = (String)var22.next();
                           componentDataSection = bonusAmountSection.getConfigurationSection(bonusAmount);
                           String componentID = key + "_" + bonusAmount;
                           lores = new ArrayList();
                           List<String> flags = new ArrayList();
                           Set<Slot> slots = new HashSet();
                           HashMap<Enchantment, Integer> mapEnchantment = new HashMap();
                           String keyLore = componentID.replace("_", " ");
                           descriptionLine = null;
                           Material material = null;
                           boolean shiny = false;
                           boolean unbreakable = false;
                           short data = 0;
                           var36 = componentDataSection.getKeys(false).iterator();

                           while(true) {
                              while(var36.hasNext()) {
                                 componentData = (String)var36.next();
                                 if (componentData.equalsIgnoreCase("KeyLore")) {
                                    keyLore = ChatColor.stripColor(TextUtil.colorful(componentDataSection.getString(componentData)));
                                 } else if (!componentData.equalsIgnoreCase("Display_Name") && !componentData.equalsIgnoreCase("Display") && !componentData.equalsIgnoreCase("Name")) {
                                    if (componentData.equalsIgnoreCase("Material")) {
                                       material = MaterialUtil.getMaterial(componentDataSection.getString(componentData));
                                    } else if (componentData.equalsIgnoreCase("Data")) {
                                       data = (short)componentDataSection.getInt(componentData);
                                    } else if (componentData.equalsIgnoreCase("Shiny")) {
                                       shiny = componentDataSection.getBoolean(componentData);
                                    } else if (componentData.equalsIgnoreCase("Unbreakable")) {
                                       unbreakable = componentDataSection.getBoolean(componentData);
                                    } else if (!componentData.equalsIgnoreCase("Lores") && !componentData.equalsIgnoreCase("Lore")) {
                                       if (!componentData.equalsIgnoreCase("Flags") && !componentData.equalsIgnoreCase("ItemFlags")) {
                                          String slotData;
                                          Iterator var39;
                                          if (!componentData.equalsIgnoreCase("Slots") && !componentData.equalsIgnoreCase("Slot")) {
                                             if (componentData.equalsIgnoreCase("Enchantments") || componentData.equalsIgnoreCase("Enchantment")) {
                                                enchantmentDataSection = componentDataSection.getConfigurationSection(componentData);
                                                var39 = enchantmentDataSection.getKeys(false).iterator();

                                                while(var39.hasNext()) {
                                                   slotData = (String)var39.next();
                                                   Enchantment enchantment = EnchantmentUtil.getEnchantment(slotData);
                                                   if (enchantment != null) {
                                                      int grade = enchantmentDataSection.getInt(slotData);
                                                      mapEnchantment.put(enchantment, grade);
                                                   }
                                                }
                                             }
                                          } else if (componentDataSection.isString(componentData)) {
                                             slotData = componentDataSection.getString(componentData);
                                             Slot slot = Slot.get(slotData);
                                             if (slot != null) {
                                                slots.add(slot);
                                             }
                                          } else if (componentDataSection.isList(componentData)) {
                                             List<String> listSlotData = componentDataSection.getStringList(componentData);
                                             var39 = listSlotData.iterator();

                                             while(var39.hasNext()) {
                                                slotData = (String)var39.next();
                                                Slot slot = Slot.get(slotData);
                                                if (slot != null) {
                                                   slots.add(slot);
                                                }
                                             }
                                          }
                                       } else {
                                          flags.addAll(componentDataSection.getStringList(componentData));
                                       }
                                    } else {
                                       lores.addAll(componentDataSection.getStringList(componentData));
                                    }
                                 } else {
                                    descriptionLine = componentDataSection.getString(componentData);
                                 }
                              }

                              if (slots.isEmpty()) {
                                 Slot[] var102;
                                 int var100 = (var102 = Slot.values()).length;

                                 for(int var95 = 0; var95 < var100; ++var95) {
                                    Slot slot = var102[var95];
                                    slots.add(slot);
                                 }
                              }

                              if (material != null) {
                                 ItemSetComponentItem itemSetComponentItem = new ItemSetComponentItem(descriptionLine, material, data, shiny, unbreakable, lores, flags, mapEnchantment);
                                 ItemSetComponent itemSetComponent = new ItemSetComponent(key, componentID, keyLore, itemSetComponentItem, slots);
                                 mapComponent.put(componentID, itemSetComponent);
                              }
                              break;
                           }
                        }
                     }
                  }
               }

               ItemSet itemSet = new ItemSet(key, name, mapBonus, mapComponent);
               this.mapItemSet.put(key, itemSet);
               break;
            }
         }
      }

   }
}
