package com.praya.myitems.manager.game;

import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.lorestats.LoreStatsWeapon;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.agarthalib.utility.EntityUtil;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.ServerUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.enums.main.SlotType;
import core.praya.agarthalib.enums.main.VersionNMS;
import java.util.Collection;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LoreStatsManager extends HandlerManager {
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$core$praya$agarthalib$enums$main$Slot;

   protected LoreStatsManager(MyItems plugin) {
      super(plugin);
   }

   public final boolean hasLoreStats(Player player, String lorestats) {
      return this.hasLoreStats(Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND), LoreStatsEnum.get(lorestats));
   }

   public final boolean hasLoreStats(Player player, LoreStatsEnum lorestats) {
      return this.hasLoreStats(Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND), lorestats);
   }

   public final boolean hasLoreStats(ItemStack item, String lorestats) {
      return this.hasLoreStats(item, LoreStatsEnum.get(lorestats));
   }

   public final boolean hasLoreStats(ItemStack item, LoreStatsEnum loreStats) {
      if (EquipmentUtil.loreCheck(item)) {
         String lores = EquipmentUtil.getLores(item).toString();
         return lores.contains(this.getKeyStats(loreStats, true));
      } else {
         return false;
      }
   }

   public final String getTextLoreStats(LoreStatsEnum loreStats, double value) {
      return loreStats.equals(LoreStatsEnum.LEVEL) ? this.getTextLoreStats(loreStats, value, 0.0D) : this.getTextLoreStats(loreStats, value, value);
   }

   public final String getTextLoreStats(LoreStatsEnum loreStats, double value1, double value2) {
      MainConfig mainConfig = MainConfig.getInstance();
      HashMap<String, String> map = new HashMap();
      String format = mainConfig.getStatsFormatValue();
      map.put("stats", this.getKeyStats(loreStats));
      map.put("value", this.statsValue(loreStats, value1, value2));
      format = TextUtil.placeholder(map, format, "<", ">");
      return format;
   }

   public final double getLoreValue(ItemStack item, LoreStatsEnum loreStats, LoreStatsOption option) {
      int line = this.getLineLoreStats(item, loreStats);
      return line != -1 ? this.getLoreValue(item, loreStats, option, line) : 0.0D;
   }

   public final double getLoreValue(ItemStack item, LoreStatsEnum loreStats, LoreStatsOption option, int line) {
      String lore = (String)EquipmentUtil.getLores(item).get(line - 1);
      return this.getLoreValue(loreStats, option, lore);
   }

   public final double getLoreValue(LoreStatsEnum loreStats, LoreStatsOption option, String lore) {
      MainConfig mainConfig = MainConfig.getInstance();
      String[] textListValue = lore.split(MainConfig.KEY_STATS_VALUE);
      if (textListValue.length > 1) {
         String positiveValue = mainConfig.getStatsLorePositiveValue();
         String negativeValue = mainConfig.getStatsLoreNegativeValue();
         String symbolDivide = mainConfig.getStatsLoreDividerSymbol();
         String symbolMultiplier = mainConfig.getStatsLoreMultiplierSymbol();
         String textValue = textListValue[1];
         String textValueMax;
         if (loreStats.equals(LoreStatsEnum.DAMAGE)) {
            String symbolRange = mainConfig.getStatsLoreRangeSymbol();
            if (textValue.contains(symbolRange)) {
               String[] valueList = textValue.split(symbolRange);
               textValueMax = valueList[0].replaceFirst(positiveValue, "").replaceFirst(negativeValue, "");
               textValueMax = valueList[1].replaceFirst(positiveValue, "").replaceFirst(negativeValue, "");
               if (MathUtil.isNumber(textValueMax) && MathUtil.isNumber(textValueMax)) {
                  double valueMin = MathUtil.parseDouble(textValueMax);
                  double valueMax = MathUtil.parseDouble(textValueMax);
                  double value = valueMin + Math.random() * (valueMax - valueMin);
                  if (option == null) {
                     return value;
                  }

                  return !option.equals(LoreStatsOption.MIN) && !option.equals(LoreStatsOption.CURRENT) ? valueMax : valueMin;
               }
            } else {
               textValue = textValue.replaceFirst(positiveValue, "").replaceFirst(negativeValue, "");
               if (MathUtil.isNumber(textValue)) {
                  return MathUtil.parseDouble(textValue);
               }
            }
         } else if (!loreStats.equals(LoreStatsEnum.CRITICAL_CHANCE) && !loreStats.equals(LoreStatsEnum.PENETRATION) && !loreStats.equals(LoreStatsEnum.BLOCK_AMOUNT) && !loreStats.equals(LoreStatsEnum.BLOCK_RATE) && !loreStats.equals(LoreStatsEnum.HIT_RATE) && !loreStats.equals(LoreStatsEnum.DODGE_RATE) && !loreStats.equals(LoreStatsEnum.PVP_DAMAGE) && !loreStats.equals(LoreStatsEnum.PVE_DAMAGE) && !loreStats.equals(LoreStatsEnum.PVP_DEFENSE) && !loreStats.equals(LoreStatsEnum.PVE_DEFENSE) && !loreStats.equals(LoreStatsEnum.ATTACK_AOE_DAMAGE) && !loreStats.equals(LoreStatsEnum.FISHING_SPEED) && !loreStats.equals(LoreStatsEnum.LURES_ENDURANCE)) {
            if (!loreStats.equals(LoreStatsEnum.CRITICAL_DAMAGE) && !loreStats.equals(LoreStatsEnum.FISHING_CHANCE)) {
               if (loreStats.equals(LoreStatsEnum.DURABILITY)) {
                  if (textValue.contains(symbolDivide)) {
                     String[] valueList = textValue.split(symbolDivide);
                     String textValueMin = valueList[0].replaceFirst(positiveValue, "").replaceFirst(negativeValue, "");
                     textValueMax = valueList[1].replaceFirst(positiveValue, "").replaceFirst(negativeValue, "");
                     if (MathUtil.isNumber(textValueMin) && MathUtil.isNumber(textValueMax)) {
                        int valueMin = Integer.valueOf(textValueMin);
                        int valueMax = Integer.valueOf(textValueMax);
                        if (option == null) {
                           return (double)valueMin;
                        }

                        return (double)(!option.equals(LoreStatsOption.MIN) && !option.equals(LoreStatsOption.CURRENT) ? valueMax : valueMin);
                     }
                  }
               } else {
                  textValue = textValue.replaceFirst(positiveValue, "").replaceFirst(negativeValue, "");
                  if (MathUtil.isNumber(textValue)) {
                     return MathUtil.parseDouble(textValue);
                  }
               }
            } else {
               textValue = textValue.replaceAll(symbolMultiplier, "");
               textValue = textValue.replaceFirst(positiveValue, "").replaceFirst(negativeValue, "");
               if (MathUtil.isNumber(textValue)) {
                  double value = MathUtil.parseDouble(textValue);
                  if (loreStats.equals(LoreStatsEnum.CRITICAL_DAMAGE)) {
                     return MathUtil.parseDouble(textValue) - 1.0D;
                  }

                  return value;
               }
            }
         } else {
            textValue = textValue.replaceAll("%", "");
            textValue = textValue.replaceFirst(positiveValue, "").replaceFirst(negativeValue, "");
            if (MathUtil.isNumber(textValue)) {
               return MathUtil.parseDouble(textValue);
            }
         }
      }

      return 0.0D;
   }

   public final void itemRepair(ItemStack item, int repair) {
      int line = this.getLineLoreStats(item, LoreStatsEnum.DURABILITY);
      if (line != -1) {
         int nowDurability = (int)this.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT);
         int maxDurability = (int)this.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.MAX);
         int durability = nowDurability + repair;
         durability = MathUtil.limitInteger(durability, 0, maxDurability);
         if (maxDurability != 0) {
            String newDurability = repair != -1 ? this.getTextLoreStats(LoreStatsEnum.DURABILITY, (double)durability, (double)maxDurability) : this.getTextLoreStats(LoreStatsEnum.DURABILITY, (double)maxDurability);
            EquipmentUtil.setLore(item, line, newDurability);
         }
      }

   }

   public final String statsValue(LoreStatsEnum loreStats, double value1, double value2) {
      MainConfig mainConfig = MainConfig.getInstance();
      String positiveValue = mainConfig.getStatsLorePositiveValue();
      String negativeValue = mainConfig.getStatsLoreNegativeValue();
      String symbolDivide = mainConfig.getStatsLoreDividerSymbol();
      String symbolMultiplier = mainConfig.getStatsLoreMultiplierSymbol();
      String colorValue = mainConfig.getStatsColorValue();
      if (loreStats.equals(LoreStatsEnum.DURABILITY)) {
         value1 = MathUtil.limitDouble(value1, 1.0D, value1);
         value2 = MathUtil.limitDouble(value2, 1.0D, value2);
      } else if (loreStats.equals(LoreStatsEnum.LEVEL)) {
         int maxLevel = mainConfig.getStatsMaxLevelValue();
         value1 = MathUtil.limitDouble(value1, 1.0D, (double)maxLevel);
      } else if (loreStats.equals(LoreStatsEnum.CRITICAL_CHANCE)) {
         value1 = MathUtil.limitDouble(value1, 0.0D, 100.0D);
      }

      String textValue1;
      String textValue2;
      if (loreStats.equals(LoreStatsEnum.DURABILITY)) {
         textValue1 = value1 < 0.0D ? negativeValue + (int)value1 : positiveValue + (int)value1;
         textValue2 = value2 < 0.0D ? negativeValue + (int)value2 : positiveValue + (int)value2;
      } else if (loreStats.equals(LoreStatsEnum.LEVEL)) {
         textValue1 = value1 < 0.0D ? negativeValue + (int)value1 : positiveValue + (int)value1;
         textValue2 = value2 < 0.0D ? "0" : String.valueOf(value2);
      } else {
         textValue1 = value1 < 0.0D ? negativeValue + value1 : positiveValue + value1;
         textValue2 = value2 < 0.0D ? negativeValue + value2 : positiveValue + value2;
      }

      String statsValue;
      String colorExpCurrent;
      if (loreStats.equals(LoreStatsEnum.DAMAGE)) {
         colorExpCurrent = mainConfig.getStatsLoreRangeSymbol();
         if (value1 == value2) {
            statsValue = MainConfig.KEY_STATS_VALUE + textValue1 + MainConfig.KEY_STATS_VALUE + colorValue;
         } else {
            statsValue = value2 > value1 ? MainConfig.KEY_STATS_VALUE + textValue1 + colorExpCurrent + textValue2 + MainConfig.KEY_STATS_VALUE + colorValue : MainConfig.KEY_STATS_VALUE + textValue2 + colorExpCurrent + textValue1 + MainConfig.KEY_STATS_VALUE + colorValue;
         }
      } else if (!loreStats.equals(LoreStatsEnum.CRITICAL_CHANCE) && !loreStats.equals(LoreStatsEnum.PENETRATION) && !loreStats.equals(LoreStatsEnum.ATTACK_AOE_DAMAGE)) {
         if (!loreStats.equals(LoreStatsEnum.CRITICAL_DAMAGE) && !loreStats.equals(LoreStatsEnum.FISHING_CHANCE)) {
            if (!loreStats.equals(LoreStatsEnum.BLOCK_AMOUNT) && !loreStats.equals(LoreStatsEnum.BLOCK_RATE) && !loreStats.equals(LoreStatsEnum.HIT_RATE) && !loreStats.equals(LoreStatsEnum.DODGE_RATE) && !loreStats.equals(LoreStatsEnum.PVP_DAMAGE) && !loreStats.equals(LoreStatsEnum.PVE_DAMAGE) && !loreStats.equals(LoreStatsEnum.PVP_DEFENSE) && !loreStats.equals(LoreStatsEnum.PVE_DEFENSE) && !loreStats.equals(LoreStatsEnum.FISHING_SPEED) && !loreStats.equals(LoreStatsEnum.LURES_ENDURANCE)) {
               if (!loreStats.equals(LoreStatsEnum.FISHING_POWER) && !loreStats.equals(LoreStatsEnum.LURES_MAX_TENSION)) {
                  if (loreStats.equals(LoreStatsEnum.DURABILITY)) {
                     statsValue = value2 > value1 ? MainConfig.KEY_STATS_VALUE + textValue1 + symbolDivide + textValue2 + MainConfig.KEY_STATS_VALUE + colorValue : MainConfig.KEY_STATS_VALUE + textValue2 + symbolDivide + textValue1 + MainConfig.KEY_STATS_VALUE + colorValue;
                  } else if (loreStats.equals(LoreStatsEnum.LEVEL)) {
                     colorExpCurrent = mainConfig.getStatsColorExpCurrent();
                     String colorExpUp = mainConfig.getStatsColorExpUp();
                     HashMap<String, String> map = new HashMap();
                     String expValue = mainConfig.getStatsFormatExp();
                     map.put("exp", MainConfig.KEY_EXP_CURRENT + colorExpCurrent + textValue2 + MainConfig.KEY_EXP_CURRENT + colorExpCurrent);
                     map.put("up", MainConfig.KEY_EXP_UP + colorExpUp + this.getUpExp((int)value1) + MainConfig.KEY_EXP_UP + colorExpUp);
                     expValue = TextUtil.placeholder(map, expValue, "<", ">");
                     statsValue = MainConfig.KEY_STATS_VALUE + textValue1 + MainConfig.KEY_STATS_VALUE + colorValue + " " + expValue;
                  } else {
                     statsValue = MainConfig.KEY_STATS_VALUE + textValue1 + MainConfig.KEY_STATS_VALUE + colorValue;
                  }
               } else {
                  statsValue = value1 > 0.0D ? positiveValue + "+" + MainConfig.KEY_STATS_VALUE + textValue1 + MainConfig.KEY_STATS_VALUE + colorValue : MainConfig.KEY_STATS_VALUE + textValue1 + MainConfig.KEY_STATS_VALUE + colorValue;
               }
            } else {
               statsValue = value1 > 0.0D ? positiveValue + "+" + MainConfig.KEY_STATS_VALUE + textValue1 + "%" + MainConfig.KEY_STATS_VALUE + colorValue : MainConfig.KEY_STATS_VALUE + textValue1 + "%" + MainConfig.KEY_STATS_VALUE + colorValue;
            }
         } else {
            statsValue = MainConfig.KEY_STATS_VALUE + textValue1 + symbolMultiplier + MainConfig.KEY_STATS_VALUE + colorValue;
         }
      } else {
         statsValue = MainConfig.KEY_STATS_VALUE + textValue1 + "%" + MainConfig.KEY_STATS_VALUE + colorValue;
      }

      return statsValue;
   }

   public final LoreStatsEnum getLoreStats(String lore) {
      LoreStatsEnum[] var5;
      int var4 = (var5 = LoreStatsEnum.values()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         LoreStatsEnum loreStats = var5[var3];
         if (lore.contains(this.getKeyStats(loreStats, true))) {
            return loreStats;
         }
      }

      return null;
   }

   public final LoreStatsEnum getLoreStats(ItemStack item, int line) {
      if (line > 0 && EquipmentUtil.hasLore(item) && line <= EquipmentUtil.getLores(item).size()) {
         String lore = (String)EquipmentUtil.getLores(item).get(line - 1);
         return this.getLoreStats(lore);
      } else {
         return null;
      }
   }

   public final boolean isLoreStats(String lore) {
      LoreStatsEnum loreStats = this.getLoreStats(lore);
      return loreStats != null;
   }

   public final boolean isLoreStats(ItemStack item, int line) {
      LoreStatsEnum loreStats = this.getLoreStats(item, line);
      return loreStats != null;
   }

   public final int getLineLoreStats(ItemStack item, LoreStatsEnum loreStats) {
      return EquipmentUtil.loreGetLineKey(item, this.getKeyStats(loreStats, true));
   }

   public final double getUpExp(int level) {
      if (level < 1) {
         level = 1;
      }

      double upExp = (double)((level ^ 2) * 25 + level * 50 + 100);
      return upExp;
   }

   public final boolean checkDurability(ItemStack item) {
      return this.checkDurability(item, (int)this.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT));
   }

   public final boolean checkDurability(ItemStack item, int durability) {
      boolean hasLoreDurability = this.hasLoreStats(item, LoreStatsEnum.DURABILITY);
      return !hasLoreDurability || durability >= 1;
   }

   public final boolean checkLevel(ItemStack item, int requirement) {
      int level = (int)this.getLoreValue(item, LoreStatsEnum.LEVEL, LoreStatsOption.CURRENT);
      return this.checkLevel(item, level, requirement);
   }

   public final boolean checkLevel(ItemStack item, int level, int requirement) {
      boolean hasLoreLevel = this.hasLoreStats(item, LoreStatsEnum.LEVEL);
      return hasLoreLevel && level >= requirement;
   }

   public boolean durability(LivingEntity livingEntity, Slot slot, int currentValue) {
      if (!EntityUtil.isPlayer(livingEntity)) {
         return true;
      } else {
         ItemStack item = Bridge.getBridgeEquipment().getEquipment(livingEntity, slot);
         return item != null && !item.getType().equals(Material.BOW) ? this.durability(livingEntity, item, currentValue, false) : true;
      }
   }

   public final boolean durability(LivingEntity livingEntity, ItemStack item, int currentValue, boolean damage) {
      int line = this.getLineLoreStats(item, LoreStatsEnum.DURABILITY);
      int check = damage ? 1 : 0;
      if (line != -1 && !item.getType().equals(Material.BOW)) {
         if (currentValue < check) {
            return false;
         }

         int nextValue = currentValue - 1;
         if (damage) {
            this.damageDurability(item);
         }

         if (nextValue < check) {
            return false;
         }
      }

      return true;
   }

   public final void damageDurability(ItemStack item) {
      MainConfig mainConfig = MainConfig.getInstance();
      int line = this.getLineLoreStats(item, LoreStatsEnum.DURABILITY);
      if (line != -1) {
         int currentValue = (int)this.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT, line);
         if (currentValue > 0) {
            String positiveValue = mainConfig.getStatsLorePositiveValue();
            String symbolDivide = mainConfig.getStatsLoreDividerSymbol();
            String loreDurability = positiveValue + currentValue + symbolDivide;
            String loreReplace = positiveValue + (currentValue - 1) + symbolDivide;
            loreDurability = ((String)EquipmentUtil.getLores(item).get(line - 1)).replaceAll(loreDurability, loreReplace);
            EquipmentUtil.setLore(item, line, loreDurability);
         }
      }

   }

   public final LoreStatsWeapon getLoreStatsWeapon(LivingEntity attacker) {
      return this.getLoreStatsWeapon(attacker, false);
   }

   public final LoreStatsWeapon getLoreStatsWeapon(LivingEntity attacker, boolean reverse) {
      return this.getLoreStatsWeapon(attacker, true, reverse);
   }

   public final LoreStatsWeapon getLoreStatsWeapon(LivingEntity attacker, boolean checkDurability, boolean reverse) {
      MainConfig mainConfig = MainConfig.getInstance();
      boolean isItemUniversal = mainConfig.isStatsEnableItemUniversal();
      double damage = 0.0D;
      double penetration = 0.0D;
      double pvpDamage = 0.0D;
      double pveDamage = 0.0D;
      double attackAoERadius = 0.0D;
      double attackAoEDamage = 0.0D;
      double criticalChance = 0.0D;
      double criticalDamage = 0.0D;
      double hitRate = 0.0D;
      Slot[] var27;
      int var26 = (var27 = Slot.values()).length;

      for(int var25 = 0; var25 < var26; ++var25) {
         Slot slot = var27[var25];
         if (slot.getType().equals(SlotType.WEAPON) || isItemUniversal) {
            ItemStack item = Bridge.getBridgeEquipment().getEquipment(attacker, slot);
            if (item != null) {
               boolean itemReverse = reverse ? slot.getType().equals(SlotType.WEAPON) : false;
               LoreStatsWeapon statsBuild = this.getLoreStatsWeapon(item, attacker, slot, checkDurability, itemReverse);
               damage += statsBuild.getDamage();
               penetration += statsBuild.getPenetration();
               pvpDamage += statsBuild.getPvPDamage();
               pveDamage += statsBuild.getPvEDamage();
               attackAoERadius += statsBuild.getAttackAoERadius();
               attackAoEDamage += statsBuild.getAttackAoEDamage();
               criticalChance += statsBuild.getCriticalChance();
               criticalDamage += statsBuild.getCriticalDamage();
               hitRate += statsBuild.getHitRate();
            }
         }
      }

      return new LoreStatsWeapon(damage, penetration, pvpDamage, pveDamage, attackAoERadius, attackAoEDamage, criticalChance, criticalDamage, hitRate);
   }

   public final LoreStatsWeapon getLoreStatsWeapon(ItemStack item) {
      return this.getLoreStatsWeapon(item, (LivingEntity)null, Slot.MAINHAND, true, false);
   }

   public final LoreStatsWeapon getLoreStatsWeapon(ItemStack item, boolean reverse) {
      return this.getLoreStatsWeapon(item, (LivingEntity)null, Slot.MAINHAND, true, reverse);
   }

   public final LoreStatsWeapon getLoreStatsWeapon(ItemStack item, boolean checkDurability, boolean reverse) {
      return this.getLoreStatsWeapon(item, (LivingEntity)null, Slot.MAINHAND, checkDurability, reverse);
   }

   public final LoreStatsWeapon getLoreStatsWeapon(ItemStack item, Slot slot, boolean checkDurability, boolean reverse) {
      return this.getLoreStatsWeapon(item, (LivingEntity)null, slot, checkDurability, reverse);
   }

   public final LoreStatsWeapon getLoreStatsWeapon(ItemStack item, LivingEntity holder, Slot slot, boolean checkDurability, boolean reverse) {
      GameManager gameManager = this.plugin.getGameManager();
      RequirementManager requirementManager = gameManager.getRequirementManager();
      MainConfig mainConfig = MainConfig.getInstance();
      Slot secondarySlot = reverse ? Slot.MAINHAND : Slot.OFFHAND;
      if (EquipmentUtil.loreCheck(item) && (!checkDurability || this.checkDurability(item)) && holder != null && holder instanceof Player && requirementManager.isAllowed((Player)holder, item)) {
         double scaleValue = slot.equals(secondarySlot) ? mainConfig.getStatsScaleOffHandValue() : 1.0D;
         double damage = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.DAMAGE, (LoreStatsOption)null) * scaleValue;
         double penetration = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.PENETRATION, (LoreStatsOption)null) * scaleValue;
         double pvpDamage = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.PVP_DAMAGE, (LoreStatsOption)null) * scaleValue;
         double pveDamage = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.PVE_DAMAGE, (LoreStatsOption)null) * scaleValue;
         double attackAoERadius = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.ATTACK_AOE_RADIUS, (LoreStatsOption)null) * scaleValue;
         double attackAoEDamage = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.ATTACK_AOE_DAMAGE, (LoreStatsOption)null) * scaleValue;
         double criticalChance = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.CRITICAL_CHANCE, (LoreStatsOption)null) * scaleValue;
         double criticalDamage = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.CRITICAL_DAMAGE, (LoreStatsOption)null) * scaleValue;
         double hitRate = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.HIT_RATE, (LoreStatsOption)null) * scaleValue;
         return new LoreStatsWeapon(damage, penetration, pvpDamage, pveDamage, attackAoERadius, attackAoEDamage, criticalChance, criticalDamage, hitRate);
      } else {
         return new LoreStatsWeapon(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
      }
   }

   public final LoreStatsArmor getLoreStatsArmor(LivingEntity victims) {
      return this.getLoreStatsArmor(victims, true);
   }

   public final LoreStatsArmor getLoreStatsArmor(LivingEntity victims, boolean checkDurability) {
      MainConfig mainConfig = MainConfig.getInstance();
      boolean isItemUniversal = mainConfig.isStatsEnableItemUniversal();
      double defense = 0.0D;
      double pvpDefense = 0.0D;
      double pveDefense = 0.0D;
      double health = 0.0D;
      double healthRegen = 0.0D;
      double staminaMax = 0.0D;
      double staminaRegen = 0.0D;
      double blockAmount = 0.0D;
      double blockRate = 0.0D;
      double dodgeRate = 0.0D;
      Slot[] var28;
      int var27 = (var28 = Slot.values()).length;

      for(int var26 = 0; var26 < var27; ++var26) {
         Slot slot = var28[var26];
         SlotType slotType = slot.getType();
         ItemStack item;
         if (!slotType.equals(SlotType.ARMOR) && !isItemUniversal) {
            if (ServerUtil.isCompatible(VersionNMS.V1_9_R1)) {
               item = Bridge.getBridgeEquipment().getEquipment(victims, slot);
               if (EquipmentUtil.isSolid(item)) {
                  Material material = item.getType();
                  if (material.equals(Material.SHIELD)) {
                     LoreStatsArmor statsBuild = this.getLoreStatsArmor(item, victims, slot, checkDurability);
                     double scale = mainConfig.getStatsScaleArmorShield();
                     defense += statsBuild.getDefense() * scale;
                     pvpDefense += statsBuild.getPvPDefense() * scale;
                     pveDefense += statsBuild.getPvEDefense() * scale;
                     health += statsBuild.getHealth() * scale;
                     healthRegen += statsBuild.getHealthRegen() * scale;
                     staminaMax += statsBuild.getStaminaMax() * scale;
                     staminaRegen += statsBuild.getStaminaRegen() * scale;
                     blockAmount += statsBuild.getBlockAmount() * scale;
                     blockRate += statsBuild.getBlockRate() * scale;
                     dodgeRate += statsBuild.getDodgeRate() * scale;
                  }
               }
            }
         } else {
            item = Bridge.getBridgeEquipment().getEquipment(victims, slot);
            if (EquipmentUtil.isSolid(item)) {
               LoreStatsArmor statsBuild = this.getLoreStatsArmor(item, victims, slot, checkDurability);
               defense += statsBuild.getDefense();
               pvpDefense += statsBuild.getPvPDefense();
               pveDefense += statsBuild.getPvEDefense();
               health += statsBuild.getHealth();
               healthRegen += statsBuild.getHealthRegen();
               staminaMax += statsBuild.getStaminaMax();
               staminaRegen += statsBuild.getStaminaRegen();
               blockAmount += statsBuild.getBlockAmount();
               blockRate += statsBuild.getBlockRate();
               dodgeRate += statsBuild.getDodgeRate();
            }
         }
      }

      return new LoreStatsArmor(defense, pvpDefense, pveDefense, health, healthRegen, staminaMax, staminaRegen, blockAmount, blockRate, dodgeRate);
   }

   public final LoreStatsArmor getLoreStatsArmor(ItemStack item) {
      return this.getLoreStatsArmor(item, (LivingEntity)null, Slot.MAINHAND, true);
   }

   public final LoreStatsArmor getLoreStatsArmor(ItemStack item, boolean checkDurability) {
      return this.getLoreStatsArmor(item, (LivingEntity)null, Slot.MAINHAND, checkDurability);
   }

   public final LoreStatsArmor getLoreStatsArmor(ItemStack item, Slot slot, boolean checkDurability) {
      return this.getLoreStatsArmor(item, (LivingEntity)null, slot, checkDurability);
   }

   public final LoreStatsArmor getLoreStatsArmor(ItemStack item, LivingEntity holder, Slot slot, boolean checkDurability) {
      GameManager gameManager = this.plugin.getGameManager();
      RequirementManager requirementManager = gameManager.getRequirementManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (!EquipmentUtil.loreCheck(item) || checkDurability && !this.checkDurability(item) || holder != null && holder instanceof Player && !requirementManager.isAllowed((Player)holder, item)) {
         return new LoreStatsArmor(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
      } else {
         double scale = slot.equals(Slot.OFFHAND) ? mainConfig.getStatsScaleOffHandValue() : 1.0D;
         double defense = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.DEFENSE, (LoreStatsOption)null) * scale;
         double pvpDefense = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.PVP_DEFENSE, (LoreStatsOption)null) * scale;
         double pveDefense = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.PVE_DEFENSE, (LoreStatsOption)null) * scale;
         double health = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.HEALTH, (LoreStatsOption)null) * scale;
         double healthRegen = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.HEALTH_REGEN, (LoreStatsOption)null) * scale;
         double staminaMax = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.STAMINA_MAX, (LoreStatsOption)null) * scale;
         double staminaRegen = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.STAMINA_REGEN, (LoreStatsOption)null) * scale;
         double blockAmount = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.BLOCK_AMOUNT, (LoreStatsOption)null) * scale;
         double blockRate = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.BLOCK_RATE, (LoreStatsOption)null) * scale;
         double dodgeRate = this.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.DODGE_RATE, (LoreStatsOption)null) * scale;
         return new LoreStatsArmor(defense, pvpDefense, pveDefense, health, healthRegen, staminaMax, staminaRegen, blockAmount, blockRate, dodgeRate);
      }
   }

   public final void sendBrokenCode(LivingEntity livingEntity, Slot slot) {
      this.sendBrokenCode(livingEntity, slot, true);
   }

   public final void sendBrokenCode(LivingEntity livingEntity, Slot slot, boolean broken) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      GameManager gameManager = this.plugin.getGameManager();
      PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (EntityUtil.isPlayer(livingEntity)) {
         Player player = EntityUtil.parsePlayer(livingEntity);
         ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, slot);
         String message;
         switch($SWITCH_TABLE$core$praya$agarthalib$enums$main$Slot()[slot.ordinal()]) {
         case 1:
            message = lang.getText((LivingEntity)player, "Item_Broken_MainHand");
            break;
         case 2:
            message = lang.getText((LivingEntity)player, "Item_Broken_Offhand");
            break;
         case 3:
            message = lang.getText((LivingEntity)player, "Item_Broken_Helmet");
            break;
         case 4:
            message = lang.getText((LivingEntity)player, "Item_Broken_Chestplate");
            break;
         case 5:
            message = lang.getText((LivingEntity)player, "Item_Broken_Leggings");
            break;
         case 6:
            message = lang.getText((LivingEntity)player, "Item_Broken_Boots");
            break;
         default:
            return;
         }

         SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
         SenderUtil.sendMessage(player, message);
         if (broken && mainConfig.isStatsEnableItemBroken()) {
            boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
            Collection<PassiveEffectEnum> buffs = passiveEffectManager.getPassiveEffects(item);
            Bridge.getBridgeEquipment().setEquipment(livingEntity, (ItemStack)null, slot);
            PlayerUtil.setMaxHealth(player);
            passiveEffectManager.reloadPassiveEffect(player, buffs, enableGradeCalculation);
         }
      }

   }

   public final String getKeyStats(LoreStatsEnum stats) {
      return this.getKeyStats(stats, false);
   }

   public final String getKeyStats(LoreStatsEnum stats, boolean justCheck) {
      MainConfig mainConfig = MainConfig.getInstance();
      String key = MainConfig.KEY_STATS;
      String color = mainConfig.getStatsColor();
      String text = stats.getText();
      return justCheck ? key + color + text : key + color + text + key + color;
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$core$praya$agarthalib$enums$main$Slot() {
      int[] var10000 = $SWITCH_TABLE$core$praya$agarthalib$enums$main$Slot;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[Slot.values().length];

         try {
            var0[Slot.BOOTS.ordinal()] = 6;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[Slot.CHESTPLATE.ordinal()] = 4;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[Slot.HELMET.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[Slot.LEGGINGS.ordinal()] = 5;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[Slot.MAINHAND.ordinal()] = 1;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[Slot.OFFHAND.ordinal()] = 2;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$core$praya$agarthalib$enums$main$Slot = var0;
         return var0;
      }
   }
}
