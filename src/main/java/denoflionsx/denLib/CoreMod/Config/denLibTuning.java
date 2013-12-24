package denoflionsx.denLib.CoreMod.Config;

import denoflionsx.denLib.Config.Annotations.Comment;
import denoflionsx.denLib.Config.Annotations.Config;
import denoflionsx.denLib.Config.Annotations.Tunable;
import denoflionsx.denLib.Mod.denLibMod;
import net.minecraftforge.common.Configuration;

public class denLibTuning {

    @Config
    public static Configuration config = new Configuration(denLibMod.coreConfig);

    @Tunable(category = "updater")
    public static class updater {

        @Comment(comment = "You might want to disable this on servers.")
        public static String updater_enabled = String.valueOf(false);
        @Comment(comment = "This just tells you there is an update.")
        public static String updater_alert = String.valueOf(true);
    }
}
