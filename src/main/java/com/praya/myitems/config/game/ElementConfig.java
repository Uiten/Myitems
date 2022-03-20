package com.praya.myitems.config.game;

import api.praya.myitems.builder.element.Element;
import api.praya.myitems.builder.element.ElementBoost;
import api.praya.myitems.builder.element.ElementPotion;
import api.praya.myitems.builder.potion.PotionProperties;
import com.praya.agarthalib.utility.FileUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.PotionUtil;
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
import org.bukkit.potion.PotionEffectType;

public class ElementConfig extends HandlerConfig {
   private final HashMap<String, Element> mapElement = new HashMap();

   public ElementConfig(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getElements() {
      return this.mapElement.keySet();
   }

   public final Collection<Element> getElementBuilds() {
      return this.mapElement.values();
   }

   public final Element getElementBuild(String element) {
      Iterator var3 = this.getElements().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(element)) {
            return (Element)this.mapElement.get(key);
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
      this.mapElement.clear();
   }

   private final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String path = dataManager.getPath("Path_File_Element");
      File file = FileUtil.getFile(this.plugin, path);
      if (!file.exists()) {
         FileUtil.saveResource(this.plugin, path);
      }

      FileConfiguration config = FileUtil.getFileConfiguration(file);
      Iterator var7 = config.getKeys(false).iterator();

      while(var7.hasNext()) {
         String key = (String)var7.next();
         ConfigurationSection section = config.getConfigurationSection(key);
         HashMap<String, Double> resistance = new HashMap();
         HashMap<PotionEffectType, PotionProperties> potionAttacker = new HashMap();
         HashMap<PotionEffectType, PotionProperties> potionVictims = new HashMap();
         String keyLore = null;
         double scaleAdditionalDamage = 0.0D;
         double scalePercentDamage = 0.0D;
         Iterator var18 = section.getKeys(false).iterator();

         while(true) {
            label104:
            while(var18.hasNext()) {
               String keySection = (String)var18.next();
               if (keySection.equalsIgnoreCase("Keylore")) {
                  keyLore = TextUtil.colorful(section.getString(keySection));
               } else if (!keySection.equalsIgnoreCase("Scale_Bonus_Additional_Damage") && !keySection.equalsIgnoreCase("Scale_Base_Additional_Damage")) {
                  if (!keySection.equalsIgnoreCase("Scale_Bonus_Percent_Damage") && !keySection.equalsIgnoreCase("Scale_Base_Percent_Damage")) {
                     ConfigurationSection potionSection;
                     String keyPotion;
                     Iterator var21;
                     if (keySection.equalsIgnoreCase("Resistance")) {
                        potionSection = section.getConfigurationSection(keySection);
                        var21 = potionSection.getKeys(false).iterator();

                        while(var21.hasNext()) {
                           keyPotion = (String)var21.next();
                           resistance.put(keyPotion, potionSection.getDouble(keyPotion));
                        }
                     } else if (keySection.equalsIgnoreCase("Potion_To_Attacker") || keySection.equalsIgnoreCase("Potion_To_Attackers") || keySection.equalsIgnoreCase("Potion_To_Victim") || keySection.equalsIgnoreCase("Potion_To_Victims")) {
                        potionSection = section.getConfigurationSection(keySection);
                        var21 = potionSection.getKeys(false).iterator();

                        while(true) {
                           PotionEffectType potion;
                           PotionProperties potionAttributes;
                           do {
                              while(true) {
                                 do {
                                    if (!var21.hasNext()) {
                                       continue label104;
                                    }

                                    keyPotion = (String)var21.next();
                                    potion = PotionUtil.getPotionEffectType(keyPotion);
                                 } while(potion == null);

                                 ConfigurationSection attributePotionSection = potionSection.getConfigurationSection(keyPotion);
                                 int potionGrade = 1;
                                 double potionScaleChance = 0.0D;
                                 double potionScaleDuration = 1.0D;
                                 Iterator var30 = attributePotionSection.getKeys(false).iterator();

                                 while(var30.hasNext()) {
                                    String keyAttribute = (String)var30.next();
                                    if (keyAttribute.equalsIgnoreCase("Grade")) {
                                       potionGrade = attributePotionSection.getInt(keyAttribute);
                                    } else if (keyAttribute.equalsIgnoreCase("Scale_Chance")) {
                                       potionScaleChance = attributePotionSection.getDouble(keyAttribute);
                                    } else if (keyAttribute.equalsIgnoreCase("Scale_Duration")) {
                                       potionScaleDuration = attributePotionSection.getDouble(keyAttribute);
                                    }
                                 }

                                 potionGrade = MathUtil.limitInteger(potionGrade, 1, 10);
                                 potionScaleChance = MathUtil.limitDouble(potionScaleChance, 0.01D, 100.0D);
                                 potionScaleDuration = MathUtil.limitDouble(potionScaleDuration, 0.1D, 100.0D);
                                 potionAttributes = new PotionProperties(potionGrade, potionScaleChance, potionScaleDuration);
                                 if (!keySection.equalsIgnoreCase("Potion_To_Attacker") && !keySection.equalsIgnoreCase("Potion_To_Attackers")) {
                                    break;
                                 }

                                 potionAttacker.put(potion, potionAttributes);
                              }
                           } while(!keySection.equalsIgnoreCase("Potion_To_Victim") && !keySection.equalsIgnoreCase("Potion_To_Victims"));

                           potionVictims.put(potion, potionAttributes);
                        }
                     }
                  } else {
                     scalePercentDamage = section.getDouble(keySection);
                  }
               } else {
                  scaleAdditionalDamage = section.getDouble(keySection);
               }
            }

            if (keyLore != null) {
               ElementBoost boostBuild = new ElementBoost(scaleAdditionalDamage, scalePercentDamage);
               ElementPotion potionBuild = new ElementPotion(potionAttacker, potionVictims);
               Element build = new Element(keyLore, boostBuild, potionBuild, resistance);
               this.mapElement.put(key, build);
            }
            break;
         }
      }

   }

   private final void moveOldFile() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathSource = "element.yml";
      String pathTarget = dataManager.getPath("Path_File_Element");
      File fileSource = FileUtil.getFile(this.plugin, "element.yml");
      File fileTarget = FileUtil.getFile(this.plugin, pathTarget);
      if (fileSource.exists()) {
         FileUtil.moveFileSilent(fileSource, fileTarget);
      }

   }
}
