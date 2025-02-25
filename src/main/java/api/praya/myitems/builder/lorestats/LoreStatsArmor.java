package api.praya.myitems.builder.lorestats;

public class LoreStatsArmor {
   private final double defense;
   private final double pvpDefense;
   private final double pveDefense;
   private final double health;
   private final double healthRegen;
   private final double staminaMax;
   private final double staminaRegen;
   private final double blockAmount;
   private final double blockRate;
   private final double dodgeRate;

   public LoreStatsArmor(double defense, double pvpDefense, double pveDefense, double health, double healthRegen, double staminaMax, double staminaRegen, double blockAmount, double blockRate, double dodgeRate) {
      this.defense = defense;
      this.pvpDefense = pvpDefense;
      this.pveDefense = pveDefense;
      this.health = health;
      this.healthRegen = healthRegen;
      this.staminaMax = staminaMax;
      this.staminaRegen = staminaRegen;
      this.blockAmount = blockAmount;
      this.blockRate = blockRate;
      this.dodgeRate = dodgeRate;
   }

   public final double getDefense() {
      return this.defense;
   }

   public final double getPvPDefense() {
      return this.pvpDefense;
   }

   public final double getPvEDefense() {
      return this.pveDefense;
   }

   public final double getHealth() {
      return this.health;
   }

   public final double getHealthRegen() {
      return this.healthRegen;
   }

   public final double getStaminaMax() {
      return this.staminaMax;
   }

   public final double getStaminaRegen() {
      return this.staminaRegen;
   }

   public final double getBlockAmount() {
      return this.blockAmount;
   }

   public final double getBlockRate() {
      return this.blockRate;
   }

   public final double getDodgeRate() {
      return this.dodgeRate;
   }
}
