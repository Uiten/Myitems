package com.praya.myitems.utility.main;

import api.praya.myitems.builder.passive.PassiveEffectTypeEnum;
import com.praya.agarthalib.utility.MetadataUtil;
import org.bukkit.entity.Entity;

public class CustomEffectUtil {
   private static final String METADATA_CUSTOM_EFFECT = "Custom_Effect_Time";
   private static final String METADATA_SPEED_BASE = "Speed_Base";

   public static final void setCustomEffect(Entity entity, long duration, PassiveEffectTypeEnum customEffect) {
      MetadataUtil.setCooldown(entity, getMetadataCustomEffect(customEffect), duration);
   }

   public static final double getLeftTimeEffect(Entity entity, PassiveEffectTypeEnum customEffect) {
      return MetadataUtil.getTimeCooldown(entity, getMetadataCustomEffect(customEffect));
   }

   public static final boolean isRunCustomEffect(Entity entity, PassiveEffectTypeEnum customEffect) {
      return !MetadataUtil.isExpired(entity, getMetadataCustomEffect(customEffect));
   }

   public static final void setSpeedBase(Entity entity, float speed) {
      entity.setMetadata(getMetadataSpeedBase(), MetadataUtil.createMetadata(speed));
   }

   public static final float getSpeedBase(Entity entity) {
      return MetadataUtil.getMetadata(entity, getMetadataSpeedBase()).asFloat();
   }

   public static final boolean hasSpeedBase(Entity entity) {
      return MetadataUtil.hasMetadata(entity, getMetadataSpeedBase());
   }

   public static final void removeSpeedBase(Entity entity) {
      MetadataUtil.removeMetadata(entity, getMetadataSpeedBase());
   }

   private static final String getMetadataCustomEffect(PassiveEffectTypeEnum customEffect) {
      return "Custom_Effect_Time:" + customEffect.getName();
   }

   private static final String getMetadataSpeedBase() {
      return "Speed_Base";
   }
}
