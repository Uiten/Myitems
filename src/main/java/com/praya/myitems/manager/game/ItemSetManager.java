package com.praya.myitems.manager.game;

import api.praya.myitems.builder.ability.AbilityItemWeapon;
import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.item.ItemSet;
import api.praya.myitems.builder.item.ItemSetBonus;
import api.praya.myitems.builder.item.ItemSetBonusEffect;
import api.praya.myitems.builder.item.ItemSetBonusEffectAbilityWeapon;
import api.praya.myitems.builder.item.ItemSetBonusEffectEntity;
import api.praya.myitems.builder.item.ItemSetBonusEffectStats;
import api.praya.myitems.builder.item.ItemSetComponent;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.game.ItemSetConfig;
import com.praya.myitems.config.plugin.MainConfig;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemSetManager extends HandlerManager {
   private final ItemSetConfig itemSetConfig;

   protected ItemSetManager(MyItems plugin) {
      super(plugin);
      this.itemSetConfig = new ItemSetConfig(plugin);
   }

   public final ItemSetConfig getItemSetConfig() {
      return this.itemSetConfig;
   }

   public final Collection<String> getItemSetIDs() {
      return this.getItemSetConfig().getItemSetIDs();
   }

   public final Collection<ItemSet> getAllItemSet() {
      return this.getItemSetConfig().getAllItemSet();
   }

   public final ItemSet getItemSet(String id) {
      return this.getItemSetConfig().getItemSet(id);
   }

   public final boolean isExists(String id) {
      return this.getItemSet(id) != null;
   }

   public final Collection<String> getItemComponentIDs() {
      Collection<String> itemComponentIDs = new ArrayList();
      Iterator var3 = this.getAllItemSet().iterator();

      while(var3.hasNext()) {
         ItemSet itemSet = (ItemSet)var3.next();
         Iterator var5 = itemSet.getAllItemSetComponent().iterator();

         while(var5.hasNext()) {
            ItemSetComponent itemSetComponent = (ItemSetComponent)var5.next();
            String itemComponentID = itemSetComponent.getID();
            itemComponentIDs.add(itemComponentID);
         }
      }

      return itemComponentIDs;
   }

   public final ItemSetComponent getItemComponentByKeyLore(String keyLore) {
      if (keyLore != null) {
         Iterator var3 = this.getAllItemSet().iterator();

         while(var3.hasNext()) {
            ItemSet itemSet = (ItemSet)var3.next();
            Iterator var5 = itemSet.getAllItemSetComponent().iterator();

            while(var5.hasNext()) {
               ItemSetComponent key = (ItemSetComponent)var5.next();
               if (key.getKeyLore().equalsIgnoreCase(keyLore)) {
                  return key;
               }
            }
         }
      }

      return null;
   }

   public final ItemSetComponent getItemComponentByLore(String lore) {
      if (lore != null) {
         String keySetComponentSelf = MainConfig.KEY_SET_COMPONENT_SELF;
         if (lore.contains(keySetComponentSelf)) {
            String[] partsComponent = lore.split(keySetComponentSelf);
            if (partsComponent.length > 1) {
               String componentKeyLore = ChatColor.stripColor(partsComponent[1]);
               ItemSetComponent itemSetComponent = this.getItemComponentByKeyLore(componentKeyLore);
               return itemSetComponent;
            }
         }
      }

      return null;
   }

   public final ItemSetComponent getItemComponent(ItemStack item) {
      if (item != null) {
         List<String> lores = EquipmentUtil.getLores(item);
         Iterator var4 = lores.iterator();

         while(var4.hasNext()) {
            String lore = (String)var4.next();
            ItemSetComponent itemSetComponent = this.getItemComponentByLore(lore);
            if (itemSetComponent != null) {
               return itemSetComponent;
            }
         }
      }

      return null;
   }

   public final ItemSet getItemSetByComponentID(String componentID) {
      if (componentID != null) {
         Iterator var3 = this.getAllItemSet().iterator();

         while(var3.hasNext()) {
            ItemSet key = (ItemSet)var3.next();
            Iterator var5 = key.getAllItemSetComponent().iterator();

            while(var5.hasNext()) {
               ItemSetComponent itemSetComponent = (ItemSetComponent)var5.next();
               if (itemSetComponent.getID().equalsIgnoreCase(componentID)) {
                  return key;
               }
            }
         }
      }

      return null;
   }

   public final ItemSet getItemSetByKeyLore(String keyLore) {
      if (keyLore != null) {
         Iterator var3 = this.getAllItemSet().iterator();

         while(var3.hasNext()) {
            ItemSet key = (ItemSet)var3.next();
            Iterator var5 = key.getAllItemSetComponent().iterator();

            while(var5.hasNext()) {
               ItemSetComponent itemSetComponent = (ItemSetComponent)var5.next();
               if (itemSetComponent.getKeyLore().equalsIgnoreCase(keyLore)) {
                  return key;
               }
            }
         }
      }

      return null;
   }

   public final ItemSet getItemSetByLore(String lore) {
      if (lore != null) {
         String keySetComponentSelf = MainConfig.KEY_SET_COMPONENT_SELF;
         if (lore.contains(keySetComponentSelf)) {
            String[] partsComponent = lore.split(keySetComponentSelf);
            if (partsComponent.length > 1) {
               String componentKeyLore = ChatColor.stripColor(partsComponent[1]);
               ItemSet itemSet = this.getItemSetByKeyLore(componentKeyLore);
               return itemSet;
            }
         }
      }

      return null;
   }

   public final ItemSet getItemSet(ItemStack item) {
      if (item != null) {
         List<String> lores = EquipmentUtil.getLores(item);
         Iterator var4 = lores.iterator();

         while(var4.hasNext()) {
            String lore = (String)var4.next();
            ItemSet itemSet = this.getItemSetByLore(lore);
            if (itemSet != null) {
               return itemSet;
            }
         }
      }

      return null;
   }

   public final boolean isItemSet(ItemStack item) {
      return this.getItemSet(item) != null;
   }

   public final HashMap<Slot, ItemSetComponent> getMapItemComponent(LivingEntity entity) {
      return this.getMapItemComponent(entity, true);
   }

   public final HashMap<Slot, ItemSetComponent> getMapItemComponent(LivingEntity entity, boolean checkSlot) {
      HashMap<Slot, ItemSetComponent> mapItemSetComponent = new HashMap();
      if (entity != null) {
         Slot[] var7;
         int var6 = (var7 = Slot.values()).length;

         for(int var5 = 0; var5 < var6; ++var5) {
            Slot slot = var7[var5];
            ItemStack item = Bridge.getBridgeEquipment().getEquipment(entity, slot);
            if (item != null) {
               ItemSetComponent itemSetComponent = this.getItemComponent(item);
               if (itemSetComponent != null && itemSetComponent.isMatchSlot(slot)) {
                  mapItemSetComponent.put(slot, itemSetComponent);
               }
            }
         }
      }

      return mapItemSetComponent;
   }

   public final HashMap<Slot, ItemSet> getMapItemSet(LivingEntity entity) {
      return this.getMapItemSet(entity, true);
   }

   public final HashMap<Slot, ItemSet> getMapItemSet(LivingEntity entity, boolean checkSlot) {
      HashMap<Slot, ItemSet> mapItemSet = new HashMap();
      if (entity != null) {
         HashMap<Slot, ItemSetComponent> mapItemSetComponent = this.getMapItemComponent(entity, checkSlot);
         Iterator var6 = mapItemSetComponent.keySet().iterator();

         while(var6.hasNext()) {
            Slot slot = (Slot)var6.next();
            ItemSetComponent itemSetComponent = (ItemSetComponent)mapItemSetComponent.get(slot);
            ItemSet itemSet = itemSetComponent.getItemSet();
            if (itemSet != null) {
               mapItemSet.put(slot, itemSet);
            }
         }
      }

      return mapItemSet;
   }

   public final HashMap<ItemSet, Integer> getMapItemSetTotal(LivingEntity entity) {
      return this.getMapItemSetTotal(entity, true);
   }

   public final HashMap<ItemSet, Integer> getMapItemSetTotal(LivingEntity entity, boolean checkSlot) {
      HashMap<ItemSet, Integer> mapItemSetTotal = new HashMap();
      if (entity != null) {
         HashMap<Slot, ItemSet> mapItemSet = this.getMapItemSet(entity, checkSlot);
         Iterator var6 = mapItemSet.keySet().iterator();

         while(var6.hasNext()) {
            Slot slot = (Slot)var6.next();
            ItemSet itemSet = (ItemSet)mapItemSet.get(slot);
            if (mapItemSetTotal.containsKey(itemSet)) {
               int total = (Integer)mapItemSetTotal.get(itemSet) + 1;
               mapItemSetTotal.put(itemSet, total);
            } else {
               mapItemSetTotal.put(itemSet, 1);
            }
         }
      }

      return mapItemSetTotal;
   }

   public final ItemSetBonusEffectEntity getItemSetBonusEffectEntity(LivingEntity entity) {
      return this.getItemSetBonusEffectEntity(entity, true);
   }

   public final ItemSetBonusEffectEntity getItemSetBonusEffectEntity(LivingEntity entity, boolean checkSlot) {
      return this.getItemSetBonusEffectEntity(entity, checkSlot, true);
   }

   public final ItemSetBonusEffectEntity getItemSetBonusEffectEntity(LivingEntity entity, boolean checkSlot, boolean checkChance) {
      GameManager gameManager = this.plugin.getGameManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      HashMap<AbilityWeapon, Integer> mapAbilityWeapon = new HashMap();
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
      if (entity != null) {
         HashMap<ItemSet, Integer> mapItemSetTotal = this.getMapItemSetTotal(entity, checkSlot);
         Iterator var51 = mapItemSetTotal.keySet().iterator();

         label34:
         while(var51.hasNext()) {
            ItemSet itemSet = (ItemSet)var51.next();
            int total = (Integer)mapItemSetTotal.get(itemSet);
            Iterator var54 = itemSet.getAllItemSetBonus().iterator();

            while(true) {
               ItemSetBonus itemSetBonus;
               int amountID;
               do {
                  if (!var54.hasNext()) {
                     continue label34;
                  }

                  itemSetBonus = (ItemSetBonus)var54.next();
                  amountID = itemSetBonus.getAmountID();
               } while(total < amountID);

               ItemSetBonusEffect itemSetBonusEffect = itemSetBonus.getEffect();
               ItemSetBonusEffectStats itemSetBonusEffectStats = itemSetBonusEffect.getEffectStats();
               ItemSetBonusEffectAbilityWeapon itemSetBonusEffectAbilityWeapon = itemSetBonusEffect.getEffectAbilityWeapon();
               Collection<AbilityItemWeapon> listAbilityItemWeapon = itemSetBonusEffectAbilityWeapon.getAllAbilityItemWeapon();
               HashMap<AbilityWeapon, Integer> mapAbilityWeaponBonus = abilityWeaponManager.getMapAbilityWeapon(listAbilityItemWeapon, checkChance);
               additionalDamage += itemSetBonusEffectStats.getAdditionalDamage();
               percentDamage += itemSetBonusEffectStats.getPercentDamage();
               penetration += itemSetBonusEffectStats.getPenetration();
               pvpDamage += itemSetBonusEffectStats.getPvPDamage();
               pveDamage += itemSetBonusEffectStats.getPvEDamage();
               additionalDefense += itemSetBonusEffectStats.getAdditionalDefense();
               percentDefense += itemSetBonusEffectStats.getPercentDefense();
               health += itemSetBonusEffectStats.getHealth();
               healthRegen += itemSetBonusEffectStats.getHealthRegen();
               staminaMax += itemSetBonusEffectStats.getStaminaMax();
               staminaRegen += itemSetBonusEffectStats.getStaminaRegen();
               attackAoERadius += itemSetBonusEffectStats.getAttackAoERadius();
               attackAoEDamage += itemSetBonusEffectStats.getAttackAoEDamage();
               pvpDefense += itemSetBonusEffectStats.getPvPDefense();
               pveDefense += itemSetBonusEffectStats.getPvEDefense();
               criticalChance += itemSetBonusEffectStats.getCriticalChance();
               criticalDamage += itemSetBonusEffectStats.getCriticalDamage();
               blockAmount += itemSetBonusEffectStats.getBlockAmount();
               blockRate += itemSetBonusEffectStats.getBlockRate();
               hitRate += itemSetBonusEffectStats.getHitRate();
               dodgeRate += itemSetBonusEffectStats.getDodgeRate();
               Iterator var62 = mapAbilityWeaponBonus.keySet().iterator();

               while(var62.hasNext()) {
                  AbilityWeapon abilityWeapon = (AbilityWeapon)var62.next();
                  int grade = (Integer)mapAbilityWeaponBonus.get(abilityWeapon);
                  if (mapAbilityWeapon.containsKey(abilityWeapon)) {
                     int totalGrade = (Integer)mapAbilityWeapon.get(abilityWeapon) + grade;
                     mapAbilityWeapon.put(abilityWeapon, totalGrade);
                  } else {
                     mapAbilityWeapon.put(abilityWeapon, grade);
                  }
               }
            }
         }
      }

      ItemSetBonusEffectStats itemSetBonusEffectStats = new ItemSetBonusEffectStats(additionalDamage, percentDamage, penetration, pvpDamage, pveDamage, additionalDefense, percentDefense, health, healthRegen, staminaMax, staminaRegen, attackAoERadius, attackAoEDamage, pvpDefense, pveDefense, criticalChance, criticalDamage, blockAmount, blockRate, hitRate, dodgeRate);
      ItemSetBonusEffectEntity itemSetBonusEffectEntity = new ItemSetBonusEffectEntity(itemSetBonusEffectStats, mapAbilityWeapon);
      return itemSetBonusEffectEntity;
   }

   public final void updateItemSet(LivingEntity entity) {
      this.updateItemSet(entity, true);
   }

   public final void updateItemSet(LivingEntity entity, boolean checkPlayerInventory) {
      this.updateItemSet(entity, checkPlayerInventory, (Inventory)null);
   }

   public final void updateItemSet(LivingEntity entity, boolean checkPlayerInventory, Inventory inventory) {
      MainConfig mainConfig = MainConfig.getInstance();
      if (entity != null) {
         String divider = "\n";
         String keyLine = MainConfig.KEY_SET_LINE;
         String keySetComponentSelf = MainConfig.KEY_SET_COMPONENT_SELF;
         String keySetComponentOther = MainConfig.KEY_SET_COMPONENT_OTHER;
         String loreBonusActive = mainConfig.getSetLoreBonusActive();
         String loreBonusInactive = mainConfig.getSetLoreBonusInactive();
         String loreComponentActive = mainConfig.getSetLoreComponentActive();
         String loreComponentInactive = mainConfig.getSetLoreComponentInactive();
         HashMap<Slot, ItemSetComponent> mapItemSetComponent = this.getMapItemComponent(entity);
         HashMap<ItemSet, Integer> mapItemSetTotal = new HashMap();
         Collection<ItemSetComponent> allItemSetComponent = mapItemSetComponent.values();
         Set<ItemStack> contents = new HashSet();
         Slot[] var20;
         int var19 = (var20 = Slot.values()).length;

         int var18;
         for(var18 = 0; var18 < var19; ++var18) {
            Slot slot = var20[var18];
            ItemStack item = Bridge.getBridgeEquipment().getEquipment(entity, slot);
            if (item != null) {
               contents.add(item);
               if (mapItemSetComponent.containsKey(slot)) {
                  ItemSetComponent itemSetComponent = (ItemSetComponent)mapItemSetComponent.get(slot);
                  ItemSet itemSet = itemSetComponent.getItemSet();
                  if (itemSet != null) {
                     if (mapItemSetTotal.containsKey(itemSet)) {
                        int total = (Integer)mapItemSetTotal.get(itemSet) + 1;
                        mapItemSetTotal.put(itemSet, total);
                     } else {
                        mapItemSetTotal.put(itemSet, 1);
                     }
                  }
               }
            }
         }

         Player player;
         ItemStack item;
         int total;
         if (entity instanceof Player) {
            if (checkPlayerInventory) {
               player = (Player)entity;
               PlayerInventory playerInventory = player.getInventory();
               ItemStack itemCursor = player.getItemOnCursor();
               if (itemCursor != null) {
                  contents.add(itemCursor);
               }

               ItemStack[] var53;
               total = (var53 = playerInventory.getContents()).length;

               for(int var49 = 0; var49 < total; ++var49) {
                  ItemStack content = var53[var49];
                  if (content != null) {
                     contents.add(content);
                  }
               }
            }

            if (inventory != null) {
               ItemStack[] var47;
               var19 = (var47 = inventory.getContents()).length;

               for(var18 = 0; var18 < var19; ++var18) {
                  item = var47[var18];
                  if (item != null) {
                     contents.add(item);
                  }
               }
            }
         }

         Iterator var42 = contents.iterator();

         while(true) {
            ItemSetComponent itemSetComponent;
            ItemSet itemSet;
            do {
               do {
                  if (!var42.hasNext()) {
                     if (entity instanceof Player) {
                        player = (Player)entity;
                        GameMode gameMode = player.getGameMode();
                        InventoryView inventoryView = player.getOpenInventory();
                        InventoryType inventoryType = inventoryView.getType();
                        if (!gameMode.equals(GameMode.CREATIVE) || !inventoryType.equals(InventoryType.CREATIVE)) {
                           player.updateInventory();
                           return;
                        }
                     }

                     return;
                  }

                  item = (ItemStack)var42.next();
                  itemSetComponent = this.getItemComponent(item);
               } while(itemSetComponent == null);

               itemSet = itemSetComponent.getItemSet();
            } while(itemSet == null);

            String name = itemSet.getName();
            total = mapItemSetTotal.containsKey(itemSet) ? (Integer)mapItemSetTotal.get(itemSet) : 0;
            int maxComponent = itemSet.getTotalComponent();
            List<String> lores = EquipmentUtil.getLores(item);
            List<String> loresBonus = new ArrayList();
            List<String> loresComponent = new ArrayList();
            List<Integer> bonusAmountIDs = new ArrayList(itemSet.getBonusAmountIDs());
            Iterator<String> iteratorLores = lores.iterator();
            HashMap<String, String> mapPlaceholder = new HashMap();
            List<String> loresSet = mainConfig.getSetFormat();
            Collections.sort(bonusAmountIDs);

            String lineBonus;
            while(iteratorLores.hasNext()) {
               lineBonus = (String)iteratorLores.next();
               if (lineBonus.contains(keyLine)) {
                  iteratorLores.remove();
               }
            }

            Iterator var32 = itemSet.getAllItemSetComponent().iterator();

            String lore;
            String description;
            while(var32.hasNext()) {
               ItemSetComponent partComponent = (ItemSetComponent)var32.next();
               String partComponentID = partComponent.getID();
               lore = partComponent.getKeyLore();
               String formatComponent = mainConfig.getSetFormatComponent();
               if (allItemSetComponent.contains(partComponent)) {
                  formatComponent = loreComponentActive + formatComponent;
                  if (partComponent.equals(itemSetComponent)) {
                     description = keySetComponentSelf + loreComponentActive + lore + keySetComponentSelf + loreComponentActive;
                  } else {
                     description = keySetComponentOther + loreComponentActive + lore + keySetComponentOther + loreComponentActive;
                  }
               } else {
                  formatComponent = loreComponentInactive + formatComponent;
                  if (partComponent.equals(itemSetComponent)) {
                     description = keySetComponentSelf + loreComponentInactive + lore + keySetComponentSelf + loreComponentInactive;
                  } else {
                     description = keySetComponentOther + loreComponentInactive + lore + keySetComponentOther + loreComponentInactive;
                  }
               }

               mapPlaceholder.clear();
               mapPlaceholder.put("item_set_component_id", partComponentID);
               mapPlaceholder.put("item_set_component_keylore", description);
               formatComponent = TextUtil.placeholder(mapPlaceholder, formatComponent, "<", ">");
               loresComponent.add(formatComponent);
            }

            var32 = bonusAmountIDs.iterator();

            while(var32.hasNext()) {
               int bonusAmountID = (Integer)var32.next();
               ItemSetBonus partBonus = itemSet.getItemSetBonus(bonusAmountID);
               List<String> listDescription = partBonus.getDescription();
               Iterator var62 = listDescription.iterator();

               while(var62.hasNext()) {
                  description = (String)var62.next();
                  String formatBonus = mainConfig.getSetFormatBonus();
                  if (total >= bonusAmountID) {
                     formatBonus = loreBonusActive + formatBonus;
                  } else {
                     formatBonus = loreBonusInactive + formatBonus;
                  }

                  mapPlaceholder.clear();
                  mapPlaceholder.put("item_set_bonus_amount", String.valueOf(bonusAmountID));
                  mapPlaceholder.put("item_set_bonus_description", String.valueOf(description));
                  formatBonus = TextUtil.placeholder(mapPlaceholder, formatBonus, "<", ">");
                  loresBonus.add(formatBonus);
               }
            }

            lineBonus = TextUtil.convertListToString(loresBonus, "\n");
            String lineComponent = TextUtil.convertListToString(loresComponent, "\n");
            mapPlaceholder.clear();
            mapPlaceholder.put("item_set_name", name);
            mapPlaceholder.put("item_set_total", String.valueOf(total));
            mapPlaceholder.put("item_set_max", String.valueOf(maxComponent));
            mapPlaceholder.put("list_item_set_component", lineComponent);
            mapPlaceholder.put("list_item_set_bonus", lineBonus);
            loresSet = TextUtil.placeholder(mapPlaceholder, loresSet, "<", ">");
            loresSet = TextUtil.expandList(loresSet, "\n");
            ListIterator iteratorLoresSet = loresSet.listIterator();

            while(iteratorLoresSet.hasNext()) {
               lore = keyLine + (String)iteratorLoresSet.next();
               iteratorLoresSet.set(lore);
            }

            lores.addAll(loresSet);
            EquipmentUtil.setLores(item, lores);
         }
      }
   }
}
