package com.praya.myitems.manager.game;

import api.praya.myitems.builder.socket.SocketEnum;
import api.praya.myitems.builder.socket.SocketGems;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import api.praya.myitems.builder.socket.SocketGemsTree;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.game.SocketConfig;
import com.praya.myitems.config.plugin.MainConfig;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class SocketManager extends HandlerManager {
   private final SocketConfig socketConfig;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$api$praya$myitems$builder$socket$SocketEnum;

   protected SocketManager(MyItems plugin) {
      super(plugin);
      this.socketConfig = new SocketConfig(plugin);
   }

   public final SocketConfig getSocketConfig() {
      return this.socketConfig;
   }

   public final Collection<String> getSocketIDs() {
      return this.getSocketConfig().getSocketIDs();
   }

   public final Collection<SocketGemsTree> getSocketTreeBuilds() {
      return this.getSocketConfig().getSocketTreeBuilds();
   }

   public final SocketGemsTree getSocketTree(String id) {
      return this.getSocketConfig().getSocketTree(id);
   }

   public final boolean isExist(String name) {
      return this.getSocketTree(name) != null;
   }

   public final String getTextSocketSlotEmpty() {
      MainConfig mainConfig = MainConfig.getInstance();
      HashMap<String, String> map = new HashMap();
      String format = mainConfig.getSocketFormatSlot();
      map.put("slot", this.getKeySocketEmpty());
      format = TextUtil.placeholder(map, format, "<", ">");
      return format;
   }

   public final String getTextSocketSlotLocked() {
      MainConfig mainConfig = MainConfig.getInstance();
      HashMap<String, String> map = new HashMap();
      String format = mainConfig.getSocketFormatSlot();
      map.put("slot", this.getKeySocketLocked());
      format = TextUtil.placeholder(map, format, "<", ">");
      return format;
   }

   public final String getRawName(String name) {
      Iterator var3 = this.getSocketIDs().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if (key.equalsIgnoreCase(name)) {
            return key;
         }
      }

      return null;
   }

   public final SocketGems getSocketBuild(String socket, int grade) {
      Iterator var4 = this.getSocketIDs().iterator();

      while(var4.hasNext()) {
         String key = (String)var4.next();
         if (key.equalsIgnoreCase(socket)) {
            return this.getSocketTree(key).getSocketBuild(grade);
         }
      }

      return null;
   }

   public final ItemStack getItem(String socket, int grade) {
      SocketGems build = this.getSocketBuild(socket, grade);
      return build != null ? build.getItem() : null;
   }

   public final String getSocketKeyLore(String socket, int grade) {
      SocketGems build = this.getSocketBuild(socket, grade);
      return build != null ? build.getKeyLore() : null;
   }

   public final String getSocketKeyLore(SocketGems socketBuild) {
      return socketBuild != null ? socketBuild.getKeyLore() : null;
   }

   public final SocketGemsProperties getSocketProperties(String socket, int grade) {
      SocketGems build = this.getSocketBuild(socket, grade);
      return build != null ? build.getSocketProperties() : null;
   }

   public final double getSocketValue(String socket, int grade, SocketEnum typeValue) {
      return this.getSocketValue(this.getSocketProperties(socket, grade), typeValue);
   }

   public final double getSocketValue(SocketGemsProperties socketProperties, SocketEnum typeValue) {
      switch($SWITCH_TABLE$api$praya$myitems$builder$socket$SocketEnum()[typeValue.ordinal()]) {
      case 1:
         return socketProperties != null ? socketProperties.getAdditionalDamage() : 0.0D;
      case 2:
         return socketProperties != null ? socketProperties.getPercentDamage() : 0.0D;
      case 3:
         return socketProperties != null ? socketProperties.getPenetration() : 0.0D;
      case 4:
         return socketProperties != null ? socketProperties.getPvPDamage() : 0.0D;
      case 5:
         return socketProperties != null ? socketProperties.getPvEDamage() : 0.0D;
      case 6:
         return socketProperties != null ? socketProperties.getAdditionalDefense() : 0.0D;
      case 7:
         return socketProperties != null ? socketProperties.getPercentDefense() : 0.0D;
      case 8:
         return socketProperties != null ? socketProperties.getHealth() : 0.0D;
      case 9:
         return socketProperties != null ? socketProperties.getPvPDefense() : 0.0D;
      case 10:
         return socketProperties != null ? socketProperties.getPvEDefense() : 0.0D;
      case 11:
         return socketProperties != null ? socketProperties.getCriticalChance() : 0.0D;
      case 12:
         return socketProperties != null ? socketProperties.getCriticalDamage() : 0.0D;
      case 13:
         return socketProperties != null ? socketProperties.getBlockAmount() : 0.0D;
      case 14:
         return socketProperties != null ? socketProperties.getBlockRate() : 0.0D;
      case 15:
         return socketProperties != null ? socketProperties.getHitRate() : 0.0D;
      case 16:
         return socketProperties != null ? socketProperties.getDodgeRate() : 0.0D;
      default:
         return 0.0D;
      }
   }

   public final boolean isSocketItem(ItemStack item) {
      return this.getSocketName(item) != null;
   }

   public final String getSocketName(ItemStack item) {
      SocketGems socket = this.getSocketBuild(item);
      return socket != null ? socket.getGems() : null;
   }

   public final SocketGems getSocketBuild(ItemStack item) {
      SocketManager socketManager = this.plugin.getGameManager().getSocketManager();
      Iterator var4 = socketManager.getSocketIDs().iterator();

      while(var4.hasNext()) {
         String key = (String)var4.next();
         Iterator var6 = socketManager.getSocketTree(key).getMapSocket().values().iterator();

         while(var6.hasNext()) {
            SocketGems socket = (SocketGems)var6.next();
            if (EquipmentUtil.isSimilar(item, socket.getItem())) {
               return socket;
            }
         }
      }

      return null;
   }

   public final List<Integer> getLineLoresSocketEmpty(ItemStack item) {
      List<Integer> list = new ArrayList();
      if (EquipmentUtil.loreCheck(item)) {
         List<String> lores = EquipmentUtil.getLores(item);

         for(int index = 0; index < lores.size(); ++index) {
            String lore = (String)lores.get(index);
            if (this.isSocketEmptyLore(lore)) {
               list.add(index + 1);
            }
         }
      }

      return list;
   }

   public final int getFirstLineSocketEmpty(ItemStack item) {
      List<Integer> list = this.getLineLoresSocketEmpty(item);
      return list.size() > 0 ? (Integer)list.get(0) : -1;
   }

   public final boolean containsSocketEmpty(ItemStack item) {
      return this.getLineLoresSocketEmpty(item).size() > 0;
   }

   public final List<Integer> getLineLoresSocketLocked(ItemStack item) {
      List<Integer> list = new ArrayList();
      if (EquipmentUtil.loreCheck(item)) {
         List<String> lores = EquipmentUtil.getLores(item);

         for(int index = 0; index < lores.size(); ++index) {
            String lore = (String)lores.get(index);
            if (this.isSocketLockedLore(lore)) {
               list.add(index + 1);
            }
         }
      }

      return list;
   }

   public final int getFirstLineSocketLocked(ItemStack item) {
      List<Integer> list = this.getLineLoresSocketLocked(item);
      return list.size() > 0 ? (Integer)list.get(0) : -1;
   }

   public final boolean containsSocketLocked(ItemStack item) {
      return this.getLineLoresSocketLocked(item).size() > 0;
   }

   public final String getSocket(String lore) {
      SocketManager socketManager = this.plugin.getGameManager().getSocketManager();
      String[] part = lore.split(MainConfig.KEY_SOCKET_LORE_GEMS);
      if (part.length > 1) {
         String keyLore = part[1];
         Iterator var6 = socketManager.getSocketIDs().iterator();

         while(var6.hasNext()) {
            String key = (String)var6.next();
            Iterator var8 = socketManager.getSocketTree(key).getMapSocket().values().iterator();

            while(var8.hasNext()) {
               SocketGems socket = (SocketGems)var8.next();
               if (keyLore.equalsIgnoreCase(socket.getKeyLore())) {
                  return key;
               }
            }
         }
      }

      return null;
   }

   public final SocketGems getSocketBuild(String lore) {
      SocketManager socketManager = this.plugin.getGameManager().getSocketManager();
      String[] part = lore.split(MainConfig.KEY_SOCKET_LORE_GEMS);
      if (part.length > 1) {
         String keyLore = part[1];
         Iterator var6 = socketManager.getSocketIDs().iterator();

         while(var6.hasNext()) {
            String key = (String)var6.next();
            Iterator var8 = socketManager.getSocketTree(key).getMapSocket().values().iterator();

            while(var8.hasNext()) {
               SocketGems socket = (SocketGems)var8.next();
               if (keyLore.equalsIgnoreCase(socket.getKeyLore())) {
                  return socket;
               }
            }
         }
      }

      return null;
   }

   public final List<String> getLoresSocket(ItemStack item) {
      List<String> list = new ArrayList();
      if (EquipmentUtil.loreCheck(item)) {
         List<String> lores = EquipmentUtil.getLores(item);
         Iterator var5 = lores.iterator();

         while(var5.hasNext()) {
            String lore = (String)var5.next();
            if (this.isSocketGemsLore(lore)) {
               list.add(lore);
            }
         }
      }

      return list;
   }

   public final List<Integer> getLineLoresSocket(ItemStack item) {
      List<Integer> list = new ArrayList();
      if (EquipmentUtil.loreCheck(item)) {
         List<String> lores = EquipmentUtil.getLores(item);

         for(int index = 0; index < lores.size(); ++index) {
            String lore = (String)lores.get(index);
            if (this.isSocketGemsLore(lore)) {
               list.add(index + 1);
            }
         }
      }

      return list;
   }

   public final boolean containsSocketGems(ItemStack item) {
      return this.getLineLoresSocket(item).size() > 0;
   }

   public final boolean isSocketGemsLore(String lore) {
      return lore.contains(MainConfig.KEY_SOCKET_LORE_GEMS);
   }

   public final boolean isSocketEmptyLore(String lore) {
      String key = this.getKeySocketEmpty(true);
      return lore.contains(key);
   }

   public final boolean isSocketLockedLore(String lore) {
      String key = this.getKeySocketLocked(true);
      return lore.contains(key);
   }

   public final SocketGemsProperties getSocketProperties(LivingEntity livingEntity) {
      return this.getSocketProperties(livingEntity, true);
   }

   public final SocketGemsProperties getSocketProperties(LivingEntity livingEntity, boolean checkDurability) {
      double additionalDamage = 0.0D;
      double percentDamage = 0.0D;
      double penetration = 0.0D;
      double pvpDamage = 0.0D;
      double pveDamage = 0.0D;
      double additionalDefense = 0.0D;
      double percentDefense = 0.0D;
      double health = 0.0D;
      double healthRegen = 0.0D;
      double staminaMax = 0.0D;
      double staminaRegen = 0.0D;
      double attackAoERadius = 0.0D;
      double attackAoEDamage = 0.0D;
      double pvpDefense = 0.0D;
      double pveDefense = 0.0D;
      double criticalChance = 0.0D;
      double criticalDamage = 0.0D;
      double blockAmount = 0.0D;
      double blockRate = 0.0D;
      double hitRate = 0.0D;
      double dodgeRate = 0.0D;
      Slot[] var48;
      int var47 = (var48 = Slot.values()).length;

      for(int var46 = 0; var46 < var47; ++var46) {
         Slot slot = var48[var46];
         ItemStack item = Bridge.getBridgeEquipment().getEquipment(livingEntity, slot);
         if (EquipmentUtil.isSolid(item)) {
            SocketGemsProperties build = this.getSocketProperties(item, checkDurability);
            additionalDamage += build.getAdditionalDamage();
            percentDamage += build.getPercentDamage();
            penetration += build.getPenetration();
            pvpDamage += build.getPvPDamage();
            pveDamage += build.getPvEDamage();
            additionalDefense += build.getAdditionalDefense();
            percentDefense += build.getPercentDefense();
            health += build.getHealth();
            healthRegen += build.getHealthRegen();
            staminaMax += build.getStaminaMax();
            staminaRegen += build.getStaminaRegen();
            attackAoERadius += build.getAttackAoERadius();
            attackAoEDamage += build.getAttackAoEDamage();
            pvpDefense += build.getPvPDefense();
            pveDefense += build.getPvEDefense();
            criticalChance += build.getCriticalChance();
            criticalDamage += build.getCriticalDamage();
            blockAmount += build.getBlockAmount();
            blockRate += build.getBlockRate();
            hitRate += build.getHitRate();
            dodgeRate += build.getDodgeRate();
         }
      }

      return new SocketGemsProperties(additionalDamage, percentDamage, penetration, pvpDamage, pveDamage, additionalDefense, percentDefense, health, healthRegen, staminaMax, staminaRegen, attackAoERadius, attackAoEDamage, pvpDefense, pveDefense, criticalChance, criticalDamage, blockAmount, blockRate, hitRate, dodgeRate);
   }

   public final SocketGemsProperties getSocketProperties(ItemStack item) {
      return this.getSocketProperties(item, true);
   }

   public final SocketGemsProperties getSocketProperties(ItemStack item, boolean checkDurability) {
      GameManager gameManager = this.plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      double additionalDamage = 0.0D;
      double percentDamage = 0.0D;
      double penetration = 0.0D;
      double pvpDamage = 0.0D;
      double pveDamage = 0.0D;
      double additionalDefense = 0.0D;
      double percentDefense = 0.0D;
      double health = 0.0D;
      double healthRegen = 0.0D;
      double staminaMax = 0.0D;
      double staminaRegen = 0.0D;
      double attackAoERadius = 0.0D;
      double attackAoEDamage = 0.0D;
      double pvpDefense = 0.0D;
      double pveDefense = 0.0D;
      double criticalChance = 0.0D;
      double criticalDamage = 0.0D;
      double blockAmount = 0.0D;
      double blockRate = 0.0D;
      double hitRate = 0.0D;
      double dodgeRate = 0.0D;
      if (EquipmentUtil.loreCheck(item) && (!checkDurability || statsManager.checkDurability(item))) {
         Iterator var48 = EquipmentUtil.getLores(item).iterator();

         while(var48.hasNext()) {
            String lore = (String)var48.next();
            SocketGems socket = this.getSocketBuild(lore);
            if (socket != null) {
               SocketGemsProperties build = socket.getSocketProperties();
               additionalDamage += build.getAdditionalDamage();
               percentDamage += build.getPercentDamage();
               penetration += build.getPenetration();
               pvpDamage += build.getPvPDamage();
               pveDamage += build.getPvEDamage();
               additionalDefense += build.getAdditionalDefense();
               percentDefense += build.getPercentDefense();
               health += build.getHealth();
               healthRegen += build.getHealthRegen();
               staminaMax += build.getStaminaMax();
               staminaRegen += build.getStaminaRegen();
               attackAoERadius += build.getAttackAoERadius();
               attackAoEDamage += build.getAttackAoEDamage();
               pvpDefense += build.getPvPDefense();
               pveDefense += build.getPvEDefense();
               criticalChance += build.getCriticalChance();
               criticalDamage += build.getCriticalDamage();
               blockAmount += build.getBlockAmount();
               blockRate += build.getBlockRate();
               hitRate += build.getHitRate();
               dodgeRate += build.getDodgeRate();
            }
         }
      }

      return new SocketGemsProperties(additionalDamage, percentDamage, penetration, pvpDamage, pveDamage, additionalDefense, percentDefense, health, healthRegen, staminaMax, staminaRegen, attackAoERadius, attackAoEDamage, pvpDefense, pveDefense, criticalChance, criticalDamage, blockAmount, blockRate, hitRate, dodgeRate);
   }

   private String getKeySocketEmpty() {
      return this.getKeySocketEmpty(false);
   }

   private String getKeySocketEmpty(boolean justCheck) {
      MainConfig mainConfig = MainConfig.getInstance();
      String key = MainConfig.KEY_SOCKET_SLOT;
      String color = mainConfig.getSocketColorSlot();
      String keylore = mainConfig.getSocketFormatSlotEmpty();
      return justCheck ? key + color + keylore : key + color + keylore + key + color;
   }

   private String getKeySocketLocked() {
      return this.getKeySocketLocked(false);
   }

   private String getKeySocketLocked(boolean justCheck) {
      MainConfig mainConfig = MainConfig.getInstance();
      String key = MainConfig.KEY_SOCKET_SLOT;
      String color = mainConfig.getSocketColorSlot();
      String keylore = mainConfig.getSocketFormatSlotLocked();
      return justCheck ? key + color + keylore : key + color + keylore + key + color;
   }

   public final String getTextSocketGemsLore(String socket, int grade) {
      return this.getTextSocketGemsLore(socket, grade, false);
   }

   public final String getTextSocketGemsLore(String socket, int grade, boolean justCheck) {
      String key = MainConfig.KEY_SOCKET_LORE_GEMS;
      String keylore = this.getSocketKeyLore(socket, grade);
      return justCheck ? key + keylore : key + keylore + key;
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$api$praya$myitems$builder$socket$SocketEnum() {
      int[] var10000 = $SWITCH_TABLE$api$praya$myitems$builder$socket$SocketEnum;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[SocketEnum.values().length];

         try {
            var0[SocketEnum.ADDITIONAL_DAMAGE.ordinal()] = 1;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[SocketEnum.ADDITIONAL_DEFENSE.ordinal()] = 6;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[SocketEnum.BLOCK_AMOUNT.ordinal()] = 13;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[SocketEnum.BLOCK_RATE.ordinal()] = 14;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[SocketEnum.CRITICAL_CHANCE.ordinal()] = 11;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[SocketEnum.CRITICAL_DAMAGE.ordinal()] = 12;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[SocketEnum.DODGE_RATE.ordinal()] = 16;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[SocketEnum.HEALTH.ordinal()] = 8;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[SocketEnum.HIT_RATE.ordinal()] = 15;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[SocketEnum.PENETRATION.ordinal()] = 3;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[SocketEnum.PERCENT_DAMAGE.ordinal()] = 2;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[SocketEnum.PERCENT_DEFENSE.ordinal()] = 7;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[SocketEnum.PVE_DAMAGE.ordinal()] = 5;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[SocketEnum.PVE_DEFENSE.ordinal()] = 10;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[SocketEnum.PVP_DAMAGE.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[SocketEnum.PVP_DEFENSE.ordinal()] = 9;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$api$praya$myitems$builder$socket$SocketEnum = var0;
         return var0;
      }
   }
}
