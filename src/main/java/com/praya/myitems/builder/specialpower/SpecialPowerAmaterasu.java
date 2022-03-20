package com.praya.myitems.builder.specialpower;

import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.agarthalib.utility.CombatUtil;
import com.praya.agarthalib.utility.LocationUtil;
import com.praya.agarthalib.utility.MathUtil;
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

public class SpecialPowerAmaterasu extends SpecialPower {
   private static final PowerSpecialEnum special;

   static {
      special = PowerSpecialEnum.AMATERASU;
   }

   public SpecialPowerAmaterasu() {
      super(special);
   }

   public final int getDuration() {
      return special.getDuration();
   }

   public final int getLimit() {
      return this.getDuration() / 2;
   }

   public final double getRange() {
      return 3.0D;
   }

   public final void cast(final LivingEntity caster) {
      MyItems plugin = (MyItems)JavaPlugin.getProvidingPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      MainConfig mainConfig = MainConfig.getInstance();
      final Location loc = LocationUtil.getLineLocation(caster, caster.getEyeLocation(), 0.5D, 2.0D, 20, 20.0D, false);
      final int limit = this.getLimit();
      final double range = this.getRange();
      double weaponDamage = statsManager.getLoreStatsWeapon(caster).getDamage();
      final double skillDamage = special.getBaseAdditionalDamage() + special.getBasePercentDamage() * weaponDamage / 100.0D;
      final Set<LivingEntity> listEntity = new HashSet();
      final Collection<Player> players = PlayerUtil.getNearbyPlayers(loc, mainConfig.getEffectRange());
      Bridge.getBridgeSound().playSound(players, loc, SoundEnum.ITEM_FIRECHARGE_USE, 5.0F, 1.0F);
      (new BukkitRunnable() {
         int t = 0;

         public void run() {
            if (this.t >= limit) {
               this.cancel();
            } else {
               Bridge.getBridgeParticle().playParticle(players, ParticleEnum.SPELL_MOB, loc, 25, 1.5D, 0.75D, 1.5D, 0.0D);
               Bridge.getBridgeSound().playSound(players, loc, SoundEnum.BLOCK_FIRE_AMBIENT, 5.0F, 1.0F);
               Iterator var2 = CombatUtil.getNearbyUnits(loc, range).iterator();

               LivingEntity victim;
               while(var2.hasNext()) {
                  victim = (LivingEntity)var2.next();
                  if (!victim.equals(caster) && !listEntity.contains(victim)) {
                     listEntity.add(victim);
                  }
               }

               var2 = listEntity.iterator();

               while(var2.hasNext()) {
                  victim = (LivingEntity)var2.next();
                  if (!victim.isDead()) {
                     Location victimLoc = victim.getLocation().add(0.0D, 0.5D, 0.0D);
                     Bridge.getBridgeParticle().playParticle(players, ParticleEnum.SPELL_MOB, victimLoc, 12, 0.25D, 0.5D, 0.25D, 0.0D);
                     Bridge.getBridgeSound().playSound(players, victimLoc, SoundEnum.BLOCK_FIRE_AMBIENT, 0.75F, 1.0F);
                     if (MathUtil.isDividedBy((double)this.t, 10.0D)) {
                        CombatUtil.areaDamage(caster, victim, skillDamage);
                     }
                  }
               }

               ++this.t;
            }
         }
      }).runTaskTimer(plugin, 0L, 2L);
   }
}
