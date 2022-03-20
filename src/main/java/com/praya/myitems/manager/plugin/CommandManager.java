package com.praya.myitems.manager.plugin;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.plugin.CommandConfig;
import core.praya.agarthalib.builder.command.CommandBuild;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.bukkit.command.CommandSender;

public class CommandManager extends HandlerManager {
   private final CommandConfig commandConfig;

   protected CommandManager(MyItems plugin) {
      super(plugin);
      this.commandConfig = new CommandConfig(plugin);
   }

   public final CommandConfig getCommandConfig() {
      return this.commandConfig;
   }

   public final Collection<String> getCommandIDs() {
      return this.getCommandConfig().getCommandIDs();
   }

   public final Collection<CommandBuild> getCommandBuilds() {
      return this.getCommandConfig().getCommandBuilds();
   }

   public final CommandBuild getCommand(String id) {
      return this.getCommandConfig().getCommand(id);
   }

   public final boolean isCommandExists(String id) {
      return this.getCommand(id) != null;
   }

   public final boolean checkCommand(String arg, String id) {
      CommandBuild commandBuild = this.getCommand(id);
      if (commandBuild != null) {
         Iterator var5 = commandBuild.getAliases().iterator();

         while(var5.hasNext()) {
            String aliases = (String)var5.next();
            if (aliases.equalsIgnoreCase(arg)) {
               return true;
            }
         }
      }

      return false;
   }

   public final boolean checkPermission(CommandSender sender, String id) {
      CommandBuild commandBuild = this.getCommand(id);
      if (commandBuild != null) {
         String permission = commandBuild.getPermission();
         return permission != null ? sender.hasPermission(permission) : true;
      } else {
         return true;
      }
   }

   public final List<String> getAliases(String id) {
      CommandBuild commandBuild = this.getCommand(id);
      return (List)(commandBuild != null ? commandBuild.getAliases() : new ArrayList());
   }

   public final String getPermission(String id) {
      CommandBuild commandBuild = this.getCommand(id);
      return commandBuild != null ? commandBuild.getPermission() : null;
   }
}
