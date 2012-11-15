package denoflionsx.denLib.CoreMod.ASM;

import cpw.mods.fml.relauncher.IClassTransformer;
import denoflionsx.denLib.CoreMod.Interfaces.IdenLibTransformer;
import java.io.File;

public class denLibTransformer implements IClassTransformer, IdenLibTransformer {

    private String obf;
    private File location;

    @Override
    public final byte[] transform(String name, byte[] bytes) {
        if (!name.equals(this.getObfName())) {
            return bytes;
        }
        bytes = OverrideClass.Override(name, bytes, this.getObfName(), this.getLocation());
        return bytes;
    }

    @Override
    public String getObfName() {
        return obf;
    }

    @Override
    public final void setObfName(String name) {
        this.obf = name;
    }

    @Override
    public final void setLocation(File location) {
        this.location = location;
    }

    @Override
    public File getLocation() {
        return this.location;
    }
}
