package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import com.praya.agarthalib.utility.EntityUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;

public class AbilityWeaponVampirism extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage {
   private static final String ABILITY_ID = "Vampirism";

   private AbilityWeaponVampirism(MyItems plugin, String id) {
      super(plugin, id);
   }

   public static final AbilityWeaponVampirism getInstance() {
      return AbilityWeaponVampirism.AbilityVampirismHelper.instance;
   }

   public String getKeyLore() {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.getAbilityWeaponIdentifierVampirism();
   }

   public List<String> getDescription() {
      return null;
   }

   public int getMaxGrade() {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Vampirism");
      return abilityWeaponProperties.getMaxGrade();
   }

   public double getBaseBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Vampirism");
      double baseBonusDamage = (double)grade * abilityWeaponProperties.getScaleBaseBonusDamage();
      return baseBonusDamage;
   }

   public double getBasePercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Vampirism");
      double basePercentDamage = (double)grade * abilityWeaponProperties.getScaleBasePercentDamage();
      return basePercentDamage;
   }

   public void cast(Entity caster, Entity target, int grade, double damage) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
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
         LivingEntity victims = (LivingEntity)target;
         Location location = attacker.getLocation();
         double drain = this.getDrain(attacker, victims, grade, damage);
         double attackerNewHealth = this.getAttackerNewHealth(attacker, victims, drain);
         MessageBuild messageAttacker = lang.getMessage(attacker, "Ability_Vampirism_Attacker");
         MessageBuild messageVictims = lang.getMessage(victims, "Ability_Vampirism_Victims");
         Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
         HashMap<String, String> mapPlaceholder = new HashMap();
         mapPlaceholder.put("health", String.valueOf(MathUtil.roundNumber(drain)));
         Bridge.getBridgeParticle().playParticle(players, ParticleEnum.HEART, location, 10, 0.25D, 0.25D, 0.25D, 0.10000000149011612D);
         Player playerVictims;
         if (EntityUtil.isPlayer(attacker)) {
            playerVictims = PlayerUtil.parse(attacker);
            messageAttacker.sendMessage(playerVictims, mapPlaceholder);
            Bridge.getBridgeSound().playSound(playerVictims, location, SoundEnum.ENTITY_WITCH_DRINK, 1.0F, 1.0F);
         }

         if (EntityUtil.isPlayer(victims)) {
            playerVictims = PlayerUtil.parse(victims);
            messageVictims.sendMessage(playerVictims, mapPlaceholder);
            Bridge.getBridgeSound().playSound(playerVictims, location, SoundEnum.ENTITY_WITCH_DRINK, 1.0F, 1.0F);
         }

         EntityUtil.setHealth(attacker, attackerNewHealth);
      }

   }

   private final double getDrain(LivingEntity attacker, LivingEntity victims, int grade, double damage) {
      double attackerHealth = attacker.getHealth();
      double attackerMaxHealth = attacker.getMaxHealth();
      int maxGrade = this.getMaxGrade();
      double drain = damage * (double)grade / (double)maxGrade;
      if (victims.getHealth() - drain < 0.0D) {
         drain = victims.getHealth();
      }

      if (!(victims instanceof Player)) {
         drain /= 2.0D;
      }

      if (attackerHealth + drain > attackerMaxHealth) {
         drain = attackerMaxHealth - attackerHealth;
      }

      return drain;
   }

   private final double getAttackerNewHealth(LivingEntity attacker, LivingEntity victims, double drain) {
      double attackerHealth = attacker.getHealth();
      double attackerMaxHealth = attacker.getMaxHealth();
      return attackerHealth + drain > attackerMaxHealth ? attackerMaxHealth : attackerHealth + drain;
   }

   // $FF: synthetic method
   AbilityWeaponVampirism(MyItems var1, String var2, AbilityWeaponVampirism var3) {
      this(var1, var2);
   }

   private static class AbilityVampirismHelper {
      private static final AbilityWeaponVampirism instance;

      static {
         MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
         instance = new AbilityWeaponVampirism(plugin, "Vampirism", (AbilityWeaponVampirism)null);
      }
   }
}
