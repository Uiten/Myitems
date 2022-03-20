package com.praya.myitems.listener.main;

import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import com.praya.agarthalib.utility.EntityUtil;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import com.praya.myitems.manager.game.RequirementManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import java.util.HashMap;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerEntityShootBow extends HandlerEvent implements Listener {
   public ListenerEntityShootBow(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void checkRequirement(EntityShootBowEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      PluginManager pluginManager = this.plugin.getPluginManager();
      RequirementManager requirementManager = gameManager.getRequirementManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      LivingEntity shooter = event.getEntity();
      if (shooter instanceof Player) {
         Player player = (Player)shooter;
         ItemStack item = event.getBow();
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

   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void checkUnbound(EntityShootBowEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      PluginManager pluginManager = this.plugin.getPluginManager();
      RequirementManager requirementManager = gameManager.getRequirementManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      if (!event.isCancelled()) {
         LivingEntity shooter = event.getEntity();
         if (shooter instanceof Player) {
            Player player = (Player)shooter;
            ItemStack item = event.getBow();
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

   }

   @EventHandler(
      priority = EventPriority.HIGH
   )
   public void entityShootBowEvent(EntityShootBowEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      ItemSetManager itemSetManager = gameManager.getItemSetManager();
      LanguageManager lang = this.plugin.getPluginManager().getLanguageManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (!event.isCancelled() && EntityUtil.isPlayer(event.getEntity())) {
         Player player = PlayerUtil.parse(event.getEntity());
         ItemStack item = event.getBow();
         if (EquipmentUtil.loreCheck(item) && statsManager.hasLoreStats(item, LoreStatsEnum.DURABILITY)) {
            int durability = (int)statsManager.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT);
            int nextValue = durability - 1;
            if (nextValue < 1) {
               boolean enableItemBroken = mainConfig.isStatsEnableItemBroken();
               boolean enableItemBrokenMessage = mainConfig.isStatsEnableItemBrokenMessage();
               if (enableItemBrokenMessage) {
                  String message = lang.getText((LivingEntity)player, "Item_Broken_Bow");
                  SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                  SenderUtil.sendMessage(player, message);
               }

               if (enableItemBroken) {
                  boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
                  passiveEffectManager.reloadPassiveEffect(player, item, enableGradeCalculation);
                  if (Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND).equals(item)) {
                     Bridge.getBridgeEquipment().setEquipment(player, (ItemStack)null, Slot.MAINHAND);
                  } else if (Bridge.getBridgeEquipment().getEquipment(player, Slot.OFFHAND).equals(item)) {
                     Bridge.getBridgeEquipment().setEquipment(player, (ItemStack)null, Slot.OFFHAND);
                  }

                  itemSetManager.updateItemSet(player);
               }
            }

            if (durability < 1) {
               event.setCancelled(true);
            } else {
               statsManager.damageDurability(item);
            }
         }
      }

   }
}
