package api.praya.myitems.builder.power;

import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PowerManager;
import com.praya.myitems.manager.game.PowerSpecialManager;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

public enum PowerSpecialEnum {
   BLINK(Arrays.asList("Blink")),
   FISSURE(Arrays.asList("Fissure")),
   ICE_SPIKES(Arrays.asList("Ice Spikes", "IceSpikes", "Ice_Spikes")),
   AMATERASU(Arrays.asList("Amaterasu", "DarkFlame", "Dark_Flame")),
   NERO_BEAM(Arrays.asList("Nero Beam", "NeroBeam", "Nero_Beam"));

   private final List<String> aliases;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$api$praya$myitems$builder$power$PowerSpecialEnum;

   private PowerSpecialEnum(List<String> aliases) {
      this.aliases = aliases;
   }

   public final List<String> getAliases() {
      return this.aliases;
   }

   public final String getText() {
      MainConfig mainConfig = MainConfig.getInstance();
      switch($SWITCH_TABLE$api$praya$myitems$builder$power$PowerSpecialEnum()[this.ordinal()]) {
      case 1:
         return mainConfig.getPowerSpecialIdentifierBlink();
      case 2:
         return mainConfig.getPowerSpecialIdentifierFissure();
      case 3:
         return mainConfig.getPowerSpecialIdentifierIceSpikes();
      case 4:
         return mainConfig.getPowerSpecialIdentifierAmaterasu();
      case 5:
         return mainConfig.getPowerSpecialIdentifierNeroBeam();
      default:
         return null;
      }
   }

   public final PowerSpecialProperties getProperties() {
      MyItems plugin = (MyItems)JavaPlugin.getProvidingPlugin(MyItems.class);
      GameManager gameManager = plugin.getGameManager();
      PowerManager powerManager = gameManager.getPowerManager();
      PowerSpecialManager powerSpecialManager = powerManager.getPowerSpecialManager();
      Iterator var6 = powerSpecialManager.getPowerSpecialIDs().iterator();

      while(var6.hasNext()) {
         PowerSpecialEnum key = (PowerSpecialEnum)var6.next();
         if (key.equals(this)) {
            return powerSpecialManager.getPowerSpecialProperties(key);
         }
      }

      return null;
   }

   public final int getDuration() {
      PowerSpecialProperties build = this.getProperties();
      return build != null ? build.getDurationEffect() : 1;
   }

   public final double getBaseAdditionalDamage() {
      PowerSpecialProperties build = this.getProperties();
      return build != null ? build.getBaseAdditionalDamage() : 0.0D;
   }

   public final double getBasePercentDamage() {
      PowerSpecialProperties build = this.getProperties();
      return build != null ? build.getBasePercentDamage() : 100.0D;
   }

   public static final PowerSpecialEnum get(String special) {
      String var1;
      switch((var1 = special.toUpperCase()).hashCode()) {
      case -1440265855:
         if (var1.equals("ICE_SPIKES")) {
            return ICE_SPIKES;
         }
         break;
      case -1435335974:
         if (var1.equals("NERO_BEAM")) {
            return NERO_BEAM;
         }
         break;
      case -438736745:
         if (var1.equals("AMATERASU")) {
            return AMATERASU;
         }
         break;
      case -236140453:
         if (var1.equals("ICESPIKE")) {
            return ICE_SPIKES;
         }
         break;
      case -130114555:
         if (var1.equals("FISSURE")) {
            return FISSURE;
         }
         break;
      case 63289148:
         if (var1.equals("BLINK")) {
            return BLINK;
         }
         break;
      case 1269580632:
         if (var1.equals("ICESPIKES")) {
            return ICE_SPIKES;
         }
         break;
      case 2031047651:
         if (var1.equals("NEROBEAM")) {
            return NERO_BEAM;
         }
      }

      return null;
   }

   public static final boolean isSpecialExists(String special) {
      return getSpecial(special) != null;
   }

   public static final PowerSpecialEnum getSpecial(String special) {
      PowerSpecialEnum[] var4;
      int var3 = (var4 = values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         PowerSpecialEnum key = var4[var2];
         Iterator var6 = key.getAliases().iterator();

         while(var6.hasNext()) {
            String aliases = (String)var6.next();
            if (aliases.equalsIgnoreCase(special)) {
               return key;
            }
         }
      }

      return null;
   }

   public static final PowerSpecialEnum getSpecialByLore(String lore) {
      PowerSpecialEnum[] var4;
      int var3 = (var4 = values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         PowerSpecialEnum special = var4[var2];
         if (special.getText().equalsIgnoreCase(lore)) {
            return special;
         }
      }

      return null;
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$api$praya$myitems$builder$power$PowerSpecialEnum() {
      int[] var10000 = $SWITCH_TABLE$api$praya$myitems$builder$power$PowerSpecialEnum;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[values().length];

         try {
            var0[AMATERASU.ordinal()] = 4;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[BLINK.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[FISSURE.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[ICE_SPIKES.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[NERO_BEAM.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$api$praya$myitems$builder$power$PowerSpecialEnum = var0;
         return var0;
      }
   }
}
