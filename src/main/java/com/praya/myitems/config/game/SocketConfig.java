package com.praya.myitems.config.game;

import api.praya.myitems.builder.socket.SocketGems;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import api.praya.myitems.builder.socket.SocketGemsTree;
import com.praya.agarthalib.utility.EnchantmentUtil;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.FileUtil;
import com.praya.agarthalib.utility.MaterialUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerConfig;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import core.praya.agarthalib.enums.main.RomanNumber;
import core.praya.agarthalib.enums.main.SlotType;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class SocketConfig extends HandlerConfig {
   private final HashMap<String, SocketGemsTree> mapSocketTree = new HashMap();
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$core$praya$agarthalib$enums$main$SlotType;

   public SocketConfig(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getSocketIDs() {
      return this.mapSocketTree.keySet();
   }

   public final Collection<SocketGemsTree> getSocketTreeBuilds() {
      return this.mapSocketTree.values();
   }

   public final SocketGemsTree getSocketTree(String id) {
      Iterator var3 = this.getSocketIDs().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(id)) {
            return (SocketGemsTree)this.mapSocketTree.get(key);
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
      this.mapSocketTree.clear();
   }

   private final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      MainConfig mainConfig = MainConfig.getInstance();
      String path = dataManager.getPath("Path_File_Socket");
      File file = FileUtil.getFile(this.plugin, path);
      if (!file.exists()) {
         FileUtil.saveResource(this.plugin, path);
      }

      FileConfiguration config = FileUtil.getFileConfiguration(file);
      Iterator var8 = config.getKeys(false).iterator();

      while(true) {
         String key;
         ArrayList lores;
         ArrayList flags;
         HashMap enchantments;
         String keyLore;
         String display;
         SlotType typeItem;
         boolean shiny;
         boolean unbreakable;
         int maxGrade;
         double baseSuccessRate;
         double scaleSuccessRate;
         double baseAdditionalDamage;
         double scaleAdditionalDamage;
         double basePercentDamage;
         double scalePercentDamage;
         double basePenetration;
         double scalePenetration;
         double basePvPDamage;
         double scalePvPDamage;
         double basePvEDamage;
         double scalePvEDamage;
         double baseAdditionalDefense;
         double scaleAdditionalDefense;
         double basePercentDefense;
         double scalePercentDefense;
         double baseMaxHealth;
         double scaleMaxHealth;
         double baseHealthRegen;
         double scaleHealthRegen;
         double baseStaminaMax;
         double scaleStaminaMax;
         double baseStaminaRegen;
         double scaleStaminaRegen;
         double baseAttackAoERadius;
         double scaleAttackAoERadius;
         double baseAttackAoEDamage;
         double scaleAttackAoEDamage;
         double basePvPDefense;
         double scalePvPDefense;
         double basePvEDefense;
         double scalePvEDefense;
         double baseCriticalChance;
         double scaleCriticalChance;
         double baseCriticalDamage;
         double scaleCriticalDamage;
         double baseBlockAmount;
         double scaleBlockAmount;
         double baseBlockRate;
         double scaleBlockRate;
         double baseHitRate;
         double scaleHitRate;
         double baseDodgeRate;
         double scaleDodgeRate;
         MaterialEnum materialEnum;
         String effect;
         do {
            Material material;
            short data;
            do {
               label575:
               do {
                  if (!var8.hasNext()) {
                     return;
                  }

                  key = (String)var8.next();
                  ConfigurationSection section = config.getConfigurationSection(key);
                  lores = new ArrayList();
                  flags = new ArrayList();
                  enchantments = new HashMap();
                  keyLore = null;
                  display = null;
                  material = null;
                  typeItem = SlotType.UNIVERSAL;
                  shiny = false;
                  unbreakable = false;
                  data = 0;
                  maxGrade = 1;
                  baseSuccessRate = 100.0D;
                  scaleSuccessRate = 0.0D;
                  baseAdditionalDamage = 0.0D;
                  scaleAdditionalDamage = 0.0D;
                  basePercentDamage = 0.0D;
                  scalePercentDamage = 0.0D;
                  basePenetration = 0.0D;
                  scalePenetration = 0.0D;
                  basePvPDamage = 0.0D;
                  scalePvPDamage = 0.0D;
                  basePvEDamage = 0.0D;
                  scalePvEDamage = 0.0D;
                  baseAdditionalDefense = 0.0D;
                  scaleAdditionalDefense = 0.0D;
                  basePercentDefense = 0.0D;
                  scalePercentDefense = 0.0D;
                  baseMaxHealth = 0.0D;
                  scaleMaxHealth = 0.0D;
                  baseHealthRegen = 0.0D;
                  scaleHealthRegen = 0.0D;
                  baseStaminaMax = 0.0D;
                  scaleStaminaMax = 0.0D;
                  baseStaminaRegen = 0.0D;
                  scaleStaminaRegen = 0.0D;
                  baseAttackAoERadius = 0.0D;
                  scaleAttackAoERadius = 0.0D;
                  baseAttackAoEDamage = 0.0D;
                  scaleAttackAoEDamage = 0.0D;
                  basePvPDefense = 0.0D;
                  scalePvPDefense = 0.0D;
                  basePvEDefense = 0.0D;
                  scalePvEDefense = 0.0D;
                  baseCriticalChance = 0.0D;
                  scaleCriticalChance = 0.0D;
                  baseCriticalDamage = 0.0D;
                  scaleCriticalDamage = 0.0D;
                  baseBlockAmount = 0.0D;
                  scaleBlockAmount = 0.0D;
                  baseBlockRate = 0.0D;
                  scaleBlockRate = 0.0D;
                  baseHitRate = 0.0D;
                  scaleHitRate = 0.0D;
                  baseDodgeRate = 0.0D;
                  scaleDodgeRate = 0.0D;
                  Iterator var110 = section.getKeys(false).iterator();

                  while(true) {
                     label568:
                     while(true) {
                        if (!var110.hasNext()) {
                           continue label575;
                        }

                        String keySection = (String)var110.next();
                        if (keySection.equalsIgnoreCase("KeyLore")) {
                           keyLore = TextUtil.colorful(section.getString(keySection));
                        } else if (!keySection.equalsIgnoreCase("Display_Name") && !keySection.equalsIgnoreCase("Display") && !keySection.equalsIgnoreCase("Name")) {
                           if (keySection.equalsIgnoreCase("Material")) {
                              material = MaterialUtil.getMaterial(section.getString(keySection));
                           } else if (keySection.equalsIgnoreCase("Data")) {
                              data = (short)section.getInt(keySection);
                           } else if (keySection.equalsIgnoreCase("Shiny")) {
                              shiny = section.getBoolean(keySection);
                           } else if (keySection.equalsIgnoreCase("Unbreakable")) {
                              unbreakable = section.getBoolean(keySection);
                           } else if (keySection.equalsIgnoreCase("Max_Grade")) {
                              maxGrade = section.getInt(keySection);
                           } else {
                              String lineEnchant;
                              if (keySection.equalsIgnoreCase("Type_Item")) {
                                 lineEnchant = section.getString(keySection);
                                 typeItem = SlotType.getSlotType(lineEnchant);
                              } else if (keySection.equalsIgnoreCase("Base_Success_Rate")) {
                                 baseSuccessRate = section.getDouble(keySection);
                              } else if (keySection.equalsIgnoreCase("Scale_Success_Rate")) {
                                 scaleSuccessRate = section.getDouble(keySection);
                              } else if (!keySection.equalsIgnoreCase("Lores") && !keySection.equalsIgnoreCase("Lore")) {
                                 if (!keySection.equalsIgnoreCase("Flags") && !keySection.equalsIgnoreCase("ItemFlags")) {
                                    if (!keySection.equalsIgnoreCase("Enchantments") && !keySection.equalsIgnoreCase("Enchantment")) {
                                       if (!keySection.equalsIgnoreCase("Bonus_Base_Additional_Damage") && !keySection.equalsIgnoreCase("Bonus_Additional_Damage")) {
                                          if (keySection.equalsIgnoreCase("Bonus_Scale_Additional_Damage")) {
                                             scaleAdditionalDamage = section.getDouble(keySection);
                                          } else if (!keySection.equalsIgnoreCase("Bonus_Base_Percent_Damage") && !keySection.equalsIgnoreCase("Bonus_Percent_Damage")) {
                                             if (keySection.equalsIgnoreCase("Bonus_Scale_Percent_Damage")) {
                                                scalePercentDamage = section.getDouble(keySection);
                                             } else if (!keySection.equalsIgnoreCase("Bonus_Base_Penetration") && !keySection.equalsIgnoreCase("Bonus_Penetration")) {
                                                if (keySection.equalsIgnoreCase("Bonus_Scale_Penetration")) {
                                                   scalePenetration = section.getDouble(keySection);
                                                } else if (!keySection.equalsIgnoreCase("Bonus_Base_PvP_Damage") && !keySection.equalsIgnoreCase("Bonus_PvP_Damage")) {
                                                   if (keySection.equalsIgnoreCase("Bonus_Scale_PvP_Damage")) {
                                                      scalePvPDamage = section.getDouble(keySection);
                                                   } else if (!keySection.equalsIgnoreCase("Bonus_Base_PvE_Damage") && !keySection.equalsIgnoreCase("Bonus_PvE_Damage")) {
                                                      if (keySection.equalsIgnoreCase("Bonus_Scale_PvE_Damage")) {
                                                         scalePvEDamage = section.getDouble(keySection);
                                                      } else if (!keySection.equalsIgnoreCase("Bonus_Base_Additional_Defense") && !keySection.equalsIgnoreCase("Bonus_Additional_Defense")) {
                                                         if (keySection.equalsIgnoreCase("Bonus_Scale_Additional_Defense")) {
                                                            scaleAdditionalDefense = section.getDouble(keySection);
                                                         } else if (!keySection.equalsIgnoreCase("Bonus_Base_Percent_Defense") && !keySection.equalsIgnoreCase("Bonus_Percent_Defense")) {
                                                            if (keySection.equalsIgnoreCase("Bonus_Scale_Percent_Defense")) {
                                                               scalePercentDefense = section.getDouble(keySection);
                                                            } else if (!keySection.equalsIgnoreCase("Bonus_Base_Max_Health") && !keySection.equalsIgnoreCase("Bonus_Max_Health")) {
                                                               if (keySection.equalsIgnoreCase("Bonus_Scale_Max_Health")) {
                                                                  scaleMaxHealth = section.getDouble(keySection);
                                                               } else if (!keySection.equalsIgnoreCase("Bonus_Base_Health_Regen") && !keySection.equalsIgnoreCase("Bonus_Health_Regen")) {
                                                                  if (keySection.equalsIgnoreCase("Bonus_Scale_Health_Regen")) {
                                                                     scaleHealthRegen = section.getDouble(keySection);
                                                                  } else if (!keySection.equalsIgnoreCase("Bonus_Base_Stamina_Max") && !keySection.equalsIgnoreCase("Bonus_Stamina_Max")) {
                                                                     if (keySection.equalsIgnoreCase("Bonus_Scale_Stamina_Max")) {
                                                                        scaleStaminaMax = section.getDouble(keySection);
                                                                     } else if (!keySection.equalsIgnoreCase("Bonus_Base_Stamina_Regen") && !keySection.equalsIgnoreCase("Bonus_Stamina_Regen")) {
                                                                        if (keySection.equalsIgnoreCase("Bonus_Scale_Stamina_Regen")) {
                                                                           scaleStaminaRegen = section.getDouble(keySection);
                                                                        } else if (!keySection.equalsIgnoreCase("Bonus_Base_Attack_AoE_Radius") && !keySection.equalsIgnoreCase("Bonus_Attack_AoE_Radius")) {
                                                                           if (keySection.equalsIgnoreCase("Bonus_Scale_Attack_AoE_Radius")) {
                                                                              scaleAttackAoERadius = section.getDouble(keySection);
                                                                           } else if (!keySection.equalsIgnoreCase("Bonus_Base_Attack_AoE_Damage") && !keySection.equalsIgnoreCase("Bonus_Attack_AoE_Damage")) {
                                                                              if (keySection.equalsIgnoreCase("Bonus_Scale_Attack_AoE_Damage")) {
                                                                                 scaleAttackAoEDamage = section.getDouble(keySection);
                                                                              } else if (!keySection.equalsIgnoreCase("Bonus_Base_PvP_Defense") && !keySection.equalsIgnoreCase("Bonus_PvP_Defense")) {
                                                                                 if (keySection.equalsIgnoreCase("Bonus_Scale_PvP_Defense")) {
                                                                                    scalePvPDefense = section.getDouble(keySection);
                                                                                 } else if (!keySection.equalsIgnoreCase("Bonus_Base_PvE_Defense") && !keySection.equalsIgnoreCase("Bonus_PvE_Defense")) {
                                                                                    if (keySection.equalsIgnoreCase("Bonus_Scale_PvE_Defense")) {
                                                                                       scalePvEDefense = section.getDouble(keySection);
                                                                                    } else if (!keySection.equalsIgnoreCase("Bonus_Base_Critical_Chance") && !keySection.equalsIgnoreCase("Bonus_Critical_Chance")) {
                                                                                       if (keySection.equalsIgnoreCase("Bonus_Scale_Critical_Chance")) {
                                                                                          scaleCriticalChance = section.getDouble(keySection);
                                                                                       } else if (!keySection.equalsIgnoreCase("Bonus_Base_Critical_Damage") && !keySection.equalsIgnoreCase("Bonus_Critical_Damage")) {
                                                                                          if (keySection.equalsIgnoreCase("Bonus_Scale_Critical_Damage")) {
                                                                                             scaleCriticalDamage = section.getDouble(keySection);
                                                                                          } else if (!keySection.equalsIgnoreCase("Bonus_Base_Block_Amount") && !keySection.equalsIgnoreCase("Bonus_Block_Amount")) {
                                                                                             if (keySection.equalsIgnoreCase("Bonus_Scale_Block_Amount")) {
                                                                                                scaleBlockAmount = section.getDouble(keySection);
                                                                                             } else if (!keySection.equalsIgnoreCase("Bonus_Base_Block_Rate") && !keySection.equalsIgnoreCase("Bonus_Block_Rate")) {
                                                                                                if (keySection.equalsIgnoreCase("Bonus_Scale_Block_Rate")) {
                                                                                                   scaleBlockRate = section.getDouble(keySection);
                                                                                                } else if (!keySection.equalsIgnoreCase("Bonus_Base_Hit_Rate") && !keySection.equalsIgnoreCase("Bonus_Hit_Rate")) {
                                                                                                   if (keySection.equalsIgnoreCase("Bonus_Scale_Hit_Rate")) {
                                                                                                      scaleHitRate = section.getDouble(keySection);
                                                                                                   } else if (!keySection.equalsIgnoreCase("Bonus_Base_Dodge_Rate") && !keySection.equalsIgnoreCase("Bonus_Dodge_Rate")) {
                                                                                                      if (keySection.equalsIgnoreCase("Bonus_Scale_Dodge_Rate")) {
                                                                                                         scaleDodgeRate = section.getDouble(keySection);
                                                                                                      } else if (keySection.equalsIgnoreCase("Effect") || keySection.equalsIgnoreCase("Effects")) {
                                                                                                         ConfigurationSection effectSection = section.getConfigurationSection(keySection);
                                                                                                         Iterator var172 = effectSection.getKeys(false).iterator();

                                                                                                         while(true) {
                                                                                                            while(true) {
                                                                                                               while(true) {
                                                                                                                  if (!var172.hasNext()) {
                                                                                                                     continue label568;
                                                                                                                  }

                                                                                                                  effect = (String)var172.next();
                                                                                                                  if (!effect.equalsIgnoreCase("Bonus_Base_Additional_Damage") && !effect.equalsIgnoreCase("Bonus_Additional_Damage")) {
                                                                                                                     if (effect.equalsIgnoreCase("Bonus_Scale_Additional_Damage")) {
                                                                                                                        scaleAdditionalDamage = effectSection.getDouble(effect);
                                                                                                                     } else if (!effect.equalsIgnoreCase("Bonus_Base_Percent_Damage") && !effect.equalsIgnoreCase("Bonus_Percent_Damage")) {
                                                                                                                        if (effect.equalsIgnoreCase("Bonus_Scale_Percent_Damage")) {
                                                                                                                           scalePercentDamage = effectSection.getDouble(effect);
                                                                                                                        } else if (!effect.equalsIgnoreCase("Bonus_Base_Penetration") && !effect.equalsIgnoreCase("Bonus_Penetration")) {
                                                                                                                           if (effect.equalsIgnoreCase("Bonus_Scale_Penetration")) {
                                                                                                                              scalePenetration = effectSection.getDouble(effect);
                                                                                                                           } else if (!effect.equalsIgnoreCase("Bonus_Base_PvP_Damage") && !effect.equalsIgnoreCase("Bonus_PvP_Damage")) {
                                                                                                                              if (effect.equalsIgnoreCase("Bonus_Scale_PvP_Damage")) {
                                                                                                                                 scalePvPDamage = effectSection.getDouble(effect);
                                                                                                                              } else if (!effect.equalsIgnoreCase("Bonus_Base_PvE_Damage") && !effect.equalsIgnoreCase("Bonus_PvE_Damage")) {
                                                                                                                                 if (effect.equalsIgnoreCase("Bonus_Scale_PvE_Damage")) {
                                                                                                                                    scalePvEDamage = effectSection.getDouble(effect);
                                                                                                                                 } else if (!effect.equalsIgnoreCase("Bonus_Base_Additional_Defense") && !effect.equalsIgnoreCase("Bonus_Additional_Defense")) {
                                                                                                                                    if (effect.equalsIgnoreCase("Bonus_Scale_Additional_Defense")) {
                                                                                                                                       scaleAdditionalDefense = effectSection.getDouble(effect);
                                                                                                                                    } else if (!effect.equalsIgnoreCase("Bonus_Base_Percent_Defense") && !effect.equalsIgnoreCase("Bonus_Percent_Defense")) {
                                                                                                                                       if (effect.equalsIgnoreCase("Bonus_Scale_Percent_Defense")) {
                                                                                                                                          scalePercentDefense = effectSection.getDouble(effect);
                                                                                                                                       } else if (!effect.equalsIgnoreCase("Bonus_Base_Max_Health") && !effect.equalsIgnoreCase("Bonus_Max_Health")) {
                                                                                                                                          if (effect.equalsIgnoreCase("Bonus_Scale_Max_Health")) {
                                                                                                                                             scaleMaxHealth = effectSection.getDouble(effect);
                                                                                                                                          } else if (!effect.equalsIgnoreCase("Bonus_Base_Health_Regen") && !effect.equalsIgnoreCase("Bonus_Health_Regen")) {
                                                                                                                                             if (effect.equalsIgnoreCase("Bonus_Scale_Health_Regen")) {
                                                                                                                                                scaleHealthRegen = effectSection.getDouble(effect);
                                                                                                                                             } else if (!effect.equalsIgnoreCase("Bonus_Base_Stamina_Max") && !effect.equalsIgnoreCase("Bonus_Stamina_Max")) {
                                                                                                                                                if (effect.equalsIgnoreCase("Bonus_Scale_Stamina_Max")) {
                                                                                                                                                   scaleStaminaMax = effectSection.getDouble(effect);
                                                                                                                                                } else if (!effect.equalsIgnoreCase("Bonus_Base_Stamina_Regen") && !effect.equalsIgnoreCase("Bonus_Stamina_Regen")) {
                                                                                                                                                   if (effect.equalsIgnoreCase("Bonus_Scale_Stamina_Regen")) {
                                                                                                                                                      scaleStaminaRegen = effectSection.getDouble(effect);
                                                                                                                                                   } else if (!effect.equalsIgnoreCase("Bonus_Base_Attack_AoE_Radius") && !effect.equalsIgnoreCase("Bonus_Attack_AoE_Radius")) {
                                                                                                                                                      if (effect.equalsIgnoreCase("Bonus_Scale_Attack_AoE_Radius")) {
                                                                                                                                                         scaleAttackAoERadius = effectSection.getDouble(effect);
                                                                                                                                                      } else if (!effect.equalsIgnoreCase("Bonus_Base_Attack_AoE_Damage") && !effect.equalsIgnoreCase("Bonus_Attack_AoE_Damage")) {
                                                                                                                                                         if (effect.equalsIgnoreCase("Bonus_Scale_Attack_AoE_Damage")) {
                                                                                                                                                            scaleAttackAoEDamage = effectSection.getDouble(effect);
                                                                                                                                                         } else if (!effect.equalsIgnoreCase("Bonus_Base_PvP_Defense") && !effect.equalsIgnoreCase("Bonus_PvP_Defense")) {
                                                                                                                                                            if (effect.equalsIgnoreCase("Bonus_Scale_PvP_Defense")) {
                                                                                                                                                               scalePvPDefense = effectSection.getDouble(effect);
                                                                                                                                                            } else if (!effect.equalsIgnoreCase("Bonus_Base_PvE_Defense") && !effect.equalsIgnoreCase("Bonus_PvE_Defense")) {
                                                                                                                                                               if (effect.equalsIgnoreCase("Bonus_Scale_PvE_Defense")) {
                                                                                                                                                                  scalePvEDefense = effectSection.getDouble(effect);
                                                                                                                                                               } else if (!effect.equalsIgnoreCase("Bonus_Base_Critical_Chance") && !effect.equalsIgnoreCase("Bonus_Critical_Chance")) {
                                                                                                                                                                  if (effect.equalsIgnoreCase("Bonus_Scale_Critical_Chance")) {
                                                                                                                                                                     scaleCriticalChance = effectSection.getDouble(effect);
                                                                                                                                                                  } else if (!effect.equalsIgnoreCase("Bonus_Base_Critical_Damage") && !effect.equalsIgnoreCase("Bonus_Critical_Damage")) {
                                                                                                                                                                     if (effect.equalsIgnoreCase("Bonus_Scale_Critical_Damage")) {
                                                                                                                                                                        scaleCriticalDamage = effectSection.getDouble(effect);
                                                                                                                                                                     } else if (!effect.equalsIgnoreCase("Bonus_Base_Block_Amount") && !effect.equalsIgnoreCase("Bonus_Block_Amount")) {
                                                                                                                                                                        if (effect.equalsIgnoreCase("Bonus_Scale_Block_Amount")) {
                                                                                                                                                                           scaleBlockAmount = effectSection.getDouble(effect);
                                                                                                                                                                        } else if (!effect.equalsIgnoreCase("Bonus_Base_Block_Rate") && !effect.equalsIgnoreCase("Bonus_Block_Rate")) {
                                                                                                                                                                           if (effect.equalsIgnoreCase("Bonus_Scale_Block_Rate")) {
                                                                                                                                                                              scaleBlockRate = effectSection.getDouble(effect);
                                                                                                                                                                           } else if (!effect.equalsIgnoreCase("Bonus_Base_Hit_Rate") && !effect.equalsIgnoreCase("Bonus_Hit_Rate")) {
                                                                                                                                                                              if (effect.equalsIgnoreCase("Bonus_Scale_Hit_Rate")) {
                                                                                                                                                                                 scaleHitRate = effectSection.getDouble(effect);
                                                                                                                                                                              } else if (!effect.equalsIgnoreCase("Bonus_Base_Dodge_Rate") && !effect.equalsIgnoreCase("Bonus_Dodge_Rate")) {
                                                                                                                                                                                 if (effect.equalsIgnoreCase("Bonus_Scale_Dodge_Rate")) {
                                                                                                                                                                                    scaleDodgeRate = effectSection.getDouble(effect);
                                                                                                                                                                                 }
                                                                                                                                                                              } else {
                                                                                                                                                                                 baseDodgeRate = effectSection.getDouble(effect);
                                                                                                                                                                              }
                                                                                                                                                                           } else {
                                                                                                                                                                              baseHitRate = effectSection.getDouble(effect);
                                                                                                                                                                           }
                                                                                                                                                                        } else {
                                                                                                                                                                           baseBlockRate = effectSection.getDouble(effect);
                                                                                                                                                                        }
                                                                                                                                                                     } else {
                                                                                                                                                                        baseBlockAmount = effectSection.getDouble(effect);
                                                                                                                                                                     }
                                                                                                                                                                  } else {
                                                                                                                                                                     baseCriticalDamage = effectSection.getDouble(effect);
                                                                                                                                                                  }
                                                                                                                                                               } else {
                                                                                                                                                                  baseCriticalChance = effectSection.getDouble(effect);
                                                                                                                                                               }
                                                                                                                                                            } else {
                                                                                                                                                               basePvEDefense = effectSection.getDouble(effect);
                                                                                                                                                            }
                                                                                                                                                         } else {
                                                                                                                                                            basePvPDefense = effectSection.getDouble(effect);
                                                                                                                                                         }
                                                                                                                                                      } else {
                                                                                                                                                         baseAttackAoEDamage = effectSection.getDouble(effect);
                                                                                                                                                      }
                                                                                                                                                   } else {
                                                                                                                                                      baseAttackAoERadius = effectSection.getDouble(effect);
                                                                                                                                                   }
                                                                                                                                                } else {
                                                                                                                                                   baseStaminaRegen = effectSection.getDouble(effect);
                                                                                                                                                }
                                                                                                                                             } else {
                                                                                                                                                baseStaminaMax = effectSection.getDouble(effect);
                                                                                                                                             }
                                                                                                                                          } else {
                                                                                                                                             baseHealthRegen = effectSection.getDouble(effect);
                                                                                                                                          }
                                                                                                                                       } else {
                                                                                                                                          baseMaxHealth = effectSection.getDouble(effect);
                                                                                                                                       }
                                                                                                                                    } else {
                                                                                                                                       basePercentDefense = effectSection.getDouble(effect);
                                                                                                                                    }
                                                                                                                                 } else {
                                                                                                                                    baseAdditionalDefense = effectSection.getDouble(effect);
                                                                                                                                 }
                                                                                                                              } else {
                                                                                                                                 basePvEDamage = effectSection.getDouble(effect);
                                                                                                                              }
                                                                                                                           } else {
                                                                                                                              basePvPDamage = effectSection.getDouble(effect);
                                                                                                                           }
                                                                                                                        } else {
                                                                                                                           basePenetration = effectSection.getDouble(effect);
                                                                                                                        }
                                                                                                                     } else {
                                                                                                                        basePercentDamage = effectSection.getDouble(effect);
                                                                                                                     }
                                                                                                                  } else {
                                                                                                                     baseAdditionalDamage = effectSection.getDouble(effect);
                                                                                                                  }
                                                                                                               }
                                                                                                            }
                                                                                                         }
                                                                                                      }
                                                                                                   } else {
                                                                                                      baseDodgeRate = section.getDouble(keySection);
                                                                                                   }
                                                                                                } else {
                                                                                                   baseHitRate = section.getDouble(keySection);
                                                                                                }
                                                                                             } else {
                                                                                                baseBlockRate = section.getDouble(keySection);
                                                                                             }
                                                                                          } else {
                                                                                             baseBlockAmount = section.getDouble(keySection);
                                                                                          }
                                                                                       } else {
                                                                                          baseCriticalDamage = section.getDouble(keySection);
                                                                                       }
                                                                                    } else {
                                                                                       baseCriticalChance = section.getDouble(keySection);
                                                                                    }
                                                                                 } else {
                                                                                    basePvEDefense = section.getDouble(keySection);
                                                                                 }
                                                                              } else {
                                                                                 basePvPDefense = section.getDouble(keySection);
                                                                              }
                                                                           } else {
                                                                              baseAttackAoEDamage = section.getDouble(keySection);
                                                                           }
                                                                        } else {
                                                                           baseAttackAoERadius = section.getDouble(keySection);
                                                                        }
                                                                     } else {
                                                                        baseStaminaRegen = section.getDouble(keySection);
                                                                     }
                                                                  } else {
                                                                     baseStaminaMax = section.getDouble(keySection);
                                                                  }
                                                               } else {
                                                                  baseHealthRegen = section.getDouble(keySection);
                                                               }
                                                            } else {
                                                               baseMaxHealth = section.getDouble(keySection);
                                                            }
                                                         } else {
                                                            basePercentDefense = section.getDouble(keySection);
                                                         }
                                                      } else {
                                                         baseAdditionalDefense = section.getDouble(keySection);
                                                      }
                                                   } else {
                                                      basePvEDamage = section.getDouble(keySection);
                                                   }
                                                } else {
                                                   basePvPDamage = section.getDouble(keySection);
                                                }
                                             } else {
                                                basePenetration = section.getDouble(keySection);
                                             }
                                          } else {
                                             basePercentDamage = section.getDouble(keySection);
                                          }
                                       } else {
                                          baseAdditionalDamage = section.getDouble(keySection);
                                       }
                                    } else {
                                       Iterator var112 = section.getStringList(keySection).iterator();

                                       while(var112.hasNext()) {
                                          lineEnchant = (String)var112.next();
                                          String[] parts = lineEnchant.replaceAll(" ", "").split(":");
                                          int grade = 1;
                                          if (parts.length > 0) {
                                             String enchantmentKey = parts[0].toUpperCase();
                                             Enchantment enchantment = EnchantmentUtil.getEnchantment(enchantmentKey);
                                             if (enchantment != null) {
                                                if (parts.length > 1) {
                                                   String textGrade = parts[1];
                                                   if (MathUtil.isNumber(textGrade)) {
                                                      grade = MathUtil.parseInteger(textGrade);
                                                      grade = MathUtil.limitInteger(grade, 1, grade);
                                                   }
                                                }

                                                enchantments.put(enchantment, grade);
                                             }
                                          }
                                       }
                                    }
                                 } else {
                                    flags.addAll(section.getStringList(keySection));
                                 }
                              } else {
                                 lores.addAll(section.getStringList(keySection));
                              }
                           }
                        } else {
                           display = section.getString(keySection);
                        }
                     }
                  }
               } while(material == null);
            } while(keyLore == null);

            materialEnum = MaterialEnum.getMaterialEnum(material, data);
         } while(materialEnum == null);

         HashMap<Integer, SocketGems> mapSocket = new HashMap();
         HashMap<String, String> map = new HashMap();
         switch($SWITCH_TABLE$core$praya$agarthalib$enums$main$SlotType()[typeItem.ordinal()]) {
         case 1:
            effect = mainConfig.getSocketTypeItemWeapon();
            break;
         case 2:
            effect = mainConfig.getSocketTypeItemArmor();
            break;
         case 3:
            effect = mainConfig.getSocketTypeItemUniversal();
            break;
         default:
            effect = "Unknown";
         }

         for(int grade = 1; grade <= maxGrade; ++grade) {
            double successRate = MathUtil.limitDouble(baseSuccessRate + (double)(grade - 1) * scaleSuccessRate, 0.0D, 100.0D);
            double failureRate = 100.0D - successRate;
            double additionalDamage = baseAdditionalDamage + (double)(grade - 1) * scaleAdditionalDamage;
            double percentDamage = basePercentDamage + (double)(grade - 1) * scalePercentDamage;
            double penetration = basePenetration + (double)(grade - 1) * scalePenetration;
            double pvpDamage = basePvPDamage + (double)(grade - 1) * scalePvPDamage;
            double pveDamage = basePvEDamage + (double)(grade - 1) * scalePvEDamage;
            double additionalDefense = baseAdditionalDefense + (double)(grade - 1) * scaleAdditionalDefense;
            double percentDefense = basePercentDefense + (double)(grade - 1) * scalePercentDefense;
            double maxHealth = baseMaxHealth + (double)(grade - 1) * scaleMaxHealth;
            double healthRegen = baseHealthRegen + (double)(grade - 1) * scaleHealthRegen;
            double staminaMax = baseStaminaMax + (double)(grade - 1) * scaleStaminaMax;
            double staminaRegen = baseStaminaRegen + (double)(grade - 1) * scaleStaminaRegen;
            double attackAoERadius = baseAttackAoERadius + (double)(grade - 1) * scaleAttackAoERadius;
            double attackAoEDamage = baseAttackAoEDamage + (double)(grade - 1) * scaleAttackAoEDamage;
            double pvpDefense = basePvPDefense + (double)(grade - 1) * scalePvPDefense;
            double pveDefense = basePvEDefense + (double)(grade - 1) * scalePvEDefense;
            double criticalChance = baseCriticalChance + (double)(grade - 1) * scaleCriticalChance;
            double criticalDamage = baseCriticalDamage + (double)(grade - 1) * scaleCriticalDamage;
            double blockAmount = baseBlockAmount + (double)(grade - 1) * scaleBlockAmount;
            double blockRate = baseBlockRate + (double)(grade - 1) * scaleBlockRate;
            double hitRate = baseHitRate + (double)(grade - 1) * scaleHitRate;
            double dodgeRate = baseDodgeRate + (double)(grade - 1) * scaleDodgeRate;
            map.clear();
            map.put("gems", key);
            map.put("grade", RomanNumber.getRomanNumber(grade));
            map.put("type_item", effect);
            map.put("success_rate", String.valueOf(MathUtil.roundNumber(successRate)));
            map.put("failure_rate", String.valueOf(MathUtil.roundNumber(failureRate)));
            map.put("additional_damage", String.valueOf(MathUtil.roundNumber(additionalDamage)));
            map.put("percent_damage", String.valueOf(MathUtil.roundNumber(percentDamage)));
            map.put("penetration", String.valueOf(MathUtil.roundNumber(penetration)));
            map.put("pvp_damage", String.valueOf(MathUtil.roundNumber(pvpDamage)));
            map.put("pve_damage", String.valueOf(MathUtil.roundNumber(pveDamage)));
            map.put("additional_defense", String.valueOf(MathUtil.roundNumber(additionalDefense)));
            map.put("percent_defense", String.valueOf(MathUtil.roundNumber(percentDefense)));
            map.put("max_health", String.valueOf(MathUtil.roundNumber(maxHealth)));
            map.put("health_max", String.valueOf(MathUtil.roundNumber(maxHealth)));
            map.put("health_regen", String.valueOf(MathUtil.roundNumber(healthRegen)));
            map.put("max_stamina", String.valueOf(MathUtil.roundNumber(staminaMax)));
            map.put("stamina_max", String.valueOf(MathUtil.roundNumber(staminaMax)));
            map.put("stamina_regen", String.valueOf(MathUtil.roundNumber(staminaRegen)));
            map.put("attack_aoe_radius", String.valueOf(MathUtil.roundNumber(attackAoERadius)));
            map.put("attack_aoe_damage", String.valueOf(MathUtil.roundNumber(attackAoEDamage)));
            map.put("pvp_defense", String.valueOf(MathUtil.roundNumber(pvpDefense)));
            map.put("pve_defense", String.valueOf(MathUtil.roundNumber(pveDefense)));
            map.put("critical_chance", String.valueOf(MathUtil.roundNumber(criticalChance)));
            map.put("critical_damage", String.valueOf(MathUtil.roundNumber(criticalDamage)));
            map.put("block_amount", String.valueOf(MathUtil.roundNumber(blockAmount)));
            map.put("block_rate", String.valueOf(MathUtil.roundNumber(blockRate)));
            map.put("hit_rate", String.valueOf(MathUtil.roundNumber(hitRate)));
            map.put("dodge_rate", String.valueOf(MathUtil.roundNumber(dodgeRate)));
            String itemDisplay = TextUtil.placeholder(map, display);
            String itemKeyLore = TextUtil.placeholder(map, keyLore);
            ItemStack item = EquipmentUtil.createItem(materialEnum, itemDisplay, 1);
            List<String> itemLores = TextUtil.placeholder(map, lores);
            if (shiny) {
               EquipmentUtil.shiny(item);
            }

            if (unbreakable) {
               Bridge.getBridgeTagsNBT().setUnbreakable(item, true);
            }

            if (!itemLores.isEmpty()) {
               EquipmentUtil.setLores(item, itemLores);
            }

            Iterator var165;
            if (!flags.isEmpty()) {
               var165 = flags.iterator();

               while(var165.hasNext()) {
                  String flag = (String)var165.next();
                  EquipmentUtil.addFlag(item, new String[]{flag});
               }
            }

            if (!enchantments.isEmpty()) {
               var165 = enchantments.keySet().iterator();

               while(var165.hasNext()) {
                  Enchantment enchantment = (Enchantment)var165.next();
                  int enchantmentGrade = (Integer)enchantments.get(enchantment);
                  EquipmentUtil.addEnchantment(item, enchantment, enchantmentGrade);
               }
            }

            EquipmentUtil.hookPlaceholderAPI(item);
            EquipmentUtil.colorful(item);
            if (EquipmentUtil.isSolid(item)) {
               SocketGemsProperties socketProperties = new SocketGemsProperties(additionalDamage, percentDamage, penetration, pvpDamage, pveDamage, additionalDefense, percentDefense, maxHealth, healthRegen, staminaMax, staminaRegen, attackAoERadius, attackAoEDamage, pvpDefense, pveDefense, criticalChance, criticalDamage, blockAmount, blockRate, hitRate, dodgeRate);
               SocketGems build = new SocketGems(item, itemKeyLore, key, grade, successRate, typeItem, socketProperties);
               mapSocket.put(grade, build);
            }
         }

         SocketGemsTree socketTree = new SocketGemsTree(key, maxGrade, typeItem, mapSocket);
         this.mapSocketTree.put(key, socketTree);
      }
   }

   private final void moveOldFile() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathSource = "socket.yml";
      String pathTarget = dataManager.getPath("Path_File_Socket");
      File fileSource = FileUtil.getFile(this.plugin, "socket.yml");
      File fileTarget = FileUtil.getFile(this.plugin, pathTarget);
      if (fileSource.exists()) {
         FileUtil.moveFileSilent(fileSource, fileTarget);
      }

   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$core$praya$agarthalib$enums$main$SlotType() {
      int[] var10000 = $SWITCH_TABLE$core$praya$agarthalib$enums$main$SlotType;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[SlotType.values().length];

         try {
            var0[SlotType.ARMOR.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[SlotType.UNIVERSAL.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[SlotType.WEAPON.ordinal()] = 1;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$core$praya$agarthalib$enums$main$SlotType = var0;
         return var0;
      }
   }
}
