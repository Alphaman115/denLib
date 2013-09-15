package denoflionsx.denLib.Mod;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import java.lang.reflect.Field;
import java.util.Arrays;

public abstract class DenModContainer extends DummyModContainer {
    
    public DenModContainer() {
        super(new ModMetadata());
        ModMetadata md = super.getMetadata();
        md.modId = "@NAME@";
        md.name = "@NAME@";
        md.version = "@VERSION@";
        md.authorList = Arrays.asList("denoflionsx");
        md.url = "http://denoflionsx.info";
        md.description = "@DESC@";
    }
    
    public DenModContainer(ModMetadata md) {
        super(md);
    }
    
    public abstract void preLoad(FMLPreInitializationEvent event);
    
    public abstract void load(FMLInitializationEvent event);
    
    public abstract void modsLoaded(FMLPostInitializationEvent evt);
    
    public final void setupProxy(String client, String server) {
        try {
            for (Field f : this.getClass().getDeclaredFields()) {
                if (f.getName().toLowerCase().equals("proxy")) {
                    Object o = FMLCommonHandler.instance().getSide().isClient() ? Class.forName("@PROXYCLIENT@").newInstance() : Class.forName("@PROXYSERVER@").newInstance();
                    f.set(this, o);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }
}
