package com.praya.myitems.manager.register;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class RegisterManager extends HandlerManager {
   private final RegisterAbilityWeaponManager registerAbilityWeaponManager;

   public RegisterManager(MyItems plugin) {
      super(plugin);
      this.registerAbilityWeaponManager = new RegisterAbilityWeaponManager(plugin);
   }

   public final RegisterAbilityWeaponManager getRegisterAbilityWeaponManager() {
      return this.registerAbilityWeaponManager;
   }
}
