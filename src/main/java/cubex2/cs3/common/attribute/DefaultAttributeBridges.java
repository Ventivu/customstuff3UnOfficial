package cubex2.cs3.common.attribute;

import com.google.common.collect.Maps;
import cubex2.cs3.common.attribute.bridges.*;
import cubex2.cs3.util.IconWrapper;
import net.minecraft.creativetab.CreativeTabs;

import java.util.Map;

public abstract class DefaultAttributeBridges
{
    private static Map<Class<?>, Class<? extends AttributeBridge>> defaultBridges = Maps.newHashMap();

    public static Class<? extends AttributeBridge> getDefaultBridge(Class<?> attributeClass)
    {
        if (defaultBridges.containsKey(attributeClass))
        {
            return defaultBridges.get(attributeClass);
        }
        return NullAttributeBridge.class;
    }

    public static <T> void register(Class<T> attributeClass, Class<? extends AttributeBridge<T>> handlerClass)
    {
        defaultBridges.put(attributeClass, handlerClass);
    }

    static
    {
        register(String.class, StringBridge.class);
        register(IconWrapper.class, IconWrapperBridge.class);
        register(int.class, IntegerBridge.class);
        register(boolean.class, BooleanBridge.class);
        register(CreativeTabs.class, CreativeTabBridge.class);
    }
}
