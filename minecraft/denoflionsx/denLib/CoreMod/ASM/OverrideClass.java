package denoflionsx.denLib.CoreMod.ASM;

import java.io.File;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class OverrideClass {

    public static byte[] Override(String name, byte[] bytes, String ObfName, File location) {
        try {
            ZipFile zip = new ZipFile(location);
            ZipEntry entry = zip.getEntry(name.replace('.', '/') + ".class");
            if (entry == null) {
                System.out.println(name + " not found in " + location.getName());
            } else {
                InputStream zin = zip.getInputStream(entry);
                bytes = new byte[(int) entry.getSize()];
                zin.read(bytes);
                zin.close();
                System.out.println("[" + "denLib" + "]: " + "Class " + name + " patched!");
            }
            zip.close();
        } catch (Exception e) {
            throw new RuntimeException("Error overriding " + name + " from " + location.getName(), e);
        }
        return bytes;
    }
}
