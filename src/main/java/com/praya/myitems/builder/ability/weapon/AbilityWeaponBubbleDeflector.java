package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeCastDamage;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeEffect;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import com.praya.agarthalib.utility.CombatUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.PotionUtil;
import com.praya.agarthalib.utility.ProjectileUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.GameManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import org.bukkit.util.Vector;

public class AbilityWeaponBubbleDeflector extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeCastDamage, AbilityWeaponAttributeEffect {
   private static final String ABILITY_ID = "Bubble_Deflector";

   private AbilityWeaponBubbleDeflector(MyItems plugin, String id) {
      super(plugin, id);
   }

   public static final AbilityWeaponBubbleDeflector getInstance() {
      return AbilityWeaponBubbleDeflector.AbilityBubbleDeflectorHelper.instance;
   }

   public String getKeyLore() {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.getAbilityWeaponIdentifierBubbleDeflector();
   }

   public List<String> getDescription() {
      return null;
   }

   public int getMaxGrade() {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bubble_Deflector");
      return abilityWeaponProperties.getMaxGrade();
   }

   public double getBaseBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bubble_Deflector");
      double baseBonusDamage = (double)grade * abilityWeaponProperties.getScaleBaseBonusDamage();
      return baseBonusDamage;
   }

   public double getBasePercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bubble_Deflector");
      double basePercentDamage = (double)grade * abilityWeaponProperties.getScaleBasePercentDamage();
      return basePercentDamage;
   }

   public double getCastBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bubble_Deflector");
      double castBonusDamage = (double)grade * abilityWeaponProperties.getScaleCastBonusDamage();
      return castBonusDamage;
   }

   public double getCastPercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bubble_Deflector");
      double castPercentDamage = (double)grade * abilityWeaponProperties.getScaleCastPercentDamage();
      return castPercentDamage;
   }

   public int getEffectDuration(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bubble_Deflector");
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
         final Location location = ProjectileUtil.isProjectile(caster) ? ProjectileUtil.getDirectLocation(caster) : attacker.getEyeLocation();
         final Vector vector = location.getDirection();
         final int limit = 10 + grade;
         int duration = this.getEffectDuration(grade);
         double range = 1.5D;
         final double bubbleDamage = this.getCastBonusDamage(grade) + damage * (this.getCastPercentDamage(grade) / 100.0D);
         final PotionEffect potionEffect = PotionUtil.createPotion(PotionEffectType.SLOW, duration, 4);
         final Set<LivingEntity> units = new HashSet();
         final Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
         victims.addPotionEffect(potionEffect);
         Bridge.getBridgeParticle().playParticle(players, ParticleEnum.WATER_SPLASH, location, 40, 0.25D, 0.25D, 0.25D, 0.0D);
         Bridge.getBridgeParticle().playParticle(players, ParticleEnum.WATER_WAKE, location, 40, 0.25D, 0.25D, 0.25D, 0.0D);
         Bridge.getBridgeSound().playSound(players, location, SoundEnum.ENTITY_GUARDIAN_FLOP, 5.0F, 1.0F);
         (new BukkitRunnable() {
            int time = 0;

            public void run() {
               if (this.time >= limit) {
                  this.cancel();
               } else {
                  Iterator var2 = CombatUtil.getNearbyUnits(location, 1.5D).iterator();

                  while(var2.hasNext()) {
                     LivingEntity unit = (LivingEntity)var2.next();
                     if (!unit.equals(attacker) && !unit.equals(victims) && !units.contains(unit)) {
                        CombatUtil.areaDamage(attacker, unit, bubbleDamage);
                        unit.addPotionEffect(potionEffect);
                        units.add(unit);
                     }
                  }

                  if (this.time % 2 == 0) {
                     Bridge.getBridgeSound().playSound(players, location, SoundEnum.BLOCK_WATER_AMBIENT, 2.0F, 1.0F);
                  }

                  Bridge.getBridgeParticle().playParticle(players, ParticleEnum.WATER_DROP, location, 25, 0.2D, 0.2D, 0.2D, 0.0D);
                  Bridge.getBridgeParticle().playParticle(players, ParticleEnum.WATER_BUBBLE, location, 25, 0.2D, 0.2D, 0.2D, 0.0D);
                  location.add(vector);
                  ++this.time;
               }
            }
         }).runTaskTimer(plugin, 0L, 1L);
      }

   }

   // $FF: synthetic method
   AbilityWeaponBubbleDeflector(MyItems var1, String var2, AbilityWeaponBubbleDeflector var3) {
      this(var1, var2);
   }

   private static class AbilityBubbleDeflectorHelper {
      private static final AbilityWeaponBubbleDeflector instance;

      static {
         MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
         instance = new AbilityWeaponBubbleDeflector(plugin, "Bubble_Deflector", (AbilityWeaponBubbleDeflector)null);
      }
   }
}
