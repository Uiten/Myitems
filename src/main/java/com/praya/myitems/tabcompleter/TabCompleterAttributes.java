package com.praya.myitems.tabcompleter;

import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import api.praya.myitems.builder.passive.PassiveTypeEnum;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import api.praya.myitems.requirement.RequirementEnum;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.ServerUtil;
import com.praya.agarthalib.utility.TabCompleterUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerTabCompleter;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PowerCommandManager;
import com.praya.myitems.manager.game.PowerManager;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.manager.register.RegisterAbilityWeaponManager;
import com.praya.myitems.manager.register.RegisterManager;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.TagsAttribute;
import core.praya.agarthalib.enums.main.VersionNMS;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabCompleterAttributes extends HandlerTabCompleter implements TabCompleter {
   public TabCompleterAttributes(MyItems plugin) {
      super(plugin);
   }

   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      GameManager gameManager = this.plugin.getGameManager();
      RegisterManager registerManager = this.plugin.getRegisterManager();
      PowerManager powerManager = gameManager.getPowerManager();
      CommandManager commandManager = pluginManager.getCommandManager();
      ElementManager elementManager = gameManager.getElementManager();
      PowerCommandManager powerCommandManager = powerManager.getPowerCommandManager();
      RegisterAbilityWeaponManager registerAbilityWeaponManager = registerManager.getRegisterAbilityWeaponManager();
      List<String> tabList = new ArrayList();
      SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
      if (args.length == 1) {
         if (commandManager.checkPermission(sender, "Attribute_Stats")) {
            tabList.add("Stats");
         }

         if (commandManager.checkPermission(sender, "Attribute_Buff")) {
            tabList.add("Buff");
         }

         if (commandManager.checkPermission(sender, "Attribute_Debuff")) {
            tabList.add("Debuff");
         }

         if (commandManager.checkPermission(sender, "Attribute_Ability")) {
            tabList.add("Ability");
         }

         if (commandManager.checkPermission(sender, "Attribute_Power")) {
            tabList.add("Power");
         }

         if (commandManager.checkPermission(sender, "Attribute_NBT")) {
            tabList.add("NBT");
         }

         if (commandManager.checkPermission(sender, "Attribute_Element")) {
            tabList.add("Element");
         }

         if (commandManager.checkPermission(sender, "Attribute_Requirement")) {
            tabList.add("Requirement");
         }
      } else {
         String argument1;
         int var16;
         int var17;
         String ability;
         if (args.length == 2) {
            argument1 = args[0];
            if (commandManager.checkCommand(argument1, "Attribute_Stats")) {
               if (commandManager.checkPermission(sender, "Attribute_Stats")) {
                  LoreStatsEnum[] var18;
                  var17 = (var18 = LoreStatsEnum.values()).length;

                  for(var16 = 0; var16 < var17; ++var16) {
                     LoreStatsEnum stats = var18[var16];
                     if (stats.isAllowed()) {
                        tabList.add(String.valueOf(stats));
                     }
                  }
               }
            } else {
               Iterator var24;
               if (commandManager.checkCommand(argument1, "Attribute_Element")) {
                  if (commandManager.checkPermission(sender, "Attribute_Element")) {
                     var24 = elementManager.getElementConfig().getElements().iterator();

                     while(var24.hasNext()) {
                        ability = (String)var24.next();
                        tabList.add(ability);
                     }
                  }
               } else {
                  PassiveEffectEnum debuff;
                  PassiveEffectEnum[] var28;
                  if (commandManager.checkCommand(argument1, "Attribute_Buff")) {
                     if (commandManager.checkPermission(sender, "Attribute_Buff")) {
                        var17 = (var28 = PassiveEffectEnum.values()).length;

                        for(var16 = 0; var16 < var17; ++var16) {
                           debuff = var28[var16];
                           if (debuff.getType().equals(PassiveTypeEnum.BUFF) && (ServerUtil.isCompatible(VersionNMS.V1_9_R1) || !debuff.equals(PassiveEffectEnum.LUCK))) {
                              tabList.add(debuff.toString());
                           }
                        }
                     }
                  } else if (commandManager.checkCommand(argument1, "Attribute_Debuff")) {
                     if (commandManager.checkPermission(sender, "Attribute_Debuff")) {
                        var17 = (var28 = PassiveEffectEnum.values()).length;

                        for(var16 = 0; var16 < var17; ++var16) {
                           debuff = var28[var16];
                           if (debuff.getType().equals(PassiveTypeEnum.DEBUFF) && (ServerUtil.isCompatible(VersionNMS.V1_9_R1) || !debuff.equals(PassiveEffectEnum.UNLUCK) && !debuff.equals(PassiveEffectEnum.GLOW))) {
                              tabList.add(debuff.toString());
                           }
                        }
                     }
                  } else if (commandManager.checkCommand(argument1, "Attribute_NBT")) {
                     if (commandManager.checkPermission(sender, "Attribute_NBT")) {
                        TagsAttribute[] var29;
                        var17 = (var29 = TagsAttribute.values()).length;

                        for(var16 = 0; var16 < var17; ++var16) {
                           TagsAttribute tags = var29[var16];
                           tabList.add(String.valueOf(tags));
                        }
                     }
                  } else if (commandManager.checkCommand(argument1, "Attribute_Ability")) {
                     if (commandManager.checkPermission(sender, "Attribute_Ability")) {
                        var24 = registerAbilityWeaponManager.getAbilityIDs().iterator();

                        while(var24.hasNext()) {
                           ability = (String)var24.next();
                           tabList.add(ability.toString());
                        }
                     }
                  } else if (commandManager.checkCommand(argument1, "Attribute_Power")) {
                     if (commandManager.checkPermission(sender, "Attribute_Power")) {
                        PowerEnum[] var31;
                        var17 = (var31 = PowerEnum.values()).length;

                        for(var16 = 0; var16 < var17; ++var16) {
                           PowerEnum power = var31[var16];
                           tabList.add(String.valueOf(power));
                        }
                     }
                  } else if (commandManager.checkCommand(argument1, "Attribute_Requirement") && commandManager.checkPermission(sender, "Attribute_Requirement")) {
                     RequirementEnum[] var33;
                     var17 = (var33 = RequirementEnum.values()).length;

                     for(var16 = 0; var16 < var17; ++var16) {
                        RequirementEnum requirement = var33[var16];
                        tabList.add(String.valueOf(requirement.getName()));
                     }
                  }
               }
            }
         } else if (args.length == 3) {
            argument1 = args[0];
            if (commandManager.checkCommand(argument1, "Attribute_Power") && commandManager.checkPermission(sender, "Attribute_Power")) {
               PowerClickEnum[] var34;
               var17 = (var34 = PowerClickEnum.values()).length;

               for(var16 = 0; var16 < var17; ++var16) {
                  PowerClickEnum click = var34[var16];
                  tabList.add(String.valueOf(click));
               }
            }
         } else if (args.length == 4) {
            argument1 = args[0];
            ability = args[1];
            if (commandManager.checkCommand(argument1, "Attribute_Power") && commandManager.checkPermission(sender, "Attribute_Power")) {
               PowerEnum power = PowerEnum.get(ability);
               if (power != null) {
                  if (power.equals(PowerEnum.COMMAND)) {
                     Iterator var36 = powerCommandManager.getPowerCommandIDs().iterator();

                     while(var36.hasNext()) {
                        String powerCommand = (String)var36.next();
                        tabList.add(powerCommand);
                     }
                  } else {
                     int var19;
                     int var38;
                     if (power.equals(PowerEnum.SHOOT)) {
                        ProjectileEnum[] var20;
                        var19 = (var20 = ProjectileEnum.values()).length;

                        for(var38 = 0; var38 < var19; ++var38) {
                           ProjectileEnum projectile = var20[var38];
                           tabList.add(String.valueOf(projectile));
                        }
                     } else if (power.equals(PowerEnum.SPECIAL)) {
                        PowerSpecialEnum[] var37;
                        var19 = (var37 = PowerSpecialEnum.values()).length;

                        for(var38 = 0; var38 < var19; ++var38) {
                           PowerSpecialEnum special = var37[var38];
                           tabList.add(String.valueOf(special));
                        }
                     }
                  }
               }
            }
         }
      }

      return TabCompleterUtil.returnList(tabList, args);
   }
}
