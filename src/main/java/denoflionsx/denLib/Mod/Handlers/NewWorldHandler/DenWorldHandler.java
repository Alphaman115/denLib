package denoflionsx.denLib.Mod.Handlers.NewWorldHandler;

import denoflionsx.denLib.Mod.denLibMod;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

import java.util.ArrayList;

public class DenWorldHandler {

    private ArrayList<IDenLibWorldHandler> handlers = new ArrayList();
    private ArrayList<IDenLibWorldHandler> removeQueue = new ArrayList();

    public DenWorldHandler() {
        this.register();
    }

    private void register() {
        denLibMod.Proxy.registerForgeSubscribe(this);
    }

    public void registerHandler(IDenLibWorldHandler handler) {
        handlers.add(handler);
    }

    public void removeHandler(IDenLibWorldHandler handler) {
        removeQueue.add(handler);
    }

    @ForgeSubscribe
    public void onEvent(WorldEvent.Load evt) {
        for (IDenLibWorldHandler i : handlers) {
            i.onWorldLoaded(evt.world);
        }
        handlers.removeAll(removeQueue);
        handlers.trimToSize();
        removeQueue.clear();
    }

    public ArrayList<IDenLibWorldHandler> getHandlers() {
        return handlers;
    }

    public ArrayList<IDenLibWorldHandler> getRemoveQueue() {
        return removeQueue;
    }
}
