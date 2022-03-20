package com.praya.myitems.manager.game;

import api.praya.myitems.builder.power.PowerClickEnum;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.utility.main.ProjectileUtil;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import java.util.HashMap;

public class PowerShootManager extends HandlerManager {
   protected PowerShootManager(MyItems plugin) {
      super(plugin);
   }

   public final String getTextPowerShoot(PowerClickEnum click, ProjectileEnum projectile, double cooldown) {
      PowerManager powerManager = this.plugin.getGameManager().getPowerManager();
      MainConfig mainConfig = MainConfig.getInstance();
      HashMap<String, String> map = new HashMap();
      String format = mainConfig.getPowerFormat();
      cooldown = MathUtil.roundNumber(cooldown, 1);
      map.put("click", powerManager.getKeyClick(click));
      map.put("type", this.getKeyShoot(projectile));
      map.put("cooldown", powerManager.getKeyCooldown(cooldown));
      format = TextUtil.placeholder(map, format, "<", ">");
      return format;
   }

   public final ProjectileEnum getShoot(String lore) {
      MainConfig mainConfig = MainConfig.getInstance();
      String[] loreCheck = lore.split(MainConfig.KEY_SHOOT);
      if (loreCheck.length > 1) {
         String colorPowerType = mainConfig.getPowerColorType();
         String loreStep = loreCheck[1].replaceFirst(colorPowerType, "");
         return ProjectileUtil.getProjectileByLore(loreStep);
      } else {
         return null;
      }
   }

   public final String getKeyShoot(ProjectileEnum projectile) {
      return this.getKeyShoot(projectile, false);
   }

   public final String getKeyShoot(ProjectileEnum projectile, boolean justCheck) {
      MainConfig mainConfig = MainConfig.getInstance();
      String key = MainConfig.KEY_SHOOT;
      String color = mainConfig.getPowerColorType();
      String text = ProjectileUtil.getText(projectile);
      return justCheck ? key + color + text : key + color + text + key + color;
   }
}
