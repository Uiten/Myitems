package api.praya.myitems.builder.player;

import api.praya.myitems.builder.power.PowerEnum;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import java.util.HashMap;
import java.util.Set;

public class PlayerPowerCooldown {
   private final HashMap<String, Long> mapPowerCommandCooldown = new HashMap();
   private final HashMap<ProjectileEnum, Long> mapPowerShootCooldown = new HashMap();
   private final HashMap<PowerSpecialEnum, Long> mapPowerSpecialCooldown = new HashMap();
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$api$praya$myitems$builder$power$PowerEnum;

   public final Set<String> getCooldownCommandKeySet() {
      return this.mapPowerCommandCooldown.keySet();
   }

   public final Set<ProjectileEnum> getCooldownProjectileKeySet() {
      return this.mapPowerShootCooldown.keySet();
   }

   public final Set<PowerSpecialEnum> getCooldownSpecialKeySet() {
      return this.mapPowerSpecialCooldown.keySet();
   }

   public final void setPowerCommandCooldown(String command, long cooldown) {
      long expired = System.currentTimeMillis() + cooldown;
      this.mapPowerCommandCooldown.put(command, expired);
   }

   public final void setPowerShootCooldown(ProjectileEnum projectile, long cooldown) {
      long expired = System.currentTimeMillis() + cooldown;
      this.mapPowerShootCooldown.put(projectile, expired);
   }

   public final void setPowerSpecialCooldown(PowerSpecialEnum special, long cooldown) {
      long expired = System.currentTimeMillis() + cooldown;
      this.mapPowerSpecialCooldown.put(special, expired);
   }

   public final boolean isPowerCommandCooldown(String command) {
      if (this.mapPowerCommandCooldown.containsKey(command)) {
         long expired = (Long)this.mapPowerCommandCooldown.get(command);
         long now = System.currentTimeMillis();
         return now < expired;
      } else {
         return false;
      }
   }

   public final boolean isPowerShootCooldown(ProjectileEnum projectile) {
      if (this.mapPowerShootCooldown.containsKey(projectile)) {
         long expired = (Long)this.mapPowerShootCooldown.get(projectile);
         long now = System.currentTimeMillis();
         return now < expired;
      } else {
         return false;
      }
   }

   public final boolean isPowerSpecialCooldown(PowerSpecialEnum special) {
      if (this.mapPowerSpecialCooldown.containsKey(special)) {
         long expired = (Long)this.mapPowerSpecialCooldown.get(special);
         long now = System.currentTimeMillis();
         return now < expired;
      } else {
         return false;
      }
   }

   public final void removePowerCommandCooldown(String command) {
      this.mapPowerCommandCooldown.remove(command);
   }

   public final void removePowerShootCooldown(ProjectileEnum projectile) {
      this.mapPowerShootCooldown.remove(projectile);
   }

   public final void removePowerSpecialCooldown(PowerSpecialEnum special) {
      this.mapPowerSpecialCooldown.remove(special);
   }

   public final long getPowerCommandExpired(String command) {
      return this.mapPowerCommandCooldown.containsKey(command) ? (Long)this.mapPowerCommandCooldown.get(command) : System.currentTimeMillis();
   }

   public final long getPowerShootExpired(ProjectileEnum projectile) {
      return this.mapPowerShootCooldown.containsKey(projectile) ? (Long)this.mapPowerShootCooldown.get(projectile) : System.currentTimeMillis();
   }

   public final long getPowerSpecialExpired(PowerSpecialEnum special) {
      return this.mapPowerSpecialCooldown.containsKey(special) ? (Long)this.mapPowerSpecialCooldown.get(special) : System.currentTimeMillis();
   }

   public final long getPowerCommandTimeLeft(String command) {
      return this.isPowerCommandCooldown(command) ? (Long)this.mapPowerCommandCooldown.get(command) - System.currentTimeMillis() : 0L;
   }

   public final long getPowerShootTimeLeft(ProjectileEnum projectile) {
      return this.isPowerShootCooldown(projectile) ? (Long)this.mapPowerShootCooldown.get(projectile) - System.currentTimeMillis() : 0L;
   }

   public final long getPowerSpecialTimeLeft(PowerSpecialEnum special) {
      return this.isPowerSpecialCooldown(special) ? (Long)this.mapPowerSpecialCooldown.get(special) - System.currentTimeMillis() : 0L;
   }

   public final void clearPowerCooldown(PowerEnum power) {
      switch($SWITCH_TABLE$api$praya$myitems$builder$power$PowerEnum()[power.ordinal()]) {
      case 1:
         this.mapPowerCommandCooldown.clear();
         return;
      case 2:
         this.mapPowerShootCooldown.clear();
         return;
      case 3:
         this.mapPowerSpecialCooldown.clear();
         return;
      default:
      }
   }

   public final void clearAllCooldown() {
      this.mapPowerCommandCooldown.clear();
      this.mapPowerShootCooldown.clear();
      this.mapPowerSpecialCooldown.clear();
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$api$praya$myitems$builder$power$PowerEnum() {
      int[] var10000 = $SWITCH_TABLE$api$praya$myitems$builder$power$PowerEnum;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[PowerEnum.values().length];

         try {
            var0[PowerEnum.COMMAND.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[PowerEnum.SHOOT.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[PowerEnum.SPECIAL.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$api$praya$myitems$builder$power$PowerEnum = var0;
         return var0;
      }
   }
}
