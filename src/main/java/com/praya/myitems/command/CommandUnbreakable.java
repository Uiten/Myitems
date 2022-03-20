package com.praya.myitems.command;

import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerCommand;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.bridge.unity.BridgeTagsNBT;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandUnbreakable extends HandlerCommand implements CommandExecutor {
   public CommandUnbreakable(MyItems plugin) {
      super(plugin);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      return unbreakable(sender, command, label, args);
   }

   protected static final boolean unbreakable(CommandSender sender, Command command, String label, String[] args) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      PluginManager pluginManager = plugin.getPluginManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      if (!commandManager.checkPermission(sender, "MyItems_Unbreakable")) {
         String permission = commandManager.getPermission("MyItems_Unbreakable");
         MessageBuild message = lang.getMessage(sender, "Permission_Lack");
         message.sendMessage(sender, "permission", permission);
         SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
         return true;
      } else if (!SenderUtil.isPlayer(sender)) {
         MessageBuild message = lang.getMessage(sender, "Console_Command_Forbiden");
         message.sendMessage(sender);
         SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
         return true;
      } else {
         Player player = PlayerUtil.parse(sender);
         ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
         if (!EquipmentUtil.isSolid(item)) {
            MessageBuild message = lang.getMessage(sender, "Item_MainHand_Empty");
            message.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
         } else {
            BridgeTagsNBT bridgeTagsNBT = Bridge.getBridgeTagsNBT();
            boolean isUnbreakable = bridgeTagsNBT.isUnbreakable(item);
            if (args.length > 0) {
               label44: {
                  String textBoolean = args[0];
                  MessageBuild message;
                  if (!textBoolean.equalsIgnoreCase("true") && !textBoolean.equalsIgnoreCase("on")) {
                     if (!textBoolean.equalsIgnoreCase("false") && !textBoolean.equalsIgnoreCase("off")) {
                        break label44;
                     }

                     message = lang.getMessage(sender, "MyItems_Unbreakable_Turn_Off");
                     bridgeTagsNBT.setUnbreakable(item, false);
                     message.sendMessage(sender);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                     return true;
                  }

                  message = lang.getMessage(sender, "MyItems_Unbreakable_Turn_On");
                  bridgeTagsNBT.setUnbreakable(item, true);
                  message.sendMessage(sender);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                  return true;
               }
            }

            MessageBuild message;
            if (isUnbreakable) {
               message = lang.getMessage(sender, "MyItems_Unbreakable_Turn_Off");
               bridgeTagsNBT.setUnbreakable(item, false);
               message.sendMessage(sender);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
               return true;
            } else {
               message = lang.getMessage(sender, "MyItems_Unbreakable_Turn_On");
               bridgeTagsNBT.setUnbreakable(item, true);
               message.sendMessage(sender);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
               player.updateInventory();
               return true;
            }
         }
      }
   }
}
