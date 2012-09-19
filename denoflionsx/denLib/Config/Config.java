package denoflionsx.denLib.Config;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import denoflionsx.denLib.denLib;

public class Config {

    public HashMap<String, String> Options = new HashMap();
    protected ArrayList<String> defaults = new ArrayList();
    protected ArrayList<String> rawFile = new ArrayList();
    public static String ConfigDir;
    protected String ConfigFile = "";
    protected boolean isStream = false;
    protected InputStream is = null;
    public static boolean verbose = false;

    public Config(String c) {
        this.ConfigFile = ConfigDir + c;
    }

    public Config(InputStream is) {
        this.is = is;
        this.isStream = true;
        this.readFile();
    }

    public String getConfigFile() {
        return ConfigFile;
    }

    public void setConfigDir(String dir) {
        this.ConfigDir = dir;
    }

    public void readFile() {
        if (!this.isStream) {
            ArrayList read = FileRead.Read(new String[]{this.ConfigFile});
            Iterator<String> al = read.iterator();
            while (al.hasNext()) {
                String next = al.next();
                parseLine(next);

            }
        } else {
            // Input stream is for internal config files.
            // These use ; line endings.
            String str = convertStreamToString(this.is);
            // Strip newline characters.
            str = str.replace("\n", "").replace("\r", "");
            String[] b = str.split(";");
            for (String line : b) {
                parseLine(line);
            }
        }
    }

    public int getOptionInt(String key) {
        String op = this.getOption(key);
        if (op.equals("")) {
            op = "0";
        }
        return Integer.valueOf(op);
    }
    
    public float getOptionFloat(String key){
        String op = this.getOption(key);
        if (op.equals("")){
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
        String op = Options.get(key);
        if (op != null) {
            return op;
        } else {
            if (!this.isStream) {
                denLib.print("Attempting to update config file...");
                for (String s : this.defaults) {
                    if (s.contains(key)) {
                        parseLine(s);
                        op = Options.get(key);
                        new File(this.ConfigFile).delete();
                        FileWrite.write(this.ConfigFile, this.rawFile);
                        return op;
                    }
                }
            }
        }
        return "";
    }

    public String getOptionNoUpdate(String key) {
        return Options.get(key);
    }

    public void parseLine(String line) {
        char s = "=".charAt(0);
        rawFile.add(line);
        if (!line.substring(0, 1).equals("#")) {
            // # is a comment line.
            if (!line.substring(0, 1).equals("[")) {
                // [] (Brackets) are for section titles.
                String read[] = line.split("=");
                if (read.length == 2) {
                    setOption(read[0], read[1]);
                }
            }
        }

    }

    public void addDefault(String d) {
        if (this.isStream) {
            return;
        }
        defaults.add(d);

    }

    public void writeConfig() {
        if (this.isStream) {
            return;
        }
        if (!new File(this.ConfigFile).exists()) {
            new File(ConfigDir).mkdirs();
            FileWrite.write(this.ConfigFile, defaults);
            denLib.print("Wrote config file: " + this.ConfigFile);
        }
    }

    public void deleteConfig() {
        if (this.isStream) {
            return;
        }
        if (new File(this.ConfigFile).exists()) {
            new File(this.ConfigFile).delete();
        }
    }

    public ArrayList<String> dumpValues() {
        // This dumps all values without the keys.
        // Good for uses where the key is arbitrary.
        Iterator i = this.Options.entrySet().iterator();
        ArrayList<String> values = new ArrayList();
        while (i.hasNext()) {
            Map.Entry pairs = (Map.Entry) i.next();
            values.add(pairs.getValue().toString());
        }
        return values;
    }

    public ArrayList<String> dumpKeys() {
        Iterator i = this.Options.entrySet().iterator();
        ArrayList<String> values = new ArrayList();
        while (i.hasNext()) {
            Map.Entry pairs = (Map.Entry) i.next();
            values.add(pairs.getKey().toString());
        }
        return values;
    }

    public static String convertStreamToString(java.io.InputStream is) {
        try {
            return new java.util.Scanner(is).useDelimiter("\\A").next();
        } catch (java.util.NoSuchElementException e) {
            return "";
        }
    }
}
