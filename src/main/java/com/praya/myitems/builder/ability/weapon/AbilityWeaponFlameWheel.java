package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeEffect;
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
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityWeaponFlameWheel extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeEffect {
   private static final String ABILITY_ID = "Flame_Wheel";

   private AbilityWeaponFlameWheel(MyItems plugin, String id) {
      super(plugin, id);
   }

   public static final AbilityWeaponFlameWheel getInstance() {
      return AbilityWeaponFlameWheel.AbilityFlameWheelHelper.instance;
   }

   public String getKeyLore() {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.getAbilityWeaponIdentifierFlameWheel();
   }

   public List<String> getDescription() {
      return null;
   }

   public int getMaxGrade() {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Flame_Wheel");
      return abilityWeaponProperties.getMaxGrade();
   }

   public double getBaseBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Flame_Wheel");
      double baseBonusDamage = (double)grade * abilityWeaponProperties.getScaleBaseBonusDamage();
      return baseBonusDamage;
   }

   public double getBasePercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Flame_Wheel");
      double basePercentDamage = (double)grade * abilityWeaponProperties.getScaleBasePercentDamage();
      return basePercentDamage;
   }

   public int getEffectDuration(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Flame_Wheel");
      return abilityWeaponProperties.getTotalDuration(grade);
   }

   public void cast(Entity caster, Entity target, int grade, double damage) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      MainConfig mainConfig = MainConfig.getInstance();
      if (target instanceof LivingEntity) {
         LivingEntity victims = (LivingEntity)target;
         final Location location = victims.getLocation();
         int duration = this.getEffectDuration(grade);
         int limit;
         final Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
         victims.setFireTicks(duration);
         Bridge.getBridgeParticle().playParticle(players, ParticleEnum.LAVA, location, 5, 0.2D, 0.05D, 0.2D, 0.10000000149011612D);
         Bridge.getBridgeSound().playSound(players, location, SoundEnum.ITEM_FIRECHARGE_USE, 1.0F, 1.0F);
         (new BukkitRunnable() {
            double horizontal = 0.0D;
            double vertical = 0.25D;
            int time = 0;
            double x;
            double y;
            double z;

            public void run() {
               if (this.time >= 12) {
                  this.cancel();
               } else {
                  for(int i = 0; i < 3; ++i) {
                     this.x = 0.8D * Math.sin(this.horizontal);
                     this.y = this.vertical;
                     this.z = 0.8D * Math.cos(this.horizontal);
                     location.add(this.x, this.y, this.z);
                     Bridge.getBridgeParticle().playParticle(players, ParticleEnum.FLAME, location, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                     location.subtract(this.x, this.y, this.z);
                     location.add(-this.x, this.y, -this.z);
                     Bridge.getBridgeParticle().playParticle(players, ParticleEnum.FLAME, location, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                     location.subtract(-this.x, this.y, -this.z);
                     this.horizontal += 0.1308996938995747D;
                     this.vertical += 0.05D;
                  }

                  ++this.time;
               }
            }
         }).runTaskTimer(plugin, 0L, 1L);
      }

   }

   // $FF: synthetic method
   AbilityWeaponFlameWheel(MyItems var1, String var2, AbilityWeaponFlameWheel var3) {
      this(var1, var2);
   }

   private static class AbilityFlameWheelHelper {
      private static final AbilityWeaponFlameWheel instance;

      static {
         MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
         instance = new AbilityWeaponFlameWheel(plugin, "Flame_Wheel", (AbilityWeaponFlameWheel)null);
      }
   }
}
