package denoflionsx.denLib.NewConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigField {

    public static final String DEFAULT_COMMENT = "Tunable Values";
    public static final String DEFAULT_CATEGORY = "items";

    String category() default DEFAULT_CATEGORY;

    String comment() default DEFAULT_COMMENT;
}
