package com.praya.myitems.listener.custom;

import api.praya.myitems.builder.event.PowerCommandCastEvent;
import api.praya.myitems.builder.event.PowerPreCastEvent;
import api.praya.myitems.builder.event.PowerShootCastEvent;
import api.praya.myitems.builder.event.PowerSpecialCastEvent;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.player.PlayerPowerCooldown;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.ServerEventUtil;
import com.praya.agarthalib.utility.TimeUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.PowerCommandManager;
import com.praya.myitems.manager.game.PowerManager;
import com.praya.myitems.manager.game.RequirementManager;
import com.praya.myitems.manager.player.PlayerManager;
import com.praya.myitems.manager.player.PlayerPowerManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.utility.main.ProjectileUtil;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class ListenerPowerPreCast extends HandlerEvent implements Listener {
   public ListenerPowerPreCast(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void eventPowerPreCast(PowerPreCastEvent event) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      PlayerManager playerManager = this.plugin.getPlayerManager();
      GameManager gameManager = this.plugin.getGameManager();
      PowerManager powerManager = gameManager.getPowerManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      RequirementManager requirementManager = gameManager.getRequirementManager();
      PowerCommandManager powerCommandManager = powerManager.getPowerCommandManager();
      PlayerPowerManager playerPowerManager = playerManager.getPlayerPowerManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (!event.isCancelled()) {
         Player player = event.getPlayer();
         PowerEnum power = event.getPower();
         PowerClickEnum click = event.getClick();
         ItemStack item = event.getItem();
         String lore = event.getLore();
         int durability = (int)statsManager.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT);
         PlayerPowerCooldown powerCooldown = playerPowerManager.getPlayerPowerCooldown(player);
         String[] cooldownList = lore.split(MainConfig.KEY_COOLDOWN);
         if (requirementManager.isAllowed(player, item) && cooldownList.length > 1) {
            String keyCooldown = cooldownList[1].replace(mainConfig.getPowerColorCooldown(), "");
            if (!MathUtil.isNumber(keyCooldown)) {
               return;
            }

            double cooldown = Math.max(0.0D, Double.valueOf(keyCooldown));
            if (!statsManager.checkDurability(item, durability)) {
               statsManager.sendBrokenCode(player, Slot.MAINHAND, false);
            } else {
               boolean enableMessageCooldown = mainConfig.isPowerEnableMessageCooldown();
               String[] specialList;
               String loreSpecial;
               long timeLeft;
               MessageBuild message;
               HashMap mapPlaceholder;
               if (power.equals(PowerEnum.COMMAND)) {
                  specialList = lore.split(MainConfig.KEY_COMMAND);
                  if (specialList.length > 1) {
                     loreSpecial = specialList[1].replaceFirst(mainConfig.getPowerColorType(), "");
                     String keyCommand = powerCommandManager.getCommandKeyByLore(loreSpecial);
                     if (keyCommand != null) {
                        if (powerCooldown.isPowerCommandCooldown(keyCommand)) {
                           if (enableMessageCooldown) {
                              timeLeft = powerCooldown.getPowerCommandTimeLeft(keyCommand);
                              message = lang.getMessage("Power_Command_Cooldown");
                              mapPlaceholder = new HashMap();
                              mapPlaceholder.put("power", keyCommand);
                              mapPlaceholder.put("time", TimeUtil.getTextTime(timeLeft));
                              message.sendMessage(player, mapPlaceholder);
                              SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                              return;
                           }
                        } else {
                           PowerCommandCastEvent powerCommandCastEvent = new PowerCommandCastEvent(player, power, click, item, lore, keyCommand, cooldown);
                           ServerEventUtil.callEvent(powerCommandCastEvent);
                        }
                     }
                  }
               } else if (power.equals(PowerEnum.SHOOT)) {
                  specialList = lore.split(MainConfig.KEY_SHOOT);
                  if (specialList.length > 1) {
                     loreSpecial = specialList[1].replace(mainConfig.getPowerColorType(), "");
                     ProjectileEnum projectile = ProjectileUtil.getProjectileByLore(loreSpecial);
                     if (projectile != null) {
                        if (powerCooldown.isPowerShootCooldown(projectile)) {
                           if (enableMessageCooldown) {
                              timeLeft = powerCooldown.getPowerShootTimeLeft(projectile);
                              message = lang.getMessage("Power_Shoot_Cooldown");
                              mapPlaceholder = new HashMap();
                              mapPlaceholder.put("power", ProjectileUtil.getText(projectile));
                              mapPlaceholder.put("time", TimeUtil.getTextTime(timeLeft));
                              message.sendMessage(player, mapPlaceholder);
                              SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                              return;
                           }
                        } else {
                           PowerShootCastEvent powerShootCastEvent = new PowerShootCastEvent(player, power, click, item, lore, projectile, cooldown);
                           ServerEventUtil.callEvent(powerShootCastEvent);
                        }
                     }
                  }
               } else if (power.equals(PowerEnum.SPECIAL)) {
                  specialList = lore.split(MainConfig.KEY_SPECIAL);
                  if (specialList.length > 1) {
                     loreSpecial = specialList[1].replace(mainConfig.getPowerColorType(), "");
                     PowerSpecialEnum special = PowerSpecialEnum.getSpecialByLore(loreSpecial);
                     if (special != null) {
                        if (powerCooldown.isPowerSpecialCooldown(special)) {
                           if (enableMessageCooldown) {
                              timeLeft = powerCooldown.getPowerSpecialTimeLeft(special);
                              message = lang.getMessage("Power_Special_Cooldown");
                              mapPlaceholder = new HashMap();
                              mapPlaceholder.put("power", special.getText());
                              mapPlaceholder.put("time", TimeUtil.getTextTime(timeLeft));
                              message.sendMessage(player, mapPlaceholder);
                              SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                              return;
                           }
                        } else {
                           PowerSpecialCastEvent powerSpecialCastEvent = new PowerSpecialCastEvent(player, power, click, item, lore, special, cooldown);
                           ServerEventUtil.callEvent(powerSpecialCastEvent);
                        }
                     }
                  }
               }
            }
         }
      }

   }
}
