package api.praya.myitems.builder.ability;

import com.praya.agarthalib.utility.MathUtil;

public class AbilityWeaponProperties {
   private final int maxGrade;
   private final int baseDurationEffect;
   private final int scaleDurationEffect;
   private final double scaleBaseBonusDamage;
   private final double scaleBasePercentDamage;
   private final double scaleCastBonusDamage;
   private final double scaleCastPercentDamage;

   public AbilityWeaponProperties(int maxGrade, int baseDurationEffect, int scaleDurationEffect, double scaleBaseBonusDamage, double scaleBasePercentDamage, double scaleCastBonusDamage, double scaleCastPercentDamage) {
      this.maxGrade = maxGrade;
      this.baseDurationEffect = baseDurationEffect;
      this.scaleDurationEffect = scaleDurationEffect;
      this.scaleBaseBonusDamage = scaleBaseBonusDamage;
      this.scaleBasePercentDamage = scaleBasePercentDamage;
      this.scaleCastBonusDamage = scaleCastBonusDamage;
      this.scaleCastPercentDamage = scaleCastPercentDamage;
   }

   public final int getMaxGrade() {
      return this.maxGrade;
   }

   public final int getBaseDurationEffect() {
      return this.baseDurationEffect;
   }

   public final int getScaleDurationEffect() {
      return this.scaleDurationEffect;
   }

   public final double getScaleBaseBonusDamage() {
      return this.scaleBaseBonusDamage;
   }

   public final double getScaleBasePercentDamage() {
      return this.scaleBasePercentDamage;
   }

   public final double getScaleCastBonusDamage() {
      return this.scaleCastBonusDamage;
   }

   public final double getScaleCastPercentDamage() {
      return this.scaleCastPercentDamage;
   }

   public final int getTotalDuration(int grade) {
      int finalGrade = MathUtil.limitInteger(grade, 0, this.getMaxGrade());
      int baseDuration = this.getBaseDurationEffect();
      int scaleDuration = this.getScaleDurationEffect();
      int totalDuration = baseDuration + finalGrade * scaleDuration;
      return totalDuration;
   }

   public final double getTotalBaseDamage(int grade, double damage) {
      int finalGrade = MathUtil.limitInteger(grade, 0, this.getMaxGrade());
      double scaleBonusDamage = this.getScaleBaseBonusDamage();
      double scalePercentDamage = this.getScaleBasePercentDamage();
      double totalDamage = (double)finalGrade * scaleBonusDamage + damage * ((double)finalGrade * scalePercentDamage / 100.0D);
      return totalDamage;
   }

   public final double getTotalCastDamage(int grade, double damage) {
      int finalGrade = MathUtil.limitInteger(grade, 0, this.getMaxGrade());
      double scaleBonusDamage = this.getScaleCastBonusDamage();
      double scalePercentDamage = this.getScaleCastPercentDamage();
      double totalDamage = (double)finalGrade * scaleBonusDamage + damage * ((double)finalGrade * scalePercentDamage / 100.0D);
      return totalDamage;
   }
}
