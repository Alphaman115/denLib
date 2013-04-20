package denoflionsx.denLib.Config.TestConfig;

import denoflionsx.denLib.Config.Annotations.Config;
import denoflionsx.denLib.Config.Annotations.Tunable;
import denoflionsx.denLib.Mod.denLibMod;
import net.minecraftforge.common.Configuration;

public class denLibTunable {
    
    @Config
    public static Configuration config = new Configuration(denLibMod.configFile);
    
    @Tunable(category = "Test")
    public static class Test {
        
        public static String test = "This is a test";
    }
}
