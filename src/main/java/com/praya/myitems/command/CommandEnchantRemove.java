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
import core.praya.agarthalib.enums.branch.EnchantmentEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandEnchantRemove extends HandlerCommand implements CommandExecutor {
   public CommandEnchantRemove(MyItems plugin) {
      super(plugin);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      return removeEnchant(sender, command, label, args);
   }

   protected static final boolean removeEnchant(CommandSender sender, Command command, String label, String[] args) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      PluginManager pluginManager = plugin.getPluginManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      if (!commandManager.checkPermission(sender, "Enchant_Remove")) {
         String permission = commandManager.getPermission("Enchant_Remove");
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
            String enchantmentName;
            if (args.length < 1) {
               enchantmentName = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Enchant_Remove"));
               MessageBuild message = lang.getMessage(sender, "Argument_RemoveEnchant");
               message.sendMessage(sender, "tooltip_enchant_remove", enchantmentName);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
               return true;
            } else {
               enchantmentName = args[0];
               EnchantmentEnum enchantmentEnum = EnchantmentEnum.getEnchantmentEnum(enchantmentName);
               Enchantment enchantment = enchantmentEnum != null ? enchantmentEnum.getEnchantment() : null;
               MessageBuild message;
               if (enchantmentEnum == null) {
                  message = lang.getMessage(sender, "Item_Enchantment_Not_Exist");
                  message.sendMessage(sender, "enchantment", enchantmentName);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                  return true;
               } else if (!item.containsEnchantment(enchantment)) {
                  message = lang.getMessage(sender, "Item_Enchantment_Empty");
                  message.sendMessage(sender, "enchantment", enchantmentName);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                  return true;
               } else {
                  message = lang.getMessage(sender, "MyItems_RemoveEnchant_Success");
                  EquipmentUtil.removeEnchantment(item, enchantment);
                  message.sendMessage(sender, "enchantment", enchantment.getName());
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                  player.updateInventory();
                  return true;
               }
            }
         }
      }
   }
}
