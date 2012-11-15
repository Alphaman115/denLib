package denoflionsx.denLib.Mod.Managers;

import denoflionsx.denLib.Mod.API.Interfaces.ILeavesDropManager;
import denoflionsx.denLib.Mod.API.Objects.LeavesDrop;
import java.util.ArrayList;
import net.minecraft.src.BlockLeaves;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class LeavesDropManager implements ILeavesDropManager {

    public static ArrayList<LeavesDrop> drops;

    public LeavesDropManager() {
        drops = new ArrayList();
    }

    @Override
    public ArrayList<LeavesDrop> getDrops() {
        return drops;
    }

    @Override
    public void registerDrop(ItemStack drop, int nextInt) {
        drops.add(new LeavesDrop(drop, nextInt));
    }

    @Override
    public void doLogic(World world, int x, int y, int z, BlockLeaves block) {
        for (LeavesDrop drop : getDrops()) {
            if (world.rand.nextInt(drop.getNextInt()) == 0) {
                block.dropItems(world, x, y, z, drop.getDrop().copy());
            }
        }
    }
}
