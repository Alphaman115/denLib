package denoflionsx.denLib.Mod.Handlers.WorldHandler;

import java.util.ArrayList;

public class WorldEventHandler {
    
    private static ArrayList<IdenWorldEventHandler> handlers = new ArrayList();
    private static ArrayList<IdenWorldEventHandler> removeQueue = new ArrayList();
    
    public static void registerHandler(IdenWorldEventHandler handler) {
        handlers.add(handler);
    }
    
    public static void unregisterHandler(IdenWorldEventHandler handler) {
        removeQueue.add(handler);
    }
    
    public static ArrayList<IdenWorldEventHandler> getHandlers() {
        return handlers;
    }
    
    public static ArrayList<IdenWorldEventHandler> getRemoveQueue(){
        return removeQueue;
    }
}
