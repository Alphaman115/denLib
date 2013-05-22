package denoflionsx.denLib.Mod.Changelog;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import denoflionsx.denLib.Lib.denLib;
import denoflionsx.denLib.Mod.denLibMod;
import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;

public class ChangelogReader implements IScheduledTickHandler {

    private boolean display = true;
    public static final ArrayList<IChangeLogHandler> handlers = new ArrayList();

    public ChangelogReader() {
    }

    @Override
    public int nextTickSpacing() {
        int delay = 400;
        if (!display) {
            return delay * 10;
        } else {
            return delay;
        }
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        if (display) {
            for (IChangeLogHandler h : handlers) {
                boolean post = true;
                String[] log;
                try {
                    log = denLib.StringUtils.readInputStream(h.getFileInput());
                } catch (Exception ex) {
                    log = new String[0];
                }
                if (denLibMod.config.hasKey("versiondisplay", h.getName() + h.getBuildNumber())){
                    post = false;
                }
                if (post) {
                    denLibMod.Proxy.sendMessageToPlayer("Changelog: " + h.getName());
                    for (String s : log) {
                        denLibMod.Proxy.sendMessageToPlayer(s);
                    }
                }
                denLibMod.config.get("versiondisplay", h.getName() + h.getBuildNumber(), true);
                denLibMod.config.save();
            }
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        display = false;
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }

    @Override
    public String getLabel() {
        return "denLib";
    }
}
