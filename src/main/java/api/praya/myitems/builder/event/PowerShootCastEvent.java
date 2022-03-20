package api.praya.myitems.builder.event;

import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PowerShootCastEvent extends PowerPreCastEvent {
   private static final HandlerList handlers = new HandlerList();
   private final ProjectileEnum projectile;
   private boolean cancel = false;
   private double cooldown;
   private double speed;

   public PowerShootCastEvent(Player player, PowerEnum power, PowerClickEnum click, ItemStack item, String lore, ProjectileEnum projectile, double cooldown) {
      super(player, power, click, item, lore);
      this.projectile = projectile;
      this.cooldown = cooldown;
      this.speed = 3.0D;
   }

   public final ProjectileEnum getProjectile() {
      return this.projectile;
   }

   public final double getCooldown() {
      return this.cooldown;
   }

   public final double getSpeed() {
      return this.speed;
   }

   public final void setCooldown(double cooldown) {
      this.cooldown = cooldown;
   }

   public final void setSpeed(double speed) {
      this.speed = speed;
   }

   public HandlerList getHandlers() {
      return handlers;
   }

   public static HandlerList getHandlerList() {
      return handlers;
   }

   public boolean isCancelled() {
      return this.cancel;
   }

   public void setCancelled(boolean cancel) {
      this.cancel = cancel;
   }
}
