package api.praya.myitems.builder.power;

public enum PowerEnum {
   COMMAND,
   SHOOT,
   SPECIAL;

   public static final PowerEnum get(String power) {
      String var1;
      switch((var1 = power.toUpperCase()).hashCode()) {
      case -2056513613:
         if (var1.equals("LAUNCH")) {
            return SHOOT;
         }
         break;
      case -1290482535:
         if (var1.equals("SPECIAL")) {
            return SPECIAL;
         }
         break;
      case 66842:
         if (var1.equals("CMD")) {
            return COMMAND;
         }
         break;
      case 78875647:
         if (var1.equals("SHOOT")) {
            return SHOOT;
         }
         break;
      case 1668377387:
         if (var1.equals("COMMAND")) {
            return COMMAND;
         }
      }

      return null;
   }
}
