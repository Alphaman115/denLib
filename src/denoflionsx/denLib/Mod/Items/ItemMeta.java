package denoflionsx.denLib.Mod.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.Icon;

public class ItemMeta extends Item {

    private ArrayList<ItemStack> stacks = new ArrayList();
    public HashMap<Integer, Icon> icons = new HashMap();
    public String[] textures;

    public ItemMeta(String[] textures, int par1) {
        super(par1);
        this.textures = textures;
    }

    public ItemStack createItemEntry(int meta) {
        ItemStack i = new ItemStack(this, 1, meta);
        stacks.add(i);
        return i;
    }

    public ItemStack createItemEntry(int meta, NBTTagCompound tag) {
        ItemStack i = new ItemStack(this, 1, meta);
        i.stackTagCompound = tag;
        stacks.add(i);
        return i;
    }

    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (ItemStack i : stacks) {
            par3List.add(i);
        }
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (par1ItemStack.stackTagCompound != null) {
            if (par1ItemStack.stackTagCompound.hasKey("info")) {
                NBTTagCompound info = par1ItemStack.stackTagCompound.getCompoundTag("info");
                for (Object o : info.getTags()) {
                    NBTTagString t = (NBTTagString) o;
                    par3List.add(t.data);
                }
            }
        }
    }

    @Override
    public void updateIcons(IconRegister par1IconRegister) {
        for (int i = 0; i < stacks.size(); i++) {
            try {
                icons.put(i, par1IconRegister.registerIcon(textures[i]));
            } catch (Exception ex) {
                // shh
                icons.put(i, par1IconRegister.registerIcon(textures[0]));
            }
        }
    }

    @Override
    public Icon getIconFromDamage(int par1) {
        return icons.get(par1);
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return this.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }

    public String getTextureFileForItemStack(ItemStack par1ItemStack) {
        try {
            return "textures/items/" + textures[par1ItemStack.getItemDamage()] + ".png";
        } catch (Exception ex) {
            // noise
        }
        return "textures/items/" + textures[0] + ".png";
    }

    public static String getTextureFileForItemStackShortcut(ItemStack par1ItemStack) {
        ItemMeta meta = (ItemMeta) par1ItemStack.getItem();
        return meta.getTextureFileForItemStack(par1ItemStack);
    }
}
