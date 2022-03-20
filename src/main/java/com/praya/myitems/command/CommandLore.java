package com.praya.myitems.command;

import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerCommand;
import com.praya.myitems.manager.plugin.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandLore extends HandlerCommand implements CommandExecutor {
   public CommandLore(MyItems plugin) {
      super(plugin);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      CommandManager commandManager = this.plugin.getPluginManager().getCommandManager();
      if (args.length > 0) {
         String subCommand = args[0];
         String[] fullArgs;
         if (commandManager.checkCommand(subCommand, "Lore_Set")) {
            fullArgs = TextUtil.pressList(args, 2);
            return CommandLoreSet.setLore(sender, command, label, fullArgs);
         } else if (commandManager.checkCommand(subCommand, "Lore_Insert")) {
            fullArgs = TextUtil.pressList(args, 2);
            return CommandLoreInsert.insertLore(sender, command, label, fullArgs);
         } else if (commandManager.checkCommand(subCommand, "Lore_Add")) {
            fullArgs = TextUtil.pressList(args, 2);
            return CommandLoreAdd.addLore(sender, command, label, fullArgs);
         } else if (commandManager.checkCommand(subCommand, "Lore_Remove")) {
            fullArgs = TextUtil.pressList(args, 2);
            return CommandLoreRemove.removeLore(sender, command, label, fullArgs);
         } else if (commandManager.checkCommand(subCommand, "Lore_Clear")) {
            fullArgs = TextUtil.pressList(args, 2);
            return CommandLoreClear.clearLore(sender, command, label, fullArgs);
         } else {
            return true;
         }
      } else {
         return true;
      }
   }
}
