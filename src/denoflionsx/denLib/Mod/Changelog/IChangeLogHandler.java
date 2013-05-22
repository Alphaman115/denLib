package denoflionsx.denLib.Mod.Changelog;

import java.io.InputStream;

public interface IChangeLogHandler {
    
    public InputStream getFileInput();
    
    public String getName();
    
    public int getBuildNumber();
    
}
