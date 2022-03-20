package com.praya.myitems.command;

import com.praya.agarthalib.utility.SenderUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerCommand;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandNotCompatible extends HandlerCommand implements CommandExecutor {
   public CommandNotCompatible(MyItems plugin) {
      super(plugin);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      MessageBuild message = lang.getMessage(sender, "MyItems_Not_Compatible");
      message.sendMessage(sender);
      SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
      return true;
   }
}
