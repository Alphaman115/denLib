package denoflionsx.denLib.Lib;

import denoflionsx.denLib.Mod.denLibMod;
import java.io.*;
import java.lang.reflect.Field;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.liquids.LiquidStack;

public class denLib {

    public static class ReflectionHelper {

        public static Object getStaticField(Class c, String f) {
            try {
                return c.getDeclaredField(f).get(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static Object getStaticField(Field f) {
            try {
                return f.get(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static void setStaticField(Field f, Object value) {
            try {
                f.set(null, value);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public static Field getStaticField(String c, String f) {
            try {
                return Class.forName(c).getField(f);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    public static class NBTUtils {

        public static void writeNBTToFile(File file, NBTTagCompound tag) {
            try {
                FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                DataOutputStream dos = new DataOutputStream(fos);
                NBTTagCompound.writeNamedTag(tag, dos);
                dos.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public static NBTTagCompound readNBTFromFile(File file) {
            try {
                FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                DataInputStream dis = new DataInputStream(fis);
                NBTTagCompound read = (NBTTagCompound) NBTTagCompound.readNamedTag(dis);
                return read;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static void saveObjectToNBTFile(File file, Object o) {
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                ObjectOutputStream b1 = new ObjectOutputStream(b);
                b1.writeObject(o);
                byte[] by = b.toByteArray();
                NBTTagCompound save = new NBTTagCompound();
                save.setByteArray("object", by);
                writeNBTToFile(file, save);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public static Object restoreObjectFromNBTFile(File file) {
            try {
                NBTTagCompound read = readNBTFromFile(file);
                ByteArrayInputStream b2 = new ByteArrayInputStream(read.getByteArray("object"));
                ObjectInputStream ois = new ObjectInputStream(b2);
                return ois.readObject();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    public static class StringUtils {

        public static final String readError = "Error";
        
        public static String removeSpaces(String s) {
            return s.replaceAll("\\s", "");
        }

        public static String[] splitByNewLine(String s) {
            return s.split("[\r\n]+");
        }

        public static String scanFileContents(InputStream stream) {
            java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }

        public static String[] readFileContentsAutomated(File configDir, String name, Object instance) {
            InputStream i = null;
            File f1 = new File(configDir.getAbsolutePath() + "/" + name);
            if (f1.exists()) {
                try {
                    i = new FileInputStream(f1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    i = instance.getClass().getResourceAsStream(name);
                } catch (Exception ex) {
                    denLibMod.Proxy.warning("Error reading file " + name + " from instance of " + instance.getClass().getName() + "!");
                }
            }
            if (i != null) {
                String f = denLib.StringUtils.scanFileContents(i);
                String[] p = denLib.StringUtils.splitByNewLine(f);
                return p;
            }else{
                return new String[]{readError};
            }
        }
    }

    public static class LiquidStackUtils {

        public static LiquidStack getNewStackCapacity(LiquidStack stack, int capacity) {
            LiquidStack t = stack.copy();
            t.amount = capacity;
            return t;
        }
    }
}
