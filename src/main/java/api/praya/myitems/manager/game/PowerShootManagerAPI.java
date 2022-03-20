package api.praya.myitems.manager.game;

import api.praya.myitems.builder.power.PowerClickEnum;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PowerShootManager;
import core.praya.agarthalib.enums.branch.ProjectileEnum;

public class PowerShootManagerAPI extends HandlerManager {
   protected PowerShootManagerAPI(MyItems plugin) {
      super(plugin);
   }

   public final String getTextPowerShoot(PowerClickEnum click, ProjectileEnum projectile, double cooldown) {
      return this.getPowerShootManager().getTextPowerShoot(click, projectile, cooldown);
   }

   private final PowerShootManager getPowerShootManager() {
      GameManager gameManager = this.plugin.getGameManager();
      PowerShootManager powerShootManager = gameManager.getPowerManager().getPowerShootManager();
      return powerShootManager;
   }
}
