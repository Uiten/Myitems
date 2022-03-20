package api.praya.myitems.builder.element;

public class ElementBoostStats {
   private final double baseAdditionalDamage;
   private final double basePercentDamage;

   public ElementBoostStats(double baseAdditionalDamage, double basePercentDamage) {
      this.baseAdditionalDamage = baseAdditionalDamage;
      this.basePercentDamage = basePercentDamage;
   }

   public final double getBaseAdditionalDamage() {
      return this.baseAdditionalDamage;
   }

   public final double getBasePercentDamage() {
      return this.basePercentDamage;
   }
}
