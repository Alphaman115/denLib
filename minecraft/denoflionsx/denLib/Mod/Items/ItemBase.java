package denoflionsx.denLib.Mod.Items;

import denoflionsx.denLib.FMLWrapper;
import denoflionsx.denLib.denLib;
import java.util.HashMap;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemBase extends Item{
    
    public HashMap<Integer, String> names;
    public HashMap<Integer, Integer> textures;

    public ItemBase(int par1) {
        super(par1);
        names = new HashMap();
        textures = new HashMap();
    }
    
    public void add(String name, int meta, int texture){
        names.put(meta,"item." + denLib.toLowerCaseNoSpaces(name));
        textures.put(meta,texture);
        FMLWrapper.MODE.FML.addNameItemStack(name, new ItemStack(this,1,meta));
    }

    @Override
    public int getIconFromDamage(int par1) {
        if (textures.get(par1) != null){
            return textures.get(par1);
        }else{
            return 0;
        }
    }

    @Override
    public String getItemNameIS(ItemStack par1ItemStack) {
        if (names.get(par1ItemStack.getItemDamage()) != null){
            return names.get(par1ItemStack.getItemDamage());
        }else{
            return "Unknown Item";
        }
    }

}
