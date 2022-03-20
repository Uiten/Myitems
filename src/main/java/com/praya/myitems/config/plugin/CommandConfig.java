package com.praya.myitems.config.plugin;

import com.praya.agarthalib.utility.FileUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerCommand;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.builder.command.CommandBuild;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class CommandConfig extends HandlerCommand {
   private final HashMap<String, CommandBuild> mapCommand = new HashMap();

   public CommandConfig(MyItems plugin) {
      super(plugin);
      this.setup();
   }

   public final Collection<String> getCommandIDs() {
      return this.mapCommand.keySet();
   }

   public final Collection<CommandBuild> getCommandBuilds() {
      return this.mapCommand.values();
   }

   public final CommandBuild getCommand(String id) {
      Iterator var3 = this.getCommandIDs().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(id)) {
            return (CommandBuild)this.mapCommand.get(key);
         }
      }

      return null;
   }

   public final void setup() {
      this.reset();
      this.loadConfig();
   }

   private final void reset() {
      this.mapCommand.clear();
   }

   private final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String path = dataManager.getPath("Path_File_Command");
      FileConfiguration config = FileUtil.getFileConfigurationResource(this.plugin, path);
      Iterator var6 = config.getKeys(false).iterator();

      while(true) {
         String key;
         do {
            if (!var6.hasNext()) {
               return;
            }

            key = (String)var6.next();
         } while(!key.equalsIgnoreCase("Command"));

         ConfigurationSection idSection = config.getConfigurationSection(key);
         Iterator var9 = idSection.getKeys(false).iterator();

         while(var9.hasNext()) {
            String id = (String)var9.next();
            ConfigurationSection mainDataSection = idSection.getConfigurationSection(id);
            List<String> aliases = new ArrayList();
            String permission = null;
            Iterator var14 = mainDataSection.getKeys(false).iterator();

            while(var14.hasNext()) {
               String mainData = (String)var14.next();
               if (mainData.equalsIgnoreCase("Permission")) {
                  permission = mainDataSection.getString(mainData);
               } else if (mainData.equalsIgnoreCase("Aliases")) {
                  if (mainDataSection.isString(mainData)) {
                     aliases.add(mainDataSection.getString(mainData));
                  } else if (mainDataSection.isList(mainData)) {
                     aliases.addAll(mainDataSection.getStringList(mainData));
                  }
               }
            }

            CommandBuild commandBuild = new CommandBuild(id, permission, aliases);
            this.mapCommand.put(id, commandBuild);
         }
      }
   }
}
