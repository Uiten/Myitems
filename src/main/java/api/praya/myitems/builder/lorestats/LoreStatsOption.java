package api.praya.myitems.builder.lorestats;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public enum LoreStatsOption {
   MIN("Min", Arrays.asList("Min", "Minimal", "Low", "Lowest")),
   MAX("Max", Arrays.asList("Max", "Maximal", "High", "Highest")),
   CURRENT("Current", Arrays.asList("Current", "Now"));

   private final String text;
   private final List<String> aliases;

   private LoreStatsOption(String text, List<String> aliases) {
      this.text = text;
      this.aliases = aliases;
   }

   public final String getText() {
      return this.text;
   }

   public final List<String> getAliases() {
      return this.aliases;
   }

   public static final LoreStatsOption get(String option) {
      LoreStatsOption[] var4;
      int var3 = (var4 = values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         LoreStatsOption key = var4[var2];
         Iterator var6 = key.getAliases().iterator();

         while(var6.hasNext()) {
            String aliase = (String)var6.next();
            if (aliase.equalsIgnoreCase(option)) {
               return key;
            }
         }
      }

      return null;
   }
}
