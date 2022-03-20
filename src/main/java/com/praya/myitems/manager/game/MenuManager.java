package com.praya.myitems.manager.game;

import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MetadataUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PlaceholderManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.menu.MenuSocket;
import com.praya.myitems.menu.MenuStats;
import core.praya.agarthalib.builder.menu.Menu;
import core.praya.agarthalib.builder.menu.MenuExecutor;
import core.praya.agarthalib.builder.menu.MenuGUI;
import core.praya.agarthalib.builder.menu.MenuSlot;
import core.praya.agarthalib.builder.menu.MenuGUI.SlotCell;
import core.praya.agarthalib.builder.menu.MenuSlotAction.ActionCategory;
import core.praya.agarthalib.builder.text.Text;
import core.praya.agarthalib.builder.text.TextLine;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MenuManager extends HandlerManager {
   private final MenuExecutor executorSocket;
   private final MenuExecutor executorStats;

   protected MenuManager(MyItems plugin) {
      super(plugin);
      this.executorSocket = new MenuSocket(plugin);
      this.executorStats = new MenuStats(plugin);
   }

   public final void openMenuSocket(Player player) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      PlaceholderManager placeholderManager = pluginManager.getPlaceholderManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      MenuExecutor executor = this.executorSocket;
      String metadataID = "MyItems Socket Line_Selector";
      String id = "MyItems Socket";
      String textTitle = lang.getText((LivingEntity)player, "Menu_Page_Title_Socket");
      int row;
      Text title = new TextLine(textTitle);
      String prefix = placeholderManager.getPlaceholder("prefix");
      String headerItemInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Item_Input");
      String headerSocketInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Socket_Input");
      String headerItemInformation = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Item_Information");
      String headerSocketInformation = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Socket_Information");
      String headerAccept = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Accept");
      String headerCancel = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Cancel");
      SlotCell cellItemInput = SlotCell.B3;
      SlotCell cellSocketInput = SlotCell.C3;
      SlotCell cellItemInformation = SlotCell.B2;
      SlotCell cellSocketInformation = SlotCell.C2;
      SlotCell cellAccept = SlotCell.H3;
      SlotCell cellCancel = SlotCell.F3;
      MenuSlot menuSlotItemInput = new MenuSlot(cellItemInput.getIndex());
      MenuSlot menuSlotSocketInput = new MenuSlot(cellSocketInput.getIndex());
      MenuSlot menuSlotItemInformation = new MenuSlot(cellItemInformation.getIndex());
      MenuSlot menuSlotSocketInformation = new MenuSlot(cellSocketInformation.getIndex());
      MenuSlot menuSlotAccept = new MenuSlot(cellAccept.getIndex());
      MenuSlot menuSlotCancel = new MenuSlot(cellCancel.getIndex());
      Map<Integer, MenuSlot> mapSlot = new ConcurrentHashMap();
      List<String> loreItemInformation = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Socket_Item_Information");
      List<String> loreSocketInformation = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Socket_Socket_Information");
      List<String> loreAccept = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Socket_Accept");
      List<String> loreCancel = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Socket_Cancel");
      ItemStack itemPaneBlack = EquipmentUtil.createItem(MaterialEnum.BLACK_STAINED_GLASS_PANE, prefix, 1);
      ItemStack itemItemInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerItemInput, 1);
      ItemStack itemSocketInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerSocketInput, 1);
      ItemStack itemItemInformation = EquipmentUtil.createItem(MaterialEnum.SIGN, headerItemInformation, 1, loreItemInformation);
      ItemStack itemSocketInformation = EquipmentUtil.createItem(MaterialEnum.SIGN, headerSocketInformation, 1, loreSocketInformation);
      ItemStack itemAccept = EquipmentUtil.createItem(MaterialEnum.GREEN_WOOL, headerAccept, 1, loreAccept);
      ItemStack itemCancel = EquipmentUtil.createItem(MaterialEnum.RED_WOOL, headerCancel, 1, loreCancel);

      for(int index = 0; index < 36; ++index) {
         MenuSlot menuSlot = new MenuSlot(index);
         menuSlot.setItem(itemPaneBlack);
         mapSlot.put(index, menuSlot);
      }

      menuSlotItemInput.setItem(itemItemInput);
      menuSlotSocketInput.setItem(itemSocketInput);
      menuSlotItemInformation.setItem(itemItemInformation);
      menuSlotSocketInformation.setItem(itemSocketInformation);
      menuSlotAccept.setItem(itemAccept);
      menuSlotCancel.setItem(itemCancel);
      menuSlotItemInput.setActionArguments(ActionCategory.ALL_CLICK, "MyItems Socket Item_Input " + cellItemInput.toString());
      menuSlotSocketInput.setActionArguments(ActionCategory.ALL_CLICK, "MyItems Socket Socket_Input " + cellSocketInput.toString());
      menuSlotAccept.setActionArguments(ActionCategory.ALL_CLICK, "MyItems Socket Accept");
      menuSlotCancel.setActionClosed(ActionCategory.ALL_CLICK, true);
      menuSlotAccept.setActionClosed(ActionCategory.ALL_CLICK, true);
      mapSlot.put(menuSlotItemInput.getSlot(), menuSlotItemInput);
      mapSlot.put(menuSlotSocketInput.getSlot(), menuSlotSocketInput);
      mapSlot.put(menuSlotItemInformation.getSlot(), menuSlotItemInformation);
      mapSlot.put(menuSlotSocketInformation.getSlot(), menuSlotSocketInformation);
      mapSlot.put(menuSlotAccept.getSlot(), menuSlotAccept);
      mapSlot.put(menuSlotCancel.getSlot(), menuSlotCancel);
      Menu menu = new MenuGUI((Player)null, "MyItems Socket", 4, title, executor, false, mapSlot);
      MetadataUtil.removeMetadata(player, "MyItems Socket Line_Selector");
      Menu.openMenu(player, menu);
   }

   public final void openMenuStats(Player player) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      PlaceholderManager placeholderManager = pluginManager.getPlaceholderManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      MenuExecutor executor = this.executorStats;
      String id = "MyItems Stats";
      String textTitle = lang.getText((LivingEntity)player, "Menu_Page_Title_Stats");
      int row;
      Text title = new TextLine(textTitle);
      String prefix = placeholderManager.getPlaceholder("prefix");
      int[] arrayPaneWhite = new int[]{0, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 53};
      Map<Integer, MenuSlot> mapSlot = new ConcurrentHashMap();
      ItemStack itemPaneBlack = EquipmentUtil.createItem(MaterialEnum.BLACK_STAINED_GLASS_PANE, prefix, 1);
      ItemStack itemPaneWhite = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, prefix, 1);

      int index;
      for(index = 0; index < 54; ++index) {
         MenuSlot menuSlot = new MenuSlot(index);
         menuSlot.setItem(itemPaneBlack);
         mapSlot.put(index, menuSlot);
      }

      int[] var18 = arrayPaneWhite;
      int var17 = arrayPaneWhite.length;

      for(int var21 = 0; var21 < var17; ++var21) {
         index = var18[var21];
         MenuSlot menuSlot = new MenuSlot(index);
         menuSlot.setItem(itemPaneWhite);
         mapSlot.put(index, menuSlot);
      }

      MenuGUI menuGUI = new MenuGUI((Player)null, "MyItems Stats", 6, title, executor, false, mapSlot);
      Menu.openMenu(player, menuGUI);
   }
}
