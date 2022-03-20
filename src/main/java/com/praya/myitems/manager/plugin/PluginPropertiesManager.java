package com.praya.myitems.manager.plugin;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import core.praya.agarthalib.builder.plugin.PluginBannedPropertiesBuild;
import core.praya.agarthalib.builder.plugin.PluginPropertiesBuild;
import core.praya.agarthalib.builder.plugin.PluginPropertiesResourceBuild;
import core.praya.agarthalib.builder.plugin.PluginPropertiesStreamBuild;
import core.praya.agarthalib.builder.plugin.PluginTypePropertiesBuild;
import core.praya.agarthalib.utility.ServerUtil;
import core.praya.agarthalib.utility.SortUtil;
import core.praya.agarthalib.utility.SystemUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PluginPropertiesManager extends HandlerManager {
   private final PluginPropertiesResourceBuild pluginPropertiesResource;
   private final PluginPropertiesStreamBuild pluginPropertiesStream;

   public PluginPropertiesManager(MyItems plugin) {
      super(plugin);
      this.pluginPropertiesResource = PluginPropertiesBuild.getPluginPropertiesResource(plugin, plugin.getPluginType(), plugin.getPluginVersion());
      this.pluginPropertiesStream = PluginPropertiesBuild.getPluginPropertiesStream(plugin);
   }

   public final PluginPropertiesResourceBuild getPluginPropertiesResource() {
      return this.pluginPropertiesResource;
   }

   public final PluginPropertiesStreamBuild getPluginPropertiesStream() {
      return this.pluginPropertiesStream;
   }

   public final boolean isActivated() {
      return this.getPluginPropertiesStream().isActivated();
   }

   public final String getName() {
      return this.getPluginPropertiesStream().getName();
   }

   public final String getCompany() {
      return this.getPluginPropertiesStream().getCompany();
   }

   public final String getAuthor() {
      return this.getPluginPropertiesStream().getAuthor();
   }

   public final String getWebsite() {
      return this.getPluginPropertiesStream().getWebsite();
   }

   public final List<String> getDevelopers() {
      return this.getPluginPropertiesStream().getDevelopers();
   }

   public final List<String> getListType() {
      return SortUtil.toArray(this.getPluginPropertiesStream().getTypeProperties().keySet());
   }

   public final PluginTypePropertiesBuild getTypeProperties(String type) {
      if (type != null) {
         Iterator var3 = this.getListType().iterator();

         while(var3.hasNext()) {
            String key = (String)var3.next();
            if (key.equalsIgnoreCase(type)) {
               return (PluginTypePropertiesBuild)this.getPluginPropertiesStream().getTypeProperties().get(key);
            }
         }
      }

      return null;
   }

   public final boolean isTypeExists(String type) {
      return this.getTypeProperties(type) != null;
   }

   public final boolean isLatestVersion() {
      String type = this.plugin.getPluginType();
      String versionLatest = this.getPluginTypeVersion(type);
      String versionCurrent = this.plugin.getPluginVersion();
      return versionLatest.equalsIgnoreCase(versionCurrent);
   }

   public final String getPluginTypeVersion(String type) {
      PluginTypePropertiesBuild typeProperties = this.getTypeProperties(type);
      return typeProperties != null ? typeProperties.getVersion() : this.getPluginPropertiesResource().getVersion();
   }

   public final String getPluginTypeWebsite(String type) {
      PluginTypePropertiesBuild typeProperties = this.getTypeProperties(type);
      return typeProperties != null ? typeProperties.getWebsite() : this.getPluginPropertiesResource().getWebsite();
   }

   /** @deprecated */
   @Deprecated
   public final String getLatestVersion(String type) {
      return this.getPluginTypeVersion(type);
   }

   /** @deprecated */
   @Deprecated
   public final List<String> getAnnouncement(String type) {
      PluginTypePropertiesBuild typeProperties = this.getTypeProperties(type);
      return (List)(typeProperties != null ? typeProperties.getAnnouncement() : new ArrayList());
   }

   /** @deprecated */
   @Deprecated
   public final List<String> getChangelog(String type) {
      PluginTypePropertiesBuild typeProperties = this.getTypeProperties(type);
      return (List)(typeProperties != null ? typeProperties.getChangelog() : new ArrayList());
   }

   /** @deprecated */
   @Deprecated
   public final List<String> getReasonBanned() {
      return SortUtil.toArray(this.getPluginPropertiesStream().getBannedProperties().keySet());
   }

   /** @deprecated */
   @Deprecated
   public final boolean isReasonBannedExists(String reason) {
      return this.getBannedProperties(reason) != null;
   }

   /** @deprecated */
   @Deprecated
   public final PluginBannedPropertiesBuild getBannedProperties(String reason) {
      Iterator var3 = this.getPluginPropertiesStream().getBannedProperties().keySet().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(reason)) {
            return (PluginBannedPropertiesBuild)this.getPluginPropertiesStream().getBannedProperties().get(key);
         }
      }

      return null;
   }

   public final void check() {
      PluginManager pluginManager = this.plugin.getPluginManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      String message;
      if (!this.isActivated()) {
         message = lang.getText("Plugin_Deactivated");
         this.disable(message);
      } else if (!this.checkPluginName() || !this.checkPluginAuthor()) {
         message = lang.getText("Plugin_Information_Not_Match");
         this.disable(message);
      }
   }

   private final boolean checkPluginName() {
      return this.getName() == null || this.getName().equalsIgnoreCase(this.getPluginPropertiesResource().getName());
   }

   private final boolean checkPluginAuthor() {
      return this.getAuthor() == null || this.getAuthor().equalsIgnoreCase(this.getPluginPropertiesResource().getAuthor());
   }

   private final void disable(String message) {
      if (message != null) {
         SystemUtil.sendMessage(message);
      }

      this.plugin.getPluginLoader().disablePlugin(this.plugin);
   }

   /** @deprecated */
   @Deprecated
   public final boolean isBanned() {
      return this.getBannedReason() != null;
   }

   /** @deprecated */
   @Deprecated
   public final String getBannedReason() {
      String serverIP = ServerUtil.getIP();
      Iterator var3 = this.pluginPropertiesStream.getBannedProperties().keySet().iterator();

      while(var3.hasNext()) {
         String reason = (String)var3.next();
         Iterator var5 = ((PluginBannedPropertiesBuild)this.pluginPropertiesStream.getBannedProperties().get(reason)).getServers().iterator();

         while(var5.hasNext()) {
            String ip = (String)var5.next();
            if (ip.equalsIgnoreCase(serverIP)) {
               return reason;
            }
         }
      }

      return null;
   }
}
