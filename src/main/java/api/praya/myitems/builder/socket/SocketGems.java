package api.praya.myitems.builder.socket;

import com.praya.myitems.MyItems;
import com.praya.myitems.manager.game.SocketManager;
import core.praya.agarthalib.enums.main.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SocketGems {
   private final ItemStack item;
   private final String keyLore;
   private final String gems;
   private final int grade;
   private final double successRate;
   private final SlotType typeItem;
   private final SocketGemsProperties socketProperties;

   public SocketGems(ItemStack item, String keyLore, String gems, int grade, double successRate, SlotType typeItem, SocketGemsProperties socketProperties) {
      this.item = item;
      this.keyLore = keyLore;
      this.gems = gems;
      this.grade = grade;
      this.successRate = successRate;
      this.typeItem = typeItem;
      this.socketProperties = socketProperties;
   }

   public final ItemStack getItem() {
      return this.item != null ? this.item.clone() : null;
   }

   public final String getKeyLore() {
      return this.keyLore;
   }

   public final String getGems() {
      return this.gems;
   }

   public final int getGrade() {
      return this.grade;
   }

   public final double getSuccessRate() {
      return this.successRate;
   }

   public final SlotType getTypeItem() {
      return this.typeItem;
   }

   public final SocketGemsProperties getSocketProperties() {
      return this.socketProperties;
   }

   public final SocketGemsTree getSocketTree() {
      MyItems plugin = (MyItems)JavaPlugin.getProvidingPlugin(MyItems.class);
      SocketManager socketManager = plugin.getGameManager().getSocketManager();
      return socketManager.getSocketTree(this.getGems());
   }
}
