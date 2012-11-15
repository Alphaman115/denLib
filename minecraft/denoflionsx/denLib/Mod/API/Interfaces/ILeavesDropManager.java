package denoflionsx.denLib.Mod.API.Interfaces;

import denoflionsx.denLib.Mod.API.Objects.LeavesDrop;
import java.util.ArrayList;
import net.minecraft.src.BlockLeaves;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public interface ILeavesDropManager {
    
    // nextInt is the amount divided by 1 for the drop chance.
    // Example: Red Apples - 0.5% drop rate = 1/200. 200 Would be the nextInt.
    
    public void registerDrop(ItemStack drop, int nextInt);
    
    public ArrayList<LeavesDrop> getDrops();
    
    public void doLogic(World world, int x, int y, int z, BlockLeaves block);
    
}
