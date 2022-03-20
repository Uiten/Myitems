package com.praya.myitems.tabcompleter;

import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.TabCompleterUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerTabCompleter;
import com.praya.myitems.manager.plugin.LanguageManager;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabCompleterNotCompatible extends HandlerTabCompleter implements TabCompleter {
   public TabCompleterNotCompatible(MyItems plugin) {
      super(plugin);
   }

   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
      LanguageManager lang = this.plugin.getPluginManager().getLanguageManager();
      String message = lang.getText(sender, "MyItems_Not_Compatible");
      List<String> tabList = new ArrayList();
      SenderUtil.sendMessage(sender, message);
      SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
      return TabCompleterUtil.returnList(tabList, args);
   }
}
