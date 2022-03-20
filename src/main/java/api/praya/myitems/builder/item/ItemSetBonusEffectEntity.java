package api.praya.myitems.builder.item;

import api.praya.myitems.builder.ability.AbilityWeapon;
import java.util.Collection;
import java.util.HashMap;

public class ItemSetBonusEffectEntity {
   private final ItemSetBonusEffectStats effectStats;
   private final HashMap<AbilityWeapon, Integer> mapAbilityWeapon;

   public ItemSetBonusEffectEntity(ItemSetBonusEffectStats effectStats, HashMap<AbilityWeapon, Integer> mapAbilityWeapon) {
      this.effectStats = effectStats;
      this.mapAbilityWeapon = mapAbilityWeapon;
   }

   public final ItemSetBonusEffectStats getEffectStats() {
      return this.effectStats;
   }

   public final Collection<AbilityWeapon> getAllAbilityWeapon() {
      return this.mapAbilityWeapon.keySet();
   }

   public final int getGradeAbilityWeapon(AbilityWeapon abilityWeapon) {
      return (Integer)this.mapAbilityWeapon.get(abilityWeapon);
   }
}
