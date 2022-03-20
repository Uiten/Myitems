package api.praya.myitems.builder.item;

public class ItemSetBonusEffect {
   private final ItemSetBonusEffectStats effectStats;
   private final ItemSetBonusEffectAbilityWeapon effectAbilityWeapon;

   public ItemSetBonusEffect(ItemSetBonusEffectStats effectStats, ItemSetBonusEffectAbilityWeapon effectAbilityWeapon) {
      this.effectStats = effectStats;
      this.effectAbilityWeapon = effectAbilityWeapon;
   }

   public final ItemSetBonusEffectStats getEffectStats() {
      return this.effectStats;
   }

   public final ItemSetBonusEffectAbilityWeapon getEffectAbilityWeapon() {
      return this.effectAbilityWeapon;
   }
}
