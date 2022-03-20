package api.praya.myitems.builder.player;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import java.util.HashMap;

public class PlayerPassiveEffectCooldown {
   private final HashMap<PassiveEffectEnum, Long> mapPassiveEffectExpired = new HashMap();

   public final void setPassiveEffectCooldown(PassiveEffectEnum effect, long cooldown) {
      long expired = System.currentTimeMillis() + cooldown;
      this.mapPassiveEffectExpired.put(effect, expired);
   }

   public final boolean isPassiveEffectCooldown(PassiveEffectEnum effect) {
      if (this.mapPassiveEffectExpired.containsKey(effect)) {
         long expired = (Long)this.mapPassiveEffectExpired.get(effect);
         long now = System.currentTimeMillis();
         return now < expired;
      } else {
         return false;
      }
   }

   public final void removePassiveEffectCooldown(PassiveEffectEnum effect) {
      this.mapPassiveEffectExpired.remove(effect);
   }

   public final long getPassiveEffectExpired(PassiveEffectEnum effect) {
      return this.mapPassiveEffectExpired.containsKey(effect) ? (Long)this.mapPassiveEffectExpired.get(effect) : System.currentTimeMillis();
   }

   public final long getPassiveEffectTimeLeft(PassiveEffectEnum effect) {
      return this.isPassiveEffectCooldown(effect) ? (Long)this.mapPassiveEffectExpired.get(effect) - System.currentTimeMillis() : 0L;
   }
}
