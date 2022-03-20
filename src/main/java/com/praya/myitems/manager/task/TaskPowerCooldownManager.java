package com.praya.myitems.manager.task;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.builder.task.TaskPowerCooldown;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class TaskPowerCooldownManager extends HandlerManager {
   private BukkitTask taskPowerCooldown;

   protected TaskPowerCooldownManager(MyItems plugin) {
      super(plugin);
      this.reloadTaskPowerCooldown();
   }

   public final void reloadTaskPowerCooldown() {
      if (this.taskPowerCooldown != null) {
         this.taskPowerCooldown.cancel();
      }

      this.taskPowerCooldown = this.createTaskPowerCooldown();
   }

   private final BukkitTask createTaskPowerCooldown() {
      BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
      Runnable runnable = new TaskPowerCooldown(this.plugin);
      int delay;
      int period;
      BukkitTask task = scheduler.runTaskTimer(this.plugin, runnable, 0L, 1L);
      return task;
   }
}
