package com.praya.myitems.config.plugin;

import com.praya.agarthalib.utility.FileUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerConfig;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PlaceholderManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.builder.main.LanguageBuild;
import core.praya.agarthalib.builder.message.MessageBuild;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.bukkit.configuration.file.FileConfiguration;

public class LanguageConfig extends HandlerConfig {
   private final HashMap<String, LanguageBuild> mapLanguage = new HashMap();

   public LanguageConfig(MyItems plugin) {
      super(plugin);
      this.setup();
   }

   public final Collection<String> getLanguageIDs() {
      return this.mapLanguage.keySet();
   }

   public final Collection<LanguageBuild> getLanguageBuilds() {
      return this.mapLanguage.values();
   }

   public final LanguageBuild getLanguageBuild(String id) {
      if (id != null) {
         Iterator var3 = this.getLanguageIDs().iterator();

         while(var3.hasNext()) {
            String key = (String)var3.next();
            if (key.equalsIgnoreCase(id)) {
               return (LanguageBuild)this.mapLanguage.get(key);
            }
         }
      }

      return null;
   }

   public final LanguageBuild getLanguage(String id) {
      if (id != null) {
         String[] parts = id.split("_");
         int length = parts.length;

         for(int size = length; size > 0; --size) {
            StringBuilder builder = new StringBuilder();

            for(int index = 0; index < size; ++index) {
               String component = parts[index];
               builder.append(component);
               if (index != size - 1) {
                  builder.append("_");
               }

               String identifier = builder.toString();
               LanguageBuild languageBuild = this.getLanguageBuild(identifier);
               if (languageBuild != null) {
                  return languageBuild;
               }
            }
         }
      }

      return this.getLanguageBuild("en");
   }

   public final MessageBuild getMessageBuild(String id, String key) {
      if (id != null && key != null) {
         LanguageBuild languageBuild = this.getLanguageBuild(id);
         if (languageBuild != null) {
            return languageBuild.getMessage(key);
         }
      }

      return new MessageBuild();
   }

   public final MessageBuild getMessage(String id, String key) {
      if (id != null) {
         String[] parts = id.split("_");
         int length = parts.length;

         for(int size = length; size > 0; --size) {
            StringBuilder builder = new StringBuilder();

            for(int index = 0; index < size; ++index) {
               String component = parts[index];
               builder.append(component);
               if (index != size - 1) {
                  builder.append("_");
               }

               String identifier = builder.toString();
               LanguageBuild languageBuild = this.getLanguageBuild(identifier);
               if (languageBuild != null) {
                  MessageBuild message = languageBuild.getMessage(key);
                  if (message.isSet()) {
                     return message;
                  }
               }
            }
         }
      }

      return this.getMessageBuild("en", key);
   }

   public final void setup() {
      this.moveOldFile();
      this.reset();
      this.loadConfig();
   }

   private final void reset() {
      this.mapLanguage.clear();
   }

   private final void loadConfig() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      DataManager dataManager = pluginManager.getDataManager();
      String pathFolder = dataManager.getPath("Path_Folder_Language");
      File folder = FileUtil.getFile(this.plugin, pathFolder);
      List<String> listPath = dataManager.getListPath("Path_File_Language");
      Iterator var7 = listPath.iterator();

      String name;
      String id;
      while(var7.hasNext()) {
         String pathFile = (String)var7.next();
         File file = FileUtil.getFile(this.plugin, pathFile);
         name = file.getName().toLowerCase();
         name = name.split(Pattern.quote("."))[0];
         id = name.startsWith("lang_") ? name.replaceFirst("lang_", "") : "en";
         if (!file.exists()) {
            FileUtil.saveResource(this.plugin, pathFile);
         }

         FileConfiguration config = FileUtil.getFileConfigurationResource(this.plugin, pathFile);
         LanguageBuild language = this.loadLanguage(id, config);
         this.mapLanguage.put(id, language);
      }

      File[] var19;
      int var18 = (var19 = folder.listFiles()).length;

      for(int var17 = 0; var17 < var18; ++var17) {
         File file = var19[var17];
         name = file.getName().toLowerCase();
         id = name.split(Pattern.quote("."))[0];
         String locale = id.startsWith("lang_") ? id.replaceFirst("lang_", "") : "en";
         FileConfiguration config = FileUtil.getFileConfiguration(file);
         LanguageBuild language = this.loadLanguage(locale, config);
         LanguageBuild localeLang = this.getLanguage(locale);
         if (localeLang != null) {
            localeLang.mergeLanguage(language);
         } else {
            this.mapLanguage.put(id, language);
         }
      }

   }

   private final LanguageBuild loadLanguage(String locale, FileConfiguration config) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      PlaceholderManager placeholderManager = pluginManager.getPlaceholderManager();
      HashMap<String, MessageBuild> mapLanguage = new HashMap();
      Iterator var7 = config.getKeys(true).iterator();

      while(var7.hasNext()) {
         String path = (String)var7.next();
         String key = path.replace(".", "_");
         if (config.isString(path)) {
            String text = config.getString(path);
            List<String> list = new ArrayList();
            list.add(text);
            List<String> listPlaceholder = placeholderManager.localPlaceholder((List)list);
            MessageBuild messages = new MessageBuild(listPlaceholder);
            mapLanguage.put(key, messages);
         } else if (config.isList(path)) {
            List<String> list = config.getStringList(path);
            List<String> listPlaceholder = placeholderManager.localPlaceholder(list);
            MessageBuild messages = new MessageBuild(listPlaceholder);
            mapLanguage.put(key, messages);
         }
      }

      return new LanguageBuild(locale, mapLanguage);
   }

   private final void moveOldFile() {
      String pathSource = "language.yml";
      String pathTarget = "Language/lang_en.yml";
      File fileSource = FileUtil.getFile(this.plugin, "language.yml");
      File fileTarget = FileUtil.getFile(this.plugin, "Language/lang_en.yml");
      if (fileSource.exists()) {
         FileUtil.moveFileSilent(fileSource, fileTarget);
      }

   }
}
