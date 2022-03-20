package com.praya.myitems.builder.passive.debuff;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.agarthalib.utility.PotionUtil;
import com.praya.myitems.builder.abs.PassiveEffect;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DebuffFatigue extends PassiveEffect {
   private static final PassiveEffectEnum debuff;

   static {
      debuff = PassiveEffectEnum.FATIGUE;
   }

   public DebuffFatigue() {
      super(debuff, 1);
   }

   public DebuffFatigue(int grade) {
      super(debuff, grade);
   }

   public final void cast(Player player) {
      MainConfig mainConfig = MainConfig.getInstance();
      PotionEffectType potionType = this.getPotion();
      boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
      PotionEffect potion = PotionUtil.createPotion(potionType, 96000, this.grade, true, isEnableParticle);
      player.addPotionEffect(potion);
   }
}
