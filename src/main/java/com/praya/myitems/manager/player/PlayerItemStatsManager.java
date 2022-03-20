package com.praya.myitems.manager.player;

import api.praya.myitems.builder.element.ElementBoostStats;
import api.praya.myitems.builder.item.ItemStatsArmor;
import api.praya.myitems.builder.item.ItemStatsWeapon;
import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.lorestats.LoreStatsWeapon;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.SocketManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.enums.main.SlotType;
import java.util.HashMap;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerItemStatsManager extends HandlerManager {
   protected PlayerItemStatsManager(MyItems plugin) {
      super(plugin);
   }

   public final ItemStatsWeapon getItemStatsWeapon(Player player) {
      GameManager gameManager = this.plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      SocketManager socketManager = gameManager.getSocketManager();
      ElementManager elementManager = gameManager.getElementManager();
      MainConfig mainConfig = MainConfig.getInstance();
      ItemStack itemEquipmentMainHand = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
      ItemStack itemEquipmentOffHand = Bridge.getBridgeEquipment().getEquipment(player, Slot.OFFHAND);
      boolean enableItemUniversal = mainConfig.isStatsEnableItemUniversal();
      boolean enableOffHand = mainConfig.isAbilityWeaponEnableOffHand();
      boolean hasStatsCriticalChance = statsManager.hasLoreStats(itemEquipmentMainHand, LoreStatsEnum.CRITICAL_CHANCE) || enableOffHand && statsManager.hasLoreStats(itemEquipmentOffHand, LoreStatsEnum.CRITICAL_CHANCE);
      boolean hasStatsCriticalDamage = statsManager.hasLoreStats(itemEquipmentMainHand, LoreStatsEnum.CRITICAL_DAMAGE) || enableOffHand && statsManager.hasLoreStats(itemEquipmentOffHand, LoreStatsEnum.CRITICAL_DAMAGE);
      HashMap<String, Double> mapElementWeapon = elementManager.getMapElement(player, SlotType.WEAPON, false);
      LoreStatsWeapon statsWeapon = statsManager.getLoreStatsWeapon((LivingEntity)player, false, false);
      SocketGemsProperties socket = socketManager.getSocketProperties((LivingEntity)player, false);
      ElementBoostStats elementWeapon = elementManager.getElementBoostStats(mapElementWeapon);
      double scaleOffHandValue = mainConfig.getStatsScaleOffHandValue();
      double statsDamage = 0.0D;
      double statsDamageDiff = 0.0D;
      Slot[] var26;
      int var25 = (var26 = Slot.values()).length;

      for(int var24 = 0; var24 < var25; ++var24) {
         Slot slot = var26[var24];
         if (slot.getType().equals(SlotType.WEAPON) || enableItemUniversal) {
            ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, slot);
            if (item != null) {
               double scaleValue = slot.equals(Slot.OFFHAND) ? (enableOffHand ? scaleOffHandValue : 0.0D) : 1.0D;
               double statsItemDamageMin = statsManager.getLoreValue(item, LoreStatsEnum.DAMAGE, LoreStatsOption.MIN) * scaleValue;
               double statsItemDamageMax = statsManager.getLoreValue(item, LoreStatsEnum.DAMAGE, LoreStatsOption.MAX) * scaleValue;
               double statsItemDamageDiff = statsItemDamageMax - statsItemDamageMin;
               statsDamage += statsItemDamageMin;
               statsDamageDiff += statsItemDamageDiff;
            }
         }
      }

      double socketDamage = socket.getAdditionalDamage() + statsDamage * socket.getPercentDamage() / 100.0D;
      double elementDamage = elementWeapon.getBaseAdditionalDamage() + statsDamage * elementWeapon.getBasePercentDamage() / 100.0D;
      double attributeDamage = statsDamage + socketDamage + elementDamage;
      double baseDamage = 0.0D;
      double basePenetration = 0.0D;
      double basePvPDamage = 100.0D;
      double basePvEDamage = 100.0D;
      double baseCriticalChance = 5.0D;
      double baseCriticalDamage = 1.2D;
      double baseAttackAoERadius = 0.0D;
      double baseAttackAoEDamage = 0.0D;
      double baseHitRate = 100.0D;
      double totalDamageMin = 0.0D + attributeDamage;
      double totalDamageMax = totalDamageMin + statsDamageDiff;
      double totalPenetration = 0.0D + statsWeapon.getPenetration() + socket.getPenetration();
      double totalPvPDamage = 100.0D + statsWeapon.getPvPDamage() + socket.getPvPDamage();
      double totalPvEDamage = 100.0D + statsWeapon.getPvEDamage() + socket.getPvEDamage();
      double totalCriticalChance = !hasStatsCriticalChance ? 5.0D : statsWeapon.getCriticalChance() + socket.getCriticalChance();
      double totalCriticalDamage = !hasStatsCriticalDamage ? 1.2D : 1.0D + statsWeapon.getCriticalDamage() + socket.getCriticalDamage() / 100.0D;
      double totalAttackAoERadius = 0.0D + statsWeapon.getAttackAoERadius() + socket.getAttackAoERadius();
      double totalAttackAoEDamage = 0.0D + statsWeapon.getAttackAoEDamage() + socket.getAttackAoEDamage();
      double totalHitRate = 100.0D + statsWeapon.getHitRate() + socket.getHitRate();
      return new ItemStatsWeapon(totalDamageMin, totalDamageMax, totalPenetration, totalPvPDamage, totalPvEDamage, totalAttackAoERadius, totalAttackAoEDamage, totalCriticalChance, totalCriticalDamage, totalHitRate, socketDamage, elementDamage);
   }

   public final ItemStatsArmor getItemStatsArmor(Player player) {
      GameManager gameManager = this.plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      SocketManager socketManager = gameManager.getSocketManager();
      LoreStatsArmor statsArmor = statsManager.getLoreStatsArmor((LivingEntity)player, false);
      SocketGemsProperties socket = socketManager.getSocketProperties((LivingEntity)player, false);
      double statsDefense = statsArmor.getDefense();
      double socketDefense = socket.getAdditionalDefense() + statsDefense * socket.getPercentDefense() / 100.0D;
      double attributeDefense = statsDefense + socketDefense;
      double baseDefense = 0.0D;
      double basePvPDefense = 100.0D;
      double basePvEDefense = 100.0D;
      double baseHealth = 0.0D;
      double baseHealthRegen = 0.0D;
      double baseStaminaMax = 0.0D;
      double baseStaminaRegen = 0.0D;
      double baseBlockAmount = 25.0D;
      double baseBlockRate = 0.0D;
      double baseDodgeRate = 0.0D;
      double totalDefense = 0.0D + attributeDefense;
      double totalPvPDefense = 100.0D + statsArmor.getPvPDefense() + socket.getPvPDefense();
      double totalPvEDefense = 100.0D + statsArmor.getPvEDefense() + socket.getPvEDefense();
      double totalHealth = 0.0D + statsArmor.getHealth() + socket.getHealth();
      double totalHealthRegen = 0.0D + statsArmor.getHealthRegen() + socket.getHealthRegen();
      double totalStaminaMax = 0.0D + statsArmor.getStaminaMax() + socket.getStaminaMax();
      double totalStaminaRegen = 0.0D + statsArmor.getStaminaRegen() + socket.getStaminaRegen();
      double totalBlockAmount = 25.0D + statsArmor.getBlockAmount() + socket.getBlockAmount();
      double totalBlockRate = 0.0D + statsArmor.getBlockRate() + socket.getBlockRate();
      double totalDodgeRate = 0.0D + statsArmor.getDodgeRate() + socket.getDodgeRate();
      return new ItemStatsArmor(totalDefense, totalPvPDefense, totalPvEDefense, totalHealth, totalHealthRegen, totalStaminaMax, totalStaminaRegen, totalBlockAmount, totalBlockRate, totalDodgeRate, socketDefense);
   }
}
