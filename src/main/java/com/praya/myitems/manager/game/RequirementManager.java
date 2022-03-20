package com.praya.myitems.manager.game;

import api.praya.agarthalib.builder.support.main.SupportClass;
import api.praya.agarthalib.builder.support.main.SupportLevel;
import api.praya.agarthalib.builder.support.main.SupportClass.SupportClassType;
import api.praya.agarthalib.builder.support.main.SupportLevel.SupportLevelType;
import api.praya.agarthalib.main.AgarthaLibAPI;
import api.praya.agarthalib.manager.plugin.SupportManagerAPI;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.ServerUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.plugin.MainConfig;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.VersionNMS;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RequirementManager extends HandlerManager {
   protected RequirementManager(MyItems plugin) {
      super(plugin);
   }

   public final String getTextSoulUnbound() {
      MainConfig mainConfig = MainConfig.getInstance();
      return mainConfig.getRequirementFormatSoulUnbound();
   }

   public final String getTextSoulBound(OfflinePlayer player) {
      MainConfig mainConfig = MainConfig.getInstance();
      HashMap<String, String> map = new HashMap();
      String format = mainConfig.getRequirementFormatSoulBound();
      map.put("player", this.getKeyReqSoulBound(player, false));
      format = TextUtil.placeholder(map, format, "<", ">");
      return format;
   }

   public final String getTextLevel(int level) {
      MainConfig mainConfig = MainConfig.getInstance();
      HashMap<String, String> map = new HashMap();
      String format = mainConfig.getRequirementFormatLevel();
      map.put("level", this.getKeyReqLevel(level, false));
      format = TextUtil.placeholder(map, format, "<", ">");
      return format;
   }

   public final String getTextPermission(String permission) {
      MainConfig mainConfig = MainConfig.getInstance();
      HashMap<String, String> map = new HashMap();
      String format = mainConfig.getRequirementFormatPermission();
      map.put("permission", this.getKeyReqPermission(permission, false));
      format = TextUtil.placeholder(map, format, "<", ">");
      return format;
   }

   public final String getTextClass(String playerClass) {
      MainConfig mainConfig = MainConfig.getInstance();
      HashMap<String, String> map = new HashMap();
      String format = mainConfig.getRequirementFormatClass();
      map.put("class", this.getKeyReqClass(playerClass, false));
      format = TextUtil.placeholder(map, format, "<", ">");
      return format;
   }

   public final Integer getLineRequirementSoulUnbound(ItemStack item) {
      MainConfig mainConfig = MainConfig.getInstance();
      String key = mainConfig.getRequirementFormatSoulUnbound();
      if (EquipmentUtil.hasLore(item)) {
         int line = EquipmentUtil.loreGetLineKey(item, key);
         return line >= 0 ? line : null;
      } else {
         return null;
      }
   }

   public final Integer getLineRequirementSoulBound(ItemStack item) {
      String key = this.getKeyReqSoulBound();
      if (EquipmentUtil.hasLore(item)) {
         int line = EquipmentUtil.loreGetLineKey(item, key);
         return line >= 0 ? line : null;
      } else {
         return null;
      }
   }

   public final Integer getLineRequirementLevel(ItemStack item) {
      String key = this.getKeyReqLevel();
      if (EquipmentUtil.hasLore(item)) {
         int line = EquipmentUtil.loreGetLineKey(item, key);
         return line >= 0 ? line : null;
      } else {
         return null;
      }
   }

   public final Integer getLineRequirementPermission(ItemStack item) {
      String key = this.getKeyReqPermission();
      if (EquipmentUtil.hasLore(item)) {
         int line = EquipmentUtil.loreGetLineKey(item, key);
         return line >= 0 ? line : null;
      } else {
         return null;
      }
   }

   public final Integer getLineRequirementClass(ItemStack item) {
      String key = this.getKeyReqClass();
      if (EquipmentUtil.hasLore(item)) {
         int line = EquipmentUtil.loreGetLineKey(item, key);
         return line >= 0 ? line : null;
      } else {
         return null;
      }
   }

   public final boolean hasRequirementSoulUnbound(ItemStack item) {
      return this.getLineRequirementSoulUnbound(item) != null;
   }

   public final boolean hasRequirementSoulBound(ItemStack item) {
      return this.getLineRequirementSoulBound(item) != null;
   }

   public final boolean hasrequirementLevel(ItemStack item) {
      return this.getLineRequirementLevel(item) != null;
   }

   public final boolean hasRequirementPermission(ItemStack item) {
      return this.getLineRequirementPermission(item) != null;
   }

   public final boolean hasRequirementClass(ItemStack item) {
      return this.getLineRequirementClass(item) != null;
   }

   public final void setMetadataSoulbound(OfflinePlayer player, ItemStack item) {
      if (ServerUtil.isCompatible(VersionNMS.V1_8_R1)) {
         String metadata = this.getMetadataSoulBound();
         String bound = player.getUniqueId().toString();
         Bridge.getBridgeTagsNBT().setString(metadata, item, bound);
      }

   }

   public final String getRequirementSoulBound(ItemStack item) {
      OfflinePlayer bound = this.getRequirementSoulBoundPlayer(item);
      return bound != null ? bound.getName() : null;
   }

   public final String getRequirementSoulBound(String lore) {
      MainConfig mainConfig = MainConfig.getInstance();
      String key = MainConfig.KEY_REQ_BOUND;
      String[] textListValue = lore.split(key);
      if (textListValue.length > 1) {
         String color = mainConfig.getRequirementColorSoulBound();
         String value = textListValue[1].replaceAll(color, "");
         return value;
      } else {
         return null;
      }
   }

   public final OfflinePlayer getRequirementSoulBoundPlayer(ItemStack item) {
      Integer line = this.getLineRequirementSoulBound(item);
      if (line != null) {
         List<String> lores = EquipmentUtil.getLores(item);
         String lore = (String)lores.get(line - 1);
         String bound;
         if (ServerUtil.isCompatible(VersionNMS.V1_8_R1)) {
            bound = this.getMetadataSoulBound();
            bound = Bridge.getBridgeTagsNBT().getString(bound, item);
            if (bound != null) {
               UUID playerID = UUID.fromString(bound);
               OfflinePlayer player = PlayerUtil.getPlayer(playerID);
               if (player != null) {
                  return player;
               }
            }
         }

         bound = this.getRequirementSoulBound(lore);
         return bound != null ? PlayerUtil.getPlayer(bound) : null;
      } else {
         return null;
      }
   }

   public final Integer getRequirementLevel(ItemStack item) {
      Integer line = this.getLineRequirementLevel(item);
      if (line != null) {
         List<String> lores = EquipmentUtil.getLores(item);
         String lore = (String)lores.get(line - 1);
         return this.getRequirementLevel(lore);
      } else {
         return null;
      }
   }

   public final Integer getRequirementLevel(String lore) {
      MainConfig mainConfig = MainConfig.getInstance();
      String key = MainConfig.KEY_REQ_LEVEL;
      String[] textListValue = lore.split(key);
      if (textListValue.length > 1) {
         String color = mainConfig.getRequirementColorLevel();
         String textValue = textListValue[1].replaceAll(color, "");
         if (MathUtil.isNumber(textValue)) {
            int value = MathUtil.parseInteger(textValue);
            return value;
         }
      }

      return null;
   }

   public final String getRequirementPermission(ItemStack item) {
      Integer line = this.getLineRequirementPermission(item);
      if (line != null) {
         List<String> lores = EquipmentUtil.getLores(item);
         String lore = (String)lores.get(line - 1);
         return this.getRequirementPermission(lore);
      } else {
         return null;
      }
   }

   public final String getRequirementPermission(String lore) {
      MainConfig mainConfig = MainConfig.getInstance();
      String key = MainConfig.KEY_REQ_PERMISSION;
      String[] textListValue = lore.split(key);
      if (textListValue.length > 1) {
         String color = mainConfig.getRequirementColorPermission();
         String value = textListValue[1].replaceAll(color, "");
         return value;
      } else {
         return null;
      }
   }

   public final String getRequirementClass(ItemStack item) {
      Integer line = this.getLineRequirementClass(item);
      if (line != null) {
         List<String> lores = EquipmentUtil.getLores(item);
         String lore = (String)lores.get(line - 1);
         return this.getRequirementClass(lore);
      } else {
         return null;
      }
   }

   public final String getRequirementClass(String lore) {
      MainConfig mainConfig = MainConfig.getInstance();
      String key = MainConfig.KEY_REQ_CLASS;
      String[] textListValue = lore.split(key);
      if (textListValue.length > 1) {
         String color = mainConfig.getRequirementColorClass();
         String value = textListValue[1].replaceAll(color, "");
         return value;
      } else {
         return null;
      }
   }

   public final boolean isAllowed(Player player, ItemStack item) {
      boolean allowSoulBound = this.isAllowedReqSoulBound(player, item);
      boolean allowLevel = this.isAllowedReqLevel(player, item);
      boolean allowPermission = this.isAllowedReqPermission(player, item);
      boolean allowClass = this.isAllowedReqClass(player, item);
      return allowSoulBound && allowLevel && allowPermission && allowClass;
   }

   public final boolean isAllowedReqSoulBound(Player player, ItemStack item) {
      String bound = this.getRequirementSoulBound(item);
      return bound != null ? this.isAllowedReqSoulBound(player, bound) : true;
   }

   public final boolean isAllowedReqSoulBound(Player player, String bound) {
      String name = player.getName();
      String id = player.getUniqueId().toString();
      boolean matchName = bound.equalsIgnoreCase(name);
      boolean matchUUID = bound.equalsIgnoreCase(id);
      return matchName || matchUUID;
   }

   public final boolean isAllowedReqLevel(Player player, ItemStack item) {
      Integer reqLevel = this.getRequirementLevel(item);
      return reqLevel != null ? this.isAllowedReqLevel(player, reqLevel) : true;
   }

   public final boolean isAllowedReqLevel(Player player, int reqLevel) {
      SupportLevel supportLevel;
      label40: {
         AgarthaLibAPI agarthaLibAPI = AgarthaLibAPI.getInstance();
         SupportManagerAPI supportManagerAPI = agarthaLibAPI.getPluginManagerAPI().getSupportManager();
         MainConfig mainConfig = MainConfig.getInstance();
         String textLevelType = mainConfig.getSupportTypeLevel();
         String var9;
         switch((var9 = textLevelType.toUpperCase()).hashCode()) {
         case -1369886167:
            if (var9.equals("SKILLAPI")) {
               supportLevel = supportManagerAPI.getSupportLevel(SupportLevelType.SKILL_API);
               break label40;
            }
            break;
         case 2020783:
            if (var9.equals("AUTO")) {
               supportLevel = supportManagerAPI.getSupportLevel();
               break label40;
            }
            break;
         case 483738379:
            if (var9.equals("SKILLSPRO")) {
               supportLevel = supportManagerAPI.getSupportLevel(SupportLevelType.SKILLS_PRO);
               break label40;
            }
            break;
         case 922199079:
            if (var9.equals("BATTLELEVELS")) {
               supportLevel = supportManagerAPI.getSupportLevel(SupportLevelType.SKILL_API);
               break label40;
            }
            break;
         case 2127542824:
            if (var9.equals("HEROES")) {
               supportLevel = supportManagerAPI.getSupportLevel(SupportLevelType.HEROES);
               break label40;
            }
         }

         supportLevel = null;
      }

      int playerLevel = supportLevel != null ? supportLevel.getPlayerLevel(player) : player.getLevel();
      return reqLevel <= playerLevel;
   }

   public final boolean isAllowedReqPermission(Player player, ItemStack item) {
      String reqPermission = this.getRequirementPermission(item);
      return reqPermission != null ? player.hasPermission(reqPermission) : true;
   }

   public final boolean isAllowedReqClass(Player player, ItemStack item) {
      AgarthaLibAPI agarthaLibAPI = AgarthaLibAPI.getInstance();
      SupportManagerAPI supportManagerAPI = agarthaLibAPI.getPluginManagerAPI().getSupportManager();
      String itemClass = this.getRequirementClass(item);
      if (itemClass == null) {
         return true;
      } else if (!supportManagerAPI.isSupportClass()) {
         return true;
      } else {
         SupportClass supportClass = this.getSupportReqClass();
         String playerClass = supportClass.getPlayerMainClassName(player);
         return playerClass != null ? playerClass.equalsIgnoreCase(itemClass) : false;
      }
   }

   public final SupportClass getSupportReqClass() {
      AgarthaLibAPI agarthaLibAPI = AgarthaLibAPI.getInstance();
      SupportManagerAPI supportManagerAPI = agarthaLibAPI.getPluginManagerAPI().getSupportManager();
      MainConfig mainConfig = MainConfig.getInstance();
      String textClassType = mainConfig.getSupportTypeClass();
      SupportClass supportClass;
      String var6;
      switch((var6 = textClassType.toUpperCase()).hashCode()) {
      case -1369886167:
         if (var6.equals("SKILLAPI")) {
            supportClass = supportManagerAPI.getSupportClass(SupportClassType.SKILL_API);
            return supportClass;
         }
         break;
      case 2020783:
         if (var6.equals("AUTO")) {
            supportClass = supportManagerAPI.getSupportClass();
            return supportClass;
         }
         break;
      case 483738379:
         if (var6.equals("SKILLSPRO")) {
            supportClass = supportManagerAPI.getSupportClass(SupportClassType.SKILLS_PRO);
            return supportClass;
         }
         break;
      case 2127542824:
         if (var6.equals("HEROES")) {
            supportClass = supportManagerAPI.getSupportClass(SupportClassType.HEROES);
            return supportClass;
         }
      }

      supportClass = null;
      return supportClass;
   }

   public final boolean isSupportReqClass() {
      return this.getSupportReqClass() != null;
   }

   private final String getMetadataSoulBound() {
      return "SoulBound";
   }

   private final String getKeyReqSoulBound() {
      return this.getKeyReqSoulBound((OfflinePlayer)null, true);
   }

   private final String getKeyReqSoulBound(OfflinePlayer player, boolean justCheck) {
      MainConfig mainConfig = MainConfig.getInstance();
      String bound = player != null ? player.getName() : null;
      String colorSoulBound = mainConfig.getRequirementColorSoulBound();
      return justCheck ? MainConfig.KEY_REQ_BOUND + colorSoulBound : MainConfig.KEY_REQ_BOUND + colorSoulBound + bound + MainConfig.KEY_REQ_BOUND + colorSoulBound;
   }

   private final String getKeyReqLevel() {
      return this.getKeyReqLevel(0, true);
   }

   private final String getKeyReqLevel(int level, boolean justCheck) {
      MainConfig mainConfig = MainConfig.getInstance();
      String colorLevel = mainConfig.getRequirementColorLevel();
      return justCheck ? MainConfig.KEY_REQ_LEVEL + colorLevel : MainConfig.KEY_REQ_LEVEL + colorLevel + level + MainConfig.KEY_REQ_LEVEL + colorLevel;
   }

   private final String getKeyReqPermission() {
      return this.getKeyReqPermission((String)null, true);
   }

   private final String getKeyReqPermission(String permission, boolean justCheck) {
      MainConfig mainConfig = MainConfig.getInstance();
      String colorPermission = mainConfig.getRequirementColorPermission();
      return justCheck ? MainConfig.KEY_REQ_PERMISSION + colorPermission : MainConfig.KEY_REQ_PERMISSION + colorPermission + permission + MainConfig.KEY_REQ_PERMISSION + colorPermission;
   }

   private final String getKeyReqClass() {
      return this.getKeyReqClass((String)null, true);
   }

   private final String getKeyReqClass(String reqClass, boolean justCheck) {
      MainConfig mainConfig = MainConfig.getInstance();
      String colorClass = mainConfig.getRequirementColorClass();
      return justCheck ? MainConfig.KEY_REQ_CLASS + colorClass : MainConfig.KEY_REQ_CLASS + colorClass + reqClass + MainConfig.KEY_REQ_CLASS + colorClass;
   }
}
