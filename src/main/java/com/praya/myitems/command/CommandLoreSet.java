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
import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandLoreSet extends HandlerCommand implements CommandExecutor {
   public CommandLoreSet(MyItems plugin) {
      super(plugin);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      return setLore(sender, command, label, args);
   }

   protected static final boolean setLore(CommandSender sender, Command command, String label, String[] args) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      PluginManager pluginManager = plugin.getPluginManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      if (!commandManager.checkPermission(sender, "Lore_Set")) {
         String permission = commandManager.getPermission("Lore_Set");
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
            String textLine;
            MessageBuild message;
            if (args.length < 1) {
               textLine = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Lore_Set"));
               message = lang.getMessage(sender, "Argument_SetLore");
               message.sendMessage(sender, "tooltip_lore_set", textLine);
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
                  if (line < 1) {
                     message = lang.getMessage(sender, "Argument_Invalid_Line");
                     message.sendMessage(sender);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else {
                     String lore = args.length < 2 ? "" : TextUtil.hookPlaceholderAPI(player, TextUtil.concatenate(args, 2));
                     message = lang.getMessage(sender, "MyItems_SetLore_Success");
                     HashMap<String, String> mapPlaceholder = new HashMap();
                     mapPlaceholder.put("line", String.valueOf(line));
                     mapPlaceholder.put("lore", lore);
                     EquipmentUtil.setLore(item, line, lore);
                     message.sendMessage(sender, mapPlaceholder);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                     player.updateInventory();
                     return true;
                  }
               }
            }
         }
      }
   }
}
