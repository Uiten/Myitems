package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
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
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AbilityWeaponAirShock extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage {
   private static final String ABILITY_ID = "Air_Shock";

   private AbilityWeaponAirShock(MyItems plugin, String id) {
      super(plugin, id);
   }

   public static final AbilityWeaponAirShock getInstance() {
      return AbilityWeaponAirShock.AbilityAirShockHelper.instance;
   }

   public String getKeyLore() {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.getAbilityWeaponIdentifierAirShock();
   }

   public List<String> getDescription() {
      return null;
   }

   public int getMaxGrade() {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Air_Shock");
      return abilityWeaponProperties.getMaxGrade();
   }

   public double getBaseBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Air_Shock");
      double baseBonusDamage = (double)grade * abilityWeaponProperties.getScaleBaseBonusDamage();
      return baseBonusDamage;
   }

   public double getBasePercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Air_Shock");
      double basePercentDamage = (double)grade * abilityWeaponProperties.getScaleBasePercentDamage();
      return basePercentDamage;
   }

   public void cast(Entity caster, Entity target, int grade, double damage) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      MainConfig mainConfig = MainConfig.getInstance();
      LivingEntity attacker;
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
         Location location = victims.getLocation().add(0.0D, 0.2D, 0.0D);
         int maxGrade = this.getMaxGrade();
         final int duration = 2 + grade;
         double speed = (double)(1 + grade * 2 / maxGrade);
         Vector vector = attacker.getLocation().getDirection().setY(0).normalize();
         final Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
         vector.setY(2).normalize().multiply(speed);
         victims.teleport(location);
         victims.setVelocity(vector);
         Bridge.getBridgeSound().playSound(players, location, SoundEnum.ENTITY_WITHER_SHOOT, 1.0F, 1.0F);
         (new BukkitRunnable() {
            int time = 0;

            public void run() {
               if (this.time >= duration) {
                  this.cancel();
               } else if (victims.isDead()) {
                  this.cancel();
               } else {
                  Location location = victims.getLocation();
                  Bridge.getBridgeParticle().playParticle(players, ParticleEnum.CLOUD, location, 10, 0.5D, 0.25D, 0.5D, 0.15000000596046448D);
                  ++this.time;
               }
            }
         }).runTaskTimer(plugin, 1L, 1L);
      }

   }

   // $FF: synthetic method
   AbilityWeaponAirShock(MyItems var1, String var2, AbilityWeaponAirShock var3) {
      this(var1, var2);
   }

   private static class AbilityAirShockHelper {
      private static final AbilityWeaponAirShock instance;

      static {
         MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
         instance = new AbilityWeaponAirShock(plugin, "Air_Shock", (AbilityWeaponAirShock)null);
      }
   }
}
