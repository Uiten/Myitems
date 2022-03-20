package com.praya.myitems.menu;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.element.ElementBoostStats;
import api.praya.myitems.builder.item.ItemSetBonusEffectEntity;
import api.praya.myitems.builder.item.ItemSetBonusEffectStats;
import api.praya.myitems.builder.item.ItemStatsArmor;
import api.praya.myitems.builder.item.ItemStatsWeapon;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerMenu;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.player.PlayerItemStatsManager;
import com.praya.myitems.manager.player.PlayerManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.menu.Menu;
import core.praya.agarthalib.builder.menu.MenuExecutor;
import core.praya.agarthalib.builder.menu.MenuGUI;
import core.praya.agarthalib.builder.menu.MenuSlot;
import core.praya.agarthalib.builder.menu.MenuGUI.SlotCell;
import core.praya.agarthalib.builder.menu.MenuSlotAction.ActionType;
import core.praya.agarthalib.enums.branch.FlagEnum;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import core.praya.agarthalib.enums.main.RomanNumber;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.enums.main.SlotType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MenuStats extends HandlerMenu implements MenuExecutor {
   public MenuStats(MyItems plugin) {
      super(plugin);
   }

   public void onClick(Player player, Menu menu, ActionType actionType, String... args) {
   }

   public final void updateStatsMenu(MenuGUI menuGUI, Player player) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      GameManager gameManager = this.plugin.getGameManager();
      PlayerManager playerManager = this.plugin.getPlayerManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      ElementManager elementManager = gameManager.getElementManager();
      ItemSetManager itemSetManager = gameManager.getItemSetManager();
      PlayerItemStatsManager playerItemStatsManager = playerManager.getPlayerItemStatsManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      String divider = "\n";
      String headerSlotHelmet = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Slot_Helmet");
      String headerSlotChestplate = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Slot_Chestplate");
      String headerSlotLeggings = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Slot_Leggings");
      String headerSlotBoots = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Slot_Boots");
      String headerSlotMainHand = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Slot_MainHand");
      String headerSlotOffHand = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Slot_OffHand");
      String headerInformation = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Information");
      String headerAttack = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Attack");
      String headerDefense = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Defense");
      String headerAbility = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Ability");
      String headerElement = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Element");
      SlotCell cellEquipmentHelmet = SlotCell.C2;
      SlotCell cellEquipmentChestplate = SlotCell.C3;
      SlotCell cellEquipmentLeggings = SlotCell.C4;
      SlotCell cellEquipmentBoots = SlotCell.C5;
      SlotCell cellEquipmentMainHand = SlotCell.E2;
      SlotCell cellEquipmentOffHand = SlotCell.E3;
      SlotCell cellInformation = SlotCell.E5;
      SlotCell cellAttack = SlotCell.G2;
      SlotCell cellDefense = SlotCell.G3;
      SlotCell cellAbility = SlotCell.G4;
      SlotCell cellElement = SlotCell.G5;
      MenuSlot menuSlotEquipmentHelmet = new MenuSlot(cellEquipmentHelmet.getIndex());
      MenuSlot menuSlotEquipmentChestplate = new MenuSlot(cellEquipmentChestplate.getIndex());
      MenuSlot menuSlotEquipmentLeggings = new MenuSlot(cellEquipmentLeggings.getIndex());
      MenuSlot menuSlotEquipmentBoots = new MenuSlot(cellEquipmentBoots.getIndex());
      MenuSlot menuSlotEquipmentMainHand = new MenuSlot(cellEquipmentMainHand.getIndex());
      MenuSlot menuSlotEquipmentOffHand = new MenuSlot(cellEquipmentOffHand.getIndex());
      MenuSlot menuSlotInformation = new MenuSlot(cellInformation.getIndex());
      MenuSlot menuSlotAttack = new MenuSlot(cellAttack.getIndex());
      MenuSlot menuSlotDefense = new MenuSlot(cellDefense.getIndex());
      MenuSlot menuSlotAbility = new MenuSlot(cellAbility.getIndex());
      MenuSlot menuSlotElement = new MenuSlot(cellElement.getIndex());
      HashMap<String, String> map = new HashMap();
      HashMap<String, String> mapData = new HashMap();
      ItemStack itemEquipmentHelmet = Bridge.getBridgeEquipment().getEquipment(player, Slot.HELMET);
      ItemStack itemEquipmentChestplate = Bridge.getBridgeEquipment().getEquipment(player, Slot.CHESTPLATE);
      ItemStack itemEquipmentLeggings = Bridge.getBridgeEquipment().getEquipment(player, Slot.LEGGINGS);
      ItemStack itemEquipmentBoots = Bridge.getBridgeEquipment().getEquipment(player, Slot.BOOTS);
      ItemStack itemEquipmentMainHand = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
      ItemStack itemEquipmentOffHand = Bridge.getBridgeEquipment().getEquipment(player, Slot.OFFHAND);
      ItemStatsWeapon itemStatsWeapon = playerItemStatsManager.getItemStatsWeapon(player);
      ItemStatsArmor itemStatsArmor = playerItemStatsManager.getItemStatsArmor(player);
      HashMap<AbilityWeapon, Integer> mapAbilityWeapon = abilityWeaponManager.getMapAbilityWeapon((LivingEntity)player);
      HashMap<String, Double> mapElementWeapon = elementManager.getMapElement(player, SlotType.WEAPON, false);
      HashMap<String, Double> mapElementArmor = elementManager.getMapElement(player, SlotType.ARMOR, false);
      ItemSetBonusEffectEntity itemSetBonusEffectEntity = itemSetManager.getItemSetBonusEffectEntity(player, true, false);
      ItemSetBonusEffectStats itemSetBonusEffectStats = itemSetBonusEffectEntity.getEffectStats();
      ElementBoostStats elementWeapon = elementManager.getElementBoostStats(mapElementWeapon);
      double elementAdditionalDamage = elementWeapon.getBaseAdditionalDamage();
      double elementPercentDamage = elementWeapon.getBasePercentDamage();
      double abilityBaseBonusDamage = abilityWeaponManager.getTotalBaseBonusDamage(mapAbilityWeapon);
      double abilityBasePercentDamage = abilityWeaponManager.getTotalBasePercentDamage(mapAbilityWeapon);
      double abilityCastBonusDamage = abilityWeaponManager.getTotalCastBonusDamage(mapAbilityWeapon);
      double abilityCastPercentDamage = abilityWeaponManager.getTotalCastPercentDamage(mapAbilityWeapon);
      double totalDamageMin = (itemStatsWeapon.getTotalDamageMin() + itemSetBonusEffectStats.getAdditionalDamage()) * ((100.0D + itemSetBonusEffectStats.getPercentDamage()) / 100.0D);
      double totalDamageMax = (itemStatsWeapon.getTotalDamageMax() + itemSetBonusEffectStats.getAdditionalDamage()) * ((100.0D + itemSetBonusEffectStats.getPercentDamage()) / 100.0D);
      double totalPenetration = itemStatsWeapon.getTotalPenetration() + itemSetBonusEffectStats.getPenetration();
      double totalPvPDamage = itemStatsWeapon.getTotalPvPDamage() + itemSetBonusEffectStats.getPvPDamage();
      double totalPvEDamage = itemStatsWeapon.getTotalPvEDamage() + itemSetBonusEffectStats.getPvEDamage();
      double totalCriticalChance = itemStatsWeapon.getTotalCriticalChance() + itemSetBonusEffectStats.getCriticalChance();
      double totalCriticalDamage = itemStatsWeapon.getTotalCriticalDamage() + itemSetBonusEffectStats.getCriticalDamage();
      double totalAttackAoERadius = itemStatsWeapon.getTotalAttackAoERadius() + itemSetBonusEffectStats.getAttackAoERadius();
      double totalAttackAoEDamage = itemStatsWeapon.getTotalAttackAoEDamage() + itemSetBonusEffectStats.getAttackAoEDamage();
      double totalHitRate = itemStatsWeapon.getTotalHitRate() + itemSetBonusEffectStats.getHitRate();
      double totalDefense = (itemStatsArmor.getTotalDefense() + itemSetBonusEffectStats.getAdditionalDefense()) * ((100.0D + itemSetBonusEffectStats.getPercentDefense()) / 100.0D);
      double totalPvPDefense = itemStatsArmor.getTotalPvPDefense() + itemSetBonusEffectStats.getPvPDefense();
      double totalPvEDefense = itemStatsArmor.getTotalPvEDefense() + itemSetBonusEffectStats.getPvEDefense();
      double totalHealth = itemStatsArmor.getTotalHealth() + itemSetBonusEffectStats.getHealth();
      double totalHealthRegen = itemStatsArmor.getTotalHealthRegen() + itemSetBonusEffectStats.getHealthRegen();
      double totalStaminaMax = itemStatsArmor.getTotalStaminaMax() + itemSetBonusEffectStats.getStaminaMax();
      double totalStaminaRegen = itemStatsArmor.getTotalStaminaRegen() + itemSetBonusEffectStats.getStaminaRegen();
      double totalBlockAmount = itemStatsArmor.getTotalBlockAmount() + itemSetBonusEffectStats.getBlockAmount();
      double totalBlockRate = itemStatsArmor.getTotalBlockRate() + itemSetBonusEffectStats.getBlockRate();
      double totalDodgeRate = itemStatsArmor.getTotalDodgeRate() + itemSetBonusEffectStats.getDodgeRate();
      List<String> loreAbilityDataWeapon = new ArrayList();
      List<String> loreElementDataWeapon = new ArrayList();
      List<String> loreElementDataArmor = new ArrayList();
      String loreDamage = String.valueOf(MathUtil.roundNumber(totalDamageMin));
      List<String> loreInformation = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Stats_Information");
      List<String> loreAttack = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Stats_Attack");
      List<String> loreDefense = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Stats_Defense");
      List<String> loreAbility = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Stats_Ability");
      List<String> loreElement = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Stats_Element");
      Iterator var123 = itemSetBonusEffectEntity.getAllAbilityWeapon().iterator();

      AbilityWeapon abilityWeapon;
      int abilityGrade;
      while(var123.hasNext()) {
         abilityWeapon = (AbilityWeapon)var123.next();
         int grade = itemSetBonusEffectEntity.getGradeAbilityWeapon(abilityWeapon);
         abilityGrade = abilityWeapon.getMaxGrade();
         if (mapAbilityWeapon.containsKey(abilityWeapon)) {
            int totalGrade = (Integer)mapAbilityWeapon.get(abilityWeapon) + grade;
            mapAbilityWeapon.put(abilityWeapon, Math.min(abilityGrade, totalGrade));
         } else {
            mapAbilityWeapon.put(abilityWeapon, Math.min(abilityGrade, grade));
         }
      }

      var123 = mapAbilityWeapon.keySet().iterator();

      String lineElementDataArmor;
      String lineElementEmpty;
      while(var123.hasNext()) {
         abilityWeapon = (AbilityWeapon)var123.next();
         lineElementDataArmor = abilityWeapon.getID();
         abilityGrade = (Integer)mapAbilityWeapon.get(abilityWeapon);
         lineElementEmpty = lang.getText((LivingEntity)player, "Menu_Item_Format_Stats_Ability_Data");
         mapData.clear();
         mapData.put("ability_type", lineElementDataArmor);
         mapData.put("ability_grade", RomanNumber.getRomanNumber(abilityGrade));
         lineElementEmpty = TextUtil.placeholder(mapData, lineElementEmpty);
         loreAbilityDataWeapon.add(lineElementEmpty);
      }

      var123 = mapElementWeapon.keySet().iterator();

      String lineAbilityDataWeapon;
      double elementValue;
      while(var123.hasNext()) {
         lineAbilityDataWeapon = (String)var123.next();
         elementValue = (Double)mapElementWeapon.get(lineAbilityDataWeapon);
         lineElementEmpty = lang.getText((LivingEntity)player, "Menu_Item_Format_Stats_Element_Data");
         mapData.clear();
         mapData.put("element_type", lineAbilityDataWeapon);
         mapData.put("element_value", String.valueOf(elementValue));
         lineElementEmpty = TextUtil.placeholder(mapData, lineElementEmpty);
         loreElementDataWeapon.add(lineElementEmpty);
      }

      var123 = mapElementArmor.keySet().iterator();

      while(var123.hasNext()) {
         lineAbilityDataWeapon = (String)var123.next();
         elementValue = (Double)mapElementArmor.get(lineAbilityDataWeapon);
         lineElementEmpty = lang.getText((LivingEntity)player, "Menu_Item_Format_Stats_Element_Data");
         mapData.clear();
         mapData.put("element_type", lineAbilityDataWeapon);
         mapData.put("element_value", String.valueOf(elementValue));
         lineElementEmpty = TextUtil.placeholder(mapData, lineElementEmpty);
         loreElementDataArmor.add(lineElementEmpty);
      }

      if (totalDamageMin != totalDamageMax) {
         loreDamage = lang.getText((LivingEntity)player, "Menu_Item_Format_Stats_Attack_Damage_Range");
         mapData.clear();
         mapData.put("stats_damage_min", String.valueOf(MathUtil.roundNumber(totalDamageMin)));
         mapData.put("stats_damage_max", String.valueOf(MathUtil.roundNumber(totalDamageMax)));
         loreDamage = TextUtil.placeholder(mapData, loreDamage);
      }

      lineAbilityDataWeapon = TextUtil.convertListToString(loreAbilityDataWeapon, "\n");
      String lineElementDataWeapon = TextUtil.convertListToString(loreElementDataWeapon, "\n");
      lineElementDataArmor = TextUtil.convertListToString(loreElementDataArmor, "\n");
      String lineAbilityEmpty = lang.getText((LivingEntity)player, "Menu_Item_Format_Stats_Ability_Empty");
      lineElementEmpty = lang.getText((LivingEntity)player, "Menu_Item_Format_Stats_Element_Empty");
      map.put("stats_damage", loreDamage);
      map.put("stats_penetration", String.valueOf(MathUtil.roundNumber(totalPenetration)));
      map.put("stats_pvp_damage", String.valueOf(MathUtil.roundNumber(totalPvPDamage)));
      map.put("stats_pve_damage", String.valueOf(MathUtil.roundNumber(totalPvEDamage)));
      map.put("stats_critical_chance", String.valueOf(MathUtil.roundNumber(totalCriticalChance)));
      map.put("stats_critical_damage", String.valueOf(MathUtil.roundNumber(totalCriticalDamage)));
      map.put("stats_aoe_radius", String.valueOf(MathUtil.roundNumber(totalAttackAoERadius)));
      map.put("stats_aoe_damage", String.valueOf(MathUtil.roundNumber(totalAttackAoEDamage)));
      map.put("stats_hit_rate", String.valueOf(MathUtil.roundNumber(totalHitRate)));
      map.put("stats_defense", String.valueOf(MathUtil.roundNumber(totalDefense)));
      map.put("stats_pvp_defense", String.valueOf(MathUtil.roundNumber(totalPvPDefense)));
      map.put("stats_pve_defense", String.valueOf(MathUtil.roundNumber(totalPvEDefense)));
      map.put("stats_health", String.valueOf(MathUtil.roundNumber(totalHealth)));
      map.put("stats_health_regen", String.valueOf(MathUtil.roundNumber(totalHealthRegen)));
      map.put("stats_stamina_max", String.valueOf(MathUtil.roundNumber(totalStaminaMax)));
      map.put("stats_stamina_regen", String.valueOf(MathUtil.roundNumber(totalStaminaRegen)));
      map.put("stats_block_amount", String.valueOf(MathUtil.roundNumber(totalBlockAmount)));
      map.put("stats_block_rate", String.valueOf(MathUtil.roundNumber(totalBlockRate)));
      map.put("stats_dodge_rate", String.valueOf(MathUtil.roundNumber(totalDodgeRate)));
      map.put("ability_base_additional_damage", String.valueOf(MathUtil.roundNumber(abilityBaseBonusDamage)));
      map.put("ability_base_percent_damage", String.valueOf(MathUtil.roundNumber(abilityBasePercentDamage)));
      map.put("ability_cast_additional_damage", String.valueOf(MathUtil.roundNumber(abilityCastBonusDamage)));
      map.put("ability_cast_percent_damage", String.valueOf(MathUtil.roundNumber(abilityCastPercentDamage)));
      map.put("element_additional_damage", String.valueOf(MathUtil.roundNumber(elementAdditionalDamage)));
      map.put("element_percent_damage", String.valueOf(MathUtil.roundNumber(elementPercentDamage)));
      map.put("ability_data", mapAbilityWeapon.isEmpty() ? lineAbilityEmpty : lineAbilityDataWeapon);
      map.put("ability_data_weapon", mapAbilityWeapon.isEmpty() ? lineAbilityEmpty : lineAbilityDataWeapon);
      map.put("element_data_weapon", mapElementWeapon.isEmpty() ? lineElementEmpty : lineElementDataWeapon);
      map.put("element_data_armor", mapElementArmor.isEmpty() ? lineElementEmpty : lineElementDataArmor);
      loreInformation = TextUtil.placeholder(map, loreInformation);
      loreAttack = TextUtil.placeholder(map, loreAttack);
      loreDefense = TextUtil.placeholder(map, loreDefense);
      loreAbility = TextUtil.placeholder(map, loreAbility);
      loreElement = TextUtil.placeholder(map, loreElement);
      loreInformation = TextUtil.expandList(loreInformation, "\n");
      loreAttack = TextUtil.expandList(loreAttack, "\n");
      loreDefense = TextUtil.expandList(loreDefense, "\n");
      loreAbility = TextUtil.expandList(loreAbility, "\n");
      loreElement = TextUtil.expandList(loreElement, "\n");
      ItemStack itemSlotHelmet = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerSlotHelmet, 1);
      ItemStack itemSlotChestplate = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerSlotChestplate, 1);
      ItemStack itemSlotLeggings = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerSlotLeggings, 1);
      ItemStack itemSlotBoots = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerSlotBoots, 1);
      ItemStack itemSlotMainHand = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerSlotMainHand, 1);
      ItemStack itemSlotOffHand = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerSlotOffHand, 1);
      ItemStack itemInformation = EquipmentUtil.createItem(MaterialEnum.SIGN, headerInformation, 1, loreInformation);
      ItemStack itemAttack = EquipmentUtil.createItem(MaterialEnum.IRON_SWORD, headerAttack, 1, loreAttack);
      ItemStack itemDefense = EquipmentUtil.createItem(MaterialEnum.IRON_CHESTPLATE, headerDefense, 1, loreDefense);
      ItemStack itemAbility = EquipmentUtil.createItem(MaterialEnum.BLAZE_POWDER, headerAbility, 1, loreAbility);
      ItemStack itemElement = EquipmentUtil.createItem(MaterialEnum.MAGMA_CREAM, headerElement, 1, loreElement);
      EquipmentUtil.addFlag(itemAttack, new FlagEnum[]{FlagEnum.HIDE_ATTRIBUTES});
      EquipmentUtil.addFlag(itemDefense, new FlagEnum[]{FlagEnum.HIDE_ATTRIBUTES});
      menuSlotEquipmentHelmet.setItem(EquipmentUtil.isSolid(itemEquipmentHelmet) ? itemEquipmentHelmet : itemSlotHelmet);
      menuSlotEquipmentChestplate.setItem(EquipmentUtil.isSolid(itemEquipmentChestplate) ? itemEquipmentChestplate : itemSlotChestplate);
      menuSlotEquipmentLeggings.setItem(EquipmentUtil.isSolid(itemEquipmentLeggings) ? itemEquipmentLeggings : itemSlotLeggings);
      menuSlotEquipmentBoots.setItem(EquipmentUtil.isSolid(itemEquipmentBoots) ? itemEquipmentBoots : itemSlotBoots);
      menuSlotEquipmentMainHand.setItem(EquipmentUtil.isSolid(itemEquipmentMainHand) ? itemEquipmentMainHand : itemSlotMainHand);
      menuSlotEquipmentOffHand.setItem(EquipmentUtil.isSolid(itemEquipmentOffHand) ? itemEquipmentOffHand : itemSlotOffHand);
      menuSlotInformation.setItem(itemInformation);
      menuSlotAttack.setItem(itemAttack);
      menuSlotDefense.setItem(itemDefense);
      menuSlotAbility.setItem(itemAbility);
      menuSlotElement.setItem(itemElement);
      menuGUI.setMenuSlot(menuSlotEquipmentHelmet);
      menuGUI.setMenuSlot(menuSlotEquipmentChestplate);
      menuGUI.setMenuSlot(menuSlotEquipmentLeggings);
      menuGUI.setMenuSlot(menuSlotEquipmentBoots);
      menuGUI.setMenuSlot(menuSlotEquipmentMainHand);
      menuGUI.setMenuSlot(menuSlotEquipmentOffHand);
      menuGUI.setMenuSlot(menuSlotInformation);
      menuGUI.setMenuSlot(menuSlotAttack);
      menuGUI.setMenuSlot(menuSlotDefense);
      menuGUI.setMenuSlot(menuSlotAbility);
      menuGUI.setMenuSlot(menuSlotElement);
      player.updateInventory();
   }
}
