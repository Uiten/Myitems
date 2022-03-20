package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeEffect;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import com.praya.agarthalib.utility.CombatUtil;
import com.praya.agarthalib.utility.PlayerUtil;
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

public class AbilityWeaponTired extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeEffect {
   private static final String ABILITY_ID = "Tired";

   private AbilityWeaponTired(MyItems plugin, String id) {
      super(plugin, id);
   }

   public static final AbilityWeaponTired getInstance() {
      return AbilityWeaponTired.AbilityTiredHelper.instance;
   }

   public String getKeyLore() {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.getAbilityWeaponIdentifierTired();
   }

   public List<String> getDescription() {
      return null;
   }

   public int getMaxGrade() {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Tired");
      return abilityWeaponProperties.getMaxGrade();
   }

   public double getBaseBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Tired");
      double baseBonusDamage = (double)grade * abilityWeaponProperties.getScaleBaseBonusDamage();
      return baseBonusDamage;
   }

   public double getBasePercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Tired");
      double basePercentDamage = (double)grade * abilityWeaponProperties.getScaleBasePercentDamage();
      return basePercentDamage;
   }

   public int getEffectDuration(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Tired");
      return abilityWeaponProperties.getTotalDuration(grade);
   }

   public void cast(Entity caster, Entity target, int grade, double damage) {
      MainConfig mainConfig = MainConfig.getInstance();
      if (target instanceof LivingEntity) {
         LivingEntity victims = (LivingEntity)target;
         Location location = victims.getEyeLocation();
         int duration = this.getEffectDuration(grade);
         Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
         Bridge.getBridgeParticle().playParticle(players, ParticleEnum.CRIT, location, 25, 0.5D, 0.5D, 0.5D, 0.5D);
         Bridge.getBridgeSound().playSound(players, location, SoundEnum.BLOCK_COMPARATOR_CLICK, 0.7F, 1.0F);
         CombatUtil.applyPotion(victims, PotionEffectType.SLOW_DIGGING, duration, 3);
      }

   }

   // $FF: synthetic method
   AbilityWeaponTired(MyItems var1, String var2, AbilityWeaponTired var3) {
      this(var1, var2);
   }

   private static class AbilityTiredHelper {
      private static final AbilityWeaponTired instance;

      static {
         MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
         instance = new AbilityWeaponTired(plugin, "Tired", (AbilityWeaponTired)null);
      }
   }
}
