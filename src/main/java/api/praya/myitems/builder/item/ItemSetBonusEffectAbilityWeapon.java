package api.praya.myitems.builder.item;

import api.praya.myitems.builder.ability.AbilityItemWeapon;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class ItemSetBonusEffectAbilityWeapon {
   private final HashMap<String, AbilityItemWeapon> mapAbilityItemWeapon;

   public ItemSetBonusEffectAbilityWeapon() {
      this(new HashMap());
   }

   public ItemSetBonusEffectAbilityWeapon(HashMap<String, AbilityItemWeapon> mapAbilityItemWeapon) {
      this.mapAbilityItemWeapon = mapAbilityItemWeapon;
   }

   public final Collection<String> getAbilityIDs() {
      return this.mapAbilityItemWeapon.keySet();
   }

   public final Collection<AbilityItemWeapon> getAllAbilityItemWeapon() {
      return this.mapAbilityItemWeapon.values();
   }

   public final AbilityItemWeapon getAbilityItemWeapon(String ability) {
      if (ability != null) {
         Iterator var3 = this.getAbilityIDs().iterator();

         while(var3.hasNext()) {
            String key = (String)var3.next();
            if (key.equalsIgnoreCase(ability)) {
               return (AbilityItemWeapon)this.mapAbilityItemWeapon.get(key);
            }
         }
      }

      return null;
   }

   public final boolean isAbilityExists(String ability) {
      return this.getAbilityItemWeapon(ability) != null;
   }
}
