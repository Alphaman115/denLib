package denoflionsx.denLib.CoreMod.ASM;

import cpw.mods.fml.relauncher.IClassTransformer;
import denoflionsx.denLib.CoreMod.Interfaces.IObfName;
import denoflionsx.denLib.CoreMod.denLibCoreMod;

public class denLibTransformer implements IClassTransformer, IObfName {

    private String obf;

    public denLibTransformer() {
        this.setObfName("");
    }

    @Override
    public byte[] transform(String name, byte[] bytes) {
        if (!name.equals(this.getObfName())) {
            return bytes;
        }
        bytes = OverrideClass.Override(name, bytes, this.getObfName(), denLibCoreMod.location);
        return bytes;
    }

    @Override
    public String getObfName() {
        return obf;
    }

    @Override
    public void setObfName(String name) {
        this.obf = name;
    }   
}
