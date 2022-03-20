package com.praya.myitems.listener.main;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import api.praya.myitems.builder.socket.SocketGems;
import api.praya.myitems.builder.socket.SocketGemsTree;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.ServerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.MenuManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.menu.MenuSocket;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.menu.MenuGUI;
import core.praya.agarthalib.builder.menu.MenuSlot;
import core.praya.agarthalib.builder.menu.MenuGUI.SlotCell;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.enums.main.SlotType;
import core.praya.agarthalib.enums.main.VersionNMS;
import java.util.Collection;
import java.util.HashMap;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ListenerInventoryClick extends HandlerEvent implements Listener {
   public ListenerInventoryClick(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void socket(final InventoryClickEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      MenuManager menuManager = gameManager.getMenuManager();
      SocketManager socketManager = gameManager.getSocketManager();
      LanguageManager lang = this.plugin.getPluginManager().getLanguageManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (!event.isCancelled()) {
         Inventory inventory = event.getInventory();
         InventoryType type = inventory.getType();
         if (type.equals(InventoryType.CRAFTING)) {
            InventoryHolder holder = inventory.getHolder();
            if (!(holder instanceof MenuGUI)) {
               Player player = PlayerUtil.parse(event.getWhoClicked());
               ItemStack cursorItem = event.getCursor();
               ItemStack currentItem = event.getCurrentItem();
               if (EquipmentUtil.isSolid(cursorItem) && EquipmentUtil.isSolid(currentItem)) {
                  GameMode gameMode = player.getGameMode();
                  InventoryAction inventoryAction = event.getAction();
                  boolean checkSocket = gameMode.equals(GameMode.CREATIVE) ? inventoryAction.equals(InventoryAction.PLACE_ALL) : inventoryAction.equals(InventoryAction.SWAP_WITH_CURSOR);
                  if (checkSocket) {
                     ItemStack itemRodUnlock = mainConfig.getSocketItemRodUnlock();
                     ItemStack itemRodRemove = mainConfig.getSocketItemRodRemove();
                     boolean containsSocketEmpty = socketManager.containsSocketEmpty(currentItem);
                     boolean containsSocketLocked = socketManager.containsSocketLocked(currentItem);
                     boolean containsSocketGems = socketManager.containsSocketGems(currentItem);
                     boolean isSocketGems = socketManager.isSocketItem(cursorItem);
                     boolean isSocketRodUnlock = cursorItem.isSimilar(itemRodUnlock);
                     boolean isSocketRodRemove = cursorItem.isSimilar(itemRodRemove);
                     String message;
                     if (isSocketGems) {
                        if (!containsSocketEmpty) {
                           message = lang.getText((LivingEntity)player, "Socket_Slot_Empty_Failure");
                           SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                           SenderUtil.sendMessage(player, message);
                           return;
                        }

                        SocketGems socket = socketManager.getSocketBuild(cursorItem);
                        SocketGemsTree socketTree = socket.getSocketTree();
                        SlotType typeItem = socketTree.getTypeItem();
                        SlotType typeDefault = SlotType.getSlotType(currentItem);
                        if (!typeItem.equals(typeDefault) && !typeItem.equals(SlotType.UNIVERSAL)) {
                           message = lang.getText((LivingEntity)player, "Socket_Type_Not_Match");
                           SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                           SenderUtil.sendMessage(player, message);
                           return;
                        }
                     } else if (isSocketRodUnlock) {
                        if (!containsSocketLocked) {
                           message = lang.getText((LivingEntity)player, "Socket_Slot_Locked_Failure");
                           SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                           SenderUtil.sendMessage(player, message);
                           return;
                        }
                     } else {
                        if (!isSocketRodRemove) {
                           return;
                        }

                        if (!containsSocketGems) {
                           message = lang.getText((LivingEntity)player, "Socket_Slot_Gems_Failure");
                           SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                           SenderUtil.sendMessage(player, message);
                           return;
                        }
                     }

                     if (!ServerUtil.isCompatible(VersionNMS.V1_10_R1)) {
                        event.setCancelled(true);
                     }

                     menuManager.openMenuSocket(player);
                     InventoryView view = player.getOpenInventory();
                     Inventory topInventory = view.getTopInventory();
                     InventoryHolder topHolder = topInventory.getHolder();
                     if (topHolder instanceof MenuGUI) {
                        MenuGUI menuGUI = (MenuGUI)topHolder;
                        MenuSocket executor = (MenuSocket)menuGUI.getExecutor();
                        SlotCell cellItemInput = SlotCell.B3;
                        SlotCell cellSocketInput = SlotCell.C3;
                        MenuSlot menuSlotItemInput = menuGUI.getMenuSlot(cellItemInput.getIndex());
                        MenuSlot menuSlotSocketInput = menuGUI.getMenuSlot(cellSocketInput.getIndex());
                        menuSlotItemInput.setItem(currentItem);
                        menuSlotSocketInput.setItem(cursorItem);
                        menuGUI.setMenuSlot(menuSlotItemInput);
                        menuGUI.setMenuSlot(menuSlotSocketInput);
                        executor.updateSocketMenu(menuGUI, player);
                        player.setItemOnCursor((ItemStack)null);
                        if (gameMode.equals(GameMode.CREATIVE)) {
                           (new BukkitRunnable() {
                              public void run() {
                                 event.setCurrentItem((ItemStack)null);
                              }
                           }).runTaskLater(this.plugin, 0L);
                        } else {
                           event.setCurrentItem((ItemStack)null);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void triggerSupport(InventoryClickEvent event) {
      if (!event.isCancelled()) {
         Inventory inventory = event.getInventory();
         InventoryHolder holder = inventory.getHolder();
         if (!(holder instanceof MenuGUI)) {
            final Player player = PlayerUtil.parse(event.getWhoClicked());
            final ItemStack oldHelmet = Bridge.getBridgeEquipment().getEquipment(player, Slot.HELMET);
            final ItemStack oldChestplate = Bridge.getBridgeEquipment().getEquipment(player, Slot.CHESTPLATE);
            final ItemStack oldLeggings = Bridge.getBridgeEquipment().getEquipment(player, Slot.LEGGINGS);
            final ItemStack oldBoots = Bridge.getBridgeEquipment().getEquipment(player, Slot.BOOTS);
            (new BukkitRunnable() {
               public void run() {
                  ItemStack newHelmet = Bridge.getBridgeEquipment().getEquipment(player, Slot.HELMET);
                  ItemStack newChestplate = Bridge.getBridgeEquipment().getEquipment(player, Slot.CHESTPLATE);
                  ItemStack newLeggings = Bridge.getBridgeEquipment().getEquipment(player, Slot.LEGGINGS);
                  ItemStack newBoots = Bridge.getBridgeEquipment().getEquipment(player, Slot.BOOTS);
                  if (oldHelmet != newHelmet || oldChestplate != newChestplate || oldLeggings != newLeggings || oldBoots != newBoots) {
                     TriggerSupportUtil.updateSupport(player);
                  }

               }
            }).runTaskLater(this.plugin, 0L);
         }
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void eventChangeEquipment(InventoryClickEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
      final ItemSetManager itemSetManager = gameManager.getItemSetManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (!event.isCancelled()) {
         final Player player = PlayerUtil.parse(event.getWhoClicked());
         org.bukkit.event.inventory.InventoryType.SlotType slotType = event.getSlotType();
         final ClickType click = event.getClick();
         ItemStack itemCurrent = event.getCurrentItem();
         ItemStack itemCursor = event.getCursor();
         final boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
         final Collection<PassiveEffectEnum> currentPassiveEffects = passiveEffectManager.getPassiveEffects(itemCurrent);
         final Collection<PassiveEffectEnum> cursorPassiveEffects = passiveEffectManager.getPassiveEffects(itemCursor);
         final HashMap<Slot, ItemStack> mapItemSlot = new HashMap();
         Slot[] var18;
         int var17 = (var18 = Slot.values()).length;

         for(int var16 = 0; var16 < var17; ++var16) {
            Slot slot = var18[var16];
            ItemStack itemSlot = Bridge.getBridgeEquipment().getEquipment(player, slot);
            mapItemSlot.put(slot, itemSlot);
         }

         (new BukkitRunnable() {
            public void run() {
               InventoryView inventoryView = player.getOpenInventory();
               InventoryType inventoryType = inventoryView.getType();
               Inventory inventory = !inventoryType.equals(InventoryType.CREATIVE) ? inventoryView.getTopInventory() : null;
               Slot[] var7;
               int var6 = (var7 = Slot.values()).length;

               for(int var5 = 0; var5 < var6; ++var5) {
                  Slot slot = var7[var5];
                  ItemStack itemBefore = (ItemStack)mapItemSlot.get(slot);
                  ItemStack itemAfter = Bridge.getBridgeEquipment().getEquipment(player, slot);
                  boolean isSolidBefore = itemBefore != null;
                  boolean isSolidAfter = itemAfter != null;
                  if (isSolidBefore && isSolidAfter) {
                     if (itemBefore.equals(itemAfter)) {
                        continue;
                     }
                  } else if (isSolidBefore == isSolidAfter) {
                     continue;
                  }

                  if (itemSetManager.isItemSet(itemBefore) || itemSetManager.isItemSet(itemAfter)) {
                     itemSetManager.updateItemSet(player, true, inventory);
                     break;
                  }
               }

            }
         }).runTaskLater(this.plugin, 0L);
         if (!click.equals(ClickType.NUMBER_KEY) && !click.equals(ClickType.LEFT) && !click.equals(ClickType.SHIFT_LEFT) && !click.equals(ClickType.RIGHT) && !click.equals(ClickType.SHIFT_RIGHT)) {
            if (slotType.equals(org.bukkit.event.inventory.InventoryType.SlotType.ARMOR) || slotType.equals(org.bukkit.event.inventory.InventoryType.SlotType.QUICKBAR)) {
               (new BukkitRunnable() {
                  public void run() {
                     passiveEffectManager.reloadPassiveEffect(player, cursorPassiveEffects, enableGradeCalculation);
                     passiveEffectManager.reloadPassiveEffect(player, currentPassiveEffects, enableGradeCalculation);
                     TriggerSupportUtil.updateSupport(player);
                  }
               }).runTaskLater(this.plugin, 0L);
            }
         } else {
            ItemStack itemMainHand = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            ItemStack itemOffHand = Bridge.getBridgeEquipment().getEquipment(player, Slot.OFFHAND);
            final Collection<PassiveEffectEnum> mainPassiveEffects = passiveEffectManager.getPassiveEffects(itemMainHand);
            final Collection<PassiveEffectEnum> offPassiveEffects = passiveEffectManager.getPassiveEffects(itemOffHand);
            (new BukkitRunnable() {
               public void run() {
                  passiveEffectManager.reloadPassiveEffect(player, mainPassiveEffects, enableGradeCalculation);
                  passiveEffectManager.reloadPassiveEffect(player, offPassiveEffects, enableGradeCalculation);
                  passiveEffectManager.reloadPassiveEffect(player, cursorPassiveEffects, enableGradeCalculation);
                  passiveEffectManager.reloadPassiveEffect(player, currentPassiveEffects, enableGradeCalculation);
                  if (click.equals(ClickType.NUMBER_KEY)) {
                     TriggerSupportUtil.updateSupport(player);
                  }

               }
            }).runTaskLater(this.plugin, 0L);
         }
      }

   }
}
