package com.praya.myitems.command;

import api.praya.myitems.builder.socket.SocketGems;
import api.praya.myitems.builder.socket.SocketGemsTree;
import com.praya.agarthalib.utility.EquipmentUtil;
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
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.RomanNumber;
import core.praya.agarthalib.enums.main.Slot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandSocket extends HandlerCommand implements CommandExecutor {
   public CommandSocket(MyItems plugin) {
      super(plugin);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      GameManager gameManager = this.plugin.getGameManager();
      SocketManager socketManager = gameManager.getSocketManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (args.length <= 0) {
         String[] fullArgs = TextUtil.pressList(args, 2);
         return CommandMyItems.help(sender, command, label, fullArgs);
      } else {
         String subCommand = args[0];
         String loadType;
         int maxGrade;
         MessageBuild message;
         ItemStack itemRod;
         String textWorld;
         MessageBuild messageToSender;
         HashMap mapPlaceholder;
         MessageBuild messageToTarget;
         if (commandManager.checkCommand(subCommand, "Socket_Add")) {
            if (!commandManager.checkPermission(sender, "Socket_Add")) {
               loadType = commandManager.getPermission("Socket_Add");
               message = lang.getMessage(sender, "Permission_Lack");
               message.sendMessage(sender, "permission", loadType);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
               return true;
            } else if (!SenderUtil.isPlayer(sender)) {
               message = lang.getMessage(sender, "Console_Command_Forbiden");
               message.sendMessage(sender);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
               return true;
            } else if (args.length < 2) {
               loadType = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Add"));
               message = lang.getMessage(sender, "Argument_Socket_Add");
               message.sendMessage(sender, "tooltip_socket_add", loadType);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
               return true;
            } else {
               loadType = args[1];
               Player player = PlayerUtil.parse(sender);
               itemRod = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
               if (!EquipmentUtil.isSolid(itemRod)) {
                  message = lang.getMessage(sender, "Item_MainHand_Empty");
                  message.sendMessage(sender);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                  return true;
               } else {
                  maxGrade = 1;
                  String lore;
                  String textLine;
                  if (!loadType.equalsIgnoreCase("Empty") && !loadType.equalsIgnoreCase("Unlock")) {
                     if (!loadType.equalsIgnoreCase("Locked") && !loadType.equalsIgnoreCase("Lock")) {
                        textLine = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Add"));
                        messageToTarget = lang.getMessage(sender, "Argument_Socket_Add");
                        messageToTarget.sendMessage(sender, "tooltip_socket_add", textLine);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     }

                     lore = socketManager.getTextSocketSlotLocked();
                     textWorld = lang.getText(sender, "Socket_Slot_Type_Locked");
                  } else {
                     lore = socketManager.getTextSocketSlotEmpty();
                     textWorld = lang.getText(sender, "Socket_Slot_Type_Empty");
                  }

                  if (EquipmentUtil.loreCheck(itemRod)) {
                     maxGrade = EquipmentUtil.getLores(itemRod).size() + 1;
                  }

                  if (args.length > 2) {
                     textLine = args[2];
                     if (!MathUtil.isNumber(textLine)) {
                        messageToTarget = lang.getMessage(sender, "Argument_Invalid_Value");
                        messageToTarget.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     }

                     maxGrade = MathUtil.parseInteger(textLine);
                     if (maxGrade < 1) {
                        messageToTarget = lang.getMessage(sender, "Argument_Invalid_Value");
                        messageToTarget.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     }
                  }

                  messageToSender = lang.getMessage(sender, "MyItems_Socket_Add_Slot_Success");
                  mapPlaceholder = new HashMap();
                  mapPlaceholder.put("line", String.valueOf(maxGrade));
                  mapPlaceholder.put("type", textWorld);
                  EquipmentUtil.setLore(itemRod, maxGrade, lore);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                  messageToSender.sendMessage(sender, mapPlaceholder);
                  player.updateInventory();
                  return true;
               }
            }
         } else {
            String textRod;
            int grade;
            SocketGemsTree socketTree;
            String textAmount;
            if (!commandManager.checkCommand(subCommand, "Socket_Drop")) {
               if (commandManager.checkCommand(subCommand, "Socket_Load")) {
                  if (!commandManager.checkPermission(sender, "Socket_Load")) {
                     loadType = commandManager.getPermission("Socket_Load");
                     message = lang.getMessage(sender, "Permission_Lack");
                     message.sendMessage(sender, "permission", loadType);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else if (args.length < 2) {
                     loadType = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Load"));
                     message = lang.getMessage(sender, "Argument_Socket_Load");
                     message.sendMessage(sender, "tooltip_socket_load", loadType);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else {
                     loadType = args[1];
                     int rawAmount;
                     if (commandManager.checkCommand(loadType, "Socket_Load_Gems")) {
                        if (!commandManager.checkPermission(sender, "Socket_Load_Gems")) {
                           textRod = commandManager.getPermission("Socket_Load_Gems");
                           message = lang.getMessage(sender, "Permission_Lack");
                           message.sendMessage(sender, "permission", textRod);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else if (args.length < (SenderUtil.isPlayer(sender) ? 3 : 5)) {
                           textRod = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Load_Gems"));
                           message = lang.getMessage(sender, "Argument_Socket_Load_Gems");
                           message.sendMessage(sender, "tooltip_socket_load_gems", textRod);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else if (args[2].contains(".")) {
                           message = lang.getMessage(sender, "Contains_Special_Character");
                           message.sendMessage(sender);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else {
                           socketTree = socketManager.getSocketTree(args[2]);
                           if (socketTree == null) {
                              message = lang.getMessage(sender, "Item_Not_Exist");
                              message.sendMessage(sender, "nameid", args[2]);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else {
                              if (args.length > 3) {
                                 textAmount = args[3];
                                 if (!MathUtil.isNumber(textAmount)) {
                                    messageToSender = lang.getMessage(sender, "Argument_Invalid_Value");
                                    messageToSender.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }

                                 rawAmount = socketTree.getMaxGrade();
                                 grade = MathUtil.limitInteger(MathUtil.parseInteger(textAmount), 1, rawAmount);
                              } else {
                                 grade = 1;
                              }

                              Player target;
                              if (args.length > 4) {
                                 textAmount = args[4];
                                 if (!PlayerUtil.isOnline(textAmount)) {
                                    messageToSender = lang.getMessage(sender, "Player_Target_Offline");
                                    messageToSender.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }

                                 target = PlayerUtil.getOnlinePlayer(textAmount);
                              } else {
                                 target = PlayerUtil.parse(sender);
                              }

                              if (args.length > 5) {
                                 textAmount = args[5];
                                 if (!MathUtil.isNumber(textAmount)) {
                                    messageToSender = lang.getMessage(sender, "Argument_Invalid_Value");
                                    messageToSender.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }

                                 grade = Math.max(1, MathUtil.parseInteger(textAmount));
                              } else {
                                 grade = 1;
                              }

                              ItemStack item = socketTree.getSocketBuild(grade).getItem();
                              if (target.equals(sender)) {
                                 messageToSender = lang.getMessage(sender, "MyItems_Socket_Load_Gems_Success_Self");
                                 mapPlaceholder = new HashMap();
                                 mapPlaceholder.put("amount", String.valueOf(grade));
                                 mapPlaceholder.put("nameID", socketTree.getGems());
                                 mapPlaceholder.put("grade", String.valueOf(RomanNumber.getRomanNumber(grade)));
                                 EquipmentUtil.setAmount(item, grade);
                                 PlayerUtil.addItem(target, item);
                                 messageToSender.sendMessage(sender, mapPlaceholder);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                 return true;
                              } else {
                                 messageToSender = lang.getMessage(sender, "MyItems_Socket_Load_Gems_Success_To_Sender");
                                 messageToTarget = lang.getMessage((LivingEntity)target, "MyItems_Socket_Load_Gems_Success_To_Target");
                                 mapPlaceholder = new HashMap();
                                 mapPlaceholder.put("nameID", socketTree.getGems());
                                 mapPlaceholder.put("grade", String.valueOf(RomanNumber.getRomanNumber(grade)));
                                 mapPlaceholder.put("amount", String.valueOf(grade));
                                 mapPlaceholder.put("target", target.getName());
                                 mapPlaceholder.put("sender", sender.getName());
                                 EquipmentUtil.setAmount(item, grade);
                                 PlayerUtil.addItem(target, item);
                                 messageToSender.sendMessage(sender, mapPlaceholder);
                                 messageToTarget.sendMessage(target, mapPlaceholder);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                 SenderUtil.playSound(target, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                 return true;
                              }
                           }
                        }
                     } else if (commandManager.checkCommand(loadType, "Socket_Load_Rod")) {
                        if (!commandManager.checkPermission(sender, "Socket_Load_Rod")) {
                           textRod = commandManager.getPermission("Socket_Load_Rod");
                           message = lang.getMessage(sender, "Permission_Lack");
                           message.sendMessage(sender, "permission", textRod);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else if (args.length < (SenderUtil.isPlayer(sender) ? 3 : 4)) {
                           textRod = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Load_Rod"));
                           message = lang.getMessage(sender, "Argument_Socket_Load_Rod");
                           message.sendMessage(sender, "tooltip_socket_load_rod", textRod);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else {
                           textRod = args[2];
                           if (textRod.equalsIgnoreCase("Unlock")) {
                              itemRod = mainConfig.getSocketItemRodUnlock();
                           } else {
                              if (!textRod.equalsIgnoreCase("Remove")) {
                                 textAmount = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Load_Rod"));
                                 messageToSender = lang.getMessage(sender, "Argument_Socket_Load_Rod");
                                 messageToSender.sendMessage(sender, "tooltip_socket_load_rod", textAmount);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              }

                              itemRod = mainConfig.getSocketItemRodRemove();
                           }

                           Player target;
                           if (args.length > 3) {
                              textAmount = args[3];
                              if (!PlayerUtil.isOnline(textAmount)) {
                                 messageToSender = lang.getMessage(sender, "Player_Target_Offline");
                                 messageToSender.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              }

                              target = PlayerUtil.getOnlinePlayer(textAmount);
                           } else {
                              target = PlayerUtil.parse(sender);
                           }

                           if (args.length > 4) {
                              textAmount = args[4];
                              if (!MathUtil.isNumber(textAmount)) {
                                 messageToSender = lang.getMessage(sender, "Argument_Invalid_Value");
                                 messageToSender.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              }

                              rawAmount = MathUtil.parseInteger(textAmount);
                              grade = MathUtil.limitInteger(rawAmount, 1, rawAmount);
                           } else {
                              grade = 1;
                           }

                           if (target.equals(sender)) {
                              messageToSender = lang.getMessage(sender, "MyItems_Socket_Load_Rod_Success_Self");
                              mapPlaceholder = new HashMap();
                              mapPlaceholder.put("socket_rod", EquipmentUtil.getDisplayName(itemRod));
                              mapPlaceholder.put("amount", String.valueOf(grade));
                              EquipmentUtil.setAmount(itemRod, grade);
                              PlayerUtil.addItem(target, itemRod);
                              messageToSender.sendMessage(sender, mapPlaceholder);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                              return true;
                           } else {
                              messageToSender = lang.getMessage(sender, "MyItems_Socket_Load_Rod_Success_To_Sender");
                              messageToSender = lang.getMessage(sender, "MyItems_Socket_Load_Rod_Success_To_Target");
                              mapPlaceholder = new HashMap();
                              mapPlaceholder.put("socket_rod", EquipmentUtil.getDisplayName(itemRod));
                              mapPlaceholder.put("amount", String.valueOf(grade));
                              EquipmentUtil.setAmount(itemRod, grade);
                              PlayerUtil.addItem(target, itemRod);
                              messageToSender.sendMessage(sender, mapPlaceholder);
                              messageToSender.sendMessage(target, mapPlaceholder);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                              SenderUtil.playSound(target, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                              return true;
                           }
                        }
                     } else {
                        textRod = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Load"));
                        message = lang.getMessage(sender, "Argument_Socket_Load");
                        message.sendMessage(sender, "tooltip_socket_load", textRod);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     }
                  }
               } else if (commandManager.checkCommand(subCommand, "Socket_List")) {
                  String[] fullArgs = TextUtil.pressList(args, 2);
                  list(sender, command, label, fullArgs);
                  return true;
               } else {
                  message = lang.getMessage(sender, "Argument_Invalid_Command");
                  message.sendMessage(sender);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                  return true;
               }
            } else if (!commandManager.checkPermission(sender, "Socket_Drop")) {
               loadType = commandManager.getPermission("Socket_Drop");
               message = lang.getMessage(sender, "Permission_Lack");
               message.sendMessage(sender, "permission", loadType);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
               return true;
            } else if (args.length < 2) {
               loadType = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Drop"));
               message = lang.getMessage(sender, "Argument_Socket_Drop");
               message.sendMessage(sender, "tooltip_socket_drop", loadType);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
               return true;
            } else {
               loadType = args[1];
               World world;
               Player player;
               Location location;
               if (commandManager.checkCommand(loadType, "Socket_Drop_Rod")) {
                  if (!commandManager.checkPermission(sender, "Socket_Drop_Rod")) {
                     textRod = commandManager.getPermission("Socket_Drop_Rod");
                     message = lang.getMessage(sender, "Permission_Lack");
                     message.sendMessage(sender, "permission", textRod);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else if (args.length < (sender instanceof Player ? 3 : 7)) {
                     textRod = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Drop_Rod"));
                     message = lang.getMessage(sender, "Argument_Socket_Drop_Rod");
                     message.sendMessage(sender, "tooltip_socket_drop_rod", textRod);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else {
                     textRod = args[2];
                     if (textRod.equalsIgnoreCase("Unlock")) {
                        itemRod = mainConfig.getSocketItemRodUnlock();
                     } else {
                        if (!textRod.equalsIgnoreCase("Remove")) {
                           textWorld = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Drop_Rod"));
                           messageToSender = lang.getMessage(sender, "Argument_Socket_Drop_Rod");
                           messageToSender.sendMessage(sender, "tooltip_socket_drop_rod", textWorld);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        }

                        itemRod = mainConfig.getSocketItemRodRemove();
                     }

                     if (args.length > 3) {
                        textWorld = args[3];
                        if (textWorld.equalsIgnoreCase("~") && sender instanceof Player) {
                           player = (Player)sender;
                           world = player.getWorld();
                        } else {
                           world = WorldUtil.getWorld(textWorld);
                        }
                     } else {
                         player = (Player)sender;
                        world = player.getWorld();
                     }

                     if (world == null) {
                         message = lang.getMessage(sender, "MyItems_World_Not_Exists");
                        message.sendMessage(sender, "world", args[3]);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else {
                        double x;
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

                        location = new Location(world, x, y, z);
                        if (SenderUtil.isPlayer(sender)) {
                           message = lang.getMessage(sender, "MyItems_Socket_Drop_Rod_Success");
                           mapPlaceholder = new HashMap();
                           mapPlaceholder.put("rod", EquipmentUtil.getDisplayName(itemRod));
                           mapPlaceholder.put("amount", String.valueOf(amount));
                           mapPlaceholder.put("world", world.getName());
                           mapPlaceholder.put("x", String.valueOf(x));
                           mapPlaceholder.put("y", String.valueOf(y));
                           mapPlaceholder.put("z", String.valueOf(z));
                           message.sendMessage(sender, mapPlaceholder);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                        }

                        itemRod.setAmount(amount);
                        world.dropItem(location, itemRod);
                        return true;
                     }
                  }
               } else if (commandManager.checkCommand(loadType, "Socket_Drop_Gems")) {
                  if (!commandManager.checkPermission(sender, "Socket_Drop_Gems")) {
                     textRod = commandManager.getPermission("Socket_Drop_Gems");
                     message = lang.getMessage(sender, "Permission_Lack");
                     message.sendMessage(sender, "permission", textRod);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else if (args.length < 8) {
                     textRod = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Drop"));
                     message = lang.getMessage(sender, "Argument_Socket_Drop_Gems");
                     message.sendMessage(sender, "tooltip_socket_drop_gems", textRod);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else if (args[2].contains(".")) {
                     message = lang.getMessage(sender, "Character_Special");
                     message.sendMessage(sender);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else {
                     socketTree = socketManager.getSocketTree(args[2]);
                     if (socketTree == null) {
                        message = lang.getMessage(sender, "Item_Not_Exist");
                        message.sendMessage(sender, "nameid", args[1]);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else {
                        String textGrade = args[3];
                        if (!MathUtil.isNumber(textGrade)) {
                           messageToSender = lang.getMessage(sender, "Argument_Invalid_Value");
                           messageToSender.sendMessage(sender);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else {
                           maxGrade = socketTree.getMaxGrade();
                           grade = MathUtil.limitInteger(MathUtil.parseInteger(textGrade), 1, maxGrade);
                           if (args.length > 4) {
                              textAmount = args[4];
                              if (textAmount.equalsIgnoreCase("~") && sender instanceof Player) {
                                 player = (Player)sender;
                                 world = player.getWorld();
                              } else {
                                 world = WorldUtil.getWorld(textAmount);
                              }
                           } else {
                              player = (Player)sender;
                              world = player.getWorld();
                           }

                           if (world == null) {
                              messageToSender = lang.getMessage(sender, "MyItems_World_Not_Exists");
                              messageToSender.sendMessage(sender, "world", args[4]);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else {
                              double x;
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
                              if (args.length > 7) {
                                 textAmount = args[7];
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
                              if (args.length > 8) {
                                 textAmount = args[8];
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

                              SocketGems socketGems = socketTree.getSocketBuild(grade);
                              ItemStack item = socketGems.getItem();
                              location = new Location(world, x, y, z);
                              if (SenderUtil.isPlayer(sender)) {
                                 message = lang.getMessage(sender, "MyItems_Socket_Drop_Gems_Success");
                                 mapPlaceholder = new HashMap();
                                 mapPlaceholder.put("amount", String.valueOf(amount));
                                 mapPlaceholder.put("nameid", socketTree.getGems());
                                 mapPlaceholder.put("grade", String.valueOf(grade));
                                 mapPlaceholder.put("world", world.getName());
                                 mapPlaceholder.put("x", String.valueOf(x));
                                 mapPlaceholder.put("y", String.valueOf(y));
                                 mapPlaceholder.put("z", String.valueOf(z));
                                 message.sendMessage(sender, mapPlaceholder);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                              }

                              item.setAmount(amount);
                              world.dropItem(location, item);
                              return true;
                           }
                        }
                     }
                  }
               } else {
                  textRod = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Drop"));
                  message = lang.getMessage(sender, "Argument_Socket_Drop");
                  message.sendMessage(sender, "tooltip_socket_drop", textRod);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                  return true;
               }
            }
         }
      }
   }

   private static final List<String> list(CommandSender sender, Command command, String label, String[] args) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      SocketManager socketManager = plugin.getGameManager().getSocketManager();
      PluginManager pluginManager = plugin.getPluginManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      List<String> list = new ArrayList();
      if (!commandManager.checkPermission(sender, "Socket_List")) {
         String permission = commandManager.getPermission("Socket_List");
         MessageBuild message = lang.getMessage(sender, "Permission_Lack");
         message.sendMessage(sender, "permission", permission);
         SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
         return list;
      } else if (socketManager.getSocketIDs().isEmpty()) {
         MessageBuild message = lang.getMessage(sender, "Item_Database_Empty");
         message.sendMessage(sender);
         SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
         return list;
      } else {
         List<String> keyList = SortUtil.toList(socketManager.getSocketIDs());
         int size = keyList.size();
         int maxRow;
         int maxPage = MathUtil.isDividedBy((double)size, 5.0D) ? size / 5 : size / 5 + 1;
         int page = 1;
         if (args.length > 0) {
            String textPage = args[0];
            if (MathUtil.isNumber(textPage)) {
               page = MathUtil.parseInteger(textPage);
               page = MathUtil.limitInteger(page, 1, maxPage);
            }
         }

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
            HashMap<String, String> subMap = new HashMap();
            String listItemMessage = lang.getText(sender, "List_Container");
            subMap.put("index", String.valueOf(index + 1));
            subMap.put("container", key);
            subMap.put("maxpage", String.valueOf(page));
            listItemMessage = TextUtil.placeholder(subMap, listItemMessage);
            list.add(key);
            SenderUtil.sendMessage(sender, listItemMessage);
         }

         SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
         return list;
      }
   }
}
