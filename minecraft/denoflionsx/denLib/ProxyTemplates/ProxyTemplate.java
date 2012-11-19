package denoflionsx.denLib.ProxyTemplates;

import denoflionsx.denLib.Interfaces.IDenProxy;
import denoflionsx.denLib.denLib;
import java.io.File;

public class ProxyTemplate implements IDenProxy{
    
    // DON'T ACTUALLY EXTEND THESE.
    // THESE TEMPLATES ARE FOR REFERENCE ONLY!

    @Override
    public String preloadTextures(String texture) {
        return texture;
    }

    @Override
    public void print(String msg) {
        denLib.print(msg);
    }

    @Override
    public void sendMessageToPlayer(String msg) {
    }

    @Override
    public String getConfigDir() {
        return "./" + File.separator + "config" + File.separator + "denoflionsx" + File.separator;
    }
}
