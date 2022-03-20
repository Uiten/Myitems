package api.praya.myitems.manager.game;

import api.praya.myitems.builder.item.ItemGenerator;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemGeneratorManager;
import java.util.Collection;

public class ItemGeneratorManagerAPI extends HandlerManager {
   protected ItemGeneratorManagerAPI(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getItemGeneratorNames() {
      return this.getItemGeneratorManager().getItemGeneratorIDs();
   }

   public final Collection<ItemGenerator> getItemGenerators() {
      return this.getItemGeneratorManager().getItemGenerators();
   }

   public final ItemGenerator getItemGenerator(String nameid) {
      return this.getItemGeneratorManager().getItemGenerator(nameid);
   }

   public final boolean isItemGeneratorExists(String nameid) {
      return this.getItemGeneratorManager().isItemGeneratorExists(nameid);
   }

   private final ItemGeneratorManager getItemGeneratorManager() {
      GameManager gameManager = this.plugin.getGameManager();
      ItemGeneratorManager itemGeneratorManager = gameManager.getItemGeneratorManager();
      return itemGeneratorManager;
   }
}
