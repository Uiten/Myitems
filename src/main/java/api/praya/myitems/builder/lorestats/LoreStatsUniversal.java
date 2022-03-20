package api.praya.myitems.builder.lorestats;

public class LoreStatsUniversal {
   private final double durability;
   private final double level;

   public LoreStatsUniversal(double durability, double level) {
      this.durability = durability;
      this.level = level;
   }

   public final double getDurability() {
      return this.durability;
   }

   public final double getLeveL() {
      return this.level;
   }
}
