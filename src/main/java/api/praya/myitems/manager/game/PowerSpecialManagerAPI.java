package api.praya.myitems.manager.game;

import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import api.praya.myitems.builder.power.PowerSpecialProperties;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PowerSpecialManager;
import java.util.Collection;

public class PowerSpecialManagerAPI extends HandlerManager {
   protected PowerSpecialManagerAPI(MyItems plugin) {
      super(plugin);
   }

   public final Collection<PowerSpecialEnum> getPowerSpecialIDs() {
      return this.getPowerSpecialManager().getPowerSpecialIDs();
   }

   public final Collection<PowerSpecialProperties> getPowerSpecialPropertyBuilds() {
      return this.getPowerSpecialManager().getPowerSpecialPropertyBuilds();
   }

   public final PowerSpecialProperties getPowerSpecialProperties(PowerSpecialEnum id) {
      return this.getPowerSpecialManager().getPowerSpecialProperties(id);
   }

   public final boolean isPowerSpecialExists(PowerSpecialEnum id) {
      return this.getPowerSpecialManager().isPowerSpecialExists(id);
   }

   public final String getTextPowerSpecial(PowerClickEnum click, PowerSpecialEnum special, double cooldown) {
      return this.getPowerSpecialManager().getTextPowerSpecial(click, special, cooldown);
   }

   private final PowerSpecialManager getPowerSpecialManager() {
      GameManager gameManager = this.plugin.getGameManager();
      PowerSpecialManager powerSpecialManager = gameManager.getPowerManager().getPowerSpecialManager();
      return powerSpecialManager;
   }
}
