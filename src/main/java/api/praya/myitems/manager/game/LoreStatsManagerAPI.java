package api.praya.myitems.manager.game;

import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.lorestats.LoreStatsWeapon;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class LoreStatsManagerAPI extends HandlerManager {
   protected LoreStatsManagerAPI(MyItems plugin) {
      super(plugin);
   }

   public final boolean hasLoreStats(ItemStack item, LoreStatsEnum loreStats) {
      return this.getLoreStatsManager().hasLoreStats(item, loreStats);
   }

   public final String getTextLoreStats(LoreStatsEnum loreStats, double value) {
      return this.getLoreStatsManager().getTextLoreStats(loreStats, value);
   }

   public final String getTextLoreStats(LoreStatsEnum loreStats, double value1, double value2) {
      return this.getLoreStatsManager().getTextLoreStats(loreStats, value1, value2);
   }

   public final double getLoreValue(ItemStack item, LoreStatsEnum loreStats, LoreStatsOption option) {
      return this.getLoreStatsManager().getLoreValue(item, loreStats, option);
   }

   public final void itemRepair(ItemStack item, int repair) {
      this.getLoreStatsManager().itemRepair(item, repair);
   }

   public final double getUpExp(int level) {
      return this.getLoreStatsManager().getUpExp(level);
   }

   public final boolean checkDurability(ItemStack item) {
      return this.getLoreStatsManager().checkDurability(item);
   }

   public final boolean checkLevel(ItemStack item, int requirement) {
      return this.getLoreStatsManager().checkLevel(item, requirement);
   }

   public final void damageDurability(ItemStack item) {
      this.getLoreStatsManager().damageDurability(item);
   }

   public final LoreStatsWeapon getLoreStatsWeapon(LivingEntity attacker) {
      return this.getLoreStatsManager().getLoreStatsWeapon(attacker);
   }

   public final LoreStatsWeapon getLoreStatsWeapon(LivingEntity attacker, boolean reverse) {
      return this.getLoreStatsManager().getLoreStatsWeapon(attacker, reverse);
   }

   public final LoreStatsWeapon getLoreStatsWeapon(LivingEntity attacker, boolean checkDurability, boolean reverse) {
      return this.getLoreStatsManager().getLoreStatsWeapon(attacker, checkDurability, reverse);
   }

   public final LoreStatsWeapon getLoreStatsWeapon(ItemStack item) {
      return this.getLoreStatsManager().getLoreStatsWeapon(item);
   }

   public final LoreStatsWeapon getLoreStatsWeapon(ItemStack item, boolean reverse) {
      return this.getLoreStatsManager().getLoreStatsWeapon(item, reverse);
   }

   public final LoreStatsWeapon getLoreStatsWeapon(ItemStack item, boolean checkDurability, boolean reverse) {
      return this.getLoreStatsManager().getLoreStatsWeapon(item, checkDurability, reverse);
   }

   public final LoreStatsWeapon getLoreStatsWeapon(ItemStack item, Slot slot, boolean checkDurability, boolean reverse) {
      return this.getLoreStatsManager().getLoreStatsWeapon(item, slot, checkDurability, reverse);
   }

   public final LoreStatsArmor getLoreStatsArmor(LivingEntity victims) {
      return this.getLoreStatsManager().getLoreStatsArmor(victims);
   }

   public final LoreStatsArmor getLoreStatsArmor(LivingEntity victims, boolean checkDurability) {
      return this.getLoreStatsManager().getLoreStatsArmor(victims, checkDurability);
   }

   public final LoreStatsArmor getLoreStatsArmor(ItemStack item) {
      return this.getLoreStatsManager().getLoreStatsArmor(item);
   }

   public final LoreStatsArmor getLoreStatsArmor(ItemStack item, boolean checkDurability) {
      return this.getLoreStatsManager().getLoreStatsArmor(item, checkDurability);
   }

   public final LoreStatsArmor getLoreStatsArmor(ItemStack item, Slot slot, boolean checkDurability) {
      return this.getLoreStatsManager().getLoreStatsArmor(item, slot, checkDurability);
   }

   private final LoreStatsManager getLoreStatsManager() {
      GameManager gameManager = this.plugin.getGameManager();
      LoreStatsManager loreStatsManager = gameManager.getStatsManager();
      return loreStatsManager;
   }
}
