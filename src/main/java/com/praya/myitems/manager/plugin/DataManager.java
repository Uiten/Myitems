package com.praya.myitems.manager.plugin;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.plugin.DataConfig;
import core.praya.agarthalib.builder.main.DataBuild;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataManager extends HandlerManager {
   private final DataConfig dataConfig;

   protected DataManager(MyItems plugin) {
      super(plugin);
      this.dataConfig = new DataConfig(plugin);
   }

   public final DataConfig getDataConfig() {
      return this.dataConfig;
   }

   public final Collection<String> getDataIDs() {
      return this.getDataConfig().getDataIDs();
   }

   public final Collection<DataBuild> getDataBuilds() {
      return this.getDataConfig().getDataBuilds();
   }

   public final DataBuild getData(String id) {
      return this.getDataConfig().getData(id);
   }

   public final boolean isDataExists(String id) {
      return this.getData(id) != null;
   }

   public final List<String> getListPath(String id) {
      DataBuild data = this.getData(id);
      return (List)(data != null ? data.getListPath() : new ArrayList());
   }

   public final String getPath(String id) {
      DataBuild data = this.getData(id);
      return data != null ? data.getPath() : "";
   }
}
