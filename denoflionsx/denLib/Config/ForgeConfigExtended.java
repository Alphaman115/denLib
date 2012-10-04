package denoflionsx.denLib.Config;

import denoflionsx.denLib.denLib;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class ForgeConfigExtended extends Configuration {

    // Adds a fuel section to the Forge Configuration.
    public static final String CATEGORY_FUEL = "fuel";
    public static final String CATEGORY_PLUGIN = "plugin";

    public ForgeConfigExtended(File file) {
        super(file);
    }

    public void addDefault(String d) {
        String cat = Configuration.CATEGORY_GENERAL;
        // This if statement is for old config files.
        if (d.contains("#") || (d.contains("[") && d.contains("]"))) {
            return;
        }
        if (d.contains("ItemID")) {
            cat = Configuration.CATEGORY_ITEM;
        } else if (d.contains("BlockID")) {
            cat = Configuration.CATEGORY_BLOCK;
        } else if (d.contains("MJt") || d.contains("BurnTime")) {
            cat = CATEGORY_FUEL;
        }else if (d.toLowerCase().contains("plugin")){
            cat = CATEGORY_PLUGIN;
        }
        String[] split = d.split("=");
        Property p = this.getOrCreateProperty(split[0], cat, split[1]);
    }

    public String getOption(String key) {
        for (Map<String, Property> cat : this.categories.values()) {
            for (String s : denLib.dumpMapKeys(cat)) {
                if (s.equals(key)) {
                    return cat.get(key).value;
                }
            }
        }
        return null;
    }
    
    public ArrayList<String> dumpAllKeys(){
        ArrayList<String> keys = new ArrayList();
        for (Map<String, Property> cat : this.categories.values()){
            keys.addAll(denLib.dumpMapKeys(cat));
        }
        return keys;
    }
    
    public ArrayList<String> dumpAllValues(){
        ArrayList values = new ArrayList();
        for (Map<String, Property> cat : this.categories.values()){
            values.addAll(denLib.dumpMapValues(cat));
        }
        ArrayList<String> actualValues = new ArrayList();
        for (Object o : values){
            Property p = (Property)o;
            actualValues.add(p.value);
        }
        return actualValues;
    }
}
