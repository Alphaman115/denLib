package denoflionsx.denLib.CoreMod.ASM;

import denoflionsx.denLib.Mod.Logger.DenFormat;
import net.minecraft.launchwrapper.IClassTransformer;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class ASMLogger implements IClassTransformer {

    private static final Logger LOG = Logger.getLogger(ASMLogger.class.getName());
    public static final boolean doLog = false;

    public ASMLogger() {
        if (doLog) {
            try {
                DenFormat f = new DenFormat();
                Handler handler = new FileHandler("denLib_obfData.log");
                handler.setFormatter(f);
                LOG.addHandler(handler);
            } catch (Throwable t) {
            }
        }
    }

    @Override
    public byte[] transform(String string, String string1, byte[] bytes) {
        if (string.equals(string1) || !doLog) {
            return bytes;
        }
        LOG.info(string.concat(" | ").concat(string1));
        return bytes;
    }
}
