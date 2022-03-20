package com.praya.myitems.manager.register;

import api.praya.myitems.builder.ability.AbilityWeapon;
import com.praya.agarthalib.utility.ServerUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponAirShock;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponBadLuck;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponBlind;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponBubbleDeflector;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponCannibalism;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponConfuse;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponCurse;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponDarkFlame;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponDarkImpact;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponFlame;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponFlameWheel;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponFreeze;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponHarm;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponHungry;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponLevitation;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponLightning;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponPoison;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponRoots;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponSlow;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponTired;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponVampirism;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponVenomBlast;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponVenomSpread;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponWeak;
import com.praya.myitems.builder.ability.weapon.AbilityWeaponWither;
import com.praya.myitems.builder.handler.HandlerManager;
import core.praya.agarthalib.enums.main.VersionNMS;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class RegisterAbilityWeaponManager extends HandlerManager {
   private final HashMap<String, AbilityWeapon> mapAbilityWeapon = new HashMap();

   protected RegisterAbilityWeaponManager(MyItems plugin) {
      super(plugin);
      this.setup();
   }

   public final Collection<String> getAbilityIDs() {
      return this.mapAbilityWeapon.keySet();
   }

   public final Collection<AbilityWeapon> getAllAbilityWeapon() {
      return this.mapAbilityWeapon.values();
   }

   public final AbilityWeapon getAbilityWeapon(String ability) {
      if (ability != null) {
         Iterator var3 = this.getAbilityIDs().iterator();

         while(var3.hasNext()) {
            String key = (String)var3.next();
            if (key.equalsIgnoreCase(ability)) {
               return (AbilityWeapon)this.mapAbilityWeapon.get(key);
            }
         }
      }

      return null;
   }

   public final boolean isExists(String ability) {
      return this.getAbilityWeapon(ability) != null;
   }

   public final AbilityWeapon getAbilityWeaponByKeyLore(String keyLore) {
      if (keyLore != null) {
         String coloredKeyLore = TextUtil.colorful(keyLore);
         Iterator var4 = this.getAllAbilityWeapon().iterator();

         while(var4.hasNext()) {
            AbilityWeapon key = (AbilityWeapon)var4.next();
            if (TextUtil.colorful(key.getKeyLore()).equalsIgnoreCase(coloredKeyLore)) {
               return key;
            }
         }
      }

      return null;
   }

   public final boolean isKeyLoreExists(String keyLore) {
      return this.getAbilityWeaponByKeyLore(keyLore) != null;
   }

   public final boolean register(AbilityWeapon abilityWeapon) {
      if (abilityWeapon != null) {
         String id = abilityWeapon.getID();
         if (!this.isExists(id)) {
            this.mapAbilityWeapon.put(id, abilityWeapon);
            return true;
         }
      }

      return false;
   }

   public final boolean unregister(String ability) {
      AbilityWeapon abilityWeapon = this.getAbilityWeapon(ability);
      if (abilityWeapon != null) {
         String id = abilityWeapon.getID();
         this.mapAbilityWeapon.remove(id);
         return true;
      } else {
         return false;
      }
   }

   private final void setup() {
      AbilityWeapon abilityWeaponAirShock = AbilityWeaponAirShock.getInstance();
      AbilityWeapon abilityWeaponBlind = AbilityWeaponBlind.getInstance();
      AbilityWeapon abilityWeaponBubbleDeflector = AbilityWeaponBubbleDeflector.getInstance();
      AbilityWeapon abilityWeaponCannibalism = AbilityWeaponCannibalism.getInstance();
      AbilityWeapon abilityWeaponConfuse = AbilityWeaponConfuse.getInstance();
      AbilityWeapon abilityWeaponCurse = AbilityWeaponCurse.getInstance();
      AbilityWeapon abilityWeaponDarkFlame = AbilityWeaponDarkFlame.getInstance();
      AbilityWeapon abilityWeaponDarkImpact = AbilityWeaponDarkImpact.getInstance();
      AbilityWeapon abilityWeaponFlame = AbilityWeaponFlame.getInstance();
      AbilityWeapon abilityWeaponFlameWheel = AbilityWeaponFlameWheel.getInstance();
      AbilityWeapon abilityWeaponFreeze = AbilityWeaponFreeze.getInstance();
      AbilityWeapon abilityWeaponHarm = AbilityWeaponHarm.getInstance();
      AbilityWeapon abilityWeaponHungry = AbilityWeaponHungry.getInstance();
      AbilityWeapon abilityWeaponLightning = AbilityWeaponLightning.getInstance();
      AbilityWeapon abilityWeaponPoison = AbilityWeaponPoison.getInstance();
      AbilityWeapon abilityWeaponRoots = AbilityWeaponRoots.getInstance();
      AbilityWeapon abilityWeaponSlowness = AbilityWeaponSlow.getInstance();
      AbilityWeapon abilityWeaponTired = AbilityWeaponTired.getInstance();
      AbilityWeapon abilityWeaponVampirism = AbilityWeaponVampirism.getInstance();
      AbilityWeapon abilityWeaponVenomBlast = AbilityWeaponVenomBlast.getInstance();
      AbilityWeapon abilityWeaponVenomSpread = AbilityWeaponVenomSpread.getInstance();
      AbilityWeapon abilityWeaponWeakness = AbilityWeaponWeak.getInstance();
      AbilityWeapon abilityWeaponWither = AbilityWeaponWither.getInstance();
      this.register(abilityWeaponAirShock);
      this.register(abilityWeaponBlind);
      this.register(abilityWeaponBubbleDeflector);
      this.register(abilityWeaponCannibalism);
      this.register(abilityWeaponConfuse);
      this.register(abilityWeaponCurse);
      this.register(abilityWeaponDarkFlame);
      this.register(abilityWeaponDarkImpact);
      this.register(abilityWeaponFlame);
      this.register(abilityWeaponFlameWheel);
      this.register(abilityWeaponFreeze);
      this.register(abilityWeaponHarm);
      this.register(abilityWeaponHungry);
      this.register(abilityWeaponLightning);
      this.register(abilityWeaponPoison);
      this.register(abilityWeaponRoots);
      this.register(abilityWeaponSlowness);
      this.register(abilityWeaponTired);
      this.register(abilityWeaponVampirism);
      this.register(abilityWeaponVenomBlast);
      this.register(abilityWeaponVenomSpread);
      this.register(abilityWeaponWeakness);
      this.register(abilityWeaponWither);
      if (ServerUtil.isCompatible(VersionNMS.V1_9_R1)) {
         AbilityWeapon abilityWeaponBadLuck = AbilityWeaponBadLuck.getInstance();
         AbilityWeapon abilityWeaponLevitation = AbilityWeaponLevitation.getInstance();
         this.register(abilityWeaponBadLuck);
         this.register(abilityWeaponLevitation);
      }

   }
}
