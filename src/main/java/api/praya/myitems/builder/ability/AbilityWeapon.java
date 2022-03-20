package api.praya.myitems.builder.ability;

import com.praya.myitems.MyItems;
import com.praya.myitems.manager.register.RegisterAbilityWeaponManager;
import com.praya.myitems.manager.register.RegisterManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbilityWeapon extends Ability {
   public AbilityWeapon(Plugin plugin, String id) {
      super(plugin, id);
   }

   public final boolean register() {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      RegisterManager registerManager = plugin.getRegisterManager();
      RegisterAbilityWeaponManager registerAbilityWeaponManager = registerManager.getRegisterAbilityWeaponManager();
      return registerAbilityWeaponManager.register(this);
   }
}
