package com.praya.myitems.builder.specialpower;

import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.agarthalib.utility.BlockUtil;
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
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SpecialPowerIceSpikes extends SpecialPower {
   private static final PowerSpecialEnum special;

   static {
      special = PowerSpecialEnum.ICE_SPIKES;
   }

   public SpecialPowerIceSpikes() {
      super(special);
   }

   public final int getDuration() {
      return special.getDuration();
   }

   public final void cast(final LivingEntity caster) {
      final MyItems plugin = (MyItems)JavaPlugin.getProvidingPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      MainConfig mainConfig = MainConfig.getInstance();
      Location horizontalLoc = new Location(caster.getLocation().getWorld(), 0.0D, 0.0D, 0.0D, caster.getLocation().getYaw(), 0.0F);
      final Vector aim = horizontalLoc.getDirection().multiply(2);
      final int duration = this.getDuration();
      double weaponDamage = statsManager.getLoreStatsWeapon(caster).getDamage();
      final double skillDamage = special.getBaseAdditionalDamage() + special.getBasePercentDamage() * weaponDamage / 100.0D;
      final Collection<Player> players = PlayerUtil.getNearbyPlayers(caster.getLocation(), mainConfig.getEffectRange());
      final Set<LivingEntity> listEntity = new HashSet();
      (new BukkitRunnable() {
         final int limit = 5;
         final int range = 2;
         int t = 0;
         Location loc = caster.getLocation().add(aim);

         public void run() {
            if (this.t >= 5) {
               this.cancel();
            } else {
               Bridge.getBridgeSound().playSound(players, this.loc, SoundEnum.BLOCK_GLASS_BREAK, 0.8F, 1.0F);

               for(int indexSpikes = 0; indexSpikes < 3; ++indexSpikes) {
                  this.loc = this.loc.add(0.0D, (double)indexSpikes, 0.0D);
                  if (this.loc.getBlock().getType().equals(Material.AIR)) {
                     BlockUtil.set(this.loc.getBlock().getLocation());
                     this.loc.getBlock().setType(Material.PACKED_ICE);
                     Iterator var3 = CombatUtil.getNearbyUnits(this.loc, 2.0D).iterator();

                     while(var3.hasNext()) {
                        LivingEntity unit = (LivingEntity)var3.next();
                        if (!unit.equals(caster) && !listEntity.contains(unit)) {
                           listEntity.add(unit);
                           CombatUtil.applyPotion(unit, PotionEffectType.SLOW, duration, 4);
                           CombatUtil.skillDamage(caster, unit, skillDamage);
                           Bridge.getBridgeParticle().playParticle(players, ParticleEnum.SNOW_SHOVEL, this.loc, 10, 0.2D, 0.2D, 0.2D, 0.10000000149011612D);
                        }
                     }

                     (new BukkitRunnable() {
                        final Location iceLoc;

                        {
                           this.iceLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
                        }

                        public void run() {
                           BlockUtil.remove(this.iceLoc.getBlock().getLocation());
                           if (this.iceLoc.getBlock().getType().equals(Material.PACKED_ICE)) {
                              this.iceLoc.getBlock().setType(Material.AIR);
                           }

                        }
                     }).runTaskLater(plugin, 9L);
                  }

                  this.loc = this.loc.subtract(0.0D, (double)indexSpikes, 0.0D);
               }

               this.loc.add(aim);
               ++this.t;
            }
         }
      }).runTaskTimer(plugin, 0L, 3L);
   }
}
