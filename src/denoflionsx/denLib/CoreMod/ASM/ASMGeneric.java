package denoflionsx.denLib.CoreMod.ASM;

import denoflionsx.denLib.Lib.denLib;
import java.io.InputStream;
import java.lang.reflect.Method;
import net.minecraft.launchwrapper.IClassTransformer;

public class ASMGeneric implements IClassTransformer {
    
    private InputStream s;
    private String name;
    private String dumpFile;
    
    public ASMGeneric() {
        this("", "", null);
    }
    
    public ASMGeneric(String name, String dumpFile, InputStream s) {
        this.s = s;
        this.name = name;
        this.dumpFile = dumpFile;
    }
    
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name.equals(this.name)) {
            String hash = denLib.StringUtils.getHash(bytes);
            if (hash.equals(denLib.StringUtils.readInputStream(s)[0])) {
                byte[] b2 = null;
                try {
                    Class c = Class.forName(this.dumpFile);
                    Method m = c.getDeclaredMethod("dump", new Class[0]);
                    Object o = m.invoke(null, new Object[0]);
                    b2 = (byte[]) o;
                } catch (Throwable t) {
                }
                bytes = b2;
                System.out.println("[@NAME@]: Class " + this.name + " patched and verified!");
            } else {
                try {
                    throw new HashVerificationError(name, hash);
                } catch (HashVerificationError e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
        return bytes;
    }
    
    public static class HashVerificationError extends Exception {
        
        private String c;
        private String hash;
        
        public HashVerificationError(String c, String hash) {
            this.c = c;
            this.hash = hash;
        }
        
        @Override
        public String getMessage() {
            return "[@NAME@]: Failed to verify class: " + this.c + "! Found hash: " + this.hash;
        }
    }
}
