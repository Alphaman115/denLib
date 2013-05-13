package denoflionsx.denLib.Lib;

import com.google.common.collect.BiMap;
import com.google.common.io.ByteStreams;
import denoflionsx.denLib.Mod.denLibMod;
import java.io.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;
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

        public static String findStringInNestedClass(Class first, String name) {
            try {
                for (Field f : first.getDeclaredFields()) {
                    if (f.getName().equals(name)) {
                        return f.get(null).toString();
                    }
                }
                for (Class c : first.getDeclaredClasses()) {
                    for (Field f : c.getDeclaredFields()) {
                        if (f.getName().equals(name)) {
                            return f.get(null).toString();
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return denLib.StringUtils.readError;
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
                Object o = ois.readObject();
                ois.close();
                return o;
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

        public static String[] readFileContentsAutomated(File configDir, String name) {
            ClassLoader c = Thread.currentThread().getContextClassLoader();
            InputStream i = null;
            if (configDir != null) {
                File f1 = new File(configDir.getAbsolutePath() + "/" + name);
                if (f1.exists()) {
                    try {
                        i = new FileInputStream(f1);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        i = c.getResourceAsStream(name);
                    } catch (Exception ex) {
                        denLibMod.Proxy.warning("Error reading file " + name + "!");
                    }
                }
            }
            if (i != null) {
                String f = denLib.StringUtils.scanFileContents(i);
                String[] p = denLib.StringUtils.splitByNewLine(f);
                return p;
            } else {
                return new String[]{readError};
            }
        }

        public static String[] readFileContentsAutomated(File configDir, String name, Object instance) {
            InputStream i = null;
            if (configDir != null) {
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
            }
            if (i != null) {
                String f = denLib.StringUtils.scanFileContents(i);
                String[] p = denLib.StringUtils.splitByNewLine(f);
                return p;
            } else {
                return new String[]{readError};
            }
        }
    }

    public static class NetUtils {

        public static String[] readFileFromURL(String URL) {
            ArrayList<String> l = new ArrayList();
            try {
                // Create a URL for the desired page
                URL url = new URL(URL);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                while ((str = in.readLine()) != null) {
                    l.add(str);
                }
                in.close();
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }
            return l.toArray(new String[l.size()]);
        }

        public static URL newUrlFromString(String s) {
            try {
                return new URL(s);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static File readBinaryFromNet(URL u, File saveTo) {
            try {
                URLConnection uc = u.openConnection();
                String contentType = uc.getContentType();
                int contentLength = uc.getContentLength();
                if (contentType.startsWith("text/") || contentLength == -1) {
                    throw new IOException("This is not a binary file.");
                }
                InputStream raw = uc.getInputStream();
                InputStream in = new BufferedInputStream(raw);
                byte[] data = new byte[contentLength];
                int bytesRead = 0;
                int offset = 0;
                while (offset < contentLength) {
                    bytesRead = in.read(data, offset, data.length - offset);
                    if (bytesRead == -1) {
                        break;
                    }
                    offset += bytesRead;
                }
                in.close();

                if (offset != contentLength) {
                    throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
                }
                FileOutputStream out = new FileOutputStream(saveTo.getAbsolutePath());
                out.write(data);
                out.flush();
                out.close();
                return saveTo;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    public static class LiquidStackUtils {

        public static LiquidStack getNewStackCapacity(LiquidStack stack, int capacity) {
            LiquidStack t = stack.copy();
            t.amount = capacity;
            return t;
        }
    }

    public static class FileUtils {

        public static void saveBiMapToFile(BiMap map, File f) {
            saveObjectToFile(map, f);
        }

        public static BiMap readBiMapFromFile(File f) {
            return (BiMap) readObjectFromFile(f);
        }

        private static void saveObjectToFile(Object o, File f) {
            try {
                byte[] array = turnObjectToByteArray(o);
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(array);
                fos.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private static byte[] turnObjectToByteArray(Object o) {
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                ObjectOutputStream b1 = new ObjectOutputStream(b);
                b1.writeObject(o);
                byte[] array = b.toByteArray();
                b.close();
                b1.close();
                return array;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static Object readObjectFromByteArray(byte[] bytes) {
            try {
                ByteArrayInputStream b2 = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(b2);
                Object o = ois.readObject();
                ois.close();
                return o;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        private static Object readObjectFromFile(File f) {
            try {
                FileInputStream fis = new FileInputStream(f.getAbsolutePath());
                byte[] b = ByteStreams.toByteArray(fis);
                fis.close();
                return readObjectFromByteArray(b);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public static byte[] createSha1(File file) throws Exception {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            InputStream fis = new FileInputStream(file);
            int n = 0;
            byte[] buffer = new byte[8192];
            while (n != -1) {
                n = fis.read(buffer);
                if (n > 0) {
                    digest.update(buffer, 0, n);
                }
            }
            fis.close();
            return digest.digest();
        }
    }

    public static class RandomUtils {

        public static void throwCustomException(String why) {
            try {
                throw new Exception(why);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
