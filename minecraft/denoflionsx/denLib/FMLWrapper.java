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
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;

public class FMLWrapper {

    public static enum MODE {

        FML();

        public void addRecipe(ItemStack output, Object[] grid) {
            GameRegistry.addRecipe(output, grid);
        }

        public void addShapelessRecipe(ItemStack output, Object[] grid) {
            GameRegistry.addShapelessRecipe(output, grid);
        }

        public void registerBlock(Block b) {
            GameRegistry.registerBlock(b);
        }

        public void registerTileEntity(Class t, String name) {
            GameRegistry.registerTileEntity(t, name);
        }

        public void registerFuelHandler(IFuelHandler f) {
            GameRegistry.registerFuelHandler(f);
        }

        public void addSmelt(ItemStack input, ItemStack output) {
            GameRegistry.addSmelting(input.itemID, output.copy(), 1);
        }

        public void addName(String name) {
            LanguageRegistry.instance().addStringLocalization("item." + denLib.toLowerCaseNoSpaces(name) + ".name", name);
        }

        public void addName(String i, String d) {
            LanguageRegistry.instance().addStringLocalization(i, d);
        }
        
        public void addNameItemStack(String i, ItemStack item){
            LanguageRegistry.addName(item, i);
        }

        public void addNameItem(Item item, String name) {
            LanguageRegistry.addName(item, name);
        }

        public void addAchievementPage(AchievementPage page) {
            AchievementPage.registerAchievementPage(page);
        }

        public void registerEvent(Object event) {
            MinecraftForge.EVENT_BUS.register(event);
        }

        public void registerRender(ISimpleBlockRenderingHandler render) {
            RenderingRegistry.registerBlockHandler(render);
        }

        public int getRenderingID() {
            return RenderingRegistry.getNextAvailableRenderId();
        }

        public void registerGUIHandler(Object instance, IGuiHandler handler) {
            NetworkRegistry.instance().registerGuiHandler(this, handler);
        }

        public void registerBlockName(Block block, String name) {
            LanguageRegistry.addName(block, name);
        }

        public void registerBlockWithItem(Block block, Class c) {
            GameRegistry.registerBlock(block, c);
        }
    }
}
