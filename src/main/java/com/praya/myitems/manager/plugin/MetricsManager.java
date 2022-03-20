package com.praya.myitems.manager.plugin;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.metrics.BStats;

public class MetricsManager extends HandlerManager {
   private BStats metricsBStats;

   protected MetricsManager(MyItems plugin) {
      super(plugin);
      this.metricsBStats = new BStats(plugin);
   }

   public final BStats getMetricsBStats() {
      return this.metricsBStats;
   }
}
