package com.praya.myitems.listener.custom;

import api.praya.agarthalib.builder.event.MenuCloseEvent;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.builder.menu.Menu;
import core.praya.agarthalib.builder.menu.MenuGUI;
import core.praya.agarthalib.builder.menu.MenuGUI.SlotCell;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ListenerMenuClose extends HandlerEvent implements Listener {
   public ListenerMenuClose(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void menuCloseEvent(MenuCloseEvent event) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      Menu menu = event.getMenu();
      Player player = event.getPlayer();
      String id = menu.getID();
      if (menu instanceof MenuGUI) {
         MenuGUI menuGUI = (MenuGUI)menu;
         if (id.equalsIgnoreCase("MyItems Socket")) {
            Inventory inventory = menuGUI.getInventory();
            SlotCell cellItemInput = SlotCell.B3;
            SlotCell cellSocketInput = SlotCell.C3;
            String headerItemInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Item_Input");
            String headerSocketInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Socket_Input");
            ItemStack itemItemInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerItemInput, 1);
            ItemStack itemSocketInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerSocketInput, 1);
            ItemStack itemItem = inventory.getItem(cellItemInput.getIndex());
            ItemStack itemSocket = inventory.getItem(cellSocketInput.getIndex());
            if (!itemItem.isSimilar(itemItemInput)) {
               PlayerUtil.addItem(player, itemItem);
            }

            if (!itemSocket.isSimilar(itemSocketInput)) {
               PlayerUtil.addItem(player, itemSocket);
            }
         }
      }

   }
}
