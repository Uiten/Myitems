package api.praya.myitems.builder.element;

import java.util.HashMap;

public class Element {
   private final String keyLore;
   private final ElementBoost boostBuild;
   private final ElementPotion potionBuild;
   private final HashMap<String, Double> resistance;

   public Element(String keyLore, ElementBoost boostBuild, ElementPotion potionBuild, HashMap<String, Double> resistance) {
      this.keyLore = keyLore;
      this.boostBuild = boostBuild;
      this.potionBuild = potionBuild;
      this.resistance = resistance;
   }

   public final String getKeyLore() {
      return this.keyLore;
   }

   public final ElementBoost getBoostBuild() {
      return this.boostBuild;
   }

   public final ElementPotion getPotionBuild() {
      return this.potionBuild;
   }

   public final HashMap<String, Double> getResistance() {
      return this.resistance;
   }
}
