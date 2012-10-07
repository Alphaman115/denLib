package denoflionsx.denLib;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;

public class FMLWrapper {

    public static enum MODE {

        MODLOADER(),
        FML();
        private boolean isFML = false;

        private MODE() {
            if (this.toString().equals("FML")) {
                isFML = true;
            }
        }

        public void addRecipe(ItemStack output, Object[] grid) {
            if (!isFML) {
                ModLoader.addRecipe(output, grid);
            } else {
                GameRegistry.addRecipe(output, grid);
            }
        }

        public void addShapelessRecipe(ItemStack output, Object[] grid) {
            if (!isFML) {
                ModLoader.addShapelessRecipe(output, grid);
            } else {
                GameRegistry.addShapelessRecipe(output, grid);
            }
        }

        public void registerBlock(Block b) {
            if (!isFML) {
                ModLoader.registerBlock(b);
            } else {
                GameRegistry.registerBlock(b);
            }
        }

        public void registerTileEntity(Class t, String name) {
            if (!isFML) {
                ModLoader.registerTileEntity(t, name);
            } else {
                GameRegistry.registerTileEntity(t, name);
            }
        }

        public void registerFuelHandler(IFuelHandler f) {
            if (!isFML) {
                denLib.print("Function registerFuelHandler is not implemented for MODE MODLOADER");
            } else {
                GameRegistry.registerFuelHandler(f);
            }
        }

        public void addSmelt(ItemStack input, ItemStack output) {
            if (!isFML) {
                ModLoader.addSmelting(input.itemID, output.copy());
            } else {
                GameRegistry.addSmelting(input.itemID, output.copy(), 1);
            }
        }

        public void addName(String name) {
            if (!isFML) {
                ModLoader.addLocalization("item." + denLib.toLowerCaseNoSpaces(name) + ".name", name);
            } else {
                LanguageRegistry.instance().addStringLocalization("item." + denLib.toLowerCaseNoSpaces(name) + ".name", name);
            }
        }

        public void addName(String i, String d) {
            if (!isFML) {
                ModLoader.addLocalization(i, d);
            } else {
                LanguageRegistry.instance().addStringLocalization(i, d);
            }
        }

        public void addNameItem(Item item, String name) {
            if (!isFML) {
                ModLoader.addName(item, name);
            } else {
                LanguageRegistry.addName(item, name);
            }
        }

        public void addAchievementPage(AchievementPage page) {
            if (!isFML) {
                denLib.print("addAchievementPage not implemented for mode ModLoader");
            } else {
                AchievementPage.registerAchievementPage(page);
            }
        }

        public void registerEvent(Object event) {
            if (!isFML) {
                denLib.print("registerEvent not implemented for mode ModLoader.");
            } else {
                MinecraftForge.EVENT_BUS.register(event);
            }
        }

        public void registerRender(ISimpleBlockRenderingHandler render) {
            if (!isFML) {
                denLib.print("registerRender not implemented for mode ModLoader!");
            } else {
                RenderingRegistry.registerBlockHandler(render);
            }
        }

        public int getRenderingID() {
            if (isFML) {
                return RenderingRegistry.getNextAvailableRenderId();
            }
            return 0;
        }

        public void registerGUIHandler(Object instance, IGuiHandler handler) {
            if (isFML) {
                NetworkRegistry.instance().registerGuiHandler(this, handler);
            }
        }

        public void registerBlockName(Block block, String name) {
            if (!isFML){
                ModLoader.addName(block, name);
            }else{
                LanguageRegistry.addName(block, name);
            }
        }
    }
}
