package api.praya.myitems.manager.game;

import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerCommandProperties;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PowerCommandManager;
import java.util.Collection;
import java.util.List;

public class PowerCommandManagerAPI extends HandlerManager {
   protected PowerCommandManagerAPI(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getPowerCommandIDs() {
      return this.getPowerCommandManager().getPowerCommandIDs();
   }

   public final Collection<PowerCommandProperties> getPowerCommandPropertyBuilds() {
      return this.getPowerCommandManager().getPowerCommandPropertyBuilds();
   }

   public final PowerCommandProperties getPowerCommandProperties(String id) {
      return this.getPowerCommandManager().getPowerCommandProperties(id);
   }

   public final boolean isPowerCommandExists(String id) {
      return this.getPowerCommandManager().isPowerCommandExists(id);
   }

   /** @deprecated */
   @Deprecated
   public final List<String> getCommands(String powerCommand) {
      return this.getPowerCommandManager().getCommands(powerCommand);
   }

   public final String getTextPowerCommand(PowerClickEnum click, String keyCommand, double cooldown) {
      return this.getPowerCommandManager().getTextPowerCommand(click, keyCommand, cooldown);
   }

   private final PowerCommandManager getPowerCommandManager() {
      GameManager gameManager = this.plugin.getGameManager();
      PowerCommandManager powerCommandManager = gameManager.getPowerManager().getPowerCommandManager();
      return powerCommandManager;
   }
}
