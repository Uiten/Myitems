package com.praya.myitems.manager.task;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.builder.task.TaskPassiveEffect;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class TaskPassiveEffectManager extends HandlerManager {
   private BukkitTask taskLoadPassiveEffect;

   protected TaskPassiveEffectManager(MyItems plugin) {
      super(plugin);
      this.reloadTaskLoadPassiveEffect();
   }

   public final void reloadTaskLoadPassiveEffect() {
      if (this.taskLoadPassiveEffect != null) {
         this.taskLoadPassiveEffect.cancel();
      }

      this.taskLoadPassiveEffect = this.createTaskLoadPassiveEffect();
   }

   private final BukkitTask createTaskLoadPassiveEffect() {
      MainConfig mainConfig = MainConfig.getInstance();
      BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
      Runnable runnable = new TaskPassiveEffect(this.plugin);
      int delay;
      int period = mainConfig.getPassivePeriodEffect();
      BukkitTask task = scheduler.runTaskTimer(this.plugin, runnable, 2L, (long)period);
      return task;
   }
}
