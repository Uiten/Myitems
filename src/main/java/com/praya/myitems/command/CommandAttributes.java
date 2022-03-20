package com.praya.myitems.command;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import api.praya.myitems.builder.passive.PassiveTypeEnum;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import api.praya.myitems.requirement.RequirementEnum;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.ServerUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerCommand;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import com.praya.myitems.manager.game.PowerCommandManager;
import com.praya.myitems.manager.game.PowerManager;
import com.praya.myitems.manager.game.PowerShootManager;
import com.praya.myitems.manager.game.PowerSpecialManager;
import com.praya.myitems.manager.game.RequirementManager;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.manager.register.RegisterAbilityWeaponManager;
import com.praya.myitems.manager.register.RegisterManager;
import com.praya.myitems.utility.main.ProjectileUtil;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.RomanNumber;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.enums.main.TagsAttribute;
import core.praya.agarthalib.enums.main.VersionNMS;
import java.util.HashMap;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandAttributes extends HandlerCommand implements CommandExecutor {
   public CommandAttributes(MyItems plugin) {
      super(plugin);
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      GameManager gameManager = this.plugin.getGameManager();
      RegisterManager registerManager = this.plugin.getRegisterManager();
      PowerManager powerManager = gameManager.getPowerManager();
      PowerCommandManager powerCommandManager = powerManager.getPowerCommandManager();
      PowerShootManager powerShootManager = powerManager.getPowerShootManager();
      PowerSpecialManager powerSpecialManager = powerManager.getPowerSpecialManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      ElementManager elementManager = gameManager.getElementManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      RequirementManager requirementManager = gameManager.getRequirementManager();
      PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      RegisterAbilityWeaponManager registerAbilityWeaponManager = registerManager.getRegisterAbilityWeaponManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (args.length <= 0) {
         String[] fullArgs = TextUtil.pressList(args, 2);
         return CommandMyItems.help(sender, command, label, fullArgs);
      } else {
         String subCommand = args[0];
         MessageBuild message;
         ItemStack item;
         String textClass;
         String lore;
         HashMap mapPlaceholder = null;
         Player player;
         String ability;
         int lastLine;
         double cooldown;
         if (commandManager.checkCommand(subCommand, "Attribute_Stats")) {
            if (!commandManager.checkPermission(sender, "Attribute_Stats")) {
               ability = commandManager.getPermission("Attribute_Stats");
               message = lang.getMessage(sender, "Permission_Lack");
               message.sendMessage(sender, "permission", ability);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
               return true;
            } else if (!SenderUtil.isPlayer(sender)) {
               message = lang.getMessage(sender, "Console_Command_Forbiden");
               message.sendMessage(sender);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
               return true;
            } else if (args.length < 3) {
               ability = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Stats"));
               message = lang.getMessage(sender, "Argument_Attribute_Stats");
               message.sendMessage(sender, "tooltip_att_stats", ability);
               SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
               return true;
            } else {
               player = PlayerUtil.parse(sender);
               item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
               LoreStatsEnum loreStats = LoreStatsEnum.get(args[1]);
               if (!EquipmentUtil.isSolid(item)) {
                  message = lang.getMessage(sender, "Item_MainHand_Empty");
                  message.sendMessage(sender);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                  return true;
               } else if (loreStats == null) {
                  message = lang.getMessage(sender, "MyItems_Attribute_Stats_Not_Exists");
                  message.sendMessage(sender, "stats", args[1]);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                  return true;
               } else {
                  textClass = args[2];
                  double value2;
                  if (args[2].contains(":")) {
                     String[] valueBuild = textClass.split(":");
                     if (!MathUtil.isNumber(valueBuild[0]) || !MathUtil.isNumber(valueBuild[1])) {
                        message = lang.getMessage(sender, "Argument_Invalid_Value");
                        message.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     }

                     cooldown = MathUtil.parseDouble(valueBuild[0]);
                     value2 = MathUtil.parseDouble(valueBuild[1]);
                  } else {
                     if (!MathUtil.isNumber(textClass)) {
                        message = lang.getMessage(sender, "Argument_Invalid_Value");
                        message.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     }

                     cooldown = MathUtil.parseDouble(args[2]);
                     if (loreStats.equals(LoreStatsEnum.LEVEL)) {
                        value2 = 0.0D;
                     } else {
                        value2 = cooldown;
                     }
                  }

                  if (args.length > 3) {
                     lore = args[3];
                     if (!MathUtil.isNumber(lore)) {
                        message = lang.getMessage(sender, "Argument_Invalid_Value");
                        message.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     }

                     lastLine = MathUtil.parseInteger(args[3]);
                     if (lastLine < 1) {
                        message = lang.getMessage(sender, "Argument_Invalid_Value");
                        message.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     }

                     if (EquipmentUtil.hasLore(item)) {
                        lastLine = statsManager.getLineLoreStats(item, loreStats);
                        if (lastLine != -1 && lastLine != lastLine) {
                           EquipmentUtil.setLore(item, lastLine, "");
                        }
                     }
                  } else if (EquipmentUtil.hasLore(item)) {
                     lastLine = statsManager.getLineLoreStats(item, loreStats);
                     if (lastLine == -1) {
                        lastLine = EquipmentUtil.getLores(item).size() + 1;
                     }
                  } else {
                     lastLine = 1;
                  }

                  lore = statsManager.getTextLoreStats(loreStats, cooldown, value2);
                  message = lang.getMessage(sender, "MyItems_Attribute_Stats_Success");
                  mapPlaceholder = new HashMap();
                  mapPlaceholder.put("stats", loreStats.getText());
                  mapPlaceholder.put("value", statsManager.statsValue(loreStats, cooldown, value2));
                  EquipmentUtil.setLore(item, lastLine, lore);
                  TriggerSupportUtil.updateSupport(player);
                  message.sendMessage(sender, mapPlaceholder);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                  player.updateInventory();
                  return true;
               }
            }
         } else {
            int line;
            String powerSpecial;
            if (commandManager.checkCommand(subCommand, "Attribute_Element")) {
               if (!commandManager.checkPermission(sender, "Attribute_Element")) {
                  ability = commandManager.getPermission("Attribute_Element");
                  message = lang.getMessage(sender, "Permission_Lack");
                  message.sendMessage(sender, "permission", ability);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                  return true;
               } else if (!SenderUtil.isPlayer(sender)) {
                  message = lang.getMessage(sender, "Console_Command_Forbiden");
                  message.sendMessage(sender);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                  return true;
               } else if (args.length < 3) {
                  ability = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Element"));
                  message = lang.getMessage(sender, "Argument_Attribute_Element");
                  message.sendMessage(sender, "tooltip_att_element", ability);
                  SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                  return true;
               } else {
                  player = PlayerUtil.parse(sender);
                  item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                  String element = elementManager.getRawElement(args[1]);
                  if (!EquipmentUtil.isSolid(item)) {
                     message = lang.getMessage(sender, "Item_MainHand_Empty");
                     message.sendMessage(sender);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else if (element == null) {
                     message = lang.getMessage(sender, "MyItems_Attribute_Element_Not_Exists");
                     message.sendMessage(sender, "element", args[1]);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else {
                     textClass = args[2];
                     if (!MathUtil.isNumber(textClass)) {
                        message = lang.getMessage(sender, "Argument_Invalid_Value");
                        message.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else {
                        cooldown = MathUtil.parseDouble(textClass);
                        if (args.length > 3) {
                           powerSpecial = args[3];
                           if (!MathUtil.isNumber(powerSpecial)) {
                              message = lang.getMessage(sender, "Argument_Invalid_Value");
                              message.sendMessage(sender);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           }

                           line = MathUtil.parseInteger(args[3]);
                           if (line < 1) {
                              message = lang.getMessage(sender, "Argument_Invalid_Value");
                              message.sendMessage(sender);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           }

                           if (EquipmentUtil.hasLore(item)) {
                              lastLine = elementManager.getLineElement(item, element);
                              if (lastLine != -1 && lastLine != line) {
                                 EquipmentUtil.setLore(item, lastLine, "");
                              }
                           }
                        } else if (EquipmentUtil.hasLore(item)) {
                           line = elementManager.getLineElement(item, element);
                           if (line == -1) {
                              line = EquipmentUtil.getLores(item).size() + 1;
                           }
                        } else {
                           line = 1;
                        }

                        powerSpecial = elementManager.getTextElement(element, cooldown);
                        message = lang.getMessage(sender, "MyItems_Attribute_Element_Success");
                        mapPlaceholder = new HashMap();
                        mapPlaceholder.put("element", element);
                        mapPlaceholder.put("value", String.valueOf(cooldown));
                        EquipmentUtil.setLore(item, line, powerSpecial);
                        message.sendMessage(sender, mapPlaceholder);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                        player.updateInventory();
                        return true;
                     }
                  }
               }
            } else {
               PassiveEffectEnum effect;
               int loreSize;
               if (commandManager.checkCommand(subCommand, "Attribute_Buff")) {
                  if (!commandManager.checkPermission(sender, "Attribute_Buff")) {
                     ability = commandManager.getPermission("Attribute_Buff");
                     message = lang.getMessage(sender, "Permission_Lack");
                     message.sendMessage(sender, "permission", ability);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else if (!SenderUtil.isPlayer(sender)) {
                     message = lang.getMessage(sender, "Console_Command_Forbiden");
                     message.sendMessage(sender);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else if (args.length < 3) {
                     ability = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Buffs"));
                     message = lang.getMessage(sender, "Argument_Attribute_Buffs");
                     message.sendMessage(sender, "tooltip_att_buffs", ability);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else {
                     player = PlayerUtil.parse(sender);
                     item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                     effect = PassiveEffectEnum.get(args[1]);
                     if (!EquipmentUtil.isSolid(item)) {
                        message = lang.getMessage(sender, "Item_MainHand_Empty");
                        message.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else if (effect != null && effect.getType().equals(PassiveTypeEnum.BUFF)) {
                        textClass = args[2];
                        if (!MathUtil.isNumber(textClass)) {
                           message = lang.getMessage(sender, "Argument_Invalid_Value");
                           message.sendMessage(sender);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else {
                           line = MathUtil.parseInteger(textClass);
                           line = MathUtil.limitInteger(line, 1, 10);
                           if (args.length > 3) {
                              lore = args[3];
                              if (!MathUtil.isNumber(lore)) {
                                 message = lang.getMessage(sender, "Argument_Invalid_Value");
                                 message.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              }

                              line = MathUtil.parseInteger(args[3]);
                              if (line < 1) {
                                 message = lang.getMessage(sender, "Argument_Invalid_Value");
                                 message.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              }

                              if (EquipmentUtil.hasLore(item)) {
                                 loreSize = passiveEffectManager.getLinePassiveEffect(item, effect);
                                 if (loreSize != -1 && loreSize != line) {
                                    EquipmentUtil.setLore(item, loreSize, "");
                                 }
                              }
                           } else if (EquipmentUtil.hasLore(item)) {
                              line = passiveEffectManager.getLinePassiveEffect(item, effect);
                              if (line == -1) {
                                 line = EquipmentUtil.getLores(item).size() + 1;
                              }
                           } else {
                              line = 1;
                           }

                           lore = passiveEffectManager.getTextPassiveEffect(effect, line);
                           message = lang.getMessage(sender, "MyItems_Attribute_Buffs_Success");
                           mapPlaceholder = new HashMap();
                           mapPlaceholder.put("buff", effect.getText());
                           mapPlaceholder.put("buffs", effect.getText());
                           mapPlaceholder.put("grade", RomanNumber.getRomanNumber(line));
                           EquipmentUtil.setLore(item, line, lore);
                           message.sendMessage(sender, mapPlaceholder);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                           player.updateInventory();
                           return true;
                        }
                     } else {
                        message = lang.getMessage(sender, "MyItems_Attribute_Buffs_Not_Exists");
                        message.sendMessage(sender, "buff", args[1]);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     }
                  }
               } else if (commandManager.checkCommand(subCommand, "Attribute_Debuff")) {
                  if (!commandManager.checkPermission(sender, "Attribute_Debuff")) {
                     ability = commandManager.getPermission("Attribute_Debuff");
                     message = lang.getMessage(sender, "Permission_Lack");
                     message.sendMessage(sender, "permission", ability);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else if (!SenderUtil.isPlayer(sender)) {
                     message = lang.getMessage(sender, "Console_Command_Forbiden");
                     message.sendMessage(sender);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else if (args.length < 3) {
                     ability = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Debuffs"));
                     message = lang.getMessage(sender, "Argument_Attribute_Debuffs");
                     message.sendMessage(sender, "tooltip_att_debuffs", ability);
                     SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                     return true;
                  } else {
                     player = PlayerUtil.parse(sender);
                     item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                     effect = PassiveEffectEnum.get(args[1]);
                     if (!EquipmentUtil.isSolid(item)) {
                        message = lang.getMessage(sender, "Item_MainHand_Empty");
                        message.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else if (effect != null && effect.getType().equals(PassiveTypeEnum.DEBUFF)) {
                        textClass = args[2];
                        if (!MathUtil.isNumber(textClass)) {
                           message = lang.getMessage(sender, "Argument_Invalid_Value");
                           message.sendMessage(sender);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else {
                           line = MathUtil.parseInteger(textClass);
                           line = MathUtil.limitInteger(line, 1, 10);
                           if (args.length > 3) {
                              lore = args[3];
                              if (!MathUtil.isNumber(lore)) {
                                 message = lang.getMessage(sender, "Argument_Invalid_Value");
                                 message.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              }

                              line = MathUtil.parseInteger(args[3]);
                              if (line < 1) {
                                 message = lang.getMessage(sender, "Argument_Invalid_Value");
                                 message.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              }

                              if (EquipmentUtil.hasLore(item)) {
                                 loreSize = passiveEffectManager.getLinePassiveEffect(item, effect);
                                 if (loreSize != -1 && loreSize != line) {
                                    EquipmentUtil.setLore(item, loreSize, "");
                                 }
                              }
                           } else if (EquipmentUtil.hasLore(item)) {
                              line = passiveEffectManager.getLinePassiveEffect(item, effect);
                              if (line == -1) {
                                 line = EquipmentUtil.getLores(item).size() + 1;
                              }
                           } else {
                              line = 1;
                           }

                           lore = passiveEffectManager.getTextPassiveEffect(effect, line);
                           message = lang.getMessage(sender, "MyItems_Attribute_Debuffs_Success");
                           mapPlaceholder = new HashMap();
                           mapPlaceholder.put("debuff", effect.getText());
                           mapPlaceholder.put("debuffs", effect.getText());
                           mapPlaceholder.put("grade", RomanNumber.getRomanNumber(line));
                           EquipmentUtil.setLore(item, line, lore);
                           message.sendMessage(sender, mapPlaceholder);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                           player.updateInventory();
                           return true;
                        }
                     } else {
                        message = lang.getMessage(sender, "MyItems_Attribute_Debuffs_Not_Exists");
                        message.sendMessage(sender, "debuff", args[1]);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     }
                  }
               } else {
                  if (commandManager.checkCommand(subCommand, "Attribute_NBT")) {
                     if (!ServerUtil.isCompatible(VersionNMS.V1_8_R1)) {
                        message = lang.getMessage(sender, "MyItems_Not_Compatible");
                        message.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else if (!commandManager.checkPermission(sender, "Attribute_NBT")) {
                        ability = commandManager.getPermission("Attribute_NBT");
                        message = lang.getMessage(sender, "Permission_Lack");
                        message.sendMessage(sender, "permission", ability);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else if (!SenderUtil.isPlayer(sender)) {
                        message = lang.getMessage(sender, "Console_Command_Forbiden");
                        message.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else if (args.length < 3) {
                        ability = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_NBT"));
                        message = lang.getMessage(sender, "Argument_Attribute_NBT");
                        message.sendMessage(sender, "tooltip_att_nbt", ability);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else {
                        player = PlayerUtil.parse(sender);
                        item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                        TagsAttribute tags = TagsAttribute.getTagsAttribute(args[1]);
                        if (!EquipmentUtil.isSolid(item)) {
                           message = lang.getMessage(sender, "Item_MainHand_Empty");
                           message.sendMessage(sender);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else if (tags == null) {
                           message = lang.getMessage(sender, "MyItems_Attribute_NBT_Not_Exists");
                           mapPlaceholder = new HashMap();
                           mapPlaceholder.put("NBT", args[1]);
                           mapPlaceholder.put("Tags", args[1]);
                           message.sendMessage(sender, mapPlaceholder);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else {
                           textClass = args[2];
                           if (!MathUtil.isNumber(textClass)) {
                              message = lang.getMessage(sender, "Argument_Invalid_Value");
                              message.sendMessage(sender);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else {
                              cooldown = MathUtil.parseDouble(textClass);
                              Slot slot;
                              if (args.length > 3) {
                                 powerSpecial = args[3];
                                 slot = Slot.get(powerSpecial);
                                 if (slot == null) {
                                    slot = Slot.getDefault(item.getType());
                                 }
                              } else {
                                 slot = Slot.getDefault(item.getType());
                              }

                              message = lang.getMessage(sender, "MyItems_Attribute_NBT_Success");
                              mapPlaceholder = new HashMap();
                              mapPlaceholder.put("NBT", TextUtil.firstSolidCharacter(String.valueOf(tags)));
                              mapPlaceholder.put("Value", String.valueOf(cooldown));
                              Bridge.getBridgeTagsNBT().addNBT(item, tags, cooldown, slot);
                              message.sendMessage(sender, mapPlaceholder);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                              player.updateInventory();
                              return true;
                           }
                        }
                     }
                  } else if (commandManager.checkCommand(subCommand, "Attribute_Ability")) {
                     if (!commandManager.checkPermission(sender, "Attribute_Ability")) {
                        ability = commandManager.getPermission("Attribute_Ability");
                        message = lang.getMessage(sender, "Permission_Lack");
                        message.sendMessage(sender, "permission", ability);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else if (!SenderUtil.isPlayer(sender)) {
                        message = lang.getMessage(sender, "Console_Command_Forbiden");
                        message.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else if (args.length < 3) {
                        ability = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Ability"));
                        message = lang.getMessage(sender, "Argument_Attribute_Ability");
                        message.sendMessage(sender, "tooltip_att_ability", ability);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else {
                        ability = args[1];
                        player = PlayerUtil.parse(sender);
                        item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                        AbilityWeapon abilityWeapon = registerAbilityWeaponManager.getAbilityWeapon(ability);
                        if (!EquipmentUtil.isSolid(item)) {
                           message = lang.getMessage(sender, "Item_MainHand_Empty");
                           message.sendMessage(sender);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else if (abilityWeapon == null) {
                           message = lang.getMessage(sender, "MyItems_Attribute_Ability_Not_Exists");
                           message.sendMessage(sender, "ability", args[1]);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else {
                           String textGrade = args[2];
                           double chance;
                           if (args.length > 3) {
                              lore = args[3];
                              if (!MathUtil.isNumber(lore)) {
                                 message = lang.getMessage(sender, "Argument_Invalid_Value");
                                 message.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              }

                              chance = MathUtil.parseDouble(lore);
                              chance = MathUtil.roundNumber(chance);
                              chance = MathUtil.limitDouble(chance, 0.0D, 100.0D);
                           } else {
                              chance = 100.0D;
                           }

                           if (!MathUtil.isNumber(textGrade)) {
                              message = lang.getMessage(sender, "Argument_Invalid_Value");
                              message.sendMessage(sender);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           } else {
                              int maxGrade = abilityWeapon.getMaxGrade();
                              loreSize = MathUtil.parseInteger(textGrade);
                              loreSize = MathUtil.limitInteger(loreSize, 1, maxGrade);
                              if (args.length > 4) {
                                 lore = args[4];
                                 if (!MathUtil.isNumber(lore)) {
                                    message = lang.getMessage(sender, "Argument_Invalid_Value");
                                    message.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }

                                 lastLine = MathUtil.parseInteger(args[4]);
                                 if (lastLine < 1) {
                                    message = lang.getMessage(sender, "Argument_Invalid_Value");
                                    message.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }

                                 if (EquipmentUtil.hasLore(item)) {
                                    Integer abilityLine = abilityWeaponManager.getLineAbilityItemWeapon(item, ability);
                                    if (abilityLine != null && abilityLine != lastLine) {
                                       EquipmentUtil.setLore(item, abilityLine, "");
                                    }
                                 }
                              } else if (EquipmentUtil.hasLore(item)) {
                                 Integer abilityLine = abilityWeaponManager.getLineAbilityItemWeapon(item, ability);
                                 lastLine = abilityLine != null ? abilityLine : EquipmentUtil.getLores(item).size() + 1;
                              } else {
                                 lastLine = 1;
                              }

                              lore = abilityWeaponManager.getTextAbility(ability, loreSize, chance);
                              message = lang.getMessage(sender, "MyItems_Attribute_Ability_Success");
                              mapPlaceholder = new HashMap();
                              mapPlaceholder.put("ability", abilityWeapon.getKeyLore());
                              mapPlaceholder.put("grade", RomanNumber.getRomanNumber(loreSize));
                              EquipmentUtil.setLore(item, lastLine, lore);
                              message.sendMessage(sender, mapPlaceholder);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                              player.updateInventory();
                              return true;
                           }
                        }
                     }
                  } else if (commandManager.checkCommand(subCommand, "Attribute_Power")) {
                     if (!commandManager.checkPermission(sender, "Attribute_Power")) {
                        ability = commandManager.getPermission("Attribute_Power");
                        message = lang.getMessage(sender, "Permission_Lack");
                        message.sendMessage(sender, "permission", ability);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else if (!SenderUtil.isPlayer(sender)) {
                        message = lang.getMessage(sender, "Console_Command_Forbiden");
                        message.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else if (args.length < 4) {
                        ability = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Power"));
                        message = lang.getMessage(sender, "Argument_Attribute_Power");
                        message.sendMessage(sender, "tooltip_att_power", ability);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else {
                        player = PlayerUtil.parse(sender);
                        item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                        PowerEnum power = PowerEnum.get(args[1]);
                        PowerClickEnum click = PowerClickEnum.get(args[2]);
                        if (!EquipmentUtil.isSolid(item)) {
                           message = lang.getMessage(sender, "Item_MainHand_Empty");
                           message.sendMessage(sender);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else if (power == null) {
                           message = lang.getMessage(sender, "MyItems_Attribute_Power_Not_Exists");
                           message.sendMessage(sender, "Power", args[1]);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else if (click == null) {
                           message = lang.getMessage(sender, "Utility_Slot_Not_Exists");
                           message.sendMessage(sender, "slot", args[2]);
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                           return true;
                        } else {
                           if (args.length > 4) {
                              powerSpecial = args[4];
                              if (!MathUtil.isNumber(powerSpecial)) {
                                 message = lang.getMessage(sender, "Argument_Invalid_Value");
                                 message.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              }

                              cooldown = MathUtil.parseDouble(powerSpecial);
                              cooldown = MathUtil.limitDouble(cooldown, 0.0D, cooldown);
                           } else {
                              cooldown = 0.0D;
                           }

                           if (args.length > 5) {
                              powerSpecial = args[5];
                              if (!MathUtil.isNumber(powerSpecial)) {
                                 message = lang.getMessage(sender, "Argument_Invalid_Value");
                                 message.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              }

                              line = MathUtil.parseInteger(powerSpecial);
                              if (line < 1) {
                                 message = lang.getMessage(sender, "Argument_Invalid_Value");
                                 message.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              }

                              if (EquipmentUtil.hasLore(item)) {
                                 lastLine = powerManager.getLineClick(item, click);
                                 if (lastLine != -1 && lastLine != line) {
                                    EquipmentUtil.setLore(item, lastLine, "");
                                 }
                              }
                           } else if (EquipmentUtil.hasLore(item)) {
                              line = powerManager.getLineClick(item, click);
                              if (line == -1) {
                                 line = EquipmentUtil.getLores(item).size() + 1;
                              }
                           } else {
                              line = 1;
                           }

                           if (power.equals(PowerEnum.COMMAND)) {
                              powerSpecial = args[3];
                              if (!powerCommandManager.isPowerCommandExists(powerSpecial)) {
                                 message = lang.getMessage(sender, "MyItems_Attribute_Power_Command_Not_Exists");
                                 message.sendMessage(sender, "Command", powerSpecial);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              } else {
                                 String keyCommand = powerCommandManager.getCommandKey(args[3]);
                                 lore = powerCommandManager.getTextPowerCommand(click, keyCommand, cooldown);
                                 message = lang.getMessage(sender, "MyItems_Attribute_Power_Command_Success");
                                 EquipmentUtil.setLore(item, line, lore);
                                 message.sendMessage(sender, "command", keyCommand);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                 player.updateInventory();
                                 return true;
                              }
                           } else if (power.equals(PowerEnum.SHOOT)) {
                              powerSpecial = args[3];
                              ProjectileEnum projectile = ProjectileEnum.getProjectileEnum(powerSpecial);
                              if (projectile == null) {
                                 message = lang.getMessage(sender, "Myitems_Attribute_Power_Shoot_Not_Exists");
                                 message.sendMessage(sender, "projectile", powerSpecial);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              } else {
                                 lore = powerShootManager.getTextPowerShoot(click, projectile, cooldown);
                                 message = lang.getMessage(sender, "Myitems_Attribute_Power_Shoot_Success");
                                 EquipmentUtil.setLore(item, line, lore);
                                 message.sendMessage(sender, "projectile", ProjectileUtil.getText(projectile));
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                 player.updateInventory();
                                 return true;
                              }
                           } else if (power.equals(PowerEnum.SPECIAL)) {
                              powerSpecial = args[3];
                              if (PowerSpecialEnum.get(powerSpecial) == null) {
                                 message = lang.getMessage(sender, "MyItems_Attribute_Power_Special_Not_Exists");
                                 message.sendMessage(sender, "special", powerSpecial);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              } else {
                                 PowerSpecialEnum special = PowerSpecialEnum.get(powerSpecial);
                                 lore = powerSpecialManager.getTextPowerSpecial(click, special, cooldown);
                                 message = lang.getMessage(sender, "MyItems_Attribute_Power_Special_Success");
                                 EquipmentUtil.setLore(item, line, lore);
                                 message.sendMessage(sender, "special", special.getText());
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                 player.updateInventory();
                                 return true;
                              }
                           } else {
                              message = lang.getMessage(sender, "Argument_Invalid_Command");
                              message.sendMessage(sender);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           }
                        }
                     }
                  } else if (commandManager.checkCommand(subCommand, "Attribute_Requirement")) {
                     if (!commandManager.checkPermission(sender, "Attribute_Requirement")) {
                        ability = commandManager.getPermission("Attribute_Requirement");
                        message = lang.getMessage(sender, "Permission_Lack");
                        message.sendMessage(sender, "permission", ability);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else if (!SenderUtil.isPlayer(sender)) {
                        message = lang.getMessage(sender, "Console_Command_Forbiden");
                        message.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else if (args.length < 2) {
                        ability = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Req"));
                        message = lang.getMessage(sender, "Argument_Attribute_Requirement");
                        message.sendMessage(sender, "tooltip_att_req", ability);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                     } else {
                        player = PlayerUtil.parse(sender);
                        item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                        RequirementEnum requirementEnum = RequirementEnum.getRequirement(args[1]);
                        Integer lineReq;
                        if (requirementEnum.equals(RequirementEnum.REQUIREMENT_SOUL_UNBOUND)) {
                           textClass = mainConfig.getRequirementFormatSoulUnbound();
                           if (args.length > 2) {
                              lore = args[2];
                              if (!MathUtil.isNumber(lore)) {
                                 message = lang.getMessage(sender, "Argument_Invalid_Value");
                                 message.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              }

                              line = MathUtil.parseInteger(lore);
                              if (line < 1) {
                                 message = lang.getMessage(sender, "Argument_Invalid_Value");
                                 message.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              }

                              if (EquipmentUtil.hasLore(item)) {
                                 lastLine = requirementManager.getLineRequirementSoulUnbound(item);
                                 if (lastLine != 0 && lastLine != line) {
                                    EquipmentUtil.setLore(item, lastLine, "");
                                 }
                              }
                           } else if (EquipmentUtil.hasLore(item)) {
                              lineReq = requirementManager.getLineRequirementSoulUnbound(item);
                              line = EquipmentUtil.getLores(item).size();
                              line = lineReq != null ? lineReq : line + 1;
                           } else {
                              line = 1;
                           }

                           message = lang.getMessage(sender, "MyItems_Attribute_Requirement_Unbound_Success");
                           EquipmentUtil.setLore(item, line, textClass);
                           message.sendMessage(sender, "line", String.valueOf(line));
                           SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                           player.updateInventory();
                           return true;
                        } else {
                           if (requirementEnum.equals(RequirementEnum.REQUIREMENT_SOUL_BOUND)) {
                              Object bound;
                              if (args.length > 2) {
                                 lore = args[2];
                                 bound = PlayerUtil.isOnline(lore) ? PlayerUtil.getOnlinePlayer(lore) : PlayerUtil.getPlayer(lore);
                                 if (bound == null) {
                                    message = lang.getMessage(sender, "Player_Not_Exists");
                                    message.sendMessage(sender, "Player", lore);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }
                              } else {
                                 bound = player;
                              }

                              if (args.length > 3) {
                                 lore = args[3];
                                 if (!MathUtil.isNumber(lore)) {
                                    message = lang.getMessage(sender, "Argument_Invalid_Value");
                                    message.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }

                                 line = MathUtil.parseInteger(lore);
                                 if (line < 1) {
                                    message = lang.getMessage(sender, "Argument_Invalid_Value");
                                    message.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }

                                 if (EquipmentUtil.hasLore(item)) {
                                    lastLine = requirementManager.getLineRequirementSoulBound(item);
                                    if (lastLine != 0 && lastLine != line) {
                                       EquipmentUtil.setLore(item, lastLine, "");
                                    }
                                 }
                              } else if (EquipmentUtil.hasLore(item)) {
                                 lineReq = requirementManager.getLineRequirementSoulBound(item);
                                 line = EquipmentUtil.getLores(item).size();
                                 line = lineReq != null ? lineReq : line + 1;
                              } else {
                                 line = 1;
                              }

                              lore = requirementManager.getTextSoulBound((OfflinePlayer)bound);
                              message = lang.getMessage(sender, "MyItems_Attribute_Requirement_Bound_Success");
                              mapPlaceholder = new HashMap();
                              mapPlaceholder.put("player", ((OfflinePlayer)bound).getName());
                              mapPlaceholder.put("line", String.valueOf(line));
                              requirementManager.setMetadataSoulbound((OfflinePlayer)bound, item);
                              EquipmentUtil.setLore(item, line, lore);
                              message.sendMessage(sender, mapPlaceholder);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                              player.updateInventory();
                              return true;
                           } else if (requirementEnum.equals(RequirementEnum.REQUIREMENT_PERMISSION)) {
                              if (args.length < 3) {
                                 textClass = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Req_Permission"));
                                 message = lang.getMessage(sender, "Argument_Attribute_Requirement_Permission");
                                 message.sendMessage(sender, "tooltip_att_req_permission", textClass);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              } else {
                                 textClass = args[2];
                                 if (args.length > 3) {
                                    lore = args[3];
                                    if (!MathUtil.isNumber(lore)) {
                                       message = lang.getMessage(sender, "Argument_Invalid_Value");
                                       message.sendMessage(sender);
                                       SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                       return true;
                                    }

                                    line = MathUtil.parseInteger(lore);
                                    if (line < 1) {
                                       message = lang.getMessage(sender, "Argument_Invalid_Value");
                                       message.sendMessage(sender);
                                       SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                       return true;
                                    }

                                    if (EquipmentUtil.hasLore(item)) {
                                       lastLine = requirementManager.getLineRequirementPermission(item);
                                       if (lastLine != 0 && lastLine != line) {
                                          EquipmentUtil.setLore(item, lastLine, "");
                                       }
                                    }
                                 } else if (EquipmentUtil.hasLore(item)) {
                                    lineReq = requirementManager.getLineRequirementPermission(item);
                                    line = EquipmentUtil.getLores(item).size();
                                    line = lineReq != null ? lineReq : line + 1;
                                 } else {
                                    line = 1;
                                 }

                                 lore = requirementManager.getTextPermission(textClass);
                                 message = lang.getMessage(sender, "MyItems_Attribute_Requirement_Permission_Success");
                                 mapPlaceholder = new HashMap();
                                 mapPlaceholder.put("permission", textClass);
                                 mapPlaceholder.put("line", String.valueOf(line));
                                 EquipmentUtil.setLore(item, line, lore);
                                 message.sendMessage(sender, mapPlaceholder);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                 player.updateInventory();
                                 return true;
                              }
                           } else if (requirementEnum.equals(RequirementEnum.REQUIREMENT_LEVEL)) {
                              if (args.length < 3) {
                                 textClass = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Req_Level"));
                                 message = lang.getMessage(sender, "Argument_Attribute_Level_Permission");
                                 message.sendMessage(sender, "tooltip_att_req_level", textClass);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              } else {
                                 textClass = args[2];
                                 if (MathUtil.isNumber(textClass)) {
                                    line = MathUtil.parseInteger(textClass);
                                    line = MathUtil.limitInteger(line, 0, line);
                                    if (args.length > 3) {
                                       lore = args[3];
                                       if (!MathUtil.isNumber(lore)) {
                                          message = lang.getMessage(sender, "Argument_Invalid_Value");
                                          message.sendMessage(sender);
                                          SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                          return true;
                                       }

                                       line = MathUtil.parseInteger(lore);
                                       if (line < 1) {
                                          message = lang.getMessage(sender, "Argument_Invalid_Value");
                                          message.sendMessage(sender);
                                          SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                          return true;
                                       }

                                       if (EquipmentUtil.hasLore(item)) {
                                          lastLine = requirementManager.getLineRequirementLevel(item);
                                          if (lastLine != 0 && lastLine != line) {
                                             EquipmentUtil.setLore(item, lastLine, "");
                                          }
                                       }
                                    } else if (EquipmentUtil.hasLore(item)) {
                                       lastLine = requirementManager.getLineRequirementLevel(item);
                                       loreSize = EquipmentUtil.getLores(item).size();
                                       line = lastLine != 0 ? lastLine : loreSize + 1;
                                    } else {
                                       line = 1;
                                    }

                                    lore = requirementManager.getTextLevel(line);
                                    message = lang.getMessage(sender, "MyItems_Attribute_Requirement_Level_Success");
                                    mapPlaceholder = new HashMap();
                                    mapPlaceholder.put("level", String.valueOf(line));
                                    mapPlaceholder.put("line", String.valueOf(line));
                                    EquipmentUtil.setLore(item, line, lore);
                                    message.sendMessage(sender, mapPlaceholder);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                    player.updateInventory();
                                    return true;
                                 } else {
                                    message = lang.getMessage(sender, "Argument_Invalid_Value");
                                    message.sendMessage(sender);
                                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                    return true;
                                 }
                              }
                           } else if (requirementEnum.equals(RequirementEnum.REQUIREMENT_CLASS)) {
                              if (!requirementManager.isSupportReqClass()) {
                                 message = lang.getMessage(sender, "Argument_Not_Support_Class");
                                 message.sendMessage(sender);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              } else if (args.length < 3) {
                                 textClass = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Req_Class"));
                                 message = lang.getMessage(sender, "Argument_Attribute_Class_Permission");
                                 message.sendMessage(sender, "tooltip_att_req_class", textClass);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                 return true;
                              } else {
                                 textClass = args[2];
                                 if (args.length > 3) {
                                    lore = args[3];
                                    if (!MathUtil.isNumber(lore)) {
                                       message = lang.getMessage(sender, "Argument_Invalid_Value");
                                       message.sendMessage(sender);
                                       SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                       return true;
                                    }

                                    line = MathUtil.parseInteger(lore);
                                    if (line < 1) {
                                       message = lang.getMessage(sender, "Argument_Invalid_Value");
                                       message.sendMessage(sender);
                                       SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                                       return true;
                                    }

                                    if (EquipmentUtil.hasLore(item)) {
                                       lastLine = requirementManager.getLineRequirementClass(item);
                                       if (lastLine != 0 && lastLine != line) {
                                          EquipmentUtil.setLore(item, lastLine, "");
                                       }
                                    }
                                 } else if (EquipmentUtil.hasLore(item)) {
                                    lineReq = requirementManager.getLineRequirementClass(item);
                                    line = EquipmentUtil.getLores(item).size();
                                    line = lineReq != null ? lineReq : line + 1;
                                 } else {
                                    line = 1;
                                 }

                                 lore = requirementManager.getTextClass(textClass);
                                 message = lang.getMessage(sender, "MyItems_Attribute_Requirement_Class_Success");
                                 mapPlaceholder = new HashMap();
                                 mapPlaceholder.put("class", textClass);
                                 mapPlaceholder.put("line", String.valueOf(line));
                                 EquipmentUtil.setLore(item, line, lore);
                                 message.sendMessage(sender, mapPlaceholder);
                                 SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                 player.updateInventory();
                                 return true;
                              }
                           } else {
                              textClass = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Req"));
                              message = lang.getMessage(sender, "Argument_Attribute_Requirement");
                              message.sendMessage(sender, "tooltip_att_req", textClass);
                              SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                              return true;
                           }
                        }
                     }
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
