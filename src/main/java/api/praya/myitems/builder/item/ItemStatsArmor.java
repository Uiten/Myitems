package api.praya.myitems.builder.item;

import com.praya.agarthalib.utility.MathUtil;

public class ItemStatsArmor {
   private final double totalDefense;
   private final double totalPvPDefense;
   private final double totalPvEDefense;
   private final double totalHealth;
   private final double totalHealthRegen;
   private final double totalStaminaMax;
   private final double totalStaminaRegen;
   private final double totalBlockAmount;
   private final double totalBlockRate;
   private final double totalDodgeRate;
   private final double socketDefense;

   public ItemStatsArmor(double totalDefense, double totalPvPDefense, double totalPvEDefense, double totalHealth, double totalHealthRegen, double totalStaminaMax, double totalStaminaRegen, double totalBlockAmount, double totalBlockRate, double totalDodgeRate, double socketDefense) {
      this.totalDefense = totalDefense;
      this.totalPvPDefense = totalPvPDefense;
      this.totalPvEDefense = totalPvEDefense;
      this.totalHealth = totalHealth;
      this.totalHealthRegen = totalHealthRegen;
      this.totalStaminaMax = totalStaminaMax;
      this.totalStaminaRegen = totalStaminaRegen;
      this.totalBlockAmount = MathUtil.limitDouble(totalBlockAmount, 0.0D, 100.0D);
      this.totalBlockRate = MathUtil.limitDouble(totalBlockRate, 0.0D, 100.0D);
      this.totalDodgeRate = totalDodgeRate;
      this.socketDefense = socketDefense;
   }

   public final double getTotalDefense() {
      return this.totalDefense;
   }

   public final double getTotalPvPDefense() {
      return this.totalPvPDefense;
   }

   public final double getTotalPvEDefense() {
      return this.totalPvEDefense;
   }

   public final double getTotalHealth() {
      return this.totalHealth;
   }

   public final double getTotalHealthRegen() {
      return this.totalHealthRegen;
   }

   public final double getTotalStaminaMax() {
      return this.totalStaminaMax;
   }

   public final double getTotalStaminaRegen() {
      return this.totalStaminaRegen;
   }

   public final double getTotalBlockAmount() {
      return this.totalBlockAmount;
   }

   public final double getTotalBlockRate() {
      return this.totalBlockRate;
   }

   public final double getTotalDodgeRate() {
      return this.totalDodgeRate;
   }

   public final double getSocketDefense() {
      return this.socketDefense;
   }
}
