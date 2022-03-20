package api.praya.myitems.manager.game;

import api.praya.myitems.builder.element.Element;
import api.praya.myitems.builder.element.ElementBoost;
import api.praya.myitems.builder.element.ElementBoostStats;
import api.praya.myitems.builder.element.ElementPotion;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.game.GameManager;
import core.praya.agarthalib.enums.main.SlotType;
import java.util.Collection;
import java.util.HashMap;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class ElementManagerAPI extends HandlerManager {
   protected ElementManagerAPI(MyItems plugin) {
      super(plugin);
   }

   public final Collection<String> getElements() {
      return this.getElementManager().getElements();
   }

   public final Collection<Element> getElementBuilds() {
      return this.getElementManager().getElementBuilds();
   }

   public final Element getElementBuild(String element) {
      return this.getElementManager().getElementBuild(element);
   }

   public final String getTextElement(String element, double value) {
      return this.getElementManager().getTextElement(element, value);
   }

   public final boolean isExists(String element) {
      return this.getElementManager().isExists(element);
   }

   public final HashMap<String, Double> getMapElement(LivingEntity livingEntity) {
      return this.getElementManager().getMapElement(livingEntity, SlotType.UNIVERSAL, false);
   }

   public final HashMap<String, Double> getElementCalculation(HashMap<String, Double> elementAttacker, HashMap<String, Double> elementVictims) {
      return this.getElementManager().getElementCalculation(elementAttacker, elementVictims);
   }

   public final void applyElementPotion(LivingEntity attacker, LivingEntity victims, HashMap<String, Double> mapElement) {
      this.getElementManager().applyElementPotion(attacker, victims, mapElement);
   }

   public final ElementBoostStats getElementBoostStats(HashMap<String, Double> mapElement) {
      return this.getElementManager().getElementBoostStats(mapElement);
   }

   public final double getElementValue(ItemStack item, String element) {
      return this.getElementManager().getElementValue(item, element);
   }

   public final double getScaleResistance(String baseElement, String targetElement) {
      return this.getElementManager().getScaleResistance(baseElement, targetElement);
   }

   public final double getScaleResistance(Element elementBuild, String element) {
      return this.getElementManager().getScaleResistance(elementBuild, element);
   }

   public final ElementBoost getElementBoostBuild(String element) {
      return this.getElementManager().getElementBoostBuild(element);
   }

   public final ElementPotion getElementPotionBuild(String element) {
      return this.getElementManager().getElementPotionBuild(element);
   }

   private final ElementManager getElementManager() {
      GameManager gameManager = this.plugin.getGameManager();
      ElementManager elementManager = gameManager.getElementManager();
      return elementManager;
   }
}
