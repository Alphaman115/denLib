package denoflionsx.denLib.CoreMod.Updater;

import com.google.common.collect.BiMap;
import denoflionsx.denLib.CoreMod.Updater.Thread.ThreadedUpdater;
import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Lib.denLib;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UpdateManager {

    private ArrayList<IDenUpdate> validforUpdate = new ArrayList();
    public static final Set<String> stuffToPrint = new HashSet();

    public void registerUpdate(IDenUpdate update) {
        validforUpdate.add(update);
        denLibCore.print("Registered updater for " + update.getUpdaterName());
    }

    public void doUpdate() {
        if (denLibCore.check.exists()) {
            denLibCore.print("Updating mods...");
            BiMap<String, String[]> mods = denLib.FileUtils.readBiMapFromFile(denLibCore.check);
            for (String[] data : mods.values()) {
                File modFile = new File(data[0]);
                URL internets = denLib.NetUtils.newUrlFromString(data[1]);
                modFile.delete();
                File wasSaved = denLib.NetUtils.readBinaryFromNet(internets, modFile);
                if (wasSaved.exists()) {
                    denLibCore.print("Mod updated!");
                }
                if (!denLibCore.check.delete()) {
                    denLibCore.check.deleteOnExit();
                }
            }
        }
    }

    public void startUpdaterThread() {
        Thread updater = new ThreadedUpdater(validforUpdate, new ArrayList(), denLibCore.check);
        updater.start();
    }
}
