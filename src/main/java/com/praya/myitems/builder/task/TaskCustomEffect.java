package com.praya.myitems.builder.task;

import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerTask;
import com.praya.myitems.utility.customeffect.CustomEffectFreeze;
import java.util.Iterator;
import org.bukkit.entity.Player;

public class TaskCustomEffect extends HandlerTask implements Runnable {
   public TaskCustomEffect(MyItems plugin) {
      super(plugin);
   }

   public void run() {
      Iterator var2 = PlayerUtil.getOnlinePlayers().iterator();

      while(var2.hasNext()) {
         Player loopPlayer = (Player)var2.next();
         CustomEffectFreeze.cast(loopPlayer);
      }

   }
}
