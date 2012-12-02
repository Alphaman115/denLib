package denoflionsx.denLib.Mod.Proxy;

import denoflionsx.denLib.Interfaces.IDenProxy;
import denoflionsx.denLib.denLib;
import java.io.File;

public class Proxy implements IDenProxy{

    @Override
    public String preloadTextures(String texture) {
        return texture;
    }
    
    @Override
    public void print(String msg) {
        denLib.print("[denLib]: " + msg);
    }

    @Override
    public void sendMessageToPlayer(String msg) {
    }

    @Override
    public String getConfigDir() {
        return "./" + File.separator + "config" + File.separator + "denoflionsx" + File.separator;
    }

}
