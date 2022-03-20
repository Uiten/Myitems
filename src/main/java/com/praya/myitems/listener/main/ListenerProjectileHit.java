package com.praya.myitems.listener.main;

import com.praya.agarthalib.utility.ProjectileUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ListenerProjectileHit extends HandlerEvent implements Listener {
   public ListenerProjectileHit(MyItems plugin) {
      super(plugin);
   }

   @EventHandler
   public void onProjectileHit(ProjectileHitEvent event) {
      Entity projectile = event.getEntity();
      if (ProjectileUtil.isDisappear(projectile) && !projectile.isDead()) {
         projectile.remove();
      }

   }
}
