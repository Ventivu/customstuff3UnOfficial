package cubex2.cs3.common.attribute;

import cubex2.cs3.ingame.gui.Window;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute
{
    Class<? extends AttributeBridge> bridgeClass() default NullAttributeBridge.class;

    Class<? extends Window> windowClass();

    /**
     * Additional information that is passed to the AttributeJsHandler
     *
     * @return The additional information
     */
    String additionalInfo() default "";
}
