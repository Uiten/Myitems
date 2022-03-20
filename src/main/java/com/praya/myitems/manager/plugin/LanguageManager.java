package com.praya.myitems.manager.plugin;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.plugin.LanguageConfig;
import com.praya.myitems.config.plugin.MainConfig;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.main.LanguageBuild;
import core.praya.agarthalib.builder.message.MessageBuild;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class LanguageManager extends HandlerManager {
   private final LanguageConfig langConfig;

   protected LanguageManager(MyItems plugin) {
      super(plugin);
      this.langConfig = new LanguageConfig(plugin);
   }

   public final LanguageConfig getLangConfig() {
      return this.langConfig;
   }

   public final Collection<String> getLanguageIDs() {
      return this.getLangConfig().getLanguageIDs();
   }

   public final Collection<LanguageBuild> getLanguageBuilds() {
      return this.getLangConfig().getLanguageBuilds();
   }

   public final LanguageBuild getLanguageBuild(String id) {
      return this.getLangConfig().getLanguageBuild(id);
   }

   public final LanguageBuild getLanguage(String id) {
      return this.getLangConfig().getLanguage(id);
   }

   public final boolean isLanguageExists(String id) {
      return this.getLanguageBuild(id) != null;
   }

   public final List<String> getListText(LivingEntity entity, String key) {
      if (entity != null && entity instanceof Player) {
         Player player = (Player)entity;
         String locale = Bridge.getBridgePlayer().getLocale(player);
         return this.getListText(locale, key);
      } else {
         return this.getListText(key);
      }
   }

   public final List<String> getListText(CommandSender sender, String key) {
      if (sender != null && sender instanceof Player) {
         Player player = (Player)sender;
         String locale = Bridge.getBridgePlayer().getLocale(player);
         return this.getListText(locale, key);
      } else {
         return this.getListText(key);
      }
   }

   public final List<String> getListText(String key) {
      MainConfig mainConfig = MainConfig.getInstance();
      String locale = mainConfig.getGeneralLocale();
      return this.getListText(locale, key);
   }

   public final List<String> getListText(String id, String key) {
      MessageBuild message = this.getMessage(id, key);
      return (List)(message != null ? message.getListText() : new ArrayList());
   }

   public final String getText(LivingEntity entity, String key) {
      if (entity != null && entity instanceof Player) {
         Player player = (Player)entity;
         String locale = Bridge.getBridgePlayer().getLocale(player);
         return this.getText(locale, key);
      } else {
         return this.getText(key);
      }
   }

   public final String getText(CommandSender sender, String key) {
      if (sender != null && sender instanceof Player) {
         Player player = (Player)sender;
         String locale = Bridge.getBridgePlayer().getLocale(player);
         return this.getText(locale, key);
      } else {
         return this.getText(key);
      }
   }

   public final String getText(String key) {
      MainConfig mainConfig = MainConfig.getInstance();
      String locale = mainConfig.getGeneralLocale();
      return this.getText(locale, key);
   }

   public final String getText(String id, String key) {
      MessageBuild message = this.getMessage(id, key);
      return message != null ? message.getText() : "";
   }

   public final MessageBuild getMessage(LivingEntity entity, String key) {
      if (entity != null && entity instanceof Player) {
         Player player = (Player)entity;
         String locale = Bridge.getBridgePlayer().getLocale(player);
         return this.getMessage(locale, key);
      } else {
         return this.getMessage(key);
      }
   }

   public final MessageBuild getMessage(CommandSender sender, String key) {
      if (sender != null && sender instanceof Player) {
         Player player = (Player)sender;
         String locale = Bridge.getBridgePlayer().getLocale(player);
         return this.getMessage(locale, key);
      } else {
         return this.getMessage(key);
      }
   }

   public final MessageBuild getMessage(String key) {
      MainConfig mainConfig = MainConfig.getInstance();
      String locale = mainConfig.getGeneralLocale();
      return this.getMessage(locale, key);
   }

   public final MessageBuild getMessage(String id, String key) {
      return this.getLangConfig().getMessage(id, key);
   }
}
