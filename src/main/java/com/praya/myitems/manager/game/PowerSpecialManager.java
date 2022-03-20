package com.praya.myitems.manager.game;

import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import api.praya.myitems.builder.power.PowerSpecialProperties;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.game.PowerSpecialConfig;
import com.praya.myitems.config.plugin.MainConfig;
import java.util.Collection;
import java.util.HashMap;

public class PowerSpecialManager extends HandlerManager {
   private final PowerSpecialConfig powerSpecialConfig;

   protected PowerSpecialManager(MyItems plugin) {
      super(plugin);
      this.powerSpecialConfig = new PowerSpecialConfig(plugin);
   }

   public final PowerSpecialConfig getPowerSpecialConfig() {
      return this.powerSpecialConfig;
   }

   public final Collection<PowerSpecialEnum> getPowerSpecialIDs() {
      return this.getPowerSpecialConfig().getPowerSpecialIDs();
   }

   public final Collection<PowerSpecialProperties> getPowerSpecialPropertyBuilds() {
      return this.getPowerSpecialConfig().getPowerSpecialPropertyBuilds();
   }

   public final PowerSpecialProperties getPowerSpecialProperties(PowerSpecialEnum id) {
      return this.getPowerSpecialConfig().getPowerSpecialProperties(id);
   }

   public final boolean isPowerSpecialExists(PowerSpecialEnum id) {
      return this.getPowerSpecialProperties(id) != null;
   }

   public final String getTextPowerSpecial(PowerClickEnum click, PowerSpecialEnum special, double cooldown) {
      PowerManager powerManager = this.plugin.getGameManager().getPowerManager();
      MainConfig mainConfig = MainConfig.getInstance();
      HashMap<String, String> map = new HashMap();
      String format = mainConfig.getPowerFormat();
      cooldown = MathUtil.roundNumber(cooldown, 1);
      map.put("click", powerManager.getKeyClick(click));
      map.put("type", this.getKeySpecial(special));
      map.put("cooldown", powerManager.getKeyCooldown(cooldown));
      format = TextUtil.placeholder(map, format, "<", ">");
      return format;
   }

   public final PowerSpecialEnum getSpecial(String lore) {
      MainConfig mainConfig = MainConfig.getInstance();
      String[] loreCheck = lore.split(MainConfig.KEY_SPECIAL);
      if (loreCheck.length > 1) {
         String colorPowerType = mainConfig.getPowerColorType();
         String loreStep = loreCheck[1].replaceFirst(colorPowerType, "");
         return PowerSpecialEnum.getSpecialByLore(loreStep);
      } else {
         return null;
      }
   }

   public final String getKeySpecial(PowerSpecialEnum special) {
      return this.getKeySpecial(special, false);
   }

   public final String getKeySpecial(PowerSpecialEnum special, boolean justCheck) {
      MainConfig mainConfig = MainConfig.getInstance();
      String key = MainConfig.KEY_SPECIAL;
      String color = mainConfig.getPowerColorType();
      String text = special.getText();
      return justCheck ? key + color + text : key + color + text + key + color;
   }
}
