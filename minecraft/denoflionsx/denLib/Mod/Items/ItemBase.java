package denoflionsx.denLib.Mod.Items;

import denoflionsx.denLib.FMLWrapper;
import denoflionsx.denLib.denLib;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item {

    public HashMap<Integer, String> names;
    public HashMap<Integer, Integer> textures;
    public ArrayList<ItemStack> stacks;

    public ItemBase(int par1) {
        super(par1);
        names = new HashMap();
        textures = new HashMap();
        stacks = new ArrayList();
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (ItemStack i : stacks) {
            par3List.add(i);
        }
    }

    public void add(String name, int meta, int texture) {
        if (names.get(meta) == null) {
            names.put(meta, "item." + denLib.toLowerCaseNoSpaces(name));
            textures.put(meta, texture);
            FMLWrapper.MODE.FML.addNameItemStack(name, new ItemStack(this, 1, meta));
            stacks.add(new ItemStack(this, 1, meta));
        }
    }

    @Override
    public int getIconFromDamage(int par1) {
        if (textures.get(par1) != null) {
            return textures.get(par1);
        } else {
            return 0;
        }
    }

    @Override
    public String getItemNameIS(ItemStack par1ItemStack) {
        try {
            if (names.get(par1ItemStack.getItemDamage()) != null) {
                return names.get(par1ItemStack.getItemDamage());
            } else {
                return "Unknown Item";
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            return "Allan please add meta aware tooltip";
        }
    }
}
