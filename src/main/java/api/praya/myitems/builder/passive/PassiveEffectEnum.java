package api.praya.myitems.builder.passive;

import com.praya.myitems.config.plugin.MainConfig;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.bukkit.potion.PotionEffectType;

public enum PassiveEffectEnum {
   STRENGTH(10, PassiveTypeEnum.BUFF, Arrays.asList("Strength", "Attack", "Damage")),
   PROTECTION(10, PassiveTypeEnum.BUFF, Arrays.asList("Protection", "Defense")),
   VISION(10, PassiveTypeEnum.BUFF, Arrays.asList("Vision", "Night Vision", "NightVision", "Night_Vision")),
   JUMP(10, PassiveTypeEnum.BUFF, Arrays.asList("Jump", "Jump Boost", "JumpBoost", "Jump_Boost")),
   ABSORB(10, PassiveTypeEnum.BUFF, Arrays.asList("Absorb", "Absorbtion")),
   FIRE_RESISTANCE(10, PassiveTypeEnum.BUFF, Arrays.asList("Fire Resistance", "FireResistance", "Fire_Resistance", "Fire Resist", "FireResist", "Fire_Resist")),
   INVISIBILITY(10, PassiveTypeEnum.BUFF, Arrays.asList("Invisibility", "Invisible")),
   LUCK(10, PassiveTypeEnum.BUFF, Arrays.asList("Luck", "Fortune")),
   HEALTH_BOOST(10, PassiveTypeEnum.BUFF, Arrays.asList("Health Boost", "HealthBoost", "Health_Boost", "Health")),
   REGENERATION(10, PassiveTypeEnum.BUFF, Arrays.asList("Regeneration", "Regen")),
   SATURATION(10, PassiveTypeEnum.BUFF, Arrays.asList("Saturation")),
   SPEED(10, PassiveTypeEnum.BUFF, Arrays.asList("Speed", "Movement", "Move")),
   WATER_BREATHING(10, PassiveTypeEnum.BUFF, Arrays.asList("Water Breathing", "WaterBreathing", "Water_Breathing")),
   HASTE(4, PassiveTypeEnum.BUFF, Arrays.asList("Haste")),
   WEAK(10, PassiveTypeEnum.DEBUFF, Arrays.asList("Weak", "Weakness")),
   SLOW(5, PassiveTypeEnum.DEBUFF, Arrays.asList("Slow", "Slowness")),
   BLIND(10, PassiveTypeEnum.DEBUFF, Arrays.asList("Blind", "Blindness")),
   CONFUSE(10, PassiveTypeEnum.DEBUFF, Arrays.asList("Confuse", "Confusion")),
   STARVE(10, PassiveTypeEnum.DEBUFF, Arrays.asList("Hunger", "Hungry", "Starve")),
   TOXIC(10, PassiveTypeEnum.DEBUFF, Arrays.asList("Poison", "Poisonous", "Toxic")),
   GLOW(1, PassiveTypeEnum.DEBUFF, Arrays.asList("Glow", "Glowing")),
   FATIGUE(4, PassiveTypeEnum.DEBUFF, Arrays.asList("Tired", "Fatigue")),
   WITHER(10, PassiveTypeEnum.DEBUFF, Arrays.asList("Wither", "Dark", "Darkness")),
   UNLUCK(10, PassiveTypeEnum.DEBUFF, Arrays.asList("Unluck", "Deluck"));

   private final int maxGrade;
   private final PassiveTypeEnum type;
   private final List<String> aliases;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$api$praya$myitems$builder$passive$PassiveEffectEnum;

   private PassiveEffectEnum(int maxGrade, PassiveTypeEnum type, List<String> aliases) {
      this.maxGrade = maxGrade;
      this.type = type;
      this.aliases = aliases;
   }

   public final int getMaxGrade() {
      return this.maxGrade;
   }

   public final PassiveTypeEnum getType() {
      return this.type;
   }

   public final List<String> getAliases() {
      return this.aliases;
   }

   public final String getName() {
      return (String)this.getAliases().get(0);
   }

   public final String getText() {
      MainConfig mainConfig = MainConfig.getInstance();
      switch($SWITCH_TABLE$api$praya$myitems$builder$passive$PassiveEffectEnum()[this.ordinal()]) {
      case 1:
         return mainConfig.getPassiveBuffIdentifierStrength();
      case 2:
         return mainConfig.getPassiveBuffIdentifierProtection();
      case 3:
         return mainConfig.getPassiveBuffIdentifierVision();
      case 4:
         return mainConfig.getPassiveBuffIdentifierJump();
      case 5:
         return mainConfig.getPassiveBuffIdentifierAbsorb();
      case 6:
         return mainConfig.getPassiveBuffIdentifierFireResistance();
      case 7:
         return mainConfig.getPassiveBuffIdentifierInvisibility();
      case 8:
         return mainConfig.getPassiveBuffIdentifierLuck();
      case 9:
         return mainConfig.getPassiveBuffIdentifierHealthBoost();
      case 10:
         return mainConfig.getPassiveBuffIdentifierRegeneration();
      case 11:
         return mainConfig.getPassiveBuffIdentifierSaturation();
      case 12:
         return mainConfig.getPassiveBuffIdentifierSpeed();
      case 13:
         return mainConfig.getPassiveBuffIdentifierWaterBreathing();
      case 14:
         return mainConfig.getPassiveBuffIdentifierHaste();
      case 15:
         return mainConfig.getPassiveDebuffIdentifierWeak();
      case 16:
         return mainConfig.getPassiveDebuffIdentifierSlow();
      case 17:
         return mainConfig.getPassiveDebuffIdentifierBlind();
      case 18:
         return mainConfig.getPassiveDebuffIdentifierConfuse();
      case 19:
         return mainConfig.getPassiveDebuffIdentifierStarve();
      case 20:
         return mainConfig.getPassiveDebuffIdentifierToxic();
      case 21:
         return mainConfig.getPassiveDebuffIdentifierGlow();
      case 22:
         return mainConfig.getPassiveDebuffIdentifierFatigue();
      case 23:
         return mainConfig.getPassiveDebuffIdentifierWither();
      case 24:
         return mainConfig.getPassiveDebuffIdentifierUnluck();
      default:
         return null;
      }
   }

   public final PotionEffectType getPotion() {
      switch($SWITCH_TABLE$api$praya$myitems$builder$passive$PassiveEffectEnum()[this.ordinal()]) {
      case 1:
         return PotionEffectType.INCREASE_DAMAGE;
      case 2:
         return PotionEffectType.DAMAGE_RESISTANCE;
      case 3:
         return PotionEffectType.NIGHT_VISION;
      case 4:
         return PotionEffectType.JUMP;
      case 5:
         return PotionEffectType.ABSORPTION;
      case 6:
         return PotionEffectType.FIRE_RESISTANCE;
      case 7:
         return PotionEffectType.INVISIBILITY;
      case 8:
         return PotionEffectType.LUCK;
      case 9:
         return PotionEffectType.HEALTH_BOOST;
      case 10:
         return PotionEffectType.REGENERATION;
      case 11:
         return PotionEffectType.SATURATION;
      case 12:
         return PotionEffectType.SPEED;
      case 13:
         return PotionEffectType.WATER_BREATHING;
      case 14:
         return PotionEffectType.FAST_DIGGING;
      case 15:
         return PotionEffectType.WEAKNESS;
      case 16:
         return PotionEffectType.SLOW;
      case 17:
         return PotionEffectType.BLINDNESS;
      case 18:
         return PotionEffectType.CONFUSION;
      case 19:
         return PotionEffectType.HUNGER;
      case 20:
         return PotionEffectType.POISON;
      case 21:
         return PotionEffectType.GLOWING;
      case 22:
         return PotionEffectType.SLOW_DIGGING;
      case 23:
         return PotionEffectType.WITHER;
      case 24:
         return PotionEffectType.UNLUCK;
      default:
         return null;
      }
   }

   public static final PassiveEffectEnum get(String buff) {
      PassiveEffectEnum[] var4;
      int var3 = (var4 = values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         PassiveEffectEnum key = var4[var2];
         Iterator var6 = key.getAliases().iterator();

         while(var6.hasNext()) {
            String aliases = (String)var6.next();
            if (aliases.equalsIgnoreCase(buff)) {
               return key;
            }
         }
      }

      return null;
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$api$praya$myitems$builder$passive$PassiveEffectEnum() {
      int[] var10000 = $SWITCH_TABLE$api$praya$myitems$builder$passive$PassiveEffectEnum;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[values().length];

         try {
            var0[ABSORB.ordinal()] = 5;
         } catch (NoSuchFieldError var24) {
         }

         try {
            var0[BLIND.ordinal()] = 17;
         } catch (NoSuchFieldError var23) {
         }

         try {
            var0[CONFUSE.ordinal()] = 18;
         } catch (NoSuchFieldError var22) {
         }

         try {
            var0[FATIGUE.ordinal()] = 22;
         } catch (NoSuchFieldError var21) {
         }

         try {
            var0[FIRE_RESISTANCE.ordinal()] = 6;
         } catch (NoSuchFieldError var20) {
         }

         try {
            var0[GLOW.ordinal()] = 21;
         } catch (NoSuchFieldError var19) {
         }

         try {
            var0[HASTE.ordinal()] = 14;
         } catch (NoSuchFieldError var18) {
         }

         try {
            var0[HEALTH_BOOST.ordinal()] = 9;
         } catch (NoSuchFieldError var17) {
         }

         try {
            var0[INVISIBILITY.ordinal()] = 7;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[JUMP.ordinal()] = 4;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[LUCK.ordinal()] = 8;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[PROTECTION.ordinal()] = 2;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[REGENERATION.ordinal()] = 10;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[SATURATION.ordinal()] = 11;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[SLOW.ordinal()] = 16;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[SPEED.ordinal()] = 12;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[STARVE.ordinal()] = 19;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[STRENGTH.ordinal()] = 1;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[TOXIC.ordinal()] = 20;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[UNLUCK.ordinal()] = 24;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[VISION.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[WATER_BREATHING.ordinal()] = 13;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[WEAK.ordinal()] = 15;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[WITHER.ordinal()] = 23;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$api$praya$myitems$builder$passive$PassiveEffectEnum = var0;
         return var0;
      }
   }
}
