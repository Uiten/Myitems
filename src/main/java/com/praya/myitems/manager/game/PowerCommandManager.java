package com.praya.myitems.manager.game;

import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerCommandProperties;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.game.PowerCommandConfig;
import com.praya.myitems.config.plugin.MainConfig;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PowerCommandManager extends HandlerManager {
   private final PowerCommandConfig powerCommandConfig;

   protected PowerCommandManager(MyItems plugin) {
      super(plugin);
      this.powerCommandConfig = new PowerCommandConfig(plugin);
   }

   public final PowerCommandConfig getPowerCommandConfig() {
      return this.powerCommandConfig;
   }

   public final Collection<String> getPowerCommandIDs() {
      return this.getPowerCommandConfig().getPowerCommandIDs();
   }

   public final Collection<PowerCommandProperties> getPowerCommandPropertyBuilds() {
      return this.getPowerCommandConfig().getPowerCommandPropertyBuilds();
   }

   public final PowerCommandProperties getPowerCommandProperties(String id) {
      return this.getPowerCommandConfig().getPowerCommandProperties(id);
   }

   public final boolean isPowerCommandExists(String id) {
      return this.getPowerCommandProperties(id) != null;
   }

   public final String getCommandKey(String powerCommand) {
      Iterator var3 = this.getPowerCommandIDs().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(powerCommand)) {
            return key;
         }
      }

      return null;
   }

   /** @deprecated */
   @Deprecated
   public final String getCommandLore(String powerCommand) {
      PowerCommandProperties build = this.getPowerCommandProperties(powerCommand);
      return build != null ? build.getLore() : null;
   }

   /** @deprecated */
   @Deprecated
   public final List<String> getCommands(String powerCommand) {
      PowerCommandProperties build = this.getPowerCommandProperties(powerCommand);
      return (List)(build != null ? build.getCommands() : new ArrayList());
   }

   public final String getCommandKeyByLore(String lore) {
      String coloredLore = TextUtil.colorful(lore);
      Iterator var4 = this.getPowerCommandIDs().iterator();

      while(var4.hasNext()) {
         String key = (String)var4.next();
         PowerCommandProperties powerCommandProperties = this.getPowerCommandProperties(key);
         if (powerCommandProperties.getKeyLore().equalsIgnoreCase(coloredLore)) {
            return key;
         }
      }

      return null;
   }

   public final String getCommand(String lore) {
      MainConfig mainConfig = MainConfig.getInstance();
      String[] loreCheck = lore.split(MainConfig.KEY_COMMAND);
      if (loreCheck.length > 1) {
         String colorPowerType = mainConfig.getPowerColorType();
         String loreStep = loreCheck[1].replaceFirst(colorPowerType, "");
         return this.getCommandKeyByLore(loreStep);
      } else {
         return null;
      }
   }

   public final String getTextPowerCommand(PowerClickEnum click, String keyCommand, double cooldown) {
      PowerManager powerManager = this.plugin.getGameManager().getPowerManager();
      MainConfig mainConfig = MainConfig.getInstance();
      HashMap<String, String> map = new HashMap();
      String format = mainConfig.getPowerFormat();
      cooldown = MathUtil.roundNumber(cooldown, 1);
      map.put("click", powerManager.getKeyClick(click));
      map.put("type", this.getKeyCommand(keyCommand));
      map.put("cooldown", powerManager.getKeyCooldown(cooldown));
      format = TextUtil.placeholder(map, format, "<", ">");
      return format;
   }

   public final String getKeyCommand(String keyCommand) {
      return this.getKeyCommand(keyCommand, false);
   }

   public final String getKeyCommand(String keyCommand, boolean justCheck) {
      MainConfig mainConfig = MainConfig.getInstance();
      String key = MainConfig.KEY_COMMAND;
      String color = mainConfig.getPowerColorType();
      String keylore = this.getCommandLore(keyCommand);
      return justCheck ? key + color + keylore : key + color + keylore + key + color;
   }
}
