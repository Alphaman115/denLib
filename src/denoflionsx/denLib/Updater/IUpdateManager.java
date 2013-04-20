package denoflionsx.denLib.Updater;

import java.util.ArrayList;

public interface IUpdateManager {
    
    public void registerModForUpdate(Object o);
    
    public ArrayList<Object> getRegisteredMods();
}
