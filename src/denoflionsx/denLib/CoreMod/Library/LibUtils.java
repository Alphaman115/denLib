package denoflionsx.denLib.CoreMod.Library;

import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Lib.denLib;
import java.io.File;
import java.lang.reflect.Method;

public class LibUtils {

    public boolean shouldDeleteCurrent(String url, String file) {
        String file_sha = denLib.NetUtils.readFileFromURL(url)[0];
        try {
            denLibCore.print("Reflecting into FML to generate sha1 hash...");
            Class c = Class.forName("cpw.mods.fml.common.CertificateHelper");
            Method m = null;
            for (Method s : c.getDeclaredMethods()) {
                if (s.getName().equals("hexify")) {
                    m = s;
                }
            }
            if (m == null) {
                return false;
            } else {
                m.setAccessible(true);
                Object o = m.invoke(null, denLib.FileUtils.createSha1(new File(file)));
                String sha1 = String.valueOf(o);
                denLibCore.print(sha1);
                denLibCore.print(file_sha);
                if (sha1.equals(file_sha)) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
