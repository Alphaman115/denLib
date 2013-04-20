package denoflionsx.denLib.Updater;

import java.util.ArrayList;

public class UpdateManager implements IUpdateManager {
    
    private ArrayList<Object> objects = new ArrayList();

    @Override
    public ArrayList<Object> getRegisteredMods() {
        return objects;
    }

    @Override
    public void registerModForUpdate(Object o) {
        objects.add(o);
    }
}
