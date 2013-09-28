package denoflionsx.denLib.Mod;

import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import denoflionsx.denLib.Config.Manager.TunableManager;
import denoflionsx.denLib.CoreMod.Config.denLibTuning;
import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Mod.Changelog.ChangeLogWorld;
import denoflionsx.denLib.Mod.Handlers.DictionaryHandler;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.IdenWorldEventHandler;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.UpdateHandler;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.WorldEventHandler;
import denoflionsx.denLib.Mod.Logger.DenFormat;
import denoflionsx.denLib.Mod.Proxy.denLibProxy;
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
    public static File coreConfig;
    public static Configuration config;
    public static DictionaryHandler DictionaryHandler;

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

    @Subscribe
    @Override
    public void preLoad(FMLPreInitializationEvent event) {
        this.setupProxy("@PROXYCLIENT@", "@PROXYSERVER@");
        denLibMod.tuning = new TunableManager();
        config = new Configuration(event.getSuggestedConfigurationFile());
        DictionaryHandler = new DictionaryHandler();
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
