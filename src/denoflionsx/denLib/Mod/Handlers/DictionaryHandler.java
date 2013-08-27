package denoflionsx.denLib.Mod.Handlers;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class DictionaryHandler {

    private static final Logger LOG = Logger.getLogger(DictionaryHandler.class.getName());

    public DictionaryHandler(String s) {
        try {
            SimpleFormatter f = new SimpleFormatter();
            Handler handler = new FileHandler(s + "/denLib.log");
            handler.setFormatter(f);
            LOG.addHandler(handler);
        } catch (Throwable t) {
        }
    }

    @ForgeSubscribe
    public void onEvent(OreRegisterEvent e) {
        LOG.info(e.Name);
    }
}
