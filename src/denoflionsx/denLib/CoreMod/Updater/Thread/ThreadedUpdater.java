package denoflionsx.denLib.CoreMod.Updater.Thread;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import denoflionsx.denLib.CoreMod.Updater.IDenUpdate;
import denoflionsx.denLib.Lib.denLib;
import java.io.File;
import java.util.List;

public class ThreadedUpdater extends Thread {

    private boolean isRunning = false;
    private List<IDenUpdate> syncedList;
    private List<IDenUpdate> syncedListUpdate;
    private File outputFile;

    public ThreadedUpdater(List<IDenUpdate> syncedList, List<IDenUpdate> syncedListUpdate, File outputFile) {
        this.syncedList = syncedList;
        this.syncedListUpdate = syncedListUpdate;
        this.outputFile = outputFile;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            if (!this.isRunning) {
                this.runUpdateChecks();
            }
        }
    }

    private void runUpdateChecks() {
        this.isRunning = true;
        synchronized (syncedList) {
            for (IDenUpdate i : syncedList) {
                String[] read2 = denLib.NetUtils.readFileFromURL(i.getUpdaterUrl());
                int versionLocal = Integer.valueOf(i.getBuildNumber());
                int versionRemote = Integer.valueOf(denLib.StringUtils.removeSpaces(read2[0].replace("# Version:", "")));
                i.setUpdatedModFileUrl(denLib.StringUtils.removeSpaces(read2[1].replace("# URL:", "")));
                if (versionRemote > versionLocal) {
                    this.print("Update Found for " + i.getUpdaterName());
                    this.print("This mod will be updated when you next launch Minecraft.");
                    synchronized (this.syncedListUpdate) {
                        this.syncedListUpdate.add(i);
                    }
                }
            }
        }
        try {
            if (!this.syncedListUpdate.isEmpty()) {
                // Create something that can be saved properly.
                BiMap<String, String[]> saveMap = HashBiMap.create();
                synchronized (this.syncedListUpdate) {
                    for (IDenUpdate i : this.syncedListUpdate) {
                        String[] info = new String[]{i.getSourceFile().getAbsolutePath(), i.getUpdatedModFileUrl()};
                        saveMap.put(i.getUpdaterName(), info);
                    }
                }
                //-----
                denLib.FileUtils.saveBiMapToFile(saveMap, outputFile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.interrupt();
    }

    public void print(String msg) {
        System.out.println("[denLibUpdater]: " + msg);
    }
}
