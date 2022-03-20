package com.praya.myitems.menu;

import api.praya.agarthalib.builder.support.SupportVault;
import api.praya.agarthalib.main.AgarthaLibAPI;
import api.praya.agarthalib.manager.plugin.SupportManagerAPI;
import api.praya.myitems.builder.socket.SocketGems;
import api.praya.myitems.builder.socket.SocketGemsTree;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.MetadataUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerMenu;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.builder.menu.Menu;
import core.praya.agarthalib.builder.menu.MenuExecutor;
import core.praya.agarthalib.builder.menu.MenuGUI;
import core.praya.agarthalib.builder.menu.MenuSlot;
import core.praya.agarthalib.builder.menu.MenuGUI.SlotCell;
import core.praya.agarthalib.builder.menu.MenuSlotAction.ActionCategory;
import core.praya.agarthalib.builder.menu.MenuSlotAction.ActionType;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.RomanNumber;
import core.praya.agarthalib.enums.main.SlotType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

public class MenuSocket extends HandlerMenu implements MenuExecutor {
   public MenuSocket(MyItems plugin) {
      super(plugin);
   }

   public void onClick(Player player, Menu menu, ActionType actionType, String... args) {
      AgarthaLibAPI agarthaLibAPI = AgarthaLibAPI.getInstance();
      PluginManager pluginManager = this.plugin.getPluginManager();
      GameManager gameManager = this.plugin.getGameManager();
      SocketManager socketManager = gameManager.getSocketManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      SupportManagerAPI supportManager = agarthaLibAPI.getPluginManagerAPI().getSupportManager();
      SupportVault supportVault = supportManager.getSupportVault();
      MainConfig mainConfig = MainConfig.getInstance();
      if (menu instanceof MenuGUI) {
         MenuGUI menuGUI = (MenuGUI)menu;
         if (args.length > 0) {
            String label = args[0];
            if (label.equalsIgnoreCase("MyItems") && args.length > 1) {
               String key = args[1];
               if (key.equalsIgnoreCase("Socket") && args.length > 2) {
                  String subArg = args[2];
                  String metadataID = "MyItems Socket Line_Selector";
                  String textLine;
                  ItemStack itemResult;
                  int line;
                  int amountItem;
                  String headerItemInput;
                  String headerSocketInput;
                  ItemStack itemItemInput;
                  ItemStack itemSocketInput;
                  ItemStack itemItem;
                  if (!subArg.equalsIgnoreCase("Item_Input") && !subArg.equalsIgnoreCase("Socket_Input")) {
                     if (subArg.equalsIgnoreCase("Line_Selector")) {
                        if (args.length > 3) {
                           textLine = args[3];
                           line = MathUtil.parseInteger(textLine);
                           MetadataValue metadata = MetadataUtil.createMetadata(line);
                           player.setMetadata("MyItems Socket Line_Selector", metadata);
                           this.updateSocketMenu(menuGUI, player);
                           return;
                        }
                     } else if (subArg.equalsIgnoreCase("Accept")) {
                        SlotCell cellItemResult = SlotCell.G3;
                        Inventory inventory = menu.getInventory();
                        String headerItemItemResult = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Item_Result");
                        ItemStack itemItemResult = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerItemItemResult, 1);
                        itemResult = inventory.getItem(cellItemResult.getIndex());
                        if (!itemResult.isSimilar(itemItemResult)) {
                           SlotCell cellItemInput = SlotCell.B3;
                           SlotCell cellSocketInput = SlotCell.C3;
                           headerItemInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Item_Input");
                           headerSocketInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Socket_Input");
                           itemItemInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerItemInput, 1);
                           itemSocketInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerSocketInput, 1);
                           itemItem = inventory.getItem(cellItemInput.getIndex());
                           ItemStack itemSocket = inventory.getItem(cellSocketInput.getIndex());
                           ItemStack itemRodUnlock = mainConfig.getSocketItemRodUnlock();
                           ItemStack itemRodRemove = mainConfig.getSocketItemRodRemove();
                           line = MetadataUtil.getMetadata(player, "MyItems Socket Line_Selector").asInt();
                           amountItem = itemItem.getAmount();
                           int amountSocket = itemSocket.getAmount();
                           byte actionID;
                           double actionCost;
                           if (socketManager.isSocketItem(itemSocket)) {
                              actionID = 0;
                              actionCost = mainConfig.getSocketCostSocket();
                           } else if (itemSocket.isSimilar(itemRodUnlock)) {
                              actionID = 1;
                              actionCost = mainConfig.getSocketCostUnlock();
                           } else if (itemSocket.isSimilar(itemRodRemove)) {
                              actionID = 2;
                              actionCost = mainConfig.getSocketCostDesocket();
                           } else {
                              actionID = -1;
                              actionCost = 0.0D;
                           }

                           if (supportVault != null) {
                              double playerBalance = supportVault.getBalance(player);
                              if (playerBalance < actionCost) {
                                 String message = lang.getText((LivingEntity)player, "Argument_Lack_Money");
                                 SenderUtil.sendMessage(player, message);
                                 SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return;
                              }

                              supportVault.remBalance(player, actionCost);
                           }

                           int amountItemLeft;
                           if (amountSocket > 1) {
                              amountItemLeft = amountSocket - 1;
                              itemSocket.setAmount(amountItemLeft);
                              inventory.setItem(cellSocketInput.getIndex(), itemSocket);
                           } else {
                              inventory.setItem(cellSocketInput.getIndex(), itemSocketInput);
                           }

                           String message;
                           if (actionID == 0) {
                              SocketGems socketBuild = socketManager.getSocketBuild(itemSocket);
                              message = socketManager.getSocketName(itemSocket);
                              double chance = socketBuild.getSuccessRate();
                              int grade = socketBuild.getGrade();
                              if (!MathUtil.chanceOf(chance)) {
                                  message = lang.getText((LivingEntity)player, "Socket_Input_Failure");
                                 SenderUtil.sendMessage(player, message);
                                 SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return;
                              }

                              HashMap<String, String> map = new HashMap();
                               message = lang.getText((LivingEntity)player, "Socket_Input_Success");
                              map.put("socket", message);
                              map.put("grade", RomanNumber.getRomanNumber(grade));
                              map.put("line", String.valueOf(line));
                              message = TextUtil.placeholder(map, message);
                              PlayerUtil.addItem(player, itemResult);
                              SenderUtil.sendMessage(player, message);
                              SenderUtil.playSound(player, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                           } else {
                              HashMap map;
                              if (actionID == 1) {
                                 map = new HashMap();
                                 message = lang.getText((LivingEntity)player, "Socket_Unlock_Success");
                                 map.put("line", String.valueOf(line));
                                 message = TextUtil.placeholder(map, message);
                                 PlayerUtil.addItem(player, itemResult);
                                 SenderUtil.sendMessage(player, message);
                                 SenderUtil.playSound(player, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                              } else if (actionID == 2) {
                                 map = new HashMap();
                                 message = lang.getText((LivingEntity)player, "Socket_Remove_Success");
                                 map.put("line", String.valueOf(line));
                                 message = TextUtil.placeholder(map, message);
                                 PlayerUtil.addItem(player, itemResult);
                                 SenderUtil.sendMessage(player, message);
                                 SenderUtil.playSound(player, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                              }
                           }

                           if (amountItem > 1) {
                              amountItemLeft = amountItem - 1;
                              itemItem.setAmount(amountItemLeft);
                              inventory.setItem(cellItemInput.getIndex(), itemItem);
                           } else {
                              inventory.setItem(cellItemInput.getIndex(), itemItemInput);
                           }
                        }
                     }
                  } else if (args.length > 3) {
                     textLine = args[3];
                     SlotCell cell = SlotCell.valueOf(textLine);
                     Inventory inventory = menu.getInventory();
                     int index = cell.getIndex();
                     itemResult = inventory.getItem(index);
                     ItemStack itemCursor = player.getItemOnCursor();
                     boolean isSocketSlot = subArg.equalsIgnoreCase("Socket_Input");
                     if (EquipmentUtil.isSolid(itemCursor)) {
                        boolean isGems;
                        if (isSocketSlot) {
                           ItemStack itemRodUnlock = mainConfig.getSocketItemRodUnlock();
                           ItemStack itemRodRemove = mainConfig.getSocketItemRodRemove();
                           isGems = socketManager.isSocketItem(itemCursor);
                           boolean isRodUnlock = itemCursor.isSimilar(itemRodUnlock);
                           boolean isRodRemove = itemCursor.isSimilar(itemRodRemove);
                           if (!isGems && !isRodUnlock && !isRodRemove) {
                              return;
                           }
                        } else {
                           boolean containsSocketEmpty = socketManager.containsSocketEmpty(itemCursor);
                           boolean containsSocketGems = socketManager.containsSocketGems(itemCursor);
                           isGems = socketManager.containsSocketLocked(itemCursor);
                           if (!containsSocketEmpty && !containsSocketGems && !isGems) {
                              return;
                           }
                        }
                     }

                     if (EquipmentUtil.isSolid(itemResult)) {
                        headerItemInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Item_Input");
                        headerSocketInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Socket_Input");
                        itemItemInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerItemInput, 1);
                        itemSocketInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerSocketInput, 1);
                        if (!itemResult.isSimilar(itemItemInput) && !itemResult.isSimilar(itemSocketInput)) {
                           if (!EquipmentUtil.isSolid(itemCursor)) {
                              itemItem = isSocketSlot ? itemSocketInput : itemItemInput;
                              player.setItemOnCursor(itemResult);
                              inventory.setItem(index, itemItem);
                           } else if (itemResult.isSimilar(itemCursor)) {
                              int amountCurrent = itemResult.getAmount();
                              int amountCursor = itemCursor.getAmount();
                              int amountTotal = amountCurrent + amountCursor;
                              int maxStack = itemResult.getMaxStackSize();
                              line = MathUtil.limitInteger(amountTotal, 1, maxStack);
                              amountItem = amountTotal - line;
                              itemResult.setAmount(line);
                              itemCursor.setAmount(amountItem);
                              inventory.setItem(index, itemResult);
                              if (amountItem == 0) {
                                 player.setItemOnCursor((ItemStack)null);
                              } else {
                                 player.setItemOnCursor(itemCursor);
                              }
                           } else {
                              inventory.setItem(index, itemCursor);
                              player.setItemOnCursor(itemResult);
                           }
                        } else {
                           if (!EquipmentUtil.isSolid(itemCursor)) {
                              return;
                           }

                           inventory.setItem(index, itemCursor);
                           player.setItemOnCursor((ItemStack)null);
                        }

                        MetadataUtil.removeMetadata(player, "MyItems Socket Line_Selector");
                        this.updateSocketMenu(menuGUI, player);
                        return;
                     }
                  }
               }
            }
         }
      }

   }

   public final void updateSocketMenu(MenuGUI menuGUI, Player player) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      GameManager gameManager = this.plugin.getGameManager();
      SocketManager socketManager = gameManager.getSocketManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      MainConfig mainConfig = MainConfig.getInstance();
      String metadataID = "MyItems Socket Line_Selector";
      String headerLineSelector = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Line_Selector");
      SlotCell cellItemInput = SlotCell.B3;
      SlotCell cellSocketInput = SlotCell.C3;
      SlotCell cellItemResult = SlotCell.G3;
      SlotCell[] cellsLineSelector = new SlotCell[]{SlotCell.F2, SlotCell.G2, SlotCell.H2};
      MenuSlot menuSlotItemResult = new MenuSlot(cellItemResult.getIndex());
      Inventory inventory = menuGUI.getInventory();
      ItemStack itemItemInput = inventory.getItem(cellItemInput.getIndex());
      ItemStack itemSocketInput = inventory.getItem(cellSocketInput.getIndex());
      HashMap<String, String> map = new HashMap();
      int socketActionID = -1;
      int socketLine = -1;
      int socketLinePrevious = -1;
      int socketLineNext = -1;
      List<String> loreLineSelector = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Socket_Line_Selector");
      if (EquipmentUtil.isSolid(itemItemInput) && EquipmentUtil.isSolid(itemSocketInput)) {
         ItemStack itemRodUnlock = mainConfig.getSocketItemRodUnlock();
         ItemStack itemRodRemove = mainConfig.getSocketItemRodRemove();
         boolean containsSocketEmpty = socketManager.containsSocketEmpty(itemItemInput);
         boolean containsSocketLocked = socketManager.containsSocketLocked(itemItemInput);
         boolean containsSocketGems = socketManager.containsSocketGems(itemItemInput);
         boolean isSocketGems = socketManager.isSocketItem(itemSocketInput);
         boolean isSocketRodUnlock = itemSocketInput.isSimilar(itemRodUnlock);
         boolean isSocketRodRemove = itemSocketInput.isSimilar(itemRodRemove);
         if (containsSocketEmpty && isSocketGems) {
            SocketGems socket = socketManager.getSocketBuild(itemSocketInput);
            SocketGemsTree socketTree = socket.getSocketTree();
            SlotType typeItem = socketTree.getTypeItem();
            SlotType typeDefault = SlotType.getSlotType(itemItemInput);
            if (typeItem.equals(typeDefault) || typeItem.equals(SlotType.UNIVERSAL)) {
               socketActionID = 0;
            }
         } else if (containsSocketLocked && isSocketRodUnlock) {
            socketActionID = 1;
         } else if (containsSocketGems && isSocketRodRemove) {
            socketActionID = 2;
         }

         if (socketActionID != -1) {
            Object listLineSocket;
            switch(socketActionID) {
            case 0:
               listLineSocket = socketManager.getLineLoresSocketEmpty(itemItemInput);
               break;
            case 1:
               listLineSocket = socketManager.getLineLoresSocketLocked(itemItemInput);
               break;
            case 2:
               listLineSocket = socketManager.getLineLoresSocket(itemItemInput);
               break;
            default:
               listLineSocket = new ArrayList();
            }

            int size = ((List)listLineSocket).size();
            boolean hasMetadataLine = MetadataUtil.hasMetadata(player, "MyItems Socket Line_Selector");
            if (hasMetadataLine) {
               int metadataLine = MetadataUtil.getMetadata(player, "MyItems Socket Line_Selector").asInt();
               int order = 0;

               for(int index = 0; index < size; ++index) {
                  if ((Integer)((List)listLineSocket).get(index) == metadataLine) {
                     order = index;
                     break;
                  }
               }

               socketLine = ((List)listLineSocket).contains(metadataLine) ? metadataLine : (Integer)((List)listLineSocket).get(0);
               socketLinePrevious = order == 0 ? (Integer)((List)listLineSocket).get(size - 1) : (Integer)((List)listLineSocket).get(order - 1);
               socketLineNext = order == size - 1 ? (Integer)((List)listLineSocket).get(0) : (Integer)((List)listLineSocket).get(order + 1);
            } else {
               socketLine = (Integer)((List)listLineSocket).get(0);
               socketLinePrevious = (Integer)((List)listLineSocket).get(size - 1);
               socketLineNext = size > 1 ? (Integer)((List)listLineSocket).get(1) : (Integer)((List)listLineSocket).get(0);
            }
         }
      }

      String socketAction;
      double socketCost;
      if (socketActionID == 0) {
         socketAction = lang.getText((LivingEntity)player, "Socket_Action_Gems");
         socketCost = mainConfig.getSocketCostSocket();
      } else if (socketActionID == 1) {
         socketAction = lang.getText((LivingEntity)player, "Socket_Action_Unlock");
         socketCost = mainConfig.getSocketCostUnlock();
      } else if (socketActionID == 2) {
         socketAction = lang.getText((LivingEntity)player, "Socket_Action_Desocket");
         socketCost = mainConfig.getSocketCostDesocket();
      } else {
         socketAction = lang.getText((LivingEntity)player, "Socket_Action_Unknown");
         socketCost = 0.0D;
      }

      map.put("socket_line", socketLine == -1 ? "None" : String.valueOf(socketLine));
      map.put("socket_action", String.valueOf(socketAction));
      map.put("socket_cost", String.valueOf(socketCost));
      map.put("symbol_currency", mainConfig.getUtilityCurrency());
      loreLineSelector = TextUtil.placeholder(map, loreLineSelector);
      ItemStack itemLineSelector = EquipmentUtil.createItem(MaterialEnum.SIGN, headerLineSelector, 1, loreLineSelector);
      SlotCell[] var50 = cellsLineSelector;
      int var46 = cellsLineSelector.length;

      int gemsGrade;
      for(int var44 = 0; var44 < var46; ++var44) {
         SlotCell cell = var50[var44];
         gemsGrade = cell.getIndex();
         MenuSlot menuSlot = new MenuSlot(gemsGrade);
         String actionLinePrevious = "MyItems Socket Line_Selector " + socketLinePrevious;
         String actionLineNext = "MyItems Socket Line_Selector " + socketLineNext;
         menuSlot.setItem(itemLineSelector);
         menuSlot.setActionArguments(ActionCategory.ALL_LEFT_CLICK, actionLinePrevious);
         menuSlot.setActionArguments(ActionCategory.ALL_RIGHT_CLICK, actionLineNext);
         menuGUI.setMenuSlot(menuSlot);
      }

      if (socketActionID == -1) {
         String headerItemItemResult = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Item_Result");
         ItemStack itemItemResult = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerItemItemResult, 1);
         menuSlotItemResult.setItem(itemItemResult);
         menuGUI.setMenuSlot(menuSlotItemResult);
      } else {
         ItemStack itemItemResult = itemItemInput.clone();
         MetadataValue metadata = MetadataUtil.createMetadata(socketLine);
         if (socketActionID == 0) {
            SocketGems socketBuild = socketManager.getSocketBuild(itemSocketInput);
            String gemsID = socketManager.getSocketName(itemSocketInput);
            gemsGrade = socketBuild.getGrade();
            String loreGems = socketManager.getTextSocketGemsLore(gemsID, gemsGrade);
            EquipmentUtil.setLore(itemItemResult, socketLine, loreGems);
         } else if (socketActionID == 1 || socketActionID == 2) {
            String loreEmpty = socketManager.getTextSocketSlotEmpty();
            EquipmentUtil.setLore(itemItemResult, socketLine, loreEmpty);
         }

         itemItemResult.setAmount(1);
         player.setMetadata("MyItems Socket Line_Selector", metadata);
         menuSlotItemResult.setItem(itemItemResult);
         menuGUI.setMenuSlot(menuSlotItemResult);
      }

      player.updateInventory();
   }
}
