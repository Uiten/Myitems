package api.praya.myitems.builder.item;

import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsModifier;
import api.praya.myitems.builder.lorestats.LoreStatsUniversal;
import api.praya.myitems.builder.lorestats.LoreStatsWeapon;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MapUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemTierManager;
import com.praya.myitems.manager.game.ItemTypeManager;
import com.praya.myitems.manager.plugin.PlaceholderManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.enums.main.TagsAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemGenerator {
   private final String id;
   private final String displayName;
   private final boolean unbreakable;
   private final List<String> lores;
   private final List<String> flags;
   private final HashMap<ItemType, ItemGeneratorType> mapType;
   private final HashMap<ItemTier, ItemGeneratorTier> mapTier;

   public ItemGenerator(String id, String displayName, boolean unbreakable, List<String> lores, List<String> flags, HashMap<ItemType, ItemGeneratorType> mapType, HashMap<ItemTier, ItemGeneratorTier> mapTier) {
      this.id = id;
      this.displayName = displayName;
      this.unbreakable = unbreakable;
      this.lores = lores;
      this.flags = flags;
      this.mapType = mapType;
      this.mapTier = mapTier;
   }

   /** @deprecated */
   @Deprecated
   public ItemGenerator(String id, String displayName, List<String> lores, HashMap<ItemType, ItemGeneratorType> mapType, HashMap<ItemTier, ItemGeneratorTier> mapTier) {
      this.id = id;
      this.displayName = displayName;
      this.unbreakable = false;
      this.lores = lores;
      this.flags = new ArrayList();
      this.mapType = mapType;
      this.mapTier = mapTier;
   }

   public final String getId() {
      return this.id;
   }

   public final String getDisplayName() {
      return this.displayName;
   }

   public final boolean isUnbreakable() {
      return this.unbreakable;
   }

   public final List<String> getLores() {
      return this.lores;
   }

   public final List<String> getFlags() {
      return this.flags;
   }

   public final HashMap<ItemType, ItemGeneratorType> getMapType() {
      return this.mapType;
   }

   public final HashMap<ItemTier, ItemGeneratorTier> getMapTier() {
      return this.mapTier;
   }

   public final HashMap<String, Integer> getMapPossibilityType() {
      return this.getMapPossibilityType((Slot)null);
   }

   public final HashMap<String, Integer> getMapPossibilityType(Slot slot) {
      HashMap<String, Integer> mapPossibilityType = new HashMap();
      Iterator var4 = this.getMapType().keySet().iterator();

      while(var4.hasNext()) {
         ItemType key = (ItemType)var4.next();
         String id = key.getId();
         Slot slotDefault = key.getDefaultSlot();
         int possibility = ((ItemGeneratorType)this.getMapType().get(key)).getPossibility();
         if (slot == null) {
            mapPossibilityType.put(id, possibility);
         } else if (slot.equals(slotDefault)) {
            mapPossibilityType.put(id, possibility);
         }
      }

      return mapPossibilityType;
   }

   public final HashMap<String, Integer> getMapPossibilityTier() {
      HashMap<String, Integer> mapPossibilityTier = new HashMap();
      Iterator var3 = this.getMapTier().keySet().iterator();

      while(var3.hasNext()) {
         ItemTier key = (ItemTier)var3.next();
         String id = key.getId();
         int possibility = ((ItemGeneratorTier)this.getMapTier().get(key)).getPossibility();
         mapPossibilityTier.put(id, possibility);
      }

      return mapPossibilityTier;
   }

   public final ItemStack generateItem() {
      return this.generateItem((Slot)null);
   }

   public final ItemStack generateItem(Slot slot) {
      MyItems plugin = (MyItems)JavaPlugin.getPlugin(MyItems.class);
      PluginManager pluginManager = plugin.getPluginManager();
      GameManager gameManager = plugin.getGameManager();
      PlaceholderManager placeholderManager = pluginManager.getPlaceholderManager();
      ItemTypeManager itemTypeManager = gameManager.getItemTypeManager();
      ItemTierManager itemTierManager = gameManager.getItemTierManager();
      HashMap<String, Integer> mapPossibilityType = this.getMapPossibilityType(slot);
      HashMap<String, Integer> mapPossibilityTier = this.getMapPossibilityTier();
      if (!mapPossibilityType.isEmpty() && !mapPossibilityTier.isEmpty()) {
         String idType = MapUtil.getRandomIdByInteger(mapPossibilityType);
         String idTier = MapUtil.getRandomIdByInteger(mapPossibilityTier);
         ItemType itemType = itemTypeManager.getItemType(idType);
         ItemTier itemTier = itemTierManager.getItemTier(idTier);
         if (itemType != null && itemType != null) {
            Material material = itemType.getMaterial();
            short data = itemType.getData();
            MaterialEnum materialEnum = MaterialEnum.getMaterialEnum(material, data);
            if (materialEnum != null) {
               boolean shiny = itemType.isShiny();
               ItemGeneratorType itemTypeProperties = (ItemGeneratorType)this.getMapType().get(itemType);
               ItemGeneratorTier itemTierProperties = (ItemGeneratorTier)this.getMapTier().get(itemTier);
               LoreStatsModifier typeModifier = itemType.getStatsModifier();
               LoreStatsModifier tierModifier = itemTier.getStatsModifier();
               List<String> description = itemTypeProperties.getDescription();
               List<String> names = itemTypeProperties.getNames();
               List<String> additionalLores = itemTierProperties.getAdditionalLores();
               Collection<Enchantment> enchantments = itemType.getEnchantments();
               Collection<Slot> allSlotNBT = itemType.getAllSlotNBT();
               int random = (int)(Math.random() * (double)names.size());
               String divider = "\n";
               String lineDescription = TextUtil.convertListToString(description, "\n");
               String lineLore = TextUtil.convertListToString(this.lores, "\n");
               String lineAdditionalLores = TextUtil.convertListToString(additionalLores, "\n");
               String name = !names.isEmpty() ? (String)names.get(MathUtil.limitInteger(random, 0, names.size() - 1)) : null;
               HashMap<String, String> map = new HashMap();
               HashMap<LoreStatsEnum, Double> mapStatsModifier = new HashMap();
               LoreStatsEnum[] var38;
               int var37 = (var38 = LoreStatsEnum.values()).length;

               double pvpDamage;
               double pveDamage;
               double criticalChance;
               for(int var36 = 0; var36 < var37; ++var36) {
                  LoreStatsEnum loreStats = var38[var36];
                  pvpDamage = typeModifier.getModifier(loreStats);
                  pveDamage = tierModifier.getModifier(loreStats);
                  criticalChance = pvpDamage * pveDamage;
                  mapStatsModifier.put(loreStats, criticalChance);
               }

               double damage = (Double)mapStatsModifier.get(LoreStatsEnum.DAMAGE);
               double penetration = (Double)mapStatsModifier.get(LoreStatsEnum.PENETRATION);
               pvpDamage = (Double)mapStatsModifier.get(LoreStatsEnum.PVP_DAMAGE);
               pveDamage = (Double)mapStatsModifier.get(LoreStatsEnum.PVE_DAMAGE);
               criticalChance = (Double)mapStatsModifier.get(LoreStatsEnum.CRITICAL_CHANCE);
               double criticalDamage = (Double)mapStatsModifier.get(LoreStatsEnum.CRITICAL_DAMAGE);
               double hitRate = (Double)mapStatsModifier.get(LoreStatsEnum.HIT_RATE);
               double defense = (Double)mapStatsModifier.get(LoreStatsEnum.DEFENSE);
               double pvpDefense = (Double)mapStatsModifier.get(LoreStatsEnum.PVP_DEFENSE);
               double pveDefense = (Double)mapStatsModifier.get(LoreStatsEnum.PVE_DEFENSE);
               double health = (Double)mapStatsModifier.get(LoreStatsEnum.HEALTH);
               double healthRegen = (Double)mapStatsModifier.get(LoreStatsEnum.HEALTH_REGEN);
               double staminaMax = (Double)mapStatsModifier.get(LoreStatsEnum.STAMINA_MAX);
               double staminaRegen = (Double)mapStatsModifier.get(LoreStatsEnum.STAMINA_REGEN);
               double attackAoERadius = (Double)mapStatsModifier.get(LoreStatsEnum.ATTACK_AOE_RADIUS);
               double attackAoEDamage = (Double)mapStatsModifier.get(LoreStatsEnum.ATTACK_AOE_DAMAGE);
               double blockAmount = (Double)mapStatsModifier.get(LoreStatsEnum.BLOCK_AMOUNT);
               double blockRate = (Double)mapStatsModifier.get(LoreStatsEnum.BLOCK_RATE);
               double dodgeRate = (Double)mapStatsModifier.get(LoreStatsEnum.DODGE_RATE);
               double durability = (Double)mapStatsModifier.get(LoreStatsEnum.DURABILITY);
               double level = (Double)mapStatsModifier.get(LoreStatsEnum.LEVEL);
               LoreStatsWeapon weaponModifier = new LoreStatsWeapon(damage, penetration, pvpDamage, pveDamage, attackAoERadius, attackAoEDamage, criticalChance, criticalDamage, hitRate);
               LoreStatsArmor armorModifier = new LoreStatsArmor(defense, pvpDefense, pveDefense, health, healthRegen, staminaMax, staminaRegen, blockAmount, blockRate, dodgeRate);
               LoreStatsUniversal universalModifier = new LoreStatsUniversal(durability, level);
               LoreStatsModifier statsModifier = new LoreStatsModifier(weaponModifier, armorModifier, universalModifier);
               String loreBuilder = TextUtil.placeholder(lineLore, "description", lineDescription);
               String display = this.displayName;
               map.put("Name", name);
               map.put("Type_ID", idType);
               map.put("Tier_ID", idTier);
               map.put("Tier_Name", itemTier.getName());
               map.put("Tier_Prefix", itemTier.getPrefix());
               display = TextUtil.placeholder(map, display);
               display = TextUtil.colorful(display);
               loreBuilder = !lineAdditionalLores.isEmpty() ? loreBuilder + "\n" + lineAdditionalLores : loreBuilder;
               loreBuilder = TextUtil.placeholder(map, loreBuilder);
               loreBuilder = placeholderManager.placeholder((Player)null, (String)loreBuilder, statsModifier);
               loreBuilder = TextUtil.colorful(loreBuilder);
               String[] finalLores = loreBuilder.split("\n");
               ItemStack item = EquipmentUtil.createItem(materialEnum, display, 1, finalLores);
               Iterator var86 = this.flags.iterator();

               while(var86.hasNext()) {
                  String flag = (String)var86.next();
                  EquipmentUtil.addFlag(item, new String[]{flag});
               }

               var86 = enchantments.iterator();

               while(var86.hasNext()) {
                  Enchantment enchantment = (Enchantment)var86.next();
                  int grade = itemType.getEnchantmentGrade(enchantment);
                  EquipmentUtil.addEnchantment(item, enchantment, grade);
               }

               var86 = allSlotNBT.iterator();

               while(var86.hasNext()) {
                  Slot slotNBT = (Slot)var86.next();
                  ItemTypeNBT itemTypeNBT = itemType.getSlotNBT(slotNBT);
                  Iterator var89 = itemTypeNBT.getAllTagsAttribute().iterator();

                  while(var89.hasNext()) {
                     TagsAttribute tagsAttribute = (TagsAttribute)var89.next();
                     double tagsValue = itemTypeNBT.getTagsAttributeValue(tagsAttribute);
                     Bridge.getBridgeTagsNBT().addNBT(item, tagsAttribute, tagsValue, slotNBT);
                  }
               }

               if (shiny) {
                  EquipmentUtil.shiny(item);
               }

               if (this.unbreakable) {
                  Bridge.getBridgeTagsNBT().setUnbreakable(item, true);
               }

               return item;
            }
         }
      }

      return null;
   }
}
