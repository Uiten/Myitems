package com.praya.myitems.listener.main;

import api.praya.myitems.builder.event.PowerPreCastEvent;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.ServerEventUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.PowerManager;
import com.praya.myitems.manager.game.RequirementManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.enums.main.SlotType;
import java.util.HashMap;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ListenerPlayerInteract extends HandlerEvent implements Listener {
   public ListenerPlayerInteract(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void checkRequirement(PlayerInteractEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      PluginManager pluginManager = this.plugin.getPluginManager();
      RequirementManager requirementManager = gameManager.getRequirementManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      Player player = event.getPlayer();
      ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
      if (EquipmentUtil.loreCheck(item)) {
         String reqClass;
         String message;
         if (!requirementManager.isAllowedReqSoulBound(player, item)) {
            reqClass = requirementManager.getRequirementSoulBound(item);
            message = TextUtil.placeholder(lang.getText((LivingEntity)player, "Requirement_Not_Allowed_Bound"), "Player", reqClass);
            event.setCancelled(true);
            SenderUtil.sendMessage(player, message);
            SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
            return;
         }

         if (!requirementManager.isAllowedReqPermission(player, item)) {
            reqClass = requirementManager.getRequirementPermission(item);
            message = TextUtil.placeholder(lang.getText((LivingEntity)player, "Requirement_Not_Allowed_Permission"), "Permission", reqClass);
            event.setCancelled(true);
            SenderUtil.sendMessage(player, message);
            SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
            return;
         }

         if (!requirementManager.isAllowedReqLevel(player, item)) {
            int reqLevel = requirementManager.getRequirementLevel(item);
            message = TextUtil.placeholder(lang.getText((LivingEntity)player, "Requirement_Not_Allowed_Level"), "Level", String.valueOf(reqLevel));
            event.setCancelled(true);
            SenderUtil.sendMessage(player, message);
            SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
            return;
         }

         if (!requirementManager.isAllowedReqClass(player, item)) {
            reqClass = requirementManager.getRequirementClass(item);
            message = TextUtil.placeholder(lang.getText((LivingEntity)player, "Requirement_Not_Allowed_Class"), "Class", reqClass);
            event.setCancelled(true);
            SenderUtil.sendMessage(player, message);
            SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
            return;
         }
      }

   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void checkBound(PlayerInteractEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      PluginManager pluginManager = this.plugin.getPluginManager();
      RequirementManager requirementManager = gameManager.getRequirementManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      if (!event.isCancelled()) {
         Player player = event.getPlayer();
         ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
         if (EquipmentUtil.loreCheck(item)) {
            Integer lineUnbound = requirementManager.getLineRequirementSoulUnbound(item);
            if (lineUnbound != null) {
               String loreBound = requirementManager.getTextSoulBound(player);
               Integer lineOld = requirementManager.getLineRequirementSoulBound(item);
               HashMap<String, String> map = new HashMap();
               if (lineOld != null) {
                  EquipmentUtil.removeLore(item, lineOld);
               }

               String message = lang.getText((LivingEntity)player, "Item_Bound");
               map.put("item", EquipmentUtil.getDisplayName(item));
               map.put("player", player.getName());
               message = TextUtil.placeholder(map, message);
               requirementManager.setMetadataSoulbound(player, item);
               EquipmentUtil.setLore(item, lineUnbound, loreBound);
               SenderUtil.sendMessage(player, message);
               return;
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void triggerEquipmentChangeEvent(PlayerInteractEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      final ItemSetManager itemSetManager = gameManager.getItemSetManager();
      Action action = event.getAction();
      if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
         final Player player = event.getPlayer();
         ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
         if (itemSetManager.isItemSet(item)) {
            SlotType slotType = SlotType.getSlotType(item);
            if (slotType.equals(SlotType.ARMOR)) {
               (new BukkitRunnable() {
                  public void run() {
                     itemSetManager.updateItemSet(player);
                  }
               }).runTaskLater(this.plugin, 0L);
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void triggerSupport(PlayerInteractEvent event) {
      Action action = event.getAction();
      if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
         final Player player = event.getPlayer();
         ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
         SlotType slotType = SlotType.getSlotType(item);
         if (slotType.equals(SlotType.ARMOR)) {
            (new BukkitRunnable() {
               public void run() {
                  TriggerSupportUtil.updateSupport(player);
               }
            }).runTaskLater(this.plugin, 2L);
         }
      }

   }

   @EventHandler
   public void powerCheckEvent(PlayerInteractEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      PowerManager powerManager = gameManager.getPowerManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      Player player = event.getPlayer();
      ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
      if (EquipmentUtil.loreCheck(item)) {
         int durability = (int)statsManager.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT);
         if (statsManager.checkDurability(item, durability)) {
            Action action = event.getAction();
            PowerClickEnum click;
            if (!action.equals(Action.LEFT_CLICK_AIR) && !action.equals(Action.LEFT_CLICK_BLOCK)) {
               if (!action.equals(Action.RIGHT_CLICK_AIR) && !action.equals(Action.RIGHT_CLICK_BLOCK)) {
                  return;
               }

               if (player.isSneaking()) {
                  click = PowerClickEnum.SHIFT_RIGHT;
               } else {
                  click = PowerClickEnum.RIGHT;
               }
            } else if (player.isSneaking()) {
               click = PowerClickEnum.SHIFT_LEFT;
            } else {
               click = PowerClickEnum.LEFT;
            }

            int line = powerManager.getLineClick(item, click);
            if (line != -1) {
               List<String> lores = EquipmentUtil.getLores(item);
               String lore = (String)lores.get(line - 1);
               PowerEnum power = powerManager.getPower(lore);
               if (power != null) {
                  PowerPreCastEvent powerPreCastEvent = new PowerPreCastEvent(player, power, click, item, lore);
                  ServerEventUtil.callEvent(powerPreCastEvent);
               }
            }
         }
      }

   }
}
