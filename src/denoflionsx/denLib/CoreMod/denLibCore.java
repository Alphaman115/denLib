package denoflionsx.denLib.CoreMod;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import denoflionsx.denLib.CoreMod.ASM.ASMLogger;
import denoflionsx.denLib.CoreMod.Updater.UpdateManager;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

public class denLibCore implements IFMLLoadingPlugin {

    public static UpdateManager updater;
    public static File check = new File("denLibUpdateCheck.bin");
    public static final String build_number = "@BUILD@";
    public static File location;
    public static String mc = "No idea";
    public static boolean isBukkit = false;

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
        if (ASMLogger.doLog) {
            trans.add("denoflionsx.denLib.CoreMod.ASM.ASMLogger");
        }
        trans.add("denoflionsx.denLib.CoreMod.ASM.SQL.SQLLibRequest");
        return trans.toArray(new String[trans.size()]);
    }

    @Override
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
        updater = new UpdateManager();
        try {
            if (Class.forName("org.bukkit.craftbukkit.Main", false, getClass().getClassLoader()) != null) {
                isBukkit = true;
            }
        } catch (Throwable t) {
        }
    }

    public static void print(String msg) {
        FMLLog.info("[denLibCore]: " + msg);
    }
}
