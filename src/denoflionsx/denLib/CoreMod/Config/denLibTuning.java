package denoflionsx.denLib.CoreMod.Config;

import denoflionsx.denLib.Config.Annotations.Comment;
import denoflionsx.denLib.Config.Annotations.Config;
import denoflionsx.denLib.Config.Annotations.Tunable;
import java.io.File;
import net.minecraftforge.common.Configuration;

public class denLibTuning {
    
    public static final File f = new File("config/denoflionsx/denLibCore/updater.cfg");
    @Config
    public static Configuration config = new Configuration(f);
    
    static{
        File q = new File("config/denoflionsx/denLibCore");
        if (!q.exists()){
            q.mkdirs();
        }
    }
    
    @Tunable(category = "updater")
    public static class updater{
        
        @Comment(comment = "You might want to disable this on servers.")
        public static String updater_enabled = String.valueOf(false);
        @Comment(comment = "This just tells you there is an update.")
        public static String updater_alert = String.valueOf(true);
        
    }
    
}
