package denoflionsx.denLib.CoreMod.Interfaces;

import java.io.File;

public interface IdenLibTransformer {
    
    public String getObfName();
    
    public void setObfName(String name);
    
    public void setLocation(File location);
    
    public File getLocation();
    
}
