package api.praya.myitems.builder.power;

public class PowerSpecialProperties {
   private final int durationEffect;
   private final double baseAdditionalDamage;
   private final double basePercentDamage;

   public PowerSpecialProperties(int durationEffect, double baseAdditionalDamage, double basePercentDamage) {
      this.durationEffect = durationEffect;
      this.baseAdditionalDamage = baseAdditionalDamage;
      this.basePercentDamage = basePercentDamage;
   }

   public final int getDurationEffect() {
      return this.durationEffect;
   }

   public final double getBaseAdditionalDamage() {
      return this.baseAdditionalDamage;
   }

   public final double getBasePercentDamage() {
      return this.basePercentDamage;
   }
}
