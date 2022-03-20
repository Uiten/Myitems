package com.praya.myitems.manager.task;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.builder.task.TaskCustomEffect;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class TaskCustomEffectManager extends HandlerManager {
   private BukkitTask taskCustomEffect;

   protected TaskCustomEffectManager(MyItems plugin) {
      super(plugin);
      this.reloadTaskCustomEffect();
   }

   public final void reloadTaskCustomEffect() {
      if (this.taskCustomEffect != null) {
         this.taskCustomEffect.cancel();
      }

      this.taskCustomEffect = this.createTaskCustomEffect();
   }

   private final BukkitTask createTaskCustomEffect() {
      BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
      Runnable runnable = new TaskCustomEffect(this.plugin);
      int delay;
      int period;
      BukkitTask task = scheduler.runTaskTimer(this.plugin, runnable, 2L, 1L);
      return task;
   }
}
