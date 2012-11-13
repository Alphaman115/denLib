package denoflionsx.denLib.Mod.Core;

import denoflionsx.denLib.Mod.API.denLibManagers;
import denoflionsx.denLib.Mod.Managers.LeavesDropManager;

public class denLibCore {
    
    public void registerManagers(){
        denLibManagers.LeavesDropManager = new LeavesDropManager();
    }
}
