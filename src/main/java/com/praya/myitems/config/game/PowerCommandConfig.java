package com.praya.myitems.config.game;

import api.praya.myitems.builder.power.PowerCommandProperties;
import com.praya.agarthalib.utility.FileUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerConfig;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class PowerCommandConfig extends HandlerConfig {
   private final HashMap<String, PowerCommandProperties> mapPowerCommand = new HashMap();

   public PowerCommandConfig(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getPowerCommandIDs() {
      return this.mapPowerCommand.keySet();
   }

   public final Collection<PowerCommandProperties> getPowerCommandPropertyBuilds() {
      return this.mapPowerCommand.values();
   }

   public final PowerCommandProperties getPowerCommandProperties(String id) {
      Iterator var3 = this.getPowerCommandIDs().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(id)) {
            return (PowerCommandProperties)this.mapPowerCommand.get(key);
         }
      }

      return null;
   }

   public final void setup() {
      this.moveOldFile();
      this.reset();
      this.loadConfig();
   }

   private final void reset() {
      this.mapPowerCommand.clear();
   }

   public final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String path = dataManager.getPath("path_File_Power_Command");
      File file = FileUtil.getFile(this.plugin, path);
      if (!file.exists()) {
         FileUtil.saveResource(this.plugin, path);
      }

      FileConfiguration config = FileUtil.getFileConfiguration(file);
      Iterator var7 = config.getKeys(false).iterator();

      while(var7.hasNext()) {
         String key = (String)var7.next();
         ConfigurationSection mainDataSection = config.getConfigurationSection(key);
         List<String> commandOP = new ArrayList();
         List<String> commandConsole = new ArrayList();
         String keyLore = null;
         boolean consume = false;
         Iterator var14 = mainDataSection.getKeys(false).iterator();

         while(true) {
            while(var14.hasNext()) {
               String mainData = (String)var14.next();
               if (mainData.equalsIgnoreCase("KeyLore")) {
                  keyLore = TextUtil.colorful(mainDataSection.getString(mainData));
               } else if (mainData.equalsIgnoreCase("Consume")) {
                  consume = mainDataSection.getBoolean(mainData);
               } else if (mainData.equalsIgnoreCase("command")) {
                  if (mainDataSection.isList(mainData)) {
                     commandOP.addAll(mainDataSection.getStringList(mainData));
                  } else if (mainDataSection.isString(mainData)) {
                     commandOP.add(mainDataSection.getString(mainData));
                  } else if (mainDataSection.isConfigurationSection(mainData)) {
                     ConfigurationSection commandDataSection = mainDataSection.getConfigurationSection(mainData);
                     Iterator var17 = commandDataSection.getKeys(false).iterator();

                     while(var17.hasNext()) {
                        String commandData = (String)var17.next();
                        if (commandData.equalsIgnoreCase("OP")) {
                           if (commandDataSection.isList(commandData)) {
                              commandOP.addAll(commandDataSection.getStringList(commandData));
                           } else if (commandDataSection.isString(commandData)) {
                              commandOP.add(commandDataSection.getString(commandData));
                           }
                        } else if (commandData.equalsIgnoreCase("Console")) {
                           if (commandDataSection.isList(commandData)) {
                              commandConsole.addAll(commandDataSection.getStringList(commandData));
                           } else if (commandDataSection.isString(commandData)) {
                              commandConsole.add(commandDataSection.getString(commandData));
                           }
                        }
                     }
                  }
               }
            }

            if (keyLore != null & (!commandOP.isEmpty() || !commandConsole.isEmpty())) {
               PowerCommandProperties powerCommandProperties = new PowerCommandProperties(keyLore, consume, commandOP, commandConsole);
               this.mapPowerCommand.put(key, powerCommandProperties);
            }
            break;
         }
      }

   }

   private final void moveOldFile() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathSource = "command.yml";
      String pathTarget = dataManager.getPath("path_File_Power_Command");
      File fileSource = FileUtil.getFile(this.plugin, "command.yml");
      File fileTarget = FileUtil.getFile(this.plugin, pathTarget);
      if (fileSource.exists()) {
         FileUtil.moveFileSilent(fileSource, fileTarget);
      }

   }
}
