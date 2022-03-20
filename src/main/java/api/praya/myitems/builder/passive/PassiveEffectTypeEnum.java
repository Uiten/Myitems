package api.praya.myitems.builder.passive;

public enum PassiveEffectTypeEnum {
   FREEZE("Freeze"),
   CURSE("Curse"),
   ROOTS("Roots"),
   DARK_FLAME("Dark_Flame");

   private final String name;

   private PassiveEffectTypeEnum(String name) {
      this.name = name;
   }

   public final String getName() {
      return this.name;
   }
}
