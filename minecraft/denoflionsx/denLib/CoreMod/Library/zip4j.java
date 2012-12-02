package denoflionsx.denLib.CoreMod.Library;

import cpw.mods.fml.relauncher.ILibrarySet;

public class zip4j implements ILibrarySet{

    @Override
    public String[] getHashes() {
        return new String[]{"5cc198a8e41901973b4a73725f3f66156b98a32f"};
    }

    @Override
    public String[] getLibraries() {
        return new String[]{"zip4j_1.3.1.jar"};
    }

    @Override
    public String getRootURL() {
        return "https://dl.dropbox.com/u/23892866/%s";
    } 
}
