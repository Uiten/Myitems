package api.praya.myitems.manager.player;

import api.praya.myitems.builder.item.ItemStatsArmor;
import api.praya.myitems.builder.item.ItemStatsWeapon;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.player.PlayerItemStatsManager;
import com.praya.myitems.manager.player.PlayerManager;
import org.bukkit.entity.Player;

public class PlayerItemStatsManagerAPI extends HandlerManager {
   protected PlayerItemStatsManagerAPI(MyItems plugin) {
      super(plugin);
   }

   public final ItemStatsWeapon getItemStatsWeapon(Player player) {
      return this.getPlayerItemStatsManager().getItemStatsWeapon(player);
   }

   public final ItemStatsArmor getItemStatsArmor(Player player) {
      return this.getPlayerItemStatsManager().getItemStatsArmor(player);
   }

   private final PlayerItemStatsManager getPlayerItemStatsManager() {
      PlayerManager playerManager = this.plugin.getPlayerManager();
      PlayerItemStatsManager playerItemStatsManager = playerManager.getPlayerItemStatsManager();
      return playerItemStatsManager;
   }
}
