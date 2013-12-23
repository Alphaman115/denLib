package denoflionsx.denLib.CoreMod.ASM;

import denoflionsx.denLib.CoreMod.denLibCore;
import net.minecraft.launchwrapper.IClassTransformer;

import java.io.File;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

public class DepScanRequest implements IClassTransformer {

    private boolean fired = false;

    private void scanForDeps() {
        for (File f : denLibCore.mods.listFiles()) {
            if (f.getAbsolutePath().contains(".jar")) {
                try {
                    JarFile j = new JarFile(f);
                    for (Attributes a : j.getManifest().getEntries().values()) {
                        for (Object o : a.keySet()) {
                            Attributes.Name name = (Attributes.Name) o;
                            String value = a.getValue(name);
                            denLibCore.print(name + ": " + value);
                        }
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }

    @Override
    public byte[] transform(String string, String string1, byte[] bytes) {
        if (!fired) {
            if (string.contains("net.minecraft")) {
                denLibCore.print("Trying to scan jars...");
                fired = true;
                this.scanForDeps();
            }
        }
        return bytes;
    }

}
