package com.praya.myitems.listener.main;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.element.ElementBoostStats;
import api.praya.myitems.builder.event.CombatCriticalDamageEvent;
import api.praya.myitems.builder.event.CombatPreCriticalEvent;
import api.praya.myitems.builder.item.ItemSetBonusEffectEntity;
import api.praya.myitems.builder.item.ItemSetBonusEffectStats;
import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsWeapon;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.RequirementManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.enums.main.SlotType;
import core.praya.agarthalib.utility.CombatUtil;
import core.praya.agarthalib.utility.EntityUtil;
import core.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.utility.MathUtil;
import core.praya.agarthalib.utility.ProjectileUtil;
import core.praya.agarthalib.utility.SenderUtil;
import core.praya.agarthalib.utility.ServerEventUtil;
import core.praya.agarthalib.utility.ServerUtil;
import core.praya.agarthalib.utility.TextUtil;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

public class ListenerEntityDamageByEntity extends HandlerEvent implements Listener {
   public ListenerEntityDamageByEntity(MyItems plugin) {
      super(plugin);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void checkBoundAttacker(EntityDamageByEntityEvent event) {
      GameManager gameManager = this.plugin.getGameManager();
      PluginManager pluginManager = this.plugin.getPluginManager();
      RequirementManager requirementManager = gameManager.getRequirementManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      if (!event.isCancelled()) {
         Entity attacker = event.getDamager();
         if (attacker instanceof Player) {
            Player player = (Player)attacker;
            Slot[] var11;
            int var10 = (var11 = Slot.values()).length;

            for(int var9 = 0; var9 < var10; ++var9) {
               Slot slot = var11[var9];
               if (slot.getID() < 2) {
                  ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, slot);
                  if (EquipmentUtil.loreCheck(item)) {
                     if (!requirementManager.isAllowed(player, item)) {
                        String message = TextUtil.placeholder(lang.getText("Item_Lack_Requirement"), "Item", EquipmentUtil.getDisplayName(item));
                        event.setCancelled(true);
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
   public void entityDamageByEntityEvent(EntityDamageByEntityEvent event) {
      PluginManager pluginManager = this.plugin.getPluginManager();
      GameManager gameManager = this.plugin.getGameManager();
      LoreStatsManager statsManager = gameManager.getStatsManager();
      SocketManager socketManager = gameManager.getSocketManager();
      ElementManager elementManager = gameManager.getElementManager();
      AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
      ItemSetManager itemSetManager = gameManager.getItemSetManager();
      LanguageManager lang = pluginManager.getLanguageManager();
      MainConfig mainConfig = MainConfig.getInstance();
      if (!event.isCancelled()) {
         Entity entityAttacker = event.getDamager();
         Entity entityVictims = event.getEntity();
         boolean reverse;
         LivingEntity attacker;
         if (ProjectileUtil.isProjectile(entityAttacker)) {
            Projectile projectile = ProjectileUtil.parseProjectile(entityAttacker);
            ProjectileSource source = projectile.getShooter();
            if (!(source instanceof LivingEntity)) {
               return;
            }

            attacker = EntityUtil.parseLivingEntity(source);
            reverse = EquipmentUtil.holdBow(attacker) ? EquipmentUtil.getActiveSlotBow(attacker).equals(Slot.OFFHAND) : false;
         } else {
            if (!EntityUtil.isLivingEntity(entityAttacker)) {
               return;
            }

            attacker = EntityUtil.parseLivingEntity(entityAttacker);
            reverse = false;
         }

         if (!EntityUtil.isLivingEntity(entityVictims)) {
            return;
         }

         LivingEntity victims = EntityUtil.parseLivingEntity(entityVictims);
         if (!Bridge.getBridgeLivingEntity().isLivingEntity(attacker)) {
            return;
         }

         if (!Bridge.getBridgeLivingEntity().isLivingEntity(victims)) {
            return;
         }

         boolean isSkillDamage = CombatUtil.isSkillDamage(victims);
         boolean isAreaDamage = CombatUtil.isAreaDamage(victims);
         if (CombatUtil.hasMetadataInstantDamage(victims)) {
            CombatUtil.removeMetadataInstantDamage(victims);
            return;
         }

         DamageCause damageCause = event.getCause();
         ItemStack itemMainHand = Bridge.getBridgeEquipment().getEquipment(attacker, Slot.MAINHAND);
         ItemStack itemOffHand = Bridge.getBridgeEquipment().getEquipment(attacker, Slot.OFFHAND);
         double baseDamage = !ServerUtil.isVersion_1_12() ? event.getOriginalDamage(DamageModifier.BASE) : 0.0D;
         boolean enableVanillaDamage = mainConfig.isModifierEnableVanillaDamage();
         double damage = event.getDamage() - baseDamage;
         boolean customDamage = true;
         boolean enableCustomMobDamage;
         if (!EntityUtil.isPlayer(attacker)) {
            enableCustomMobDamage = mainConfig.isModifierEnableCustomMobDamage();
            customDamage = enableCustomMobDamage;
         } else {
            enableCustomMobDamage = mainConfig.isModifierEnableCustomModifier();
            customDamage = enableCustomMobDamage;
         }

         if (!damageCause.equals(DamageCause.PROJECTILE) && EquipmentUtil.isSolid(itemMainHand)) {
            Material materialMainHand = itemMainHand.getType();
            if (materialMainHand.equals(Material.BOW) && !isSkillDamage && !isAreaDamage) {
               customDamage = false;
            }
         }

         double scaleDamageOverall;
         if (enableVanillaDamage) {
            scaleDamageOverall = mainConfig.getModifierScaleDamageVanilla();
            if (scaleDamageOverall > 0.0D) {
               double vanillaDamage = scaleDamageOverall * baseDamage;
               damage += vanillaDamage;
            }
         } else {
            --damage;
         }

         if (customDamage) {
            LoreStatsWeapon loreStatsAttacker = statsManager.getLoreStatsWeapon(attacker, reverse);
            LoreStatsArmor loreStatsVictims = statsManager.getLoreStatsArmor(victims);
            SocketGemsProperties socketAttacker = socketManager.getSocketProperties(attacker);
            SocketGemsProperties socketVictims = socketManager.getSocketProperties(victims);
            HashMap<String, Double> elementAttacker = elementManager.getMapElement(attacker, SlotType.WEAPON);
            HashMap<String, Double> elementVictims = elementManager.getMapElement(victims, SlotType.ARMOR);
            HashMap<String, Double> mapElement = elementManager.getElementCalculation(elementAttacker, elementVictims);
            ElementBoostStats elementBoostStatsAttacker = elementManager.getElementBoostStats(mapElement);
            HashMap<AbilityWeapon, Integer> mapAbilityWeapon = abilityWeaponManager.getMapAbilityWeapon(attacker, true);
            ItemSetBonusEffectEntity itemSetBonusEffectEntityAttacker = itemSetManager.getItemSetBonusEffectEntity(attacker);
            ItemSetBonusEffectEntity itemSetBonusEffectEntityVictims = itemSetManager.getItemSetBonusEffectEntity(victims);
            ItemSetBonusEffectStats itemSetBonusEffectStatsAttacker = itemSetBonusEffectEntityAttacker.getEffectStats();
            ItemSetBonusEffectStats itemSetBonusEffectStatsVictims = itemSetBonusEffectEntityVictims.getEffectStats();
            Iterator var41 = itemSetBonusEffectEntityAttacker.getAllAbilityWeapon().iterator();

            while(var41.hasNext()) {
               AbilityWeapon abilityWeaponItemSet = (AbilityWeapon)var41.next();
               int maxGrade = abilityWeaponItemSet.getMaxGrade();
               int totalGrade;
               if (mapAbilityWeapon.containsKey(abilityWeaponItemSet)) {
                  totalGrade = (Integer)mapAbilityWeapon.get(abilityWeaponItemSet) + itemSetBonusEffectEntityAttacker.getGradeAbilityWeapon(abilityWeaponItemSet);
                  mapAbilityWeapon.put(abilityWeaponItemSet, Math.min(maxGrade, totalGrade));
               } else {
                  totalGrade = itemSetBonusEffectEntityAttacker.getGradeAbilityWeapon(abilityWeaponItemSet);
                  mapAbilityWeapon.put(abilityWeaponItemSet, Math.min(maxGrade, totalGrade));
               }
            }

            double scaleDamageCustom = mainConfig.getModifierScaleDamageCustom();
            double statsDamage = loreStatsAttacker.getDamage();
            double socketDamage = socketAttacker.getAdditionalDamage() + statsDamage * (socketAttacker.getPercentDamage() / 100.0D);
            double abilityDamage = abilityWeaponManager.getTotalBaseBonusDamage(mapAbilityWeapon) + statsDamage * (abilityWeaponManager.getTotalBasePercentDamage(mapAbilityWeapon) / 100.0D);
            double elementDamage = elementBoostStatsAttacker.getBaseAdditionalDamage() + statsDamage * (elementBoostStatsAttacker.getBasePercentDamage() / 100.0D);
            double itemSetDamage = itemSetBonusEffectStatsAttacker.getAdditionalDamage() + statsDamage * (itemSetBonusEffectStatsAttacker.getPercentDamage() / 100.0D);
            double attributeDamage = scaleDamageCustom * (statsDamage + socketDamage + abilityDamage + elementDamage + itemSetDamage);
            double statsDefense = loreStatsVictims.getDefense();
            double socketDefense = socketVictims.getAdditionalDefense() + statsDefense * (socketVictims.getPercentDefense() / 100.0D);
            double itemSetDefense = itemSetBonusEffectStatsVictims.getAdditionalDefense() + statsDefense * (itemSetBonusEffectStatsVictims.getPercentDefense() / 100.0D);
            double attributeDefense = statsDefense + socketDefense + itemSetDefense;
            boolean enableOffHand = mainConfig.isAbilityWeaponEnableOffHand();
            boolean hasStatsCriticalChance = statsManager.hasLoreStats(itemMainHand, LoreStatsEnum.CRITICAL_CHANCE) || enableOffHand && statsManager.hasLoreStats(itemOffHand, LoreStatsEnum.CRITICAL_CHANCE);
            boolean hasStatsCriticalDamage = statsManager.hasLoreStats(itemMainHand, LoreStatsEnum.CRITICAL_DAMAGE) || enableOffHand && statsManager.hasLoreStats(itemOffHand, LoreStatsEnum.CRITICAL_DAMAGE);
            double penetration = 0.0D;
            double pvpDamage = 0.0D;
            double pveDamage = 0.0D;
            double defense = 0.0D;
            double pvpDefense = 0.0D;
            double pveDefense = 0.0D;
            double cc = 5.0D;
            double cd = 1.2D;
            double attackAoERadius = 0.0D;
            double attackAoEDamage = 0.0D;
            double blockAmount = 25.0D;
            double blockRate = 0.0D;
            double accuration = 100.0D;
            damage = !isSkillDamage && !isAreaDamage ? damage + attributeDamage : damage;
            penetration = penetration + loreStatsAttacker.getPenetration() + socketAttacker.getPenetration() + itemSetBonusEffectStatsAttacker.getPenetration();
            pvpDamage = pvpDamage + loreStatsAttacker.getPvPDamage() + socketAttacker.getPvPDamage() + itemSetBonusEffectStatsAttacker.getPvPDamage();
            pveDamage = pveDamage + loreStatsAttacker.getPvEDamage() + socketAttacker.getPvEDamage() + itemSetBonusEffectStatsAttacker.getPvEDamage();
            defense += attributeDefense;
            pvpDefense = pvpDefense + loreStatsVictims.getPvPDefense() + socketVictims.getPvPDefense() + itemSetBonusEffectStatsVictims.getPvPDefense();
            pveDefense = pveDefense + loreStatsVictims.getPvEDefense() + socketVictims.getPvEDefense() + itemSetBonusEffectStatsVictims.getPvEDefense();
            cc = !hasStatsCriticalChance ? cc : loreStatsAttacker.getCriticalChance() + socketAttacker.getCriticalChance() + itemSetBonusEffectStatsAttacker.getCriticalChance();
            cd = !hasStatsCriticalDamage ? cd : 1.0D + loreStatsAttacker.getCriticalDamage() + (socketAttacker.getCriticalDamage() + itemSetBonusEffectStatsAttacker.getCriticalDamage()) / 100.0D;
            attackAoERadius = attackAoERadius + loreStatsAttacker.getAttackAoERadius() + socketAttacker.getAttackAoERadius() + itemSetBonusEffectStatsAttacker.getAttackAoERadius();
            attackAoEDamage = attackAoEDamage + loreStatsAttacker.getAttackAoEDamage() + socketAttacker.getAttackAoEDamage() + itemSetBonusEffectStatsAttacker.getAttackAoEDamage();
            blockAmount = blockAmount + loreStatsVictims.getBlockAmount() + socketVictims.getBlockAmount() + itemSetBonusEffectStatsVictims.getBlockAmount();
            blockRate = blockRate + loreStatsVictims.getBlockRate() + socketVictims.getBlockRate() + itemSetBonusEffectStatsVictims.getBlockRate();
            accuration = accuration + loreStatsAttacker.getHitRate() + socketAttacker.getHitRate() + itemSetBonusEffectStatsAttacker.getHitRate() - (loreStatsVictims.getDodgeRate() + socketVictims.getDodgeRate() + itemSetBonusEffectStatsVictims.getDodgeRate());
            if (MathUtil.chanceOf(100.0D - accuration)) {
               Player player;
               String message;
               if (EntityUtil.isPlayer(attacker)) {
                  player = EntityUtil.parsePlayer(attacker);
                  message = lang.getText("Attack_Miss");
                  SenderUtil.playSound(player, SoundEnum.ENTITY_BAT_TAKEOFF);
                  SenderUtil.sendMessage(player, message);
               }

               if (EntityUtil.isPlayer(victims)) {
                  player = EntityUtil.parsePlayer(victims);
                  message = lang.getText("Attack_Dodge");
                  SenderUtil.playSound(player, SoundEnum.ENTITY_BAT_TAKEOFF);
                  SenderUtil.sendMessage(player, message);
               }

               event.setCancelled(true);
               return;
            }

            boolean enableCustomCritical = mainConfig.isModifierEnableCustomCritical();
            accuration = MathUtil.limitDouble(accuration, 0.0D, accuration);
            damage *= 1.0D + Math.log10(accuration / 100.0D);
            if (MathUtil.chanceOf(blockRate)) {
               blockAmount = MathUtil.limitDouble(blockAmount, 0.0D, 100.0D);
               damage = damage * (100.0D - blockAmount) / 100.0D;
               Player player;
               String message;
               if (EntityUtil.isPlayer(attacker)) {
                  player = EntityUtil.parsePlayer(attacker);
                  message = lang.getText("Attack_Block");
                  SenderUtil.playSound(player, SoundEnum.BLOCK_ANVIL_PLACE);
                  SenderUtil.sendMessage(player, message);
               }

               if (EntityUtil.isPlayer(victims)) {
                  player = EntityUtil.parsePlayer(victims);
                  message = lang.getText("Attack_Blocked");
                  SenderUtil.playSound(player, SoundEnum.BLOCK_ANVIL_PLACE);
                  SenderUtil.sendMessage(player, message);
               }
            }

            if (enableCustomCritical) {
               CombatPreCriticalEvent combatPreCriticalEvent = new CombatPreCriticalEvent(attacker, victims, cc);
               ServerEventUtil.callEvent(combatPreCriticalEvent);
               if (!combatPreCriticalEvent.isCancelled() && combatPreCriticalEvent.isCritical()) {
                  CombatCriticalDamageEvent combatCriticalDamageEvent = new CombatCriticalDamageEvent(attacker, victims, damage, cd, 0.0D);
                  ServerEventUtil.callEvent(combatCriticalDamageEvent);
                  if (!combatCriticalDamageEvent.isCancelled()) {
                     damage = combatCriticalDamageEvent.getCalculationDamage();
                  }
               }
            }

            int grade;
            if (!isAreaDamage && !isSkillDamage) {
               Slot[] var95;
               grade = (var95 = Slot.values()).length;

               for(int var130 = 0; var130 < grade; ++var130) {
                  Slot slot = var95[var130];
                  LivingEntity holder = slot.getType().equals(SlotType.WEAPON) ? attacker : victims;
                  ItemStack item = Bridge.getBridgeEquipment().getEquipment(holder, slot);
                  if (EquipmentUtil.isSolid(item) && !item.getType().equals(Material.BOW)) {
                     boolean enableItemBrokenMessage = mainConfig.isStatsEnableItemBrokenMessage();
                     statsManager.damageDurability(item);
                     if (enableItemBrokenMessage && !statsManager.checkDurability(item)) {
                        statsManager.sendBrokenCode(holder, slot);
                     }
                  }
               }
            }

            if (!isAreaDamage) {
               elementManager.applyElementPotion(attacker, victims, mapElement);
               Iterator var131 = mapAbilityWeapon.keySet().iterator();

               while(var131.hasNext()) {
                  AbilityWeapon abilityWeapon = (AbilityWeapon)var131.next();
                  grade = (Integer)mapAbilityWeapon.get(abilityWeapon);
                  abilityWeapon.cast(entityAttacker, entityVictims, grade, damage);
               }
            }

            double bonusPercentDamage = EntityUtil.isPlayer(victims) ? pvpDamage : pveDamage;
            double bonusPercentDefense = EntityUtil.isPlayer(attacker) ? pvpDefense : pveDefense;
            double scaleDefenseOverall = mainConfig.getModifierScaleDefenseOverall();
            double scaleDamageMobReceive = mainConfig.getModifierScaleMobDamageReceive();
            boolean enableFlatDamage = mainConfig.isModifierEnableFlatDamage();
            penetration = MathUtil.limitDouble(penetration, 0.0D, 100.0D);
            defense *= scaleDefenseOverall;
            defense = defense * (100.0D + bonusPercentDefense) / 100.0D;
            defense = defense * (100.0D - penetration) / 100.0D;
            damage = damage * (100.0D + bonusPercentDamage) / 100.0D;
            damage -= defense;
            damage = MathUtil.limitDouble(damage, 0.0D, damage);
            damage = EntityUtil.isPlayer(victims) ? damage : damage * scaleDamageMobReceive;
            if (!isAreaDamage && !isSkillDamage && attackAoERadius > 0.0D && attackAoEDamage > 0.0D) {
               double attackAoEDamageEntity = damage * (attackAoEDamage / 100.0D);
               Location attackAoELocation = victims.getLocation();
               Iterator var105 = CombatUtil.getNearbyUnits(attackAoELocation, attackAoERadius).iterator();

               while(var105.hasNext()) {
                  LivingEntity attackAoEVictim = (LivingEntity)var105.next();
                  if (!attackAoEVictim.equals(attacker) && !attackAoEVictim.equals(victims)) {
                     CombatUtil.areaDamage(attacker, attackAoEVictim, attackAoEDamageEntity);
                  }
               }
            }

            if (!enableFlatDamage) {
               label340: {
                  boolean enableBalancingSystem = mainConfig.isModifierEnableBalancingSystem();
                  boolean enableBalancingMob = mainConfig.isModifierEnableBalancingMob();
                  if (EntityUtil.isPlayer(victims)) {
                     if (!enableBalancingSystem) {
                        break label340;
                     }
                  } else if (!enableBalancingMob) {
                     break label340;
                  }

                  double modusValue = mainConfig.getModifierModusValue();
                  double divider = 1.0D + Math.log10(1.0D + 10.0D * defense / modusValue);
                  damage /= divider;
                  damage = damage * 10.0D / modusValue;
               }
            }
         }

         if (!event.isCancelled()) {
            scaleDamageOverall = mainConfig.getModifierScaleDamageOverall();
            boolean enableVanillaModifier = mainConfig.isModifierEnableVanillaDamage();
            damage *= scaleDamageOverall;
            if (enableVanillaModifier) {
               PotionEffect potion;
               Iterator var116;
               if (attacker.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                  var116 = attacker.getActivePotionEffects().iterator();

                  while(var116.hasNext()) {
                     potion = (PotionEffect)var116.next();
                     if (potion.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                        damage += damage * (double)potion.getAmplifier() / 10.0D;
                        break;
                     }
                  }
               }

               if (victims.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                  var116 = victims.getActivePotionEffects().iterator();

                  while(var116.hasNext()) {
                     potion = (PotionEffect)var116.next();
                     if (potion.getType().equals(PotionEffectType.DAMAGE_RESISTANCE)) {
                        damage -= damage * (double)potion.getAmplifier() / 20.0D;
                        break;
                     }
                  }
               }

               if (!ServerUtil.isVersion_1_12() && event.isApplicable(DamageModifier.ABSORPTION) && event.getDamage(DamageModifier.ABSORPTION) < 0.0D) {
                  double scaleAbsorbEffect = mainConfig.getModifierScaleAbsorbEffect();
                  if (scaleAbsorbEffect > 1.0D) {
                     event.setDamage(DamageModifier.ABSORPTION, -damage);
                  } else if (scaleAbsorbEffect < 0.1D) {
                     event.setDamage(DamageModifier.ABSORPTION, -(damage * 0.1D));
                  } else {
                     event.setDamage(DamageModifier.ABSORPTION, -(damage * scaleAbsorbEffect));
                  }
               }
            }

            if (!ServerUtil.isVersion_1_12()) {
               boolean enableVanillaFormulaArmor = mainConfig.isModifierEnableVanillaFormulaArmor();
               if (enableVanillaFormulaArmor && customDamage) {
                  double modDamage = 0.0D;
                  double modArmor = 0.0D;
                  if (event.isApplicable(DamageModifier.BASE)) {
                     modDamage = event.getDamage(DamageModifier.BASE);
                  }

                  if (event.isApplicable(DamageModifier.ARMOR)) {
                     modArmor = event.getDamage(DamageModifier.ARMOR);
                  }

                  double calculation = modDamage != 0.0D ? (modDamage + modArmor) / modDamage : 0.0D;
                  damage *= calculation;
               }
            }

            event.setDamage(damage);
            return;
         }
      }

   }
}
