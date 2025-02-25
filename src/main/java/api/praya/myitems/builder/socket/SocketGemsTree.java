package api.praya.myitems.builder.socket;

import core.praya.agarthalib.enums.main.SlotType;
import java.util.HashMap;

public class SocketGemsTree {
   private final String gems;
   private final int maxGrade;
   private final SlotType typeItem;
   private final HashMap<Integer, SocketGems> mapSocket;

   public SocketGemsTree(String gems, int maxGrade, SlotType typeItem, HashMap<Integer, SocketGems> mapSocket) {
      this.gems = gems;
      this.maxGrade = maxGrade;
      this.typeItem = typeItem;
      this.mapSocket = mapSocket;
   }

   public final String getGems() {
      return this.gems;
   }

   public final int getMaxGrade() {
      return this.maxGrade;
   }

   public final SlotType getTypeItem() {
      return this.typeItem;
   }

   public final HashMap<Integer, SocketGems> getMapSocket() {
      return this.mapSocket;
   }

   public final boolean isGemsAvailable(int grade) {
      return this.mapSocket.containsKey(grade);
   }

   public final SocketGems getSocketBuild(int grade) {
      return this.isGemsAvailable(grade) ? (SocketGems)this.mapSocket.get(grade) : null;
   }
}
