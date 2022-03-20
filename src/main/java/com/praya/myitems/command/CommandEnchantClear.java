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
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandEnchantClear extends HandlerCommand implements CommandExecutor {
   public CommandEnchantClear(MyItems plugin) {
      super(plugin);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      return clearEnchant(sender, command, label, args);
   }

   protected static final boolean clearEnchant(CommandSender sender, Command command, String label, String[] args) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      PluginManager pluginManager = plugin.getPluginManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      if (!commandManager.checkPermission(sender, "Enchant_Clear")) {
         String permission = commandManager.getPermission("Enchant_Clear");
         MessageBuild message = lang.getMessage(sender, "Permission_Lack");
         message.sendMessage(sender, "permission", permission);
         SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
         return true;
      } else if (!SenderUtil.isPlayer(sender)) {
         MessageBuild message = lang.getMessage(sender, "Console_Command_Forbiden");
         message.sendMessage(sender);
         SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
         return false;
      } else {
         Player player = PlayerUtil.parse(sender);
         ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
         MessageBuild message;
         if (!EquipmentUtil.isSolid(item)) {
            message = lang.getMessage(sender, "Item_MainHand_Empty");
            message.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return false;
         } else if (!EquipmentUtil.hasLore(item)) {
            message = lang.getMessage(sender, "Item_Lore_Empty");
            message.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return false;
         } else {
            message = lang.getMessage(sender, "MyItems_ClearEnchant_Success");
            EquipmentUtil.clearEnchantment(item);
            message.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            player.updateInventory();
            return true;
         }
      }
   }
}
