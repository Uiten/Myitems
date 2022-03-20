package api.praya.myitems.builder.event;

import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PowerCommandCastEvent extends PowerPreCastEvent {
   private static final HandlerList handlers = new HandlerList();
   private final String keyCommand;
   private boolean cancel = false;
   private double cooldown;

   public PowerCommandCastEvent(Player player, PowerEnum power, PowerClickEnum click, ItemStack item, String lore, String keyCommand, double cooldown) {
      super(player, power, click, item, lore);
      this.keyCommand = keyCommand;
      this.cooldown = cooldown;
   }

   public final String getKeyCommand() {
      return this.keyCommand;
   }

   public final double getCooldown() {
      return this.cooldown;
   }

   public final void setCooldown(double cooldown) {
      this.cooldown = cooldown;
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
