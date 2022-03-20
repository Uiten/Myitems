package com.praya.myitems.command;

import api.praya.myitems.builder.item.ItemGenerator;
import api.praya.myitems.builder.item.ItemSet;
import api.praya.myitems.builder.item.ItemSetComponent;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.JsonUtil;
import com.praya.agarthalib.utility.MaterialUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.SortUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.agarthalib.utility.WorldUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerCommand;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemGeneratorManager;
import com.praya.myitems.manager.game.ItemManager;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.MenuManager;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PlaceholderManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.manager.plugin.PluginPropertiesManager;
import com.praya.myitems.manager.task.TaskManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.builder.plugin.PluginPropertiesResourceBuild;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandMyItems extends HandlerCommand implements CommandExecutor {
   public CommandMyItems(MyItems plugin) {
      super(plugin);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      GameManager gameManager = this.plugin.getGameManager();
      TaskManager taskManager = this.plugin.getTaskManager();
      MenuManager menuManager = gameManager.getMenuManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      ItemManager itemManager = gameManager.getItemManager();
      ItemGeneratorManager itemGeneratorManager = gameManager.getItemGeneratorManager();
      ItemSetManager itemSetManager = gameManager.getItemSetManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      PlaceholderManager placeholderManager = pluginManager.getPlaceholderManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (args.length > 0) {
         String subCommand = args[0];
         MessageBuild message;
         String textCategory;
         if (commandManager.checkCommand(subCommand, "MyItems_Reload")) {
            if (!commandManager.checkPermission(sender, "MyItems_Reload")) {
               textCategory = commandManager.getPermission("MyItems_Reload");
               message = lang.getMessage(sender, "Permission_Lack");
               message.sendMessage(sender, "permission", textCategory);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
               return true;
            } else {
               message = lang.getMessage(sender, "MyItems_Reload_Success");
               mainConfig.setup();
               pluginManager.getPlaceholderManager().getPlaceholderConfig().setup();
               pluginManager.getLanguageManager().getLangConfig().setup();
               pluginManager.getCommandManager().getCommandConfig().setup();
               gameManager.getAbilityWeaponManager().getAbilityWeaponConfig().setup();
               gameManager.getElementManager().getElementConfig().setup();
               gameManager.getPowerManager().getPowerCommandManager().getPowerCommandConfig().setup();
               gameManager.getPowerManager().getPowerSpecialManager().getPowerSpecialConfig().setup();
               gameManager.getSocketManager().getSocketConfig().setup();
               gameManager.getItemManager().getItemConfig().setup();
               gameManager.getItemTypeManager().getItemTypeConfig().setup();
               gameManager.getItemTierManager().getItemTierConfig().setup();
               gameManager.getItemGeneratorManager().getItemGeneratorConfig().setup();
               gameManager.getItemSetManager().getItemSetConfig().setup();
               taskManager.getTaskCustomEffectManager().reloadTaskCustomEffect();
               taskManager.getTaskPassiveEffectManager().reloadTaskLoadPassiveEffect();
               message.sendMessage(sender);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
               return true;
            }
         } else {
            String name;
            String detailHeader;
            String detailName;
            String textAmount;
            String detailFlagsHead;
            String textWorld;
            if (commandManager.checkCommand(subCommand, "MyItems_About")) {
               if (!commandManager.checkPermission(sender, "MyItems_About")) {
                  textCategory = commandManager.getPermission("MyItems_About");
                  message = lang.getMessage(sender, "Permission_Lack");
                  message.sendMessage(sender, "permission", textCategory);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                  return true;
               } else {
                  PluginPropertiesManager pluginPropertiesManager = pluginManager.getPluginPropertiesManager();
                  PluginPropertiesResourceBuild pluginPropertiesResource = pluginPropertiesManager.getPluginPropertiesResource();
                  name = placeholderManager.getPlaceholder("Prefix") + " ";
                  HashMap<String, String> map = new HashMap();
                  String aboutHeader = name + "&7=-=-=-=-=-=-= &6About&7 =-=-=-=-=-=-=";
                  textWorld = name + "&7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=";
                  textAmount = name;
                  String aboutPlugin = name + "Plugin&f: &c{plugin}";
                  String aboutType = name + "Type&f: &c{type}";
                  String aboutVersion = name + "Version&f: &c{version}";
                  detailHeader = name + "Author&f: &c{author}";
                  map.put("plugin", pluginPropertiesResource.getName());
                  map.put("type", pluginPropertiesResource.getType());
                  map.put("version", pluginPropertiesResource.getVersion());
                  map.put("author", pluginPropertiesResource.getAuthor());
                  map.put("company", pluginPropertiesResource.getCompany());
                  aboutHeader = TextUtil.placeholder(map, aboutHeader);
                  textWorld = TextUtil.placeholder(map, textWorld);
                  aboutPlugin = TextUtil.placeholder(map, aboutPlugin);
                  aboutType = TextUtil.placeholder(map, aboutType);
                  aboutVersion = TextUtil.placeholder(map, aboutVersion);
                  detailHeader = TextUtil.placeholder(map, detailHeader);
                  SenderUtil.sendMessage(sender, aboutHeader);
                  SenderUtil.sendMessage(sender, textAmount);
                  SenderUtil.sendMessage(sender, aboutPlugin);
                  SenderUtil.sendMessage(sender, aboutType);
                  SenderUtil.sendMessage(sender, aboutVersion);
                  SenderUtil.sendMessage(sender, detailHeader);
                  if (pluginPropertiesManager.getCompany() != null) {
                     HashMap<String, String> subMap = new HashMap();
                     textAmount = name + "Company&7: &c{company}";
                     subMap.put("company", pluginPropertiesManager.getCompany());
                     TextUtil.placeholder(subMap, textAmount);
                  }

                  if (!pluginPropertiesManager.getDevelopers().isEmpty()) {
                     detailName = name + "Developer&7:";
                     SenderUtil.sendMessage(sender, detailName);
                     Iterator var93 = pluginPropertiesManager.getDevelopers().iterator();

                     while(var93.hasNext()) {
                        textAmount = (String)var93.next();
                        HashMap<String, String> subMap = new HashMap();
                        detailFlagsHead = name + "&7&l➨ &d{developer}";
                        subMap.put("developer", textAmount);
                        detailFlagsHead = TextUtil.placeholder(subMap, detailFlagsHead);
                        SenderUtil.sendMessage(sender, detailFlagsHead);
                     }
                  }

                  SenderUtil.sendMessage(sender, textAmount);
                  SenderUtil.sendMessage(sender, textWorld);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                  return true;
               }
            } else {
               MessageBuild senderMessage;
               ItemStack item;
               MessageBuild targetMessage;
               if (commandManager.checkCommand(subCommand, "MyItems_Save")) {
                  if (!commandManager.checkPermission(sender, "MyItems_Save")) {
                     textCategory = commandManager.getPermission("MyItems_Save");
                     message = lang.getMessage(sender, "Permission_Lack");
                     message.sendMessage(sender, "permission", textCategory);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else if (!SenderUtil.isPlayer(sender)) {
                     message = lang.getMessage(sender, "Console_Command_Forbiden");
                     message.sendMessage(sender);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else if (args.length < 2) {
                     textCategory = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Save"));
                     message = lang.getMessage(sender, "Argument_MyItems_Save");
                     message.sendMessage(sender, "tooltip_save", textCategory);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else if (args[1].contains(".")) {
                     message = lang.getMessage(sender, "Character_Special");
                     message.sendMessage(sender);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else {
                     textCategory = itemManager.isExist(args[1]) ? itemManager.getRawName(args[1]) : args[1];
                     Player player = PlayerUtil.parse(sender);
                     item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                     if (!EquipmentUtil.isSolid(item)) {
                        senderMessage = lang.getMessage(sender, "Item_MainHand_Empty");
                        senderMessage.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else {
                        item = item.clone();
                        targetMessage = lang.getMessage(sender, "MyItems_Save_Success");
                        item.setAmount(1);
                        itemManager.getItemConfig().saveItem(item, textCategory);
                        targetMessage.sendMessage(sender, "nameid", textCategory);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                        return true;
                     }
                  }
               } else if (commandManager.checkCommand(subCommand, "MyItems_Remove")) {
                  if (!commandManager.checkPermission(sender, "MyItems_Remove")) {
                     textCategory = commandManager.getPermission("MyItems_Remove");
                     message = lang.getMessage(sender, "Permission_Lack");
                     message.sendMessage(sender, "permission", textCategory);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else if (args.length < 2) {
                     textCategory = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Remove"));
                     message = lang.getMessage(sender, "Argument_MyItems_Remove");
                     message.sendMessage(sender, "tooltip_remove", textCategory);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else if (args[1].contains(".")) {
                     message = lang.getMessage(sender, "Character_Special");
                     message.sendMessage(sender);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else {
                     textCategory = itemManager.getRawName(args[1]);
                     if (textCategory == null) {
                        message = lang.getMessage(sender, "Item_Not_Exist");
                        message.sendMessage(sender, "nameid", args[1]);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else {
                        message = lang.getMessage(sender, "MyItems_Remove_Success");
                        itemManager.getItemConfig().removeItem(textCategory);
                        message.sendMessage(sender, "nameid", textCategory);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                        return true;
                     }
                  }
               } else {
                  ItemSetComponent itemSetComponent;
                  ItemGenerator itemGenerator;
                  ItemSet itemSet;
                  String textItemName;
                  if (commandManager.checkCommand(subCommand, "MyItems_Drop")) {
                     if (!commandManager.checkPermission(sender, "MyItems_Drop")) {
                        textCategory = commandManager.getPermission("MyItems_Drop");
                        message = lang.getMessage(sender, "Permission_Lack");
                        message.sendMessage(sender, "permission", textCategory);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else if (args.length < (sender instanceof Player ? 3 : 7)) {
                        textCategory = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Drop"));
                        message = lang.getMessage(sender, "Argument_MyItems_Drop");
                        message.sendMessage(sender, "tooltip_drop", textCategory);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else if (args[2].contains(".")) {
                        message = lang.getMessage(sender, "Character_Special");
                        message.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else {
                        textCategory = args[1];
                        textItemName = args[2];
                        if (commandManager.checkCommand(textCategory, "MyItems_Drop_Custom")) {
                           item = itemManager.getItem(textItemName);
                           name = item != null ? itemManager.getRawName(textItemName) : textItemName;
                        } else if (commandManager.checkCommand(textCategory, "MyItems_Drop_Generator")) {
                           itemGenerator = itemGeneratorManager.getItemGenerator(textItemName);
                           item = itemGenerator != null ? itemGenerator.generateItem() : null;
                           name = itemGenerator != null ? itemGenerator.getId() : textItemName;
                        } else {
                           if (!commandManager.checkCommand(textCategory, "MyItems_Drop_Set")) {
                              targetMessage = lang.getMessage(sender, "MyItems_Category_Not_Exists");
                              targetMessage.sendMessage(sender, "category", textCategory);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           }

                           itemSet = itemSetManager.getItemSetByComponentID(textItemName);
                           itemSetComponent = itemSet != null ? itemSet.getItemSetComponent(textItemName) : null;
                           item = itemSetComponent != null ? itemSet.generateItem(textItemName) : null;
                           name = itemSetComponent != null ? itemSetComponent.getID() : textItemName;
                        }

                        if (item == null) {
                           targetMessage = lang.getMessage(sender, "Item_Not_Exist");
                           targetMessage.sendMessage(sender, "nameid", name);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else {
                           World world;
                           if (args.length > 3) {
                              textWorld = args[3];
                              if (textWorld.equalsIgnoreCase("~") && sender instanceof Player) {
                                 Player player = (Player)sender;
                                 world = player.getWorld();
                              } else {
                                 world = WorldUtil.getWorld(textWorld);
                              }
                           } else {
                              Player player = (Player)sender;
                              world = player.getWorld();
                           }

                           if (world == null) {
                              message = lang.getMessage(sender, "MyItems_World_Not_Exists");
                              message.sendMessage(sender, "world", args[3]);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else {
                              double x;
                              Player player;
                              Location location;
                              if (args.length > 4) {
                                 textAmount = args[4];
                                 if (!MathUtil.isNumber(textAmount)) {
                                    if (!textAmount.equalsIgnoreCase("~") || !(sender instanceof Player)) {
                                       message = lang.getMessage(sender, "Argument_Invalid_Value");
                                       message.sendMessage(sender);
                                       SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                       return true;
                                    }

                                    player = (Player)sender;
                                    location = player.getLocation();
                                    x = location.getX();
                                 } else {
                                    x = MathUtil.parseDouble(textAmount);
                                 }
                              } else {
                                 player = (Player)sender;
                                 location = player.getLocation();
                                 x = location.getX();
                              }

                              double y;
                              if (args.length > 5) {
                                 textAmount = args[5];
                                 if (!MathUtil.isNumber(textAmount)) {
                                    if (!textAmount.equalsIgnoreCase("~") || !(sender instanceof Player)) {
                                       message = lang.getMessage(sender, "Argument_Invalid_Value");
                                       message.sendMessage(sender);
                                       SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                       return true;
                                    }

                                    player = (Player)sender;
                                    location = player.getLocation();
                                    y = location.getY();
                                 } else {
                                    y = MathUtil.parseDouble(textAmount);
                                 }
                              } else {
                                 player = (Player)sender;
                                 location = player.getLocation();
                                 y = location.getY();
                              }

                              double z;
                              if (args.length > 6) {
                                 textAmount = args[6];
                                 if (!MathUtil.isNumber(textAmount)) {
                                    if (!textAmount.equalsIgnoreCase("~") || !(sender instanceof Player)) {
                                       message = lang.getMessage(sender, "Argument_Invalid_Value");
                                       message.sendMessage(sender);
                                       SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                       return true;
                                    }

                                    player = (Player)sender;
                                    location = player.getLocation();
                                    z = location.getZ();
                                 } else {
                                    z = MathUtil.parseDouble(textAmount);
                                 }
                              } else {
                                 player = (Player)sender;
                                 location = player.getLocation();
                                 z = location.getZ();
                              }

                              int amount;
                              if (args.length > 7) {
                                 textAmount = args[7];
                                 if (!MathUtil.isNumber(textAmount)) {
                                    message = lang.getMessage(sender, "Argument_Invalid_Value");
                                    message.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }

                                 amount = MathUtil.parseInteger(textAmount);
                              } else {
                                 amount = 1;
                              }

                              ItemStack clone = item.clone();
                              location = new Location(world, x, y, z);
                              if (SenderUtil.isPlayer(sender)) {
                                 message = lang.getMessage(sender, "MyItems_Drop_Success");
                                 HashMap<String, String> mapPlaceholder = new HashMap();
                                 mapPlaceholder.put("amount", String.valueOf(amount));
                                 mapPlaceholder.put("nameid", name);
                                 mapPlaceholder.put("world", world.getName());
                                 mapPlaceholder.put("x", String.valueOf(x));
                                 mapPlaceholder.put("y", String.valueOf(y));
                                 mapPlaceholder.put("z", String.valueOf(z));
                                 message.sendMessage(sender, mapPlaceholder);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                              }

                              clone.setAmount(amount);
                              world.dropItem(location, clone);
                              return true;
                           }
                        }
                     }
                  } else {
                     int rawAmount;
                     if (commandManager.checkCommand(subCommand, "MyItems_Load")) {
                        if (!commandManager.checkPermission(sender, "MyItems_Load")) {
                           textCategory = commandManager.getPermission("MyItems_Load");
                           message = lang.getMessage(sender, "Permission_Lack");
                           message.sendMessage(sender, "permission", textCategory);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else if (args.length < (SenderUtil.isPlayer(sender) ? 3 : 4)) {
                           textCategory = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Load"));
                           message = lang.getMessage(sender, "Argument_MyItems_Load");
                           message.sendMessage(sender, "tooltip_load", textCategory);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else if (args[2].contains(".")) {
                           message = lang.getMessage(sender, "Character_Special");
                           message.sendMessage(sender);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else {
                           textCategory = args[1];
                           textItemName = args[2];
                           if (commandManager.checkCommand(textCategory, "MyItems_Load_Custom")) {
                              item = itemManager.getItem(textItemName);
                              name = item != null ? itemManager.getRawName(textItemName) : textItemName;
                           } else if (commandManager.checkCommand(textCategory, "MyItems_Load_Generator")) {
                              itemGenerator = itemGeneratorManager.getItemGenerator(textItemName);
                              item = itemGenerator != null ? itemGenerator.generateItem() : null;
                              name = itemGenerator != null ? itemGenerator.getId() : textItemName;
                           } else {
                              if (!commandManager.checkCommand(textCategory, "MyItems_Load_Set")) {
                                 targetMessage = lang.getMessage(sender, "MyItems_Category_Not_Exists");
                                 targetMessage.sendMessage(sender, "category", textCategory);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              }

                              itemSet = itemSetManager.getItemSetByComponentID(textItemName);
                              itemSetComponent = itemSet != null ? itemSet.getItemSetComponent(textItemName) : null;
                              item = itemSetComponent != null ? itemSet.generateItem(textItemName) : null;
                              name = itemSetComponent != null ? itemSetComponent.getID() : textItemName;
                           }

                           if (item == null) {
                              targetMessage = lang.getMessage(sender, "Item_Not_Exist");
                              targetMessage.sendMessage(sender, "nameid", name);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else {
                              Player target;
                              if (args.length > 3) {
                                 textAmount = args[3];
                                 if (!PlayerUtil.isOnline(textAmount)) {
                                    message = lang.getMessage(sender, "Player_Target_Offline");
                                    message.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }

                                 target = PlayerUtil.getOnlinePlayer(textAmount);
                              } else {
                                 target = PlayerUtil.parse(sender);
                              }

                              int amount;
                              if (args.length > 4) {
                                 textAmount = args[4];
                                 if (!MathUtil.isNumber(textAmount)) {
                                    message = lang.getMessage(sender, "Argument_Invalid_Value");
                                    message.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }

                                 rawAmount = MathUtil.parseInteger(textAmount);
                                 amount = MathUtil.limitInteger(rawAmount, 1, rawAmount);
                              } else {
                                 amount = 1;
                              }

                              HashMap mapPlaceholder;
                              if (target.equals(sender)) {
                                 ItemStack clone = item.clone();
                                 message = lang.getMessage(sender, "MyItems_Load_Success_Self");
                                 mapPlaceholder = new HashMap();
                                 mapPlaceholder.put("amount", String.valueOf(amount));
                                 mapPlaceholder.put("nameID", name);
                                 EquipmentUtil.setAmount(clone, amount);
                                 EquipmentUtil.hookPlaceholderAPI(clone);
                                 PlayerUtil.addItem(target, clone);
                                 message.sendMessage(sender, mapPlaceholder);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                              } else {
                                 MessageBuild messageToSender = lang.getMessage(sender, "MyItems_Load_Success_To_Sender");
                                 message = lang.getMessage((LivingEntity)target, "MyItems_Load_Success_To_Target");
                                 mapPlaceholder = new HashMap();
                                 mapPlaceholder.put("nameID", name);
                                 mapPlaceholder.put("amount", String.valueOf(amount));
                                 mapPlaceholder.put("target", target.getName());
                                 mapPlaceholder.put("sender", sender.getName());
                                 EquipmentUtil.hookPlaceholderAPI(item, target);
                                 EquipmentUtil.setAmount(item, amount);
                                 PlayerUtil.addItem(target, item);
                                 messageToSender.sendMessage(sender, mapPlaceholder);
                                 message.sendMessage(target, mapPlaceholder);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                 SenderUtil.playSound(target, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                              }

                              if (commandManager.checkCommand(textCategory, "MyItems_Load_Set")) {
                                 itemSetManager.updateItemSet(target);
                              }

                              return true;
                           }
                        }
                     } else {
                        Player player;
                        if (commandManager.checkCommand(subCommand, "MyItems_Amount")) {
                           if (!commandManager.checkPermission(sender, "MyItems_Amount")) {
                              textCategory = commandManager.getPermission("MyItems_Amount");
                              message = lang.getMessage(sender, "Permission_Lack");
                              message.sendMessage(sender, "permission", textCategory);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else if (!SenderUtil.isPlayer(sender)) {
                              message = lang.getMessage(sender, "Console_Command_Forbiden");
                              message.sendMessage(sender);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else if (args.length < 2) {
                              textCategory = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Amount"));
                              message = lang.getMessage(sender, "Argument_MyItems_Amount");
                              message.sendMessage(sender, "tooltip_amount", textCategory);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else {
                              player = PlayerUtil.parse(sender);
                              item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                              if (!EquipmentUtil.isSolid(item)) {
                                 message = lang.getMessage(sender, "Item_MainHand_Empty");
                                 message.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              } else {
                                 name = args[1];
                                 if (MathUtil.isNumber(name)) {
                                    int amount = MathUtil.parseInteger(name);
                                    amount = MathUtil.limitInteger(amount, 1, 64);
                                    targetMessage = lang.getMessage(sender, "MyItems_Amount_Success");
                                    EquipmentUtil.setAmount(item, amount);
                                    targetMessage.sendMessage(sender, "amount", String.valueOf(amount));
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                    player.updateInventory();
                                    return true;
                                 } else {
                                    targetMessage = lang.getMessage(sender, "Argument_Invalid_Value");
                                    targetMessage.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }
                              }
                           }
                        } else if (commandManager.checkCommand(subCommand, "MyItems_Data")) {
                           if (!commandManager.checkPermission(sender, "MyItems_Data")) {
                              textCategory = commandManager.getPermission("MyItems_Data");
                              message = lang.getMessage(sender, "Permission_Lack");
                              message.sendMessage(sender, "permission", textCategory);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else if (!SenderUtil.isPlayer(sender)) {
                              message = lang.getMessage(sender, "Console_Command_Forbiden");
                              message.sendMessage(sender);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else if (args.length < 2) {
                              textCategory = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Data"));
                              message = lang.getMessage(sender, "Argument_MyItems_Data");
                              message.sendMessage(sender, "tooltip_data", textCategory);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else {
                              player = PlayerUtil.parse(sender);
                              item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                              if (!EquipmentUtil.isSolid(item)) {
                                 message = lang.getMessage(sender, "Item_MainHand_Empty");
                                 message.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              } else {
                                 name = args[1];
                                 if (MathUtil.isNumber(name)) {
                                    short data = MathUtil.parseShort(name);
                                    data = MathUtil.limitShort(data, (short)0, (short)4096);
                                    targetMessage = lang.getMessage(sender, "MyItems_Data_Success");
                                    EquipmentUtil.setData(item, data);
                                    targetMessage.sendMessage(sender, "data", String.valueOf(data));
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                    player.updateInventory();
                                    return true;
                                 } else {
                                    targetMessage = lang.getMessage(sender, "Argument_Invalid_Value");
                                    targetMessage.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }
                              }
                           }
                        } else if (commandManager.checkCommand(subCommand, "MyItems_Material")) {
                           if (!commandManager.checkPermission(sender, "MyItems_Material")) {
                              textCategory = commandManager.getPermission("MyItems_Material");
                              message = lang.getMessage(sender, "Permission_Lack");
                              message.sendMessage(sender, "permission", textCategory);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else if (!SenderUtil.isPlayer(sender)) {
                              message = lang.getMessage(sender, "Console_Command_Forbiden");
                              message.sendMessage(sender);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else if (args.length < 2) {
                              textCategory = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Material"));
                              message = lang.getMessage(sender, "Argument_MyItems_Material");
                              message.sendMessage(sender, "tooltip_material", textCategory);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else {
                              player = PlayerUtil.parse(sender);
                              item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                              Material material = MaterialUtil.getMaterial(args[1]);
                              if (!EquipmentUtil.isSolid(item)) {
                                 senderMessage = lang.getMessage(sender, "Item_MainHand_Empty");
                                 senderMessage.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              } else if (material == null) {
                                 senderMessage = lang.getMessage(sender, "MyItems_Material_Not_Existss");
                                 senderMessage.sendMessage(sender, "material", args[1]);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              } else {
                                 senderMessage = lang.getMessage(sender, "MyItems_Material_Success");
                                 EquipmentUtil.setMaterial(item, material);
                                 senderMessage.sendMessage(sender, "material", material.toString());
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                 player.updateInventory();
                                 return true;
                              }
                           }
                        } else if (commandManager.checkCommand(subCommand, "MyItems_Repair")) {
                           if (!commandManager.checkPermission(sender, "MyItems_Repair")) {
                              textCategory = commandManager.getPermission("MyItems_Repair");
                              message = lang.getMessage(sender, "Permission_Lack");
                              message.sendMessage(sender, "permission", textCategory);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else if (!SenderUtil.isPlayer(sender) && args.length < 2) {
                              textCategory = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Repair"));
                              message = lang.getMessage(sender, "Argument_MyItems_Repair");
                              message.sendMessage(sender, "tooltip_repair", textCategory);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else {
                              if (args.length > 1) {
                                 name = args[1];
                                 if (!PlayerUtil.isOnline(name)) {
                                    senderMessage = lang.getMessage(sender, "Player_Target_Offline");
                                    senderMessage.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }

                                 player = PlayerUtil.getOnlinePlayer(name);
                              } else {
                                 player = PlayerUtil.parse(sender);
                              }

                              int repair;
                              if (args.length > 2) {
                                 name = args[2];
                                 if (!MathUtil.isNumber(name)) {
                                    senderMessage = lang.getMessage(sender, "Argument_Invalid_Value");
                                    senderMessage.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }

                                 repair = Integer.valueOf(name);
                              } else {
                                 repair = -1;
                              }

                              item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                              if (!statsManager.hasLoreStats(item, LoreStatsEnum.DURABILITY)) {
                                 senderMessage = lang.getMessage(sender, "Item_Durability_Not_Exist");
                                 senderMessage.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              } else if (player.equals(sender)) {
                                 senderMessage = lang.getMessage(sender, "MyItems_Repair_Success");
                                 statsManager.itemRepair(item, repair);
                                 senderMessage.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                 player.updateInventory();
                                 return true;
                              } else {
                                 senderMessage = lang.getMessage(sender, "MyItems_Repair_Success_To_Sender");
                                 targetMessage = lang.getMessage((LivingEntity)player, "MyItems_Repair_Success_To_Target");
                                 HashMap<String, String> mapPlaceholder = new HashMap();
                                 mapPlaceholder.put("sender", sender.getName());
                                 mapPlaceholder.put("target", player.getName());
                                 statsManager.itemRepair(item, repair);
                                 senderMessage.sendMessage(sender, mapPlaceholder);
                                 targetMessage.sendMessage(player, mapPlaceholder);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                 SenderUtil.playSound(player, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                 player.updateInventory();
                                 return true;
                              }
                           }
                        } else if (commandManager.checkCommand(subCommand, "MyItems_Detail")) {
                           if (!commandManager.checkPermission(sender, "MyItems_Detail")) {
                              textCategory = commandManager.getPermission("MyItems_Detail");
                              message = lang.getMessage(sender, "Permission_Lack");
                              message.sendMessage(sender, "permission", textCategory);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else if (!SenderUtil.isPlayer(sender)) {
                              message = lang.getMessage(sender, "Console_Command_Forbiden");
                              message.sendMessage(sender);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else {
                              player = PlayerUtil.parse(sender);
                              item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                              if (!EquipmentUtil.isSolid(item)) {
                                 name = lang.getText(sender, "Item_MainHand_Empty");
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 SenderUtil.sendMessage(sender, name);
                                 return true;
                              } else {
                                 name = EquipmentUtil.getDisplayName(item);
                                 Material material = item.getType();
                                 ItemMeta meta = item.getItemMeta();
                                 int durability = material.getMaxDurability();
                                 int data = item.getDurability();
                                 rawAmount = item.hashCode();
                                 List<String> lores = EquipmentUtil.getLores(item);
                                 HashMap<String, String> mapPlaceholder = new HashMap();
                                 detailHeader = lang.getText(sender, "Detail_Header");
                                 detailName = lang.getText(sender, "Detail_Name");
                                 textAmount = lang.getText(sender, "Detail_Durability");
                                 String detailData = lang.getText(sender, "Detail_Data");
                                 String detailHashCode = lang.getText(sender, "Detail_HashCode");
                                 mapPlaceholder.put("name", name);
                                 mapPlaceholder.put("durability", String.valueOf(durability));
                                 mapPlaceholder.put("data", String.valueOf(data));
                                 mapPlaceholder.put("hashcode", String.valueOf(rawAmount));
                                 detailHeader = TextUtil.placeholder(mapPlaceholder, detailHeader);
                                 detailName = TextUtil.placeholder(mapPlaceholder, detailName);
                                 textAmount = TextUtil.placeholder(mapPlaceholder, textAmount);
                                 detailData = TextUtil.placeholder(mapPlaceholder, detailData);
                                 detailHashCode = TextUtil.placeholder(mapPlaceholder, detailHashCode);
                                 SenderUtil.sendMessage(sender, detailHeader);
                                 SenderUtil.sendMessage(sender, " ");
                                 SenderUtil.sendMessage(sender, detailName);
                                 SenderUtil.sendMessage(sender, textAmount);
                                 SenderUtil.sendMessage(sender, detailData);
                                 SenderUtil.sendMessage(sender, detailHashCode);
                                 if (!lores.isEmpty()) {
                                    detailFlagsHead = lang.getText(sender, "Detail_Lores_Head");
                                    SenderUtil.sendMessage(sender, " ");
                                    SenderUtil.sendMessage(sender, detailFlagsHead);

                                    for(int i = 0; i < lores.size(); ++i) {
                                       MessageBuild loreMessage = lang.getMessage(sender, "Detail_Lores_List");
                                       mapPlaceholder.clear();
                                       mapPlaceholder.put("line", String.valueOf(i + 1));
                                       mapPlaceholder.put("lore", (String)lores.get(i));
                                       loreMessage.sendMessage(sender, mapPlaceholder);
                                    }
                                 }

                                 if (meta.hasEnchants()) {
                                    detailFlagsHead = lang.getText(sender, "Detail_Enchantment_Head");
                                    Map<Enchantment, Integer> mapEnchantment = meta.getEnchants();
                                    SenderUtil.sendMessage(sender, " ");
                                    SenderUtil.sendMessage(sender, detailFlagsHead);
                                    Iterator var36 = meta.getEnchants().keySet().iterator();

                                    while(var36.hasNext()) {
                                       Enchantment enchant = (Enchantment)var36.next();
                                       MessageBuild enchantmentMessage = lang.getMessage(sender, "Detail_Enchantment_List");
                                       mapPlaceholder.clear();
                                       mapPlaceholder.put("enchantment", enchant.getName());
                                       mapPlaceholder.put("grade", String.valueOf(mapEnchantment.get(enchant)));
                                       enchantmentMessage.sendMessage(sender, mapPlaceholder);
                                    }
                                 }

                                 if (!meta.getItemFlags().isEmpty()) {
                                    detailFlagsHead = lang.getText(sender, "Detail_Flags_Head");
                                    SenderUtil.sendMessage(sender, " ");
                                    SenderUtil.sendMessage(sender, detailFlagsHead);
                                    Iterator var95 = meta.getItemFlags().iterator();

                                    while(var95.hasNext()) {
                                       ItemFlag flag = (ItemFlag)var95.next();
                                       MessageBuild flagMessage = lang.getMessage(sender, "Detail_Flags_List");
                                       mapPlaceholder.clear();
                                       mapPlaceholder.put("flag", String.valueOf(flag));
                                       flagMessage.sendMessage(sender, mapPlaceholder);
                                    }
                                 }

                                 SenderUtil.playSound(player, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                 return true;
                              }
                           }
                        } else if (commandManager.checkCommand(subCommand, "MyItems_Stats")) {
                           if (!commandManager.checkPermission(sender, "MyItems_Stats")) {
                              textCategory = commandManager.getPermission("MyItems_Stats");
                              message = lang.getMessage(sender, "Permission_Lack");
                              message.sendMessage(sender, "permission", textCategory);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else if (!SenderUtil.isPlayer(sender)) {
                              message = lang.getMessage(sender, "Console_Command_Forbiden");
                              message.sendMessage(sender);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else {
                              player = PlayerUtil.parse(sender);
                              menuManager.openMenuStats(player);
                              return true;
                           }
                        } else {
                           String[] fullArgs;
                           if (commandManager.checkCommand(subCommand, "MyItems_Help")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              return help(sender, command, label, fullArgs);
                           } else if (commandManager.checkCommand(subCommand, "MyItems_List")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              list(sender, command, label, fullArgs);
                              return true;
                           } else if (commandManager.checkCommand(subCommand, "MyItems_Name")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              return CommandItemName.setName(sender, command, label, fullArgs);
                           } else if (commandManager.checkCommand(subCommand, "MyItems_Lore_Set")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              return CommandLoreSet.setLore(sender, command, label, fullArgs);
                           } else if (commandManager.checkCommand(subCommand, "MyItems_Lore_Insert")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              return CommandLoreInsert.insertLore(sender, command, label, fullArgs);
                           } else if (commandManager.checkCommand(subCommand, "MyItems_Lore_Add")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              return CommandLoreAdd.addLore(sender, command, label, fullArgs);
                           } else if (commandManager.checkCommand(subCommand, "MyItems_Lore_Remove")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              return CommandLoreRemove.removeLore(sender, command, label, fullArgs);
                           } else if (commandManager.checkCommand(subCommand, "MyItems_Lore_Clear")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              return CommandLoreClear.clearLore(sender, command, label, fullArgs);
                           } else if (commandManager.checkCommand(subCommand, "MyItems_Flag_Add")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              return CommandFlagAdd.addFlag(sender, command, label, fullArgs);
                           } else if (commandManager.checkCommand(subCommand, "MyItems_Flag_Remove")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              return CommandFlagRemove.removeFlag(sender, command, label, fullArgs);
                           } else if (commandManager.checkCommand(subCommand, "MyItems_Flag_Clear")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              return CommandFlagClear.clearFlag(sender, command, label, fullArgs);
                           } else if (commandManager.checkCommand(subCommand, "MyItems_Enchant_Add")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              return CommandEnchantAdd.addEnchant(sender, command, label, fullArgs);
                           } else if (commandManager.checkCommand(subCommand, "MyItems_Enchant_Remove")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              return CommandEnchantRemove.removeEnchant(sender, command, label, fullArgs);
                           } else if (commandManager.checkCommand(subCommand, "MyItems_Enchant_Clear")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              return CommandEnchantClear.clearEnchant(sender, command, label, fullArgs);
                           } else if (commandManager.checkCommand(subCommand, "MyItems_Unbreakable")) {
                              fullArgs = TextUtil.pressList(args, 2);
                              return CommandUnbreakable.unbreakable(sender, command, label, fullArgs);
                           } else {
                              message = lang.getMessage(sender, "Argument_Invalid_Command");
                              message.sendMessage(sender);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           }
                        }
                     }
                  }
               }
            }
         }
      } else {
         String[] fullArgs = TextUtil.pressList(args, 2);
         return help(sender, command, label, fullArgs);
      }
   }

   protected static final boolean help(CommandSender sender, Command command, String label, String[] args) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      PluginManager pluginManager = plugin.getPluginManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      String tooltip;
      MessageBuild message;
      if (!commandManager.checkPermission(sender, "MyItems_Help")) {
         tooltip = commandManager.getPermission("MyItems_Help");
         message = lang.getMessage(sender, "Permission_Lack");
         message.sendMessage(sender, "permission", tooltip);
         SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
         return true;
      } else if (args.length < 1) {
         tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Help"));
         message = lang.getMessage(sender, "Argument_MyItems_Help");
         message.sendMessage(sender, "tooltip_help", tooltip);
         SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
         return true;
      } else {
         int maxPage;
         PluginPropertiesManager pluginPropertiesManager = pluginManager.getPluginPropertiesManager();
         PluginPropertiesResourceBuild pluginPropertiesResource = pluginPropertiesManager.getPluginPropertiesResource();
         HashMap<String, String> map = new HashMap();
         int page = 1;
         String textPage;
         if (args.length > 0) {
            textPage = args[0];
            if (MathUtil.isNumber(textPage)) {
               page = MathUtil.parseInteger(textPage);
               page = MathUtil.limitInteger(page, 1, 7);
            }
         }

         textPage = lang.getText(sender, "Help_Header");
         String helpPage = lang.getText(sender, "Help_Page");
         String previousTooltip = "||&6&l◀||ttp: {text_previous_page}||cmd: /{plugin} help {previous_page}||";
         String nextTooltip = "||&6&l▶||ttp: {text_next_page}||cmd: /{plugin} help {next_page}||";
         String myitems_Help = lang.getText(sender, "Argument_MyItems_Help");
         String myitems_About = lang.getText(sender, "Argument_MyItems_About");
         String myitems_Reload = lang.getText(sender, "Argument_MyItems_Reload");
         String myitems_Detail = lang.getText(sender, "Argument_MyItems_Detail");
         String myitems_Stats = lang.getText(sender, "Argument_MyItems_Detail");
         String myitems_List = lang.getText(sender, "Argument_MyItems_List");
         String myitems_Save = lang.getText(sender, "Argument_MyItems_Save");
         String myitems_Load = lang.getText(sender, "Argument_MyItems_Load");
         String myitems_Drop = lang.getText(sender, "Argument_MyItems_Drop");
         String myitems_Remove = lang.getText(sender, "Argument_MyItems_Remove");
         String myitems_Repair = lang.getText(sender, "Argument_MyItems_Repair");
         String myitems_Amount = lang.getText(sender, "Argument_MyItems_Amount");
         String myitems_Data = lang.getText(sender, "Argument_MyItems_Data");
         String myitems_Material = lang.getText(sender, "Argument_MyItems_Material");
         String myitems_Unbreakable = lang.getText(sender, "Argument_Unbreakable");
         String myitems_Setname = lang.getText(sender, "Argument_SetName");
         String myitems_Lore_Set = lang.getText(sender, "Argument_SetLore");
         String myitems_Lore_Insert = lang.getText(sender, "Argument_InsertLore");
         String myitems_Lore_Add = lang.getText(sender, "Argument_AddLore");
         String myitems_Lore_Remove = lang.getText(sender, "Argument_RemoveLore");
         String myitems_Lore_Clear = lang.getText(sender, "Argument_ClearLore");
         String myitems_Flag_Add = lang.getText(sender, "Argument_AddFlag");
         String myitems_Flag_Remove = lang.getText(sender, "Argument_RemoveFlag");
         String myitems_Flag_Clear = lang.getText(sender, "Argument_ClearFlag");
         String myitems_Enchant_Add = lang.getText(sender, "Argument_AddEnchant");
         String myitems_Enchant_Remove = lang.getText(sender, "Argument_RemoveEnchant");
         String myitems_Enchant_Clear = lang.getText(sender, "Argument_ClearEnchant");
         String myitems_Attribute_Stats = lang.getText(sender, "Argument_Attribute_Stats");
         String myitems_Attribute_Element = lang.getText(sender, "Argument_Attribute_Element");
         String myitems_Attribute_Buff = lang.getText(sender, "Argument_Attribute_Buffs");
         String myitems_Attribute_Debuff = lang.getText(sender, "Argument_Attribute_Debuffs");
         String myitems_Attribute_NBT = lang.getText(sender, "Argument_Attribute_NBT");
         String myitems_Attribute_Power = lang.getText(sender, "Argument_Attribute_Power");
         String myitems_Attribute_Ability = lang.getText(sender, "Argument_Attribute_Ability");
         String myitems_Socket_Add = lang.getText(sender, "Argument_Socket_Add");
         String myitems_Socket_Load = lang.getText(sender, "Argument_Socket_Load");
         String myitems_Socket_Drop = lang.getText(sender, "Argument_Socket_Drop");
         String myitems_Socket_List = lang.getText(sender, "Argument_Socket_List");
         map.put("plugin", pluginPropertiesResource.getName());
         map.put("page", String.valueOf(page));
         map.put("maxpage", String.valueOf(7));
         map.put("previous_page", String.valueOf(page - 1));
         map.put("next_page", String.valueOf(page + 1));
         map.put("text_previous_page", lang.getText(sender, "Help_Previous_Page"));
         map.put("text_next_page", lang.getText(sender, "Help_Next_Page"));
         map.put("tooltip_help", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Help")));
         map.put("tooltip_about", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_About")));
         map.put("tooltip_reload", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Reload")));
         map.put("tooltip_detail", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Detail")));
         map.put("tooltip_stats", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Stats")));
         map.put("tooltip_list", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_List")));
         map.put("tooltip_save", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Save")));
         map.put("tooltip_load", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Load")));
         map.put("tooltip_drop", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Drop")));
         map.put("tooltip_remove", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Remove")));
         map.put("tooltip_repair", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Repair")));
         map.put("tooltip_amount", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Amount")));
         map.put("tooltip_data", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Data")));
         map.put("tooltip_material", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Material")));
         map.put("tooltip_setname", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Setname")));
         map.put("tooltip_lore_set", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Lore_Set")));
         map.put("tooltip_lore_insert", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Lore_Insert")));
         map.put("tooltip_lore_add", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Lore_Add")));
         map.put("tooltip_lore_remove", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Lore_Remove")));
         map.put("tooltip_lore_clear", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Lore_Clear")));
         map.put("tooltip_flag_add", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Flag_Add")));
         map.put("tooltip_flag_remove", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Flag_Remove")));
         map.put("tooltip_flag_clear", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Flag_Clear")));
         map.put("tooltip_enchant_add", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Enchant_Add")));
         map.put("tooltip_enchant_remove", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Enchant_Remove")));
         map.put("tooltip_enchant_clear", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Enchant_Clear")));
         map.put("tooltip_unbreakable", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Unbreakable")));
         map.put("tooltip_att_stats", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Stats")));
         map.put("tooltip_att_buffs", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Buffs")));
         map.put("tooltip_att_debuffs", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Debuffs")));
         map.put("tooltip_att_ability", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Ability")));
         map.put("tooltip_att_power", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Power")));
         map.put("tooltip_att_nbt", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_NBT")));
         map.put("tooltip_att_element", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Element")));
         map.put("tooltip_socket_add", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Add")));
         map.put("tooltip_socket_load", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Load")));
         map.put("tooltip_socket_drop", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Drop")));
         map.put("tooltip_socket_list", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_List")));
         previousTooltip = TextUtil.placeholder(map, previousTooltip);
         nextTooltip = TextUtil.placeholder(map, nextTooltip);
         myitems_Help = TextUtil.placeholder(map, myitems_Help);
         myitems_About = TextUtil.placeholder(map, myitems_About);
         myitems_Reload = TextUtil.placeholder(map, myitems_Reload);
         myitems_Detail = TextUtil.placeholder(map, myitems_Detail);
         myitems_Stats = TextUtil.placeholder(map, myitems_Stats);
         myitems_List = TextUtil.placeholder(map, myitems_List);
         myitems_Save = TextUtil.placeholder(map, myitems_Save);
         myitems_Load = TextUtil.placeholder(map, myitems_Load);
         myitems_Drop = TextUtil.placeholder(map, myitems_Drop);
         myitems_Remove = TextUtil.placeholder(map, myitems_Remove);
         myitems_Repair = TextUtil.placeholder(map, myitems_Repair);
         myitems_Amount = TextUtil.placeholder(map, myitems_Amount);
         myitems_Data = TextUtil.placeholder(map, myitems_Data);
         myitems_Material = TextUtil.placeholder(map, myitems_Material);
         myitems_Unbreakable = TextUtil.placeholder(map, myitems_Unbreakable);
         myitems_Setname = TextUtil.placeholder(map, myitems_Setname);
         myitems_Lore_Set = TextUtil.placeholder(map, myitems_Lore_Set);
         myitems_Lore_Add = TextUtil.placeholder(map, myitems_Lore_Add);
         myitems_Lore_Insert = TextUtil.placeholder(map, myitems_Lore_Insert);
         myitems_Lore_Remove = TextUtil.placeholder(map, myitems_Lore_Remove);
         myitems_Lore_Clear = TextUtil.placeholder(map, myitems_Lore_Clear);
         myitems_Flag_Add = TextUtil.placeholder(map, myitems_Flag_Add);
         myitems_Flag_Remove = TextUtil.placeholder(map, myitems_Flag_Remove);
         myitems_Flag_Clear = TextUtil.placeholder(map, myitems_Flag_Clear);
         myitems_Enchant_Add = TextUtil.placeholder(map, myitems_Enchant_Add);
         myitems_Enchant_Remove = TextUtil.placeholder(map, myitems_Enchant_Remove);
         myitems_Enchant_Clear = TextUtil.placeholder(map, myitems_Enchant_Clear);
         myitems_Attribute_Stats = TextUtil.placeholder(map, myitems_Attribute_Stats);
         myitems_Attribute_Buff = TextUtil.placeholder(map, myitems_Attribute_Buff);
         myitems_Attribute_Debuff = TextUtil.placeholder(map, myitems_Attribute_Debuff);
         myitems_Attribute_Ability = TextUtil.placeholder(map, myitems_Attribute_Ability);
         myitems_Attribute_Power = TextUtil.placeholder(map, myitems_Attribute_Power);
         myitems_Attribute_NBT = TextUtil.placeholder(map, myitems_Attribute_NBT);
         myitems_Attribute_Element = TextUtil.placeholder(map, myitems_Attribute_Element);
         myitems_Socket_Add = TextUtil.placeholder(map, myitems_Socket_Add);
         myitems_Socket_Load = TextUtil.placeholder(map, myitems_Socket_Load);
         myitems_Socket_Drop = TextUtil.placeholder(map, myitems_Socket_Drop);
         myitems_Socket_List = TextUtil.placeholder(map, myitems_Socket_List);
         map.put("previous", previousTooltip);
         map.put("next", nextTooltip);
         textPage = TextUtil.placeholder(map, textPage);
         helpPage = TextUtil.placeholder(map, helpPage);
         SenderUtil.sendMessage(sender, textPage);
         SenderUtil.sendMessage(sender, "");
         SenderUtil.sendMessage(sender, helpPage);
         if (page == 1) {
            SenderUtil.sendMessage(sender, myitems_Help);
            SenderUtil.sendMessage(sender, myitems_List);
            SenderUtil.sendMessage(sender, myitems_Save);
            SenderUtil.sendMessage(sender, myitems_Remove);
            SenderUtil.sendMessage(sender, myitems_Load);
            SenderUtil.sendMessage(sender, myitems_Drop);
         } else if (page == 2) {
            SenderUtil.sendMessage(sender, myitems_Detail);
            SenderUtil.sendMessage(sender, myitems_Stats);
            SenderUtil.sendMessage(sender, myitems_Amount);
            SenderUtil.sendMessage(sender, myitems_Data);
            SenderUtil.sendMessage(sender, myitems_Material);
            SenderUtil.sendMessage(sender, myitems_Repair);
         } else if (page == 3) {
            SenderUtil.sendMessage(sender, myitems_Setname);
            SenderUtil.sendMessage(sender, myitems_Lore_Set);
            SenderUtil.sendMessage(sender, myitems_Lore_Insert);
            SenderUtil.sendMessage(sender, myitems_Lore_Add);
            SenderUtil.sendMessage(sender, myitems_Lore_Remove);
            SenderUtil.sendMessage(sender, myitems_Lore_Clear);
         } else if (page == 4) {
            SenderUtil.sendMessage(sender, myitems_Flag_Add);
            SenderUtil.sendMessage(sender, myitems_Flag_Remove);
            SenderUtil.sendMessage(sender, myitems_Flag_Clear);
            SenderUtil.sendMessage(sender, myitems_Enchant_Add);
            SenderUtil.sendMessage(sender, myitems_Enchant_Remove);
            SenderUtil.sendMessage(sender, myitems_Enchant_Clear);
         } else if (page == 5) {
            SenderUtil.sendMessage(sender, myitems_Attribute_Stats);
            SenderUtil.sendMessage(sender, myitems_Attribute_Buff);
            SenderUtil.sendMessage(sender, myitems_Attribute_Debuff);
            SenderUtil.sendMessage(sender, myitems_Attribute_Ability);
            SenderUtil.sendMessage(sender, myitems_Attribute_Power);
            SenderUtil.sendMessage(sender, myitems_Attribute_NBT);
         } else if (page == 6) {
            SenderUtil.sendMessage(sender, myitems_Attribute_Element);
            SenderUtil.sendMessage(sender, myitems_Socket_Add);
            SenderUtil.sendMessage(sender, myitems_Socket_Load);
            SenderUtil.sendMessage(sender, myitems_Socket_Drop);
            SenderUtil.sendMessage(sender, myitems_Socket_List);
            SenderUtil.sendMessage(sender, myitems_Unbreakable);
         } else if (page == 7) {
            SenderUtil.sendMessage(sender, myitems_About);
            SenderUtil.sendMessage(sender, myitems_Reload);
         }

         SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
         SenderUtil.sendMessage(sender, helpPage);
         return true;
      }
   }

   public static final List<String> list(CommandSender sender, Command command, String label, String[] args) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      ItemManager itemManager = plugin.getGameManager().getItemManager();
      PluginManager pluginManager = plugin.getPluginManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      MainConfig mainConfig = MainConfig.getInstance();
      List<String> list = new ArrayList();
      if (!commandManager.checkPermission(sender, "MyItems_List")) {
         String permission = commandManager.getPermission("MyItems_List");
         MessageBuild message = lang.getMessage(sender, "Permission_Lack");
         message.sendMessage(sender, "permission", permission);
         SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
         return list;
      } else if (itemManager.getItems().isEmpty()) {
         MessageBuild message = lang.getMessage(sender, "Item_Database_Empty");
         message.sendMessage(sender);
         SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
         return list;
      } else {
         List<String> keyList = SortUtil.toList(itemManager.getItemIDs());
         int size = keyList.size();
         int maxRow;
         int maxPage = MathUtil.isDividedBy((double)size, 5.0D) ? size / 5 : size / 5 + 1;
         int page = 1;
         String codeTooltip;
         if (args.length > 0) {
            codeTooltip = args[0];
            if (MathUtil.isNumber(codeTooltip)) {
               page = MathUtil.parseInteger(codeTooltip);
               page = MathUtil.limitInteger(page, 1, maxPage);
            }
         }

         codeTooltip = mainConfig.getUtilityTooltip();
         HashMap<String, String> map = new HashMap();
         String listHeaderMessage = lang.getText(sender, "List_Header");
         map.put("page", String.valueOf(page));
         map.put("maxpage", String.valueOf(maxPage));
         listHeaderMessage = TextUtil.placeholder(map, listHeaderMessage);
         SenderUtil.sendMessage(sender, listHeaderMessage);
         int addNum = (page - 1) * 5;

         for(int t = 0; t < 5 && t + addNum < size; ++t) {
            int index = t + addNum;
            String key = (String)keyList.get(index);
            ItemStack item = itemManager.getItem(key);
            HashMap<String, String> subMap = new HashMap();
            String listItemMessage = lang.getText(sender, "List_Item");
            subMap.put("index", String.valueOf(index + 1));
            subMap.put("item", key);
            subMap.put("maxpage", String.valueOf(page));
            subMap.put("tooltip", JsonUtil.generateJsonItem(codeTooltip, item));
            listItemMessage = TextUtil.placeholder(subMap, listItemMessage);
            list.add(key);
            SenderUtil.sendMessage(sender, listItemMessage);
         }

         SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
         return list;
      }
   }
}
