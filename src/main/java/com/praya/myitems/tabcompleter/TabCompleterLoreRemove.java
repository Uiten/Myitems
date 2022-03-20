package com.praya.myitems.tabcompleter;

import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.TabCompleterUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerTabCompleter;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TabCompleterLoreRemove extends HandlerTabCompleter implements TabCompleter {
   public TabCompleterLoreRemove(MyItems plugin) {
      super(plugin);
   }

   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      List<String> tabList = new ArrayList();
      SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
      if (SenderUtil.isPlayer(sender)) {
         Player player = PlayerUtil.parse(sender);
         ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
         ItemMeta meta = item.getItemMeta();
         if (args.length == 1) {
            if (meta.hasLore()) {
               if (commandManager.checkPermission(sender, "Lore_Remove")) {
                  for(int t = 1; t <= meta.getLore().size(); ++t) {
                     tabList.add(String.valueOf(t));
                  }
               }
            } else {
               String message = lang.getText(sender, "MyItems_RemoveLore_Empty");
               tabList.add("");
               SenderUtil.sendMessage(sender, message);
            }
         }
      }

      return TabCompleterUtil.returnList(tabList, args);
   }
}
