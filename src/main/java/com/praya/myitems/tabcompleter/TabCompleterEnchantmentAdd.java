package com.praya.myitems.tabcompleter;

import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.TabCompleterUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerTabCompleter;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;

public class TabCompleterEnchantmentAdd extends HandlerTabCompleter implements TabCompleter {
   public TabCompleterEnchantmentAdd(MyItems plugin) {
      super(plugin);
   }

   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      List<String> tabList = new ArrayList();
      SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
      if (SenderUtil.isPlayer(sender) && args.length == 1 && commandManager.checkPermission(sender, "Enchant_Add")) {
         Enchantment[] var11;
         int var10 = (var11 = Enchantment.values()).length;

         for(int var9 = 0; var9 < var10; ++var9) {
            Enchantment enchantment = var11[var9];
            tabList.add(enchantment.getName());
         }
      }

      return TabCompleterUtil.returnList(tabList, args);
   }
}
