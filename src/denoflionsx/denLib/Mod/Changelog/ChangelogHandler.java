package denoflionsx.denLib.Mod.Changelog;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.IdenWorldEventHandler;
import denoflionsx.denLib.Mod.Handlers.WorldHandler.WorldEventHandler;
import java.io.File;

public class ChangelogHandler implements IdenWorldEventHandler {
    
    private int buildnumber;
    private File configDir;
    private String name;
    private String path;
    private Side side;
    
    public static void createNewHandler(int buildnumber, File configDir, String name, String path) {
        ChangelogHandler c = new ChangelogHandler(buildnumber, configDir, name, path, Side.CLIENT);
        WorldEventHandler.registerHandler(c);
    }
    
    public ChangelogHandler(int buildnumber, File configDir, String name, String path, Side side) {
        this.buildnumber = buildnumber;
        this.configDir = configDir;
        this.name = name;
        this.path = path;
        this.side = side;
    }
    
    @Override
    public void onWorldLoaded() {
        TickRegistry.registerTickHandler(new ChangelogReader(this.buildnumber, this.configDir, this.name, this.path), this.side);
        WorldEventHandler.unregisterHandler(this);
    }
    
    @Override
    public void onWorldEnded() {
    }
}
