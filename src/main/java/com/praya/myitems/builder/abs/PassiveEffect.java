package com.praya.myitems.builder.abs;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.myitems.builder.passive.buff.BuffAbsorb;
import com.praya.myitems.builder.passive.buff.BuffFireResistance;
import com.praya.myitems.builder.passive.buff.BuffHaste;
import com.praya.myitems.builder.passive.buff.BuffHealthBoost;
import com.praya.myitems.builder.passive.buff.BuffInvisibility;
import com.praya.myitems.builder.passive.buff.BuffJump;
import com.praya.myitems.builder.passive.buff.BuffLuck;
import com.praya.myitems.builder.passive.buff.BuffProtection;
import com.praya.myitems.builder.passive.buff.BuffRegeneration;
import com.praya.myitems.builder.passive.buff.BuffSaturation;
import com.praya.myitems.builder.passive.buff.BuffSpeed;
import com.praya.myitems.builder.passive.buff.BuffStrength;
import com.praya.myitems.builder.passive.buff.BuffVision;
import com.praya.myitems.builder.passive.buff.BuffWaterBreathing;
import com.praya.myitems.builder.passive.debuff.DebuffBlind;
import com.praya.myitems.builder.passive.debuff.DebuffConfuse;
import com.praya.myitems.builder.passive.debuff.DebuffFatigue;
import com.praya.myitems.builder.passive.debuff.DebuffGlow;
import com.praya.myitems.builder.passive.debuff.DebuffSlow;
import com.praya.myitems.builder.passive.debuff.DebuffStarve;
import com.praya.myitems.builder.passive.debuff.DebuffToxic;
import com.praya.myitems.builder.passive.debuff.DebuffUnluck;
import com.praya.myitems.builder.passive.debuff.DebuffWeak;
import com.praya.myitems.builder.passive.debuff.DebuffWither;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public abstract class PassiveEffect {
   protected final PassiveEffectEnum buffEnum;
   protected final int grade;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$api$praya$myitems$builder$passive$PassiveEffectEnum;

   public PassiveEffect(PassiveEffectEnum buffEnum, int grade) {
      this.buffEnum = buffEnum;
      this.grade = grade;
   }

   public abstract void cast(Player var1);

   public final PassiveEffectEnum getPassiveEffectEnum() {
      return this.buffEnum;
   }

   public final int getGrade() {
      return this.grade;
   }

   public final PotionEffectType getPotion() {
      return this.buffEnum.getPotion();
   }

   public static final PassiveEffect getPassiveEffect(PassiveEffectEnum buffEnum, int grade) {
      switch($SWITCH_TABLE$api$praya$myitems$builder$passive$PassiveEffectEnum()[buffEnum.ordinal()]) {
      case 1:
         return new BuffStrength(grade);
      case 2:
         return new BuffProtection(grade);
      case 3:
         return new BuffVision(grade);
      case 4:
         return new BuffJump(grade);
      case 5:
         return new BuffAbsorb(grade);
      case 6:
         return new BuffFireResistance(grade);
      case 7:
         return new BuffInvisibility(grade);
      case 8:
         return new BuffLuck(grade);
      case 9:
         return new BuffHealthBoost(grade);
      case 10:
         return new BuffRegeneration(grade);
      case 11:
         return new BuffSaturation(grade);
      case 12:
         return new BuffSpeed(grade);
      case 13:
         return new BuffWaterBreathing(grade);
      case 14:
         return new BuffHaste(grade);
      case 15:
         return new DebuffWeak(grade);
      case 16:
         return new DebuffSlow(grade);
      case 17:
         return new DebuffBlind(grade);
      case 18:
         return new DebuffConfuse(grade);
      case 19:
         return new DebuffStarve(grade);
      case 20:
         return new DebuffToxic(grade);
      case 21:
         return new DebuffGlow(grade);
      case 22:
         return new DebuffFatigue(grade);
      case 23:
         return new DebuffWither(grade);
      case 24:
         return new DebuffUnluck(grade);
      default:
         return null;
      }
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$api$praya$myitems$builder$passive$PassiveEffectEnum() {
      int[] var10000 = $SWITCH_TABLE$api$praya$myitems$builder$passive$PassiveEffectEnum;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[PassiveEffectEnum.values().length];

         try {
            var0[PassiveEffectEnum.ABSORB.ordinal()] = 5;
         } catch (NoSuchFieldError var24) {
         }

         try {
            var0[PassiveEffectEnum.BLIND.ordinal()] = 17;
         } catch (NoSuchFieldError var23) {
         }

         try {
            var0[PassiveEffectEnum.CONFUSE.ordinal()] = 18;
         } catch (NoSuchFieldError var22) {
         }

         try {
            var0[PassiveEffectEnum.FATIGUE.ordinal()] = 22;
         } catch (NoSuchFieldError var21) {
         }

         try {
            var0[PassiveEffectEnum.FIRE_RESISTANCE.ordinal()] = 6;
         } catch (NoSuchFieldError var20) {
         }

         try {
            var0[PassiveEffectEnum.GLOW.ordinal()] = 21;
         } catch (NoSuchFieldError var19) {
         }

         try {
            var0[PassiveEffectEnum.HASTE.ordinal()] = 14;
         } catch (NoSuchFieldError var18) {
         }

         try {
            var0[PassiveEffectEnum.HEALTH_BOOST.ordinal()] = 9;
         } catch (NoSuchFieldError var17) {
         }

         try {
            var0[PassiveEffectEnum.INVISIBILITY.ordinal()] = 7;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[PassiveEffectEnum.JUMP.ordinal()] = 4;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[PassiveEffectEnum.LUCK.ordinal()] = 8;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[PassiveEffectEnum.PROTECTION.ordinal()] = 2;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[PassiveEffectEnum.REGENERATION.ordinal()] = 10;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[PassiveEffectEnum.SATURATION.ordinal()] = 11;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[PassiveEffectEnum.SLOW.ordinal()] = 16;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[PassiveEffectEnum.SPEED.ordinal()] = 12;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[PassiveEffectEnum.STARVE.ordinal()] = 19;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[PassiveEffectEnum.STRENGTH.ordinal()] = 1;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[PassiveEffectEnum.TOXIC.ordinal()] = 20;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[PassiveEffectEnum.UNLUCK.ordinal()] = 24;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[PassiveEffectEnum.VISION.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[PassiveEffectEnum.WATER_BREATHING.ordinal()] = 13;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[PassiveEffectEnum.WEAK.ordinal()] = 15;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[PassiveEffectEnum.WITHER.ordinal()] = 23;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$api$praya$myitems$builder$passive$PassiveEffectEnum = var0;
         return var0;
      }
   }
}
