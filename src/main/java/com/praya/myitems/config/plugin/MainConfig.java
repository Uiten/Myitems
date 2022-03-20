package com.praya.myitems.config.plugin;

import com.praya.agarthalib.utility.DataUtil;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.FileUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerConfig;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MainConfig extends HandlerConfig {
   public static final String KEY_STATS = TextUtil.colorful("&0&1&r");
   public static final String KEY_STATS_VALUE = TextUtil.colorful("&0&2&r");
   public static final String KEY_PASSIVE_EFFECT = TextUtil.colorful("&0&3&r");
   public static final String KEY_ABILITY_WEAPON = TextUtil.colorful("&0&4&r");
   public static final String KEY_ABILITY_ARMOR = TextUtil.colorful("&2&5&r");
   public static final String KEY_ABILITY_PERCENT = TextUtil.colorful("&0&5&r");
   public static final String KEY_CLICK = TextUtil.colorful("&0&6&r");
   public static final String KEY_COMMAND = TextUtil.colorful("&0&7&r");
   public static final String KEY_SHOOT = TextUtil.colorful("&0&8&r");
   public static final String KEY_SPECIAL = TextUtil.colorful("&0&9&r");
   public static final String KEY_EXP_CURRENT = TextUtil.colorful("&1&0&r");
   public static final String KEY_EXP_UP = TextUtil.colorful("&1&1&r");
   public static final String KEY_COOLDOWN = TextUtil.colorful("&1&2&r");
   public static final String KEY_ELEMENT = TextUtil.colorful("&1&3&r");
   public static final String KEY_ELEMENT_VALUE = TextUtil.colorful("&1&4&r");
   public static final String KEY_SOCKET_SLOT = TextUtil.colorful("&1&5&r");
   public static final String KEY_SOCKET_LORE_GEMS = TextUtil.colorful("&1&6&r");
   public static final String KEY_REQ_BOUND = TextUtil.colorful("&2&1&r");
   public static final String KEY_REQ_LEVEL = TextUtil.colorful("&2&2&r");
   public static final String KEY_REQ_PERMISSION = TextUtil.colorful("&2&3&r");
   public static final String KEY_REQ_CLASS = TextUtil.colorful("&2&4&r");
   public static final String KEY_SET_LINE = TextUtil.colorful("&3&0&r");
   public static final String KEY_SET_COMPONENT_SELF = TextUtil.colorful("&3&1&r");
   public static final String KEY_SET_COMPONENT_OTHER = TextUtil.colorful("&3&2&r");
   private final FileConfiguration config;

   private MainConfig(MyItems plugin) {
      super(plugin);
      this.config = new YamlConfiguration();
   }

   public static final MainConfig getInstance() {
      return MainConfig.MainConfigHelper.instance;
   }

   public final String getGeneralVersion() {
      return this.config.getString("Configuration.General.Version");
   }

   public final String getGeneralLocale() {
      return this.config.getString("Configuration.General.Locale");
   }

   public final boolean isMetricsMessage() {
      return this.config.getBoolean("Configuration.Metrics.Message");
   }

   public final boolean isHookMessage() {
      return this.config.getBoolean("Configuration.Hook.Message");
   }

   public final String getUtilityTooltip() {
      return this.config.getString("Configuration.Utility.Tooltip");
   }

   public final String getUtilityCurrency() {
      return this.config.getString("Configuration.Utility.Currency");
   }

   public final double getEffectRange() {
      return this.config.getDouble("Configuration.Effect.Range");
   }

   public final int getListContent() {
      return this.config.getInt("Configuration.List.Content");
   }

   public final boolean isModifierEnableVanillaDamage() {
      return this.config.getBoolean("Configuration.Modifier.Enable_Vanilla_Damage");
   }

   public final boolean isModifierEnableVanillaFormulaArmor() {
      return this.config.getBoolean("Configuration.Modifier.Enable_Vanilla_Formula_Armor");
   }

   public final boolean isModifierEnableCustomModifier() {
      return this.config.getBoolean("Configuration.Modifier.Enable_Custom_Modifier");
   }

   public final boolean isModifierEnableFlatDamage() {
      return this.config.getBoolean("Configuration.Modifier.Enable_Flat_Damage");
   }

   public final boolean isModifierEnableBalancingSystem() {
      return this.config.getBoolean("Configuration.Modifier.Enable_Balancing_System");
   }

   public final boolean isModifierEnableBalancingMob() {
      return this.config.getBoolean("Configuration.Modifier.Enable_Balancing_Mob");
   }

   public final boolean isModifierEnableCustomCritical() {
      return this.config.getBoolean("Configuration.Modifier.Enable_Custom_Critical");
   }

   public final boolean isModifierEnableCustomMobDamage() {
      return this.config.getBoolean("Configuration.Modifier.Enable_Custom_Mob_Damage");
   }

   public final boolean isModifierDefenseAffectEntityExplosion() {
      return this.config.getBoolean("Configuration.Modifier.Defense_Affect_Entity_Explosion");
   }

   public final boolean isModifierDefenseAffectBlockExplosion() {
      return this.config.getBoolean("Configuration.Modifier.Defense_Affect_Block_Explosion");
   }

   public final boolean isModifierDefenseAffectCustom() {
      return this.config.getBoolean("Configuration.Modifier.Defense_Affect_Custom");
   }

   public final boolean isModifierDefenseAffectContact() {
      return this.config.getBoolean("Configuration.Modifier.Defense_Affect_Contact");
   }

   public final boolean isModifierDefenseAffectFall() {
      return this.config.getBoolean("Configuration.Modifier.Defense_Affect_Fall");
   }

   public final boolean isModifierDefenseAffectFallingBlock() {
      return this.config.getBoolean("Configuration.Modifier.Defense_Affect_Falling_Block");
   }

   public final boolean isModifierDefenseAffectThorn() {
      return this.config.getBoolean("Configuration.Modifier.Defense_Affect_Thorn");
   }

   public final String getModifierCriticalAttackType() {
      return this.config.getString("Configuration.Modifier.Critical_Attack_Type");
   }

   public final double getModifierModusValue() {
      return this.config.getDouble("Configuration.Modifier.Modus_Value");
   }

   public final double getModifierScaleDamageVanilla() {
      return this.config.getDouble("Configuration.Modifier.Scale_Damage_Vanilla");
   }

   public final double getModifierScaleDamageCustom() {
      return this.config.getDouble("Configuration.Modifier.Scale_Damage_Custom");
   }

   public final double getModifierScaleDamageOverall() {
      return this.config.getDouble("Configuration.Modifier.Scale_Damage_Overall");
   }

   public final double getModifierScaleDefenseOverall() {
      return this.config.getDouble("Configuration.Modifier.Scale_Defense_Overall");
   }

   public final double getModifierScaleDefensePhysic() {
      return this.config.getDouble("Configuration.Modifier.Scale_Defense_Physic");
   }

   public final double getModifierScaleAbsorbEffect() {
      return this.config.getDouble("Configuration.Modifier.Scale_Absorb_Effect");
   }

   public final double getModifierScaleMobDamageReceive() {
      return this.config.getDouble("Configuration.Modifier.Scale_Mob_Damage_Receive");
   }

   public final double getModifierScaleExpOffHand() {
      return this.config.getDouble("Configuration.Modifier.Scale_Exp_OffHand");
   }

   public final double getModifierScaleExpArmor() {
      return this.config.getDouble("Configuration.Modifier.Scale_Exp_Armor");
   }

   public final double getDropExpPlayer() {
      return this.config.getDouble("Configuration.Drop.Exp_Player");
   }

   public final double getDropExpMobs() {
      return this.config.getDouble("Configuration.Drop.Exp_Mobs");
   }

   public final String getSupportTypeLevel() {
      return this.config.getString("Configuration.Support.Type_Level");
   }

   public final String getSupportTypeClass() {
      return this.config.getString("Configuration.Support.Type_Class");
   }

   public final boolean isStatsEnableItemUniversal() {
      return this.config.getBoolean("Configuration.Stats.Enable_Item_Universal");
   }

   public final boolean isStatsEnableItemBroken() {
      return this.config.getBoolean("Configuration.Stats.Enable_Item_Broken");
   }

   public final boolean isStatsEnableItemBrokenMessage() {
      return this.config.getBoolean("Configuration.Stats.Enable_Item_Broken_Message");
   }

   public final boolean isStatsEnableMaxHealth() {
      return this.config.getBoolean("Configuration.Stats.Enable_Max_Health");
   }

   public final String getStatsFormatValue() {
      return this.config.getString("Configuration.Stats.Format_Value");
   }

   public final String getStatsFormatExp() {
      return this.config.getString("Configuration.Stats.Format_Exp");
   }

   public final String getStatsColor() {
      return this.config.getString("Configuration.Stats.Color");
   }

   public final String getStatsColorValue() {
      return this.config.getString("Configuration.Stats.Color_Value");
   }

   public final String getStatsColorExpCurrent() {
      return this.config.getString("Configuration.Stats.Color_Exp_Current");
   }

   public final String getStatsColorExpUp() {
      return this.config.getString("Configuration.Stats.Color_Exp_Up");
   }

   public final String getStatsLorePositiveValue() {
      return this.config.getString("Configuration.Stats.Lore_Positive_Value");
   }

   public final String getStatsLoreNegativeValue() {
      return this.config.getString("Configuration.Stats.Lore_Negative_Value");
   }

   public final String getStatsLoreRangeSymbol() {
      return this.config.getString("Configuration.Stats.Lore_Range_Symbol");
   }

   public final String getStatsLoreDividerSymbol() {
      return this.config.getString("Configuration.Stats.Lore_Divider_Symbol");
   }

   public final String getStatsLoreMultiplierSymbol() {
      return this.config.getString("Configuration.Stats.Lore_Multiplier_Symbol");
   }

   public final double getStatsScaleOffHandValue() {
      return this.config.getDouble("Configuration.Stats.Scale_OffHand_Value");
   }

   public final double getStatsScaleUpValue() {
      return this.config.getDouble("Configuration.Stats.Scale_Up_Value");
   }

   public final double getStatsScaleArmorShield() {
      return this.config.getDouble("Configuration.Stats.Scale_Armor_Shield");
   }

   public final int getStatsMaxLevelValue() {
      return this.config.getInt("Configuration.Stats.Max_Level_Value");
   }

   public final String getStatsIdentifierDamage() {
      return this.config.getString("Configuration.Stats.Identifier_Damage");
   }

   public final String getStatsIdentifierPenetration() {
      return this.config.getString("Configuration.Stats.Identifier_Penetration");
   }

   public final String getStatsIdentifierPvPDamage() {
      return this.config.getString("Configuration.Stats.Identifier_PvP_Damage");
   }

   public final String getStatsIdentifierPvEDamage() {
      return this.config.getString("Configuration.Stats.Identifier_PvE_Damage");
   }

   public final String getStatsIdentifierDefense() {
      return this.config.getString("Configuration.Stats.Identifier_Defense");
   }

   public final String getStatsIdentifierPvPDefense() {
      return this.config.getString("Configuration.Stats.Identifier_PvP_Defense");
   }

   public final String getStatsIdentifierPvEDefense() {
      return this.config.getString("Configuration.Stats.Identifier_PvE_Defense");
   }

   public final String getStatsIdentifierHealth() {
      return this.config.getString("Configuration.Stats.Identifier_Health");
   }

   public final String getStatsIdentifierHealthRegen() {
      return this.config.getString("Configuration.Stats.Identifier_Health_Regen");
   }

   public final String getStatsIdentifierStaminaMax() {
      return this.config.getString("Configuration.Stats.Identifier_Stamina_Max");
   }

   public final String getStatsIdentifierStaminaRegen() {
      return this.config.getString("Configuration.Stats.Identifier_Stamina_Regen");
   }

   public final String getStatsIdentifierAttackAoERadius() {
      return this.config.getString("Configuration.Stats.Identifier_Attack_AoE_Radius");
   }

   public final String getStatsIdentifierAttackAoEDamage() {
      return this.config.getString("Configuration.Stats.Identifier_Attack_AoE_Damage");
   }

   public final String getStatsIdentifierCriticalChance() {
      return this.config.getString("Configuration.Stats.Identifier_Critical_Chance");
   }

   public final String getStatsIdentifierCriticalDamage() {
      return this.config.getString("Configuration.Stats.Identifier_Critical_Damage");
   }

   public final String getStatsIdentifierBlockAmount() {
      return this.config.getString("Configuration.Stats.Identifier_Block_Amount");
   }

   public final String getStatsIdentifierBlockRate() {
      return this.config.getString("Configuration.Stats.Identifier_Block_Rate");
   }

   public final String getStatsIdentifierHitRate() {
      return this.config.getString("Configuration.Stats.Identifier_Hit_Rate");
   }

   public final String getStatsIdentifierDodgeRate() {
      return this.config.getString("Configuration.Stats.Identifier_Dodge_Rate");
   }

   public final String getStatsIdentifierFishingChance() {
      return this.config.getString("Configuration.Stats.Identifier_Fishing_Chance");
   }

   public final String getStatsIdentifierFishingPower() {
      return this.config.getString("Configuration.Stats.Identifier_Fishing_Power");
   }

   public final String getStatsIdentifierFishingSpeed() {
      return this.config.getString("Configuration.Stats.Identifier_Fishing_Speed");
   }

   public final String getStatsIdentifierLuresMaxTension() {
      return this.config.getString("Configuration.Stats.Identifier_Lures_Max_Tension");
   }

   public final String getStatsIdentifierLuresEndurance() {
      return this.config.getString("Configuration.Stats.Identifier_Lures_Endurance");
   }

   public final String getStatsIdentifierDurability() {
      return this.config.getString("Configuration.Stats.Identifier_Durability");
   }

   public final String getStatsIdentifierLevel() {
      return this.config.getString("Configuration.Stats.Identifier_Level");
   }

   public final String getAbilityFormat() {
      return this.config.getString("Configuration.Ability.Format");
   }

   public final String getAbilityColor() {
      return this.config.getString("Configuration.Ability.Color");
   }

   public final String getAbilityColorPercent() {
      return this.config.getString("Configuration.Ability.Color_Percent");
   }

   public final boolean isAbilityWeaponEnableOffHand() {
      return this.config.getBoolean("Configuration.Ability.Weapon.Enable_OffHand");
   }

   public final String getAbilityWeaponIdentifierAirShock() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Air_Shock");
   }

   public final String getAbilityWeaponIdentifierBadLuck() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Bad_Luck");
   }

   public final String getAbilityWeaponIdentifierBlind() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Blind");
   }

   public final String getAbilityWeaponIdentifierBubbleDeflector() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Bubble_Deflector");
   }

   public final String getAbilityWeaponIdentifierCannibalism() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Cannibalism");
   }

   public final String getAbilityWeaponIdentifierConfuse() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Confuse");
   }

   public final String getAbilityWeaponIdentifierCurse() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Curse");
   }

   public final String getAbilityWeaponIdentifierDarkFlame() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Dark_Flame");
   }

   public final String getAbilityWeaponIdentifierDarkImpact() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Dark_Impact");
   }

   public final String getAbilityWeaponIdentifierFlame() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Flame");
   }

   public final String getAbilityWeaponIdentifierFlameWheel() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Flame_Wheel");
   }

   public final String getAbilityWeaponIdentifierFreeze() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Freeze");
   }

   public final String getAbilityWeaponIdentifierHarm() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Harm");
   }

   public final String getAbilityWeaponIdentifierHungry() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Hungry");
   }

   public final String getAbilityWeaponIdentifierLevitation() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Levitation");
   }

   public final String getAbilityWeaponIdentifierLightning() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Lightning");
   }

   public final String getAbilityWeaponIdentifierPoison() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Poison");
   }

   public final String getAbilityWeaponIdentifierRoots() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Roots");
   }

   public final String getAbilityWeaponIdentifierSlowness() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Slowness");
   }

   public final String getAbilityWeaponIdentifierTired() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Tired");
   }

   public final String getAbilityWeaponIdentifierVampirism() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Vampirism");
   }

   public final String getAbilityWeaponIdentifierVenomBlast() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Venom_Blast");
   }

   public final String getAbilityWeaponIdentifierVenomSpread() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Venom_Spread");
   }

   public final String getAbilityWeaponIdentifierWeakness() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Weakness");
   }

   public final String getAbilityWeaponIdentifierWither() {
      return this.config.getString("Configuration.Ability.Weapon.Identifier_Wither");
   }

   public final boolean isPassiveEnableGradeCalculation() {
      return this.config.getBoolean("Configuration.Passive.Enable_Grade_Calculation");
   }

   public final boolean isPassiveEnableHand() {
      return this.config.getBoolean("Configuration.Passive.Enable_Hand");
   }

   public final int getPassivePeriodEffect() {
      return this.config.getInt("Configuration.Passive.Period_Effect");
   }

   public final String getPassiveBuffFormat() {
      return this.config.getString("Configuration.Passive.Buff.Format");
   }

   public final String getPassiveBuffColor() {
      return this.config.getString("Configuration.Passive.Buff.Color");
   }

   public final String getPassiveBuffIdentifierStrength() {
      return this.config.getString("Configuration.Passive.Buff.Identifier_Strength");
   }

   public final String getPassiveBuffIdentifierProtection() {
      return this.config.getString("Configuration.Passive.Buff.Identifier_Protection");
   }

   public final String getPassiveBuffIdentifierVision() {
      return this.config.getString("Configuration.Passive.Buff.Identifier_Vision");
   }

   public final String getPassiveBuffIdentifierJump() {
      return this.config.getString("Configuration.Passive.Buff.Identifier_Jump");
   }

   public final String getPassiveBuffIdentifierAbsorb() {
      return this.config.getString("Configuration.Passive.Buff.Identifier_Absorb");
   }

   public final String getPassiveBuffIdentifierFireResistance() {
      return this.config.getString("Configuration.Passive.Buff.Identifier_Fire_Resistance");
   }

   public final String getPassiveBuffIdentifierInvisibility() {
      return this.config.getString("Configuration.Passive.Buff.Identifier_Invisibility");
   }

   public final String getPassiveBuffIdentifierLuck() {
      return this.config.getString("Configuration.Passive.Buff.Identifier_Luck");
   }

   public final String getPassiveBuffIdentifierHealthBoost() {
      return this.config.getString("Configuration.Passive.Buff.Identifier_Health_Boost");
   }

   public final String getPassiveBuffIdentifierRegeneration() {
      return this.config.getString("Configuration.Passive.Buff.Identifier_Regeneration");
   }

   public final String getPassiveBuffIdentifierSaturation() {
      return this.config.getString("Configuration.Passive.Buff.Identifier_Saturation");
   }

   public final String getPassiveBuffIdentifierSpeed() {
      return this.config.getString("Configuration.Passive.Buff.Identifier_Speed");
   }

   public final String getPassiveBuffIdentifierWaterBreathing() {
      return this.config.getString("Configuration.Passive.Buff.Identifier_Water_Breathing");
   }

   public final String getPassiveBuffIdentifierHaste() {
      return this.config.getString("Configuration.Passive.Buff.Identifier_Haste");
   }

   public final String getPassiveDebuffFormat() {
      return this.config.getString("Configuration.Passive.Debuff.Format");
   }

   public final String getPassiveDebuffColor() {
      return this.config.getString("Configuration.Passive.Debuff.Color");
   }

   public final String getPassiveDebuffIdentifierWeak() {
      return this.config.getString("Configuration.Passive.Debuff.Identifier_Weak");
   }

   public final String getPassiveDebuffIdentifierSlow() {
      return this.config.getString("Configuration.Passive.Debuff.Identifier_Slow");
   }

   public final String getPassiveDebuffIdentifierBlind() {
      return this.config.getString("Configuration.Passive.Debuff.Identifier_Blind");
   }

   public final String getPassiveDebuffIdentifierConfuse() {
      return this.config.getString("Configuration.Passive.Debuff.Identifier_Confuse");
   }

   public final String getPassiveDebuffIdentifierStarve() {
      return this.config.getString("Configuration.Passive.Debuff.Identifier_Starve");
   }

   public final String getPassiveDebuffIdentifierToxic() {
      return this.config.getString("Configuration.Passive.Debuff.Identifier_Toxic");
   }

   public final String getPassiveDebuffIdentifierGlow() {
      return this.config.getString("Configuration.Passive.Debuff.Identifier_Glow");
   }

   public final String getPassiveDebuffIdentifierFatigue() {
      return this.config.getString("Configuration.Passive.Debuff.Identifier_Fatigue");
   }

   public final String getPassiveDebuffIdentifierWither() {
      return this.config.getString("Configuration.Passive.Debuff.Identifier_Wither");
   }

   public final String getPassiveDebuffIdentifierUnluck() {
      return this.config.getString("Configuration.Passive.Debuff.Identifier_Unluck");
   }

   public final boolean isPowerEnableMessageCooldown() {
      return this.config.getBoolean("Configuration.Power.Enable_Message_Cooldown");
   }

   public final String getPowerFormat() {
      return this.config.getString("Configuration.Power.Format");
   }

   public final String getPowerColorClick() {
      return this.config.getString("Configuration.Power.Color_Click");
   }

   public final String getPowerColorType() {
      return this.config.getString("Configuration.Power.Color_Type");
   }

   public final String getPowerColorCooldown() {
      return this.config.getString("Configuration.Power.Color_Cooldown");
   }

   public final String getPowerClickLeft() {
      return this.config.getString("Configuration.Power.Click_Left");
   }

   public final String getPowerClickRight() {
      return this.config.getString("Configuration.Power.Click_Right");
   }

   public final String getPowerClickShiftLeft() {
      return this.config.getString("Configuration.Power.Click_Shift_Left");
   }

   public final String getPowerClickShiftRight() {
      return this.config.getString("Configuration.Power.Click_Shift_Right");
   }

   public final String getPowerProjectileIdentifierArrow() {
      return this.config.getString("Configuration.Power.Projectile.Identifier_Arrow");
   }

   public final String getPowerProjectileIdentifierSnowBall() {
      return this.config.getString("Configuration.Power.Projectile.Identifier_Snow_Ball");
   }

   public final String getPowerProjectileIdentifierEgg() {
      return this.config.getString("Configuration.Power.Projectile.Identifier_Egg");
   }

   public final String getPowerProjectileIdentifierFlameArrow() {
      return this.config.getString("Configuration.Power.Projectile.Identifier_Flame_Arrow");
   }

   public final String getPowerProjectileIdentifierFlameBall() {
      return this.config.getString("Configuration.Power.Projectile.Identifier_Flame_Ball");
   }

   public final String getPowerProjectileIdentifierFlameEgg() {
      return this.config.getString("Configuration.Power.Projectile.Identifier_Flame_Egg");
   }

   public final String getPowerProjectileIdentifierSmallFireball() {
      return this.config.getString("Configuration.Power.Projectile.Identifier_Small_Fireball");
   }

   public final String getPowerProjectileIdentifierLargeFireball() {
      return this.config.getString("Configuration.Power.Projectile.Identifier_Large_Fireball");
   }

   public final String getPowerProjectileIdentifierWitherSkull() {
      return this.config.getString("Configuration.Power.Projectile.Identifier_Wither_Skull");
   }

   public final String getPowerSpecialIdentifierBlink() {
      return this.config.getString("Configuration.Power.Special.Identifier_Blink");
   }

   public final String getPowerSpecialIdentifierFissure() {
      return this.config.getString("Configuration.Power.Special.Identifier_Fissure");
   }

   public final String getPowerSpecialIdentifierIceSpikes() {
      return this.config.getString("Configuration.Power.Special.Identifier_Ice_Spikes");
   }

   public final String getPowerSpecialIdentifierAmaterasu() {
      return this.config.getString("Configuration.Power.Special.Identifier_Amaterasu");
   }

   public final String getPowerSpecialIdentifierNeroBeam() {
      return this.config.getString("Configuration.Power.Special.Identifier_Nero_Beam");
   }

   public final String getElementFormat() {
      return this.config.getString("Configuration.Element.Format");
   }

   public final String getElementColor() {
      return this.config.getString("Configuration.Element.Color");
   }

   public final String getElementColorValue() {
      return this.config.getString("Configuration.Element.Color_Value");
   }

   public final String getSocketFormatSlot() {
      return this.config.getString("Configuration.Socket.Format_Slot");
   }

   public final String getSocketFormatSlotEmpty() {
      return this.config.getString("Configuration.Socket.Format_Slot_Empty");
   }

   public final String getSocketFormatSlotLocked() {
      return this.config.getString("Configuration.Socket.Format_Slot_Locked");
   }

   public final String getSocketTypeItemWeapon() {
      return this.config.getString("Configuration.Socket.Type_Item_Weapon");
   }

   public final String getSocketTypeItemArmor() {
      return this.config.getString("Configuration.Socket.Type_Item_Armor");
   }

   public final String getSocketTypeItemUniversal() {
      return this.config.getString("Configuration.Socket.Type_Item_Universal");
   }

   public final double getSocketCostSocket() {
      return (double)this.config.getInt("Configuration.Socket.Cost_Socket");
   }

   public final double getSocketCostUnlock() {
      return (double)this.config.getInt("Configuration.Socket.Cost_Unlock");
   }

   public final double getSocketCostDesocket() {
      return (double)this.config.getInt("Configuration.Socket.Cost_Desocket");
   }

   public final ItemStack getSocketItemRodUnlock() {
      ItemStack socketItemRodUnlock = this.config.getItemStack("Configuration.Socket.Item.Rod_Unlock");
      return socketItemRodUnlock != null ? socketItemRodUnlock.clone() : null;
   }

   public final ItemStack getSocketItemRodRemove() {
      ItemStack socketItemRodRemove = this.config.getItemStack("Configuration.Socket.Item.Rod_Remove");
      return socketItemRodRemove != null ? socketItemRodRemove.clone() : null;
   }

   public final String getSocketColorSlot() {
      return this.config.getString("Configuration.Socket.Color_Slot");
   }

   public final String getRequirementFormatLevel() {
      return this.config.getString("Configuration.Requirement.Format_Level");
   }

   public final String getRequirementFormatPermission() {
      return this.config.getString("Configuration.Requirement.Format_Permission");
   }

   public final String getRequirementFormatClass() {
      return this.config.getString("Configuration.Requirement.Format_Class");
   }

   public final String getRequirementFormatSoulUnbound() {
      return this.config.getString("Configuration.Requirement.Format_Soul_Unbound");
   }

   public final String getRequirementFormatSoulBound() {
      return this.config.getString("Configuration.Requirement.Format_Soul_Bound");
   }

   public final String getRequirementColorSoulBound() {
      return this.config.getString("Configuration.Requirement.Color_Soul_Bound");
   }

   public final String getRequirementColorLevel() {
      return this.config.getString("Configuration.Requirement.Color_Level");
   }

   public final String getRequirementColorPermission() {
      return this.config.getString("Configuration.Requirement.Color_Permission");
   }

   public final String getRequirementColorClass() {
      return this.config.getString("Configuration.Requirement.Color_Class");
   }

   public final List<String> getSetFormat() {
      return this.config.getStringList("Configuration.Set.Format");
   }

   public final String getSetFormatComponent() {
      return this.config.getString("Configuration.Set.Format_Component");
   }

   public final String getSetFormatBonus() {
      return this.config.getString("Configuration.Set.Format_Bonus");
   }

   public final String getSetLoreComponentActive() {
      return this.config.getString("Configuration.Set.Lore_Component_Active");
   }

   public final String getSetLoreComponentInactive() {
      return this.config.getString("Configuration.Set.Lore_Component_Inactive");
   }

   public final String getSetLoreBonusActive() {
      return this.config.getString("Configuration.Set.Lore_Bonus_Active");
   }

   public final String getSetLoreBonusInactive() {
      return this.config.getString("Configuration.Set.Lore_Bonus_Inactive");
   }

   public final boolean isMiscEnableParticlePotion() {
      return this.config.getBoolean("Configuration.Misc.Enable_Particle_Potion");
   }

   public final void setup() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathFile = dataManager.getPath("Path_File_Config");
      this.moveOldFile();
      File file = FileUtil.getFile(this.plugin, pathFile);
      if (!file.exists()) {
         FileUtil.saveResource(this.plugin, pathFile);
      }

      FileConfiguration configurationResource = FileUtil.getFileConfigurationResource(this.plugin, pathFile);
      FileConfiguration configurationFile = FileUtil.getFileConfiguration(file);
      this.loadConfig(this.config, configurationResource);
      this.loadConfig(this.config, configurationFile);
      this.loadOldConfig(this.config, configurationFile);
      this.loadConfigColor(this.config);
   }

   private final void loadConfig(FileConfiguration config, FileConfiguration source) {
      Iterator var4 = source.getKeys(false).iterator();

      label862:
      while(true) {
         String key;
         do {
            if (!var4.hasNext()) {
               return;
            }

            key = (String)var4.next();
         } while(!key.equalsIgnoreCase("Configuration") && !key.equalsIgnoreCase("Config"));

         ConfigurationSection dataSection = source.getConfigurationSection(key);
         Iterator var7 = dataSection.getKeys(false).iterator();

         while(true) {
            label853:
            while(true) {
               if (!var7.hasNext()) {
                  continue label862;
               }

               String data = (String)var7.next();
               ConfigurationSection statsDataSection;
               String statsData;
               Iterator var10;
               String statsIdentifierLevel;
               String path;
               if (data.equalsIgnoreCase("General")) {
                  statsDataSection = dataSection.getConfigurationSection(data);
                  var10 = statsDataSection.getKeys(false).iterator();

                  while(var10.hasNext()) {
                     statsData = (String)var10.next();
                     if (statsData.equalsIgnoreCase("Version")) {
                        path = "Configuration.General.Version";
                        statsIdentifierLevel = statsDataSection.getString(statsData);
                        config.set("Configuration.General.Version", statsIdentifierLevel);
                     } else if (statsData.equalsIgnoreCase("Locale")) {
                        path = "Configuration.General.Locale";
                        statsIdentifierLevel = statsDataSection.getString(statsData);
                        config.set("Configuration.General.Locale", statsIdentifierLevel);
                     }
                  }
               } else {
                  boolean modifierDefenseAffectThorn;
                  if (data.equalsIgnoreCase("Metrics")) {
                     statsDataSection = dataSection.getConfigurationSection(data);
                     var10 = statsDataSection.getKeys(false).iterator();

                     while(var10.hasNext()) {
                        statsData = (String)var10.next();
                        if (statsData.equalsIgnoreCase("Message")) {
                           path = "Configuration.Metrics.Message";
                           modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                           config.set("Configuration.Metrics.Message", modifierDefenseAffectThorn);
                        }
                     }
                  } else if (data.equalsIgnoreCase("Hook")) {
                     statsDataSection = dataSection.getConfigurationSection(data);
                     var10 = statsDataSection.getKeys(false).iterator();

                     while(var10.hasNext()) {
                        statsData = (String)var10.next();
                        if (statsData.equalsIgnoreCase("Message")) {
                           path = "Configuration.Hook.Message";
                           modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                           config.set("Configuration.Hook.Message", modifierDefenseAffectThorn);
                        }
                     }
                  } else if (data.equalsIgnoreCase("Utility")) {
                     statsDataSection = dataSection.getConfigurationSection(data);
                     var10 = statsDataSection.getKeys(false).iterator();

                     while(var10.hasNext()) {
                        statsData = (String)var10.next();
                        if (statsData.equalsIgnoreCase("Tooltip")) {
                           path = "Configuration.Utility.Tooltip";
                           statsIdentifierLevel = statsDataSection.getString(statsData);
                           config.set("Configuration.Utility.Tooltip", statsIdentifierLevel);
                        } else if (statsData.equalsIgnoreCase("Currency")) {
                           path = "Configuration.Utility.Currency";
                           statsIdentifierLevel = statsDataSection.getString(statsData);
                           config.set("Configuration.Utility.Currency", statsIdentifierLevel);
                        }
                     }
                  } else {
                     double modifierScaleExpArmor;
                     if (data.equalsIgnoreCase("Effect")) {
                        statsDataSection = dataSection.getConfigurationSection(data);
                        var10 = statsDataSection.getKeys(false).iterator();

                        while(var10.hasNext()) {
                           statsData = (String)var10.next();
                           if (statsData.equalsIgnoreCase("Range")) {
                              path = "Configuration.Effect.Range";
                              modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                              config.set("Configuration.Effect.Range", modifierScaleExpArmor);
                           }
                        }
                     } else {
                        int statsMaxLevelValue;
                        if (data.equalsIgnoreCase("List")) {
                           statsDataSection = dataSection.getConfigurationSection(data);
                           var10 = statsDataSection.getKeys(false).iterator();

                           while(var10.hasNext()) {
                              statsData = (String)var10.next();
                              if (statsData.equalsIgnoreCase("Content")) {
                                 path = "Configuration.List.Content";
                                 statsMaxLevelValue = statsDataSection.getInt(statsData);
                                 config.set("Configuration.List.Content", statsMaxLevelValue);
                              }
                           }
                        } else if (data.equalsIgnoreCase("Modifier")) {
                           statsDataSection = dataSection.getConfigurationSection(data);
                           var10 = statsDataSection.getKeys(false).iterator();

                           while(var10.hasNext()) {
                              statsData = (String)var10.next();
                              if (statsData.equalsIgnoreCase("Enable_Vanilla_Damage")) {
                                 path = "Configuration.Modifier.Enable_Vanilla_Damage";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Enable_Vanilla_Damage", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Enable_Vanilla_Formula_Armor")) {
                                 path = "Configuration.Modifier.Enable_Vanilla_Formula_Armor";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Enable_Vanilla_Formula_Armor", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Enable_Custom_Modifier")) {
                                 path = "Configuration.Modifier.Enable_Custom_Modifier";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Enable_Custom_Modifier", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Enable_Flat_Damage")) {
                                 path = "Configuration.Modifier.Enable_Flat_Damage";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Enable_Flat_Damage", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Enable_Balancing_System")) {
                                 path = "Configuration.Modifier.Enable_Balancing_System";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Enable_Balancing_System", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Enable_Balancing_Mob")) {
                                 path = "Configuration.Modifier.Enable_Balancing_Mob";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Enable_Balancing_Mob", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Enable_Custom_Critical")) {
                                 path = "Configuration.Modifier.Enable_Custom_Critical";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Enable_Custom_Critical", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Enable_Custom_Mob_Damage")) {
                                 path = "Configuration.Modifier.Enable_Custom_Mob_Damage";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Enable_Custom_Mob_Damage", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Defense_Affect_Entity_Explosion")) {
                                 path = "Configuration.Modifier.Defense_Affect_Entity_Explosion";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Defense_Affect_Entity_Explosion", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Defense_Affect_Block_Explosion")) {
                                 path = "Configuration.Modifier.Defense_Affect_Block_Explosion";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Defense_Affect_Block_Explosion", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Defense_Affect_Custom")) {
                                 path = "Configuration.Modifier.Defense_Affect_Custom";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Defense_Affect_Custom", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Defense_Affect_Contact")) {
                                 path = "Configuration.Modifier.Defense_Affect_Contact";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Defense_Affect_Contact", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Defense_Affect_Fall")) {
                                 path = "Configuration.Modifier.Defense_Affect_Fall";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Defense_Affect_Fall", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Defense_Affect_Falling_Block")) {
                                 path = "Configuration.Modifier.Defense_Affect_Falling_Block";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Defense_Affect_Falling_Block", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Defense_Affect_Thorn")) {
                                 path = "Configuration.Modifier.Defense_Affect_Thorn";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Modifier.Defense_Affect_Thorn", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Critical_Attack_Type")) {
                                 path = "Configuration.Modifier.Critical_Attack_Type";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Modifier.Critical_Attack_Type", statsIdentifierLevel);
                              } else if (statsData.equalsIgnoreCase("Modus_Value")) {
                                 path = "Configuration.Modifier.Modus_Value";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Modifier.Modus_Value", modifierScaleExpArmor);
                              } else if (statsData.equalsIgnoreCase("Scale_Damage_Vanilla")) {
                                 path = "Configuration.Modifier.Scale_Damage_Vanilla";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Modifier.Scale_Damage_Vanilla", modifierScaleExpArmor);
                              } else if (statsData.equalsIgnoreCase("Scale_Damage_Custom")) {
                                 path = "Configuration.Modifier.Scale_Damage_Custom";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Modifier.Scale_Damage_Custom", modifierScaleExpArmor);
                              } else if (statsData.equalsIgnoreCase("Scale_Damage_Overall")) {
                                 path = "Configuration.Modifier.Scale_Damage_Overall";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Modifier.Scale_Damage_Overall", modifierScaleExpArmor);
                              } else if (statsData.equalsIgnoreCase("Scale_Defense_Overall")) {
                                 path = "Configuration.Modifier.Scale_Defense_Overall";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Modifier.Scale_Defense_Overall", modifierScaleExpArmor);
                              } else if (statsData.equalsIgnoreCase("Scale_Defense_Physic")) {
                                 path = "Configuration.Modifier.Scale_Defense_Physic";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Modifier.Scale_Defense_Physic", modifierScaleExpArmor);
                              } else if (statsData.equalsIgnoreCase("Scale_Absorb_Effect")) {
                                 path = "Configuration.Modifier.Scale_Absorb_Effect";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Modifier.Scale_Absorb_Effect", modifierScaleExpArmor);
                              } else if (statsData.equalsIgnoreCase("Scale_Mob_Damage_Receive")) {
                                 path = "Configuration.Modifier.Scale_Mob_Damage_Receive";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Modifier.Scale_Mob_Damage_Receive", modifierScaleExpArmor);
                              } else if (statsData.equalsIgnoreCase("Scale_Exp_OffHand")) {
                                 path = "Configuration.Modifier.Scale_Exp_OffHand";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Modifier.Scale_Exp_OffHand", modifierScaleExpArmor);
                              } else if (statsData.equalsIgnoreCase("Scale_Exp_Armor")) {
                                 path = "Configuration.Modifier.Scale_Exp_Armor";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Modifier.Scale_Exp_Armor", modifierScaleExpArmor);
                              }
                           }
                        } else if (data.equalsIgnoreCase("Drop")) {
                           statsDataSection = dataSection.getConfigurationSection(data);
                           var10 = statsDataSection.getKeys(false).iterator();

                           while(var10.hasNext()) {
                              statsData = (String)var10.next();
                              if (statsData.equalsIgnoreCase("Exp_Player")) {
                                 path = "Configuration.Drop.Exp_Player";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Drop.Exp_Player", modifierScaleExpArmor);
                              } else if (statsData.equalsIgnoreCase("Exp_Mobs")) {
                                 path = "Configuration.Drop.Exp_Mobs";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Drop.Exp_Mobs", modifierScaleExpArmor);
                              }
                           }
                        } else if (data.equalsIgnoreCase("Support")) {
                           statsDataSection = dataSection.getConfigurationSection(data);
                           var10 = statsDataSection.getKeys(false).iterator();

                           while(var10.hasNext()) {
                              statsData = (String)var10.next();
                              if (statsData.equalsIgnoreCase("Type_Level")) {
                                 path = "Configuration.Support.Type_Level";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Support.Type_Level", statsIdentifierLevel);
                              } else if (statsData.equalsIgnoreCase("Type_Class")) {
                                 path = "Configuration.Support.Type_Class";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Support.Type_Class", statsIdentifierLevel);
                              }
                           }
                        } else if (data.equalsIgnoreCase("Stats")) {
                           statsDataSection = dataSection.getConfigurationSection(data);
                           var10 = statsDataSection.getKeys(false).iterator();

                           while(var10.hasNext()) {
                              statsData = (String)var10.next();
                              if (statsData.equalsIgnoreCase("Enable_Item_Universal")) {
                                 path = "Configuration.Stats.Enable_Item_Universal";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Stats.Enable_Item_Universal", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Enable_Item_Broken")) {
                                 path = "Configuration.Stats.Enable_Item_Broken";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Stats.Enable_Item_Broken", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Enable_Item_Broken_Message")) {
                                 path = "Configuration.Stats.Enable_Item_Broken_Message";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Stats.Enable_Item_Broken_Message", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Enable_Max_Health")) {
                                 path = "Configuration.Stats.Enable_Max_Health";
                                 modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                 config.set("Configuration.Stats.Enable_Max_Health", modifierDefenseAffectThorn);
                              } else if (statsData.equalsIgnoreCase("Format_Value")) {
                                 path = "Configuration.Stats.Format_Value";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Format_Value", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Format_Exp")) {
                                 path = "Configuration.Stats.Format_Exp";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Format_Exp", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Lore_Positive_Value")) {
                                 path = "Configuration.Stats.Lore_Positive_Value";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Lore_Positive_Value", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Lore_Negative_Value")) {
                                 path = "Configuration.Stats.Lore_Negative_Value";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Lore_Negative_Value", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Lore_Range_Symbol")) {
                                 path = "Configuration.Stats.Lore_Range_Symbol";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Lore_Range_Symbol", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Lore_Divider_Symbol")) {
                                 path = "Configuration.Stats.Lore_Divider_Symbol";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Lore_Divider_Symbol", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Lore_Multiplier_Symbol")) {
                                 path = "Configuration.Stats.Lore_Multiplier_Symbol";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Lore_Multiplier_Symbol", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Scale_OffHand_Value")) {
                                 path = "Configuration.Stats.Scale_OffHand_Value";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Stats.Scale_OffHand_Value", modifierScaleExpArmor);
                              } else if (statsData.equalsIgnoreCase("Scale_Up_Value")) {
                                 path = "Configuration.Stats.Scale_Up_Value";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Stats.Scale_Up_Value", modifierScaleExpArmor);
                              } else if (statsData.equalsIgnoreCase("Scale_Armor_Shield")) {
                                 path = "Configuration.Stats.Scale_Armor_Shield";
                                 modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                 config.set("Configuration.Stats.Scale_Armor_Shield", modifierScaleExpArmor);
                              } else if (statsData.equalsIgnoreCase("Max_Level_Value")) {
                                 path = "Configuration.Stats.Max_Level_Value";
                                 statsMaxLevelValue = statsDataSection.getInt(statsData);
                                 config.set("Configuration.Stats.Max_Level_Value", statsMaxLevelValue);
                              } else if (statsData.equalsIgnoreCase("Identifier_Damage")) {
                                 path = "Configuration.Stats.Identifier_Damage";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Damage", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Penetration")) {
                                 path = "Configuration.Stats.Identifier_Penetration";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Penetration", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_PvP_Damage")) {
                                 path = "Configuration.Stats.Identifier_PvP_Damage";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_PvP_Damage", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_PvE_Damage")) {
                                 path = "Configuration.Stats.Identifier_PvE_Damage";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_PvE_Damage", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Defense")) {
                                 path = "Configuration.Stats.Identifier_Defense";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Defense", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_PvP_Defense")) {
                                 path = "Configuration.Stats.Identifier_PvP_Defense";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_PvP_Defense", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_PvE_Defense")) {
                                 path = "Configuration.Stats.Identifier_PvE_Defense";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_PvE_Defense", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Health")) {
                                 path = "Configuration.Stats.Identifier_Health";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Health", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Health_Regen")) {
                                 path = "Configuration.Stats.Identifier_Health_Regen";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Health_Regen", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Stamina_Max")) {
                                 path = "Configuration.Stats.Identifier_Stamina_Max";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Stamina_Max", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Stamina_Regen")) {
                                 path = "Configuration.Stats.Identifier_Stamina_Regen";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Stamina_Regen", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Attack_AoE_Radius")) {
                                 path = "Configuration.Stats.Identifier_Attack_AoE_Radius";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Attack_AoE_Radius", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Attack_AoE_Damage")) {
                                 path = "Configuration.Stats.Identifier_Attack_AoE_Damage";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Attack_AoE_Damage", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Critical_Chance")) {
                                 path = "Configuration.Stats.Identifier_Critical_Chance";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Critical_Chance", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Critical_Damage")) {
                                 path = "Configuration.Stats.Identifier_Critical_Damage";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Critical_Damage", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Block_Amount")) {
                                 path = "Configuration.Stats.Identifier_Block_Amount";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Block_Amount", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Block_Rate")) {
                                 path = "Configuration.Stats.Identifier_Block_Rate";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Block_Rate", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Hit_Rate")) {
                                 path = "Configuration.Stats.Identifier_Hit_Rate";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Hit_Rate", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Dodge_Rate")) {
                                 path = "Configuration.Stats.Identifier_Dodge_Rate";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Dodge_Rate", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Fishing_Chance")) {
                                 path = "Configuration.Stats.Identifier_Fishing_Chance";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Fishing_Chance", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Fishing_Power")) {
                                 path = "Configuration.Stats.Identifier_Fishing_Power";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Fishing_Power", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Fishing_Speed")) {
                                 path = "Configuration.Stats.Identifier_Fishing_Speed";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Fishing_Speed", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Lures_Max_Tension")) {
                                 path = "Configuration.Stats.Identifier_Lures_Max_Tension";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Lures_Max_Tension", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Lures_Endurance")) {
                                 path = "Configuration.Stats.Identifier_Lures_Endurance";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Lures_Endurance", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Durability")) {
                                 path = "Configuration.Stats.Identifier_Durability";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Durability", TextUtil.colorful(statsIdentifierLevel));
                              } else if (statsData.equalsIgnoreCase("Identifier_Level")) {
                                 path = "Configuration.Stats.Identifier_Level";
                                 statsIdentifierLevel = statsDataSection.getString(statsData);
                                 config.set("Configuration.Stats.Identifier_Level", TextUtil.colorful(statsIdentifierLevel));
                              }
                           }
                        } else {
                           ConfigurationSection abilityWeaponDataSection;
                           Iterator var13;
                           String abilityWeaponIdentifierWither;
                           if (data.equalsIgnoreCase("Ability")) {
                              statsDataSection = dataSection.getConfigurationSection(data);
                              var10 = statsDataSection.getKeys(false).iterator();

                              while(true) {
                                 label746:
                                 while(true) {
                                    if (!var10.hasNext()) {
                                       continue label853;
                                    }

                                    statsData = (String)var10.next();
                                    if (statsData.equalsIgnoreCase("Format")) {
                                       path = "Configuration.Ability.Format";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Ability.Format", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Weapon")) {
                                       abilityWeaponDataSection = statsDataSection.getConfigurationSection(statsData);
                                       var13 = abilityWeaponDataSection.getKeys(false).iterator();

                                       while(true) {
                                          while(true) {
                                             if (!var13.hasNext()) {
                                                continue label746;
                                             }

                                             statsIdentifierLevel = (String)var13.next();
                                             if (statsIdentifierLevel.equalsIgnoreCase("Enable_OffHand")) {
                                                path = "Configuration.Ability.Weapon.Enable_OffHand";
                                                boolean abilityWeaponEnableOffHand = abilityWeaponDataSection.getBoolean(statsIdentifierLevel);
                                                config.set("Configuration.Ability.Weapon.Enable_OffHand", abilityWeaponEnableOffHand);
                                             } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Air_Shock")) {
                                                path = "Configuration.Ability.Weapon.Identifier_Air_Shock";
                                                abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                config.set("Configuration.Ability.Weapon.Identifier_Air_Shock", abilityWeaponIdentifierWither);
                                             } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Bad_Luck")) {
                                                path = "Configuration.Ability.Weapon.Identifier_Bad_Luck";
                                                abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                config.set("Configuration.Ability.Weapon.Identifier_Bad_Luck", abilityWeaponIdentifierWither);
                                             } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Blind")) {
                                                path = "Configuration.Ability.Weapon.Identifier_Blind";
                                                abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                config.set("Configuration.Ability.Weapon.Identifier_Blind", abilityWeaponIdentifierWither);
                                             } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Bubble_Deflector")) {
                                                path = "Configuration.Ability.Weapon.Identifier_Bubble_Deflector";
                                                abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                config.set("Configuration.Ability.Weapon.Identifier_Bubble_Deflector", abilityWeaponIdentifierWither);
                                             } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Cannibalism")) {
                                                path = "Configuration.Ability.Weapon.Identifier_Cannibalism";
                                                abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                config.set("Configuration.Ability.Weapon.Identifier_Cannibalism", abilityWeaponIdentifierWither);
                                             } else if (!statsIdentifierLevel.equalsIgnoreCase("Identifier_Confuse") && !statsIdentifierLevel.equalsIgnoreCase("Identifier_Nausea")) {
                                                if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Curse")) {
                                                   path = "Configuration.Ability.Weapon.Identifier_Curse";
                                                   abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                   config.set("Configuration.Ability.Weapon.Identifier_Curse", abilityWeaponIdentifierWither);
                                                } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Dark_Flame")) {
                                                   path = "Configuration.Ability.Weapon.Identifier_Dark_Flame";
                                                   abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                   config.set("Configuration.Ability.Weapon.Identifier_Dark_Flame", abilityWeaponIdentifierWither);
                                                } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Dark_Impact")) {
                                                   path = "Configuration.Ability.Weapon.Identifier_Dark_Impact";
                                                   abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                   config.set("Configuration.Ability.Weapon.Identifier_Dark_Impact", abilityWeaponIdentifierWither);
                                                } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Flame")) {
                                                   path = "Configuration.Ability.Weapon.Identifier_Flame";
                                                   abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                   config.set("Configuration.Ability.Weapon.Identifier_Flame", abilityWeaponIdentifierWither);
                                                } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Flame_Wheel")) {
                                                   path = "Configuration.Ability.Weapon.Identifier_Flame_Wheel";
                                                   abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                   config.set("Configuration.Ability.Weapon.Identifier_Flame_Wheel", abilityWeaponIdentifierWither);
                                                } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Freeze")) {
                                                   path = "Configuration.Ability.Weapon.Identifier_Freeze";
                                                   abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                   config.set("Configuration.Ability.Weapon.Identifier_Freeze", abilityWeaponIdentifierWither);
                                                } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Harm")) {
                                                   path = "Configuration.Ability.Weapon.Identifier_Harm";
                                                   abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                   config.set("Configuration.Ability.Weapon.Identifier_Harm", abilityWeaponIdentifierWither);
                                                } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Hungry")) {
                                                   path = "Configuration.Ability.Weapon.Identifier_Hungry";
                                                   abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                   config.set("Configuration.Ability.Weapon.Identifier_Hungry", abilityWeaponIdentifierWither);
                                                } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Levitation")) {
                                                   path = "Configuration.Ability.Weapon.Identifier_Levitation";
                                                   abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                   config.set("Configuration.Ability.Weapon.Identifier_Levitation", abilityWeaponIdentifierWither);
                                                } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Lightning")) {
                                                   path = "Configuration.Ability.Weapon.Identifier_Lightning";
                                                   abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                   config.set("Configuration.Ability.Weapon.Identifier_Lightning", abilityWeaponIdentifierWither);
                                                } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Poison")) {
                                                   path = "Configuration.Ability.Weapon.Identifier_Poison";
                                                   abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                   config.set("Configuration.Ability.Weapon.Identifier_Poison", abilityWeaponIdentifierWither);
                                                } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Roots")) {
                                                   path = "Configuration.Ability.Weapon.Identifier_Roots";
                                                   abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                   config.set("Configuration.Ability.Weapon.Identifier_Roots", abilityWeaponIdentifierWither);
                                                } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Slowness")) {
                                                   path = "Configuration.Ability.Weapon.Identifier_Slowness";
                                                   abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                   config.set("Configuration.Ability.Weapon.Identifier_Slowness", abilityWeaponIdentifierWither);
                                                } else if (!statsIdentifierLevel.equalsIgnoreCase("Identifier_Tired") && !statsIdentifierLevel.equalsIgnoreCase("Identifier_Fatigue")) {
                                                   if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Vampirism")) {
                                                      path = "Configuration.Ability.Weapon.Identifier_Vampirism";
                                                      abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                      config.set("Configuration.Ability.Weapon.Identifier_Vampirism", abilityWeaponIdentifierWither);
                                                   } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Venom_Blast")) {
                                                      path = "Configuration.Ability.Weapon.Identifier_Venom_Blast";
                                                      abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                      config.set("Configuration.Ability.Weapon.Identifier_Venom_Blast", abilityWeaponIdentifierWither);
                                                   } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Venom_Spread")) {
                                                      path = "Configuration.Ability.Weapon.Identifier_Venom_Spread";
                                                      abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                      config.set("Configuration.Ability.Weapon.Identifier_Venom_Spread", abilityWeaponIdentifierWither);
                                                   } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Weakness")) {
                                                      path = "Configuration.Ability.Weapon.Identifier_Weakness";
                                                      abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                      config.set("Configuration.Ability.Weapon.Identifier_Weakness", abilityWeaponIdentifierWither);
                                                   } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Wither")) {
                                                      path = "Configuration.Ability.Weapon.Identifier_Wither";
                                                      abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                      config.set("Configuration.Ability.Weapon.Identifier_Wither", abilityWeaponIdentifierWither);
                                                   }
                                                } else {
                                                   path = "Configuration.Ability.Weapon.Identifier_Tired";
                                                   abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                   config.set("Configuration.Ability.Weapon.Identifier_Tired", abilityWeaponIdentifierWither);
                                                }
                                             } else {
                                                path = "Configuration.Ability.Weapon.Identifier_Confuse";
                                                abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                                config.set("Configuration.Ability.Weapon.Identifier_Confuse", abilityWeaponIdentifierWither);
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           } else if (data.equalsIgnoreCase("Passive")) {
                              statsDataSection = dataSection.getConfigurationSection(data);
                              var10 = statsDataSection.getKeys(false).iterator();

                              while(true) {
                                 while(true) {
                                    if (!var10.hasNext()) {
                                       continue label853;
                                    }

                                    statsData = (String)var10.next();
                                    if (statsData.equalsIgnoreCase("Enable_Grade_Calculation")) {
                                       path = "Configuration.Passive.Enable_Grade_Calculation";
                                       modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                       config.set("Configuration.Passive.Enable_Grade_Calculation", modifierDefenseAffectThorn);
                                    } else if (statsData.equalsIgnoreCase("Enable_Hand")) {
                                       path = "Configuration.Passive.Enable_Hand";
                                       modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                       config.set("Configuration.Passive.Enable_Hand", modifierDefenseAffectThorn);
                                    } else if (statsData.equalsIgnoreCase("Period_Effect")) {
                                       path = "Configuration.Passive.Period_Effect";
                                       statsMaxLevelValue = statsDataSection.getInt(statsData);
                                       config.set("Configuration.Passive.Period_Effect", statsMaxLevelValue);
                                    } else if (statsData.equalsIgnoreCase("Buff")) {
                                       abilityWeaponDataSection = statsDataSection.getConfigurationSection(statsData);
                                       var13 = abilityWeaponDataSection.getKeys(false).iterator();

                                       while(var13.hasNext()) {
                                          statsIdentifierLevel = (String)var13.next();
                                          if (statsIdentifierLevel.equalsIgnoreCase("Format")) {
                                             path = "Configuration.Passive.Buff.Format";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Format", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Strength")) {
                                             path = "Configuration.Passive.Buff.Identifier_Strength";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Identifier_Strength", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Protection")) {
                                             path = "Configuration.Passive.Buff.Identifier_Protection";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Identifier_Protection", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Vision")) {
                                             path = "Configuration.Passive.Buff.Identifier_Vision";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Identifier_Vision", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Jump")) {
                                             path = "Configuration.Passive.Buff.Identifier_Jump";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Identifier_Jump", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Absorb")) {
                                             path = "Configuration.Passive.Buff.Identifier_Absorb";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Identifier_Absorb", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Fire_Resistance")) {
                                             path = "Configuration.Passive.Buff.Identifier_Fire_Resistance";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Identifier_Fire_Resistance", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Invisibility")) {
                                             path = "Configuration.Passive.Buff.Identifier_Invisibility";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Identifier_Invisibility", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Luck")) {
                                             path = "Configuration.Passive.Buff.Identifier_Luck";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Identifier_Luck", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Health_Boost")) {
                                             path = "Configuration.Passive.Buff.Identifier_Health_Boost";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Identifier_Health_Boost", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Regeneration")) {
                                             path = "Configuration.Passive.Buff.Identifier_Regeneration";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Identifier_Regeneration", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Saturation")) {
                                             path = "Configuration.Passive.Buff.Identifier_Saturation";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Identifier_Saturation", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Speed")) {
                                             path = "Configuration.Passive.Buff.Identifier_Speed";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Identifier_Speed", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Water_Breathing")) {
                                             path = "Configuration.Passive.Buff.Identifier_Water_Breathing";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Identifier_Water_Breathing", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Haste")) {
                                             path = "Configuration.Passive.Buff.Identifier_Haste";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Buff.Identifier_Haste", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          }
                                       }
                                    } else if (statsData.equalsIgnoreCase("Debuff")) {
                                       abilityWeaponDataSection = statsDataSection.getConfigurationSection(statsData);
                                       var13 = abilityWeaponDataSection.getKeys(false).iterator();

                                       while(var13.hasNext()) {
                                          statsIdentifierLevel = (String)var13.next();
                                          if (statsIdentifierLevel.equalsIgnoreCase("Format")) {
                                             path = "Configuration.Passive.Debuff.Format";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Debuff.Format", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Weak")) {
                                             path = "Configuration.Passive.Debuff.Identifier_Weak";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Debuff.Identifier_Weak", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Slow")) {
                                             path = "Configuration.Passive.Debuff.Identifier_Slow";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Debuff.Identifier_Slow", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Blind")) {
                                             path = "Configuration.Passive.Debuff.Identifier_Blind";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Debuff.Identifier_Blind", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Confuse")) {
                                             path = "Configuration.Passive.Debuff.Identifier_Confuse";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Debuff.Identifier_Confuse", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Starve")) {
                                             path = "Configuration.Passive.Debuff.Identifier_Starve";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Debuff.Identifier_Starve", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Toxic")) {
                                             path = "Configuration.Passive.Debuff.Identifier_Toxic";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Debuff.Identifier_Toxic", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Glow")) {
                                             path = "Configuration.Passive.Debuff.Identifier_Glow";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Debuff.Identifier_Glow", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Fatigue")) {
                                             path = "Configuration.Passive.Debuff.Identifier_Fatigue";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Debuff.Identifier_Fatigue", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Wither")) {
                                             path = "Configuration.Passive.Debuff.Identifier_Wither";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Debuff.Identifier_Wither", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Unluck")) {
                                             path = "Configuration.Passive.Debuff.Identifier_Unluck";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Passive.Debuff.Identifier_Unluck", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          }
                                       }
                                    }
                                 }
                              }
                           } else if (data.equalsIgnoreCase("Power")) {
                              statsDataSection = dataSection.getConfigurationSection(data);
                              var10 = statsDataSection.getKeys(false).iterator();

                              while(true) {
                                 while(true) {
                                    if (!var10.hasNext()) {
                                       continue label853;
                                    }

                                    statsData = (String)var10.next();
                                    if (statsData.equalsIgnoreCase("Enable_Message_Cooldown")) {
                                       path = "Configuration.Power.Enable_Message_Cooldown";
                                       modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                       config.set("Configuration.Power.Enable_Message_Cooldown", modifierDefenseAffectThorn);
                                    } else if (statsData.equalsIgnoreCase("Format")) {
                                       path = "Configuration.Power.Format";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Power.Format", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Click_Left")) {
                                       path = "Configuration.Power.Click_Left";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Power.Click_Left", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Click_Right")) {
                                       path = "Configuration.Power.Click_Right";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Power.Click_Right", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Click_Shift_Left")) {
                                       path = "Configuration.Power.Click_Shift_Left";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Power.Click_Shift_Left", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Click_Shift_Right")) {
                                       path = "Configuration.Power.Click_Shift_Right";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Power.Click_Shift_Right", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Projectile")) {
                                       abilityWeaponDataSection = statsDataSection.getConfigurationSection(statsData);
                                       var13 = abilityWeaponDataSection.getKeys(false).iterator();

                                       while(var13.hasNext()) {
                                          statsIdentifierLevel = (String)var13.next();
                                          if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Arrow")) {
                                             path = "Configuration.Power.Projectile.Identifier_Arrow";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Power.Projectile.Identifier_Arrow", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Snow_Ball")) {
                                             path = "Configuration.Power.Projectile.Identifier_Snow_Ball";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Power.Projectile.Identifier_Snow_Ball", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Egg")) {
                                             path = "Configuration.Power.Projectile.Identifier_Egg";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Power.Projectile.Identifier_Egg", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Flame_Arrow")) {
                                             path = "Configuration.Power.Projectile.Identifier_Flame_Arrow";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Power.Projectile.Identifier_Flame_Arrow", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Flame_Ball")) {
                                             path = "Configuration.Power.Projectile.Identifier_Flame_Ball";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Power.Projectile.Identifier_Flame_Ball", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Flame_Egg")) {
                                             path = "Configuration.Power.Projectile.Identifier_Flame_Egg";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Power.Projectile.Identifier_Flame_Egg", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Small_Fireball")) {
                                             path = "Configuration.Power.Projectile.Identifier_Small_Fireball";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Power.Projectile.Identifier_Small_Fireball", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Large_Fireball")) {
                                             path = "Configuration.Power.Projectile.Identifier_Large_Fireball";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Power.Projectile.Identifier_Large_Fireball", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Wither_Skull")) {
                                             path = "Configuration.Power.Projectile.Identifier_Wither_Skull";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Power.Projectile.Identifier_Wither_Skull", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          }
                                       }
                                    } else if (statsData.equalsIgnoreCase("Special")) {
                                       abilityWeaponDataSection = statsDataSection.getConfigurationSection(statsData);
                                       var13 = abilityWeaponDataSection.getKeys(false).iterator();

                                       while(var13.hasNext()) {
                                          statsIdentifierLevel = (String)var13.next();
                                          if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Blink")) {
                                             path = "Configuration.Power.Special.Identifier_Blink";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Power.Special.Identifier_Blink", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Fissure")) {
                                             path = "Configuration.Power.Special.Identifier_Fissure";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Power.Special.Identifier_Fissure", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Ice_Spikes")) {
                                             path = "Configuration.Power.Special.Identifier_Ice_Spikes";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Power.Special.Identifier_Ice_Spikes", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Amaterasu")) {
                                             path = "Configuration.Power.Special.Identifier_Amaterasu";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Power.Special.Identifier_Amaterasu", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Identifier_Nero_Beam")) {
                                             path = "Configuration.Power.Special.Identifier_Nero_Beam";
                                             abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(statsIdentifierLevel);
                                             config.set("Configuration.Power.Special.Identifier_Nero_Beam", TextUtil.colorful(abilityWeaponIdentifierWither));
                                          }
                                       }
                                    }
                                 }
                              }
                           } else if (data.equalsIgnoreCase("Element")) {
                              statsDataSection = dataSection.getConfigurationSection(data);
                              var10 = statsDataSection.getKeys(false).iterator();

                              while(var10.hasNext()) {
                                 statsData = (String)var10.next();
                                 if (statsData.equalsIgnoreCase("Format")) {
                                    path = "Configuration.Element.Format";
                                    statsIdentifierLevel = statsDataSection.getString(statsData);
                                    config.set("Configuration.Element.Format", TextUtil.colorful(statsIdentifierLevel));
                                 }
                              }
                           } else if (!data.equalsIgnoreCase("Socket")) {
                              if (data.equalsIgnoreCase("Requirement")) {
                                 statsDataSection = dataSection.getConfigurationSection(data);
                                 var10 = statsDataSection.getKeys(false).iterator();

                                 while(var10.hasNext()) {
                                    statsData = (String)var10.next();
                                    if (statsData.equalsIgnoreCase("Format_Level")) {
                                       path = "Configuration.Requirement.Format_Level";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Requirement.Format_Level", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Format_Permission")) {
                                       path = "Configuration.Requirement.Format_Permission";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Requirement.Format_Permission", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Format_Class")) {
                                       path = "Configuration.Requirement.Format_Class";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Requirement.Format_Class", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Format_Soul_Unbound")) {
                                       path = "Configuration.Requirement.Format_Soul_Unbound";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Requirement.Format_Soul_Unbound", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Format_Soul_Bound")) {
                                       path = "Configuration.Requirement.Format_Soul_Bound";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Requirement.Format_Soul_Bound", TextUtil.colorful(statsIdentifierLevel));
                                    }
                                 }
                              } else if (data.equalsIgnoreCase("Set")) {
                                 statsDataSection = dataSection.getConfigurationSection(data);
                                 var10 = statsDataSection.getKeys(false).iterator();

                                 while(var10.hasNext()) {
                                    statsData = (String)var10.next();
                                    if (statsData.equalsIgnoreCase("Format")) {
                                       path = "Configuration.Set.Format";
                                       List<String> setFormat = statsDataSection.getStringList(statsData);
                                       config.set("Configuration.Set.Format", setFormat);
                                    } else if (statsData.equalsIgnoreCase("Format_Component")) {
                                       path = "Configuration.Set.Format_Component";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Set.Format_Component", ChatColor.stripColor(TextUtil.colorful(statsIdentifierLevel)));
                                    } else if (statsData.equalsIgnoreCase("Format_Bonus")) {
                                       path = "Configuration.Set.Format_Bonus";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Set.Format_Bonus", ChatColor.stripColor(TextUtil.colorful(statsIdentifierLevel)));
                                    } else if (statsData.equalsIgnoreCase("Lore_Component_Active")) {
                                       path = "Configuration.Set.Lore_Component_Active";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Set.Lore_Component_Active", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Lore_Component_Inactive")) {
                                       path = "Configuration.Set.Lore_Component_Inactive";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Set.Lore_Component_Inactive", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Lore_Bonus_Active")) {
                                       path = "Configuration.Set.Lore_Bonus_Active";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Set.Lore_Bonus_Active", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Lore_Bonus_Inactive")) {
                                       path = "Configuration.Set.Lore_Bonus_Inactive";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Set.Lore_Bonus_Inactive", TextUtil.colorful(statsIdentifierLevel));
                                    }
                                 }
                              } else if (data.equalsIgnoreCase("Misc")) {
                                 statsDataSection = dataSection.getConfigurationSection(data);
                                 var10 = statsDataSection.getKeys(false).iterator();

                                 while(var10.hasNext()) {
                                    statsData = (String)var10.next();
                                    if (statsData.equalsIgnoreCase("Enable_Particle_Potion")) {
                                       path = "Configuration.Misc.Enable_Particle_Potion";
                                       modifierDefenseAffectThorn = statsDataSection.getBoolean(statsData);
                                       config.set("Configuration.Misc.Enable_Particle_Potion", modifierDefenseAffectThorn);
                                    }
                                 }
                              }
                           } else {
                              statsDataSection = dataSection.getConfigurationSection(data);
                              var10 = statsDataSection.getKeys(false).iterator();

                              while(true) {
                                 while(true) {
                                    if (!var10.hasNext()) {
                                       continue label853;
                                    }

                                    statsData = (String)var10.next();
                                    if (statsData.equalsIgnoreCase("Format_Slot")) {
                                       path = "Configuration.Socket.Format_Slot";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Socket.Format_Slot", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Format_Slot_Empty")) {
                                       path = "Configuration.Socket.Format_Slot_Empty";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Socket.Format_Slot_Empty", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Format_Slot_Locked")) {
                                       path = "Configuration.Socket.Format_Slot_Locked";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Socket.Format_Slot_Locked", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Type_Item_Weapon")) {
                                       path = "Configuration.Socket.Type_Item_Weapon";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Socket.Type_Item_Weapon", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Type_Item_Armor")) {
                                       path = "Configuration.Socket.Type_Item_Armor";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Socket.Type_Item_Armor", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Type_Item_Universal")) {
                                       path = "Configuration.Socket.Type_Item_Universal";
                                       statsIdentifierLevel = statsDataSection.getString(statsData);
                                       config.set("Configuration.Socket.Type_Item_Universal", TextUtil.colorful(statsIdentifierLevel));
                                    } else if (statsData.equalsIgnoreCase("Cost_Socket")) {
                                       path = "Configuration.Socket.Cost_Socket";
                                       modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                       config.set("Configuration.Socket.Cost_Socket", Math.max(0.0D, modifierScaleExpArmor));
                                    } else if (statsData.equalsIgnoreCase("Cost_Unlock")) {
                                       path = "Configuration.Socket.Cost_Unlock";
                                       modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                       config.set("Configuration.Socket.Cost_Unlock", Math.max(0.0D, modifierScaleExpArmor));
                                    } else if (statsData.equalsIgnoreCase("Cost_Desocket")) {
                                       path = "Configuration.Socket.Cost_Desocket";
                                       modifierScaleExpArmor = statsDataSection.getDouble(statsData);
                                       config.set("Configuration.Socket.Cost_Desocket", Math.max(0.0D, modifierScaleExpArmor));
                                    } else if (statsData.equalsIgnoreCase("Item")) {
                                       abilityWeaponDataSection = statsDataSection.getConfigurationSection(statsData);
                                       var13 = abilityWeaponDataSection.getKeys(false).iterator();

                                       while(var13.hasNext()) {
                                          statsIdentifierLevel = (String)var13.next();
                                          ConfigurationSection itemSection;
                                          ItemStack socketItemRodRemove;
                                          if (statsIdentifierLevel.equalsIgnoreCase("Rod_Unlock")) {
                                             path = "Configuration.Socket.Item.Rod_Unlock";
                                             itemSection = abilityWeaponDataSection.getConfigurationSection(statsIdentifierLevel);
                                             socketItemRodRemove = this.loadItemStack(itemSection);
                                             config.set("Configuration.Socket.Item.Rod_Unlock", socketItemRodRemove);
                                          } else if (statsIdentifierLevel.equalsIgnoreCase("Rod_Remove")) {
                                             path = "Configuration.Socket.Item.Rod_Remove";
                                             itemSection = abilityWeaponDataSection.getConfigurationSection(statsIdentifierLevel);
                                             socketItemRodRemove = this.loadItemStack(itemSection);
                                             config.set("Configuration.Socket.Item.Rod_Remove", socketItemRodRemove);
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private final void loadOldConfig(FileConfiguration config, FileConfiguration source) {
      Iterator var4 = source.getKeys(false).iterator();

      while(var4.hasNext()) {
         String data = (String)var4.next();
         String path;
         boolean miscEnableParticlePotion;
         if (data.equalsIgnoreCase("Metrics_Message")) {
            path = "Configuration.Metrics.Message";
            miscEnableParticlePotion = source.getBoolean(data);
            config.set("Configuration.Metrics.Message", miscEnableParticlePotion);
         } else if (data.equalsIgnoreCase("Hook_Message")) {
            path = "Configuration.Hook.Message";
            miscEnableParticlePotion = source.getBoolean(data);
            config.set("Configuration.Hook.Message", miscEnableParticlePotion);
         } else {
            String requirementFormatSoulBound;
            if (data.equalsIgnoreCase("Code_Tooltip")) {
               path = "Configuration.Utility.Tooltip";
               requirementFormatSoulBound = source.getString(data);
               config.set("Configuration.Utility.Tooltip", requirementFormatSoulBound);
            } else if (data.equalsIgnoreCase("Hook_Message")) {
               path = "Configuration.Utility.Currency";
               requirementFormatSoulBound = source.getString(data);
               config.set("Configuration.Utility.Currency", requirementFormatSoulBound);
            } else {
               double statsMaxLevelValue;
               if (data.equalsIgnoreCase("Effect_Range")) {
                  path = "Configuration.Effect.Range";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Effect.Range", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("List_Content")) {
                  path = "Configuration.List.Content";
                  int listContent = source.getInt(data);
                  config.set("Configuration.List.Content", listContent);
               } else if (data.equalsIgnoreCase("Enable_Vanilla_Modifier")) {
                  path = "Configuration.Modifier.Enable_Vanilla_Damage";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Enable_Vanilla_Damage", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Enable_Vanilla_Formula_Armor")) {
                  path = "Configuration.Modifier.Enable_Vanilla_Formula_Armor";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Enable_Vanilla_Formula_Armor", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Enable_CustomLore_Modifier")) {
                  path = "Configuration.Modifier.Enable_Custom_Modifier";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Enable_Custom_Modifier", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Enable_Flat_Damage")) {
                  path = "Configuration.Modifier.Enable_Flat_Damage";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Enable_Flat_Damage", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Enable_Balancing_System")) {
                  path = "Configuration.Modifier.Enable_Balancing_System";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Enable_Balancing_System", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Enable_Balancing_MobDamage")) {
                  path = "Configuration.Modifier.Enable_Balancing_Mob";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Enable_Balancing_Mob", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Custom_Critical")) {
                  path = "Configuration.Modifier.Enable_Custom_Critical";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Enable_Custom_Critical", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Custom_Mob_Modifier")) {
                  path = "Configuration.Modifier.Enable_Custom_Mob_Damage";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Enable_Custom_Mob_Damage", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Defense_Affect_Entity_Explosion")) {
                  path = "Configuration.Modifier.Defense_Affect_Entity_Explosion";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Defense_Affect_Entity_Explosion", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Defense_Affect_Block_Explosion")) {
                  path = "Configuration.Modifier.Defense_Affect_Block_Explosion";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Defense_Affect_Block_Explosion", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Defense_Affect_Custom")) {
                  path = "Configuration.Modifier.Defense_Affect_Custom";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Defense_Affect_Custom", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Defense_Affect_Contact")) {
                  path = "Configuration.Modifier.Defense_Affect_Contact";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Defense_Affect_Contact", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Defense_Affect_Fall")) {
                  path = "Configuration.Modifier.Defense_Affect_Fall";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Defense_Affect_Fall", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Defense_Affect_Falling_Block")) {
                  path = "Configuration.Modifier.Defense_Affect_Falling_Block";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Defense_Affect_Falling_Block", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Defense_Affect_Thorns")) {
                  path = "Configuration.Modifier.Defense_Affect_Thorn";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Modifier.Defense_Affect_Thorn", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Critical_Attack")) {
                  path = "Configuration.Modifier.Critical_Attack_Type";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Modifier.Critical_Attack_Type", requirementFormatSoulBound);
               } else if (data.equalsIgnoreCase("Modus_Value")) {
                  path = "Configuration.Modifier.Modus_Value";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Modifier.Modus_Value", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Scale_Damage_Vanilla")) {
                  path = "Configuration.Modifier.Scale_Damage_Vanilla";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Modifier.Scale_Damage_Vanilla", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Scale_Damage_Custom")) {
                  path = "Configuration.Modifier.Scale_Damage_Custom";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Modifier.Scale_Damage_Custom", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Scale_Damage_Overall")) {
                  path = "Configuration.Modifier.Scale_Damage_Overall";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Modifier.Scale_Damage_Overall", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Scale_Defense_Overall")) {
                  path = "Configuration.Modifier.Scale_Defense_Overall";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Modifier.Scale_Defense_Overall", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Scale_Defense_Physic")) {
                  path = "Configuration.Modifier.Scale_Defense_Physic";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Modifier.Scale_Defense_Physic", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Scale_Absorb_Effect")) {
                  path = "Configuration.Modifier.Scale_Absorb_Effect";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Modifier.Scale_Absorb_Effect", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Scale_Mob_Damage_Receive")) {
                  path = "Configuration.Modifier.Scale_Mob_Damage_Receive";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Modifier.Scale_Mob_Damage_Receive", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Scale_Exp_OffHand")) {
                  path = "Configuration.Modifier.Scale_Exp_OffHand";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Modifier.Scale_Exp_OffHand", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Scale_Exp_Armor")) {
                  path = "Configuration.Modifier.Scale_Exp_Armor";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Modifier.Scale_Exp_Armor", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Exp_Player")) {
                  path = "Configuration.Drop.Exp_Player";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Drop.Exp_Player", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Exp_Mobs")) {
                  path = "Configuration.Drop.Exp_Mobs";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Drop.Exp_Mobs", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Support_Level_Type")) {
                  path = "Configuration.Support.Type_Level";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Support.Type_Level", requirementFormatSoulBound);
               } else if (data.equalsIgnoreCase("Support_Class_Type")) {
                  path = "Configuration.Support.Type_Class";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Support.Type_Class", requirementFormatSoulBound);
               } else if (data.equalsIgnoreCase("Enable_Item_Broken")) {
                  path = "Configuration.Stats.Enable_Item_Broken";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Stats.Enable_Item_Broken", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Enable_Stats_Max_Health")) {
                  path = "Configuration.Stats.Enable_Max_Health";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Stats.Enable_Max_Health", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Stats_Format")) {
                  path = "Configuration.Stats.Format_Value";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Format_Value", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Stats_Exp_Format")) {
                  path = "Configuration.Stats.Format_Exp";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Format_Exp", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Positive_Value")) {
                  path = "Configuration.Stats.Lore_Positive_Value";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Lore_Positive_Value", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Negative_Value")) {
                  path = "Configuration.Stats.Lore_Negative_Value";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Lore_Negative_Value", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Range_Symbol")) {
                  path = "Configuration.Stats.Lore_Range_Symbol";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Lore_Range_Symbol", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Divider_Symbol")) {
                  path = "Configuration.Stats.Lore_Divider_Symbol";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Lore_Divider_Symbol", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Multiplier_Symbol")) {
                  path = "Configuration.Stats.Lore_Multiplier_Symbol";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Lore_Multiplier_Symbol", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Scale_OffHand_Value")) {
                  path = "Configuration.Stats.Scale_OffHand_Value";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Stats.Scale_OffHand_Value", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Scale_Absorb_Effect")) {
                  path = "Configuration.Stats.Scale_Up_Value";
                  statsMaxLevelValue = source.getDouble(data);
                  config.set("Configuration.Stats.Scale_Up_Value", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Max_Level_Value")) {
                  path = "Configuration.Stats.Max_Level_Value";
                  statsMaxLevelValue = (double)source.getInt(data);
                  config.set("Configuration.Stats.Max_Level_Value", statsMaxLevelValue);
               } else if (data.equalsIgnoreCase("Damage")) {
                  path = "Configuration.Stats.Identifier_Damage";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Damage", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Penetration")) {
                  path = "Configuration.Stats.Identifier_Penetration";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Penetration", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("PvP_Damage")) {
                  path = "Configuration.Stats.Identifier_PvP_Damage";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_PvP_Damage", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("PvE_Damage")) {
                  path = "Configuration.Stats.Identifier_PvE_Damage";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_PvE_Damage", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Defense")) {
                  path = "Configuration.Stats.Identifier_Defense";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Defense", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("PvP_Defense")) {
                  path = "Configuration.Stats.Identifier_PvP_Defense";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_PvP_Defense", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("PvE_Defense")) {
                  path = "Configuration.Stats.Identifier_PvE_Defense";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_PvE_Defense", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Health")) {
                  path = "Configuration.Stats.Identifier_Health";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Health", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Health_Regen")) {
                  path = "Configuration.Stats.Identifier_Health_Regen";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Health_Regen", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Stamina_Max")) {
                  path = "Configuration.Stats.Identifier_Stamina_Max";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Stamina_Max", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Stamina_Regen")) {
                  path = "Configuration.Stats.Identifier_Stamina_Regen";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Stamina_Regen", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Attack_AoE_Radius")) {
                  path = "Configuration.Stats.Identifier_Attack_AoE_Radius";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Attack_AoE_Radius", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Attack_AoE_Damage")) {
                  path = "Configuration.Stats.Identifier_Attack_AoE_Damage";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Attack_AoE_Damage", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Critical_Chance")) {
                  path = "Configuration.Stats.Identifier_Critical_Chance";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Critical_Chance", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Critical_Damage")) {
                  path = "Configuration.Stats.Identifier_Critical_Damage";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Critical_Damage", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Block_Amount")) {
                  path = "Configuration.Stats.Identifier_Block_Amount";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Block_Amount", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Block_Rate")) {
                  path = "Configuration.Stats.Identifier_Block_Rate";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Block_Rate", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Hit_Rate")) {
                  path = "Configuration.Stats.Identifier_Hit_Rate";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Hit_Rate", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Dodge_Rate")) {
                  path = "Configuration.Stats.Identifier_Dodge_Rate";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Dodge_Rate", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Durability")) {
                  path = "Configuration.Stats.Identifier_Durability";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Durability", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Level")) {
                  path = "Configuration.Stats.Identifier_Level";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Stats.Identifier_Level", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Ability_Format")) {
                  path = "Configuration.Ability.Format";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Format", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Enable_OffHand_Ability")) {
                  path = "Configuration.Ability.Weapon.Enable_OffHand";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Ability.Weapon.Enable_OffHand", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Flame")) {
                  path = "Configuration.Ability.Weapon.Identifier_Flame";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Flame", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Poison")) {
                  path = "Configuration.Ability.Weapon.Identifier_Poison";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Poison", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Wither")) {
                  path = "Configuration.Ability.Weapon.Identifier_Wither";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Wither", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Lightning")) {
                  path = "Configuration.Ability.Weapon.Identifier_Lightning";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Lightning", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Cannibalism")) {
                  path = "Configuration.Ability.Weapon.Identifier_Cannibalism";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Cannibalism", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Vampirism")) {
                  path = "Configuration.Ability.Weapon.Identifier_Vampirism";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Vampirism", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Freeze")) {
                  path = "Configuration.Ability.Weapon.Identifier_Freeze";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Freeze", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Roots")) {
                  path = "Configuration.Ability.Weapon.Identifier_Roots";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Roots", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Curse")) {
                  path = "Configuration.Ability.Weapon.Identifier_Curse";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Curse", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Slowness")) {
                  path = "Configuration.Ability.Weapon.Identifier_Slowness";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Slowness", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Fatigue")) {
                  path = "Configuration.Ability.Weapon.Identifier_Fatigue";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Fatigue", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Nausea")) {
                  path = "Configuration.Ability.Weapon.Identifier_Nausea";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Nausea", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Weakness")) {
                  path = "Configuration.Ability.Weapon.Identifier_Weakness";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Weakness", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Blind")) {
                  path = "Configuration.Ability.Weapon.Identifier_Blind";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Blind", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Hungry")) {
                  path = "Configuration.Ability.Weapon.Identifier_Hungry";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Hungry", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Levitation")) {
                  path = "Configuration.Ability.Weapon.Identifier_Levitation";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Levitation", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Bad_Luck")) {
                  path = "Configuration.Ability.Weapon.Identifier_Bad_Luck";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Bad_Luck", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Harm")) {
                  path = "Configuration.Ability.Weapon.Identifier_Harm";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Harm", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Flame_Wheel")) {
                  path = "Configuration.Ability.Weapon.Identifier_Flame_Wheel";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Flame_Wheel", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Air_Shock")) {
                  path = "Configuration.Ability.Weapon.Identifier_Air_Shock";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Air_Shock", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Dark_Flame")) {
                  path = "Configuration.Ability.Weapon.Identifier_Dark_Flame";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Dark_Flame", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Dark_Impact")) {
                  path = "Configuration.Ability.Weapon.Identifier_Dark_Impact";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Dark_Impact", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Venom_Spread")) {
                  path = "Configuration.Ability.Weapon.Identifier_Venom_Spread";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Venom_Spread", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Venom_Blast")) {
                  path = "Configuration.Ability.Weapon.Identifier_Venom_Blast";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Venom_Blast", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Bubble_Deflector")) {
                  path = "Configuration.Ability.Weapon.Identifier_Bubble_Deflector";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Ability.Weapon.Identifier_Bubble_Deflector", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Enable_Buffs_Calculation")) {
                  path = "Configuration.Passive.Enable_Grade_Calculation";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Passive.Enable_Grade_Calculation", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Enable_Buffs_Hand")) {
                  path = "Configuration.Passive.Enable_Hand";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Passive.Enable_Hand", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Buffs_Format")) {
                  path = "Configuration.Passive.Buff.Format";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Format", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Strength")) {
                  path = "Configuration.Passive.Buff.Identifier_Strength";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Identifier_Strength", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Protection")) {
                  path = "Configuration.Passive.Buff.Identifier_Protection";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Identifier_Protection", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Vision")) {
                  path = "Configuration.Passive.Buff.Identifier_Vision";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Identifier_Vision", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Jump")) {
                  path = "Configuration.Passive.Buff.Identifier_Jump";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Identifier_Jump", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Absorb")) {
                  path = "Configuration.Passive.Buff.Identifier_Absorb";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Identifier_Absorb", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Fire_Resist")) {
                  path = "Configuration.Passive.Buff.Identifier_Fire_Resistance";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Identifier_Fire_Resistance", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Invisibility")) {
                  path = "Configuration.Passive.Buff.Identifier_Invisibility";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Identifier_Invisibility", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Luck")) {
                  path = "Configuration.Passive.Buff.Identifier_Luck";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Identifier_Luck", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Health_Boost")) {
                  path = "Configuration.Passive.Buff.Identifier_Health_Boost";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Identifier_Health_Boost", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Regeneration")) {
                  path = "Configuration.Passive.Buff.Identifier_Regeneration";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Identifier_Regeneration", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Saturation")) {
                  path = "Configuration.Passive.Buff.Identifier_Saturation";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Identifier_Saturation", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Speed")) {
                  path = "Configuration.Passive.Buff.Identifier_Speed";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Identifier_Speed", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Water_Breathing")) {
                  path = "Configuration.Passive.Buff.Identifier_Water_Breathing";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Identifier_Water_Breathing", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Haste")) {
                  path = "Configuration.Passive.Buff.Identifier_Haste";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Buff.Identifier_Haste", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Debuffs_Format")) {
                  path = "Configuration.Passive.Debuff.Format";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Debuff.Format", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Debuff_Weak")) {
                  path = "Configuration.Passive.Debuff.Identifier_Weak";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Debuff.Identifier_Weak", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Debuff_Slow")) {
                  path = "Configuration.Passive.Debuff.Identifier_Slow";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Debuff.Identifier_Slow", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Debuff_Blind")) {
                  path = "Configuration.Passive.Debuff.Identifier_Blind";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Debuff.Identifier_Blind", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Debuff_Confuse")) {
                  path = "Configuration.Passive.Debuff.Identifier_Confuse";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Debuff.Identifier_Confuse", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Debuff_Starve")) {
                  path = "Configuration.Passive.Debuff.Identifier_Starve";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Debuff.Identifier_Starve", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Debuff_Toxic")) {
                  path = "Configuration.Passive.Debuff.Identifier_Toxic";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Debuff.Identifier_Toxic", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Debuff_Fatigue")) {
                  path = "Configuration.Passive.Debuff.Identifier_Fatigue";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Debuff.Identifier_Fatigue", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Debuff_Wither")) {
                  path = "Configuration.Passive.Debuff.Identifier_Wither";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Debuff.Identifier_Wither", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Debuff_Unluck")) {
                  path = "Configuration.Passive.Debuff.Identifier_Unluck";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Passive.Debuff.Identifier_Unluck", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Enable_Power_Message")) {
                  path = "Configuration.Power.Enable_Message_Cooldown";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Power.Enable_Message_Cooldown", miscEnableParticlePotion);
               } else if (data.equalsIgnoreCase("Power_Format")) {
                  path = "Configuration.Power.Format";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Format", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Left_Click")) {
                  path = "Configuration.Power.Click_Left";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Click_Left", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Right_Click")) {
                  path = "Configuration.Power.Click_Right";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Click_Right", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Shift_Left_Click")) {
                  path = "Configuration.Power.Click_Shift_Left";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Click_Shift_Left", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Shift_Right_Click")) {
                  path = "Configuration.Power.Click_Shift_Right";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Click_Shift_Right", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Projectile_Arrow")) {
                  path = "Configuration.Power.Projectile.Identifier_Arrow";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Projectile.Identifier_Arrow", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Projectile_SnowBall")) {
                  path = "Configuration.Power.Projectile.Identifier_Snow_Ball";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Projectile.Identifier_Snow_Ball", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Projectile_Egg")) {
                  path = "Configuration.Power.Projectile.Identifier_Egg";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Projectile.Identifier_Egg", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Projectile_Flame_Arrow")) {
                  path = "Configuration.Power.Projectile.Identifier_Flame_Arrow";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Projectile.Identifier_Flame_Arrow", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Projectile_Flame_Ball")) {
                  path = "Configuration.Power.Projectile.Identifier_Flame_Ball";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Projectile.Identifier_Flame_Ball", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Projectile_Flame_Egg")) {
                  path = "Configuration.Power.Projectile.Identifier_Flame_Egg";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Projectile.Identifier_Flame_Egg", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Projectile_Small_Fireball")) {
                  path = "Configuration.Power.Projectile.Identifier_Small_Fireball";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Projectile.Identifier_Small_Fireball", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Projectile_Large_Fireball")) {
                  path = "Configuration.Power.Projectile.Identifier_Large_Fireball";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Projectile.Identifier_Large_Fireball", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Projectile_WitherSkull")) {
                  path = "Configuration.Power.Projectile.Identifier_Wither_Skull";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Projectile.Identifier_Wither_Skull", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Special_Blink")) {
                  path = "Configuration.Power.Special.Identifier_Blink";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Special.Identifier_Blink", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Special_Fissure")) {
                  path = "Configuration.Power.Special.Identifier_Fissure";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Special.Identifier_Fissure", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Special_Ice_Spikes")) {
                  path = "Configuration.Power.Special.Identifier_Ice_Spikes";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Special.Identifier_Ice_Spikes", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Special_Amaterasu")) {
                  path = "Configuration.Power.Special.Identifier_Amaterasu";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Special.Identifier_Amaterasu", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Special_Nero_Beam")) {
                  path = "Configuration.Power.Special.Identifier_Nero_Beam";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Power.Special.Identifier_Nero_Beam", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Element_Format")) {
                  path = "Configuration.Element.Format";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Element.Format", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Format_Socket")) {
                  path = "Configuration.Socket.Format_Slot";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Socket.Format_Slot", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Socket_Empty")) {
                  path = "Configuration.Socket.Format_Slot_Empty";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Socket.Format_Slot_Empty", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Format_Requirement_Level")) {
                  path = "Configuration.Requirement.Format_Level";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Requirement.Format_Level", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Format_Requirement_Permission")) {
                  path = "Configuration.Requirement.Format_Permission";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Requirement.Format_Permission", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Format_Requirement_Class")) {
                  path = "Configuration.Requirement.Format_Class";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Requirement.Format_Class", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Format_Requirement_Soulbound_Unbound")) {
                  path = "Configuration.Requirement.Format_Soul_Unbound";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Requirement.Format_Soul_Unbound", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Format_Requirement_Soulbound_Bound")) {
                  path = "Configuration.Requirement.Format_Soul_Bound";
                  requirementFormatSoulBound = source.getString(data);
                  config.set("Configuration.Requirement.Format_Soul_Bound", TextUtil.colorful(requirementFormatSoulBound));
               } else if (data.equalsIgnoreCase("Enable_Particle_Potion")) {
                  path = "Configuration.Misc.Enable_Particle_Potion";
                  miscEnableParticlePotion = source.getBoolean(data);
                  config.set("Configuration.Misc.Enable_Particle_Potion", miscEnableParticlePotion);
               }
            }
         }
      }

   }

   private final void loadConfigColor(FileConfiguration config) {
      String pathStatsColor = "Configuration.Stats.Color";
      String pathStatsColorValue = "Configuration.Stats.Color_Value";
      String pathStatsColorExpCurrent = "Configuration.Stats.Color_Exp_Current";
      String pathStatsColorExpUp = "Configuration.Stats.Color_Exp_Up";
      String pathPassiveBuffColor = "Configuration.Passive.Buff.Color";
      String pathPassiveDebuffColor = "Configuration.Passive.Debuff.Color";
      String pathPowerColorClick = "Configuration.Power.Color_Click";
      String pathPowerColorType = "Configuration.Power.Color_Type";
      String pathPowerColorCooldown = "Configuration.Power.Color_Cooldown";
      String pathAbilityColor = "Configuration.Ability.Color";
      String pathAbilityColorPercent = "Configuration.Ability.Color_Percent";
      String pathElementColor = "Configuration.Element.Color";
      String pathElementColorValue = "Configuration.Element.Color_Value";
      String pathSocketColorSlot = "Configuration.Socket.Color_Slot";
      String pathRequirementSoulBound = "Configuration.Requirement.Color_Soul_Bound";
      String pathRequirementLevel = "Configuration.Requirement.Color_Level";
      String pathRequirementPermission = "Configuration.Requirement.Color_Permission";
      String pathRequirementClass = "Configuration.Requirement.Color_Class";
      String keyStatsColor = DataUtil.keyGen(this.getStatsFormatValue(), "<stats>");
      String keyStatsColorValue = DataUtil.keyGen(this.getStatsFormatValue(), "<value>");
      String keyStatsColorExpCurrent = DataUtil.keyGen(this.getStatsFormatExp(), "<exp>");
      String keyStatsColorExpUp = DataUtil.keyGen(this.getStatsFormatExp(), "<up>");
      String keyPassiveBuffColor = DataUtil.keyGen(this.getPassiveBuffFormat(), "<buff>");
      String keyPassiveDebuffColor = DataUtil.keyGen(this.getPassiveDebuffFormat(), "<debuff>");
      String keyPowerColorClick = DataUtil.keyGen(this.getPowerFormat(), "<click>");
      String keyPowerColorType = DataUtil.keyGen(this.getPowerFormat(), "<type>");
      String keyPowerColorCooldown = DataUtil.keyGen(this.getPowerFormat(), "<cooldown>");
      String keyAbilityColor = DataUtil.keyGen(this.getAbilityFormat(), "<ability>");
      String keyAbilityColorPercent = DataUtil.keyGen(this.getAbilityFormat(), "<chance>");
      String keyElementColor = DataUtil.keyGen(this.getElementFormat(), "<element>");
      String keyElementColorValue = DataUtil.keyGen(this.getElementFormat(), "<value>");
      String keySocketColorSlot = DataUtil.keyGen(this.getSocketFormatSlot(), "<slot>");
      String keyRequirementSoulBound = DataUtil.keyGen(this.getRequirementFormatSoulBound(), "<player>");
      String keyRequirementLevel = DataUtil.keyGen(this.getRequirementFormatLevel(), "<level>");
      String keyRequirementPermission = DataUtil.keyGen(this.getRequirementFormatPermission(), "<permission>");
      String keyRequirementClass = DataUtil.keyGen(this.getRequirementFormatClass(), "<class>");
      config.set("Configuration.Stats.Color", keyStatsColor);
      config.set("Configuration.Stats.Color_Value", keyStatsColorValue);
      config.set("Configuration.Stats.Color_Exp_Current", keyStatsColorExpCurrent);
      config.set("Configuration.Stats.Color_Exp_Up", keyStatsColorExpUp);
      config.set("Configuration.Passive.Buff.Color", keyPassiveBuffColor);
      config.set("Configuration.Passive.Debuff.Color", keyPassiveDebuffColor);
      config.set("Configuration.Power.Color_Click", keyPowerColorClick);
      config.set("Configuration.Power.Color_Type", keyPowerColorType);
      config.set("Configuration.Power.Color_Cooldown", keyPowerColorCooldown);
      config.set("Configuration.Ability.Color", keyAbilityColor);
      config.set("Configuration.Ability.Color_Percent", keyAbilityColorPercent);
      config.set("Configuration.Element.Color", keyElementColor);
      config.set("Configuration.Element.Color_Value", keyElementColorValue);
      config.set("Configuration.Socket.Color_Slot", keySocketColorSlot);
      config.set("Configuration.Requirement.Color_Soul_Bound", keyRequirementSoulBound);
      config.set("Configuration.Requirement.Color_Level", keyRequirementLevel);
      config.set("Configuration.Requirement.Color_Permission", keyRequirementPermission);
      config.set("Configuration.Requirement.Color_Class", keyRequirementClass);
   }

   private final ItemStack loadItemStack(ConfigurationSection dataSection) {
      List<String> itemLores = new ArrayList();
      String itemName = null;
      Material itemMaterial = Material.STONE;
      short itemData = 0;
      boolean itemShiny = false;
      Iterator var8 = dataSection.getKeys(false).iterator();

      while(var8.hasNext()) {
         String data = (String)var8.next();
         if (data.equalsIgnoreCase("Display_Name")) {
            itemName = dataSection.getString(data);
         } else if (data.equalsIgnoreCase("Data")) {
            itemData = (short)dataSection.getInt(data);
         } else if (data.equalsIgnoreCase("Material")) {
            String dataMaterialText = dataSection.getString(data);
            Material dataMaterial = Material.getMaterial(dataMaterialText);
            itemMaterial = dataMaterial != null ? dataMaterial : itemMaterial;
         } else if (data.equalsIgnoreCase("Lores")) {
            List<String> dataLores = dataSection.getStringList(data);
            itemLores.addAll(dataLores);
         } else if (data.equalsIgnoreCase("Shiny")) {
            itemShiny = dataSection.getBoolean(data);
         }
      }

      if (itemMaterial != null) {
         MaterialEnum materialEnum = MaterialEnum.getMaterialEnum(itemMaterial, itemData);
         if (materialEnum != null) {
            ItemStack item = EquipmentUtil.createItem(materialEnum, itemName, 1, itemLores);
            if (itemShiny) {
               EquipmentUtil.shiny(item);
            }

            EquipmentUtil.colorful(item);
            return item;
         }
      }

      return null;
   }

   private final void moveOldFile() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathSource = "config.yml";
      String pathTarget = dataManager.getPath("Path_File_Config");
      File fileSourcce = FileUtil.getFile(this.plugin, "config.yml");
      File fileTarget = FileUtil.getFile(this.plugin, pathTarget);
      if (fileSourcce.exists()) {
         FileUtil.moveFileSilent(fileSourcce, fileTarget);
      }

   }

   // $FF: synthetic method
   MainConfig(MyItems var1, MainConfig var2) {
      this(var1);
   }

   private static class MainConfigHelper {
      private static MainConfig instance;

      static {
         MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
         instance = new MainConfig(plugin, (MainConfig)null);
         instance.setup();
      }
   }
}
