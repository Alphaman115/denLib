package denoflionsx.denLib.CoreMod.Updater;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import denoflionsx.denLib.CoreMod.Updater.Thread.ThreadedUpdater;
import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Lib.denLib;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class UpdateManager {

    private ArrayList<IDenUpdate> validforUpdate = new ArrayList();
    public static final Set<String> stuffToPrint = new HashSet();
    private static final File denLibUpdated = new File("denLibUpdated.BiMap");
    private static final File DELETE = new File("YOU_WILL_DELETE.bin");

    /*
     * This turned into a clusterfuck really fast due to files getting locked.
     */
    public void registerUpdate(IDenUpdate update) {
        validforUpdate.add(update);
        denLibCore.print("Registered updater for " + update.getUpdaterName());
    }

    public void doUpdate() {
        if (DELETE.exists()) {
            DELETE.delete();
            denLibUpdated.delete();
            denLibCore.check.delete();
        }
        if (denLibCore.check.exists()) {
            denLibCore.print("Updating mods...");
            BiMap<String, String[]> mods = denLib.FileUtils.readBiMapFromFile(denLibCore.check);
            for (String[] data : mods.values()) {
                File modFile = new File(data[0]);
                if (modFile.getAbsolutePath().equals(denLibCore.location.getAbsolutePath()) && denLibUpdated.exists()) {
                    continue;
                }
                URL internets = denLib.NetUtils.newUrlFromString(data[1]);
                modFile.delete();
                File wasSaved = denLib.NetUtils.readBinaryFromNet(internets, modFile);
                if (wasSaved.exists()) {
                    denLibCore.print("Mod updated!");
                    BiMap<String, String> ensureDelete = HashBiMap.create();
                    ensureDelete.put("asdf", String.valueOf(1234));
                    denLib.FileUtils.saveBiMapToFile(ensureDelete, DELETE);
                    if (DELETE.exists()) {
                        denLibCore.print("Marked old files for delete.");
                    }else{
                        denLibCore.print("Something went wrong.");
                    }
                }
                if (!denLibCore.check.delete()) {
                    denLibCore.check.deleteOnExit();
                }
                if (denLibUpdated.exists()) {
                    if (!denLibUpdated.delete()) {
                        denLibUpdated.deleteOnExit();
                    }
                }
                if (modFile.getAbsolutePath().equals(denLibCore.location.getAbsolutePath()) && !denLibUpdated.exists()) {
                    denLibCore.print("This was a denLib update. Need to restart again. Sorry.");
                    try {
                        BiMap<String, String> temp = HashBiMap.create();
                        temp.put("DENLIB", "UPDATED");
                        denLib.FileUtils.saveBiMapToFile(temp, denLibUpdated);
                        TimeUnit.SECONDS.sleep(5);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        }
    }

    public void startUpdaterThread() {
        Thread updater = new ThreadedUpdater(validforUpdate, new ArrayList(), denLibCore.check);
        updater.start();
    }
}
