package api.praya.myitems.builder.item;

import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.plugin.PlaceholderManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemSet {
   private final String id;
   private final String name;
   private final HashMap<Integer, ItemSetBonus> mapBonus;
   private final HashMap<String, ItemSetComponent> mapComponent;

   public ItemSet(String id, String name, HashMap<Integer, ItemSetBonus> mapBonus, HashMap<String, ItemSetComponent> mapComponent) {
      this.id = id;
      this.name = name;
      this.mapBonus = mapBonus;
      this.mapComponent = mapComponent;
   }

   public final String getID() {
      return this.id;
   }

   public final String getName() {
      return this.name;
   }

   public final Collection<Integer> getBonusAmountIDs() {
      return this.mapBonus.keySet();
   }

   public final Collection<String> getComponentIDs() {
      return this.mapComponent.keySet();
   }

   public final Collection<ItemSetBonus> getAllItemSetBonus() {
      return this.mapBonus.values();
   }

   public final Collection<ItemSetComponent> getAllItemSetComponent() {
      return this.mapComponent.values();
   }

   public final ItemSetBonus getItemSetBonus(int amountID) {
      return (ItemSetBonus)this.mapBonus.get(amountID);
   }

   public final boolean isBonusExists(int amountID) {
      return this.getItemSetBonus(amountID) != null;
   }

   public final ItemSetComponent getItemSetComponent(String componentID) {
      if (componentID != null) {
         Iterator var3 = this.getComponentIDs().iterator();

         while(var3.hasNext()) {
            String key = (String)var3.next();
            if (key.equalsIgnoreCase(componentID)) {
               return (ItemSetComponent)this.mapComponent.get(key);
            }
         }
      }

      return null;
   }

   public final boolean isComponentExists(String componentID) {
      return this.getItemSetComponent(componentID) != null;
   }

   public final int getTotalComponent() {
      return this.mapComponent.size();
   }

   public final ItemStack generateItem(String componentID) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      PluginManager pluginManager = plugin.getPluginManager();
      PlaceholderManager placeholderManager = pluginManager.getPlaceholderManager();
      MainConfig mainConfig = MainConfig.getInstance();
      ItemSetComponent itemSetComponent = this.getItemSetComponent(componentID);
      if (itemSetComponent != null) {
         ItemSetComponentItem componentItem = itemSetComponent.getComponentItem();
         Material material = componentItem.getMaterial();
         short data = (short)componentItem.getData();
         MaterialEnum materialEnum = MaterialEnum.getMaterialEnum(material, data);
         if (materialEnum != null) {
            String divider = "\n";
            String keyLine = MainConfig.KEY_SET_LINE;
            String keySetComponentSelf = MainConfig.KEY_SET_COMPONENT_SELF;
            String keySetComponentOther = MainConfig.KEY_SET_COMPONENT_OTHER;
            String loreBonusInactive = mainConfig.getSetLoreBonusInactive();
            String loreComponentInactive = mainConfig.getSetLoreComponentInactive();
            boolean shiny = componentItem.isShiny();
            boolean unbreakable = componentItem.isUnbreakable();
            List<String> flags = componentItem.getFlags();
            List<String> lores = new ArrayList(componentItem.getLores());
            List<String> loresBonus = new ArrayList();
            List<String> loresComponent = new ArrayList();
            List<Integer> bonusAmountIDs = new ArrayList(this.getBonusAmountIDs());
            Collection<Enchantment> enchantments = componentItem.getEnchantments();
            HashMap<String, String> mapPlaceholder = new HashMap();
            String display = componentItem.getDisplayName();
            List<String> loresSet = mainConfig.getSetFormat();
            Collections.sort(bonusAmountIDs);
            Iterator var29 = this.getAllItemSetComponent().iterator();

            String lore;
            String flag;
            while(var29.hasNext()) {
               ItemSetComponent partComponent = (ItemSetComponent)var29.next();
               String partComponentID = partComponent.getID();
               lore = partComponent.getKeyLore();
               flag = loreComponentInactive + mainConfig.getSetFormatComponent();
               String replacementKeyLore;
               if (partComponent.equals(itemSetComponent)) {
                  replacementKeyLore = keySetComponentSelf + loreComponentInactive + lore + keySetComponentSelf + loreComponentInactive;
               } else {
                  replacementKeyLore = keySetComponentOther + loreComponentInactive + lore + keySetComponentOther + loreComponentInactive;
               }

               mapPlaceholder.clear();
               mapPlaceholder.put("item_set_component_id", partComponentID);
               mapPlaceholder.put("item_set_component_keylore", replacementKeyLore);
               flag = TextUtil.placeholder(mapPlaceholder, flag, "<", ">");
               loresComponent.add(flag);
            }

            var29 = bonusAmountIDs.iterator();

            while(var29.hasNext()) {
               int bonusAmountID = (Integer)var29.next();
               ItemSetBonus partBonus = this.getItemSetBonus(bonusAmountID);
               Iterator var42 = partBonus.getDescription().iterator();

               while(var42.hasNext()) {
                  lore = (String)var42.next();
                  flag = loreBonusInactive + mainConfig.getSetFormatBonus();
                  mapPlaceholder.clear();
                  mapPlaceholder.put("item_set_bonus_amount", String.valueOf(bonusAmountID));
                  mapPlaceholder.put("item_set_bonus_description", String.valueOf(lore));
                  flag = TextUtil.placeholder(mapPlaceholder, flag, "<", ">");
                  loresBonus.add(flag);
               }
            }

            String lineBonus = TextUtil.convertListToString(loresBonus, "\n");
            String lineComponent = TextUtil.convertListToString(loresComponent, "\n");
            mapPlaceholder.clear();
            mapPlaceholder.put("item_set_name", this.getName());
            mapPlaceholder.put("item_set_total", String.valueOf("0"));
            mapPlaceholder.put("item_set_max", String.valueOf(this.getTotalComponent()));
            mapPlaceholder.put("list_item_set_component", lineComponent);
            mapPlaceholder.put("list_item_set_bonus", lineBonus);
            loresSet = TextUtil.placeholder(mapPlaceholder, loresSet, "<", ">");
            loresSet = TextUtil.expandList(loresSet, "\n");
            ListIterator iteratorLoresSet = loresSet.listIterator();

            while(iteratorLoresSet.hasNext()) {
               lore = keyLine + (String)iteratorLoresSet.next();
               iteratorLoresSet.set(lore);
            }

            lores.addAll(loresSet);
            List<String> loresFinal = placeholderManager.placeholder((Player)null, (List)lores);
            ItemStack item = EquipmentUtil.createItem(materialEnum, display, 1, loresFinal);
            Iterator var34 = flags.iterator();

            while(var34.hasNext()) {
               flag = (String)var34.next();
               EquipmentUtil.addFlag(item, new String[]{flag});
            }

            var34 = enchantments.iterator();

            while(var34.hasNext()) {
               Enchantment enchantment = (Enchantment)var34.next();
               int grade = componentItem.getEnchantmentGrade(enchantment);
               EquipmentUtil.addEnchantment(item, enchantment, grade);
            }

            if (shiny) {
               EquipmentUtil.shiny(item);
            }

            if (unbreakable) {
               Bridge.getBridgeTagsNBT().setUnbreakable(item, true);
            }

            return item;
         }
      }

      return null;
   }
}
