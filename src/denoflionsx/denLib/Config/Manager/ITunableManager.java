package denoflionsx.denLib.Config.Manager;

import java.util.ArrayList;

@Deprecated
public interface ITunableManager {

    public void registerTunableClass(Class c);
    
    public ArrayList<Class> getTunableClasses();
}
