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
import java.util.Iterator;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TabCompleterEnchantmentRemove extends HandlerTabCompleter implements TabCompleter {
   public TabCompleterEnchantmentRemove(MyItems plugin) {
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
         ItemMeta itemmeta = item.getItemMeta();
         if (args.length == 1 && commandManager.checkPermission(sender, "Enchant_Remove") && itemmeta.hasEnchants()) {
            Iterator var12 = itemmeta.getEnchants().keySet().iterator();

            while(var12.hasNext()) {
               Enchantment enchant = (Enchantment)var12.next();
               tabList.add(enchant.getName());
            }
         }
      }

      return TabCompleterUtil.returnList(tabList, args);
   }
}
