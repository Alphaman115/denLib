package denoflionsx.denLib.Config;

import java.io.File;
import java.util.ArrayList;
import denoflionsx.denLib.denLib;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Config_JavaProperties {

    public static String ConfigDir;
    protected String ConfigFile = "";
    public static boolean verbose = false;
    public static final boolean debug = false;
    public static MODE mode = MODE.INI;
    public Properties Options;
    public Properties Defaults;

    public Config_JavaProperties(String c) {
        if (mode.equals(MODE.XML)){
            String[] split = c.split(".");
            for (String s : split){
                denLib.print(s);
            }
        }
        this.ConfigFile = ConfigDir + c;
        Options = new Properties();
        Defaults = new Properties();
    }

    public String getConfigFile() {
        return ConfigFile;
    }

    public void setConfigDir(String dir) {
        ConfigDir = dir;
    }

    public void readFile() {
        try {
            if (mode.equals(MODE.INI)) {
                Options.load(new FileInputStream(this.ConfigFile));
            }else if (mode.equals(MODE.XML)){
                Options.loadFromXML(new FileInputStream(this.ConfigFile));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getOptionInt(String key) {
        String op = this.getOption(key);
        if (op.equals("")) {
            op = "0";
        }
        return Integer.valueOf(op);
    }

    public float getOptionFloat(String key) {
        String op = this.getOption(key);
        if (op.equals("")) {
            op = "0";
        }
        return Float.valueOf(op);
    }

    public boolean getOptionBool(String key) {
        String value = this.getOption(key);
        if (denLib.toLowerCaseNoSpaces(value).equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public void setOption(String key, String param) {
        Options.put(key, param);
    }

    public String getOption(String key) {
        String op = "";
        if (Options.get(key) == null) {
            for (String s : denLib.dumpPropertiesKeys(Defaults)) {
                if (s.equals(key)) {
                    Options.setProperty(key, Defaults.getProperty(key));
                }
            }
        }
        if (Options.get(key) != null) {
            op = Options.get(key).toString();
            //denLib.print(key + " = " + op);
        }
        return op;
    }

    public String getOptionNoUpdate(String key) {
        if (Options.get(key) == null) {
            return null;
        }
        return Options.get(key).toString();
    }

    public void addDefault(String d) {
        if (d.contains("#") || (d.contains("[") && d.contains("]"))) {
            return;
        }
        String[] split = d.split("=");
        String key = split[0];
        String value = split[1];
        Defaults.setProperty(key, value);
    }

    public boolean doesConfigExist() {
        return new File(this.ConfigFile).exists();
    }

    public void writeConfig() {
        try {
            if (!doesConfigExist()) {
                if (mode.equals(MODE.INI)) {
                    Defaults.store(new FileOutputStream(this.ConfigFile), null);
                } else if (mode.equals(MODE.XML)) {
                    Defaults.storeToXML(new FileOutputStream(this.ConfigFile), null);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteConfig() {
        if (new File(this.ConfigFile).exists()) {
            new File(this.ConfigFile).delete();
        }
    }

    public ArrayList<String> dumpValues() {
        // This dumps all values without the keys.
        // Good for uses where the key is arbitrary.
        ArrayList<String> values = new ArrayList();
        for (Object o : Options.values()) {
            String v = o.toString();
            values.add(v);
        }
        return values;
    }

    public ArrayList<String> dumpKeys() {
        return denLib.dumpPropertiesKeys(Options);
    }

    public enum MODE {

        INI,
        XML;
    }
}
