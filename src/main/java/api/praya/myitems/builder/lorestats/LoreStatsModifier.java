package api.praya.myitems.builder.lorestats;

public class LoreStatsModifier {
   private final LoreStatsWeapon weaponModifier;
   private final LoreStatsArmor armorModifier;
   private final LoreStatsUniversal universalModifier;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$api$praya$myitems$builder$lorestats$LoreStatsEnum;

   public LoreStatsModifier() {
      this.weaponModifier = new LoreStatsWeapon(1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D);
      this.armorModifier = new LoreStatsArmor(1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D);
      this.universalModifier = new LoreStatsUniversal(1.0D, 1.0D);
   }

   public LoreStatsModifier(LoreStatsWeapon weaponModifier, LoreStatsArmor armorModifier, LoreStatsUniversal universalModifier) {
      this.weaponModifier = weaponModifier;
      this.armorModifier = armorModifier;
      this.universalModifier = universalModifier;
   }

   public final LoreStatsWeapon getWeaponModifier() {
      return this.weaponModifier;
   }

   public final LoreStatsArmor getArmorModifier() {
      return this.armorModifier;
   }

   public final LoreStatsUniversal getUniversalModifier() {
      return this.universalModifier;
   }

   public final double getModifier(LoreStatsEnum loreStats) {
      switch($SWITCH_TABLE$api$praya$myitems$builder$lorestats$LoreStatsEnum()[loreStats.ordinal()]) {
      case 1:
         return this.weaponModifier.getDamage();
      case 2:
         return this.weaponModifier.getPenetration();
      case 3:
         return this.weaponModifier.getPvPDamage();
      case 4:
         return this.weaponModifier.getPvEDamage();
      case 5:
         return this.armorModifier.getDefense();
      case 6:
         return this.armorModifier.getPvPDefense();
      case 7:
         return this.armorModifier.getPvEDefense();
      case 8:
         return this.armorModifier.getHealth();
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      default:
         return 1.0D;
      case 14:
         return this.weaponModifier.getCriticalChance();
      case 15:
         return this.weaponModifier.getCriticalDamage();
      case 16:
         return this.armorModifier.getBlockAmount();
      case 17:
         return this.armorModifier.getBlockRate();
      case 18:
         return this.weaponModifier.getHitRate();
      case 19:
         return this.armorModifier.getDodgeRate();
      case 25:
         return this.universalModifier.getDurability();
      case 26:
         return this.universalModifier.getLeveL();
      }
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$api$praya$myitems$builder$lorestats$LoreStatsEnum() {
      int[] var10000 = $SWITCH_TABLE$api$praya$myitems$builder$lorestats$LoreStatsEnum;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[LoreStatsEnum.values().length];

         try {
            var0[LoreStatsEnum.ATTACK_AOE_DAMAGE.ordinal()] = 13;
         } catch (NoSuchFieldError var26) {
         }

         try {
            var0[LoreStatsEnum.ATTACK_AOE_RADIUS.ordinal()] = 12;
         } catch (NoSuchFieldError var25) {
         }

         try {
            var0[LoreStatsEnum.BLOCK_AMOUNT.ordinal()] = 16;
         } catch (NoSuchFieldError var24) {
         }

         try {
            var0[LoreStatsEnum.BLOCK_RATE.ordinal()] = 17;
         } catch (NoSuchFieldError var23) {
         }

         try {
            var0[LoreStatsEnum.CRITICAL_CHANCE.ordinal()] = 14;
         } catch (NoSuchFieldError var22) {
         }

         try {
            var0[LoreStatsEnum.CRITICAL_DAMAGE.ordinal()] = 15;
         } catch (NoSuchFieldError var21) {
         }

         try {
            var0[LoreStatsEnum.DAMAGE.ordinal()] = 1;
         } catch (NoSuchFieldError var20) {
         }

         try {
            var0[LoreStatsEnum.DEFENSE.ordinal()] = 5;
         } catch (NoSuchFieldError var19) {
         }

         try {
            var0[LoreStatsEnum.DODGE_RATE.ordinal()] = 19;
         } catch (NoSuchFieldError var18) {
         }

         try {
            var0[LoreStatsEnum.DURABILITY.ordinal()] = 25;
         } catch (NoSuchFieldError var17) {
         }

         try {
            var0[LoreStatsEnum.FISHING_CHANCE.ordinal()] = 20;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[LoreStatsEnum.FISHING_POWER.ordinal()] = 21;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[LoreStatsEnum.FISHING_SPEED.ordinal()] = 22;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[LoreStatsEnum.HEALTH.ordinal()] = 8;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[LoreStatsEnum.HEALTH_REGEN.ordinal()] = 9;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[LoreStatsEnum.HIT_RATE.ordinal()] = 18;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[LoreStatsEnum.LEVEL.ordinal()] = 26;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[LoreStatsEnum.LURES_ENDURANCE.ordinal()] = 24;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[LoreStatsEnum.LURES_MAX_TENSION.ordinal()] = 23;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[LoreStatsEnum.PENETRATION.ordinal()] = 2;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[LoreStatsEnum.PVE_DAMAGE.ordinal()] = 4;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[LoreStatsEnum.PVE_DEFENSE.ordinal()] = 7;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[LoreStatsEnum.PVP_DAMAGE.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[LoreStatsEnum.PVP_DEFENSE.ordinal()] = 6;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[LoreStatsEnum.STAMINA_MAX.ordinal()] = 10;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[LoreStatsEnum.STAMINA_REGEN.ordinal()] = 11;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$api$praya$myitems$builder$lorestats$LoreStatsEnum = var0;
         return var0;
      }
   }
}
