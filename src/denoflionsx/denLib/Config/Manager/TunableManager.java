package denoflionsx.denLib.Config.Manager;

import denoflionsx.denLib.Config.Annotations.Comment;
import denoflionsx.denLib.Config.Annotations.Config;
import denoflionsx.denLib.Config.Annotations.Tunable;
import denoflionsx.denLib.Mod.denLibMod;
import denoflionsx.denLib.Lib.denLib;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class TunableManager implements ITunableManager {

    private ArrayList<Class> tunableClasses = new ArrayList();

    public TunableManager() {
    }

    @Override
    public void registerTunableClass(Class c) {
        Configuration config1 = null;
        tunableClasses.add(c);
        denLibMod.Proxy.print("Registered tunable class: " + c.getName());
        Field[] f = c.getDeclaredFields();
        for (Field d : f) {
            Annotation[] annos = d.getDeclaredAnnotations();
            for (Annotation e : annos) {
                if (e instanceof Config) {
                    Object o = denLib.ReflectionHelper.getStaticField(d);
                    config1 = (Configuration) o;
                }
            }
        }
        Class[] classes = c.getDeclaredClasses();
        for (Class a : classes) {
            Annotation[] annos = a.getDeclaredAnnotations();
            for (Annotation b : annos) {
                if (b instanceof Tunable) {
                    Tunable t = (Tunable) b;
                    String cat = t.category();
                    Field[] g = a.getDeclaredFields();
                    for (Field h : g) {
                        if (config1 != null) {
                            Property p;
                            if (t.category().contains("items")) {
                                p = config1.getItem(cat, h.getName(), Integer.valueOf(denLib.ReflectionHelper.getStaticField(h).toString()));
                            } else {
                                p = config1.get(cat, h.getName(), denLib.ReflectionHelper.getStaticField(h).toString());
                            }

                            denLib.ReflectionHelper.setStaticField(h, p.getString());
                            ConfigCategory a123 = config1.getCategory(cat);
                            if (!t.comment().equals("Tunable Values")) {
                                a123.setComment(t.comment());
                            }
                            Annotation[] fAnno = h.getDeclaredAnnotations();
                            for (Annotation q : fAnno) {
                                if (q instanceof Comment) {
                                    Comment comment = (Comment) q;
                                    p.comment = comment.comment();
                                }
                            }
                        }
                    }
                }
            }
            if (config1 != null) {
                config1.save();
            }
        }
    }

    @Override
    public ArrayList<Class> getTunableClasses() {
        return tunableClasses;
    }
}
