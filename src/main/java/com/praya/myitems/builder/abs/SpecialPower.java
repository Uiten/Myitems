package com.praya.myitems.builder.abs;

import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.myitems.builder.specialpower.SpecialPowerAmaterasu;
import com.praya.myitems.builder.specialpower.SpecialPowerBlink;
import com.praya.myitems.builder.specialpower.SpecialPowerFissure;
import com.praya.myitems.builder.specialpower.SpecialPowerIceSpikes;
import com.praya.myitems.builder.specialpower.SpecialPowerNeroBeam;
import org.bukkit.entity.LivingEntity;

public abstract class SpecialPower {
   protected final PowerSpecialEnum specialEnum;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$api$praya$myitems$builder$power$PowerSpecialEnum;

   public SpecialPower(PowerSpecialEnum specialEnum) {
      this.specialEnum = specialEnum;
   }

   public abstract void cast(LivingEntity var1);

   public final PowerSpecialEnum getSpecialEnum() {
      return this.specialEnum;
   }

   public static final SpecialPower getSpecial(PowerSpecialEnum specialEnum) {
      switch($SWITCH_TABLE$api$praya$myitems$builder$power$PowerSpecialEnum()[specialEnum.ordinal()]) {
      case 1:
         return new SpecialPowerBlink();
      case 2:
         return new SpecialPowerFissure();
      case 3:
         return new SpecialPowerIceSpikes();
      case 4:
         return new SpecialPowerAmaterasu();
      case 5:
         return new SpecialPowerNeroBeam();
      default:
         return null;
      }
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$api$praya$myitems$builder$power$PowerSpecialEnum() {
      int[] var10000 = $SWITCH_TABLE$api$praya$myitems$builder$power$PowerSpecialEnum;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[PowerSpecialEnum.values().length];

         try {
            var0[PowerSpecialEnum.AMATERASU.ordinal()] = 4;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[PowerSpecialEnum.BLINK.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[PowerSpecialEnum.FISSURE.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[PowerSpecialEnum.ICE_SPIKES.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[PowerSpecialEnum.NERO_BEAM.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$api$praya$myitems$builder$power$PowerSpecialEnum = var0;
         return var0;
      }
   }
}
