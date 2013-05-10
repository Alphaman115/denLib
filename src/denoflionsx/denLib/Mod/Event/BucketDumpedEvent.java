package denoflionsx.denLib.Mod.Event;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BucketDumpedEvent {

    public ItemStack current;
    public ItemStack result = new ItemStack(Item.bucketEmpty);
    protected int liquidID;

    public BucketDumpedEvent(ItemStack current, int liquidID) {
        this.current = current;
        this.liquidID = liquidID;
    }
}
