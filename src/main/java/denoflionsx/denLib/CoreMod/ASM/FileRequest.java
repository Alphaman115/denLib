package denoflionsx.denLib.CoreMod.ASM;

import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Lib.denLib;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;

public class FileRequest implements IClassTransformer {

    private String fileName;
    private String url;
    public static final HashMap<String, Boolean> done = new HashMap();

    public FileRequest(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
        doLib();
    }

    public final void doLib() {
        if (done != null) {
            if (fileName != null) {
                if (done.get(fileName) != null) {
                    return;
                }
            }
        }
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
        if (!denLibCore.testing){
            if (isUpdateRequired(lib, ".zip")) {
                denLibCore.print("Downloading required files...");
                denLibCore.print(url);
                denLib.NetUtils.readBinaryFromNet(denLib.NetUtils.newUrlFromString(url), zippedLib);
                denLib.FileUtils.unzip(zippedLib, lib);
                if (!zippedLib.delete()) {
                    zippedLib.deleteOnExit();
                }
            }
        }
        try {
            if (lib.getAbsolutePath().contains(".jar")) {
                this.loadFiles(lib);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        done.put(fileName, Boolean.TRUE);
    }

    private boolean isUpdateRequired(File file, String extension) {
        try {
            if (!file.exists()) {
                return true;
            }
            String hash = denLib.StringUtils.hexify(denLib.StringUtils.createSha1(file));
            String hash2 = denLib.NetUtils.readFileFromURL(url.replace(extension, ".sha1"))[0];
            denLibCore.print(file.getName() + " | " + hash + " | " + hash2 + " | " + (String.valueOf(!hash.equals(hash2) && !hash2.equals("404"))));
            return !hash.equals(hash2) && !hash2.equals("404");
        } catch (Throwable t) {
            return false;
        }
    }

    private void loadFiles(File file) throws MalformedURLException {
        LaunchClassLoader classLoader = (LaunchClassLoader) this.getClass().getClassLoader();
        classLoader.addURL(file.toURI().toURL());
        denLibCore.print("Loaded library " + file.getName());
    }

    @Override
    public byte[] transform(String string, String string1, byte[] bytes) {
        return bytes;
    }
}
