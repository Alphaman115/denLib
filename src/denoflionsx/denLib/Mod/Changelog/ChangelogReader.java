package denoflionsx.denLib.Mod.Changelog;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import denoflionsx.denLib.Lib.denLib;
import denoflionsx.denLib.Mod.denLibMod;
import java.io.File;
import java.util.EnumSet;

public class ChangelogReader implements ITickHandler {

    private static int delay = 400;
    private int delay_p;
    private int count = 0;
    private boolean display = true;
    private BiMap<Integer, Integer> hasBroadcast = HashBiMap.create();
    private File saveFile;
    private String changeLogDir;
    private int buildnumber;
    private String name;

    public ChangelogReader(int buildnumber, File configDir, String name, String changelogDir) {
        delay+=100;
        this.delay_p = delay;
        this.buildnumber = buildnumber;
        this.name = name;
        this.saveFile = new File(configDir.getAbsolutePath() + "/hasAlerted_fixed.BiMap");
        this.changeLogDir = changelogDir;
        if (saveFile.exists()) {
            hasBroadcast = denLib.FileUtils.readBiMapFromFile(saveFile);
        }
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        if (display) {
            count++;
            if (count > delay_p) {
                if (hasBroadcast.get(this.buildnumber) == null) {
                    String[] changelog = denLib.StringUtils.readFileContentsAutomated(null, this.changeLogDir + this.buildnumber + ".txt");
                    denLibMod.Proxy.sendMessageToPlayer("Changelog: " + this.name);
                    for (String s : changelog) {
                        if (s.equals(denLib.StringUtils.readError)) {
                            continue;
                        }
                        denLibMod.Proxy.sendMessageToPlayer(s);
                    }
                    hasBroadcast.put(this.buildnumber, this.buildnumber);

                    denLib.FileUtils.saveBiMapToFile(hasBroadcast, saveFile);
                }
                display = false;
            }
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
    }

    @Override
    public EnumSet<TickType> ticks() {
        if (!display) {
            return EnumSet.noneOf(TickType.class);
        }
        return EnumSet.of(TickType.CLIENT);
    }

    @Override
    public String getLabel() {
        return this.name;
    }
}
