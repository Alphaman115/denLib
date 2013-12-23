package denoflionsx.denLib.CoreMod.Updater;

import java.io.File;

@Deprecated
public interface IDenUpdate {

    public String getUpdaterUrl();

    public int getBuildNumber();

    public String getUpdaterName();

    public void registerWithUpdater();

    public File getSourceFile();

    public String getUpdatedModFileUrl();

    public void setUpdatedModFileUrl(String url);

}
