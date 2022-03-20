package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeCastDamage;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeEffect;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import api.praya.myitems.builder.passive.PassiveEffectTypeEnum;
import com.praya.agarthalib.utility.CombatUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.utility.main.CustomEffectUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.Collection;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityWeaponDarkFlame extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeCastDamage, AbilityWeaponAttributeEffect {
   private static final String ABILITY_ID = "Dark_Flame";

   private AbilityWeaponDarkFlame(MyItems plugin, String id) {
      super(plugin, id);
   }

   public static final AbilityWeaponDarkFlame getInstance() {
      return AbilityWeaponDarkFlame.AbilityDarkFlameHelper.instance;
   }

   public String getKeyLore() {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.getAbilityWeaponIdentifierDarkFlame();
   }

   public List<String> getDescription() {
      return null;
   }

   public int getMaxGrade() {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Flame");
      return abilityWeaponProperties.getMaxGrade();
   }

   public double getBaseBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Flame");
      double baseBonusDamage = (double)grade * abilityWeaponProperties.getScaleBaseBonusDamage();
      return baseBonusDamage;
   }

   public double getBasePercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Flame");
      double basePercentDamage = (double)grade * abilityWeaponProperties.getScaleBasePercentDamage();
      return basePercentDamage;
   }

   public double getCastBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Flame");
      double castBonusDamage = (double)grade * abilityWeaponProperties.getScaleCastBonusDamage();
      return castBonusDamage;
   }

   public double getCastPercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Flame");
      double castPercentDamage = (double)grade * abilityWeaponProperties.getScaleCastPercentDamage();
      return castPercentDamage;
   }

   public int getEffectDuration(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Flame");
      return abilityWeaponProperties.getTotalDuration(grade);
   }

   public void cast(Entity caster, Entity target, int grade, double damage) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      MainConfig mainConfig = MainConfig.getInstance();
      final LivingEntity attacker;
      if (caster instanceof Projectile) {
         Projectile projectile = (Projectile)caster;
         ProjectileSource projectileSource = projectile.getShooter();
         if (projectileSource == null || !(projectileSource instanceof LivingEntity)) {
            return;
         }

         attacker = (LivingEntity)projectileSource;
      } else {
         attacker = (LivingEntity)caster;
      }

      if (target instanceof LivingEntity) {
         final LivingEntity victims = (LivingEntity)target;
         if (!CustomEffectUtil.isRunCustomEffect(victims, PassiveEffectTypeEnum.DARK_FLAME)) {
            Location location = victims.getLocation();
            int effectDuration = this.getEffectDuration(grade);
            int period;
            final int limit = effectDuration / 2;
            long milis = (long)(effectDuration * 50);
            final double flameDamage = this.getCastBonusDamage(grade) + damage * (this.getCastPercentDamage(grade) / 100.0D);
            final Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
            Bridge.getBridgeSound().playSound(players, location, SoundEnum.ITEM_FIRECHARGE_USE, 5.0F, 1.0F);
            CustomEffectUtil.setCustomEffect(victims, milis, PassiveEffectTypeEnum.DARK_FLAME);
            (new BukkitRunnable() {
               int time = 0;

               public void run() {
                  if (this.time >= limit) {
                     this.cancel();
                  } else if (victims.isDead()) {
                     this.cancel();
                  } else {
                     Location location = victims.getLocation().add(0.0D, 0.5D, 0.0D);
                     if (this.time % 5 == 0) {
                        CombatUtil.areaDamage(attacker, victims, flameDamage);
                     }

                     Bridge.getBridgeParticle().playParticle(players, ParticleEnum.SPELL_MOB, location, 20, 0.25D, 0.5D, 0.25D, 0.0D);
                     Bridge.getBridgeSound().playSound(players, location, SoundEnum.BLOCK_FIRE_AMBIENT, 5.0F, 1.0F);
                     ++this.time;
                  }
               }
            }).runTaskTimer(plugin, 2L, 2L);
         }
      }

   }

   // $FF: synthetic method
   AbilityWeaponDarkFlame(MyItems var1, String var2, AbilityWeaponDarkFlame var3) {
      this(var1, var2);
   }

   private static class AbilityDarkFlameHelper {
      private static final AbilityWeaponDarkFlame instance;

      static {
         MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
         instance = new AbilityWeaponDarkFlame(plugin, "Dark_Flame", (AbilityWeaponDarkFlame)null);
      }
   }
}
