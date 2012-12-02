package denoflionsx.denLib.CoreMod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import java.io.File;
import java.util.Map;

public class denLibCoreMod implements IFMLLoadingPlugin {

    public static final String clazzpath = "denoflionsx.denLib.CoreMod.Transformers.";
    public static final String Leaves = clazzpath + "TransformerBlockLeave";
    public static final String[] ASM = new String[]{Leaves};
    public static final String[] Libs = new String[]{"denoflionsx.denLib.CoreMod.Library.zip4j"};
    public static boolean ASMReg = false;
    public static boolean LibReg = false;
    public static File location;
    public static final String LeavesMapping = "akm";

    @Override
    public String[] getASMTransformerClass() {
        if (!ASMReg) {
            for (String s : ASM) {
                try {
                    Class c = Class.forName(s);
                    if (c != null) {
                        System.out.println("[denLib]: " + "Registered Transformer " + s.substring(clazzpath.length()));
                    }
                } catch (Exception ex) {
                    System.out.println("[denLib]: " + "Error registering Transformer " + s.substring(clazzpath.length()));
                    return null;
                }
            }
            ASMReg = true;
        }
        return ASM;
    }

    @Override
    public String[] getLibraryRequestClass() {
        if (!LibReg){
            for (String s : Libs){
                try{
                    Class c = Class.forName(s);
                    if (c != null){
                        System.out.println("[denLib]: " + "Library registered succesfully!");
                    }
                }catch(Exception ex){
                    System.out.println("[denLib]: " + "Library failed!");
                    return null;
                }
            }
            LibReg = true;
        }
        return Libs;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        location = (File) data.get("coremodLocation");
    }
}
