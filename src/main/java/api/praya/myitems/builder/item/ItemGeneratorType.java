package api.praya.myitems.builder.item;

import java.util.List;

public class ItemGeneratorType {
   private final int possibility;
   private final List<String> description;
   private final List<String> names;

   public ItemGeneratorType(int possibility, List<String> description, List<String> names) {
      this.possibility = possibility;
      this.description = description;
      this.names = names;
   }

   public final int getPossibility() {
      return this.possibility;
   }

   public final List<String> getDescription() {
      return this.description;
   }

   public final List<String> getNames() {
      return this.names;
   }
}
