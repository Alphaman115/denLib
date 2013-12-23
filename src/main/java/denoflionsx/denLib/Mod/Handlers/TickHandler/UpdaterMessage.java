package denoflionsx.denLib.Mod.Handlers.TickHandler;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import denoflionsx.denLib.CoreMod.Config.denLibTuning;
import denoflionsx.denLib.Mod.denLibMod;

import java.util.ArrayList;
import java.util.EnumSet;

public class UpdaterMessage implements ITickHandler {

    private static final ArrayList<String> stuffToPrint = new ArrayList();
    private boolean printed = false;
    private final int delay = 1000;
    private int count = 0;

    public static void add(String msg) {
        if (!stuffToPrint.contains(msg)) {
            stuffToPrint.add(msg);
        }
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        if (!printed) {
            count++;
            if (count > delay) {
                if (denLibTuning.updater.updater_alert.toLowerCase().equals("true")) {
                    for (String s : stuffToPrint) {
                        denLibMod.Proxy.sendMessageToPlayer(s);
                    }
                }
                printed = true;
            }
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
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
