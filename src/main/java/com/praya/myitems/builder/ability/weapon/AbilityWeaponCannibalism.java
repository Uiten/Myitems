package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import com.praya.agarthalib.utility.EntityUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;

public class AbilityWeaponCannibalism extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage {
   private static final String ABILITY_ID = "Cannibalism";

   private AbilityWeaponCannibalism(MyItems plugin, String id) {
      super(plugin, id);
   }

   public static final AbilityWeaponCannibalism getInstance() {
      return AbilityWeaponCannibalism.AbilityCannibalismHelper.instance;
   }

   public String getKeyLore() {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.getAbilityWeaponIdentifierCannibalism();
   }

   public List<String> getDescription() {
      return null;
   }

   public int getMaxGrade() {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Cannibalism");
      return abilityWeaponProperties.getMaxGrade();
   }

   public double getBaseBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Cannibalism");
      double baseBonusDamage = (double)grade * abilityWeaponProperties.getScaleBaseBonusDamage();
      return baseBonusDamage;
   }

   public double getBasePercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Cannibalism");
      double basePercentDamage = (double)grade * abilityWeaponProperties.getScaleBasePercentDamage();
      return basePercentDamage;
   }

   public void cast(Entity caster, Entity target, int grade, double damage) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      PluginManager pluginManager = plugin.getPluginManager();
      LanguageManager lang = pluginManager.getLanguageManager();
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
         Location location = attacker.getEyeLocation();
         MessageBuild messageAttacker = lang.getMessage(attacker, "Ability_Cannibalism_Attacker");
         MessageBuild messageVictims = lang.getMessage(victims, "Ability_Cannibalism_Victims");
         int maxGrade = this.getMaxGrade();
         int food = 10 * (grade / maxGrade);
         Player playerAttacker;
         int foodAttacker;
         if (victims instanceof Player) {
            playerAttacker = PlayerUtil.parse(victims);
            foodAttacker = playerAttacker.getFoodLevel();
            food = Math.max(foodAttacker, food);
            playerAttacker.setFoodLevel(foodAttacker - food);
            messageVictims.sendMessage(victims, "food", String.valueOf(food));
            Bridge.getBridgeSound().playSound(playerAttacker, location, SoundEnum.ENTITY_PLAYER_BURP, 0.7F, 1.0F);
         }

         if (EntityUtil.isPlayer(attacker)) {
            playerAttacker = PlayerUtil.parse(attacker);
            foodAttacker = playerAttacker.getFoodLevel();
            food = foodAttacker + food > 20 ? 20 - foodAttacker : food;
            playerAttacker.setFoodLevel(foodAttacker + food);
            messageAttacker.sendMessage(playerAttacker, "food", String.valueOf(food));
            Bridge.getBridgeSound().playSound(playerAttacker, location, SoundEnum.ENTITY_PLAYER_BURP, 0.7F, 1.0F);
         }
      }

   }

   // $FF: synthetic method
   AbilityWeaponCannibalism(MyItems var1, String var2, AbilityWeaponCannibalism var3) {
      this(var1, var2);
   }

   private static class AbilityCannibalismHelper {
      private static final AbilityWeaponCannibalism instance;

      static {
         MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
         instance = new AbilityWeaponCannibalism(plugin, "Cannibalism", (AbilityWeaponCannibalism)null);
      }
   }
}
