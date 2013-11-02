package denoflionsx.denLib.Lib;

import com.google.common.collect.BiMap;
import com.google.common.io.ByteStreams;
import denoflionsx.denLib.CoreMod.denLibCore;
import denoflionsx.denLib.Mod.denLibMod;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class denLib {

    public static boolean debug = false;

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
        private static final String HEXES = "0123456789abcdef";

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
                return readInputStream(i);
            } else {
                return new String[]{readError};
            }
        }

        public static String[] readInputStream(InputStream stream) {
            String f = denLib.StringUtils.scanFileContents(stream);
            String[] p = denLib.StringUtils.splitByNewLine(f);
            return p;
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
                return readInputStream(i);
            } else {
                return new String[]{readError};
            }
        }

        public static String Hash(String tag) {
            byte[] bytes = tag.getBytes();
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] md5 = md.digest(bytes);
                String hash = "";
                for (byte b : md5) {
                    hash = hash + String.valueOf(b);
                }
                hash = hash.replace("-", "");
                tag = hash;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return tag;
        }

        public static String Hash(String[] input) {
            String i = "";
            for (String s : input) {
                i += s;
            }
            return Hash(i);
        }

        public static String getHash(byte[] bytes) {
            String s = "";
            for (byte b : bytes) {
                s += String.valueOf(b);
            }
            return denLib.StringUtils.Hash(s);
        }

        public static String hexify(byte[] chksum) {
            final StringBuilder hex = new StringBuilder(2 * chksum.length);
            for (final byte b : chksum) {
                hex.append(HEXES.charAt((b & 0xF0) >> 4))
                        .append(HEXES.charAt((b & 0x0F)));
            }
            return hex.toString();
        }

        public static byte[] createSha1(File file) throws Exception {
            InputStream fis = new FileInputStream(file);
            return createSha1(fis);
        }

        public static byte[] createSha1(InputStream fis) throws Exception {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
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
            } catch (Throwable t) {
                return new String[]{"404"};
            }
            try {
                if (URL.contains("dropbox")) {
                    String t = l.get(0);
                    if (denLib.StringUtils.removeSpaces(t).substring(0, 2).equals("PK")) {
                        return new String[]{"404"};
                    }
                }
            } catch (Throwable t) {
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

        public static FluidStack getNewStackCapacity(FluidStack stack, int capacity) {
            FluidStack t = stack.copy();
            t.amount = capacity;
            return t;
        }
    }

    public static class FileUtils {

        public static ArrayList<String> getClassNamesInJar(File source) {
            ArrayList<String> classes = new ArrayList();
            try {
                JarFile jarFile = new JarFile(source);
                Enumeration e = jarFile.entries();
                while (e.hasMoreElements()) {
                    JarEntry je = (JarEntry) e.nextElement();
                    if (je.isDirectory() || !je.getName().endsWith(".class")) {
                        continue;
                    }
                    // -6 because of .class
                    String className = je.getName().substring(0, je.getName().length() - 6);
                    className = className.replace('/', '.');
                    if (debug) {
                        denLibMod.Proxy.print(className);
                    }
                    classes.add(className);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return classes;
        }

        public static ArrayList<Field> findFieldsInJarWithAnnotation(File source, Class<? extends Annotation> annotation) {
            ArrayList<Field> fields = new ArrayList();
            for (String s : denLib.FileUtils.getClassNamesInJar(source)) {
                try {
                    Class c = Class.forName(s);
                    for (Field f : c.getDeclaredFields()) {
                        if (f.isAnnotationPresent(annotation)) {
                            fields.add(f);
                        }
                    }
                } catch (Throwable t) {
                    continue;
                }
            }
            return fields;
        }

        public static ArrayList<Object> getClassesInJar(File source, Class<?> target) {
            ArrayList<Object> classes = new ArrayList();
            try {
                for (String className : denLib.FileUtils.getClassNamesInJar(source)) {
                    Class c = null;
                    try {
                        c = Class.forName(className);
                    } catch (Throwable ex) {
                        // This is needed for server side. Some client only classes won't exist.
                        denLibMod.Proxy.print("Skipping " + className + " due to SideOnly = CLIENT.");
                        continue;
                    }
                    if (c.isInterface()) {
                        continue;
                    }
                    for (Class<?> q : c.getInterfaces()) {
                        if (q.getName().equals(target.getName())) {
                            classes.add(c.newInstance());
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return classes;
        }

        public static void saveBiMapToFile(BiMap map, File f) {
            saveObjectToFile(map, f);
        }

        public static BiMap readBiMapFromFile(File f) {
            return (BiMap) readObjectFromFile(f);
        }

        public static void saveObjectToFile(Object o, File f) {
            try {
                byte[] array = turnObjectToByteArray(o);
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(array);
                fos.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public static byte[] turnObjectToByteArray(Object o) {
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
            InputStream fis = new FileInputStream(file);
            return createSha1(fis);
        }

        public static byte[] createSha1(InputStream fis) throws Exception {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
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

        public static void unzip(File zip, File save) {
            denLibCore.print("Unzipping " + zip.getName());
            try {
                OutputStream out = new FileOutputStream(save);
                FileInputStream fin = new FileInputStream(zip);
                BufferedInputStream bin = new BufferedInputStream(fin);
                ZipInputStream zin = new ZipInputStream(bin);
                ZipEntry ze = null;
                while ((ze = zin.getNextEntry()) != null) {
                    byte[] buffer = new byte[8192];
                    int len;
                    while ((len = zin.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                    out.close();
                    break;
                }
            } catch (Exception ex) {
            }
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

    public static class ASMHelper {

        public static void dumpClass(File outDir, byte[] bytes, String name) {
            try {
                DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(outDir, name)));
                out.write(bytes);
                out.flush();
                out.close();
            } catch (Throwable t) {
            }
        }

        // These methods shamelessly stolen from CodeChicken. You rock at ASM dude.
        public static ClassNode createClassNode(byte[] bytes) {
            return createClassNode(bytes, 0);
        }

        public static ClassNode createClassNode(byte[] bytes, int flags) {
            ClassNode cnode = new ClassNode();
            ClassReader reader = new ClassReader(bytes);
            reader.accept(cnode, flags);
            return cnode;
        }

        public static byte[] createBytes(ClassNode cnode, int flags) {
            ClassWriter cw = new ClassWriter(flags);
            cnode.accept(cw);
            return cw.toByteArray();
        }
    }

    public static class SQLHelper {

        public static Connection createDB(String dbName) {
            try {
                return DriverManager.getConnection("jdbc:sqlite:" + dbName);
            } catch (Throwable t) {
            }
            return null;
        }

        private static File dbFromTableInfo(String[] tableInfo) {
            return new File(tableInfo[0]);
        }

        private static Statement makeConnection(String[] tableInfo) {
            try {
                File db = dbFromTableInfo(tableInfo);
                Connection connection = createDB(db.getAbsolutePath());
                Statement statement = connection.createStatement();
                return statement;
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return null;
        }

        private static Connection makeConnection2(String[] tableInfo) {
            try {
                File db = dbFromTableInfo(tableInfo);
                Connection connection = createDB(db.getAbsolutePath());
                return connection;
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return null;
        }

        public static int getIntFromDB(String[] tableInfo, String targetFieldValue) {
            try {
                String table = tableInfo[1];
                int rvalue = -1;
                Statement statement = makeConnection(tableInfo);
                ResultSet rs = statement.executeQuery("select * from " + table);
                while (rs.next()) {
                    if (rs.getString(1).equals(targetFieldValue)) {
                        rvalue = rs.getInt(2);
                    }
                }
                return rvalue;
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return -1;
        }

        public static void putIntInDB(String[] tableInfo, Object[] input) {
            try {
                String table = tableInfo[1];
                Connection connection = makeConnection2(tableInfo);
                Statement statement = connection.createStatement();
                statement.executeUpdate("create table if not exists " + table + " (KEY string, VALUE integer);");
                PreparedStatement prep = connection.prepareStatement(
                        "insert into " + table + " values (?, ?);");
                prep.setString(1, input[0].toString());
                prep.setInt(2, Integer.valueOf(input[1].toString()));
                prep.addBatch();
                connection.setAutoCommit(false);
                prep.executeBatch();
                connection.setAutoCommit(true);
                connection.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        public static void putStringInDB(String[] tableInfo, Object[] input) {
            try {
                String table = tableInfo[1];
                Connection connection = makeConnection2(tableInfo);
                Statement statement = connection.createStatement();
                statement.executeUpdate("create table if not exists " + table + " (KEY string, VALUE string);");
                PreparedStatement prep = connection.prepareStatement(
                        "insert into " + table + " values (?, ?);");
                prep.setString(1, input[0].toString());
                prep.setString(2, input[1].toString());
                prep.addBatch();
                connection.setAutoCommit(false);
                prep.executeBatch();
                connection.setAutoCommit(true);
                connection.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        public static String getStringInDB(String[] tableInfo, String targetFieldValue) {
            try {
                String rvalue = null;
                Connection connection = makeConnection2(tableInfo);
                Statement statement = connection.createStatement();
                String table = tableInfo[1];
                ResultSet rs = statement.executeQuery("select * from " + table);
                while (rs.next()) {
                    if (rs.getString(1).equals(targetFieldValue)) {
                        rvalue = rs.getString(2);
                    }
                }
                return rvalue;
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return null;
        }
    }
}
