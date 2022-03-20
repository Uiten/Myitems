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
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SpecialPowerNeroBeam extends SpecialPower {
   private static final PowerSpecialEnum special;

   static {
      special = PowerSpecialEnum.NERO_BEAM;
   }

   public SpecialPowerNeroBeam() {
      super(special);
   }

   public final int getDuration() {
      return special.getDuration();
   }

   public final int getLimit() {
      return 15;
   }

   public final double getBaseRange() {
      return 1.5D;
   }

   public final double getScaleRange() {
      return 0.05D;
   }

   public final double getStartRadius() {
      return 0.2D;
   }

   public final double getScaleRadius() {
      return 0.05D;
   }

   public final void cast(final LivingEntity caster) {
      MyItems plugin = (MyItems)JavaPlugin.getProvidingPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      MainConfig mainConfig = MainConfig.getInstance();
      final Location loc = caster.getEyeLocation();
      Location leftLoc = new Location(loc.getWorld(), 0.0D, 0.0D, 0.0D, loc.getYaw() - 90.0F, loc.getPitch());
      Location upLoc = new Location(loc.getWorld(), 0.0D, 0.0D, 0.0D, loc.getYaw(), loc.getPitch() - 90.0F);
      final Vector left = leftLoc.getDirection();
      final Vector up = upLoc.getDirection();
      final Vector vector = loc.getDirection();
      final int duration = this.getDuration();
      double weaponDamage = statsManager.getLoreStatsWeapon(caster).getDamage();
      final double skillDamage = special.getBaseAdditionalDamage() + special.getBasePercentDamage() * weaponDamage / 100.0D;
      final Set<LivingEntity> listEntity = new HashSet();
      final Collection<Player> players = PlayerUtil.getNearbyPlayers(loc, mainConfig.getEffectRange());
      (new BukkitRunnable() {
         final int limit = SpecialPowerNeroBeam.this.getLimit();
         double range = SpecialPowerNeroBeam.this.getBaseRange();
         double startRadius = SpecialPowerNeroBeam.this.getStartRadius();
         double radius = 0.2D;
         int t = 0;
         double degree;

         public void run() {
            if (this.t >= this.limit) {
               this.cancel();
            } else {
               Location partLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
               this.degree = 3.141592653589793D / (2.0D * (this.radius / this.startRadius));

               for(double math = 0.0D; math <= 6.283185307179586D; math += this.degree) {
                  double calcHorizontal = Math.sin(math) * this.radius;
                  double calVertical = Math.cos(math) * this.radius;
                  partLoc.add(left.getX() * calcHorizontal, left.getY() * calcHorizontal, left.getZ() * calcHorizontal);
                  partLoc.add(up.getX() * calVertical, up.getY() * calVertical, up.getZ() * calVertical);
                  Bridge.getBridgeParticle().playParticle(players, ParticleEnum.SPELL_WITCH, partLoc, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                  partLoc.subtract(left.getX() * calcHorizontal, left.getY() * calcHorizontal, left.getZ() * calcHorizontal);
                  partLoc.subtract(up.getX() * calVertical, up.getY() * calVertical, up.getZ() * calVertical);
               }

               Bridge.getBridgeSound().playSound(players, loc, SoundEnum.ENTITY_WITHER_SHOOT, 5.0F, 1.0F);
               Iterator var3 = CombatUtil.getNearbyUnits(loc, this.range).iterator();

               while(var3.hasNext()) {
                  LivingEntity unit = (LivingEntity)var3.next();
                  if (!unit.equals(caster) && !listEntity.contains(unit)) {
                     listEntity.add(unit);
                     CombatUtil.applyPotion(unit, PotionEffectType.SLOW, duration, 10);
                     CombatUtil.skillDamage(caster, unit, skillDamage);
                  }
               }

               this.radius += SpecialPowerNeroBeam.this.getScaleRadius();
               this.range += SpecialPowerNeroBeam.this.getScaleRange();
               loc.add(vector);
               ++this.t;
            }
         }
      }).runTaskTimer(plugin, 0L, 1L);
   }
}
