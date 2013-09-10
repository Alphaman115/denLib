package denoflionsx.denLib.Mod;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.ModMetadata;
import java.util.Arrays;

public class DenModContainer extends DummyModContainer {

    public DenModContainer() {
        super(new ModMetadata());
        ModMetadata md = super.getMetadata();
        md.modId = "@NAME@";
        md.name = "@NAME@";
        md.version = "@VERSION@";
        md.authorList = Arrays.asList("denoflionsx");
        md.url = "http://denoflionsx.info";
        md.description = "@DESC@";
    }
}
