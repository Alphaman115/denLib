package denoflionsx.denLib.CoreMod.ASM.PfF;

import cpw.mods.fml.relauncher.IClassTransformer;
import denoflionsx.denLib.CoreMod.ASM.ClassOverride;
import denoflionsx.denLib.CoreMod.denLibCore;

public class TransformerBucket implements IClassTransformer {

    private String mapping;

    public TransformerBucket() {
        denLibCore.print("Setting up Bucket Transformer so PfF Wooden Buckets work correctly...");
        mapping = "uy";
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name.equals(mapping)) {
            bytes = ClassOverride.Override(name, bytes, mapping, denLibCore.location);
        }
        return bytes;
    }
}
