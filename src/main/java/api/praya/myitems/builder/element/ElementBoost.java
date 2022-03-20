package api.praya.myitems.builder.element;

public class ElementBoost {
   private final double scaleBaseAdditionalDamage;
   private final double scaleBasePercentDamage;

   public ElementBoost(double scaleBaseAdditionalDamage, double scaleBasePercentDamage) {
      this.scaleBaseAdditionalDamage = scaleBaseAdditionalDamage;
      this.scaleBasePercentDamage = scaleBasePercentDamage;
   }

   public final double getScaleBaseAdditionalDamage() {
      return this.scaleBaseAdditionalDamage;
   }

   public final double getScaleBasePercentDamage() {
      return this.scaleBasePercentDamage;
   }
}
