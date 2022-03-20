package com.praya.myitems.config.plugin;

import com.praya.agarthalib.utility.FileUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerConfig;
import core.praya.agarthalib.builder.main.DataBuild;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;

public class DataConfig extends HandlerConfig {
   private final HashMap<String, DataBuild> mapData = new HashMap();

   public DataConfig(MyItems plugin) {
      super(plugin);
      this.setup();
   }

   public final Collection<String> getDataIDs() {
      return this.mapData.keySet();
   }

   public final Collection<DataBuild> getDataBuilds() {
      return this.mapData.values();
   }

   public final DataBuild getData(String id) {
      if (id != null) {
         Iterator var3 = this.getDataIDs().iterator();

         while(var3.hasNext()) {
            String key = (String)var3.next();
            if (key.equalsIgnoreCase(id)) {
               return (DataBuild)this.mapData.get(key);
            }
         }
      }

      return null;
   }

   public final void setup() {
      this.reset();
      this.loadConfig();
   }

   private final void reset() {
      this.mapData.clear();
   }

   private final void loadConfig() {
      FileConfiguration config = FileUtil.getFileConfigurationResource(this.plugin, "Resources/data.yml");
      Iterator var3 = config.getKeys(true).iterator();

      while(var3.hasNext()) {
         String path = (String)var3.next();
         String key = path.replace(".", "_");
         if (config.isString(path)) {
            String text = config.getString(path);
            List<String> list = new ArrayList();
            list.add(text);
            DataBuild dataBuild = new DataBuild(key, list);
            this.mapData.put(key, dataBuild);
         } else if (config.isList(path)) {
            List<String> list = config.getStringList(path);
            DataBuild dataBuild = new DataBuild(key, list);
            this.mapData.put(key, dataBuild);
         }
      }

   }
}
