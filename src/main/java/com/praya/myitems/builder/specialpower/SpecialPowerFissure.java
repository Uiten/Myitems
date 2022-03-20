package com.praya.myitems.builder.specialpower;

import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.agarthalib.utility.CombatUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.abs.SpecialPower;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SpecialPowerFissure extends SpecialPower {
   private static final PowerSpecialEnum special;

   static {
      special = PowerSpecialEnum.FISSURE;
   }

   public SpecialPowerFissure() {
      super(special);
   }

   public final int getDuration() {
      return special.getDuration();
   }

   public final void cast(final LivingEntity caster) {
      MyItems plugin = (MyItems)JavaPlugin.getProvidingPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      MainConfig mainConfig = MainConfig.getInstance();
      final Location loc = caster.getLocation();
      Location horizontalLoc = new Location(loc.getWorld(), 0.0D, 0.0D, 0.0D, loc.getYaw(), 0.0F);
      final Vector aim = horizontalLoc.getDirection().normalize();
      final int duration = this.getDuration();
      double weaponDamage = statsManager.getLoreStatsWeapon(caster).getDamage();
      final double skillDamage = special.getBaseAdditionalDamage() + special.getBasePercentDamage() * weaponDamage / 100.0D;
      final Set<LivingEntity> listEntity = new HashSet();
      final Collection<Player> players = PlayerUtil.getNearbyPlayers(loc, mainConfig.getEffectRange());
      loc.add(aim);
      Bridge.getBridgeSound().playSound(players, loc, SoundEnum.ITEM_FIRECHARGE_USE, 1.0F, 1.0F);
      (new BukkitRunnable() {
         final int limit = 12;
         final double range = 2.0D;
         int t = 0;

         public void run() {
            if (this.t >= 12) {
               this.cancel();
            } else {
               Bridge.getBridgeParticle().playParticle(players, ParticleEnum.FLAME, loc, 25, 0.15D, 0.25D, 0.15D, 0.019999999552965164D);
               Bridge.getBridgeParticle().playParticle(players, ParticleEnum.LAVA, loc, 10, 0.2D, 0.15D, 0.2D, 0.05000000074505806D);
               Bridge.getBridgeSound().playSound(players, loc, SoundEnum.BLOCK_FIRE_AMBIENT, 0.8F, 1.0F);
               Iterator var2 = CombatUtil.getNearbyUnits(loc, 2.0D).iterator();

               while(var2.hasNext()) {
                  LivingEntity unit = (LivingEntity)var2.next();
                  if (!unit.equals(caster) && !listEntity.contains(unit)) {
                     listEntity.add(unit);
                     unit.setFireTicks(duration);
                     CombatUtil.skillDamage(caster, unit, skillDamage);
                  }
               }

               loc.add(aim);
               ++this.t;
            }
         }
      }).runTaskTimer(plugin, 0L, 1L);
   }
}
