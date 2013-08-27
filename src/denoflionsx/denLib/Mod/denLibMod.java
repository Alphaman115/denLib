package denoflionsx.denLib.Mod;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import denoflionsx.denLib.Config.Manager.TunableManager;
import denoflionsx.denLib.CoreMod.Config.denLibTuning;
import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Mod.Changelog.ChangeLogWorld;
import denoflionsx.denLib.Mod.Handlers.DictionaryHandler;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.IdenWorldEventHandler;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.UpdateHandler;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.WorldEventHandler;
import denoflionsx.denLib.Mod.Proxy.denLibProxy;
import java.io.File;
import java.util.Arrays;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

@Mod(modid = "@NAME@", name = "@NAME@", version = "@VERSION@")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class denLibMod extends DummyModContainer {

    @SidedProxy(clientSide = "@PROXYCLIENT@", serverSide = "@PROXYSERVER@")
    public static denLibProxy Proxy;
    public static TunableManager tuning;
    public static File configFile;
    public static File coreConfig;
    public static Configuration config;
    public static DictionaryHandler h;

    public denLibMod() {
        super(new ModMetadata());
        ModMetadata md = super.getMetadata();
        md.modId = "@NAME@";
        md.name = "@NAME@";
        md.version = "@VERSION@";
        md.authorList = Arrays.asList("denoflionsx");
        md.url = "http://denoflionsx.info";
        md.description = "den's Library";
    }

    @EventHandler
    public void preLoad(FMLPreInitializationEvent event) {
        Proxy.setupLogger(event.getModConfigurationDirectory());
        denLibMod.tuning = new TunableManager();
        Proxy.registerForgeSubscribe(this);
        configFile = event.getSuggestedConfigurationFile();
        config = new Configuration(configFile);
        coreConfig = new File(event.getModConfigurationDirectory() + "/denoflionsx/denLibCore/updater.cfg");
        tuning.registerTunableClass(denLibTuning.class);
        //Proxy.print("Liquid Block code from powercrystalscore, originally from King_Lemming.");
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        Proxy.print("denLib loading...");
    }

    @EventHandler
    public void modsLoaded(FMLPostInitializationEvent evt) {
        Proxy.print("denLib load complete.");
        denLibCore.updater.startUpdaterThread();
        WorldEventHandler.registerHandler(new UpdateHandler());
        WorldEventHandler.registerHandler(new ChangeLogWorld());
        Proxy.print("This is denLib version " + "@VERSION@");
    }

    @ForgeSubscribe
    public void worldLoaded(WorldEvent.Load event) {
        for (IdenWorldEventHandler handler : WorldEventHandler.getHandlers()) {
            handler.onWorldLoaded();
        }
        WorldEventHandler.getHandlers().removeAll(WorldEventHandler.getRemoveQueue());
        WorldEventHandler.getHandlers().trimToSize();
        WorldEventHandler.getRemoveQueue().clear();
    }

    @ForgeSubscribe
    public void worldEnded(WorldEvent.Unload event) {
        for (IdenWorldEventHandler handler : WorldEventHandler.getHandlers()) {
            handler.onWorldEnded();
        }
        WorldEventHandler.getHandlers().removeAll(WorldEventHandler.getRemoveQueue());
        WorldEventHandler.getHandlers().trimToSize();
        WorldEventHandler.getRemoveQueue().clear();
    }

    // Wtf is this shit? Didn't FML used to do this automatically?
    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        return true;
    }
}
