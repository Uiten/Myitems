package api.praya.myitems.builder.ability;

import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public abstract class Ability {
   private final Plugin plugin;
   private final String id;

   protected Ability(Plugin plugin, String id) {
      this.plugin = plugin;
      this.id = id;
   }

   public abstract String getKeyLore();

   public abstract List<String> getDescription();

   public abstract int getMaxGrade();

   public abstract void cast(Entity var1, Entity var2, int var3, double var4);

   public final Plugin getPlugin() {
      return this.plugin;
   }

   public final String getID() {
      return this.id;
   }
}
