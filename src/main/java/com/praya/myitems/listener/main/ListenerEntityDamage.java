package com.praya.myitems.listener.main;

import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.RequirementManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.main.MainBridge;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.utility.EntityUtil;
import core.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.utility.MathUtil;
import core.praya.agarthalib.utility.SenderUtil;
import core.praya.agarthalib.utility.TextUtil;
import java.util.HashMap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class ListenerEntityDamage extends HandlerEvent implements Listener {
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$org$bukkit$event$entity$EntityDamageEvent$DamageCause;

   public ListenerEntityDamage(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void checkBound(EntityDamageEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      PluginManager pluginManager = this.plugin.getPluginManager();
      RequirementManager requirementManager = gameManager.getRequirementManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      if (!event.isCancelled()) {
         Entity entity = event.getEntity();
         if (entity instanceof Player) {
            Player player = (Player)entity;
            Slot[] var11;
            int var10 = (var11 = Slot.values()).length;

            for(int var9 = 0; var9 < var10; ++var9) {
               Slot slot = var11[var9];
               if (slot.getID() > 1) {
                  ItemStack item = MainBridge.getBridgeEquipment().getEquipment(player, slot);
                  if (EquipmentUtil.loreCheck(item)) {
                     if (!requirementManager.isAllowed(player, item)) {
                        String message = TextUtil.placeholder(lang.getText("Item_Lack_Requirement"), "Item", EquipmentUtil.getDisplayName(item));
                        SenderUtil.sendMessage(player, message);
                        SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                     } else {
                        Integer lineUnbound = requirementManager.getLineRequirementSoulUnbound(item);
                        if (lineUnbound != null) {
                           String loreBound = requirementManager.getTextSoulBound(player);
                           Integer lineOld = requirementManager.getLineRequirementSoulBound(item);
                           HashMap<String, String> map = new HashMap();
                           if (lineOld != null) {
                              EquipmentUtil.removeLore(item, lineOld);
                           }

                           String message = lang.getText("Item_Bound");
                           map.put("item", EquipmentUtil.getDisplayName(item));
                           map.put("player", player.getName());
                           message = TextUtil.placeholder(map, message);
                           requirementManager.setMetadataSoulbound(player, item);
                           EquipmentUtil.setLore(item, lineUnbound, loreBound);
                           SenderUtil.sendMessage(player, message);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void damageEvent(EntityDamageEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      SocketManager socketManager = gameManager.getSocketManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (!event.isCancelled()) {
         boolean customDamage = mainConfig.isModifierEnableCustomModifier();
         if (customDamage) {
            Entity getEntity = event.getEntity();
            if (EntityUtil.isLivingEntity(getEntity) && MainBridge.getBridgeLivingEntity().isLivingEntity(getEntity)) {
               LivingEntity victims = EntityUtil.parseLivingEntity(getEntity);
               boolean defenseAffect;
               switch($SWITCH_TABLE$org$bukkit$event$entity$EntityDamageEvent$DamageCause()[event.getCause().ordinal()]) {
               case 1:
                  defenseAffect = mainConfig.isModifierDefenseAffectContact();
                  break;
               case 6:
                  defenseAffect = mainConfig.isModifierDefenseAffectFall();
                  break;
               case 12:
                  defenseAffect = mainConfig.isModifierDefenseAffectBlockExplosion();
                  break;
               case 13:
                  defenseAffect = mainConfig.isModifierDefenseAffectEntityExplosion();
                  break;
               case 21:
                  defenseAffect = mainConfig.isModifierDefenseAffectFallingBlock();
                  break;
               case 22:
                  defenseAffect = mainConfig.isModifierDefenseAffectThorn();
                  break;
               case 24:
                  defenseAffect = mainConfig.isModifierDefenseAffectCustom();
                  break;
               default:
                  defenseAffect = false;
               }

               if (defenseAffect) {
                  LoreStatsArmor loreStatsVictims = statsManager.getLoreStatsArmor(victims);
                  SocketGemsProperties socketVictims = socketManager.getSocketProperties(victims);
                  double scaleDefenseOverall = mainConfig.getModifierScaleDefenseOverall();
                  double scaleDefensePhysic = mainConfig.getModifierScaleDefensePhysic();
                  double damage = event.getDamage();
                  double defense = loreStatsVictims.getDefense() + socketVictims.getAdditionalDefense() + damage * socketVictims.getPercentDefense() / 100.0D;
                  defense = defense * scaleDefenseOverall * scaleDefensePhysic;
                  damage -= defense;
                  damage = MathUtil.limitDouble(damage, 1.0D, damage);
                  event.setDamage(damage);
               }
            }
         }
      }

   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$org$bukkit$event$entity$EntityDamageEvent$DamageCause() {
      int[] var10000 = $SWITCH_TABLE$org$bukkit$event$entity$EntityDamageEvent$DamageCause;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[DamageCause.values().length];

         try {
            var0[DamageCause.BLOCK_EXPLOSION.ordinal()] = 12;
         } catch (NoSuchFieldError var28) {
         }

         try {
            var0[DamageCause.CONTACT.ordinal()] = 1;
         } catch (NoSuchFieldError var27) {
         }

         try {
            var0[DamageCause.CRAMMING.ordinal()] = 27;
         } catch (NoSuchFieldError var26) {
         }

         try {
            var0[DamageCause.CUSTOM.ordinal()] = 24;
         } catch (NoSuchFieldError var25) {
         }

         try {
            var0[DamageCause.DRAGON_BREATH.ordinal()] = 23;
         } catch (NoSuchFieldError var24) {
         }

         try {
            var0[DamageCause.DROWNING.ordinal()] = 11;
         } catch (NoSuchFieldError var23) {
         }

         try {
            var0[DamageCause.DRYOUT.ordinal()] = 28;
         } catch (NoSuchFieldError var22) {
         }

         try {
            var0[DamageCause.ENTITY_ATTACK.ordinal()] = 2;
         } catch (NoSuchFieldError var21) {
         }

         try {
            var0[DamageCause.ENTITY_EXPLOSION.ordinal()] = 13;
         } catch (NoSuchFieldError var20) {
         }

         try {
            var0[DamageCause.ENTITY_SWEEP_ATTACK.ordinal()] = 3;
         } catch (NoSuchFieldError var19) {
         }

         try {
            var0[DamageCause.FALL.ordinal()] = 6;
         } catch (NoSuchFieldError var18) {
         }

         try {
            var0[DamageCause.FALLING_BLOCK.ordinal()] = 21;
         } catch (NoSuchFieldError var17) {
         }

         try {
            var0[DamageCause.FIRE.ordinal()] = 7;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[DamageCause.FIRE_TICK.ordinal()] = 8;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[DamageCause.FLY_INTO_WALL.ordinal()] = 25;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[DamageCause.HOT_FLOOR.ordinal()] = 26;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[DamageCause.LAVA.ordinal()] = 10;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[DamageCause.LIGHTNING.ordinal()] = 15;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[DamageCause.MAGIC.ordinal()] = 19;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[DamageCause.MELTING.ordinal()] = 9;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[DamageCause.POISON.ordinal()] = 18;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[DamageCause.PROJECTILE.ordinal()] = 4;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[DamageCause.STARVATION.ordinal()] = 17;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[DamageCause.SUFFOCATION.ordinal()] = 5;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[DamageCause.SUICIDE.ordinal()] = 16;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[DamageCause.THORNS.ordinal()] = 22;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[DamageCause.VOID.ordinal()] = 14;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[DamageCause.WITHER.ordinal()] = 20;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$org$bukkit$event$entity$EntityDamageEvent$DamageCause = var0;
         return var0;
      }
   }
}
