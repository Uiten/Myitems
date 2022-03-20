package com.praya.myitems.builder.task;

import api.praya.myitems.builder.player.PlayerPowerCooldown;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerTask;
import com.praya.myitems.manager.player.PlayerManager;
import com.praya.myitems.manager.player.PlayerPowerManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.utility.main.ProjectileUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class TaskPowerCooldown extends HandlerTask implements Runnable {
   private final HashMap<String, String> mapPlaceholder = new HashMap();

   public TaskPowerCooldown(MyItems plugin) {
      super(plugin);
   }

   public void run() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      PlayerManager playerManager = this.plugin.getPlayerManager();
      PlayerPowerManager playerPowerManager = playerManager.getPlayerPowerManager();
      Iterator var6 = PlayerUtil.getOnlinePlayers().iterator();

      while(true) {
         Player player;
         PlayerPowerCooldown playerPowerCooldown;
         Set cooldownSpecialKeySet;
         Object[] arrayObjectSpecial;
         Object objectSpecial;
         int var13;
         int var14;
         Object[] var15;
         Location location;
         String message;
         do {
            if (!var6.hasNext()) {
               return;
            }

            player = (Player)var6.next();
            playerPowerCooldown = playerPowerManager.getPlayerPowerCooldown(player);
            Set<String> cooldownCommandKeySet = playerPowerCooldown.getCooldownCommandKeySet();
            Set<ProjectileEnum> cooldownProjectileKeySet = playerPowerCooldown.getCooldownProjectileKeySet();
            cooldownSpecialKeySet = playerPowerCooldown.getCooldownSpecialKeySet();
            if (!cooldownCommandKeySet.isEmpty()) {
               arrayObjectSpecial = cooldownCommandKeySet.toArray();
               var15 = arrayObjectSpecial;
               var14 = arrayObjectSpecial.length;

               for(var13 = 0; var13 < var14; ++var13) {
                  objectSpecial = var15[var13];
                  String keyCommand = (String)objectSpecial;
                  if (!playerPowerCooldown.isPowerCommandCooldown(keyCommand)) {
                     location = player.getLocation();
                     message = lang.getText((LivingEntity)player, "Power_Command_Refresh");
                     this.mapPlaceholder.clear();
                     this.mapPlaceholder.put("power", keyCommand);
                     message = TextUtil.placeholder(this.mapPlaceholder, message);
                     playerPowerCooldown.removePowerCommandCooldown(keyCommand);
                     Bridge.getBridgeSound().playSound(player, location, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON, 1.0F, 1.0F);
                     PlayerUtil.sendMessage(player, message);
                  }
               }
            }

            if (!cooldownProjectileKeySet.isEmpty()) {
               arrayObjectSpecial = cooldownProjectileKeySet.toArray();
               var15 = arrayObjectSpecial;
               var14 = arrayObjectSpecial.length;

               for(var13 = 0; var13 < var14; ++var13) {
                  objectSpecial = var15[var13];
                  ProjectileEnum keyProjectile = (ProjectileEnum)objectSpecial;
                  if (!playerPowerCooldown.isPowerShootCooldown(keyProjectile)) {
                     location = player.getLocation();
                     message = lang.getText((LivingEntity)player, "Power_Shoot_Refresh");
                     this.mapPlaceholder.clear();
                     this.mapPlaceholder.put("power", ProjectileUtil.getText(keyProjectile));
                     message = TextUtil.placeholder(this.mapPlaceholder, message);
                     playerPowerCooldown.removePowerShootCooldown(keyProjectile);
                     Bridge.getBridgeSound().playSound(player, location, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON, 1.0F, 1.0F);
                     PlayerUtil.sendMessage(player, message);
                  }
               }
            }
         } while(cooldownSpecialKeySet.isEmpty());

         arrayObjectSpecial = cooldownSpecialKeySet.toArray();
         var15 = arrayObjectSpecial;
         var14 = arrayObjectSpecial.length;

         for(var13 = 0; var13 < var14; ++var13) {
            objectSpecial = var15[var13];
            PowerSpecialEnum keySpecial = (PowerSpecialEnum)objectSpecial;
            if (!playerPowerCooldown.isPowerSpecialCooldown(keySpecial)) {
               location = player.getLocation();
               message = lang.getText((LivingEntity)player, "Power_Special_Refresh");
               this.mapPlaceholder.clear();
               this.mapPlaceholder.put("power", keySpecial.getText());
               message = TextUtil.placeholder(this.mapPlaceholder, message);
               playerPowerCooldown.removePowerSpecialCooldown(keySpecial);
               Bridge.getBridgeSound().playSound(player, location, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON, 1.0F, 1.0F);
               PlayerUtil.sendMessage(player, message);
            }
         }
      }
   }
}
