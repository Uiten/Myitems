package com.praya.myitems.command;

import com.praya.agarthalib.utility.EquipmentUtil;
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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandItemName extends HandlerCommand implements CommandExecutor {
   public CommandItemName(MyItems plugin) {
      super(plugin);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      return setName(sender, command, label, args);
   }

   protected static final boolean setName(CommandSender sender, Command command, String label, String[] args) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      PluginManager pluginManager = plugin.getPluginManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      if (!commandManager.checkPermission(sender, "MyItems_Name")) {
         String permission = commandManager.getPermission("MyItems_Name");
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
         } else if (args.length < 1) {
            message = lang.getMessage((LivingEntity)player, "MyItems_SetName_Clear_Success");
            message.sendMessage(sender);
            EquipmentUtil.clearDisplayName(item);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            player.updateInventory();
            return true;
         } else {
            String name = TextUtil.hookPlaceholderAPI(player, TextUtil.concatenate(args));
            message = lang.getMessage(sender, "MyItems_SetName_Success");
            EquipmentUtil.setDisplayName(item, name);
            message.sendMessage(sender, "name", name);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            player.updateInventory();
            return true;
         }
      }
   }
}
