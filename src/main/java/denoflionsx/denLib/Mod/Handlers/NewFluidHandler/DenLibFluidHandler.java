package denoflionsx.denLib.Mod.Handlers.NewFluidHandler;

import denoflionsx.denLib.Mod.denLibMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashMap;

public class DenLibFluidHandler {

    private HashMap<String, ArrayList<IDenLibFluidHandler>> handlerList = new HashMap();
    private HashMap<String, FluidStack> cache = new HashMap();

    public DenLibFluidHandler() {
        this.register();
    }

    private void register() {
        denLibMod.Proxy.registerForgeSubscribe(this);
        cache.put("water", FluidRegistry.getFluidStack("water", FluidContainerRegistry.BUCKET_VOLUME));
        cache.put("lava", FluidRegistry.getFluidStack("lava", FluidContainerRegistry.BUCKET_VOLUME));
        MinecraftForge.EVENT_BUS.post(new DenFluidHandlerEvents.Ready());
    }

    public void register(IDenLibFluidHandler handler) {
        if (handler.lookingForFluid() == null) {
            addToList("null", handler);
            for (FluidStack f : cache.values()) {
                handler.onEvent(f);
            }
        } else {
            addToList(handler.lookingForFluid(), handler);
            if (cache.containsKey(handler.lookingForFluid())) {
                handler.onEvent(cache.get(handler.lookingForFluid()));
            }
        }
    }

    private void addToList(String s, IDenLibFluidHandler handler) {
        if (!handlerList.containsKey(s)) {
            handlerList.put(s, new ArrayList());
        }
        handlerList.get(s).add(handler);
    }

    @ForgeSubscribe
    public void onEvent(FluidRegistry.FluidRegisterEvent e) {
        denLibMod.log("Fluid: " + e.fluidName);
        FluidStack f = new FluidStack(e.fluidID, FluidContainerRegistry.BUCKET_VOLUME);
        cache.put(f.getFluid().getName(), f);
        if (handlerList.containsKey(e.fluidName)) {
            for (IDenLibFluidHandler h : handlerList.get(e.fluidName)) {
                h.onEvent(f);
            }
        }
        if (handlerList.containsKey("null")) {
            for (IDenLibFluidHandler h : handlerList.get("null")) {
                h.onEvent(f);
            }
        }
    }
}
