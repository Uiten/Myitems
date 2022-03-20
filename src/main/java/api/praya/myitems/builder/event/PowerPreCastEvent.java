package api.praya.myitems.builder.event;

import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PowerPreCastEvent extends Event implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private boolean cancel = false;
   private final Player player;
   private final PowerEnum power;
   private final PowerClickEnum click;
   private final ItemStack item;
   private final String lore;

   public PowerPreCastEvent(Player player, PowerEnum power, PowerClickEnum click, ItemStack item, String lore) {
      this.player = player;
      this.power = power;
      this.click = click;
      this.item = item;
      this.lore = lore;
   }

   public final Player getPlayer() {
      return this.player;
   }

   public final PowerEnum getPower() {
      return this.power;
   }

   public final PowerClickEnum getClick() {
      return this.click;
   }

   public final ItemStack getItem() {
      return this.item;
   }

   public final String getLore() {
      return this.lore;
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
