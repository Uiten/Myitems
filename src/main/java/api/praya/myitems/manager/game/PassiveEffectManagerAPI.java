package api.praya.myitems.manager.game;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import core.praya.agarthalib.enums.main.Slot;
import java.util.Collection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PassiveEffectManagerAPI extends HandlerManager {
   protected PassiveEffectManagerAPI(MyItems plugin) {
      super(plugin);
   }

   public final String getTextPassiveEffect(PassiveEffectEnum effect, int grade) {
      return this.getPassiveEffectManager().getTextPassiveEffect(effect, grade);
   }

   public final int getHighestGradePassiveEffect(PassiveEffectEnum effect, LivingEntity livingEntity) {
      return this.getPassiveEffectManager().getHighestGradePassiveEffect(effect, livingEntity);
   }

   public final int getTotalGradePassiveEffect(PassiveEffectEnum effect, LivingEntity livingEntity) {
      return this.getPassiveEffectManager().getTotalGradePassiveEffect(effect, livingEntity);
   }

   public final int passiveEffectGrade(LivingEntity livingEntity, PassiveEffectEnum effect, Slot slot) {
      return this.getPassiveEffectManager().passiveEffectGrade(livingEntity, effect, slot);
   }

   public final int passiveEffectGrade(ItemStack item, PassiveEffectEnum effect) {
      return this.getPassiveEffectManager().passiveEffectGrade(item, effect);
   }

   public Collection<PassiveEffectEnum> getPassiveEffects(ItemStack item) {
      return this.getPassiveEffectManager().getPassiveEffects(item);
   }

   public final void applyPassiveEffect(Player player, PassiveEffectEnum effect, int grade) {
      this.getPassiveEffectManager().applyPassiveEffect(player, effect, grade);
   }

   public final boolean checkAllowedSlot(Slot slot) {
      return this.getPassiveEffectManager().checkAllowedSlot(slot);
   }

   private final PassiveEffectManager getPassiveEffectManager() {
      GameManager gameManager = this.plugin.getGameManager();
      PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
      return passiveEffectManager;
   }
}
