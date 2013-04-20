package denoflionsx.denLib.Config.Manager;

import java.util.ArrayList;

public interface ITunableManager {

    public void registerTunableClass(Class c);
    
    public ArrayList<Class> getTunableClasses();
}
