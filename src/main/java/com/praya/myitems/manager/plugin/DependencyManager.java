package com.praya.myitems.manager.plugin;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.plugin.DependencyConfig;
import core.praya.agarthalib.enums.main.Dependency;
import java.util.Collection;
import java.util.Iterator;

public class DependencyManager extends HandlerManager {
   private final DependencyConfig dependencyConfig;

   protected DependencyManager(MyItems plugin) {
      super(plugin);
      this.dependencyConfig = new DependencyConfig(plugin);
   }

   public final DependencyConfig getDependencyConfig() {
      return this.dependencyConfig;
   }

   public final Collection<String> getDependency(Dependency type) {
      return this.getDependencyConfig().getDependency(type);
   }

   public final boolean hasDependency(Dependency type) {
      return this.getDependencyConfig().hasDependency(type);
   }

   public final boolean hasAnyDependency() {
      return this.getDependencyConfig().hasAnyDependency();
   }

   public final Dependency getTypeDependency(String dependency) {
      Dependency[] var5;
      int var4 = (var5 = Dependency.values()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Dependency type = var5[var3];
         Iterator var7 = this.getDependency(type).iterator();

         while(var7.hasNext()) {
            String depends = (String)var7.next();
            if (depends.equalsIgnoreCase(dependency)) {
               return type;
            }
         }
      }

      return null;
   }

   public final boolean containsDependency(String dependency) {
      return this.getTypeDependency(dependency) != null;
   }
}
