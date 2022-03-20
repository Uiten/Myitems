package com.praya.myitems.builder.specialpower;

import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.agarthalib.utility.LocationUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.builder.abs.SpecialPower;
import com.praya.myitems.config.plugin.MainConfig;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class SpecialPowerBlink extends SpecialPower {
   private static final PowerSpecialEnum special;

   static {
      special = PowerSpecialEnum.BLINK;
   }

   public SpecialPowerBlink() {
      super(special);
   }

   public final void cast(LivingEntity caster) {
      MainConfig mainConfig = MainConfig.getInstance();
      Location locationCasterEye = caster.getEyeLocation();
      Location locationBlink = LocationUtil.getLineBlock(locationCasterEye, 20, 20.0D);
      double height = caster.getEyeHeight();
      Collection<Player> players = PlayerUtil.getNearbyPlayers(locationCasterEye, mainConfig.getEffectRange());
      locationBlink.setYaw(locationCasterEye.getYaw());
      locationBlink.setPitch(locationCasterEye.getPitch());
      locationBlink.subtract(0.0D, height, 0.0D);
      if (locationBlink.getBlock().getType().isSolid()) {
         locationBlink.add(0.0D, height, 0.0D);
      }

      caster.teleport(locationBlink);
      Bridge.getBridgeParticle().playParticle(players, ParticleEnum.PORTAL, locationBlink, 25, 0.5D, 0.25D, 0.5D);
      Bridge.getBridgeSound().playSound(players, locationBlink, SoundEnum.BLOCK_PORTAL_TRAVEL, 0.6F, 1.0F);
   }
}
