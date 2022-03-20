package api.praya.myitems.builder.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class ItemSetComponentItem {
   private final String displayName;
   private final Material material;
   private final int data;
   private final boolean shiny;
   private final boolean unbreakable;
   private final List<String> lores;
   private final List<String> flags;
   private final HashMap<Enchantment, Integer> mapEnchantment;

   public ItemSetComponentItem(String displayName, Material material, int data, boolean shiny, boolean unbreakable, List<String> lores, List<String> flags, HashMap<Enchantment, Integer> mapEnchantment) {
      this.displayName = displayName;
      this.material = material;
      this.data = data;
      this.shiny = shiny;
      this.unbreakable = unbreakable;
      this.lores = lores;
      this.flags = flags;
      this.mapEnchantment = mapEnchantment;
   }

   public final String getDisplayName() {
      return this.displayName;
   }

   public final Material getMaterial() {
      return this.material;
   }

   public final int getData() {
      return this.data;
   }

   public final boolean isShiny() {
      return this.shiny;
   }

   public final boolean isUnbreakable() {
      return this.unbreakable;
   }

   public final List<String> getLores() {
      return this.lores;
   }

   public final List<String> getFlags() {
      return this.flags;
   }

   public final Collection<Enchantment> getEnchantments() {
      return this.mapEnchantment.keySet();
   }

   public final int getEnchantmentGrade(Enchantment enchantment) {
      return (Integer)this.mapEnchantment.get(enchantment);
   }

   public final boolean hasEnchantment(Enchantment enchantment) {
      return enchantment != null ? this.mapEnchantment.containsKey(enchantment) : false;
   }
}
