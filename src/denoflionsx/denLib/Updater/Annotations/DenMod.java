package denoflionsx.denLib.Updater.Annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DenMod {

    String name();

    String version();

    String url();
}
