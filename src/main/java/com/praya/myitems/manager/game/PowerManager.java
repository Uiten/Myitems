package com.praya.myitems.manager.game;

import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.inventory.ItemStack;

public class PowerManager extends HandlerManager {
   private final PowerCommandManager powerCommandManager;
   private final PowerShootManager powerShootManager;
   private final PowerSpecialManager powerSpecialManager;

   protected PowerManager(MyItems plugin) {
      super(plugin);
      this.powerCommandManager = new PowerCommandManager(plugin);
      this.powerShootManager = new PowerShootManager(plugin);
      this.powerSpecialManager = new PowerSpecialManager(plugin);
   }

   public final PowerCommandManager getPowerCommandManager() {
      return this.powerCommandManager;
   }

   public final PowerShootManager getPowerShootManager() {
      return this.powerShootManager;
   }

   public final PowerSpecialManager getPowerSpecialManager() {
      return this.powerSpecialManager;
   }

   public final int getLineClick(ItemStack item, PowerClickEnum click) {
      return EquipmentUtil.hasLore(item) ? EquipmentUtil.loreGetLineKey(item, this.getKeyClick(click)) : -1;
   }

   public final PowerClickEnum getClick(String lore) {
      PowerClickEnum[] var5;
      int var4 = (var5 = PowerClickEnum.values()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         PowerClickEnum click = var5[var3];
         if (lore.contains(this.getKeyClick(click, true))) {
            return click;
         }
      }

      return null;
   }

   public final double getCooldown(String lore) {
      MainConfig mainConfig = MainConfig.getInstance();
      String[] loreCheck = lore.split(MainConfig.KEY_COOLDOWN);
      if (loreCheck.length > 1) {
         String colorPowerCooldown = mainConfig.getPowerColorCooldown();
         String loreStep = loreCheck[1].replaceFirst(colorPowerCooldown, "");
         if (MathUtil.isNumber(loreStep)) {
            return MathUtil.parseDouble(loreStep);
         }
      }

      return 0.0D;
   }

   public final PowerEnum getPower(ItemStack item, PowerClickEnum click) {
      int line = this.getLineClick(item, click);
      if (line != -1) {
         String lore = (String)EquipmentUtil.getLores(item).get(line - 1);
         return this.getPower(lore);
      } else {
         return null;
      }
   }

   public final PowerEnum getPower(String lore) {
      if (lore.contains(MainConfig.KEY_COMMAND)) {
         return PowerEnum.COMMAND;
      } else if (lore.contains(MainConfig.KEY_SHOOT)) {
         return PowerEnum.SHOOT;
      } else {
         return lore.contains(MainConfig.KEY_SPECIAL) ? PowerEnum.SPECIAL : null;
      }
   }

   public final boolean isPower(String lore) {
      return this.getPower(lore) != null;
   }

   public final boolean hasPower(ItemStack item, PowerClickEnum click) {
      if (EquipmentUtil.hasLore(item)) {
         String lores = EquipmentUtil.getLores(item).toString();
         String key = this.getKeyClick(click, true);
         if (lores.contains(key)) {
            return true;
         }
      }

      return false;
   }

   public final String getKeyClick(PowerClickEnum click) {
      return this.getKeyClick(click, false);
   }

   public final String getKeyClick(PowerClickEnum click, boolean justCheck) {
      MainConfig mainConfig = MainConfig.getInstance();
      String key = MainConfig.KEY_CLICK;
      String color = mainConfig.getPowerColorClick();
      String text = click.getText();
      return justCheck ? key + color + text : key + color + text + key + color;
   }

   public final String getKeyCooldown(double cooldown) {
      return this.getKeyCooldown(cooldown, false);
   }

   public final String getKeyCooldown(double cooldown, boolean justCheck) {
      MainConfig mainConfig = MainConfig.getInstance();
      String key = MainConfig.KEY_COOLDOWN;
      String color = mainConfig.getPowerColorCooldown();
      return justCheck ? key + color : key + color + cooldown + key + color;
   }
}
