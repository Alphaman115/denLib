package denoflionsx.denLib.CoreMod.Transformers;

import denoflionsx.denLib.CoreMod.ASM.denLibTransformer;
import denoflionsx.denLib.CoreMod.denLibCoreMod;
import java.io.File;

public class TransformerBase extends denLibTransformer{

    @Override
    public File getLocation() {
        return denLibCoreMod.location;
    }
}
