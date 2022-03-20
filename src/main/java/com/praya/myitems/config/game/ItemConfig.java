package com.praya.myitems.config.game;

import api.praya.myitems.builder.ability.AbilityItemWeapon;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import api.praya.myitems.builder.socket.SocketGems;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.FileUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerConfig;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import com.praya.myitems.manager.game.PowerCommandManager;
import com.praya.myitems.manager.game.PowerManager;
import com.praya.myitems.manager.game.PowerShootManager;
import com.praya.myitems.manager.game.PowerSpecialManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemConfig extends HandlerConfig {
   private final HashMap<String, ItemStack> mapItem = new HashMap();

   public ItemConfig(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getItemIDs() {
      return this.mapItem.keySet();
   }

   public final ItemStack getItem(String id) {
      Iterator var3 = this.getItemIDs().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(id)) {
            return ((ItemStack)this.mapItem.get(key)).clone();
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
      this.mapItem.clear();
   }

   private final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String path = dataManager.getPath("Path_File_Item");
      File file = FileUtil.getFile(this.plugin, path);
      this.convertOldDatabase();
      if (!file.exists()) {
         FileUtil.saveResource(this.plugin, path);
      }

      FileConfiguration config = FileUtil.getFileConfiguration(file);
      Iterator var7 = config.getKeys(false).iterator();

      while(var7.hasNext()) {
         String key = (String)var7.next();
         ItemStack item = config.getItemStack(key);
         if (item != null) {
            ItemStack gameItem = this.convertToGame(item);
            EquipmentUtil.colorful(gameItem);
            this.mapItem.put(key, gameItem);
         }
      }

   }

   private final ItemStack convertToFile(ItemStack convertItem) {
      GameManager gameManager = this.plugin.getGameManager();
      PowerManager powerManager = gameManager.getPowerManager();
      PowerCommandManager powerCommandManager = powerManager.getPowerCommandManager();
      PowerShootManager powerShootManager = powerManager.getPowerShootManager();
      PowerSpecialManager powerSpecialManager = powerManager.getPowerSpecialManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      ElementManager elementManager = gameManager.getElementManager();
      SocketManager socketManager = gameManager.getSocketManager();
      PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      ItemStack item = new ItemStack(convertItem);
      if (EquipmentUtil.hasDisplayName(item)) {
         EquipmentUtil.setDisplayName(item, EquipmentUtil.getDisplayName(item).replaceAll("�", "&"));
      }

      if (EquipmentUtil.loreCheck(item)) {
         List<String> lores = EquipmentUtil.getLores(item);
         HashMap<String, String> mapPlaceholder = new HashMap();

         for(int i = 0; i < lores.size(); ++i) {
            int line = i + 1;
            String lore = (String)lores.get(i);
            double value;
            double chance;
            String replacement;
            if (statsManager.isLoreStats(lore)) {
               LoreStatsEnum loreStats = statsManager.getLoreStats(lore);
               value = loreStats.equals(LoreStatsEnum.CRITICAL_DAMAGE) ? statsManager.getLoreValue(loreStats, LoreStatsOption.MIN, lore) + 1.0D : statsManager.getLoreValue(loreStats, LoreStatsOption.MIN, lore);
               chance = loreStats.equals(LoreStatsEnum.CRITICAL_DAMAGE) ? statsManager.getLoreValue(loreStats, LoreStatsOption.MAX, lore) + 1.0D : statsManager.getLoreValue(loreStats, LoreStatsOption.MAX, lore);
               if (value == chance) {
                  replacement = "@MyItems, LoreStats = {lorestats}, Value = {value}";
                  mapPlaceholder.clear();
                  mapPlaceholder.put("lorestats", String.valueOf(loreStats));
                  mapPlaceholder.put("value", String.valueOf(value));
                  replacement = TextUtil.placeholder(mapPlaceholder, replacement);
                  EquipmentUtil.setLore(item, line, replacement);
               } else {
                  replacement = "@MyItems, LoreStats = {lorestats}, MinValue = {minvalue}, MaxValue = {maxvalue}";
                  mapPlaceholder.clear();
                  mapPlaceholder.put("lorestats", String.valueOf(loreStats));
                  mapPlaceholder.put("minvalue", String.valueOf(value));
                  mapPlaceholder.put("maxvalue", String.valueOf(chance));
                  replacement = TextUtil.placeholder(mapPlaceholder, replacement);
                  EquipmentUtil.setLore(item, line, replacement);
               }
            } else {
               String element;
               if (socketManager.isSocketEmptyLore(lore)) {
                  element = "@MyItems, SlotSocket = Empty";
                  EquipmentUtil.setLore(item, line, "@MyItems, SlotSocket = Empty");
               } else {
                  String ability;
                  if (socketManager.isSocketGemsLore(lore)) {
                     element = socketManager.getSocket(lore);
                     ability = "@MyItems, Socket = {socket}";
                     mapPlaceholder.clear();
                     mapPlaceholder.put("socket", String.valueOf(element));
                     ability = TextUtil.placeholder(mapPlaceholder, ability);
                     EquipmentUtil.setLore(item, line, ability);
                  } else if (abilityWeaponManager.isAbilityItemWeapon(lore)) {
                     AbilityItemWeapon abilityItemWeapon = abilityWeaponManager.getAbilityItemWeapon(lore);
                     ability = abilityItemWeapon.getAbility();
                     int grade = abilityItemWeapon.getGrade();
                     chance = abilityItemWeapon.getChance();
                     replacement = "@MyItems, Ability = {ability}, Grade = {grade}, Chance = {chance}";
                     mapPlaceholder.clear();
                     mapPlaceholder.put("ability", String.valueOf(ability));
                     mapPlaceholder.put("grade", String.valueOf(grade));
                     mapPlaceholder.put("chance", String.valueOf(chance));
                     replacement = TextUtil.placeholder(mapPlaceholder, replacement);
                     EquipmentUtil.setLore(item, line, replacement);
                  } else if (elementManager.isElement(lore)) {
                     element = elementManager.getElement(lore);
                     value = elementManager.getElementValue(lore);
                      replacement = "@MyItems, Element = {element}, Value = {value}";
                     mapPlaceholder.clear();
                     mapPlaceholder.put("element", element);
                     mapPlaceholder.put("value", String.valueOf(value));
                     replacement = TextUtil.placeholder(mapPlaceholder, replacement);
                     EquipmentUtil.setLore(item, line, replacement);
                  } else if (passiveEffectManager.isPassiveEffect(lore)) {
                     PassiveEffectEnum buff = passiveEffectManager.getPassiveEffect(lore);
                     int grade = passiveEffectManager.passiveEffectGrade(buff, lore);
                      replacement = "@MyItems, Buff = {buff}, Grade = {grade}";
                     mapPlaceholder.clear();
                     mapPlaceholder.put("buff", String.valueOf(buff));
                     mapPlaceholder.put("grade", String.valueOf(grade));
                     replacement = TextUtil.placeholder(mapPlaceholder, replacement);
                     EquipmentUtil.setLore(item, line, replacement);
                  } else if (powerManager.isPower(lore)) {
                     PowerEnum power = powerManager.getPower(lore);
                     PowerClickEnum click = powerManager.getClick(lore);
                     double cooldown = powerManager.getCooldown(lore);
                     if (click != null) {
                        if (power.equals(PowerEnum.COMMAND)) {
                           String type = powerCommandManager.getCommand(lore);
                           if (type != null) {
                              replacement = "@MyItems, Power = {power}, Click = {click}, Type = {type}, Cooldown = {cooldown}";
                              mapPlaceholder.clear();
                              mapPlaceholder.put("power", String.valueOf(power));
                              mapPlaceholder.put("click", String.valueOf(click));
                              mapPlaceholder.put("type", type);
                              mapPlaceholder.put("cooldown", String.valueOf(cooldown));
                              replacement = TextUtil.placeholder(mapPlaceholder, replacement);
                              EquipmentUtil.setLore(item, line, replacement);
                           }
                        } else if (power.equals(PowerEnum.SHOOT)) {
                           ProjectileEnum type = powerShootManager.getShoot(lore);
                           if (type != null) {
                              replacement = "@MyItems, Power = {power}, Click = {click}, Type = {type}, Cooldown = {cooldown}";
                              mapPlaceholder.clear();
                              mapPlaceholder.put("power", String.valueOf(power));
                              mapPlaceholder.put("click", String.valueOf(click));
                              mapPlaceholder.put("type", String.valueOf(type));
                              mapPlaceholder.put("cooldown", String.valueOf(cooldown));
                              replacement = TextUtil.placeholder(mapPlaceholder, replacement);
                              EquipmentUtil.setLore(item, line, replacement);
                           }
                        } else if (power.equals(PowerEnum.SPECIAL)) {
                           PowerSpecialEnum type = powerSpecialManager.getSpecial(lore);
                           if (type != null) {
                              replacement = "@MyItems, Power = {power}, Click = {click}, Type = {type}, Cooldown = {cooldown}";
                              mapPlaceholder.clear();
                              mapPlaceholder.put("power", String.valueOf(power));
                              mapPlaceholder.put("click", String.valueOf(click));
                              mapPlaceholder.put("type", String.valueOf(type));
                              mapPlaceholder.put("cooldown", String.valueOf(cooldown));
                              replacement = TextUtil.placeholder(mapPlaceholder, replacement);
                              EquipmentUtil.setLore(item, line, replacement);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return item;
   }

   private final ItemStack convertToGame(ItemStack convertItem) {
      GameManager gameManager = this.plugin.getGameManager();
      PowerManager powerManager = gameManager.getPowerManager();
      PowerCommandManager powerCommandManager = powerManager.getPowerCommandManager();
      PowerShootManager powerShootManager = powerManager.getPowerShootManager();
      PowerSpecialManager powerSpecialManager = powerManager.getPowerSpecialManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      ElementManager elementManager = gameManager.getElementManager();
      SocketManager socketManager = gameManager.getSocketManager();
      PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      ItemStack item = new ItemStack(convertItem);
      if (EquipmentUtil.loreCheck(item)) {
         List<String> lores = EquipmentUtil.getLores(item);

         for(int i = 0; i < lores.size(); ++i) {
            int line = i + 1;
            String lore = ((String)lores.get(i)).replaceAll(" ", "");
            if (lore.contains("@MyItems")) {
               String[] part = lore.split(",");
               if (part.length > 1) {
                  double valueElement;
                  int t;
                  String replacement;
                  String[] element;
                  String key;
                  String value;
                  String keyCommand;
                  if (!lore.contains("LoreStats=") && !lore.contains("Stats=")) {
                     String type;
                     String ability;
                     int grade;
                     String gems;
                     if (!lore.contains("SlotSocket=") && !lore.contains("SlotSockets=")) {
                        if (!lore.contains("Socket=") && !lore.contains("Sockets=")) {
                           if (!lore.contains("Element=") && !lore.contains("Elements=")) {
                              if (!lore.contains("Buff=") && !lore.contains("Buffs=")) {
                                 double cooldown;
                                 if (lore.contains("Ability=")) {
                                    ability = null;
                                    grade = -1;
                                    cooldown = 0.0D;

                                    for(t = 0; t < part.length; ++t) {
                                       keyCommand = part[t];
                                       element = keyCommand.split("=");
                                       if (element.length == 2) {
                                          key = element[0];
                                          key = element[1];
                                          if (key.equalsIgnoreCase("Ability")) {
                                             ability = key;
                                          } else if (key.equalsIgnoreCase("Grade")) {
                                             if (MathUtil.isNumber(key)) {
                                                grade = MathUtil.parseInteger(key);
                                             }
                                          } else if (key.equalsIgnoreCase("chance") && MathUtil.isNumber(key)) {
                                             cooldown = MathUtil.parseDouble(key);
                                          }
                                       }
                                    }

                                    if (ability != null && grade != -1 && cooldown != 0.0D) {
                                       type = abilityWeaponManager.getTextAbility(ability, grade, cooldown);
                                       EquipmentUtil.setLore(item, line, type);
                                    }
                                 } else if (lore.contains("Power=")) {
                                    PowerEnum power = null;
                                    PowerClickEnum click = null;
                                    cooldown = 0.0D;
                                    type = null;

                                    for(t = 0; t < part.length; ++t) {
                                       replacement = part[t];
                                       element = replacement.split("=");
                                       if (element.length == 2) {
                                          key = element[0];
                                          value = element[1];
                                          if (key.equalsIgnoreCase("Power")) {
                                             power = PowerEnum.get(value);
                                          } else if (key.equalsIgnoreCase("Click")) {
                                             click = PowerClickEnum.get(value);
                                          } else if (key.equalsIgnoreCase("Cooldown")) {
                                             if (MathUtil.isNumber(value)) {
                                                cooldown = MathUtil.parseDouble(value);
                                             }
                                          } else if (key.equalsIgnoreCase("Type")) {
                                             type = value;
                                          }
                                       }
                                    }

                                    if (power != null && click != null && type != null) {
                                       if (power.equals(PowerEnum.COMMAND)) {
                                          if (powerCommandManager.isPowerCommandExists(type)) {
                                             keyCommand = powerCommandManager.getCommandKey(type);
                                             replacement = powerCommandManager.getTextPowerCommand(click, keyCommand, cooldown);
                                             EquipmentUtil.setLore(item, line, replacement);
                                          }
                                       } else if (power.equals(PowerEnum.SHOOT)) {
                                          ProjectileEnum projectile = ProjectileEnum.getProjectileEnum(type);
                                          if (projectile != null) {
                                             replacement = powerShootManager.getTextPowerShoot(click, projectile, cooldown);
                                             EquipmentUtil.setLore(item, line, replacement);
                                          }
                                       } else if (power.equals(PowerEnum.SPECIAL)) {
                                          PowerSpecialEnum special = PowerSpecialEnum.get(type);
                                          if (special != null) {
                                             replacement = powerSpecialManager.getTextPowerSpecial(click, special, cooldown);
                                             EquipmentUtil.setLore(item, line, replacement);
                                          }
                                       }
                                    }
                                 }
                              } else {
                                 PassiveEffectEnum effect = null;
                                 grade = -1;

                                 for(t = 0; t < part.length; ++t) {
                                    replacement = part[t];
                                    element = replacement.split("=");
                                    if (element.length == 2) {
                                       keyCommand = element[0];
                                       replacement = element[1];
                                       if (!keyCommand.equalsIgnoreCase("Buff") && !keyCommand.equalsIgnoreCase("Buffs")) {
                                          if ((keyCommand.equalsIgnoreCase("Grade") || keyCommand.equalsIgnoreCase("Grades")) && MathUtil.isNumber(replacement)) {
                                             grade = MathUtil.parseInteger(replacement);
                                          }
                                       } else {
                                          effect = PassiveEffectEnum.get(replacement);
                                       }
                                    }
                                 }

                                 if (effect != null && grade != -1) {
                                    replacement = passiveEffectManager.getTextPassiveEffect(effect, grade);
                                    EquipmentUtil.setLore(item, line, replacement);
                                 }
                              }
                           } else {
                              ability = null;
                              valueElement = 1.0D;

                              for( t = 0; t < part.length; ++t) {
                                 type = part[t];
                                 element = type.split("=");
                                 if (element.length == 2) {
                                    replacement = element[0];
                                    key = element[1];
                                    if (!replacement.equalsIgnoreCase("Element") && !replacement.equalsIgnoreCase("Elements")) {
                                       if ((replacement.equalsIgnoreCase("Value") || replacement.equalsIgnoreCase("Values")) && MathUtil.isNumber(key)) {
                                          valueElement = MathUtil.parseDouble(key);
                                       }
                                    } else {
                                       ability = key;
                                    }
                                 }
                              }

                              if (ability != null) {
                                 replacement = elementManager.getTextElement(ability, valueElement);
                                 EquipmentUtil.setLore(item, line, replacement);
                              }
                           }
                        } else {
                           SocketGems socket = null;

                           for(grade = 0; grade < part.length; ++grade) {
                              replacement = part[grade];
                              element = replacement.split("=");
                              if (element.length >= 2) {
                                 type = element[0];
                                 keyCommand = element[1];
                                 replacement = element.length > 2 ? element[2] : String.valueOf(1);
                                 if (type.equalsIgnoreCase("Socket") || type.equalsIgnoreCase("Sockets")) {
                                    grade = MathUtil.isNumber(replacement) ? MathUtil.parseInteger(replacement) : 1;
                                    socket = socketManager.getSocketBuild(keyCommand, grade);
                                 }
                              }
                           }

                           if (socket != null) {
                              gems = socket.getGems();
                              t = socket.getGrade();
                              replacement = socketManager.getTextSocketGemsLore(gems, t);
                              EquipmentUtil.setLore(item, line, replacement);
                           }
                        }
                     } else {
                        ability = null;

                        for(grade = 0; grade < part.length; ++grade) {
                           replacement = part[grade];
                           element = replacement.split("=");
                           if (element.length == 2) {
                              type = element[0];
                              keyCommand = element[1];
                              if (type.equalsIgnoreCase("SlotSocket") || type.equalsIgnoreCase("SlotSockets")) {
                                 ability = keyCommand;
                              }
                           }
                        }

                        if (ability != null && ability.equalsIgnoreCase("Empty")) {
                           gems = socketManager.getTextSocketSlotEmpty();
                           EquipmentUtil.setLore(item, line, gems);
                        }
                     }
                  } else {
                     LoreStatsEnum loreStats = null;
                     valueElement = -1.0D;
                     double maxValue = -1.0D;

                     for(t = 0; t < part.length; ++t) {
                        replacement = part[t];
                        element = replacement.split("=");
                        if (element.length == 2) {
                           key = element[0];
                           value = element[1];
                           if (!key.equalsIgnoreCase("LoreStats") && !key.equalsIgnoreCase("Stats")) {
                              if (!key.equalsIgnoreCase("MinValue") && !key.equalsIgnoreCase("Value")) {
                                 if ((key.equalsIgnoreCase("MaxValue") || key.equalsIgnoreCase("Max")) && MathUtil.isNumber(value)) {
                                    maxValue = MathUtil.parseDouble(value);
                                 }
                              } else if (MathUtil.isNumber(value)) {
                                 valueElement = MathUtil.parseDouble(value);
                              }
                           } else {
                              loreStats = LoreStatsEnum.get(value);
                           }
                        }
                     }

                     if (loreStats != null && valueElement != -1.0D) {
                        keyCommand = maxValue != -1.0D ? statsManager.getTextLoreStats(loreStats, valueElement, maxValue) : statsManager.getTextLoreStats(loreStats, valueElement);
                        EquipmentUtil.setLore(item, line, keyCommand);
                     }
                  }
               }
            }
         }
      }

      return item;
   }

   public final void saveItem(ItemStack item, String nameid) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String path = dataManager.getPath("Path_File_Item");
      ItemStack fileItem = this.convertToFile(item);
      this.mapItem.put(nameid, item);
      FileUtil.addObject(this.plugin, path, nameid, fileItem);
   }

   public final void removeItem(String nameid) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String path = dataManager.getPath("Path_File_Item");
      this.mapItem.remove(nameid);
      FileUtil.removeObject(this.plugin, path, nameid);
   }

   public final void convertOldDatabase() {
      File dirDatabase = FileUtil.getFile(this.plugin, "database");
      if (dirDatabase.exists()) {
         File oldItemsFile = FileUtil.getFile(this.plugin, "database/items.dat");
         if (oldItemsFile.exists()) {
            HashMap<String, Object> oldItems = (HashMap)FileUtil.loadObjectSilent(oldItemsFile);
            Iterator var5 = oldItems.keySet().iterator();

            while(var5.hasNext()) {
               String key = (String)var5.next();
               Object rawItem = oldItems.get(key);
               ItemStack item = this.deserializeItemStack((HashMap)rawItem);
               this.convertToFile(item);
               this.saveItem(item, key);
            }

            oldItemsFile.delete();
            dirDatabase.delete();
         }
      }

   }

   private ItemStack deserializeItemStack(HashMap<Map<String, Object>, Map<String, Object>> serializedItemStackMap) {
      Entry<Map<String, Object>, Map<String, Object>> serializedItemStack = (Entry)serializedItemStackMap.entrySet().iterator().next();
      ItemStack itemStack = ItemStack.deserialize((Map)serializedItemStack.getKey());
      if (serializedItemStack.getValue() != null) {
         ItemMeta itemMeta = (ItemMeta)ConfigurationSerialization.deserializeObject((Map)serializedItemStack.getValue(), ConfigurationSerialization.getClassByAlias("ItemMeta"));
         itemStack.setItemMeta(itemMeta);
      }

      return itemStack;
   }

   private final void moveOldFile() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathSource = "item.yml";
      String pathTarget = dataManager.getPath("Path_File_Item");
      File fileSource = FileUtil.getFile(this.plugin, "item.yml");
      File fileTarget = FileUtil.getFile(this.plugin, pathTarget);
      if (fileSource.exists()) {
         FileUtil.moveFileSilent(fileSource, fileTarget);
      }

   }
}
