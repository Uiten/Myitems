package com.praya.myitems.listener.main;

import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import com.praya.agarthalib.utility.EntityUtil;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ListenerEntityDeath extends HandlerEvent implements Listener {
   public ListenerEntityDeath(MyItems plugin) {
      super(plugin);
   }

   @EventHandler
   public void onDeath(EntityDeathEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      LivingEntity victims = event.getEntity();
      MainConfig mainConfig = MainConfig.getInstance();
      if (victims.getKiller() != null) {
         Player player = victims.getKiller();
         double expGain = EntityUtil.isPlayer(victims) ? mainConfig.getDropExpPlayer() : mainConfig.getDropExpMobs();

         for(int itemCode = 0; itemCode < 6; ++itemCode) {
            ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, itemCode);
            if (EquipmentUtil.loreCheck(item)) {
               int line = statsManager.getLineLoreStats(item, LoreStatsEnum.LEVEL);
               if (line != -1) {
                  double scaleExp;
                  if (itemCode == 0) {
                     scaleExp = 1.0D;
                  } else if (itemCode == 1) {
                     scaleExp = mainConfig.getModifierScaleExpOffHand();
                  } else {
                     scaleExp = mainConfig.getModifierScaleExpArmor();
                  }

                  String loreLevel = (String)EquipmentUtil.getLores(item).get(line - 1);
                  String[] expLores = loreLevel.split(MainConfig.KEY_EXP_CURRENT);
                  String[] upLores = loreLevel.split(MainConfig.KEY_EXP_UP);
                  String colorExpCurrent = mainConfig.getStatsColorExpCurrent();
                  String colorExpUp = mainConfig.getStatsColorExpUp();
                  double exp = MathUtil.parseDouble(expLores[1].replaceAll(colorExpCurrent, ""));
                  double up = MathUtil.parseDouble(upLores[1].replaceAll(colorExpUp, ""));
                  int level = (int)statsManager.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.LEVEL, (LoreStatsOption)null);
                  int maxLevel = mainConfig.getStatsMaxLevelValue();
                  if (exp + expGain * scaleExp < up) {
                     if (level < maxLevel) {
                        double newExp = MathUtil.roundNumber(exp + expGain * scaleExp, 1);
                        String newExpLore = expLores[0] + MainConfig.KEY_EXP_CURRENT + colorExpCurrent + newExp + MainConfig.KEY_EXP_CURRENT + expLores[2];
                        EquipmentUtil.setLore(item, line, newExpLore);
                     }
                  } else {
                     ItemMeta meta = item.getItemMeta();
                     double scaleUp = mainConfig.getStatsScaleUpValue();
                     double calculation = (1.0D + scaleUp * (double)level) / (1.0D + scaleUp * (double)(level - 1));
                     double nextExp = MathUtil.roundNumber(exp + expGain * scaleExp - up, 1);
                     if (level + 1 >= maxLevel) {
                        nextExp = 0.0D;
                     }

                     String newLoreLevel = statsManager.getTextLoreStats(LoreStatsEnum.LEVEL, (double)(level + 1), nextExp);
                     List<String> lores = meta.getLore();
                     HashMap<Integer, String> mapLore = new HashMap();

                     int lineAdditional;
                     for(lineAdditional = 0; lineAdditional < meta.getLore().size(); ++lineAdditional) {
                        mapLore.put(lineAdditional, (String)lores.get(lineAdditional));
                     }

                     mapLore.put(line - 1, newLoreLevel);
                     double minDamage;
                     if (itemCode < 2) {
                        lineAdditional = statsManager.getLineLoreStats(item, LoreStatsEnum.DAMAGE);
                        if (lineAdditional != -1) {
                           minDamage = statsManager.getLoreValue(item, LoreStatsEnum.DAMAGE, LoreStatsOption.MIN);
                           double maxDamage = statsManager.getLoreValue(item, LoreStatsEnum.DAMAGE, LoreStatsOption.MAX);
                           double scale = maxDamage / minDamage;
                           minDamage = MathUtil.roundNumber(minDamage * calculation, 2);
                           maxDamage = MathUtil.roundNumber(minDamage * scale, 2);
                           String newLoreDamage = statsManager.getTextLoreStats(LoreStatsEnum.DAMAGE, minDamage, maxDamage);
                           mapLore.put(lineAdditional - 1, newLoreDamage);
                        }
                     } else {
                        lineAdditional = statsManager.getLineLoreStats(item, LoreStatsEnum.DEFENSE);
                        if (lineAdditional != -1) {
                           minDamage = statsManager.getLoreValue((ItemStack)item, (LoreStatsEnum)LoreStatsEnum.DEFENSE, (LoreStatsOption)null);
                           minDamage = MathUtil.roundNumber(minDamage * calculation, 2);
                           String newLoreDefense = statsManager.getTextLoreStats(LoreStatsEnum.DEFENSE, minDamage);
                           mapLore.put(lineAdditional - 1, newLoreDefense);
                        }
                     }

                     List<String> newLores = new ArrayList();

                     for(int i = 0; i < mapLore.size(); ++i) {
                        newLores.add((String)mapLore.get(i));
                     }

                     meta.setLore(newLores);
                     item.setItemMeta(meta);
                  }
               }
            }
         }
      }

   }
}
