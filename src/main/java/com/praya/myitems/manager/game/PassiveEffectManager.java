package com.praya.myitems.manager.game;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import api.praya.myitems.builder.passive.PassiveTypeEnum;
import api.praya.myitems.builder.player.PlayerPassiveEffectCooldown;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.ServerUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.abs.PassiveEffect;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.builder.passive.buff.BuffHealthBoost;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.player.PlayerPassiveEffectManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.RomanNumber;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.enums.main.VersionNMS;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class PassiveEffectManager extends HandlerManager {
   protected PassiveEffectManager(MyItems plugin) {
      super(plugin);
   }

   public final String getTextPassiveEffect(PassiveEffectEnum effect, int grade) {
      MainConfig mainConfig = MainConfig.getInstance();
      HashMap<String, String> map = new HashMap();
      String format = effect.getType().equals(PassiveTypeEnum.BUFF) ? mainConfig.getPassiveBuffFormat() : mainConfig.getPassiveDebuffFormat();
      map.put("buff", this.getKeyPassiveEffect(effect, grade));
      map.put("buffs", this.getKeyPassiveEffect(effect, grade));
      map.put("debuff", this.getKeyPassiveEffect(effect, grade));
      map.put("debuffs", this.getKeyPassiveEffect(effect, grade));
      format = TextUtil.placeholder(map, format, "<", ">");
      return format;
   }

   public final int getHighestGradePassiveEffect(PassiveEffectEnum effect, LivingEntity livingEntity) {
      int grade = 0;
      Slot[] var7;
      int var6 = (var7 = Slot.values()).length;

      for(int var5 = 0; var5 < var6; ++var5) {
         Slot slot = var7[var5];
         if (this.checkAllowedSlot(slot)) {
            ItemStack item = Bridge.getBridgeEquipment().getEquipment(livingEntity, slot);
            if (EquipmentUtil.loreCheck(item)) {
               int passiveEffectGrade = this.passiveEffectGrade(item, effect);
               if (passiveEffectGrade > grade) {
                  grade = passiveEffectGrade;
               }
            }
         }
      }

      return grade;
   }

   public final int getTotalGradePassiveEffect(PassiveEffectEnum effect, LivingEntity livingEntity) {
      int grade = 0;
      Slot[] var7;
      int var6 = (var7 = Slot.values()).length;

      for(int var5 = 0; var5 < var6; ++var5) {
         Slot slot = var7[var5];
         if (this.checkAllowedSlot(slot)) {
            ItemStack item = Bridge.getBridgeEquipment().getEquipment(livingEntity, slot);
            if (EquipmentUtil.loreCheck(item)) {
               grade += this.passiveEffectGrade(item, effect);
            }
         }
      }

      return grade;
   }

   public final PassiveEffectEnum getPassiveEffect(String lore) {
      PassiveEffectEnum[] var5;
      int var4 = (var5 = PassiveEffectEnum.values()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         PassiveEffectEnum passiveEffect = var5[var3];
         if (lore.contains(this.getKeyPassiveEffect(passiveEffect, true))) {
            return passiveEffect;
         }
      }

      return null;
   }

   public final PassiveEffectEnum getPassiveEffect(ItemStack item, int line) {
      if (line > 0 && EquipmentUtil.hasLore(item) && line <= EquipmentUtil.getLores(item).size()) {
         String lore = (String)EquipmentUtil.getLores(item).get(line - 1);
         return this.getPassiveEffect(lore);
      } else {
         return null;
      }
   }

   public final boolean isPassiveEffect(String lore) {
      return this.getPassiveEffect(lore) != null;
   }

   public final boolean isPassiveEffect(ItemStack item, int line) {
      return this.getPassiveEffect(item, line) != null;
   }

   public final int passiveEffectGrade(LivingEntity livingEntity, PassiveEffectEnum effect, Slot slot) {
      return this.passiveEffectGrade(Bridge.getBridgeEquipment().getEquipment(livingEntity, slot), effect);
   }

   public final int passiveEffectGrade(ItemStack item, PassiveEffectEnum effect) {
      int line = this.getLinePassiveEffect(item, effect);
      return line != -1 ? this.passiveEffectGrade(item, effect, line) : 0;
   }

   public final int passiveEffectGrade(ItemStack item, PassiveEffectEnum effect, int line) {
      return this.passiveEffectGrade(effect, (String)EquipmentUtil.getLores(item).get(line - 1));
   }

   public final int passiveEffectGrade(PassiveEffectEnum effect, String lore) {
      MainConfig mainConfig = MainConfig.getInstance();
      String[] textListValue = lore.split(MainConfig.KEY_PASSIVE_EFFECT);
      if (textListValue.length > 1) {
         String regex = (effect.getType().equals(PassiveTypeEnum.BUFF) ? mainConfig.getPassiveBuffColor() : mainConfig.getPassiveDebuffColor()) + effect.getText() + " ";
         String textValue = textListValue[1];
         if (textValue.contains(regex)) {
            textValue = textValue.replaceAll(regex, "");
            return RomanNumber.romanConvert(textValue);
         }
      }

      return 0;
   }

   public final int getLinePassiveEffect(ItemStack item, PassiveEffectEnum effect) {
      return EquipmentUtil.loreGetLineKey(item, this.getKeyPassiveEffect(effect, true));
   }

   public Collection<PassiveEffectEnum> getPassiveEffects(ItemStack item) {
      Collection<PassiveEffectEnum> listEffect = new ArrayList();
      if (EquipmentUtil.loreCheck(item)) {
         PassiveEffectEnum[] var6;
         int var5 = (var6 = PassiveEffectEnum.values()).length;

         for(int var4 = 0; var4 < var5; ++var4) {
            PassiveEffectEnum effect = var6[var4];
            int line = this.getLinePassiveEffect(item, effect);
            if (line != -1) {
               listEffect.add(effect);
            }
         }
      }

      return listEffect;
   }

   public final void setPassiveEffectCooldown(PassiveEffectEnum effect, OfflinePlayer player, long cooldown) {
      PlayerPassiveEffectManager playerPassiveEffectManager = this.plugin.getPlayerManager().getPlayerPassiveEffectManager();
      PlayerPassiveEffectCooldown playerPassiveEffectCooldown = playerPassiveEffectManager.getPlayerPassiveEffectCooldown(player);
      playerPassiveEffectCooldown.setPassiveEffectCooldown(effect, cooldown);
   }

   public final boolean isPassiveEffectCooldown(PassiveEffectEnum effect, OfflinePlayer player) {
      PlayerPassiveEffectManager playerPassiveEffectManager = this.plugin.getPlayerManager().getPlayerPassiveEffectManager();
      PlayerPassiveEffectCooldown playerPassiveEffectCooldown = playerPassiveEffectManager.getPlayerPassiveEffectCooldown(player);
      return playerPassiveEffectCooldown.isPassiveEffectCooldown(effect);
   }

   public final void reloadPassiveEffect(Player player, ItemStack item, boolean sum) {
      this.reloadPassiveEffect(player, this.getPassiveEffects(item), sum);
   }

   public final void reloadPassiveEffect(Player player, Collection<PassiveEffectEnum> effects, boolean sum) {
      Iterator var5 = effects.iterator();

      while(var5.hasNext()) {
         PassiveEffectEnum effect = (PassiveEffectEnum)var5.next();
         this.runPassiveEffect(player, effect, true, sum);
      }

   }

   public final void loadPassiveEffect(boolean sum) {
      Iterator var3 = PlayerUtil.getOnlinePlayers().iterator();

      while(var3.hasNext()) {
         Player player = (Player)var3.next();
         this.runAllPassiveEffect(player, sum);
      }

   }

   public final void runAllPassiveEffect(Player player, boolean sum) {
      this.runAllPassiveEffect(player, false, sum);
   }

   public final void runAllPassiveEffect(Player player, boolean reset, boolean sum) {
      PassiveEffectEnum[] var7;
      int var6 = (var7 = PassiveEffectEnum.values()).length;

      for(int var5 = 0; var5 < var6; ++var5) {
         PassiveEffectEnum effect = var7[var5];
         this.runPassiveEffect(player, effect, reset, sum);
      }

   }

   public final void runPassiveEffect(Player player, PassiveEffectEnum effect, boolean sum) {
      this.runPassiveEffect(player, effect, false, sum);
   }

   public final void runPassiveEffect(Player player, PassiveEffectEnum effect, boolean reset, boolean sum) {
      int grade = sum ? this.getTotalGradePassiveEffect(effect, player) : this.getHighestGradePassiveEffect(effect, player);
      this.applyPassiveEffect(player, effect, grade, reset);
   }

   public final void applyPassiveEffect(Player player, PassiveEffectEnum effect, int grade) {
      this.applyPassiveEffect(player, effect, grade, false);
   }

   public final void applyPassiveEffect(Player player, PassiveEffectEnum effect, int grade, boolean reset) {
      if (ServerUtil.isCompatible(VersionNMS.V1_9_R1) || !effect.equals(PassiveEffectEnum.LUCK)) {
         if (reset) {
            PotionEffectType potion = effect.getPotion();
            if (potion != null) {
               if (potion.equals(PotionEffectType.HEALTH_BOOST)) {
                  BuffHealthBoost buffHealth = new BuffHealthBoost();
                  buffHealth.reset(player);
               } else {
                  player.removePotionEffect(potion);
               }
            }
         }

         grade = MathUtil.limitInteger(grade, grade, effect.getMaxGrade());
         if (grade != 0) {
            PassiveEffect passiveEffect = PassiveEffect.getPassiveEffect(effect, grade);
            passiveEffect.cast(player);
         }

      }
   }

   public final String getKeyPassiveEffect(PassiveEffectEnum effect, boolean justCheck) {
      return this.getKeyPassiveEffect(effect, 1, justCheck);
   }

   public final String getKeyPassiveEffect(PassiveEffectEnum effect, int grade) {
      return this.getKeyPassiveEffect(effect, grade, false);
   }

   public final String getKeyPassiveEffect(PassiveEffectEnum effect, int grade, boolean justCheck) {
      MainConfig mainConfig = MainConfig.getInstance();
      String key = MainConfig.KEY_PASSIVE_EFFECT;
      String color = effect.getType().equals(PassiveTypeEnum.BUFF) ? mainConfig.getPassiveBuffColor() : mainConfig.getPassiveDebuffColor();
      String text = effect.getText();
      String roman = RomanNumber.getRomanNumber(grade);
      return justCheck ? key + color + text : key + color + text + " " + roman + key + color;
   }

   public final boolean checkAllowedSlot(Slot slot) {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.isPassiveEnableHand() ? true : !slot.equals(Slot.MAINHAND) && !slot.equals(Slot.OFFHAND);
   }
}
