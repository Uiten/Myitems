package api.praya.myitems.main;

import api.praya.myitems.manager.game.GameManagerAPI;
import api.praya.myitems.manager.player.PlayerManagerAPI;
import com.praya.myitems.MyItems;
import org.bukkit.plugin.java.JavaPlugin;

public class MyItemsAPI {
   private final GameManagerAPI gameManagerAPI;
   private final PlayerManagerAPI playerManagerAPI;

   private MyItemsAPI(MyItems plugin) {
      this.gameManagerAPI = new GameManagerAPI(plugin);
      this.playerManagerAPI = new PlayerManagerAPI(plugin);
   }

   public static final MyItemsAPI getInstance() {
      return MyItemsAPI.MyItemsAPIHelper.instance;
   }

   public final GameManagerAPI getGameManagerAPI() {
      return this.gameManagerAPI;
   }

   public final PlayerManagerAPI getPlayerManagerAPI() {
      return this.playerManagerAPI;
   }

   // $FF: synthetic method
   MyItemsAPI(MyItems var1, MyItemsAPI var2) {
      this(var1);
   }

   private static class MyItemsAPIHelper {
      private static final MyItemsAPI instance;

      static {
         MyItems plugin = (MyItems)JavaPlugin.getProvidingPlugin(MyItems.class);
         instance = new MyItemsAPI(plugin, (MyItemsAPI)null);
      }
   }
}
