package denoflionsx.denLib.Mod.Proxy;

import cpw.mods.fml.common.FMLLog;
import denoflionsx.denLib.Mod.Changelog.ChangelogReader;
import denoflionsx.denLib.Mod.Changelog.IChangeLogHandler;
import denoflionsx.denLib.Mod.Handlers.DictionaryHandler;
import denoflionsx.denLib.Mod.denLibMod;
import java.io.File;
import net.minecraftforge.common.MinecraftForge;

public class denLibProxy implements IdenLibProxy {
    
    @Override
    public void print(String msg) {
        FMLLog.info("[denLib]: " + msg);
    }
    
    @Override
    public void warning(String warning) {
        FMLLog.warning("[denLib]: " + warning);
    }
    
    @Override
    public void registerForgeSubscribe(Object o) {
        MinecraftForge.EVENT_BUS.register(o);
    }
    
    @Override
    public void sendMessageToPlayer(String msg) {
    }
    
    @Override
    public void registerChangelogHandler(Object o) {
        ChangelogReader.handlers.add((IChangeLogHandler) o);
    }
    
    @Override
    public void makeDirs(File configDir) {
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
    }
    
    @Override
    public void setupLogger(File configDir) {
        denLibMod.h = new DictionaryHandler(configDir.getAbsolutePath());
        this.registerForgeSubscribe(denLibMod.h);
    }
}
