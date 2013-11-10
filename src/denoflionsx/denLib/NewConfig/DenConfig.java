package denoflionsx.denLib.NewConfig;

import denoflionsx.denLib.Lib.denLib;
import denoflionsx.denLib.Mod.denLibMod;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class DenConfig {

    public Configuration setup(File origin, File configFile) {
        return setup(origin, configFile, null);
    }

    public Configuration setup(File origin, File configFile, String modID) {
        Configuration config = new Configuration(configFile);
        try {
            ArrayList<Field> fields = denLib.FileUtils.findFieldsInJarWithAnnotation(origin, ConfigField.class);
            for (Field f : fields) {
                f.setAccessible(true);
                ConfigField anno = f.getAnnotation(ConfigField.class);
                denLibMod.log("Found ConfigField " + f.getName());
                getAndSet(anno, f, config);
            }
        } catch (Throwable t) {
        }
        return config;
    }

    private void getAndSet(ConfigField annotation, Field f, Configuration config) {
        Object o = null;
        Property p = null;
        try {
            o = f.get(null);
            if (f.getType().equals(int.class)) {
                p = config.get(annotation.category(), f.getName(), (Integer) o);
                f.set(null, p.getInt());
            } else if (f.getType().equals(String.class)) {
                p = config.get(annotation.category(), f.getName(), (String) o);
                f.set(null, p.getString());
            } else if (f.getType().equals(float.class)) {
                p = config.get(annotation.category(), f.getName(), String.valueOf((Float) o));
                f.set(null, Float.valueOf(p.getString()));
            } else if (f.getType().equals(String[].class)) {
                p = config.get(annotation.category(), f.getName(), (String[]) o);
                f.set(null, p.getStringList());
            } else if (f.getType().equals((boolean.class))) {
                p = config.get(annotation.category(), f.getName(), (Boolean) o);
                f.set(null, p.getBoolean((Boolean) o));
            }
            if (!annotation.comment().equals(ConfigField.DEFAULT_COMMENT)) {
                p.comment = annotation.comment();
            }
            config.save();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
