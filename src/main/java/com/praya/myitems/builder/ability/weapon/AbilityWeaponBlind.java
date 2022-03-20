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

public class AbilityWeaponBlind extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeEffect {
   private static final String ABILITY_ID = "Blind";

   private AbilityWeaponBlind(MyItems plugin, String id) {
      super(plugin, id);
   }

   public static final AbilityWeaponBlind getInstance() {
      return AbilityWeaponBlind.AbilityBlindHelper.instance;
   }

   public String getKeyLore() {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.getAbilityWeaponIdentifierBlind();
   }

   public List<String> getDescription() {
      return null;
   }

   public int getMaxGrade() {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Blind");
      return abilityWeaponProperties.getMaxGrade();
   }

   public double getBaseBonusDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Blind");
      double baseBonusDamage = (double)grade * abilityWeaponProperties.getScaleBaseBonusDamage();
      return baseBonusDamage;
   }

   public double getBasePercentDamage(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Blind");
      double basePercentDamage = (double)grade * abilityWeaponProperties.getScaleBasePercentDamage();
      return basePercentDamage;
   }

   public int getEffectDuration(int grade) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Blind");
      return abilityWeaponProperties.getTotalDuration(grade);
   }

   public void cast(Entity caster, Entity target, int grade, double damage) {
      MainConfig mainConfig = MainConfig.getInstance();
      if (target instanceof LivingEntity) {
         LivingEntity victims = (LivingEntity)target;
         Location location = victims.getEyeLocation();
         int duration = this.getEffectDuration(grade);
         Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
         Bridge.getBridgeParticle().playParticle(players, ParticleEnum.SMOKE_LARGE, location, 25, 0.5D, 0.5D, 0.5D, 0.5D);
         Bridge.getBridgeSound().playSound(players, location, SoundEnum.ENTITY_CAT_HISS, 0.7F, 1.0F);
         CombatUtil.applyPotion(victims, PotionEffectType.BLINDNESS, duration, 4);
      }

   }

   // $FF: synthetic method
   AbilityWeaponBlind(MyItems var1, String var2, AbilityWeaponBlind var3) {
      this(var1, var2);
   }

   private static class AbilityBlindHelper {
      private static final AbilityWeaponBlind instance;

      static {
         MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
         instance = new AbilityWeaponBlind(plugin, "Blind", (AbilityWeaponBlind)null);
      }
   }
}
