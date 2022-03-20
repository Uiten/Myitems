package com.praya.myitems.listener.main;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import java.util.Collection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ListenerCommand extends HandlerEvent implements Listener {
   public ListenerCommand(MyItems plugin) {
      super(plugin);
   }

   @EventHandler
   public void onCommand(PlayerCommandPreprocessEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
      MainConfig mainConfig = MainConfig.getInstance();
      final Player player = event.getPlayer();
      ItemStack itemBefore = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
      final Collection<PassiveEffectEnum> passiveEffectBefore = passiveEffectManager.getPassiveEffects(itemBefore);
      final int idBefore = EquipmentUtil.isSolid(itemBefore) ? itemBefore.hashCode() : 0;
      final boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
      (new BukkitRunnable() {
         public void run() {
            if (player.isOnline()) {
               ItemStack itemAfter = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
               Collection<PassiveEffectEnum> passiveEffectAfter = passiveEffectManager.getPassiveEffects(itemAfter);
               int idAfter = EquipmentUtil.isSolid(itemAfter) ? itemAfter.hashCode() : 0;
               if (idBefore != idAfter) {
                  passiveEffectManager.reloadPassiveEffect(player, passiveEffectBefore, enableGradeCalculation);
                  passiveEffectManager.reloadPassiveEffect(player, passiveEffectAfter, enableGradeCalculation);
               }
            }

         }
      }).runTaskLater(this.plugin, 1L);
   }
}
