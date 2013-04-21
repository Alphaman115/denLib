package denoflionsx.denLib.CoreMod.Updater;

import com.google.common.collect.BiMap;
import denoflionsx.denLib.CoreMod.Updater.Thread.ThreadedUpdater;
import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Lib.denLib;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpdateManager {

    private ArrayList<IDenUpdate> validForUpdate = new ArrayList();
    private List<IDenUpdate> threadSafe = new ArrayList();
    private ArrayList<IDenUpdate> needsUpdated = new ArrayList();
    private List<IDenUpdate> threadSafeUpdate = new ArrayList();

    public void registerUpdate(IDenUpdate update) {
        validForUpdate.add(update);
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
                if (wasSaved.exists()){
                    denLibCore.print("Mod updated!");
                }
                denLibCore.check.delete();
            }
        }
    }

    public void startUpdaterThread() {
        threadSafe = Collections.synchronizedList(validForUpdate);
        threadSafeUpdate = Collections.synchronizedList(needsUpdated);
        Thread updater = new ThreadedUpdater(threadSafe, threadSafeUpdate, denLibCore.check);
        updater.start();
    }
}
