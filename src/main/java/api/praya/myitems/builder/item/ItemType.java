package api.praya.myitems.builder.item;

import api.praya.myitems.builder.lorestats.LoreStatsModifier;
import core.praya.agarthalib.enums.main.Slot;
import java.util.Collection;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class ItemType {
   private final String id;
   private final Material material;
   private final short data;
   private final boolean shiny;
   private final LoreStatsModifier statsModifier;
   private final HashMap<Enchantment, Integer> mapEnchantment;
   private final HashMap<Slot, ItemTypeNBT> mapNBT;

   public ItemType(String id, Material material, short data, boolean shiny, LoreStatsModifier statsModifier, HashMap<Enchantment, Integer> mapEnchantment, HashMap<Slot, ItemTypeNBT> mapNBT) {
      this.id = id;
      this.material = material;
      this.data = data;
      this.shiny = shiny;
      this.statsModifier = statsModifier;
      this.mapEnchantment = mapEnchantment;
      this.mapNBT = mapNBT;
   }

   public final String getId() {
      return this.id;
   }

   public final Material getMaterial() {
      return this.material;
   }

   public final short getData() {
      return this.data;
   }

   public final boolean isShiny() {
      return this.shiny;
   }

   public final LoreStatsModifier getStatsModifier() {
      return this.statsModifier;
   }

   public final Collection<Enchantment> getEnchantments() {
      return this.mapEnchantment.keySet();
   }

   public final int getEnchantmentGrade(Enchantment enchantment) {
      return enchantment != null && this.mapEnchantment.containsKey(enchantment) ? (Integer)this.mapEnchantment.get(enchantment) : 0;
   }

   public final Collection<Slot> getAllSlotNBT() {
      return this.mapNBT.keySet();
   }

   public final ItemTypeNBT getSlotNBT(Slot slot) {
      return (ItemTypeNBT)this.mapNBT.get(slot);
   }

   public final Slot getDefaultSlot() {
      return Slot.getDefault(this.getMaterial());
   }
}
