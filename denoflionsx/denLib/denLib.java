package denoflionsx.denLib;

import cpw.mods.fml.common.Loader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.*;
import net.minecraft.src.*;

public class denLib {
    
    public static int buildnumber = 4;

    public static boolean detect(String mod) {
        return LOADER.FML.isModLoaded(mod);
    }

    public static HashMap flipHashMap(HashMap map) {
        HashMap metaMapReversed = new HashMap();
        Iterator i = map.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry pairs = (Map.Entry) i.next();
            metaMapReversed.put(pairs.getValue(), pairs.getKey());
        }
        return metaMapReversed;
    }

    // These get functions are for compatibility for code written before
    // the reflection helper class.
    public static Item getItem(String mod, String name) {
        return ReflectionHelper.getItem(mod, name);
    }

    public static ItemStack getItemStack(String mod, String name) {
        return ReflectionHelper.getItemStack(mod, name);
    }

    public static String getItemDisplayName(ItemStack item) {
        return item.getItem().getItemNameIS(item);
    }

    public static String removeDotItemFromName(String name) {
        String t;
        try{
            t = name.substring(5);
        }catch(Exception ex){
            return name;
        }
        return t;
    }

    public static Block getBlock(String mod, String name) {
        return ReflectionHelper.getBlock(mod, name);
    }

    public static void print(String msg) {

        System.out.println(msg);

    }

    public static boolean convertToBoolean(String bool) {
        bool = bool.toLowerCase();
        if (bool.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public static String toLowerCaseNoSpaces(String s) {
        return toNoSpaces(s).toLowerCase();
    }
    
    public static ArrayList<String> dumpPropertiesKeys(Properties p){
        Iterator i = p.entrySet().iterator();
        ArrayList<String> values = new ArrayList();
        while (i.hasNext()) {
            Map.Entry pairs = (Map.Entry) i.next();
            values.add(pairs.getKey().toString());
        }
        return values;
    }
    
    public static ArrayList<String> dumpMapKeys(Map map){
        Iterator i = map.entrySet().iterator();
        ArrayList<String> keys = new ArrayList();
        while (i.hasNext()){
            Map.Entry pairs = (Map.Entry) i.next();
            keys.add(pairs.getKey().toString());
        }
        return keys;
    }
    
    public static ArrayList<Object> dumpMapValues(Map map){
        ArrayList<Object> values = new ArrayList();
        for (Object o : map.values()){
            values.add(o);
        }
        return values;
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

    public static String getTileEntityName(TileEntity t) {
        return t.getClass().toString();
    }

    public static String toNoSpaces(String s) {
        return s.replaceAll("\\s", "");
    }

    public static String addName(String name) {
        FMLWrapper.MODE.FML.addName(name);
        return "item." + denLib.toLowerCaseNoSpaces(name);
    }

    public static void classSnoop(String param1) {
        ReflectionHelper.classSnoop(param1);
    }

    public static enum LOADER {

        MODLOADER(),
        FML();
        private String mode;

        private LOADER() {
            this.mode = this.toString();
        }

        public boolean isModLoaded(String mod) {
            if (this.mode.equals("MODLOADER")) {
                return ModLoader.isModLoaded(mod);
            } else if (this.mode.equals("FML")) {
                return Loader.isModLoaded(mod);
            }
            return false;
        }
    }

    public static class ReflectionHelper {

        // DO NOT USE THIS. IT CRASHES NEI!
        public static void killBlock(String theClass, String theBlock) {
            Block b = getBlock(theClass,theBlock);
            int id = b.blockID;
            Block.blocksList[id] = null;
        }

        public static void setIntFromTileEntity(String theClass, String theField, TileEntity t, int set) {
            try {
                Class c = Class.forName(theClass);
                Field f = c.getField(theField);
                f.set(t, set);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public static Class getClass(String name) {
            Class c = null;
            try {
                c = Class.forName(name);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return c;
        }

        public static Field getField(String field, Class c) {
            Field f = null;
            try {
                f = c.getDeclaredField(field);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return f;
        }

        public static int getStaticInt(String theClass, String theField) {
            try {
                Class c = Class.forName(theClass);
                Field f = c.getField(theField);
                int i = f.getInt(null);
                return i;
            } catch (Exception ex) {
                ex.printStackTrace();
                return -1;
            }
        }

        public static void setStaticInt(String theClass, String theField, int set) {
            try {
                Class c = Class.forName(theClass);
                Field f = c.getField(theField);
                f.setInt(null, set);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public static int getIntFromTileEntity(String theClass, String theField, TileEntity t) {
            try {
                Class c = Class.forName(theClass);
                Field f = c.getField(theField);
                int i = f.getInt(t);
                return i;
            } catch (Exception ex) {
                ex.printStackTrace();
                return -1;
            }
        }

        // This stuff got moved from the base denLib class.
        // There are still shell functions up there so shit doesn't break.
        public static Item getItem(String mod, String name) {
            return (Item) getWithReflection(mod, name);
        }

        public static ItemStack getNewItemStack(String mod, String name) {
            return new ItemStack(getItem(mod, name));
        }

        public static ItemStack getNewItemStackBlock(String mod, String name) {
            return new ItemStack(getBlock(mod, name));
        }

        public static Object getWithReflection(String mod, String name) {
            Object o = null;
            try {
                o = Class.forName(mod).getField(name).get(null);
            } catch (Exception ex) {
                return null;
            }
            return o;
        }

        public static ItemStack getItemStack(String mod, String name) {
            return (ItemStack) getWithReflection(mod, name);
        }

        public static Block getBlock(String mod, String name) {
            return (Block) getWithReflection(mod, name);
        }

        public static void classSnoop(String param1) {
            try {
                Class c = Class.forName(param1);
                print("--------------------");
                print("Class Snoop Engaged");
                print("Class: " + param1);
                print("--------------------");
                Method[] m = c.getDeclaredMethods();
                Field[] f = c.getDeclaredFields();
                print("Defined Methods");
                print("--------------------");
                for (int i = 0; i < m.length; i++) {
                    print(m[i].getName());
                }
                print("--------------------");
                print("Defined Fields");
                print("--------------------");
                for (int i = 0; i < f.length; i++) {
                    print(f[i].getName());
                }
                print("--------------------");
                print("End of Snoop");
                print("--------------------");
            } catch (Exception ex) {
            }

        }
    }
}
