package com.praya.myitems.command;

import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.ItemFlagUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerCommand;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandFlagAdd extends HandlerCommand implements CommandExecutor {
   public CommandFlagAdd(MyItems plugin) {
      super(plugin);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      return addFlag(sender, command, label, args);
   }

   protected static final boolean addFlag(CommandSender sender, Command command, String label, String[] args) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      PluginManager pluginManager = plugin.getPluginManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      if (!commandManager.checkPermission(sender, "Flag_Add")) {
         String permission = commandManager.getPermission("Flag_Add");
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
            String flagName;
            if (args.length < 1) {
               flagName = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Flag_Add"));
               MessageBuild message = lang.getMessage(sender, "Argument_AddFlag");
               message.sendMessage(sender, "tooltip_flag_add", flagName);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
               return true;
            } else {
               flagName = args[0];
               ItemFlag flag = ItemFlagUtil.getFlag(flagName);
               MessageBuild message;
               if (flag == null) {
                  message = lang.getMessage(sender, "Item_Flag_Not_Exist");
                  message.sendMessage(sender, "flag", flagName);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                  return true;
               } else {
                  message = lang.getMessage(sender, "MyItems_AddFlag_Success");
                  ItemFlagUtil.addFlag(item, new ItemFlag[]{flag});
                  message.sendMessage(sender, "flag", flag.toString());
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                  player.updateInventory();
                  return true;
               }
            }
         }
      }
   }
}
