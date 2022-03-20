package com.praya.myitems;

import com.praya.agarthalib.utility.PluginUtil;
import com.praya.agarthalib.utility.ServerEventUtil;
import com.praya.agarthalib.utility.ServerUtil;
import com.praya.myitems.command.CommandAttributes;
import com.praya.myitems.command.CommandEnchant;
import com.praya.myitems.command.CommandEnchantAdd;
import com.praya.myitems.command.CommandEnchantClear;
import com.praya.myitems.command.CommandEnchantRemove;
import com.praya.myitems.command.CommandFlag;
import com.praya.myitems.command.CommandFlagAdd;
import com.praya.myitems.command.CommandFlagClear;
import com.praya.myitems.command.CommandFlagRemove;
import com.praya.myitems.command.CommandItemName;
import com.praya.myitems.command.CommandLore;
import com.praya.myitems.command.CommandLoreAdd;
import com.praya.myitems.command.CommandLoreClear;
import com.praya.myitems.command.CommandLoreInsert;
import com.praya.myitems.command.CommandLoreRemove;
import com.praya.myitems.command.CommandLoreSet;
import com.praya.myitems.command.CommandMyItems;
import com.praya.myitems.command.CommandNBTClear;
import com.praya.myitems.command.CommandNotCompatible;
import com.praya.myitems.command.CommandSocket;
import com.praya.myitems.command.CommandUnbreakable;
import com.praya.myitems.listener.custom.ListenerCombatCriticalDamage;
import com.praya.myitems.listener.custom.ListenerMenuClose;
import com.praya.myitems.listener.custom.ListenerMenuOpen;
import com.praya.myitems.listener.custom.ListenerPowerCommandCast;
import com.praya.myitems.listener.custom.ListenerPowerPreCast;
import com.praya.myitems.listener.custom.ListenerPowerShootCast;
import com.praya.myitems.listener.custom.ListenerPowerSpecialCast;
import com.praya.myitems.listener.main.ListenerBlockBreak;
import com.praya.myitems.listener.main.ListenerBlockExplode;
import com.praya.myitems.listener.main.ListenerBlockPhysic;
import com.praya.myitems.listener.main.ListenerCommand;
import com.praya.myitems.listener.main.ListenerEntityDamage;
import com.praya.myitems.listener.main.ListenerEntityDamageByEntity;
import com.praya.myitems.listener.main.ListenerEntityDeath;
import com.praya.myitems.listener.main.ListenerEntityRegainHealth;
import com.praya.myitems.listener.main.ListenerEntityShootBow;
import com.praya.myitems.listener.main.ListenerHeldItem;
import com.praya.myitems.listener.main.ListenerInventoryClick;
import com.praya.myitems.listener.main.ListenerInventoryDrag;
import com.praya.myitems.listener.main.ListenerInventoryOpen;
import com.praya.myitems.listener.main.ListenerPlayerDropItem;
import com.praya.myitems.listener.main.ListenerPlayerInteract;
import com.praya.myitems.listener.main.ListenerPlayerInteractEntity;
import com.praya.myitems.listener.main.ListenerPlayerItemDamage;
import com.praya.myitems.listener.main.ListenerPlayerJoin;
import com.praya.myitems.listener.main.ListenerPlayerRespawn;
import com.praya.myitems.listener.main.ListenerPlayerSwapHandItems;
import com.praya.myitems.listener.main.ListenerProjectileHit;
import com.praya.myitems.listener.support.ListenerPlayerHealthMaxChange;
import com.praya.myitems.listener.support.ListenerPlayerHealthRegenChange;
import com.praya.myitems.listener.support.ListenerPlayerLevelUp;
import com.praya.myitems.listener.support.ListenerPlayerStaminaMaxChange;
import com.praya.myitems.listener.support.ListenerPlayerStaminaRegenChange;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.player.PlayerManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.manager.register.RegisterManager;
import com.praya.myitems.manager.task.TaskManager;
import com.praya.myitems.tabcompleter.TabCompleterAttributes;
import com.praya.myitems.tabcompleter.TabCompleterEnchantmentAdd;
import com.praya.myitems.tabcompleter.TabCompleterEnchantmentRemove;
import com.praya.myitems.tabcompleter.TabCompleterFlagAdd;
import com.praya.myitems.tabcompleter.TabCompleterFlagRemove;
import com.praya.myitems.tabcompleter.TabCompleterLoreRemove;
import com.praya.myitems.tabcompleter.TabCompleterMyItems;
import com.praya.myitems.tabcompleter.TabCompleterNotCompatible;
import com.praya.myitems.tabcompleter.TabCompleterSocket;
import com.praya.myitems.tabcompleter.TabCompleterUnbreakable;
import com.praya.myitems.utility.main.AntiBugUtil;
import core.praya.agarthalib.builder.face.Agartha;
import core.praya.agarthalib.enums.main.VersionNMS;
import java.util.List;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MyItems extends JavaPlugin implements Agartha {
   private final String type = "Premium";
   private final String placeholder = "myitems";
   private PluginManager pluginManager;
   private PlayerManager playerManager;
   private GameManager gameManager;
   private TaskManager taskManager;
   private RegisterManager registerManager;

   public String getPluginName() {
      return this.getName();
   }

   public String getPluginType() {
      return "Premium";
   }

   public String getPluginVersion() {
      return this.getDescription().getVersion();
   }

   public String getPluginPlaceholder() {
      return "myitems";
   }

   public String getPluginWebsite() {
      return this.getPluginManager().getPluginPropertiesManager().getWebsite();
   }

   public String getPluginLatest() {
      return this.getPluginManager().getPluginPropertiesManager().getPluginTypeVersion(this.getPluginType());
   }

   public List<String> getPluginDevelopers() {
      return this.getPluginManager().getPluginPropertiesManager().getDevelopers();
   }

   public final PluginManager getPluginManager() {
      return this.pluginManager;
   }

   public final GameManager getGameManager() {
      return this.gameManager;
   }

   public final PlayerManager getPlayerManager() {
      return this.playerManager;
   }

   public final TaskManager getTaskManager() {
      return this.taskManager;
   }

   public final RegisterManager getRegisterManager() {
      return this.registerManager;
   }

   public void onEnable() {
      this.setPluginManager();
      this.setPlayerManager();
      this.setGameManager();
      this.setTaskManager();
      this.setRegisterManager();
      this.getPluginManager().getDependencyManager().getDependencyConfig().setup();
      this.getPluginManager().getHookManager().getHookConfig().setup();
      this.setup();
      this.registerCommand();
      this.registerTabComplete();
      this.registerEvent();
      this.registerPlaceholder();
   }

   private final void setPluginManager() {
      this.pluginManager = new PluginManager(this);
      this.pluginManager.initialize();
   }

   private final void setGameManager() {
      this.gameManager = new GameManager(this);
   }

   private final void setPlayerManager() {
      this.playerManager = new PlayerManager(this);
   }

   private final void setTaskManager() {
      this.taskManager = new TaskManager(this);
   }

   private final void setRegisterManager() {
      this.registerManager = new RegisterManager(this);
   }

   private final void setup() {
      this.gameManager.getAbilityWeaponManager().getAbilityWeaponConfig().setup();
      this.gameManager.getElementManager().getElementConfig().setup();
      this.gameManager.getPowerManager().getPowerCommandManager().getPowerCommandConfig().setup();
      this.gameManager.getPowerManager().getPowerSpecialManager().getPowerSpecialConfig().setup();
      this.gameManager.getSocketManager().getSocketConfig().setup();
      this.gameManager.getItemManager().getItemConfig().setup();
      this.gameManager.getItemTypeManager().getItemTypeConfig().setup();
      this.gameManager.getItemTierManager().getItemTierConfig().setup();
      this.gameManager.getItemGeneratorManager().getItemGeneratorConfig().setup();
      this.gameManager.getItemSetManager().getItemSetConfig().setup();
   }

   private final void registerPlaceholder() {
      this.getPluginManager().getPlaceholderManager().registerAll();
   }

   private final void registerCommand() {
      CommandExecutor commandMyItems = new CommandMyItems(this);
      CommandExecutor commandAttributes = new CommandAttributes(this);
      CommandExecutor commandEnchant = new CommandEnchant(this);
      CommandExecutor commandEnchantAdd = new CommandEnchantAdd(this);
      CommandExecutor commandEnchantClear = new CommandEnchantClear(this);
      CommandExecutor commandEnchantRemove = new CommandEnchantRemove(this);
      CommandExecutor commandItemName = new CommandItemName(this);
      CommandExecutor commandLore = new CommandLore(this);
      CommandExecutor commandLoreAdd = new CommandLoreAdd(this);
      CommandExecutor commandLoreClear = new CommandLoreClear(this);
      CommandExecutor commandLoreInsert = new CommandLoreInsert(this);
      CommandExecutor commandLoreRemove = new CommandLoreRemove(this);
      CommandExecutor commandLoreSet = new CommandLoreSet(this);
      CommandExecutor commandNBTClear = new CommandNBTClear(this);
      CommandExecutor commandSocket = new CommandSocket(this);
      CommandExecutor commandUnbreakable = new CommandUnbreakable(this);
      CommandExecutor commandNotCompatible = new CommandNotCompatible(this);
      CommandExecutor commandFlag = ServerUtil.isCompatible(VersionNMS.V1_8_R1) ? new CommandFlag(this) : commandNotCompatible;
      CommandExecutor commandAddFlag = ServerUtil.isCompatible(VersionNMS.V1_8_R1) ? new CommandFlagAdd(this) : commandNotCompatible;
      CommandExecutor commandRemoveFlag = ServerUtil.isCompatible(VersionNMS.V1_8_R1) ? new CommandFlagRemove(this) : commandNotCompatible;
      CommandExecutor commandClearFlag = ServerUtil.isCompatible(VersionNMS.V1_8_R1) ? new CommandFlagClear(this) : commandNotCompatible;
      this.getCommand("MyItems").setExecutor(commandMyItems);
      this.getCommand("ItemAtt").setExecutor(commandAttributes);
      this.getCommand("Enchant").setExecutor(commandEnchant);
      this.getCommand("EnchantAdd").setExecutor(commandEnchantAdd);
      this.getCommand("EnchantRemove").setExecutor(commandEnchantRemove);
      this.getCommand("EnchantClear").setExecutor(commandEnchantClear);
      this.getCommand("ItemName").setExecutor(commandItemName);
      this.getCommand("Lore").setExecutor(commandLore);
      this.getCommand("LoreSet").setExecutor(commandLoreSet);
      this.getCommand("LoreInsert").setExecutor(commandLoreInsert);
      this.getCommand("LoreAdd").setExecutor(commandLoreAdd);
      this.getCommand("LoreRemove").setExecutor(commandLoreRemove);
      this.getCommand("LoreClear").setExecutor(commandLoreClear);
      this.getCommand("NBTClear").setExecutor(commandNBTClear);
      this.getCommand("Socket").setExecutor(commandSocket);
      this.getCommand("Unbreakable").setExecutor(commandUnbreakable);
      this.getCommand("Flag").setExecutor((CommandExecutor)commandFlag);
      this.getCommand("FlagAdd").setExecutor((CommandExecutor)commandAddFlag);
      this.getCommand("FlagRemove").setExecutor((CommandExecutor)commandRemoveFlag);
      this.getCommand("FlagClear").setExecutor((CommandExecutor)commandClearFlag);
   }

   private final void registerTabComplete() {
      TabCompleter tabCompleterMyItems = new TabCompleterMyItems(this);
      TabCompleter tabCompleterAttributes = new TabCompleterAttributes(this);
      TabCompleter tabCompleterEnchantmentAdd = new TabCompleterEnchantmentAdd(this);
      TabCompleter tabCompleterEnchantmentRemove = new TabCompleterEnchantmentRemove(this);
      TabCompleter tabCompleterLoreRemove = new TabCompleterLoreRemove(this);
      TabCompleter tabCompleterSocket = new TabCompleterSocket(this);
      TabCompleter tabCompleterUnbreakable = new TabCompleterUnbreakable(this);
      TabCompleter tabCompleterNotCompatible = new TabCompleterNotCompatible(this);
      TabCompleter tabCompleterFlagAdd = ServerUtil.isCompatible(VersionNMS.V1_8_R1) ? new TabCompleterFlagAdd(this) : tabCompleterNotCompatible;
      TabCompleter tabCompleterFlagRemove = ServerUtil.isCompatible(VersionNMS.V1_8_R1) ? new TabCompleterFlagRemove(this) : tabCompleterNotCompatible;
      this.getCommand("MyItems").setTabCompleter(tabCompleterMyItems);
      this.getCommand("ItemAtt").setTabCompleter(tabCompleterAttributes);
      this.getCommand("EnchantAdd").setTabCompleter(tabCompleterEnchantmentAdd);
      this.getCommand("EnchantRemove").setTabCompleter(tabCompleterEnchantmentRemove);
      this.getCommand("LoreRemove").setTabCompleter(tabCompleterLoreRemove);
      this.getCommand("Socket").setTabCompleter(tabCompleterSocket);
      this.getCommand("Unbreakable").setTabCompleter(tabCompleterUnbreakable);
      this.getCommand("FlagAdd").setTabCompleter((TabCompleter)tabCompleterFlagAdd);
      this.getCommand("FlagRemove").setTabCompleter((TabCompleter)tabCompleterFlagRemove);
   }

   private final void registerEvent() {
      Listener listenerBlockBreak = new ListenerBlockBreak(this);
      Listener listenerBlockPhysic = new ListenerBlockPhysic(this);
      Listener listenerCommand = new ListenerCommand(this);
      Listener listenerPlayerDropItem = new ListenerPlayerDropItem(this);
      Listener listenerEntityDamage = new ListenerEntityDamage(this);
      Listener listenerEntityDamageByEntity = new ListenerEntityDamageByEntity(this);
      Listener listenerEntityDeath = new ListenerEntityDeath(this);
      Listener listenerEntityRegainHealth = new ListenerEntityRegainHealth(this);
      Listener listenerHeldItem = new ListenerHeldItem(this);
      Listener listenerInventoryClick = new ListenerInventoryClick(this);
      Listener listenerInventoryDrag = new ListenerInventoryDrag(this);
      Listener listenerInventoryOpen = new ListenerInventoryOpen(this);
      Listener listenerPlayerItemDamage = new ListenerPlayerItemDamage(this);
      Listener listenerPlayerInteract = new ListenerPlayerInteract(this);
      Listener listenerPlayerInteractEntity = new ListenerPlayerInteractEntity(this);
      Listener listenerPlayerJoin = new ListenerPlayerJoin(this);
      Listener listenerPlayerRespawn = new ListenerPlayerRespawn(this);
      Listener listenerEntityShootBowEvent = new ListenerEntityShootBow(this);
      Listener listenerProjectileHit = new ListenerProjectileHit(this);
      Listener listenerCombatCriticalDamage = new ListenerCombatCriticalDamage(this);
      Listener listenerMenuClose = new ListenerMenuClose(this);
      Listener listenerMenuOpen = new ListenerMenuOpen(this);
      Listener listenerPowerCommandCast = new ListenerPowerCommandCast(this);
      Listener listenerPowerPreCast = new ListenerPowerPreCast(this);
      Listener listenerPowerShootCast = new ListenerPowerShootCast(this);
      Listener listenerPowerSpecialCast = new ListenerPowerSpecialCast(this);
      Listener listenerPlayerHealthMaxChange = new ListenerPlayerHealthMaxChange(this);
      ServerEventUtil.registerEvent(this, listenerBlockBreak);
      ServerEventUtil.registerEvent(this, listenerBlockPhysic);
      ServerEventUtil.registerEvent(this, listenerCommand);
      ServerEventUtil.registerEvent(this, listenerEntityDamage);
      ServerEventUtil.registerEvent(this, listenerEntityDamageByEntity);
      ServerEventUtil.registerEvent(this, listenerPlayerDropItem);
      ServerEventUtil.registerEvent(this, listenerEntityDeath);
      ServerEventUtil.registerEvent(this, listenerEntityRegainHealth);
      ServerEventUtil.registerEvent(this, listenerHeldItem);
      ServerEventUtil.registerEvent(this, listenerInventoryClick);
      ServerEventUtil.registerEvent(this, listenerInventoryDrag);
      ServerEventUtil.registerEvent(this, listenerInventoryOpen);
      ServerEventUtil.registerEvent(this, listenerPlayerItemDamage);
      ServerEventUtil.registerEvent(this, listenerPlayerInteract);
      ServerEventUtil.registerEvent(this, listenerPlayerInteractEntity);
      ServerEventUtil.registerEvent(this, listenerPlayerJoin);
      ServerEventUtil.registerEvent(this, listenerPlayerRespawn);
      ServerEventUtil.registerEvent(this, listenerEntityShootBowEvent);
      ServerEventUtil.registerEvent(this, listenerProjectileHit);
      ServerEventUtil.registerEvent(this, listenerCombatCriticalDamage);
      ServerEventUtil.registerEvent(this, listenerMenuClose);
      ServerEventUtil.registerEvent(this, listenerMenuOpen);
      ServerEventUtil.registerEvent(this, listenerPowerCommandCast);
      ServerEventUtil.registerEvent(this, listenerPowerPreCast);
      ServerEventUtil.registerEvent(this, listenerPowerShootCast);
      ServerEventUtil.registerEvent(this, listenerPowerSpecialCast);
      ServerEventUtil.registerEvent(this, listenerPlayerHealthMaxChange);
      if (ServerUtil.isCompatible(VersionNMS.V1_9_R1)) {
         Listener listenerBlockExplode = new ListenerBlockExplode(this);
         Listener listenerPlayerSwapHandItems = new ListenerPlayerSwapHandItems(this);
         ServerEventUtil.registerEvent(this, listenerBlockExplode);
         ServerEventUtil.registerEvent(this, listenerPlayerSwapHandItems);
      }

      if (PluginUtil.isPluginInstalled("SkillAPI")) {
         Listener listenerPlayerLevelUp = new ListenerPlayerLevelUp(this);
         ServerEventUtil.registerEvent(this, listenerPlayerLevelUp);
      }

      if (PluginUtil.isPluginInstalled("LifeEssence")) {
         Listener listenerPlayerHealthRegenChange = new ListenerPlayerHealthRegenChange(this);
         ServerEventUtil.registerEvent(this, listenerPlayerHealthRegenChange);
      }

      if (PluginUtil.isPluginInstalled("CombatStamina")) {
         Listener listenerPlayerStaminaMaxChange = new ListenerPlayerStaminaMaxChange(this);
         Listener listenerPlayerStaminaRegenChange = new ListenerPlayerStaminaRegenChange(this);
         ServerEventUtil.registerEvent(this, listenerPlayerStaminaMaxChange);
         ServerEventUtil.registerEvent(this, listenerPlayerStaminaRegenChange);
      }

   }

   public void onDisable() {
      AntiBugUtil.antiBugCustomStats();
   }
}
