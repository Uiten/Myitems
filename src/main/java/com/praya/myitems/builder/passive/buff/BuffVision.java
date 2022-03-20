package com.praya.myitems.builder.passive.buff;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.PotionUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.abs.PassiveEffect;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BuffVision extends PassiveEffect {
   private static final PassiveEffectEnum buff;

   static {
      buff = PassiveEffectEnum.VISION;
   }

   public BuffVision() {
      super(buff, 1);
   }

   public BuffVision(int grade) {
      super(buff, grade);
   }

   public final void cast(Player player) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (!passiveEffectManager.isPassiveEffectCooldown(buff, player)) {
         PotionEffectType potionType = this.getPotion();
         long cooldown = this.getCooldown();
         int duration = this.getDuration();
         boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
         PotionEffect potion = PotionUtil.createPotion(potionType, duration, this.grade, true, isEnableParticle);
         player.addPotionEffect(potion);
         passiveEffectManager.setPassiveEffectCooldown(buff, player, cooldown);
      }

   }

   private final int getDuration() {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.getPassivePeriodEffect() * this.grade + 20;
   }

   private final long getCooldown() {
      MainConfig mainConfig = MainConfig.getInstance();
      return MathUtil.convertTickToMilis(mainConfig.getPassivePeriodEffect()) * (long)PassiveEffectEnum.HASTE.getMaxGrade();
   }
}
