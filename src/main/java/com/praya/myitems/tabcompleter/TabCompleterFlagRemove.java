package com.praya.myitems.tabcompleter;

import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.TabCompleterUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerTabCompleter;
import com.praya.myitems.manager.plugin.CommandManager;
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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TabCompleterFlagRemove extends HandlerTabCompleter implements TabCompleter {
   public TabCompleterFlagRemove(MyItems plugin) {
      super(plugin);
   }

   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      List<String> tabList = new ArrayList();
      SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
      if (SenderUtil.isPlayer(sender)) {
         Player player = PlayerUtil.parse(sender);
         ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
         ItemMeta meta = item.getItemMeta();
         if (args.length == 1 && commandManager.checkPermission(sender, "Flag_Remove")) {
            ItemFlag[] var14;
            int var13 = (var14 = ItemFlag.values()).length;

            for(int var12 = 0; var12 < var13; ++var12) {
               ItemFlag flag = var14[var12];
               if (meta.hasItemFlag(flag)) {
                  tabList.add(flag.toString());
               }
            }
         }
      }

      return TabCompleterUtil.returnList(tabList, args);
   }
}
