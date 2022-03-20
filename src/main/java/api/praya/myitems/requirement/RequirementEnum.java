package api.praya.myitems.requirement;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public enum RequirementEnum {
   REQUIREMENT_SOUL_UNBOUND("Unbound", Arrays.asList("Unbound")),
   REQUIREMENT_SOUL_BOUND("Bound", Arrays.asList("Bound", "Soulbound")),
   REQUIREMENT_PERMISSION("Permission", Arrays.asList("Permission")),
   REQUIREMENT_LEVEL("Level", Arrays.asList("Level")),
   REQUIREMENT_CLASS("Class", Arrays.asList("Class", "Hero"));

   private final String name;
   private final List<String> aliases;

   private RequirementEnum(String name, List<String> aliases) {
      this.name = name;
      this.aliases = aliases;
   }

   public final String getName() {
      return this.name;
   }

   public final List<String> getAliases() {
      return this.aliases;
   }

   public static final RequirementEnum getRequirement(String requirement) {
      RequirementEnum[] var4;
      int var3 = (var4 = values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         RequirementEnum key = var4[var2];
         Iterator var6 = key.getAliases().iterator();

         while(var6.hasNext()) {
            String aliases = (String)var6.next();
            if (aliases.equalsIgnoreCase(requirement)) {
               return key;
            }
         }
      }

      return null;
   }
}
