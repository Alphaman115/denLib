package denoflionsx.denLib.Mod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import denoflionsx.denLib.Mod.API.denLibManagers;
import denoflionsx.denLib.Mod.Core.denLibCore;
import denoflionsx.denLib.Mod.Proxy.Proxy;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

@Mod(modid = "denLib", name = "denLib", version = "2.0")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class denLibMod {
    
    @Mod.Instance
    public static Object instance;
    
    @SidedProxy(clientSide = "denoflionsx.denLib.Mod.Proxy.ProxyClient", serverSide = "denoflionsx.denLib.Mod.Proxy.ProxyServer")
    public static Proxy proxy;
    
    public static denLibCore core;

    public denLibMod() {
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @Mod.PreInit
    public void preload(FMLPreInitializationEvent evt) {
        core = new denLibCore();
        core.registerManagers();
    }
    
    @Mod.Init
    public void load(FMLInitializationEvent event) {
        denLibManagers.LeavesDropManager.registerDrop(new ItemStack(Item.beefCooked), 10);
    }
    
    @Mod.PostInit
    public void modsLoaded(FMLPostInitializationEvent evt) {
        
    }
    
    @ForgeSubscribe
    public void onWorldLoaded(WorldEvent.Load event) {
        
    }

    @ForgeSubscribe
    public void onWorldEnded(WorldEvent.Unload event) {
        
    }
    
}
