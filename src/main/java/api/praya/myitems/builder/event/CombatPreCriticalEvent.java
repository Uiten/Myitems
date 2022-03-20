package api.praya.myitems.builder.event;

import com.praya.agarthalib.utility.MathUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CombatPreCriticalEvent extends Event implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private final LivingEntity attacker;
   private final LivingEntity victims;
   private boolean cancel = false;
   private double chance;

   public CombatPreCriticalEvent(LivingEntity attacker, LivingEntity victims, double chance) {
      this.attacker = attacker;
      this.victims = victims;
      this.chance = MathUtil.limitDouble(chance, 0.0D, 100.0D);
   }

   public final LivingEntity getAttacker() {
      return this.attacker;
   }

   public final LivingEntity getVictims() {
      return this.victims;
   }

   public final double getChance() {
      return this.chance;
   }

   public final boolean isCritical() {
      return MathUtil.chanceOf(this.chance);
   }

   public final void setChance(double chance) {
      this.chance = MathUtil.limitDouble(chance, 0.0D, 100.0D);
   }

   public HandlerList getHandlers() {
      return handlers;
   }

   public static final HandlerList getHandlerList() {
      return handlers;
   }

   public boolean isCancelled() {
      return this.cancel;
   }

   public void setCancelled(boolean cancel) {
      this.cancel = cancel;
   }
}
