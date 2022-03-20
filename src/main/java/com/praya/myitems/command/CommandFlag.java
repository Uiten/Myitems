package com.praya.myitems.command;

import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerCommand;
import com.praya.myitems.manager.plugin.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandFlag extends HandlerCommand implements CommandExecutor {
   public CommandFlag(MyItems plugin) {
      super(plugin);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      CommandManager commandManager = this.plugin.getPluginManager().getCommandManager();
      if (args.length > 0) {
         String subCommand = args[0];
         String[] fullArgs;
         if (commandManager.checkCommand(subCommand, "Flag_Add")) {
            fullArgs = TextUtil.pressList(args, 2);
            return CommandFlagAdd.addFlag(sender, command, label, fullArgs);
         } else if (commandManager.checkCommand(subCommand, "Flag_Remove")) {
            fullArgs = TextUtil.pressList(args, 2);
            return CommandFlagRemove.removeFlag(sender, command, label, fullArgs);
         } else if (commandManager.checkCommand(subCommand, "Flag_Clear")) {
            fullArgs = TextUtil.pressList(args, 2);
            return CommandFlagClear.clearFlag(sender, command, label, fullArgs);
         } else {
            return true;
         }
      } else {
         return true;
      }
   }
}
