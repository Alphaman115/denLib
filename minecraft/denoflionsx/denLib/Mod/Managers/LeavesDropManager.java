package denoflionsx.denLib.Mod.Managers;

import denoflionsx.denLib.CoreMod.denLibCoreMod;
import denoflionsx.denLib.Mod.API.Interfaces.ILeavesDropManager;
import denoflionsx.denLib.Mod.API.Objects.LeavesDrop;
import java.lang.reflect.Method;
import java.util.ArrayList;
import net.minecraft.block.BlockLeaves;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LeavesDropManager implements ILeavesDropManager {

    public static ArrayList<LeavesDrop> drops;
    private static Method dropItems = null;

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
                // Why am I doing this with reflection?
                // Because when I compile mods that depend on denLib they don't
                // have the modifed BlockLeaves in the class path.
                // So I have to wrap this in such a way that MCP won't
                // punch me when I try to compile.
                if (dropItems == null) {
                    try {
                        Class c = Class.forName("net.minecraft.block." + denLibCoreMod.LeavesMapping);
                        dropItems = c.getDeclaredMethod("dropItems", new Class[]{World.class, int.class, int.class, int.class, ItemStack.class});
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        dropItems.invoke(block,world,x,y,z,drop.getDrop().copy());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
