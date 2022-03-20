package com.praya.myitems.listener.main;

import com.praya.agarthalib.utility.BlockUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

public class ListenerBlockExplode extends HandlerEvent implements Listener {
   public ListenerBlockExplode(MyItems plugin) {
      super(plugin);
   }

   @EventHandler
   public void onBlockExplode(BlockExplodeEvent event) {
      if (!event.isCancelled() && BlockUtil.isSet(event.getBlock())) {
         BlockUtil.remove(event.getBlock());
      }

   }
}
