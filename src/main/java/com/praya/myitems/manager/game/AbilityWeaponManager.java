package com.praya.myitems.manager.game;

import api.praya.myitems.builder.ability.AbilityItemWeapon;
import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeCastDamage;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.game.AbilityWeaponConfig;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.register.RegisterAbilityWeaponManager;
import com.praya.myitems.manager.register.RegisterManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.RomanNumber;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.enums.main.SlotType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class AbilityWeaponManager extends HandlerManager {
   private final AbilityWeaponConfig abilityWeaponConfig;

   protected AbilityWeaponManager(MyItems plugin) {
      super(plugin);
      this.abilityWeaponConfig = new AbilityWeaponConfig(plugin);
   }

   public final AbilityWeaponConfig getAbilityWeaponConfig() {
      return this.abilityWeaponConfig;
   }

   public final Collection<String> getAbilityWeaponPropertiesIDs() {
      return this.getAbilityWeaponConfig().getAbilityWeaponPropertiesIDs();
   }

   public final Collection<AbilityWeaponProperties> getAllAbilityWeaponProperties() {
      return this.getAbilityWeaponConfig().getAllAbilityWeaponProperties();
   }

   public final AbilityWeaponProperties getAbilityWeaponProperties(String ability) {
      return this.getAbilityWeaponConfig().getAbilityWeaponProperties(ability);
   }

   public final AbilityItemWeapon getAbilityItemWeapon(String lore) {
      RegisterManager registerManager = this.plugin.getRegisterManager();
      RegisterAbilityWeaponManager registerAbilityWeaponManager = registerManager.getRegisterAbilityWeaponManager();
      if (lore != null) {
         String keyAbility = this.getKeyAbility();
         String keyChance = this.getKeyChance();
         if (lore.contains(keyAbility) && lore.contains(keyChance)) {
            String[] partsAbility = lore.split(keyAbility);
            int partsAbilityLength = partsAbility.length;
            if (partsAbilityLength > 1) {
               String[] partsKeyLore = partsAbility[1].split(" ");
               int partsKeyLoreLength = partsKeyLore.length;
               String textGrade = partsKeyLore[partsKeyLoreLength - 1];
               String keyLore = null;

               for(int index = 0; index < partsKeyLoreLength - 1; ++index) {
                  String part = partsKeyLore[index];
                  if (index == 0) {
                     keyLore = part;
                  } else {
                     keyLore = keyLore + " " + part;
                  }
               }

               if (keyLore != null) {
                  AbilityWeapon abilityWeapon = registerAbilityWeaponManager.getAbilityWeaponByKeyLore(keyLore);
                  int grade = RomanNumber.romanConvert(textGrade);
                  if (grade > 0) {
                     String[] partsChance = lore.split(keyChance);
                     int partsChanceLength = partsChance.length;
                     if (partsChanceLength > 1) {
                        String textChance = partsChance[1];
                        if (MathUtil.isNumber(textChance)) {
                           String ability = abilityWeapon.getID();
                           double chance = MathUtil.parseDouble(textChance);
                           AbilityItemWeapon abilityItemWeapon = new AbilityItemWeapon(ability, grade, chance);
                           return abilityItemWeapon;
                        }
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   public final boolean isAbilityItemWeapon(String lore) {
      return this.getAbilityItemWeapon(lore) != null;
   }

   public final List<AbilityItemWeapon> getListAbilityItemWeapon(ItemStack item) {
      List<AbilityItemWeapon> listAbilityItemWeapon = new ArrayList();
      if (item != null) {
         String keyAbilityCheck = this.getKeyAbility();
         List<String> lores = EquipmentUtil.getLores(item);
         Iterator var6 = lores.iterator();

         while(var6.hasNext()) {
            String lore = (String)var6.next();
            if (lore.contains(keyAbilityCheck)) {
               AbilityItemWeapon abilityItemWeapon = this.getAbilityItemWeapon(lore);
               if (abilityItemWeapon != null) {
                  listAbilityItemWeapon.add(abilityItemWeapon);
               }
            }
         }
      }

      return listAbilityItemWeapon;
   }

   public final boolean hasAbilityItemWeapon(ItemStack item) {
      return !this.getListAbilityItemWeapon(item).isEmpty();
   }

   public final Integer getLineAbilityItemWeapon(ItemStack item, String ability) {
      if (item != null && ability != null) {
         String keyAbility = this.getKeyAbility(ability);
         if (keyAbility != null) {
            List<String> lores = EquipmentUtil.getLores(item);

            for(int index = 0; index < lores.size(); ++index) {
               String lore = (String)lores.get(index);
               if (lore.contains(keyAbility)) {
                  int line = index + 1;
                  return line;
               }
            }
         }
      }

      return null;
   }

   public final HashMap<Slot, Collection<AbilityItemWeapon>> getMapListAbilityItemWeapon(LivingEntity entity) {
      MainConfig mainConfig = MainConfig.getInstance();
      HashMap<Slot, Collection<AbilityItemWeapon>> mapListAbilityItemWeapon = new HashMap();
      if (entity != null) {
         boolean enableAbilityOffHand = mainConfig.isAbilityWeaponEnableOffHand();
         Slot[] var8;
         int var7 = (var8 = Slot.values()).length;

         for(int var6 = 0; var6 < var7; ++var6) {
            Slot slot = var8[var6];
            if (slot.getType().equals(SlotType.WEAPON) && (!slot.equals(Slot.OFFHAND) || enableAbilityOffHand)) {
               ItemStack item = Bridge.getBridgeEquipment().getEquipment(entity, slot);
               List<AbilityItemWeapon> listAbilityItemWeapon = this.getListAbilityItemWeapon(item);
               mapListAbilityItemWeapon.put(slot, listAbilityItemWeapon);
            }
         }
      }

      return mapListAbilityItemWeapon;
   }

   public final HashMap<AbilityWeapon, Integer> getMapAbilityWeapon(Collection<AbilityItemWeapon> listAbilityItemWeapon) {
      return this.getMapAbilityWeapon(listAbilityItemWeapon, false);
   }

   public final HashMap<AbilityWeapon, Integer> getMapAbilityWeapon(Collection<AbilityItemWeapon> listAbilityItemWeapon, boolean checkChance) {
      RegisterManager registerManager = this.plugin.getRegisterManager();
      RegisterAbilityWeaponManager registerAbilityWeaponManager = registerManager.getRegisterAbilityWeaponManager();
      HashMap<AbilityWeapon, Integer> mapAbilityWeapon = new HashMap();
      if (listAbilityItemWeapon != null) {
         HashMap<String, Integer> mapAbilityGrade = new HashMap();
         Iterator var8 = listAbilityItemWeapon.iterator();

         label33:
         while(true) {
            AbilityItemWeapon abilityItemWeapon;
            double chance;
            do {
               if (!var8.hasNext()) {
                  var8 = mapAbilityGrade.keySet().iterator();

                  while(var8.hasNext()) {
                     String ability = (String)var8.next();
                     AbilityWeapon abilityWeapon = registerAbilityWeaponManager.getAbilityWeapon(ability);
                     if (abilityWeapon != null) {
                        int grade = (Integer)mapAbilityGrade.get(ability);
                        mapAbilityWeapon.put(abilityWeapon, grade);
                     }
                  }
                  break label33;
               }

               abilityItemWeapon = (AbilityItemWeapon)var8.next();
               chance = abilityItemWeapon.getChance();
            } while(checkChance && !MathUtil.chanceOf(chance));

            String ability = abilityItemWeapon.getAbility();
            int grade = abilityItemWeapon.getGrade();
            if (mapAbilityGrade.containsKey(ability)) {
               int totalGrade = (Integer)mapAbilityGrade.get(ability) + grade;
               mapAbilityGrade.put(ability, totalGrade);
            } else {
               mapAbilityGrade.put(ability, grade);
            }
         }
      }

      return mapAbilityWeapon;
   }

   public final HashMap<AbilityWeapon, Integer> getMapAbilityWeapon(LivingEntity entity) {
      return this.getMapAbilityWeapon(entity, false);
   }

   public final HashMap<AbilityWeapon, Integer> getMapAbilityWeapon(LivingEntity entity, boolean checkChance) {
      HashMap<AbilityWeapon, Integer> mapAbilityWeapon = new HashMap();
      if (entity != null) {
         HashMap<Slot, Collection<AbilityItemWeapon>> mapListAbilityItemWeapon = this.getMapListAbilityItemWeapon(entity);
         Iterator var6 = mapListAbilityItemWeapon.values().iterator();

         while(var6.hasNext()) {
            Collection<AbilityItemWeapon> listAbilityItemWeapon = (Collection)var6.next();
            HashMap<AbilityWeapon, Integer> mapAbilityWeaponSlot = this.getMapAbilityWeapon(listAbilityItemWeapon, checkChance);
            Iterator var9 = mapAbilityWeaponSlot.keySet().iterator();

            while(var9.hasNext()) {
               AbilityWeapon abilityWeapon = (AbilityWeapon)var9.next();
               int grade = (Integer)mapAbilityWeaponSlot.get(abilityWeapon);
               if (mapAbilityWeapon.containsKey(abilityWeapon)) {
                  int totalGrade = (Integer)mapAbilityWeapon.get(abilityWeapon) + grade;
                  mapAbilityWeapon.put(abilityWeapon, totalGrade);
               } else {
                  mapAbilityWeapon.put(abilityWeapon, grade);
               }
            }
         }
      }

      return mapAbilityWeapon;
   }

   public final double getTotalBaseBonusDamage(HashMap<AbilityWeapon, Integer> mapAbilityWeapon) {
      double totalBaseBonusDamage = 0.0D;
      if (mapAbilityWeapon != null) {
         Iterator var5 = mapAbilityWeapon.keySet().iterator();

         while(var5.hasNext()) {
            AbilityWeapon abilityWeapon = (AbilityWeapon)var5.next();
            if (abilityWeapon instanceof AbilityWeaponAttributeBaseDamage) {
               AbilityWeaponAttributeBaseDamage abilityWeaponAttributeBaseDamage = (AbilityWeaponAttributeBaseDamage)abilityWeapon;
               int grade = (Integer)mapAbilityWeapon.get(abilityWeapon);
               double baseBonusDamage = abilityWeaponAttributeBaseDamage.getBaseBonusDamage(grade);
               totalBaseBonusDamage += baseBonusDamage;
            }
         }
      }

      return totalBaseBonusDamage;
   }

   public final double getTotalBasePercentDamage(HashMap<AbilityWeapon, Integer> mapAbilityWeapon) {
      double totalBasePercentDamage = 0.0D;
      if (mapAbilityWeapon != null) {
         Iterator var5 = mapAbilityWeapon.keySet().iterator();

         while(var5.hasNext()) {
            AbilityWeapon abilityWeapon = (AbilityWeapon)var5.next();
            if (abilityWeapon instanceof AbilityWeaponAttributeBaseDamage) {
               AbilityWeaponAttributeBaseDamage abilityWeaponAttributeBaseDamage = (AbilityWeaponAttributeBaseDamage)abilityWeapon;
               int grade = (Integer)mapAbilityWeapon.get(abilityWeapon);
               double basePercentDamage = abilityWeaponAttributeBaseDamage.getBasePercentDamage(grade);
               totalBasePercentDamage += basePercentDamage;
            }
         }
      }

      return totalBasePercentDamage;
   }

   public final double getTotalCastBonusDamage(HashMap<AbilityWeapon, Integer> mapAbilityWeapon) {
      double totalCastBonusDamage = 0.0D;
      if (mapAbilityWeapon != null) {
         Iterator var5 = mapAbilityWeapon.keySet().iterator();

         while(var5.hasNext()) {
            AbilityWeapon abilityWeapon = (AbilityWeapon)var5.next();
            if (abilityWeapon instanceof AbilityWeaponAttributeCastDamage) {
               AbilityWeaponAttributeCastDamage abilityWeaponAttributeCastDamage = (AbilityWeaponAttributeCastDamage)abilityWeapon;
               int grade = (Integer)mapAbilityWeapon.get(abilityWeapon);
               double castBonusDamage = abilityWeaponAttributeCastDamage.getCastBonusDamage(grade);
               totalCastBonusDamage += castBonusDamage;
            }
         }
      }

      return totalCastBonusDamage;
   }

   public final double getTotalCastPercentDamage(HashMap<AbilityWeapon, Integer> mapAbilityWeapon) {
      double totalCastPercentDamage = 0.0D;
      if (mapAbilityWeapon != null) {
         Iterator var5 = mapAbilityWeapon.keySet().iterator();

         while(var5.hasNext()) {
            AbilityWeapon abilityWeapon = (AbilityWeapon)var5.next();
            if (abilityWeapon instanceof AbilityWeaponAttributeCastDamage) {
               AbilityWeaponAttributeCastDamage abilityWeaponAttributeCastDamage = (AbilityWeaponAttributeCastDamage)abilityWeapon;
               int grade = (Integer)mapAbilityWeapon.get(abilityWeapon);
               double castPercentDamage = abilityWeaponAttributeCastDamage.getCastPercentDamage(grade);
               totalCastPercentDamage += castPercentDamage;
            }
         }
      }

      return totalCastPercentDamage;
   }

   public final String getTextAbility(String ability, int grade) {
      return this.getTextAbility(ability, grade, 100.0D);
   }

   public final String getTextAbility(String ability, int grade, double chance) {
      MainConfig mainConfig = MainConfig.getInstance();
      String keyAbility = this.getKeyAbility(ability, grade);
      String keyChance = this.getKeyChance(chance);
      HashMap<String, String> mapPlaceholder = new HashMap();
      String format = mainConfig.getAbilityFormat();
      mapPlaceholder.put("ability", keyAbility);
      mapPlaceholder.put("chance", keyChance);
      format = TextUtil.placeholder(mapPlaceholder, format, "<", ">");
      return format;
   }

   private final String getKeyAbility() {
      return this.getKeyAbility((String)null);
   }

   private final String getKeyAbility(String ability) {
      return this.getKeyAbility(ability, (Integer)null);
   }

   private final String getKeyAbility(String ability, Integer grade) {
      RegisterManager registerManager = this.plugin.getRegisterManager();
      RegisterAbilityWeaponManager registerAbilityWeaponManager = registerManager.getRegisterAbilityWeaponManager();
      MainConfig mainConfig = MainConfig.getInstance();
      String abilityKey = MainConfig.KEY_ABILITY_WEAPON;
      String abilityColor = mainConfig.getAbilityColor();
      if (ability != null) {
         AbilityWeapon abilityWeapon = registerAbilityWeaponManager.getAbilityWeapon(ability);
         if (abilityWeapon != null) {
            String keyLore = TextUtil.colorful(abilityWeapon.getKeyLore());
            if (grade != null) {
               int maxGrade = abilityWeapon.getMaxGrade();
               int abilityGrade = MathUtil.limitInteger(grade, 1, maxGrade);
               String keyAbility = abilityKey + abilityColor + keyLore + " " + RomanNumber.getRomanNumber(abilityGrade) + abilityKey + abilityColor;
               return keyAbility;
            } else {
               String keyAbility = abilityKey + abilityColor + keyLore;
               return keyAbility;
            }
         } else {
            return null;
         }
      } else {
         String keyAbility = abilityKey + abilityColor;
         return keyAbility;
      }
   }

   private final String getKeyChance() {
      return this.getKeyChance((Double)null);
   }

   private final String getKeyChance(Double chance) {
      MainConfig mainConfig = MainConfig.getInstance();
      String abilityKeyPercent = MainConfig.KEY_ABILITY_PERCENT;
      String abilityColorPercent = mainConfig.getAbilityColorPercent();
      if (chance != null) {
         double abilityChance = MathUtil.limitDouble(chance, 0.0D, 100.0D);
         String keyChance = abilityKeyPercent + abilityColorPercent + abilityChance + abilityKeyPercent + abilityColorPercent + "%";
         return keyChance;
      } else {
         String keyChance = abilityKeyPercent + abilityColorPercent;
         return keyChance;
      }
   }
}
