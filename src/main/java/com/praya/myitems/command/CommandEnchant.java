package com.praya.myitems.command;

import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerCommand;
import com.praya.myitems.manager.plugin.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandEnchant extends HandlerCommand implements CommandExecutor {
   public CommandEnchant(MyItems plugin) {
      super(plugin);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      CommandManager commandManager = this.plugin.getPluginManager().getCommandManager();
      if (args.length > 0) {
         String subCommand = args[0];
         String[] fullArgs;
         if (commandManager.checkCommand(subCommand, "Enchant_Add")) {
            fullArgs = TextUtil.pressList(args, 2);
            return CommandEnchantAdd.addEnchant(sender, command, label, fullArgs);
         } else if (commandManager.checkCommand(subCommand, "Enchant_Remove")) {
            fullArgs = TextUtil.pressList(args, 2);
            return CommandEnchantRemove.removeEnchant(sender, command, label, fullArgs);
         } else if (commandManager.checkCommand(subCommand, "Enchant_Clear")) {
            fullArgs = TextUtil.pressList(args, 2);
            return CommandEnchantClear.clearEnchant(sender, command, label, fullArgs);
         } else {
            return true;
         }
      } else {
         return true;
      }
   }
}
