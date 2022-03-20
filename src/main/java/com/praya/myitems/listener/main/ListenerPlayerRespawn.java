package com.praya.myitems.listener.main;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ListenerPlayerRespawn extends HandlerEvent implements Listener {
   public ListenerPlayerRespawn(MyItems plugin) {
      super(plugin);
   }

   @EventHandler
   public void triggerSupport(PlayerRespawnEvent event) {
      Player player = event.getPlayer();
      TriggerSupportUtil.updateSupport(player);
   }
}
