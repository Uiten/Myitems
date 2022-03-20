package api.praya.myitems.builder.item;

import com.praya.myitems.MyItems;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemSetManager;
import core.praya.agarthalib.enums.main.Slot;
import java.util.Set;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemSetComponent {
   private final String itemSetID;
   private final String id;
   private final String keyLore;
   private final ItemSetComponentItem componentItem;
   private final Set<Slot> slots;

   public ItemSetComponent(String itemSetID, String id, String keyLore, ItemSetComponentItem componentItem, Set<Slot> slots) {
      this.itemSetID = itemSetID;
      this.id = id;
      this.keyLore = keyLore;
      this.componentItem = componentItem;
      this.slots = slots;
   }

   public final String getItemSetID() {
      return this.itemSetID;
   }

   public final String getID() {
      return this.id;
   }

   public final String getKeyLore() {
      return this.keyLore;
   }

   public final ItemSetComponentItem getComponentItem() {
      return this.componentItem;
   }

   public final Set<Slot> getSlots() {
      return this.slots;
   }

   public final boolean isMatchSlot(Slot slot) {
      return slot != null ? this.getSlots().contains(slot) : false;
   }

   public final ItemSet getItemSet() {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      ItemSetManager itemSetManager = gameManager.getItemSetManager();
      ItemSet itemSet = itemSetManager.getItemSet(this.getItemSetID());
      return itemSet;
   }
}
