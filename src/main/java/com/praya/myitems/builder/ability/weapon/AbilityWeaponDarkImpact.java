package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
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
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

public class AbilityWeaponDarkImpact extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage {
   private static final String ABILITY_ID = "Dark_Impact";

   private AbilityWeaponDarkImpact(MyItems plugin, String id) {
      super(plugin, id);
   }

   public static final AbilityWeaponDarkImpact getInstance() {
      return AbilityWeaponDarkImpact.AbilityDarkImpactHelper.instance;
   }

   public String getKeyLore() {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.getAbilityWeaponIdentifierDarkImpact();
   }

   public List<String> getDescription() {
      return null;
   }

   public int getMaxGrade() {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Impact");
      return abilityWeaponProperties.getMaxGrade();
   }

   public double getBaseBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Impact");
      double baseBonusDamage = (double)grade * abilityWeaponProperties.getScaleBaseBonusDamage();
      return baseBonusDamage;
   }

   public double getBasePercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Impact");
      double basePercentDamage = (double)grade * abilityWeaponProperties.getScaleBasePercentDamage();
      return basePercentDamage;
   }

   public void cast(Entity caster, Entity target, int grade, double damage) {
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
         LivingEntity victims = (LivingEntity)target;
         Location location = victims.getLocation().add(0.0D, 0.6D, 0.0D);
         Location backLook = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), attacker.getLocation().getYaw(), 0.0F);
         Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
         victims.teleport(backLook);
         CombatUtil.applyPotion(victims, PotionEffectType.BLINDNESS, 20, 1);
         Bridge.getBridgeSound().playSound(players, location, SoundEnum.ITEM_FIRECHARGE_USE, 1.0F, 1.0F);
         double radius = 1.0D;

         for(int i1 = 0; i1 < 180; i1 += 90) {
            double xBase = Math.cos((double)i1) * radius;
            double zBase = Math.sin((double)i1) * radius;

            for(int i2 = 0; i2 < 360; i2 += 9) {
               double x = xBase * Math.cos((double)i2);
               double y = Math.sin((double)i2) * radius;
               double z = zBase * Math.cos((double)i2);
               location.add(x, y, z);
               Bridge.getBridgeParticle().playParticle(players, ParticleEnum.SPELL_WITCH, location, 1, 0.0D, 0.0D, 0.0D, 0.0D);
               location.subtract(x, y, z);
            }
         }
      }

   }

   // $FF: synthetic method
   AbilityWeaponDarkImpact(MyItems var1, String var2, AbilityWeaponDarkImpact var3) {
      this(var1, var2);
   }

   private static class AbilityDarkImpactHelper {
      private static final AbilityWeaponDarkImpact instance;

      static {
         MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
         instance = new AbilityWeaponDarkImpact(plugin, "Dark_Impact", (AbilityWeaponDarkImpact)null);
      }
   }
}
