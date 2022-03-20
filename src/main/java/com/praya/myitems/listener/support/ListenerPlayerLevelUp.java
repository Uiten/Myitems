package com.praya.myitems.listener.support;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import com.sucy.skill.api.event.PlayerLevelUpEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ListenerPlayerLevelUp extends HandlerEvent implements Listener {
   public ListenerPlayerLevelUp(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void eventPlayerLevelUp(PlayerLevelUpEvent event) {
      Player player = event.getPlayerData().getPlayer();
      TriggerSupportUtil.updateSupport(player);
   }
}
