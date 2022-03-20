package com.praya.myitems.builder.passive.buff;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.agarthalib.utility.PotionUtil;
import com.praya.myitems.builder.abs.PassiveEffect;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BuffJump extends PassiveEffect {
   private static final PassiveEffectEnum buff;

   static {
      buff = PassiveEffectEnum.JUMP;
   }

   public BuffJump() {
      super(buff, 1);
   }

   public BuffJump(int grade) {
      super(buff, grade);
   }

   public final void cast(Player player) {
      MainConfig mainConfig = MainConfig.getInstance();
      PotionEffectType potionType = this.getPotion();
      boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
      PotionEffect potion = PotionUtil.createPotion(potionType, 96000, this.grade, true, isEnableParticle);
      player.addPotionEffect(potion);
   }
}
