package api.praya.myitems.builder.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CombatCriticalDamageEvent extends Event implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private final LivingEntity attacker;
   private final LivingEntity victims;
   private boolean cancel = false;
   private double baseDamage;
   private double scaleDamage;
   private double bonusDamage;

   public CombatCriticalDamageEvent(LivingEntity attacker, LivingEntity victims, double baseDamage, double scaleDamage, double bonusDamage) {
      this.attacker = attacker;
      this.victims = victims;
      this.baseDamage = baseDamage;
      this.scaleDamage = scaleDamage;
      this.bonusDamage = bonusDamage;
   }

   public final LivingEntity getAttacker() {
      return this.attacker;
   }

   public final LivingEntity getVictims() {
      return this.victims;
   }

   public final double getBaseDamage() {
      return this.baseDamage;
   }

   public final double getScaleDamage() {
      return this.scaleDamage;
   }

   public final double getBonusDamage() {
      return this.bonusDamage;
   }

   public final double getCalculationDamage() {
      return this.baseDamage * this.scaleDamage + this.bonusDamage;
   }

   public final void setScaleDamage(double scaleDamage) {
      this.scaleDamage = scaleDamage;
   }

   public final void setBonusDamage(double bonusDamage) {
      this.bonusDamage = bonusDamage;
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
