package denoflionsx.denLib.Mod.Handlers.WorldHandler;

import java.util.ArrayList;

public class WorldEventHandler {
    
    private static ArrayList<IdenWorldEventHandler> handlers = new ArrayList();
    
    public static void registerHandler(IdenWorldEventHandler handler){
        handlers.add(handler);
    }
    
    public static ArrayList<IdenWorldEventHandler> getHandlers(){
        return handlers;
    }
    
}
