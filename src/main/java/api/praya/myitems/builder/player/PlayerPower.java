package api.praya.myitems.builder.player;

import com.praya.agarthalib.utility.PlayerUtil;
import java.util.UUID;
import org.bukkit.OfflinePlayer;

public class PlayerPower {
   private final UUID uuid;
   private final PlayerPowerCooldown powerCooldown;

   public PlayerPower(OfflinePlayer player) {
      this.uuid = player.getUniqueId();
      this.powerCooldown = new PlayerPowerCooldown();
   }

   public final UUID getUUID() {
      return this.uuid;
   }

   public final PlayerPowerCooldown getPowerCooldown() {
      return this.powerCooldown;
   }

   public final OfflinePlayer getOfflinePlayer() {
      return PlayerUtil.getPlayer(this.getUUID());
   }
}
