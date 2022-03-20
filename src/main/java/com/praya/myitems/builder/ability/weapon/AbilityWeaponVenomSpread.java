package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeCastDamage;
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
import java.util.Iterator;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityWeaponVenomSpread extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeCastDamage, AbilityWeaponAttributeEffect {
   private static final String ABILITY_ID = "Venom_Spread";

   private AbilityWeaponVenomSpread(MyItems plugin, String id) {
      super(plugin, id);
   }

   public static final AbilityWeaponVenomSpread getInstance() {
      return AbilityWeaponVenomSpread.AbilityVenomSpreadHelper.instance;
   }

   public String getKeyLore() {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.getAbilityWeaponIdentifierVenomSpread();
   }

   public List<String> getDescription() {
      return null;
   }

   public int getMaxGrade() {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Spread");
      return abilityWeaponProperties.getMaxGrade();
   }

   public double getBaseBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Spread");
      double baseBonusDamage = (double)grade * abilityWeaponProperties.getScaleBaseBonusDamage();
      return baseBonusDamage;
   }

   public double getBasePercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Spread");
      double basePercentDamage = (double)grade * abilityWeaponProperties.getScaleBasePercentDamage();
      return basePercentDamage;
   }

   public double getCastBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Spread");
      double castBonusDamage = (double)grade * abilityWeaponProperties.getScaleCastBonusDamage();
      return castBonusDamage;
   }

   public double getCastPercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Spread");
      double castPercentDamage = (double)grade * abilityWeaponProperties.getScaleCastPercentDamage();
      return castPercentDamage;
   }

   public int getEffectDuration(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Spread");
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
         final Location location = victims.getLocation();
         final int duration = this.getEffectDuration(grade);
         int limit;
         double dividen = 2.0D;
         final double spreadDamage = this.getCastBonusDamage(grade) + damage * (this.getCastPercentDamage(grade) / 100.0D);
         final Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
         PotionEffectType potionType = PotionUtil.getPoisonType(victims);
         int amplifier = potionType.equals(PotionEffectType.WITHER) ? 3 : 2;
         PotionEffect potion = PotionUtil.createPotion(potionType, duration, amplifier);
         victims.addPotionEffect(potion);
         Bridge.getBridgeParticle().playParticle(players, ParticleEnum.SLIME, location, 40, 0.25D, 0.5D, 0.25D, 0.0D);
         Bridge.getBridgeSound().playSound(players, location, SoundEnum.BLOCK_SLIME_HIT, 5.0F, 1.0F);
         (new BukkitRunnable() {
            double range = 0.5D;
            int time = 0;

            public void run() {
               if (this.time >= 5) {
                  this.cancel();
               } else {
                  Bridge.getBridgeSound().playSound(players, location, SoundEnum.BLOCK_GRAVEL_BREAK, 5.0F, 1.0F);

                  for(double i = 0.0D; i <= 6.283185307179586D; i += 3.141592653589793D / (2.0D * (double)(1 + this.time))) {
                     double x = this.range * Math.sin(i);
                     double z = this.range * Math.cos(i);
                     location.add(x, 0.0D, z);
                     Bridge.getBridgeParticle().playParticle(players, ParticleEnum.VILLAGER_HAPPY, location, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                     if (this.time < 3) {
                        Bridge.getBridgeParticle().playParticle(players, ParticleEnum.CLOUD, location, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                     }

                     location.subtract(x, 0.0D, z);
                  }

                  Iterator var2 = CombatUtil.getNearbyUnits(location, this.range).iterator();

                  while(var2.hasNext()) {
                     LivingEntity unit = (LivingEntity)var2.next();
                     if (!unit.equals(attacker) && !unit.equals(victims)) {
                        PotionEffectType potionType = PotionUtil.getPoisonType(victims);
                        int grade = potionType.equals(PotionEffectType.WITHER) ? 3 : 2;
                        PotionEffect potion = PotionUtil.createPotion(potionType, grade, duration);
                        unit.addPotionEffect(potion);
                        CombatUtil.areaDamage(attacker, unit, spreadDamage);
                     }
                  }

                  this.range += 0.5D;
                  ++this.time;
               }
            }
         }).runTaskTimer(plugin, 0L, 1L);
      }

   }

   // $FF: synthetic method
   AbilityWeaponVenomSpread(MyItems var1, String var2, AbilityWeaponVenomSpread var3) {
      this(var1, var2);
   }

   private static class AbilityVenomSpreadHelper {
      private static final AbilityWeaponVenomSpread instance;

      static {
         MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
         instance = new AbilityWeaponVenomSpread(plugin, "Venom_Spread", (AbilityWeaponVenomSpread)null);
      }
   }
}
