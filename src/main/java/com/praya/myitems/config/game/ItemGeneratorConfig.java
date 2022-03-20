package com.praya.myitems.config.game;

import api.praya.myitems.builder.item.ItemGenerator;
import api.praya.myitems.builder.item.ItemGeneratorTier;
import api.praya.myitems.builder.item.ItemGeneratorType;
import api.praya.myitems.builder.item.ItemTier;
import api.praya.myitems.builder.item.ItemType;
import com.praya.agarthalib.utility.FileUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemTierManager;
import com.praya.myitems.manager.game.ItemTypeManager;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ItemGeneratorConfig extends HandlerConfig {
   private final HashMap<String, ItemGenerator> mapItemGenerator = new HashMap();

   public ItemGeneratorConfig(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getItemGeneratorIDs() {
      return this.mapItemGenerator.keySet();
   }

   public final Collection<ItemGenerator> getItemGenerators() {
      return this.mapItemGenerator.values();
   }

   public final ItemGenerator getItemGenerator(String nameid) {
      Iterator var3 = this.getItemGeneratorIDs().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(nameid)) {
            return (ItemGenerator)this.mapItemGenerator.get(key);
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
      this.mapItemGenerator.clear();
   }

   private final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      GameManager gameManager = this.plugin.getGameManager();
      DataManager dataManager = pluginManager.getDataManager();
      ItemTypeManager itemTypeManager = gameManager.getItemTypeManager();
      ItemTierManager itemTierManager = gameManager.getItemTierManager();
      String pathDefault = dataManager.getPath("Path_File_Item_Generator");
      String pathFolder = dataManager.getPath("Path_Folder_Item_Generator");
      File fileDefault = FileUtil.getFile(this.plugin, pathDefault);
      File fileFolder = FileUtil.getFile(this.plugin, pathFolder);
      if (!fileDefault.exists()) {
         FileUtil.saveResource(this.plugin, pathDefault);
      }

      File[] var13;
      int var12 = (var13 = fileFolder.listFiles()).length;

      for(int var11 = 0; var11 < var12; ++var11) {
         File file = var13[var11];
         FileConfiguration config = FileUtil.getFileConfiguration(file);
         Iterator var16 = config.getKeys(false).iterator();

         while(var16.hasNext()) {
            String key = (String)var16.next();
            ConfigurationSection dataSection = config.getConfigurationSection(key);
            List<String> lores = new ArrayList();
            List<String> flags = new ArrayList();
            HashMap<ItemType, ItemGeneratorType> mapType = new HashMap();
            HashMap<ItemTier, ItemGeneratorTier> mapTier = new HashMap();
            String displayName = null;
            boolean unbreakable = false;
            Iterator var25 = dataSection.getKeys(false).iterator();

            while(true) {
               label140:
               while(var25.hasNext()) {
                  String data = (String)var25.next();
                  if (data.equalsIgnoreCase("Display_Name")) {
                     displayName = TextUtil.colorful(dataSection.getString(data));
                  } else if (data.equalsIgnoreCase("Unbreakable")) {
                     unbreakable = dataSection.getBoolean(data);
                  } else if (!data.equalsIgnoreCase("Flags") && !data.equalsIgnoreCase("ItemFlags")) {
                     if (data.equalsIgnoreCase("Lores")) {
                        lores.addAll(dataSection.getStringList(data));
                     } else {
                        ConfigurationSection tierSection;
                        String tier;
                        Iterator var28;
                        ConfigurationSection tierPropertiesSection;
                        ArrayList additionalLores;
                        if (data.equalsIgnoreCase("Type")) {
                           tierSection = dataSection.getConfigurationSection(data);
                           var28 = tierSection.getKeys(false).iterator();

                           while(true) {
                              ItemType itemType;
                              do {
                                 if (!var28.hasNext()) {
                                    continue label140;
                                 }

                                 tier = (String)var28.next();
                                 itemType = itemTypeManager.getItemType(tier);
                              } while(itemType == null);

                              tierPropertiesSection = tierSection.getConfigurationSection(tier);
                              additionalLores = new ArrayList();
                              List<String> names = new ArrayList();
                              int possibility = 1;
                              Iterator var45 = tierPropertiesSection.getKeys(false).iterator();

                              while(true) {
                                 while(var45.hasNext()) {
                                    String typeProperties = (String)var45.next();
                                    if (typeProperties.equalsIgnoreCase("Possibility")) {
                                       possibility = tierPropertiesSection.getInt(typeProperties);
                                    } else {
                                       Iterator var37;
                                       String name;
                                       if (typeProperties.equalsIgnoreCase("Description")) {
                                          var37 = tierPropertiesSection.getStringList(typeProperties).iterator();

                                          while(var37.hasNext()) {
                                             name = (String)var37.next();
                                             additionalLores.add(name);
                                          }
                                       } else if (typeProperties.equalsIgnoreCase("Name") || typeProperties.equalsIgnoreCase("Names")) {
                                          var37 = tierPropertiesSection.getStringList(typeProperties).iterator();

                                          while(var37.hasNext()) {
                                             name = (String)var37.next();
                                             names.add(name);
                                          }
                                       }
                                    }
                                 }

                                 ItemGeneratorType itemTypeProperties = new ItemGeneratorType(possibility, additionalLores, names);
                                 mapType.put(itemType, itemTypeProperties);
                                 break;
                              }
                           }
                        } else if (data.equalsIgnoreCase("Tier")) {
                           tierSection = dataSection.getConfigurationSection(data);
                           var28 = tierSection.getKeys(false).iterator();

                           while(true) {
                              ItemTier itemTier;
                              do {
                                 if (!var28.hasNext()) {
                                    continue label140;
                                 }

                                 tier = (String)var28.next();
                                 itemTier = itemTierManager.getItemTier(tier);
                              } while(itemTier == null);

                              tierPropertiesSection = tierSection.getConfigurationSection(tier);
                              additionalLores = new ArrayList();
                              int possibility = 1;
                              Iterator var34 = tierPropertiesSection.getKeys(false).iterator();

                              while(true) {
                                 while(var34.hasNext()) {
                                    String tierProperties = (String)var34.next();
                                    if (tierProperties.equalsIgnoreCase("Possibility")) {
                                       possibility = tierPropertiesSection.getInt(tierProperties);
                                    } else if (tierProperties.equalsIgnoreCase("Additional_Lores")) {
                                       Iterator var36 = tierPropertiesSection.getStringList(tierProperties).iterator();

                                       while(var36.hasNext()) {
                                          String additionalLore = (String)var36.next();
                                          additionalLores.add(additionalLore);
                                       }
                                    }
                                 }

                                 ItemGeneratorTier itemTierProperties = new ItemGeneratorTier(possibility, additionalLores);
                                 mapTier.put(itemTier, itemTierProperties);
                                 break;
                              }
                           }
                        }
                     }
                  } else {
                     flags.addAll(dataSection.getStringList(data));
                  }
               }

               if (displayName != null) {
                  ItemGenerator itemGenerator = new ItemGenerator(key, displayName, unbreakable, lores, flags, mapType, mapTier);
                  this.mapItemGenerator.put(key, itemGenerator);
               }
               break;
            }
         }
      }

   }

   private final void moveOldFile() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathSource_1 = "item_generator.yml";
      String pathSource_2 = "Configuration/item_generator.yml";
      String pathTarget = dataManager.getPath("Path_File_Item_Generator");
      File fileSource_1 = FileUtil.getFile(this.plugin, "item_generator.yml");
      File fileSource_2 = FileUtil.getFile(this.plugin, "Configuration/item_generator.yml");
      File fileTarget = FileUtil.getFile(this.plugin, pathTarget);
      if (fileSource_1.exists()) {
         FileUtil.moveFileSilent(fileSource_1, fileTarget);
      } else if (fileSource_2.exists()) {
         FileUtil.moveFileSilent(fileSource_2, fileTarget);
      }

   }
}
