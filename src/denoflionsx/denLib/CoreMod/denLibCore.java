package denoflionsx.denLib.CoreMod;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import denoflionsx.denLib.CoreMod.Updater.IDenUpdate;
import denoflionsx.denLib.CoreMod.Updater.UpdateManager;
import java.io.File;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion(value = "1.5.2")
public class denLibCore implements IFMLLoadingPlugin, IDenUpdate {

    public static UpdateManager updater;
    public static File check = new File("denLibUpdateCheck.bin");
    public static final String build_number = "@BUILD_NUMBER@";
    public static File location;
    private String updatedurl;

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{"denoflionsx.denLib.CoreMod.ASM.PfF.TransformerBucket"};
    }

    @Override
    public String[] getLibraryRequestClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        location = (File) data.get("coremodLocation");
        updater = new UpdateManager();
        this.registerWithUpdater();
        updater.doUpdate();
        //LibraryManager.instance.runLibraryChecks();
    }

    public static void print(String msg) {
        FMLLog.info("[denLibCore]: " + msg);
    }

    @Override
    public String getUpdaterUrl() {
        return "https://dl.dropboxusercontent.com/u/23892866/VersionCheck/PfF3x/denLibTest.txt";
    }

    @Override
    public int getBuildNumber() {
        return Integer.valueOf(build_number);
    }

    @Override
    public String getUpdaterName() {
        return "denLib";
    }

    @Override
    public void registerWithUpdater() {
        denLibCore.updater.registerUpdate(this);
    }

    @Override
    public File getSourceFile() {
        return location;
    }

    @Override
    public String getUpdatedModFileUrl() {
        return this.updatedurl;
    }

    @Override
    public void setUpdatedModFileUrl(String url) {
        this.updatedurl = url;
    }
}
