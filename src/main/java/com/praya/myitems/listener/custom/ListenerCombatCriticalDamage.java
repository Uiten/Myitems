package com.praya.myitems.listener.custom;

import api.praya.myitems.builder.event.CombatCriticalDamageEvent;
import com.praya.agarthalib.utility.EntityUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.plugin.LanguageManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ListenerCombatCriticalDamage extends HandlerEvent implements Listener {
   public ListenerCombatCriticalDamage(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void eventCombatCriticalDamage(CombatCriticalDamageEvent event) {
      LanguageManager lang = this.plugin.getPluginManager().getLanguageManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (!event.isCancelled()) {
         LivingEntity attacker = event.getAttacker();
         LivingEntity victims = event.getVictims();
         String criticalAttackType = mainConfig.getModifierCriticalAttackType();
         if (criticalAttackType != null) {
            if (criticalAttackType.equalsIgnoreCase("Effect")) {
               Location loc = victims.getEyeLocation();
               Collection<Player> players = PlayerUtil.getNearbyPlayers(loc, mainConfig.getEffectRange());
               Bridge.getBridgeParticle().playParticle(players, ParticleEnum.EXPLOSION_LARGE, loc, 3, 0.1D, 0.1D, 0.1D, 0.5D);
               Bridge.getBridgeSound().playSound(players, loc, SoundEnum.ENTITY_GENERIC_EXPLODE, 0.5F, 1.0F);
            } else if (criticalAttackType.equalsIgnoreCase("Messages") && EntityUtil.isPlayer(attacker)) {
               Player player = EntityUtil.parsePlayer(attacker);
               String message = lang.getText((LivingEntity)player, "Attack_Critical");
               SenderUtil.sendMessage(player, message);
            }
         }
      }

   }
}
