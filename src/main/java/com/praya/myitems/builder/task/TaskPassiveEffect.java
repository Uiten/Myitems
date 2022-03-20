package com.praya.myitems.builder.task;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerTask;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PassiveEffectManager;

public class TaskPassiveEffect extends HandlerTask implements Runnable {
   public TaskPassiveEffect(MyItems plugin) {
      super(plugin);
   }

   public void run() {
      GameManager gameManager = this.plugin.getGameManager();
      PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
      MainConfig mainConfig = MainConfig.getInstance();
      boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
      passiveEffectManager.loadPassiveEffect(enableGradeCalculation);
   }
}
