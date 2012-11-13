package denoflionsx.denLib.Mod.API.Objects;

import net.minecraft.src.ItemStack;

public class LeavesDrop {
    
    private ItemStack drop;
    private int nextInt;

    public LeavesDrop(ItemStack drop, int nextInt) {
        this.drop = drop;
        this.nextInt = nextInt;
    }

    public ItemStack getDrop() {
        return drop;
    }

    public int getNextInt() {
        return nextInt;
    }
}
