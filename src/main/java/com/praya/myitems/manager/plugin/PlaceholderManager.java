package com.praya.myitems.manager.plugin;

import api.praya.agarthalib.main.AgarthaLibAPI;
import api.praya.agarthalib.manager.plugin.SupportManagerAPI;
import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsModifier;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.ListUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.builder.placeholder.ReplacerMVDWPlaceholderAPIBuild;
import com.praya.myitems.builder.placeholder.ReplacerPlaceholderAPIBuild;
import com.praya.myitems.config.plugin.PlaceholderConfig;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import com.praya.myitems.manager.game.PowerCommandManager;
import com.praya.myitems.manager.game.PowerManager;
import com.praya.myitems.manager.game.PowerShootManager;
import com.praya.myitems.manager.game.PowerSpecialManager;
import com.praya.myitems.manager.game.RequirementManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.register.RegisterAbilityWeaponManager;
import com.praya.myitems.manager.register.RegisterManager;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlaceholderManager extends HandlerManager {
   private final PlaceholderConfig placeholderConfig;

   protected PlaceholderManager(MyItems plugin) {
      super(plugin);
      this.placeholderConfig = new PlaceholderConfig(plugin);
   }

   public final PlaceholderConfig getPlaceholderConfig() {
      return this.placeholderConfig;
   }

   public final Collection<String> getPlaceholderIDs() {
      return this.getPlaceholderConfig().getPlaceholderIDs();
   }

   public final Collection<String> getPlaceholders() {
      return this.getPlaceholderConfig().getPlaceholders();
   }

   public final String getPlaceholder(String id) {
      return this.getPlaceholderConfig().getPlaceholder(id);
   }

   public final HashMap<String, String> getPlaceholderCopy() {
      return this.getPlaceholderConfig().getPlaceholderCopy();
   }

   public final boolean isPlaceholderExists(String id) {
      return this.getPlaceholder(id) != null;
   }

   public final void registerAll() {
      AgarthaLibAPI agarthaLibAPI = AgarthaLibAPI.getInstance();
      SupportManagerAPI supportManagerAPI = agarthaLibAPI.getPluginManagerAPI().getSupportManager();
      String placeholder = this.plugin.getPluginPlaceholder();
      if (supportManagerAPI.isSupportPlaceholderAPI()) {
         (new ReplacerPlaceholderAPIBuild(this.plugin, placeholder)).hook();
      }

      if (supportManagerAPI.isSupportMVdWPlaceholder()) {
         (new ReplacerMVDWPlaceholderAPIBuild(this.plugin, placeholder)).register();
      }

   }

   public final List<String> localPlaceholder(List<String> list) {
      String divider = "\n";
      String builder = TextUtil.convertListToString(list, "\n");
      String text = this.localPlaceholder(builder);
      return ListUtil.convertStringToList(text, "\n");
   }

   public final String localPlaceholder(String text) {
      return TextUtil.placeholder(this.getPlaceholderCopy(), text);
   }

   public final List<String> pluginPlaceholder(List<String> list, String... identifiers) {
      return this.pluginPlaceholder((List)list, (Player)null, identifiers);
   }

   public final List<String> pluginPlaceholder(List<String> list, Player player, String... identifiers) {
      String divider = "\n";
      String builder = TextUtil.convertListToString(list, "\n");
      String text = this.pluginPlaceholder(builder, player, identifiers);
      return ListUtil.convertStringToList(text, "\n");
   }

   public final String pluginPlaceholder(String text, String... identifiers) {
      return this.pluginPlaceholder((String)text, (Player)null, identifiers);
   }

   public final String pluginPlaceholder(String text, Player player, String... identifiers) {
      HashMap<String, String> map = this.getMapPluginPlaceholder(player, identifiers);
      return TextUtil.placeholder(map, text);
   }

   public final HashMap<String, String> getMapPluginPlaceholder(String... identifiers) {
      return this.getMapPluginPlaceholder((Player)null, identifiers);
   }

   public final HashMap<String, String> getMapPluginPlaceholder(Player player, String... identifiers) {
      String placeholder = this.plugin.getPluginPlaceholder();
      HashMap<String, String> map = new HashMap();
      String[] var8 = identifiers;
      int var7 = identifiers.length;

      for(int var6 = 0; var6 < var7; ++var6) {
         String identifier = var8[var6];
         String replacement = this.getReplacement(player, identifier);
         if (replacement != null) {
            String key = placeholder + "_" + identifier;
            map.put(key, replacement);
         }
      }

      return map;
   }

   public final ItemStack parseItem(Player player, ItemStack item) {
      String divider = "\n";
      String oldLineLore;
      if (EquipmentUtil.hasDisplayName(item)) {
         String oldDisplayName = EquipmentUtil.getDisplayName(item);
         oldLineLore = this.placeholder(player, oldDisplayName);
         EquipmentUtil.setDisplayName(item, oldLineLore);
      }

      if (EquipmentUtil.hasLore(item)) {
         List<String> oldLores = EquipmentUtil.getLores(item);
         oldLineLore = TextUtil.convertListToString(oldLores, "\n");
         String newLineLore = this.placeholder(player, oldLineLore);
         String[] split = newLineLore.split("\n");
         List<String> newLores = new ArrayList();
         String[] var12 = split;
         int var11 = split.length;

         for(int var10 = 0; var10 < var11; ++var10) {
            String lore = var12[var10];
            newLores.add(lore);
         }

         EquipmentUtil.setLores(item, newLores);
      }

      return item;
   }

   public final List<String> placeholder(Player player, List<String> listText) {
      return this.placeholder(player, (List)listText, (LoreStatsModifier)null);
   }

   public final List<String> placeholder(Player player, List<String> listText, LoreStatsModifier modifier) {
      return this.placeholder(player, listText, modifier, "{", "}");
   }

   public final List<String> placeholder(Player player, List<String> listText, LoreStatsModifier modifier, String leftKey, String rightKey) {
      List<String> list = new ArrayList();
      if (listText != null) {
         ListIterator iteratorText = listText.listIterator();

         while(iteratorText.hasNext()) {
            String text = (String)iteratorText.next();
            String textPlaceholder = this.placeholder(player, text, modifier, leftKey, rightKey);
            iteratorText.set(textPlaceholder);
         }

         list.addAll(listText);
      }

      return listText;
   }

   public final String placeholder(Player player, String text) {
      return this.placeholder(player, (String)text, (LoreStatsModifier)null);
   }

   public final String placeholder(Player player, String text, LoreStatsModifier modifier) {
      return this.placeholder(player, text, modifier, "{", "}");
   }

   public final String placeholder(Player player, String text, LoreStatsModifier modifier, String leftKey, String rightKey) {
      String placeholder = this.plugin.getPluginPlaceholder();
      if (text.contains(leftKey)) {
         String[] fullPartFirst = text.split(Pattern.quote(leftKey));
         String[] var11 = fullPartFirst;
         int var10 = fullPartFirst.length;

         for(int var9 = 0; var9 < var10; ++var9) {
            String checkPartFirst = var11[var9];
            if (checkPartFirst.contains(rightKey)) {
               String check = checkPartFirst.split(Pattern.quote(rightKey))[0];
               if (check.contains("_")) {
                  String[] elements = check.split("_", 2);
                  String textholder = elements[0];
                  String identifier = elements[1];
                  if (textholder.equalsIgnoreCase(placeholder)) {
                     CharSequence replacement = this.getReplacement(player, identifier, modifier);
                     CharSequence sequence = String.valueOf(leftKey) + check + rightKey;
                     if (replacement != null) {
                        text = text.replace(sequence, replacement);
                     }
                  }
               }
            }
         }
      }

      return text;
   }

   public final String getReplacement(Player player, String identifier) {
      return this.getReplacement(player, identifier, (LoreStatsModifier)null);
   }

   public final String getReplacement(Player player, String identifier, LoreStatsModifier statsModifier) {
      GameManager gameManager = this.plugin.getGameManager();
      RegisterManager registerManager = this.plugin.getRegisterManager();
      PowerManager powerManager = gameManager.getPowerManager();
      PowerCommandManager powerCommandManager = powerManager.getPowerCommandManager();
      PowerShootManager powerShootManager = powerManager.getPowerShootManager();
      PowerSpecialManager powerSpecialManager = powerManager.getPowerSpecialManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      ElementManager elementManager = gameManager.getElementManager();
      SocketManager socketManager = gameManager.getSocketManager();
      PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      RequirementManager requirementManager = gameManager.getRequirementManager();
      RegisterAbilityWeaponManager registerAbilityWeaponManager = registerManager.getRegisterAbilityWeaponManager();
      String[] parts = identifier.split(":");
      int length = parts.length;
      if (length > 0) {
         String key = parts[0];
         String textClass;
         String textCooldown3;
         double cooldown3;
         String textGrade;
         double cooldown;
         double value1;
         String textCooldown2;
         double chance2;
         if (key.equalsIgnoreCase("text_lorestats")) {
            if (length >= 3) {
               textClass = parts[1];
               LoreStatsEnum loreStats = LoreStatsEnum.get(textClass);
               if (loreStats != null) {
                  textGrade = parts[2];
                  double modifier = statsModifier != null ? statsModifier.getModifier(loreStats) : 1.0D;
                  String textMaxValue2;
                  if (textGrade.contains("~")) {
                     String[] componentsMinValue = textGrade.split("~");
                     textCooldown3 = componentsMinValue[0];
                     textMaxValue2 = componentsMinValue[1];
                     if (!MathUtil.isNumber(textCooldown3) || !MathUtil.isNumber(textMaxValue2)) {
                        return null;
                     }

                     chance2 = MathUtil.parseDouble(textCooldown3);
                     double minValue2 = MathUtil.parseDouble(textMaxValue2);
                     cooldown = MathUtil.valueBetween(chance2, minValue2);
                  } else {
                     if (!MathUtil.isNumber(textGrade)) {
                        return null;
                     }

                     cooldown = MathUtil.roundNumber(MathUtil.parseDouble(textGrade));
                  }

                  if (length == 3) {
                     value1 = cooldown;
                  } else {
                     textCooldown2 = parts[3];
                     if (textCooldown2.contains("~")) {
                        String[] componentsMaxValue = textCooldown2.split("~");
                        textMaxValue2 = componentsMaxValue[0];
                        String textMaxValue3 = componentsMaxValue[1];
                        if (!MathUtil.isNumber(textMaxValue2) || !MathUtil.isNumber(textMaxValue3)) {
                           return null;
                        }

                        cooldown3 = MathUtil.parseDouble(textMaxValue2);
                        double maxValue3 = MathUtil.parseDouble(textMaxValue3);
                        value1 = MathUtil.valueBetween(cooldown3, maxValue3);
                     } else {
                        if (!MathUtil.isNumber(textCooldown2)) {
                           return null;
                        }

                        value1 = MathUtil.roundNumber(MathUtil.parseDouble(textCooldown2));
                     }
                  }

                  return statsManager.getTextLoreStats(loreStats, MathUtil.roundNumber(cooldown * modifier, 2), MathUtil.roundNumber(value1 * modifier, 2));
               }
            }
         } else {
            int rawLevel;
            String textLevel1;
            if (key.equalsIgnoreCase("text_ability")) {
               if (length >= 4) {
                  textClass = parts[1];
                  AbilityWeapon abilityWeapon = registerAbilityWeaponManager.getAbilityWeapon(textClass);
                  if (abilityWeapon != null) {
                     textGrade = parts[2];
                     textLevel1 = parts[3];
                     int grade3;
                     String textChance2;
                     String[] componentsChance;
                     if (textGrade.contains("~")) {
                        componentsChance = textGrade.split("~");
                        textChance2 = componentsChance[0];
                        textCooldown2 = componentsChance[1];
                        if (!MathUtil.isNumber(textChance2) || !MathUtil.isNumber(textCooldown2)) {
                           return null;
                        }

                        int grade1 = MathUtil.parseInteger(textChance2);
                        int grade2 = MathUtil.parseInteger(textCooldown2);
                        int rawGrade = (int)MathUtil.valueBetween((double)grade1, (double)grade2);
                        grade3 = MathUtil.limitInteger(rawGrade, 1, rawGrade);
                     } else {
                        if (!MathUtil.isNumber(textGrade)) {
                           return null;
                        }

                        rawLevel = MathUtil.parseInteger(textGrade);
                        grade3 = MathUtil.limitInteger(rawLevel, 1, rawLevel);
                     }

                     if (textLevel1.contains("~")) {
                        componentsChance = textLevel1.split("~");
                        textChance2 = componentsChance[0];
                        textCooldown2 = componentsChance[1];
                        if (!MathUtil.isNumber(textChance2) || !MathUtil.isNumber(textCooldown2)) {
                           return null;
                        }

                        double chance1 = MathUtil.parseDouble(textChance2);
                        chance2 = MathUtil.parseDouble(textCooldown2);
                        cooldown = MathUtil.valueBetween(chance1, chance2);
                     } else {
                        if (!MathUtil.isNumber(textLevel1)) {
                           return null;
                        }

                        cooldown = MathUtil.roundNumber(MathUtil.parseDouble(textLevel1));
                     }

                     return abilityWeaponManager.getTextAbility(textClass, grade3, cooldown);
                  }
               }
            } else {
               String textGrade8;
               int rawGrade3;
               String[] componentsValue;
               String textValue3;
               if (key.equalsIgnoreCase("text_buff")) {
                  if (length >= 3) {
                     textClass = parts[1];
                     PassiveEffectEnum effect = PassiveEffectEnum.get(textClass);
                     if (effect != null) {
                        textGrade = parts[2];
                        int grade6;
                        if (textGrade.contains("~")) {
                           componentsValue = textGrade.split("~");
                           textGrade8 = componentsValue[0];
                           textValue3 = componentsValue[1];
                           if (!MathUtil.isNumber(textGrade8) || !MathUtil.isNumber(textValue3)) {
                              return null;
                           }

                           rawLevel = MathUtil.parseInteger(textGrade8);
                           rawGrade3 = MathUtil.parseInteger(textValue3);
                           grade6 = (int)MathUtil.valueBetween((double)rawLevel, (double)rawGrade3);
                        } else {
                           if (!MathUtil.isNumber(textGrade)) {
                              return null;
                           }

                           grade6 = MathUtil.parseInteger(textGrade);
                        }

                        return passiveEffectManager.getTextPassiveEffect(effect, grade6);
                     }
                  }
               } else {
                  String textLevel2;
                  if (key.equalsIgnoreCase("text_power")) {
                     if (length >= 4) {
                        textClass = parts[1];
                        PowerEnum power = PowerEnum.get(textClass);
                        if (power != null) {
                           textGrade = parts[2];
                           PowerClickEnum click = PowerClickEnum.get(textGrade);
                           if (click != null) {
                              textLevel2 = parts[3];
                              String type;
                              if (length == 4) {
                                 cooldown = 0.0D;
                              } else {
                                 type = parts[4];
                                 if (type.contains("~")) {
                                    String[] componentsCooldown = type.split("~");
                                    textCooldown2 = componentsCooldown[0];
                                    textCooldown3 = componentsCooldown[1];
                                    if (!MathUtil.isNumber(textCooldown2) || !MathUtil.isNumber(textCooldown3)) {
                                       return null;
                                    }

                                    double cooldown2 = MathUtil.parseDouble(textCooldown2);
                                    cooldown3 = MathUtil.parseDouble(textCooldown3);
                                    cooldown = MathUtil.valueBetween(cooldown2, cooldown3);
                                 } else {
                                    if (!MathUtil.isNumber(type)) {
                                       return null;
                                    }

                                    cooldown = MathUtil.roundNumber(MathUtil.parseDouble(type));
                                 }
                              }

                              if (power.equals(PowerEnum.COMMAND)) {
                                 type = powerCommandManager.getCommandKey(textLevel2);
                                 if (type != null) {
                                    return powerCommandManager.getTextPowerCommand(click, type, cooldown);
                                 }
                              } else if (power.equals(PowerEnum.SHOOT)) {
                                 ProjectileEnum type1 = ProjectileEnum.getProjectileEnum(textLevel2);
                                 if (type1 != null) {
                                    return powerShootManager.getTextPowerShoot(click, type1, cooldown);
                                 }
                              } else if (power.equals(PowerEnum.SPECIAL)) {
                                 PowerSpecialEnum type2 = PowerSpecialEnum.get(textLevel2);
                                 if (type2 != null) {
                                    return powerSpecialManager.getTextPowerSpecial(click, type2, cooldown);
                                 }
                              }
                           }
                        }
                     }
                  } else if (key.equalsIgnoreCase("text_socket_empty")) {
                     if (length >= 1) {
                        return socketManager.getTextSocketSlotEmpty();
                     }
                  } else {
                     String textGrade6;
                     int level2;
                     if (key.equalsIgnoreCase("text_socket_fill")) {
                        if (length >= 3) {
                           textClass = parts[1];
                           if (socketManager.isExist(textClass)) {
                              textGrade6 = parts[2];
                              int grade9;
                              if (textGrade6.contains("~")) {
                                 String[] componentsGrade3 = textGrade6.split("~");
                                 textLevel2 = componentsGrade3[0];
                                 textGrade8 = componentsGrade3[1];
                                 if (!MathUtil.isNumber(textLevel2) || !MathUtil.isNumber(textGrade8)) {
                                    return null;
                                 }

                                 level2 = MathUtil.parseInteger(textLevel2);
                                 rawLevel = MathUtil.parseInteger(textGrade8);
                                 rawGrade3 = (int)MathUtil.valueBetween((double)level2, (double)rawLevel);
                                 grade9 = MathUtil.limitInteger(rawGrade3, 1, rawGrade3);
                              } else {
                                 if (!MathUtil.isNumber(textGrade6)) {
                                    return null;
                                 }

                                 grade9 = MathUtil.parseInteger(textGrade6);
                              }

                              return socketManager.getTextSocketGemsLore(textClass, grade9);
                           }
                        }
                     } else if (key.equalsIgnoreCase("text_element") && length >= 3) {
                        textClass = parts[1];
                        if (elementManager.isExists(textClass)) {
                           textGrade6 = parts[2];
                           double value3;
                           if (textGrade6.contains("~")) {
                              componentsValue = textGrade6.split("~");
                              textGrade8 = componentsValue[0];
                              textValue3 = componentsValue[1];
                              if (!MathUtil.isNumber(textGrade8) || !MathUtil.isNumber(textValue3)) {
                                 return null;
                              }

                              value1 = MathUtil.parseDouble(textGrade8);
                              double value2 = MathUtil.parseDouble(textValue3);
                              value3 = MathUtil.valueBetween(value1, value2);
                           } else {
                              if (!MathUtil.isNumber(textGrade6)) {
                                 return null;
                              }

                              value3 = MathUtil.roundNumber(MathUtil.parseDouble(textGrade6));
                           }

                           return elementManager.getTextElement(textClass, value3);
                        }
                     } else {
                        if (key.equalsIgnoreCase("text_requirement_unbound")) {
                           return requirementManager.getTextSoulUnbound();
                        }

                        if (key.equalsIgnoreCase("text_requirement_bound") && length >= 2) {
                           textClass = parts[1];
                           OfflinePlayer bound = PlayerUtil.getPlayer(textClass);
                           if (bound != null) {
                              return bound.getName();
                           }
                        } else {
                           if (key.equalsIgnoreCase("text_requirement_level") && length >= 2) {
                              textClass = parts[1];
                              int level;
                              if (textClass.contains("~")) {
                                 String[] componentsLevel = textClass.split("~");
                                 textLevel1 = componentsLevel[0];
                                 textLevel2 = componentsLevel[1];
                                 if (!MathUtil.isNumber(textLevel1) || !MathUtil.isNumber(textLevel2)) {
                                    return null;
                                 }

                                 int level1 = MathUtil.parseInteger(textLevel1);
                                 level2 = MathUtil.parseInteger(textLevel2);
                                 rawLevel = (int)MathUtil.valueBetween((double)level1, (double)level2);
                                 level = MathUtil.limitInteger(rawLevel, 0, rawLevel);
                              } else {
                                 if (!MathUtil.isNumber(textClass)) {
                                    return null;
                                 }

                                 level = MathUtil.parseInteger(textClass);
                                 level = MathUtil.limitInteger(level, 0, level);
                              }

                              return requirementManager.getTextLevel(level);
                           }

                           if (key.equalsIgnoreCase("text_requirement_permission") && length >= 2) {
                              textClass = parts[1];
                              return requirementManager.getTextPermission(textClass);
                           }

                           if (key.equalsIgnoreCase("text_requirement_class") && length >= 2) {
                              textClass = parts[1];
                              return requirementManager.getTextClass(textClass);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return null;
   }
}
