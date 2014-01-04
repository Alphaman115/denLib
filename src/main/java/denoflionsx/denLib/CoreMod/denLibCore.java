package denoflionsx.denLib.CoreMod;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import denoflionsx.denLib.CoreMod.ASM.ASMLogger;
import denoflionsx.denLib.CoreMod.ASM.DenEventsLib;
import denoflionsx.denLib.CoreMod.Updater.UpdateManager;
import denoflionsx.denLib.Lib.denLib;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class denLibCore implements IFMLLoadingPlugin {

    public static UpdateManager updater;
    public static File check = new File("denLibUpdateCheck.bin");
    public static final String build_number = "@BUILD@";
    public static File location;
    public static File mods;
    public static String mc = "No idea";
    public static IFMLLoadingPlugin DenEvents;

    @Override
    public String[] getASMTransformerClass() {
        try {
            Class c = FMLInjectionData.class;
            Field f = c.getDeclaredField("mccversion");
            f.setAccessible(true);
            Object o = f.get(null);
            mc = String.valueOf(o);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ArrayList<String> trans = new ArrayList();
        //trans.add(DepScanRequest.class.getName());
        if (ASMLogger.doLog) {
            trans.add("denoflionsx.denLib.CoreMod.ASM.ASMLogger");
        }
        trans.add("denoflionsx.denLib.CoreMod.ASM.SQL.SQLLibRequest");
        // Jumpstart DenEvents.
        try {
            Object e = DenEventsLib.class.newInstance();
            if (denLib.BukkitHelper.isBukkit()){
                throw new Exception("Bukkit detected. Bail out of DenEvents transformer!");
            }
            DenEvents = (IFMLLoadingPlugin) Class.forName("denoflionsx.DenEvents.DenEvents").newInstance();
            trans.addAll(Arrays.asList(DenEvents.getASMTransformerClass()));
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return trans.toArray(new String[trans.size()]);
    }

    public String[] getLibraryRequestClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return "denoflionsx.denLib.Mod.denLibMod";
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        location = (File) data.get("coremodLocation");
        mods = new File((File) data.get("mcLocation"), "/mods");
        if (location == null) {
            // Probably a deobf'd env. Check it.
            boolean obf = (Boolean) data.get("runtimeDeobfuscationEnabled");
            if (!obf) {
                print("Development env detected. Trying to figure out where the coremod is. Please stand by...");
                location = denLib.FileUtils.findMeInMods(mods, "denLib");
            }
        }
        updater = new UpdateManager();
        DenEvents.injectData(data);
    }

    public static void print(String msg) {
        FMLLog.info("[denLibCore]: " + msg);
    }
}
