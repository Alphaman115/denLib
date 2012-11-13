package denoflionsx.denLib.CoreMod.Transformers;

import codechicken.core.asm.ClassOverrider;
import codechicken.core.asm.ObfuscationManager;
import cpw.mods.fml.relauncher.IClassTransformer;
import denoflionsx.denLib.CoreMod.Interfaces.IObfName;
import denoflionsx.denLib.CoreMod.denLibCoreMod;

public class TransformerBlockLeave implements IClassTransformer, IObfName{

    @Override
    public byte[] transform(String name, byte[] bytes) {
        bytes = ClassOverrider.overrideBytes(name, bytes, new ObfuscationManager.ClassMapping(this.getObfName()), denLibCoreMod.location);
        return bytes;
    }

    @Override
    public String getObfName() {
        return "aji";
    }

}
