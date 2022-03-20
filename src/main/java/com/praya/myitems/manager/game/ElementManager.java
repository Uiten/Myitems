package com.praya.myitems.manager.game;

import api.praya.myitems.builder.element.Element;
import api.praya.myitems.builder.element.ElementBoost;
import api.praya.myitems.builder.element.ElementBoostStats;
import api.praya.myitems.builder.element.ElementPotion;
import api.praya.myitems.builder.potion.PotionProperties;
import com.praya.agarthalib.utility.CombatUtil;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.game.ElementConfig;
import com.praya.myitems.config.plugin.MainConfig;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.enums.main.SlotType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class ElementManager extends HandlerManager {
   private final ElementConfig elementConfig;

   protected ElementManager(MyItems plugin) {
      super(plugin);
      this.elementConfig = new ElementConfig(plugin);
   }

   public final ElementConfig getElementConfig() {
      return this.elementConfig;
   }

   public final Collection<String> getElements() {
      return this.getElementConfig().getElements();
   }

   public final Collection<Element> getElementBuilds() {
      return this.getElementConfig().getElementBuilds();
   }

   public final Element getElementBuild(String element) {
      return this.getElementConfig().getElementBuild(element);
   }

   public final String getTextElement(String element, double value) {
      MainConfig mainConfig = MainConfig.getInstance();
      HashMap<String, String> map = new HashMap();
      String format = mainConfig.getElementFormat();
      map.put("element", this.getKeyElement(element));
      map.put("value", this.getKeyValue(value));
      format = TextUtil.placeholder(map, format, "<", ">");
      return format;
   }

   public final String getElement(String lore) {
      MainConfig mainConfig = MainConfig.getInstance();
      String[] part = lore.split(MainConfig.KEY_ELEMENT);
      if (part.length > 1) {
         String loreElement = part[1].replaceAll(mainConfig.getElementColor(), "");
         Iterator var6 = this.getElementConfig().getElements().iterator();

         while(var6.hasNext()) {
            String key = (String)var6.next();
            if (this.getElementConfig().getElementBuild(key).getKeyLore().equalsIgnoreCase(loreElement)) {
               return key;
            }
         }
      }

      return null;
   }

   public final String getRawElement(String element) {
      Iterator var3 = this.getElementConfig().getElements().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(element)) {
            return key;
         }
      }

      return null;
   }

   public final boolean isExists(String element) {
      Iterator var3 = this.getElementConfig().getElements().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(element)) {
            return true;
         }
      }

      return false;
   }

   public final boolean isElement(String lore) {
      return this.getElement(lore) != null;
   }

   public final HashMap<String, Double> getMapElement(LivingEntity livingEntity) {
      return this.getMapElement(livingEntity, SlotType.UNIVERSAL);
   }

   public final HashMap<String, Double> getMapElement(LivingEntity livingEntity, SlotType slotType) {
      return this.getMapElement(livingEntity, slotType, true);
   }

   public final HashMap<String, Double> getMapElement(LivingEntity livingEntity, SlotType slotType, boolean checkDurability) {
      HashMap<String, Double> map = new HashMap();
      Slot[] var8;
      int var7 = (var8 = Slot.values()).length;

      for(int var6 = 0; var6 < var7; ++var6) {
         Slot slot = var8[var6];
         ItemStack item = Bridge.getBridgeEquipment().getEquipment(livingEntity, slot);
         if (EquipmentUtil.isSolid(item)) {
            Material itemMaterial = item.getType();
            SlotType itemSlotType = SlotType.getSlotType(itemMaterial);
            if (slotType.equals(SlotType.UNIVERSAL) || slotType.equals(itemSlotType)) {
               HashMap<String, Double> subMap = this.getMapElement(item, checkDurability);
               Iterator var14 = subMap.keySet().iterator();

               while(var14.hasNext()) {
                  String key = (String)var14.next();
                  double value = (Double)subMap.get(key);
                  map.put(key, map.containsKey(key) ? (Double)map.get(key) + value : value);
               }
            }
         }
      }

      return map;
   }

   public final HashMap<String, Double> getMapElement(ItemStack item, boolean checkDurability) {
      GameManager gameManager = this.plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      HashMap<String, Double> map = new HashMap();
      if (EquipmentUtil.loreCheck(item) && (!checkDurability || statsManager.checkDurability(item))) {
         Iterator var7 = this.getElementConfig().getElements().iterator();

         while(var7.hasNext()) {
            String key = (String)var7.next();
            double value = this.getElementValue(item, key);
            if (value != 0.0D) {
               map.put(key, value);
            }
         }
      }

      return map;
   }

   public final HashMap<String, Double> getElementCalculation(HashMap<String, Double> elementAttacker, HashMap<String, Double> elementVictims) {
      HashMap<String, Double> map = new HashMap();
      Iterator var5 = elementAttacker.keySet().iterator();

      while(true) {
         String key;
         do {
            if (!var5.hasNext()) {
               return map;
            }

            key = (String)var5.next();
         } while(!this.isExists(key));

         double valueAttack = (Double)elementAttacker.get(key);
         double minValue = MathUtil.negative(valueAttack);
         double resistance = 0.0D;
         Iterator var13 = elementVictims.keySet().iterator();

         while(var13.hasNext()) {
            String resist = (String)var13.next();
            if (this.isExists(resist)) {
               Element build = this.getElementBuild(resist);
               double scale = this.getScaleResistance(build, key);
               double valueDefense = (Double)elementVictims.get(resist);
               resistance += scale * valueDefense;
            }
         }

         double value = valueAttack - resistance;
         map.put(key, MathUtil.limitDouble(value, minValue, value));
      }
   }

   public final void applyElementPotion(LivingEntity attacker, LivingEntity victims, HashMap<String, Double> mapElement) {
      Iterator var5 = mapElement.keySet().iterator();

      while(true) {
         String key;
         double value;
         do {
            do {
               if (!var5.hasNext()) {
                  return;
               }

               key = (String)var5.next();
            } while(!this.isExists(key));

            value = (Double)mapElement.get(key);
         } while(!(value > 0.0D));

         ElementPotion build = this.getElementPotionBuild(key);
         Iterator var10 = build.getPotionAttacker().keySet().iterator();

         PotionEffectType potion;
         PotionProperties potionAttributes;
         double chance;
         int grade;
         int duration;
         while(var10.hasNext()) {
            potion = (PotionEffectType)var10.next();
            potionAttributes = (PotionProperties)build.getPotionAttacker().get(potion);
            chance = value * potionAttributes.getScaleChance();
            if (MathUtil.chanceOf(chance)) {
               grade = potionAttributes.getGrade();
               duration = (int)(value * potionAttributes.getScaleDuration());
               CombatUtil.applyPotion(attacker, potion, grade, duration);
            }
         }

         var10 = build.getPotionVictims().keySet().iterator();

         while(var10.hasNext()) {
            potion = (PotionEffectType)var10.next();
            potionAttributes = (PotionProperties)build.getPotionVictims().get(potion);
            chance = value * potionAttributes.getScaleChance();
            if (MathUtil.chanceOf(chance)) {
               grade = potionAttributes.getGrade();
               duration = (int)(value * potionAttributes.getScaleDuration());
               CombatUtil.applyPotion(victims, potion, grade, duration);
            }
         }
      }
   }

   public final ElementBoostStats getElementBoostStats(HashMap<String, Double> mapElement) {
      double baseAdditionalDamage = 0.0D;
      double basePercentDamage = 0.0D;
      Iterator var7 = mapElement.keySet().iterator();

      while(var7.hasNext()) {
         String key = (String)var7.next();
         if (this.isExists(key)) {
            double value = (Double)mapElement.get(key);
            ElementBoost build = this.getElementBoostBuild(key);
            baseAdditionalDamage += value * build.getScaleBaseAdditionalDamage();
            basePercentDamage += value * build.getScaleBasePercentDamage();
         }
      }

      return new ElementBoostStats(baseAdditionalDamage, basePercentDamage);
   }

   public final double getElementValue(ItemStack item, String element) {
      int line = this.getLineElement(item, element);
      return line != -1 ? this.getElementValue(item, line) : 0.0D;
   }

   public final double getElementValue(ItemStack item, int line) {
      String lore = (String)EquipmentUtil.getLores(item).get(line - 1);
      return this.getElementValue(lore);
   }

   public final double getElementValue(String lore) {
      MainConfig mainConfig = MainConfig.getInstance();
      String[] part = lore.split(MainConfig.KEY_ELEMENT_VALUE);
      if (part.length > 1) {
         String positiveValue = mainConfig.getStatsLorePositiveValue();
         String negativeValue = mainConfig.getStatsLoreNegativeValue();
         String textValue = part[1].replaceAll(positiveValue, "").replaceAll(negativeValue, "");
         if (MathUtil.isNumber(textValue)) {
            return MathUtil.parseDouble(textValue);
         }
      }

      return 0.0D;
   }

   public final double getScaleResistance(String baseElement, String targetElement) {
      Element elementBuild = this.getElementBuild(baseElement);
      return elementBuild != null ? this.getScaleResistance(elementBuild, targetElement) : 0.0D;
   }

   public final double getScaleResistance(Element elementBuild, String element) {
      Iterator var4 = elementBuild.getResistance().keySet().iterator();

      while(var4.hasNext()) {
         String key = (String)var4.next();
         if (key.equalsIgnoreCase(element)) {
            return (Double)elementBuild.getResistance().get(key);
         }
      }

      return 0.0D;
   }

   public final ElementBoost getElementBoostBuild(String element) {
      return this.isExists(element) ? this.getElementBuild(element).getBoostBuild() : null;
   }

   public final ElementPotion getElementPotionBuild(String element) {
      return this.isExists(element) ? this.getElementBuild(element).getPotionBuild() : null;
   }

   public final String getElementKeyLore(String element) {
      return this.isExists(element) ? this.getElementBuild(element).getKeyLore() : null;
   }

   public final int getLineElement(ItemStack item, String element) {
      return this.isExists(element) ? EquipmentUtil.loreGetLineKey(item, this.getKeyElement(element, true)) : -1;
   }

   private final String getKeyElement(String element) {
      return this.getKeyElement(element, false);
   }

   private final String getKeyElement(String element, boolean justCheck) {
      MainConfig mainConfig = MainConfig.getInstance();
      return justCheck ? MainConfig.KEY_ELEMENT + mainConfig.getElementColor() + this.getElementKeyLore(element) : MainConfig.KEY_ELEMENT + mainConfig.getElementColor() + this.getElementKeyLore(element) + MainConfig.KEY_ELEMENT + mainConfig.getElementColor();
   }

   private final String getKeyValue(double value) {
      MainConfig mainConfig = MainConfig.getInstance();
      return MainConfig.KEY_ELEMENT_VALUE + (value > 0.0D ? mainConfig.getStatsLorePositiveValue() : mainConfig.getStatsLoreNegativeValue()) + value + MainConfig.KEY_ELEMENT_VALUE + mainConfig.getElementColor();
   }
}
