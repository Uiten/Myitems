package com.praya.myitems.utility.main;

import com.praya.myitems.config.plugin.MainConfig;
import core.praya.agarthalib.enums.branch.ProjectileEnum;

public class ProjectileUtil {
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$core$praya$agarthalib$enums$branch$ProjectileEnum;

   public static final String getText(ProjectileEnum projectile) {
      MainConfig mainConfig = MainConfig.getInstance();
      switch($SWITCH_TABLE$core$praya$agarthalib$enums$branch$ProjectileEnum()[projectile.ordinal()]) {
      case 1:
         return mainConfig.getPowerProjectileIdentifierArrow();
      case 2:
         return mainConfig.getPowerProjectileIdentifierSnowBall();
      case 3:
         return mainConfig.getPowerProjectileIdentifierEgg();
      case 4:
         return mainConfig.getPowerProjectileIdentifierFlameArrow();
      case 5:
         return mainConfig.getPowerProjectileIdentifierFlameBall();
      case 6:
         return mainConfig.getPowerProjectileIdentifierFlameEgg();
      case 7:
         return mainConfig.getPowerProjectileIdentifierSmallFireball();
      case 8:
         return mainConfig.getPowerProjectileIdentifierLargeFireball();
      case 9:
         return mainConfig.getPowerProjectileIdentifierWitherSkull();
      default:
         return null;
      }
   }

   public static final ProjectileEnum getProjectileByLore(String lore) {
      ProjectileEnum[] var4;
      int var3 = (var4 = ProjectileEnum.values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         ProjectileEnum projectile = var4[var2];
         if (getText(projectile).equalsIgnoreCase(lore)) {
            return projectile;
         }
      }

      return null;
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$core$praya$agarthalib$enums$branch$ProjectileEnum() {
      int[] var10000 = $SWITCH_TABLE$core$praya$agarthalib$enums$branch$ProjectileEnum;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[ProjectileEnum.values().length];

         try {
            var0[ProjectileEnum.ARROW.ordinal()] = 1;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[ProjectileEnum.EGG.ordinal()] = 3;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[ProjectileEnum.FLAME_ARROW.ordinal()] = 4;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[ProjectileEnum.FLAME_BALL.ordinal()] = 5;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[ProjectileEnum.FLAME_EGG.ordinal()] = 6;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[ProjectileEnum.LARGE_FIREBALL.ordinal()] = 8;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[ProjectileEnum.SMALL_FIREBALL.ordinal()] = 7;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[ProjectileEnum.SNOWBALL.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[ProjectileEnum.WITHER_SKULL.ordinal()] = 9;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$core$praya$agarthalib$enums$branch$ProjectileEnum = var0;
         return var0;
      }
   }
}
