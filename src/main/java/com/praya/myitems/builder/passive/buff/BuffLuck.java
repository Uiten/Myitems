package com.praya.myitems.builder.passive.buff;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.agarthalib.utility.PotionUtil;
import com.praya.agarthalib.utility.ServerUtil;
import com.praya.myitems.builder.abs.PassiveEffect;
import com.praya.myitems.config.plugin.MainConfig;
import core.praya.agarthalib.enums.main.VersionNMS;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BuffLuck extends PassiveEffect {
   private static final PassiveEffectEnum buff;

   static {
      buff = PassiveEffectEnum.LUCK;
   }

   public BuffLuck() {
      super(buff, 1);
   }

   public BuffLuck(int grade) {
      super(buff, grade);
   }

   public final void cast(Player player) {
      MainConfig mainConfig = MainConfig.getInstance();
      if (ServerUtil.isCompatible(VersionNMS.V1_9_R1)) {
         PotionEffectType potionType = this.getPotion();
         boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
         PotionEffect potion = PotionUtil.createPotion(potionType, 96000, this.grade, true, isEnableParticle);
         player.addPotionEffect(potion);
      }

   }
}
