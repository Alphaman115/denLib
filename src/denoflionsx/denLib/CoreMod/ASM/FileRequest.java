package denoflionsx.denLib.CoreMod.ASM;

import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Lib.denLib;
import java.io.File;
import java.net.MalformedURLException;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class FileRequest implements IClassTransformer {

    private String fileName;
    private String url;

    public FileRequest(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
        doLib();
    }

    public final void doLib() {
        File libDir = new File("./mods/denLib");
        if (!libDir.exists()) {
            libDir.mkdirs();
        }
        File lib = new File(libDir, "/" + fileName);
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        File zippedLib = new File(lib.getAbsolutePath().replace(extension, "zip"));
        if (!lib.exists()) {
            denLibCore.print("Downloading required files...");
            denLib.NetUtils.readBinaryFromNet(denLib.NetUtils.newUrlFromString(url), zippedLib);
            denLib.FileUtils.unzip(zippedLib, lib);
            if (!zippedLib.delete()) {
                zippedLib.deleteOnExit();
            }
        }
        try {
            this.loadFiles(libDir);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private void loadFiles(File dir) throws MalformedURLException {
        LaunchClassLoader classLoader = (LaunchClassLoader) this.getClass().getClassLoader();

        for (File file : dir.listFiles()) {
            if (!file.getAbsolutePath().contains(".jar")) {
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
