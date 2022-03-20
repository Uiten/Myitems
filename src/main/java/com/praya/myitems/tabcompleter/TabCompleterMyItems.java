package com.praya.myitems.tabcompleter;

import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.TabCompleterUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerTabCompleter;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemGeneratorManager;
import com.praya.myitems.manager.game.ItemManager;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.builder.message.MessageBuild;
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

public class TabCompleterMyItems extends HandlerTabCompleter implements TabCompleter {
   public TabCompleterMyItems(MyItems plugin) {
      super(plugin);
   }

   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      GameManager gameManager = this.plugin.getGameManager();
      ItemManager itemManager = gameManager.getItemManager();
      ItemGeneratorManager itemGeneratorManager = gameManager.getItemGeneratorManager();
      ItemSetManager itemSetManager = gameManager.getItemSetManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      List<String> tabList = new ArrayList();
      SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
      if (args.length == 1) {
         if (commandManager.checkPermission(sender, "MyItems_Help")) {
            tabList.add("Help");
         }

         if (commandManager.checkPermission(sender, "MyItems_About")) {
            tabList.add("About");
         }

         if (commandManager.checkPermission(sender, "MyItems_Reload")) {
            tabList.add("Reload");
         }

         if (commandManager.checkPermission(sender, "MyItems_Detail")) {
            tabList.add("Detail");
         }

         if (commandManager.checkPermission(sender, "MyItems_Data")) {
            tabList.add("Data");
         }

         if (commandManager.checkPermission(sender, "MyItems_Amount")) {
            tabList.add("Amount");
         }

         if (commandManager.checkPermission(sender, "MyItems_Material")) {
            tabList.add("Material");
         }

         if (commandManager.checkPermission(sender, "MyItems_Save")) {
            tabList.add("Save");
         }

         if (commandManager.checkPermission(sender, "MyItems_Load")) {
            tabList.add("Load");
         }

         if (commandManager.checkPermission(sender, "MyItems_Drop")) {
            tabList.add("Drop");
         }

         if (commandManager.checkPermission(sender, "MyItems_Remove")) {
            tabList.add("Remove");
         }

         if (commandManager.checkPermission(sender, "MyItems_Repair")) {
            tabList.add("Repair");
         }

         if (commandManager.checkPermission(sender, "MyItems_Simulation")) {
            tabList.add("Simulation");
         }
      } else {
         String argument1;
         String argument2;
         Collection itemIDs;
         if (args.length == 2) {
            argument1 = args[0];
            if (commandManager.checkCommand(argument1, "MyItems_Save")) {
               if (commandManager.checkPermission(sender, "MyItems_Save")) {
                  argument2 = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Save"));
                  MessageBuild message = lang.getMessage(sender, "Argument_MyItems_Save");
                  message.sendMessage(sender, "tooltip_save", argument2);
               }
            } else if (commandManager.checkCommand(argument1, "MyItems_Load")) {
               if (commandManager.checkPermission(sender, "MyItems_Load")) {
                  tabList.add("Custom");
                  tabList.add("Generator");
                  tabList.add("Set");
               }
            } else if (commandManager.checkCommand(argument1, "MyItems_Drop")) {
               if (commandManager.checkPermission(sender, "MyItems_Drop")) {
                  tabList.add("Custom");
                  tabList.add("Generator");
                  tabList.add("Set");
               }
            } else if (commandManager.checkCommand(argument1, "MyItems_Remove") && commandManager.checkPermission(sender, "MyItems_Remove")) {
               itemIDs = itemManager.getItemIDs();
               tabList.addAll(itemIDs);
            }
         } else if (args.length == 3) {
            argument1 = args[0];
            argument2 = args[1];
            if (commandManager.checkCommand(argument1, "MyItems_Load")) {
               if (commandManager.checkPermission(sender, "MyItems_Load")) {
                  if (commandManager.checkCommand(argument2, "MyItems_Load_Custom")) {
                     itemIDs = itemManager.getItemIDs();
                     tabList.addAll(itemIDs);
                  } else if (commandManager.checkCommand(argument2, "MyItems_Load_Generator")) {
                     itemIDs = itemGeneratorManager.getItemGeneratorIDs();
                     tabList.addAll(itemIDs);
                  } else if (commandManager.checkCommand(argument2, "MyItems_Load_Set")) {
                     itemIDs = itemSetManager.getItemComponentIDs();
                     tabList.addAll(itemIDs);
                  }
               }
            } else if (commandManager.checkCommand(argument1, "MyItems_Drop") && commandManager.checkPermission(sender, "MyItems_Drop")) {
               if (commandManager.checkCommand(argument2, "MyItems_Drop_Custom")) {
                  itemIDs = itemManager.getItemIDs();
                  tabList.addAll(itemIDs);
               } else if (commandManager.checkCommand(argument2, "MyItems_Drop_Generator")) {
                  itemIDs = itemGeneratorManager.getItemGeneratorIDs();
                  tabList.addAll(itemIDs);
               } else if (commandManager.checkCommand(argument2, "MyItems_Load_Set")) {
                  itemIDs = itemSetManager.getItemComponentIDs();
                  tabList.addAll(itemIDs);
               }
            }
         } else if (args.length == 4) {
            argument1 = args[0];
            if (commandManager.checkCommand(argument1, "MyItems_Drop") && commandManager.checkPermission(sender, "MyItems_Drop")) {
               itemIDs = itemSetManager.getItemComponentIDs();
               Iterator var16 = Bukkit.getWorlds().iterator();

               while(var16.hasNext()) {
                  World world = (World)var16.next();
                  String worldName = world.getName();
                  itemIDs.add(worldName);
               }

               tabList.addAll(itemIDs);
            }
         }
      }

      return TabCompleterUtil.returnList(tabList, args);
   }
}
