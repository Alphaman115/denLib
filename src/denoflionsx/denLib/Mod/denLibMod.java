package denoflionsx.denLib.Mod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import denoflionsx.denLib.Config.Manager.TunableManager;
import denoflionsx.denLib.Config.TestConfig.denLibTunable;
import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.IdenWorldEventHandler;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.WorldEventHandler;
import denoflionsx.denLib.Mod.Proxy.denLibProxy;
import java.io.File;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

@Mod(modid = "denLib", name = "denLib", version = "@VERSION@")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class denLibMod {

    @Mod.Instance("denLib")
    public static Object instance;
    private static final String proxyPath = "denoflionsx.denLib.Mod.Proxy.";
    @SidedProxy(clientSide = proxyPath + "denLibClientProxy", serverSide = proxyPath + "denLibCommonProxy")
    public static denLibProxy Proxy;
    public static TunableManager tuning;
    public static File configFile;

    @Mod.PreInit
    public void preLoad(FMLPreInitializationEvent event) {
        Proxy.registerForgeSubscribe(this);
        tuning = new TunableManager();
        configFile = event.getSuggestedConfigurationFile();
        Proxy.print("Localization code shamelessly lifted from powercrystals.");
        Proxy.print("Liquid Block code from powercrystalscore, originally from King_Lemming.");
    }

    @Mod.Init
    public void load(FMLInitializationEvent event) {
        Proxy.print("denLib loading...");
        tuning.registerTunableClass(denLibTunable.class);
    }

    @Mod.PostInit
    public void modsLoaded(FMLPostInitializationEvent evt) {
        Proxy.print("denLib load complete.");
        denLibCore.updater.startUpdaterThread();
    }

    @ForgeSubscribe
    public void worldLoaded(WorldEvent.Load event) {
        for (IdenWorldEventHandler handler : WorldEventHandler.getHandlers()) {
            handler.onWorldLoaded();
        }
    }

    @ForgeSubscribe
    public void worldEnded(WorldEvent.Unload event) {
        for (IdenWorldEventHandler handler : WorldEventHandler.getHandlers()) {
            handler.onWorldEnded();
        }
    }
}
