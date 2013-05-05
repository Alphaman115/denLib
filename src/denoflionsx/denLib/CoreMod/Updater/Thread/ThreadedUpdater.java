package denoflionsx.denLib.CoreMod.Updater.Thread;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import denoflionsx.denLib.CoreMod.Updater.IDenUpdate;
import denoflionsx.denLib.Lib.denLib;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ThreadedUpdater extends Thread {

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
            this.runUpdateChecks();

        }
    }

    private void runUpdateChecks() {
        File r = new File("do_not_update.txt");
        if (r.exists()){
            this.interrupt();
            return;
        }
        for (IDenUpdate i : syncedList) {
            String[] read2 = denLib.NetUtils.readFileFromURL(i.getUpdaterUrl());
            int versionLocal = Integer.valueOf(i.getBuildNumber());
            int versionRemote = Integer.valueOf(denLib.StringUtils.removeSpaces(read2[0].replace("# Version:", "")));
            i.setUpdatedModFileUrl(denLib.StringUtils.removeSpaces(read2[1].replace("# URL:", "")));
            if (versionRemote > versionLocal) {
                this.print("Update Found for " + i.getUpdaterName());
                this.print("This mod will be updated when you next launch Minecraft.");
                this.syncedListUpdate.add(i);
            }

        }
        try {
            if (!this.syncedListUpdate.isEmpty()) {
                // Create something that can be saved properly.
                BiMap<String, String[]> saveMap = HashBiMap.create();
                for (IDenUpdate i : this.syncedListUpdate) {
                    String[] info = new String[]{i.getSourceFile().getAbsolutePath(), i.getUpdatedModFileUrl()};
                    saveMap.put(i.getUpdaterName(), info);
                }
                //-----
                if (outputFile.exists()) {
                    outputFile.delete();
                }
                denLib.FileUtils.saveBiMapToFile(saveMap, outputFile);
                this.syncedListUpdate.clear();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void print(String msg) {
        System.out.println("[denLibUpdater]: " + msg);
    }
}
