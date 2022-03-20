package api.praya.myitems.builder.passive;

public enum PassiveTypeEnum {
   BUFF("Buff"),
   DEBUFF("Debuff");

   private final String name;

   private PassiveTypeEnum(String name) {
      this.name = name;
   }

   public final String getName() {
      return this.name;
   }
}
