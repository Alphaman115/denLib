package denoflionsx.denLib.Mod.Proxy;

import cpw.mods.fml.common.FMLLog;
import denoflionsx.denLib.Mod.Event.DenEventHandler;
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
    public void registerDenListen(Object o) {
        DenEventHandler.instance.register(o);
    }
}
