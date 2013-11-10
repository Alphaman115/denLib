package denoflionsx.denLib.Mod;

import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import denoflionsx.denLib.Config.Manager.TunableManager;
import denoflionsx.denLib.CoreMod.Config.denLibTuning;
import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Mod.Changelog.ChangeLogWorld;
import denoflionsx.denLib.Mod.Handlers.ChunkHandler;
import denoflionsx.denLib.Mod.Handlers.DictionaryHandler;
import denoflionsx.denLib.Mod.Handlers.NewDictHandler.DenLibDictionaryHandler;
import denoflionsx.denLib.Mod.Handlers.NewFluidHandler.DenLibFluidHandler;
import denoflionsx.denLib.Mod.Handlers.NewWorldHandler.DenWorldHandler;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.IdenWorldEventHandler;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.UpdateHandler;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.WorldEventHandler;
import denoflionsx.denLib.Mod.Logger.DenFormat;
import denoflionsx.denLib.Mod.Proxy.denLibProxy;
import denoflionsx.denLib.NewConfig.DenConfig;
import java.io.File;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

public class denLibMod extends DenModContainer {

    private static final Logger LOG = Logger.getLogger(denLibMod.class.getName());
    public static denLibProxy Proxy;
    public static TunableManager tuning;
    public static DenConfig configManager;
    public static File coreConfig;
    public static Configuration config;
    public static DictionaryHandler DictionaryHandler;
    public static DenLibDictionaryHandler dict;
    public static DenLibFluidHandler fluids;
    public static ChunkHandler TERemover;
    public static DenWorldHandler worldHandler;

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

    public static void log(String msg) {
        LOG.info(msg);
    }
    
    public static void error(String clazz, String method, Throwable ex){
        LOG.throwing(clazz, method, ex);
    }

    @Subscribe
    @Override
    public void preLoad(FMLPreInitializationEvent event) {
        this.setupProxy("@PROXYCLIENT@", "@PROXYSERVER@");
        denLibMod.tuning = new TunableManager();
        configManager = new DenConfig();
        config = new Configuration(event.getSuggestedConfigurationFile());
        DictionaryHandler = new DictionaryHandler();
        fluids =  new DenLibFluidHandler();
        dict = new DenLibDictionaryHandler();
        Proxy.registerForgeSubscribe(DictionaryHandler);
        try {
            DenFormat f = new DenFormat();
            Handler handler = new FileHandler(event.getModConfigurationDirectory().getAbsolutePath() + "/denLib.log");
            handler.setFormatter(f);
            LOG.addHandler(handler);
        } catch (Throwable t) {
        }
        Proxy.registerForgeSubscribe(this);
        coreConfig = new File(event.getModConfigurationDirectory() + "/denoflionsx/denLibCore/updater.cfg");
        tuning.registerTunableClass(denLibTuning.class);
        Proxy.reloadConfig();
        TERemover = new ChunkHandler();
        Proxy.registerForgeSubscribe(TERemover);
        worldHandler = new DenWorldHandler();
        // dummy commit to test jenkins
    }
    
    @Subscribe
    public void onServerStarting(FMLServerStartingEvent event){
    }

    @Subscribe
    @Override
    public void load(FMLInitializationEvent event) {
        Proxy.print("denLib loading...");
    }

    @Subscribe
    @Override
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
}
