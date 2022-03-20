package api.praya.myitems.manager.game;

import api.praya.myitems.builder.item.ItemTier;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemTierManager;
import java.util.Collection;

public class ItemTierManagerAPI extends HandlerManager {
   protected ItemTierManagerAPI(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getItemTierIDs() {
      return this.getItemTierManager().getItemTierIDs();
   }

   public final Collection<ItemTier> getItemTiers() {
      return this.getItemTierManager().getItemTiers();
   }

   public final ItemTier getItemTier(String id) {
      return this.getItemTierManager().getItemTier(id);
   }

   public final boolean isItemTierExists(String id) {
      return this.getItemTierManager().isItemTierExists(id);
   }

   private final ItemTierManager getItemTierManager() {
      GameManager gameManager = this.plugin.getGameManager();
      ItemTierManager itemTierManager = gameManager.getItemTierManager();
      return itemTierManager;
   }
}
