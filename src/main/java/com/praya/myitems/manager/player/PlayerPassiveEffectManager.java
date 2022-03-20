package com.praya.myitems.manager.player;

import api.praya.myitems.builder.player.PlayerPassiveEffectCooldown;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.OfflinePlayer;

public class PlayerPassiveEffectManager extends HandlerManager {
   private final HashMap<UUID, PlayerPassiveEffectCooldown> mapPassiveEffectCooldown = new HashMap();

   protected PlayerPassiveEffectManager(MyItems plugin) {
      super(plugin);
   }

   public final PlayerPassiveEffectCooldown getPlayerPassiveEffectCooldown(OfflinePlayer player) {
      UUID uuid = player.getUniqueId();
      if (!this.mapPassiveEffectCooldown.containsKey(uuid)) {
         this.mapPassiveEffectCooldown.put(uuid, new PlayerPassiveEffectCooldown());
      }

      return (PlayerPassiveEffectCooldown)this.mapPassiveEffectCooldown.get(uuid);
   }

   public final void removePlayerPassiveEffectCooldown(OfflinePlayer player) {
      UUID uuid = player.getUniqueId();
      this.mapPassiveEffectCooldown.remove(uuid);
   }
}
