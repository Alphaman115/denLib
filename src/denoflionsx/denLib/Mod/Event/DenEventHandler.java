package denoflionsx.denLib.Mod.Event;

import denoflionsx.denLib.Mod.denLibMod;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DenEventHandler {
    
    /*
     * This event system was made because Forge has no event for dumping a bucket.
     * 
     */
    
    public static DenEventHandler instance;
    private ArrayList<Object> listeners = new ArrayList();
    private HashMap<Object, ArrayList<Method>> methods = new HashMap();
    
    public static void create() {
        instance = new DenEventHandler();
    }
    
    public void register(Object o) {
        listeners.add(o);
        ArrayList<Method> list = new ArrayList();
        Class c = o.getClass();
        for (Method m : c.getDeclaredMethods()) {
            for (Annotation a : m.getDeclaredAnnotations()) {
                if (a instanceof DenListen) {
                    if (Arrays.equals(m.getParameterTypes(), new Object[]{BucketDumpedEvent.class})) {
                        list.add(m);
                        methods.put(o, list);
                        denLibMod.Proxy.print("Registered DenListen @ " + c.getName() + ", " + m.getName());
                    }
                }
            }
        }
    }
    
    public void processEvent(BucketDumpedEvent e) {
        for (Object o : listeners) {
            try {
                for (Method m : methods.get(o)) {
                    m.invoke(o, e);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
