package api.praya.myitems.manager.game;

import api.praya.myitems.builder.socket.SocketEnum;
import api.praya.myitems.builder.socket.SocketGems;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import api.praya.myitems.builder.socket.SocketGemsTree;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.SocketManager;
import java.util.Collection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class SocketManagerAPI extends HandlerManager {
   protected SocketManagerAPI(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getSocketIDs() {
      return this.getSocketManager().getSocketIDs();
   }

   public final Collection<SocketGemsTree> getSocketTreeBuilds() {
      return this.getSocketManager().getSocketTreeBuilds();
   }

   public final SocketGemsTree getSocketTree(String id) {
      return this.getSocketManager().getSocketTree(id);
   }

   public final boolean isExist(String name) {
      return this.getSocketManager().isExist(name);
   }

   public final String getTextSocketSlotEmpty() {
      return this.getSocketManager().getTextSocketSlotEmpty();
   }

   public final SocketGems getSocketBuild(String socket, int grade) {
      return this.getSocketManager().getSocketBuild(socket, grade);
   }

   public final ItemStack getItem(String socket, int grade) {
      return this.getSocketManager().getItem(socket, grade);
   }

   public final SocketGemsProperties getSocketProperties(String socket, int grade) {
      return this.getSocketManager().getSocketProperties(socket, grade);
   }

   public final double getSocketValue(String socket, int grade, SocketEnum typeValue) {
      return this.getSocketManager().getSocketValue(socket, grade, typeValue);
   }

   public final double getSocketValue(SocketGemsProperties socketProperties, SocketEnum typeValue) {
      return this.getSocketManager().getSocketValue(socketProperties, typeValue);
   }

   public final boolean isSocketItem(ItemStack item) {
      return this.getSocketManager().isSocketItem(item);
   }

   public final String getSocketName(ItemStack item) {
      return this.getSocketManager().getSocketName(item);
   }

   public final SocketGems getSocketBuild(ItemStack item) {
      return this.getSocketManager().getSocketBuild(item);
   }

   public final boolean containSocketEmpty(ItemStack item) {
      return this.getSocketManager().containsSocketEmpty(item);
   }

   public final int getLineSocketEmpty(ItemStack item) {
      return this.getSocketManager().getFirstLineSocketEmpty(item);
   }

   public final SocketGemsProperties getSocketProperties(LivingEntity livingEntity) {
      return this.getSocketManager().getSocketProperties(livingEntity);
   }

   public final SocketGemsProperties getSocketProperties(LivingEntity livingEntity, boolean checkDurability) {
      return this.getSocketManager().getSocketProperties(livingEntity, checkDurability);
   }

   public final SocketGemsProperties getSocketProperties(ItemStack item) {
      return this.getSocketManager().getSocketProperties(item);
   }

   public final SocketGemsProperties getSocketProperties(ItemStack item, boolean checkDurability) {
      return this.getSocketManager().getSocketProperties(item, checkDurability);
   }

   public final String getTextSocketGemsLore(String socket, int grade) {
      return this.getSocketManager().getTextSocketGemsLore(socket, grade);
   }

   private final SocketManager getSocketManager() {
      GameManager gameManager = this.plugin.getGameManager();
      SocketManager socketManager = gameManager.getSocketManager();
      return socketManager;
   }
}
