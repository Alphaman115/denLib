package denoflionsx.denLib.Config.Annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Deprecated
@Retention(RetentionPolicy.RUNTIME)
public @interface Tunable {

    String category();

    String comment() default "Tunable Values";
}
