package api.praya.myitems.manager.game;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemManager;
import java.util.Collection;
import org.bukkit.inventory.ItemStack;

public class ItemManagerAPI extends HandlerManager {
   protected ItemManagerAPI(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getItemNames() {
      return this.getItemManager().getItemIDs();
   }

   public final Collection<ItemStack> getItems() {
      return this.getItemManager().getItems();
   }

   public final ItemStack getItem(String nameid) {
      return this.getItemManager().getItem(nameid);
   }

   public final boolean isExist(String nameid) {
      return this.getItemManager().isExist(nameid);
   }

   private final ItemManager getItemManager() {
      GameManager gameManager = this.plugin.getGameManager();
      ItemManager itemManager = gameManager.getItemManager();
      return itemManager;
   }
}
