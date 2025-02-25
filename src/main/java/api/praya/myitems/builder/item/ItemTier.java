package api.praya.myitems.builder.item;

import api.praya.myitems.builder.lorestats.LoreStatsModifier;

public class ItemTier {
   private final String id;
   private final String name;
   private final String prefix;
   private final LoreStatsModifier statsModifier;

   public ItemTier(String id, String name, String prefix, LoreStatsModifier statsModifier) {
      this.id = id;
      this.name = name;
      this.prefix = prefix;
      this.statsModifier = statsModifier;
   }

   public final String getId() {
      return this.id;
   }

   public final String getPrefix() {
      return this.prefix;
   }

   public final String getName() {
      return this.name;
   }

   public final LoreStatsModifier getStatsModifier() {
      return this.statsModifier;
   }
}
