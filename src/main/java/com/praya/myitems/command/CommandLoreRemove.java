package com.praya.myitems.command;

import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MathUtil;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandLoreRemove extends HandlerCommand implements CommandExecutor {
   public CommandLoreRemove(MyItems plugin) {
      super(plugin);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      return removeLore(sender, command, label, args);
   }

   protected static final boolean removeLore(CommandSender sender, Command command, String label, String[] args) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      PluginManager pluginManager = plugin.getPluginManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      if (!commandManager.checkPermission(sender, "Lore_Remove")) {
         String permission = commandManager.getPermission("Lore_Remove");
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
         MessageBuild message;
         if (!EquipmentUtil.isSolid(item)) {
            message = lang.getMessage(sender, "Item_MainHand_Empty");
            message.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
         } else if (!EquipmentUtil.hasLore(item)) {
            message = lang.getMessage(sender, "Item_Lore_Empty");
            message.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
         } else {
            String textLine;
            if (args.length < 1) {
               textLine = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Lore_Remove"));
               message = lang.getMessage(sender, "Argument_RemoveLore");
               message.sendMessage(sender, "tooltip_lore_remove", textLine);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
               return true;
            } else {
               textLine = args[0];
               if (!MathUtil.isNumber(textLine)) {
                  message = lang.getMessage(sender, "Argument_Invalid_Value");
                  message.sendMessage(sender);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                  return true;
               } else {
                  int line = MathUtil.parseInteger(textLine);
                  if (line >= 1 && line <= EquipmentUtil.getLores(item).size()) {
                     message = lang.getMessage(sender, "MyItems_RemoveLore_Success");
                     EquipmentUtil.removeLore(item, line);
                     message.sendMessage(sender, "line", String.valueOf(line));
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                     player.updateInventory();
                     return true;
                  } else {
                     message = lang.getMessage(sender, "MyItems_RemoveLore_Lore_Correction");
                     message.sendMessage(sender);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  }
               }
            }
         }
      }
   }
}
