package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeEffect;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import com.praya.agarthalib.utility.CombatUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.PotionUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.GameManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.Collection;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class AbilityWeaponPoison extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeEffect {
   private static final String ABILITY_ID = "Poison";

   private AbilityWeaponPoison(MyItems plugin, String id) {
      super(plugin, id);
   }

   public static final AbilityWeaponPoison getInstance() {
      return AbilityWeaponPoison.AbilityPoisonHelper.instance;
   }

   public String getKeyLore() {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.getAbilityWeaponIdentifierPoison();
   }

   public List<String> getDescription() {
      return null;
   }

   public int getMaxGrade() {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Poison");
      return abilityWeaponProperties.getMaxGrade();
   }

   public double getBaseBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Poison");
      double baseBonusDamage = (double)grade * abilityWeaponProperties.getScaleBaseBonusDamage();
      return baseBonusDamage;
   }

   public double getBasePercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Poison");
      double basePercentDamage = (double)grade * abilityWeaponProperties.getScaleBasePercentDamage();
      return basePercentDamage;
   }

   public int getEffectDuration(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Poison");
      int baseDuration = abilityWeaponProperties.getBaseDurationEffect();
      int scaleDuration = abilityWeaponProperties.getScaleDurationEffect();
      int duration = abilityWeaponProperties.getTotalDuration(grade);

      for(int amplifier = 0; duration > baseDuration + scaleDuration * (5 + amplifier); ++amplifier) {
         duration -= scaleDuration * 2;
      }

      return duration;
   }

   private final int getEffectAmplifier(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Poison");
      int baseDuration = abilityWeaponProperties.getBaseDurationEffect();
      int scaleDuration = abilityWeaponProperties.getScaleDurationEffect();
      int duration = abilityWeaponProperties.getTotalDuration(grade);

      int amplifier;
      for(amplifier = 0; duration > baseDuration + scaleDuration * (5 + amplifier); ++amplifier) {
         duration -= scaleDuration * 2;
      }

      return amplifier;
   }

   public void cast(Entity caster, Entity target, int grade, double damage) {
      MainConfig mainConfig = MainConfig.getInstance();
      if (target instanceof LivingEntity) {
         LivingEntity victims = (LivingEntity)target;
         Location location = victims.getEyeLocation();
         PotionEffectType potion = PotionUtil.getPoisonType(victims);
         int duration = this.getEffectDuration(grade);
         int amplifier = potion.equals(PotionEffectType.WITHER) ? this.getEffectAmplifier(grade) + 1 : this.getEffectAmplifier(grade);
         Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
         Bridge.getBridgeParticle().playParticle(players, ParticleEnum.SLIME, location, 20, 0.3D, 0.3D, 0.3D, 0.30000001192092896D);
         Bridge.getBridgeSound().playSound(players, location, SoundEnum.ENCHANT_THORNS_HIT, 1.0F, 1.0F);
         CombatUtil.applyPotion(victims, potion, duration, amplifier);
      }

   }

   // $FF: synthetic method
   AbilityWeaponPoison(MyItems var1, String var2, AbilityWeaponPoison var3) {
      this(var1, var2);
   }

   private static class AbilityPoisonHelper {
      private static final AbilityWeaponPoison instance;

      static {
         MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
         instance = new AbilityWeaponPoison(plugin, "Poison", (AbilityWeaponPoison)null);
      }
   }
}
