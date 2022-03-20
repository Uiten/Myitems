package api.praya.myitems.builder.lorestats;

import com.praya.agarthalib.utility.PluginUtil;
import com.praya.myitems.config.plugin.MainConfig;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public enum LoreStatsEnum {
   DAMAGE((String)null, Arrays.asList("Damage", "Attack", "Physical Attack", "PhysicalAttack", "Physical_Attack")),
   PENETRATION((String)null, Arrays.asList("Penetration", "Pentrate", "Pierce")),
   PVP_DAMAGE((String)null, Arrays.asList("PvP Damage", "PvP_Damage", "PvPDamage")),
   PVE_DAMAGE((String)null, Arrays.asList("PvE Damage", "PvE_Damage", "PvEDamage")),
   DEFENSE((String)null, Arrays.asList("Defense", "Armor", "Protection", "Physical Defense", "PhysicalDefense", "Physical_Defense")),
   PVP_DEFENSE((String)null, Arrays.asList("PvP Defense", "PvP_Defense", "PvPDefense")),
   PVE_DEFENSE((String)null, Arrays.asList("PvE Defense", "PvE_Defense", "PvEDefense")),
   HEALTH((String)null, Arrays.asList("Health", "Hearth", "HP", "Life")),
   HEALTH_REGEN("LifeEssence", Arrays.asList("Health Regen", "Health_Regen", "HealthRegen", "Regen", "Regeneration")),
   STAMINA_MAX("CombatStamina", Arrays.asList("Stamina Max", "Stamina_Max", "StaminaMax", "Max Stamina", "Max_Stamina", "Max_Stamina")),
   STAMINA_REGEN("CombatStamina", Arrays.asList("Stamina Regen", "Stamina_Regen", "StaminaRegen", "Regen Stamina", "Regen_Stamina", "Regen_Stamina")),
   ATTACK_AOE_RADIUS((String)null, Arrays.asList("Attack AoE Radius", "Attack_AoE_Radius", "AoE Radius", "AoE_Radius")),
   ATTACK_AOE_DAMAGE((String)null, Arrays.asList("Attack AoE Damage", "Attack_AoE_Damage", "AoE Damage", "AoE_Damage")),
   CRITICAL_CHANCE((String)null, Arrays.asList("Critical Chance", "CriticalChance", "Critical_Chance", "CC", "CritChance")),
   CRITICAL_DAMAGE((String)null, Arrays.asList("Critical Damage", "CriticalDamage", "Critical_Damage", "CD", "CritDamage")),
   BLOCK_AMOUNT((String)null, Arrays.asList("Block Amount", "BlockAmount", "Block_Amount")),
   BLOCK_RATE((String)null, Arrays.asList("Block Rate", "BlockRate", "Block_Rate", "Block")),
   HIT_RATE((String)null, Arrays.asList("Hit Rate", "HitRate", "Hit_Rate", "Hit", "Accuration", "Accuracy")),
   DODGE_RATE((String)null, Arrays.asList("Dodge Rate", "DodgeRate", "Dodge_Rate", "Dodge")),
   FISHING_CHANCE("DreamFish", Arrays.asList("Fishing Chance", "FishingChance", "Fishing_Chance", "Fishing Rate", "FishingRate", "Fishing_Rate")),
   FISHING_POWER("DreamFish", Arrays.asList("Fishing Power", "FishingPower", "Fishing_Power")),
   FISHING_SPEED("DreamFish", Arrays.asList("Fishing Speed", "FishingSpeed", "Fishing_Speed")),
   LURES_MAX_TENSION("DreamFish", Arrays.asList("Lures Tension", "LuresTension", "Lures_Tension", "Lures Max Tension", "LuresMaxTension", "Lures_Max_Tension")),
   LURES_ENDURANCE("DreamFish", Arrays.asList("Lures Endurance", "LuresEndurance", "Lures_Endurance")),
   DURABILITY((String)null, Arrays.asList("Durability")),
   LEVEL((String)null, Arrays.asList("Level"));

   private final String dependency;
   private final List<String> aliases;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$api$praya$myitems$builder$lorestats$LoreStatsEnum;

   private LoreStatsEnum(String dependency, List<String> aliases) {
      this.dependency = dependency;
      this.aliases = aliases;
   }

   public final String getDependency() {
      return this.dependency;
   }

   public final List<String> getAliases() {
      return this.aliases;
   }

   public final boolean hasDependency() {
      return this.getDependency() != null;
   }

   public final boolean isAllowed() {
      String dependency = this.getDependency();
      return PluginUtil.isPluginInstalled(dependency);
   }

   public final String getText() {
      MainConfig mainConfig = MainConfig.getInstance();
      switch($SWITCH_TABLE$api$praya$myitems$builder$lorestats$LoreStatsEnum()[this.ordinal()]) {
      case 1:
         return mainConfig.getStatsIdentifierDamage();
      case 2:
         return mainConfig.getStatsIdentifierPenetration();
      case 3:
         return mainConfig.getStatsIdentifierPvPDamage();
      case 4:
         return mainConfig.getStatsIdentifierPvEDamage();
      case 5:
         return mainConfig.getStatsIdentifierDefense();
      case 6:
         return mainConfig.getStatsIdentifierPvPDefense();
      case 7:
         return mainConfig.getStatsIdentifierPvEDefense();
      case 8:
         return mainConfig.getStatsIdentifierHealth();
      case 9:
         return mainConfig.getStatsIdentifierHealthRegen();
      case 10:
         return mainConfig.getStatsIdentifierStaminaMax();
      case 11:
         return mainConfig.getStatsIdentifierStaminaRegen();
      case 12:
         return mainConfig.getStatsIdentifierAttackAoERadius();
      case 13:
         return mainConfig.getStatsIdentifierAttackAoEDamage();
      case 14:
         return mainConfig.getStatsIdentifierCriticalChance();
      case 15:
         return mainConfig.getStatsIdentifierCriticalDamage();
      case 16:
         return mainConfig.getStatsIdentifierBlockAmount();
      case 17:
         return mainConfig.getStatsIdentifierBlockRate();
      case 18:
         return mainConfig.getStatsIdentifierHitRate();
      case 19:
         return mainConfig.getStatsIdentifierDodgeRate();
      case 20:
         return mainConfig.getStatsIdentifierFishingChance();
      case 21:
         return mainConfig.getStatsIdentifierFishingPower();
      case 22:
         return mainConfig.getStatsIdentifierFishingSpeed();
      case 23:
         return mainConfig.getStatsIdentifierLuresMaxTension();
      case 24:
         return mainConfig.getStatsIdentifierLuresEndurance();
      case 25:
         return mainConfig.getStatsIdentifierDurability();
      case 26:
         return mainConfig.getStatsIdentifierLevel();
      default:
         return null;
      }
   }

   public static final LoreStatsEnum get(String stats) {
      LoreStatsEnum[] var4;
      int var3 = (var4 = values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         LoreStatsEnum key = var4[var2];
         Iterator var6 = key.getAliases().iterator();

         while(var6.hasNext()) {
            String aliases = (String)var6.next();
            if (aliases.equalsIgnoreCase(stats)) {
               return key;
            }
         }
      }

      return null;
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$api$praya$myitems$builder$lorestats$LoreStatsEnum() {
      int[] var10000 = $SWITCH_TABLE$api$praya$myitems$builder$lorestats$LoreStatsEnum;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[values().length];

         try {
            var0[ATTACK_AOE_DAMAGE.ordinal()] = 13;
         } catch (NoSuchFieldError var26) {
         }

         try {
            var0[ATTACK_AOE_RADIUS.ordinal()] = 12;
         } catch (NoSuchFieldError var25) {
         }

         try {
            var0[BLOCK_AMOUNT.ordinal()] = 16;
         } catch (NoSuchFieldError var24) {
         }

         try {
            var0[BLOCK_RATE.ordinal()] = 17;
         } catch (NoSuchFieldError var23) {
         }

         try {
            var0[CRITICAL_CHANCE.ordinal()] = 14;
         } catch (NoSuchFieldError var22) {
         }

         try {
            var0[CRITICAL_DAMAGE.ordinal()] = 15;
         } catch (NoSuchFieldError var21) {
         }

         try {
            var0[DAMAGE.ordinal()] = 1;
         } catch (NoSuchFieldError var20) {
         }

         try {
            var0[DEFENSE.ordinal()] = 5;
         } catch (NoSuchFieldError var19) {
         }

         try {
            var0[DODGE_RATE.ordinal()] = 19;
         } catch (NoSuchFieldError var18) {
         }

         try {
            var0[DURABILITY.ordinal()] = 25;
         } catch (NoSuchFieldError var17) {
         }

         try {
            var0[FISHING_CHANCE.ordinal()] = 20;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[FISHING_POWER.ordinal()] = 21;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[FISHING_SPEED.ordinal()] = 22;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[HEALTH.ordinal()] = 8;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[HEALTH_REGEN.ordinal()] = 9;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[HIT_RATE.ordinal()] = 18;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[LEVEL.ordinal()] = 26;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[LURES_ENDURANCE.ordinal()] = 24;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[LURES_MAX_TENSION.ordinal()] = 23;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[PENETRATION.ordinal()] = 2;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[PVE_DAMAGE.ordinal()] = 4;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[PVE_DEFENSE.ordinal()] = 7;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[PVP_DAMAGE.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[PVP_DEFENSE.ordinal()] = 6;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[STAMINA_MAX.ordinal()] = 10;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[STAMINA_REGEN.ordinal()] = 11;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$api$praya$myitems$builder$lorestats$LoreStatsEnum = var0;
         return var0;
      }
   }
}
