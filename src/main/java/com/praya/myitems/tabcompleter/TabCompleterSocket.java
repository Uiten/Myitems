package com.praya.myitems.tabcompleter;

import com.praya.agarthalib.utility.ListUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.TabCompleterUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerTabCompleter;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabCompleterSocket extends HandlerTabCompleter implements TabCompleter {
   public TabCompleterSocket(MyItems plugin) {
      super(plugin);
   }

   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      GameManager gameManager = this.plugin.getGameManager();
      SocketManager socketManager = gameManager.getSocketManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      List<String> tabList = new ArrayList();
      SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
      if (args.length == 1) {
         if (commandManager.checkPermission(sender, "Socket_Add")) {
            tabList.add("Add");
         }

         if (commandManager.checkPermission(sender, "Socket_Load")) {
            tabList.add("Load");
         }

         if (commandManager.checkPermission(sender, "Socket_Drop")) {
            tabList.add("Drop");
         }

         if (commandManager.checkPermission(sender, "Socket_List")) {
            tabList.add("List");
         }
      } else {
         String argument1;
         if (args.length == 2) {
            argument1 = args[0];
            if (commandManager.checkCommand(argument1, "Socket_Add")) {
               if (commandManager.checkPermission(sender, "Socket_Add")) {
                  tabList.add("Empty");
                  tabList.add("Locked");
               }
            } else if (commandManager.checkCommand(argument1, "Socket_Load")) {
               if (commandManager.checkPermission(sender, "Socket_Load")) {
                  tabList.add("Gems");
                  tabList.add("Rod");
               }
            } else if (commandManager.checkCommand(argument1, "Socket_Drop") && commandManager.checkPermission(sender, "Socket_Drop")) {
               tabList.add("Gems");
               tabList.add("Rod");
            }
         } else {
            String argument2;
            if (args.length == 3) {
               argument1 = args[0];
               argument2 = args[1];
               Collection gems;
               if (commandManager.checkCommand(argument1, "Socket_Load")) {
                  if (commandManager.checkPermission(sender, "Socket_Load")) {
                     if (commandManager.checkCommand(argument2, "Socket_Load_Gems")) {
                        if (commandManager.checkPermission(sender, "Socket_Load_Gems")) {
                           gems = socketManager.getSocketIDs();
                           if (gems.isEmpty()) {
                              tabList.add("");
                           } else {
                              tabList.addAll(gems);
                           }
                        }
                     } else if (commandManager.checkCommand(argument2, "Socket_Load_Rod") && commandManager.checkPermission(sender, "Socket_Load_Rod")) {
                        tabList.add("Unlock");
                        tabList.add("Remove");
                     }
                  }
               } else if (commandManager.checkCommand(argument1, "Socket_Drop") && commandManager.checkPermission(sender, "Socket_Drop")) {
                  if (commandManager.checkCommand(argument2, "Socket_Drop_Gems")) {
                     if (commandManager.checkPermission(sender, "Socket_Drop_Gems")) {
                        gems = socketManager.getSocketIDs();
                        if (gems.isEmpty()) {
                           tabList.add("");
                        } else {
                           tabList.addAll(gems);
                        }
                     }
                  } else if (commandManager.checkCommand(argument2, "Socket_Drop_Rod") && commandManager.checkPermission(sender, "Socket_Drop_Rod")) {
                     tabList.add("Unlock");
                     tabList.add("Remove");
                  }
               }
            } else {
               int page;
               ArrayList keyList;
               World world;
               Iterator var16;
               String argument5;
               if (args.length == 4) {
                  argument1 = args[0];
                  argument2 = args[1];
                  argument5 = args[3];
                  if (commandManager.checkCommand(argument1, "Socket_Drop") && commandManager.checkPermission(sender, "Socket_Drop") && commandManager.checkCommand(argument2, "Socket_Drop_Rod") && commandManager.checkPermission(sender, "Socket_Drop_Rod")) {
                     page = MathUtil.isNumber(argument5) ? MathUtil.parseInteger(argument5) : 1;
                     keyList = new ArrayList();
                     var16 = Bukkit.getWorlds().iterator();

                     while(var16.hasNext()) {
                        world = (World)var16.next();
                        keyList.add(world.getName());
                     }

                     return ListUtil.sendList(sender, keyList, page);
                  }
               } else if (args.length == 5) {
                  argument1 = args[0];
                  argument2 = args[1];
                  argument5 = args[4];
                  if (commandManager.checkCommand(argument1, "Socket_Drop") && commandManager.checkPermission(sender, "Socket_Drop") && commandManager.checkCommand(argument2, "Socket_Drop_Gems") && commandManager.checkPermission(sender, "Socket_Drop_Gems")) {
                     page = MathUtil.isNumber(argument5) ? MathUtil.parseInteger(argument5) : 1;
                     keyList = new ArrayList();
                     var16 = Bukkit.getWorlds().iterator();

                     while(var16.hasNext()) {
                        world = (World)var16.next();
                        keyList.add(world.getName());
                     }

                     return ListUtil.sendList(sender, keyList, page);
                  }
               }
            }
         }
      }

      return TabCompleterUtil.returnList(tabList, args);
   }
}
