package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeEffect;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import api.praya.myitems.builder.passive.PassiveEffectTypeEnum;
import com.praya.agarthalib.utility.BlockUtil;
import com.praya.agarthalib.utility.CombatUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.utility.main.CustomEffectUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityWeaponFreeze extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeEffect {
   private static final String ABILITY_ID = "Freeze";

   private AbilityWeaponFreeze(MyItems plugin, String id) {
      super(plugin, id);
   }

   public static final AbilityWeaponFreeze getInstance() {
      return AbilityWeaponFreeze.AbilityFreezeHelper.instance;
   }

   public String getKeyLore() {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.getAbilityWeaponIdentifierFreeze();
   }

   public List<String> getDescription() {
      return null;
   }

   public int getMaxGrade() {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Freeze");
      return abilityWeaponProperties.getMaxGrade();
   }

   public double getBaseBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Freeze");
      double baseBonusDamage = (double)grade * abilityWeaponProperties.getScaleBaseBonusDamage();
      return baseBonusDamage;
   }

   public double getBasePercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Freeze");
      double basePercentDamage = (double)grade * abilityWeaponProperties.getScaleBasePercentDamage();
      return basePercentDamage;
   }

   public int getEffectDuration(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Freeze");
      return abilityWeaponProperties.getTotalDuration(grade);
   }

   public void cast(Entity caster, Entity target, int grade, double damage) {
      final MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      PluginManager pluginManager = plugin.getPluginManager();
      LanguageManager lang = pluginManager.getLanguageManager();
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
         Location location = victims.getLocation();
         final int duration = this.getEffectDuration(grade);
         long milis = (long)(duration * 50);
         double seconds = (double)(duration / 20);
         MessageBuild messageAttacker = lang.getMessage(attacker, "Ability_Freeze_Attacker");
         MessageBuild messageVictims = lang.getMessage(victims, "Ability_Freeze_Victims");
         Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
         HashMap<String, String> mapPlaceholder = new HashMap();
         mapPlaceholder.put("seconds", String.valueOf(MathUtil.roundNumber(seconds)));
         victims.setVelocity(victims.getVelocity().multiply(0.0D));
         messageAttacker.sendMessage(attacker, mapPlaceholder);
         messageVictims.sendMessage(victims, mapPlaceholder);
         Bridge.getBridgeParticle().playParticle(players, ParticleEnum.CLOUD, location, 10, 0.25D, 0.25D, 0.25D, 0.10000000149011612D);
         Bridge.getBridgeSound().playSound(players, location, SoundEnum.BLOCK_GLASS_BREAK, 1.0F, 1.0F);
         if (victims instanceof Player) {
            CustomEffectUtil.setCustomEffect(victims, milis, PassiveEffectTypeEnum.FREEZE);
         } else {
            CombatUtil.applyPotion(victims, PotionEffectType.SLOW, duration, 100, true, mainConfig.isMiscEnableParticlePotion());
         }

         (new BukkitRunnable() {
            public void run() {
               final Collection<Location> locationIce = new ArrayList();
               Location location = victims.getLocation();

               for(int i = 0; i < 2; ++i) {
                  if (i > 0) {
                     location.add(0.0D, 1.0D, 0.0D);
                  }

                  Block block = location.getBlock();
                  Material material = block.getType();
                  if (material.equals(Material.AIR)) {
                     Location locationBlock = block.getLocation();
                     locationIce.add(locationBlock);
                     block.setType(Material.PACKED_ICE);
                     BlockUtil.set(locationBlock);
                  }
               }

               (new BukkitRunnable() {
                  public void run() {
                     Iterator var2 = locationIce.iterator();

                     while(var2.hasNext()) {
                        Location location = (Location)var2.next();
                        Block block = location.getBlock();
                        Material material = block.getType();
                        Location locationBlock = block.getLocation();
                        BlockUtil.remove(locationBlock);
                        if (material.equals(Material.PACKED_ICE)) {
                           block.setType(Material.AIR);
                        }
                     }

                  }
               }).runTaskLater(plugin, (long)duration);
            }
         }).runTaskLater(plugin, 1L);
      }

   }

   // $FF: synthetic method
   AbilityWeaponFreeze(MyItems var1, String var2, AbilityWeaponFreeze var3) {
      this(var1, var2);
   }

   private static class AbilityFreezeHelper {
      private static final AbilityWeaponFreeze instance;

      static {
         MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
         instance = new AbilityWeaponFreeze(plugin, "Freeze", (AbilityWeaponFreeze)null);
      }
   }
}
