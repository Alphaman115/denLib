package denoflionsx.denLib.Mod.Handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidRegistry.FluidRegisterEvent;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

// Will replace with something else just for fluids. I didn't know about ShapedOreRecipe.

@Deprecated
public class DictionaryHandler {

    private static final Logger LOG = Logger.getLogger(DictionaryHandler.class.getName());
    private static final HashMap<Short, ArrayList<IDictionaryListener>> listeners = new HashMap();
    private static final ArrayList<OreRegisterEvent> oreEvents = new ArrayList();
    private static final ArrayList<FluidRegistry.FluidRegisterEvent> fluidEvents = new ArrayList();

    public DictionaryHandler(String s) {
        try {
            SimpleFormatter f = new SimpleFormatter();
            Handler handler = new FileHandler(s + "/denLib.log");
            handler.setFormatter(f);
            LOG.addHandler(handler);
        } catch (Throwable t) {
        }
        this.onFluidEvent(new FluidRegisterEvent("water", FluidRegistry.WATER.getID()));
        this.onFluidEvent(new FluidRegisterEvent("lava", FluidRegistry.LAVA.getID()));
    }

    public void registerListener(IDictionaryListener o, short channel) {
        if (listeners.get(channel) == null) {
            listeners.put(channel, new ArrayList());
            LOG.info("Created listener list on channel ".concat(String.valueOf(channel)));
        }
        listeners.get(channel).add(o);
        LOG.info("Registered Listener: ".concat(o.getClass().getName()));
        // Post backlog events to late listeners.
        if (channel == channels.FLUID) {
            if (!fluidEvents.isEmpty()) {
                LOG.info("Sending ".concat(String.valueOf(fluidEvents.size()) + " cached events to ".concat(o.getClass().getName())));
                for (FluidRegistry.FluidRegisterEvent e : fluidEvents) {
                    o.onEvent(e.fluidName, channel, FluidRegistry.getFluid(e.fluidName));
                }
            }
        } else if (channel == channels.ORE) {
            if (!oreEvents.isEmpty()) {
                LOG.info("Sending ".concat(String.valueOf(oreEvents.size()) + " cached events to ".concat(o.getClass().getName())));
                for (OreRegisterEvent e : oreEvents) {
                    o.onEvent(e.Name, channel, e.Ore);
                }
            }
        }
    }

    public void postEvent(String tag, short channel, Object o) {
        try {
            ArrayList<IDictionaryListener> a = listeners.get(channel);
            for (IDictionaryListener b : a) {
                b.onEvent(tag, channel, o);
            }
        } catch (Throwable t) {
        }
    }

    @ForgeSubscribe
    public void onEvent(OreRegisterEvent e) {
        LOG.info("OreDictionary: ".concat(e.Name));
        this.postEvent(e.Name, channels.ORE, e.Ore);
        oreEvents.add(e);
    }

    @ForgeSubscribe
    public void onFluidEvent(FluidRegistry.FluidRegisterEvent e) {
        LOG.info("FluidDictionary: ".concat(e.fluidName));
        this.postEvent(e.fluidName, channels.FLUID, FluidRegistry.getFluid(e.fluidName));
        fluidEvents.add(e);
    }

    public static class channels {

        public static final short ORE = 0;
        public static final short FLUID = 1;
    }
}
