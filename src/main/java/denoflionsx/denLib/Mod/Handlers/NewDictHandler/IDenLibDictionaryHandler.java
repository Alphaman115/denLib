package denoflionsx.denLib.Mod.Handlers.NewDictHandler;

import net.minecraft.item.ItemStack;

public interface IDenLibDictionaryHandler {

    // Use * as a wildcard.
    public String lookingFor();

    public void onEvent(ItemStack item);
}
