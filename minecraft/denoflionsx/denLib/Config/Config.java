package denoflionsx.denLib.Config;

import java.io.File;
import java.util.ArrayList;
import denoflionsx.denLib.denLib;

public class Config {

    public static String ConfigDir;
    protected String ConfigFile = "";
    public static boolean verbose = false;
    public static final boolean debug = false;
    public ForgeConfigExtended Options;
    public ForgeConfigExtended Defaults;

    public Config(String c) {
        this.ConfigFile = ConfigDir + c;
        Options = new ForgeConfigExtended(new File(this.ConfigFile));
        Defaults = new ForgeConfigExtended(new File(this.ConfigFile));
    }

    public String getConfigFile() {
        return ConfigFile;
    }

    public void setConfigDir(String dir) {
        ConfigDir = dir;
    }

    public void readFile() {
        try {

            Options.load();

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
    }

    public String getOption(String key) {
        String op = "";
        if (Options.getOption(key) == null) {
            for (String s : Defaults.dumpAllKeys()) {
                if (s.equals(key)) {
                    Options.addDefault(key + "=" + Defaults.getOption(key));
                }
            }
        }
        if (Options.getOption(key) != null) {
            op = Options.getOption(key).toString();
            //denLib.print(key + " = " + op);
        }
        return op;
    }

    public String getOptionNoUpdate(String key) {
        if (Options.getOption(key) == null) {
            return null;
        }
        return Options.getOption(key).toString();
    }

    public void addDefault(String d) {
        Defaults.addDefault(d);
    }

    public boolean doesConfigExist() {
        return new File(this.ConfigFile).exists();
    }

    public void writeConfig() {
        try {
            if (!doesConfigExist()) {
                Defaults.save();
            }else{
                this.deleteConfig();
                Options.save();
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
        return Options.dumpAllValues();
    }

    public ArrayList<String> dumpKeys() {
        return Options.dumpAllKeys();
    }
}
