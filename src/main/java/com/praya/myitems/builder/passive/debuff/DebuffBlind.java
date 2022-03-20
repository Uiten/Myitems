package com.praya.myitems.builder.passive.debuff;

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

public class DebuffBlind extends PassiveEffect {
   private static final PassiveEffectEnum debuff;

   static {
      debuff = PassiveEffectEnum.BLIND;
   }

   public DebuffBlind() {
      super(debuff, 1);
   }

   public DebuffBlind(int grade) {
      super(debuff, grade);
   }

   public final void cast(Player player) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (!passiveEffectManager.isPassiveEffectCooldown(debuff, player)) {
         PotionEffectType potionType = this.getPotion();
         int duration = this.getDuration();
         long cooldown = this.getCooldown();
         boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
         PotionEffect potion = PotionUtil.createPotion(potionType, duration, this.grade, true, isEnableParticle);
         player.addPotionEffect(potion);
         passiveEffectManager.setPassiveEffectCooldown(debuff, player, cooldown);
      }

   }

   private final int getDuration() {
      MainConfig mainConfig = MainConfig.getInstance();
      return (int)((double)(mainConfig.getPassivePeriodEffect() * this.grade) / 2.5D + 20.0D);
   }

   private final long getCooldown() {
      MainConfig mainConfig = MainConfig.getInstance();
      return (long)((double)(MathUtil.convertTickToMilis(mainConfig.getPassivePeriodEffect()) * (long)debuff.getMaxGrade()) / 2.5D);
   }
}
