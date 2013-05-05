package denoflionsx.denLib.CoreMod.Library;

import cpw.mods.fml.relauncher.ILibrarySet;
import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Lib.denLib;
import java.io.File;
import java.util.ArrayList;

public class LibraryManager {

    public static final LibraryManager instance = new LibraryManager();
    private static final LibUtils utils = new LibUtils();
    private ArrayList<ILibrarySet> libraries = new ArrayList();

    static {
        instance.addLibrary(instance.new APIFile());
    }

    public void addLibrary(ILibrarySet lib) {
        libraries.add(lib);
    }

    public void runLibraryChecks() {
        for (ILibrarySet lib : libraries) {
            String url = lib.getRootURL().replace("%s", lib.getLibraries()[0]);
            String hashUrl = lib.getRootURL().replace("%s", lib.getHashes()[0]);
            File deleteMe = new File("mods/" + lib.getLibraries()[0]);
            if (!deleteMe.exists()) {
                denLib.NetUtils.readBinaryFromNet(denLib.NetUtils.newUrlFromString(url), deleteMe);
            }
            //----
            if (utils.shouldDeleteCurrent(hashUrl, "mods/" + lib.getLibraries()[0])) {
                denLibCore.print("Updating API files...");
                deleteMe.delete();
                denLib.NetUtils.readBinaryFromNet(denLib.NetUtils.newUrlFromString(url), deleteMe);
            } else {
                denLibCore.print("No update needed.");
            }

        }
    }

    public class APIFile implements ILibrarySet {

        // I know I'm not using this how cpw intended.
        @Override
        public String[] getLibraries() {
            return new String[]{"APICollection-1.5.1-${project.version}.unknown.jar"};
        }

        @Override
        public String[] getHashes() {
            return new String[]{"sha1.txt"};
        }

        @Override
        public String getRootURL() {
            return "https://dl.dropboxusercontent.com/u/23892866/denLibLibrary/%s";
        }
    }
}
