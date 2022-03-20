package api.praya.myitems.builder.power;

import com.praya.myitems.config.plugin.MainConfig;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public enum PowerClickEnum {
   LEFT(Arrays.asList("Left", "LeftClick", "Left Click", "Left_Click")),
   SHIFT_LEFT(Arrays.asList("Shift Left", "ShiftLeft", "Shift_Left", "Shift Left Click")),
   RIGHT(Arrays.asList("Right", "RightClick,", "Right Click", "Right_Click")),
   SHIFT_RIGHT(Arrays.asList("Shift Right", "ShiftRight", "Shift_Right", "Shift Right Click"));

   private final List<String> aliases;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$api$praya$myitems$builder$power$PowerClickEnum;

   private PowerClickEnum(List<String> aliases) {
      this.aliases = aliases;
   }

   public final List<String> getAliases() {
      return this.aliases;
   }

   public final String getText() {
      MainConfig mainConfig = MainConfig.getInstance();
      switch($SWITCH_TABLE$api$praya$myitems$builder$power$PowerClickEnum()[this.ordinal()]) {
      case 1:
         return mainConfig.getPowerClickLeft();
      case 2:
         return mainConfig.getPowerClickShiftLeft();
      case 3:
         return mainConfig.getPowerClickRight();
      case 4:
         return mainConfig.getPowerClickShiftRight();
      default:
         return null;
      }
   }

   public static final PowerClickEnum get(String click) {
      PowerClickEnum[] var4;
      int var3 = (var4 = values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         PowerClickEnum key = var4[var2];
         Iterator var6 = key.getAliases().iterator();

         while(var6.hasNext()) {
            String aliases = (String)var6.next();
            if (aliases.equalsIgnoreCase(click)) {
               return key;
            }
         }
      }

      return null;
   }

   public static final String getText(PowerClickEnum click) {
      return click.getText();
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$api$praya$myitems$builder$power$PowerClickEnum() {
      int[] var10000 = $SWITCH_TABLE$api$praya$myitems$builder$power$PowerClickEnum;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[values().length];

         try {
            var0[LEFT.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[RIGHT.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[SHIFT_LEFT.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[SHIFT_RIGHT.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$api$praya$myitems$builder$power$PowerClickEnum = var0;
         return var0;
      }
   }
}
