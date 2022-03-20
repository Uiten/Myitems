package com.praya.myitems.builder.passive.buff;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.agarthalib.utility.PotionUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.abs.PassiveEffect;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class BuffHealthBoost extends PassiveEffect {
   private static final PassiveEffectEnum buff;

   static {
      buff = PassiveEffectEnum.HEALTH_BOOST;
   }

   public BuffHealthBoost() {
      super(buff, 1);
   }

   public BuffHealthBoost(int grade) {
      super(buff, grade);
   }

   public final void cast(Player player) {
      MainConfig mainConfig = MainConfig.getInstance();
      PotionEffectType potionType = this.getPotion();
      boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
      PotionEffect potion = PotionUtil.createPotion(potionType, 96000, this.grade, true, isEnableParticle);
      player.addPotionEffect(potion);
   }

   public final void reset(final Player player) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      PotionEffectType potion = this.getPotion();
      final double health = player.getHealth();
      player.removePotionEffect(potion);
      (new BukkitRunnable() {
         public void run() {
            if (player.isOnline()) {
               double maxHealth = player.getMaxHealth();
               player.setHealth(health > maxHealth ? maxHealth : health);
            }

         }
      }).runTaskLater(plugin, 1L);
   }
}
