package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.*;
import com.praya.agarthalib.utility.*;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.GameManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class AbilityWeaponVenomBlast extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeCastDamage, AbilityWeaponAttributeEffect {
    private static final String ABILITY_ID = "Venom_Blast";

    private AbilityWeaponVenomBlast(MyItems plugin, String id) {
        super(plugin, id);
    }

    public static final AbilityWeaponVenomBlast getInstance() {
        return AbilityWeaponVenomBlast.AbilityVenomBlastHelper.instance;
    }

    public String getKeyLore() {
        MainConfig mainConfig = MainConfig.getInstance();
        return mainConfig.getAbilityWeaponIdentifierVenomBlast();
    }

    public List<String> getDescription() {
        return null;
    }

    public int getMaxGrade() {
        MyItems plugin = (MyItems) JavaPlugin.getPlugin(MyItems.class);
        GameManager gameManager = plugin.getGameManager();
        AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Blast");
        return abilityWeaponProperties.getMaxGrade();
    }

    public double getBaseBonusDamage(int grade) {
        MyItems plugin = (MyItems) JavaPlugin.getPlugin(MyItems.class);
        GameManager gameManager = plugin.getGameManager();
        AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Blast");
        double baseBonusDamage = (double) grade * abilityWeaponProperties.getScaleBaseBonusDamage();
        return baseBonusDamage;
    }

    public double getBasePercentDamage(int grade) {
        MyItems plugin = (MyItems) JavaPlugin.getPlugin(MyItems.class);
        GameManager gameManager = plugin.getGameManager();
        AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Blast");
        double basePercentDamage = (double) grade * abilityWeaponProperties.getScaleBasePercentDamage();
        return basePercentDamage;
    }

    public double getCastBonusDamage(int grade) {
        MyItems plugin = (MyItems) JavaPlugin.getPlugin(MyItems.class);
        GameManager gameManager = plugin.getGameManager();
        AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Blast");
        double castBonusDamage = (double) grade * abilityWeaponProperties.getScaleCastBonusDamage();
        return castBonusDamage;
    }

    public double getCastPercentDamage(int grade) {
        MyItems plugin = (MyItems) JavaPlugin.getPlugin(MyItems.class);
        GameManager gameManager = plugin.getGameManager();
        AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Blast");
        double castPercentDamage = (double) grade * abilityWeaponProperties.getScaleCastPercentDamage();
        return castPercentDamage;
    }

    public int getEffectDuration(int grade) {
        MyItems plugin = (MyItems) JavaPlugin.getPlugin(MyItems.class);
        GameManager gameManager = plugin.getGameManager();
        AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Blast");
        return abilityWeaponProperties.getTotalDuration(grade);
    }

    public void cast(Entity caster, Entity target, int grade, double damage) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin(MyItems.class);
        MainConfig mainConfig = MainConfig.getInstance();
        final LivingEntity attacker;
        if (caster instanceof Projectile) {
            Projectile projectile = (Projectile) caster;
            ProjectileSource projectileSource = projectile.getShooter();
            if (projectileSource == null || !(projectileSource instanceof LivingEntity)) {
                return;
            }

            attacker = (LivingEntity) projectileSource;
        } else {
            attacker = (LivingEntity) caster;
        }

        if (target instanceof LivingEntity) {
            final LivingEntity victims = (LivingEntity) target;
            final Location location = victims.getLocation().add(0.0D, 0.5D, 0.0D);
            PotionEffectType potionType = PotionUtil.getPoisonType(victims);
            final double spreadDamage = this.getCastBonusDamage(grade) + damage * (this.getCastPercentDamage(grade) / 100.0D);
            final double blastDamage = spreadDamage * 2.0D;
            double decrease = 1.0D;
            int limit;
            final int duration = this.getEffectDuration(grade);
            int amplifier = potionType.equals(PotionEffectType.WITHER) ? 2 : 1;
            PotionEffect potion = PotionUtil.createPotion(potionType, duration, amplifier);
            final Set<LivingEntity> units = new HashSet();
            final Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
            victims.addPotionEffect(potion);
            (new BukkitRunnable() {
                int time = 0;
                double radius = 3.0D;
                double x;
                double z;

                public void run() {
                    if (this.time > 4) {
                        this.cancel();
                    } else {
                        if (this.time == 4) {
                            Bridge.getBridgeParticle().playParticle(players, ParticleEnum.EXPLOSION_HUGE, location, 10, 0.0D, 0.2D, 0.0D, 0.05000000074505806D);
                            Bridge.getBridgeSound().playSound(players, location, SoundEnum.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
                            Iterator var2 = CombatUtil.getNearbyUnits(location, 3.0D).iterator();

                            while (var2.hasNext()) {
                                LivingEntity unit = (LivingEntity) var2.next();
                                if (!unit.equals(attacker) && !unit.equals(victims) && !units.contains(unit)) {
                                    CombatUtil.areaDamage(attacker, unit, blastDamage);
                                    units.add(unit);
                                }
                            }
                        } else {
                            final Material materialDandelion = MaterialEnum.DANDELION.getMaterial();
                            Bridge.getBridgeSound().playSound(players, location, SoundEnum.BLOCK_GRAVEL_BREAK, 1.0F, 1.0F);
                            Iterator var3 = CombatUtil.getNearbyUnits(location, this.radius + 1.0D).iterator();

                            while (var3.hasNext()) {
                                LivingEntity unitx = (LivingEntity) var3.next();
                                if (!unitx.equals(attacker) && !unitx.equals(victims) && !units.contains(unitx)) {
                                    PotionEffectType potionType = PotionUtil.getPoisonType(unitx);
                                    int amplifier = potionType.equals(PotionEffectType.WITHER) ? 2 : 1;
                                    PotionEffect potion = PotionUtil.createPotion(potionType, duration, amplifier);
                                    unitx.addPotionEffect(potion);
                                    CombatUtil.areaDamage(attacker, unitx, spreadDamage);
                                    units.add(unitx);
                                }
                            }

                            for (int i = 0; i < 360; i += 30) {
                                this.x = Math.sin((double) i) * this.radius;
                                this.z = Math.cos((double) i) * this.radius;
                                location.add(this.x, 0.0D, this.z);
                                Bridge.getBridgeParticle().playParticle(players, ParticleEnum.VILLAGER_HAPPY, location, 3, 0.05D, 0.05D, 0.05D, 0.05000000074505806D);
                                Block block = location.getBlock();
                                Material material = block.getType();
                                if (material.equals(Material.AIR)) {
                                    Location locationBlock = block.getLocation();
                                    BlockUtil.set(locationBlock);
                                    block.setType(materialDandelion);
                                    block.setMetadata("Anti_Block_Physic", MetadataUtil.createMetadata(true));
                                    (new BukkitRunnable() {
                                        final Location locationFlower;

                                        {
                                            Location var2 = null;
                                            this.locationFlower = (Location) var2.clone();
                                        }

                                        public void run() {
                                            Block blockFlower = this.locationFlower.getBlock();
                                            Material materialFlower = blockFlower.getType();
                                            Location locationBlockFlower = blockFlower.getLocation();
                                            BlockUtil.remove(locationBlockFlower);
                                            if (materialFlower.equals(materialDandelion)) {
                                                blockFlower.setType(Material.AIR);
                                            }

                                        }
                                    }).runTaskLater(plugin, 5L);
                                }

                                location.subtract(this.x, 0.0D, this.z);
                            }

                            --this.radius;
                        }

                        ++this.time;
                    }
                }
            }).runTaskTimer(plugin, 0L, 5L);
        }

    }

    // $FF: synthetic method
    AbilityWeaponVenomBlast(MyItems var1, String var2, AbilityWeaponVenomBlast var3) {
        this(var1, var2);
    }

    private static class AbilityVenomBlastHelper {
        private static final AbilityWeaponVenomBlast instance;

        static {
            MyItems plugin = (MyItems) JavaPlugin.getPlugin(MyItems.class);
            instance = new AbilityWeaponVenomBlast(plugin, "Venom_Blast", (AbilityWeaponVenomBlast) null);
        }
    }
}
