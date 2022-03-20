package com.praya.myitems.utility.customeffect;

import api.praya.myitems.builder.passive.PassiveEffectTypeEnum;
import com.praya.agarthalib.utility.EntityUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.utility.main.CustomEffectUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class CustomEffectFreeze {
   protected static final PassiveEffectTypeEnum customEffect;

   static {
      customEffect = PassiveEffectTypeEnum.FREEZE;
   }

   public static final void cast(LivingEntity livingEntity) {
      if (EntityUtil.isPlayer(livingEntity)) {
         effect(PlayerUtil.parse(livingEntity));
      }

      display(livingEntity);
   }

   public static final void cast(Player player) {
      effect(player);
      display(player);
   }

   public static final void effect(Player player) {
      if (CustomEffectUtil.isRunCustomEffect(player, customEffect)) {
         if (player.getWalkSpeed() >= 0.05F) {
            CustomEffectUtil.setSpeedBase(player, player.getWalkSpeed());
            player.setWalkSpeed(0.0F);
         }
      } else if (CustomEffectUtil.hasSpeedBase(player)) {
         float baseSpeed = CustomEffectUtil.getSpeedBase(player);
         CustomEffectUtil.removeSpeedBase(player);
         player.setWalkSpeed(baseSpeed);
      }

   }

   public static final void display(LivingEntity livingEntity) {
      MainConfig mainConfig = MainConfig.getInstance();
      if (CustomEffectUtil.isRunCustomEffect(livingEntity, customEffect)) {
         Location loc = livingEntity.getLocation();
         Collection<Player> players = PlayerUtil.getNearbyPlayers(loc, mainConfig.getEffectRange());
         Bridge.getBridgeParticle().playParticle(players, ParticleEnum.CLOUD, loc, 10, 0.25D, 0.25D, 0.25D, 0.10000000149011612D);
         Bridge.getBridgeSound().playSound(players, loc, SoundEnum.BLOCK_GLASS_BREAK, 1.0F, 1.0F);
      }

   }
}
