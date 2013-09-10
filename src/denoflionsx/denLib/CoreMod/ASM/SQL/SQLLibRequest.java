package denoflionsx.denLib.CoreMod.ASM.SQL;

import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Lib.denLib;
import java.io.File;
import java.net.MalformedURLException;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class SQLLibRequest implements IClassTransformer {

    public SQLLibRequest() {
        this.doLib();
    }

    public final void doLib() {
        File libDir = new File("./mods/denLib");
        if (!libDir.exists()) {
            libDir.mkdirs();
        }
        File lib = new File(libDir, "/sqlite-jdbc-3.7.15-M1.jar");
        File zippedLib = new File(lib.getAbsolutePath().replace(".jar", ".zip"));
        if (!lib.exists()) {
            denLibCore.print("Downloading required files...");
            denLib.NetUtils.readBinaryFromNet(denLib.NetUtils.newUrlFromString("https://dl.dropboxusercontent.com/u/23892866/Downloads/sqlite-jdbc-3.7.15-M1.zip"), zippedLib);
            denLib.FileUtils.unzip(zippedLib, lib);
            if (!zippedLib.delete()) {
                zippedLib.deleteOnExit();
            }
        }
        try {
            this.loadFiles(libDir);
            denLibCore.print("Testing library...");
            if (Class.forName("org.sqlite.JDBC") != null) {
                denLibCore.print("Test successful!");
            } else {
                denLibCore.print("Test failed!");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void loadFiles(File dir) throws MalformedURLException {
        LaunchClassLoader classLoader = (LaunchClassLoader) this.getClass().getClassLoader();

        for (File file : dir.listFiles()) {
            if (file.getAbsolutePath().contains(".zip")) {
                continue;
            }
            classLoader.addURL(file.toURI().toURL());

            denLibCore.print("Loaded library " + file.getName());
        }
    }

    @Override
    public byte[] transform(String string, String string1, byte[] bytes) {
        return bytes;
    }
}
