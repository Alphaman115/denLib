package denoflionsx.denLib.Mod.API.Interfaces;

import denoflionsx.denLib.Mod.API.Objects.LeavesDrop;
import java.util.ArrayList;
import net.minecraft.src.ItemStack;

public interface ILeavesDropManager {
    
    public void registerDrop(ItemStack drop, int nextInt);
    
    public ArrayList<LeavesDrop> getDrops();
    
}
