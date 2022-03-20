package api.praya.myitems.builder.element;

import api.praya.myitems.builder.potion.PotionProperties;
import java.util.HashMap;
import org.bukkit.potion.PotionEffectType;

public class ElementPotion {
   private final HashMap<PotionEffectType, PotionProperties> potionAttacker;
   private final HashMap<PotionEffectType, PotionProperties> potionVictims;

   public ElementPotion(HashMap<PotionEffectType, PotionProperties> potionAttacker, HashMap<PotionEffectType, PotionProperties> potionVictims) {
      this.potionAttacker = potionAttacker;
      this.potionVictims = potionVictims;
   }

   public final HashMap<PotionEffectType, PotionProperties> getPotionAttacker() {
      return this.potionAttacker;
   }

   public final HashMap<PotionEffectType, PotionProperties> getPotionVictims() {
      return this.potionVictims;
   }
}
