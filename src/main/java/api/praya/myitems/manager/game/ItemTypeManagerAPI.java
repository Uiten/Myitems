package api.praya.myitems.manager.game;

import api.praya.myitems.builder.item.ItemType;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemTypeManager;
import java.util.Collection;

public class ItemTypeManagerAPI extends HandlerManager {
   protected ItemTypeManagerAPI(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getItemTypeIDs() {
      return this.getItemTypeManager().getItemTypeIDs();
   }

   public final Collection<ItemType> getItemTypes() {
      return this.getItemTypeManager().getItemTypes();
   }

   public final ItemType getItemType(String id) {
      return this.getItemTypeManager().getItemType(id);
   }

   public final boolean isItemTypeExists(String id) {
      return this.getItemTypeManager().isItemTypeExists(id);
   }

   private final ItemTypeManager getItemTypeManager() {
      GameManager gameManager = this.plugin.getGameManager();
      ItemTypeManager itemTypeManager = gameManager.getItemTypeManager();
      return itemTypeManager;
   }
}
