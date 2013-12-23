package denoflionsx.denLib.Mod.Changelog;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.IdenWorldEventHandler;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.WorldEventHandler;

public class ChangeLogWorld implements IdenWorldEventHandler {

    @Override
    public void onWorldLoaded() {
        TickRegistry.registerScheduledTickHandler(new ChangelogReader(), Side.CLIENT);
        WorldEventHandler.unregisterHandler(this);
    }

    @Override
    public void onWorldEnded() {
    }


}
