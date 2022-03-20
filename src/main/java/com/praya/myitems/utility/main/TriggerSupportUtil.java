package com.praya.myitems.utility.main;

import api.praya.agarthalib.builder.support.SupportCombatStamina;
import api.praya.agarthalib.builder.support.SupportLifeEssence;
import api.praya.agarthalib.main.AgarthaLibAPI;
import api.praya.agarthalib.manager.plugin.SupportManagerAPI;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.entity.Player;

public class TriggerSupportUtil {
   public static final void updateSupport(Player player) {
      AgarthaLibAPI agarthaLibAPI = AgarthaLibAPI.getInstance();
      SupportManagerAPI supportManagerAPI = agarthaLibAPI.getPluginManagerAPI().getSupportManager();
      MainConfig mainConfig = MainConfig.getInstance();
      boolean enableMaxHealth = mainConfig.isStatsEnableMaxHealth();
      if (enableMaxHealth) {
         PlayerUtil.setMaxHealth(player);
      }

      if (supportManagerAPI.isSupportCombatStamina()) {
         SupportCombatStamina supportCombatStamina = supportManagerAPI.getSupportCombatStamina();
         supportCombatStamina.updateMaxStamina(player);
         supportCombatStamina.updateStaminaRegen(player);
      }

      if (supportManagerAPI.isSupportLifeEssence()) {
         SupportLifeEssence supportLifeEssence = supportManagerAPI.getSupportLifeEssence();
         supportLifeEssence.updateHealthRegen(player);
      }

   }
}
