package com.praya.myitems.manager.player;

import api.praya.myitems.builder.player.PlayerPowerCooldown;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.OfflinePlayer;

public class PlayerPowerManager extends HandlerManager {
   private final HashMap<UUID, PlayerPowerCooldown> playerPowerCooldown = new HashMap();

   protected PlayerPowerManager(MyItems plugin) {
      super(plugin);
   }

   public final PlayerPowerCooldown getPlayerPowerCooldown(OfflinePlayer player) {
      UUID uuid = player.getUniqueId();
      if (!this.playerPowerCooldown.containsKey(uuid)) {
         this.playerPowerCooldown.put(uuid, new PlayerPowerCooldown());
      }

      return (PlayerPowerCooldown)this.playerPowerCooldown.get(uuid);
   }

   public final void removePlayerPowerCooldown(OfflinePlayer player) {
      UUID uuid = player.getUniqueId();
      this.playerPowerCooldown.remove(uuid);
   }
}
