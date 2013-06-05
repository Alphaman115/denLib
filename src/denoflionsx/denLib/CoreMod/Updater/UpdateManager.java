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

    /*
     * This turned into a clusterfuck really fast due to files getting locked.
     */
    public void registerUpdate(IDenUpdate update) {
        validforUpdate.add(update);
        denLibCore.print("Registered updater for " + update.getUpdaterName());
    }

    public void doUpdate() {
        boolean does = false;
        String[] r = denLib.StringUtils.readFileContentsAutomated(new File("config/denoflionsx/denLibCore"), "updater.cfg", this);
        for (String s : r) {
            if (s.contains("S:updater_enabled")) {
                denLibCore.print("Found config file entry.");
                s = denLib.StringUtils.removeSpaces(s);
                String[] q = s.split("=");
                if (q[1].toLowerCase().equals("true")) {
                    does = true;
                }
            }
        }
        if (!does) {
            return;
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
