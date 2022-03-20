package com.praya.myitems.utility.main;

import com.praya.agarthalib.utility.BlockUtil;
import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.Material;

public class AntiBugUtil {
   public static void antiBugCustomStats() {
      Iterator var1 = BlockUtil.getDataLoc().iterator();

      while(var1.hasNext()) {
         Location loc = (Location)var1.next();
         loc.getBlock().setType(Material.AIR);
      }

   }
}
