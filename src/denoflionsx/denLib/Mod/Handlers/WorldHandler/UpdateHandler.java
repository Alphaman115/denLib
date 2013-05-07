package denoflionsx.denLib.Mod.Handlers.WorldHandler;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import denoflionsx.denLib.Mod.Handlers.TickHandler.UpdaterMessage;

public class UpdateHandler implements IdenWorldEventHandler {

    @Override
    public void onWorldLoaded() {
        TickRegistry.registerTickHandler(new UpdaterMessage(), Side.CLIENT);
        WorldEventHandler.unregisterHandler(this);
    }

    @Override
    public void onWorldEnded() {
    }
}
