package api.praya.myitems.builder.item;

import core.praya.agarthalib.enums.main.TagsAttribute;
import java.util.Collection;
import java.util.HashMap;

public class ItemTypeNBT {
   private final HashMap<TagsAttribute, Double> mapTagsAttribute;

   public ItemTypeNBT(HashMap<TagsAttribute, Double> mapTagsAttribute) {
      this.mapTagsAttribute = mapTagsAttribute;
   }

   public final Collection<TagsAttribute> getAllTagsAttribute() {
      return this.mapTagsAttribute.keySet();
   }

   public final double getTagsAttributeValue(TagsAttribute attribute) {
      return attribute != null && this.mapTagsAttribute.containsKey(attribute) ? (Double)this.mapTagsAttribute.get(attribute) : 0.0D;
   }
}
