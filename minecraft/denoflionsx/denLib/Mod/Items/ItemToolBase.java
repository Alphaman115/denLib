package denoflionsx.denLib.Mod.Items;

import net.minecraft.src.Item;

public class ItemToolBase extends Item{

    public ItemToolBase(int id, int durability) {
        super(id);
        this.setMaxDamage(durability);
        this.setFull3D();
        this.setMaxStackSize(1);
    }
}
