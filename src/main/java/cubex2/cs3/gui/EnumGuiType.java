package cubex2.cs3.gui;

import com.google.common.collect.Maps;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.WrappedGui;
import cubex2.cs3.gui.attributes.GuiAttributes;

import java.lang.reflect.Constructor;
import java.util.Map;

public enum EnumGuiType
{
    NORMAL("normal", WindowNormal.class, GuiAttributes.class);

    public final String name;
    public final Class<? extends WindowNormal> guiClass;
    public final Class<? extends GuiAttributes> attributeClass;

    private final Constructor<? extends WindowNormal> constructor;

    EnumGuiType(String name, Class<? extends WindowNormal> guiClass, Class<? extends GuiAttributes> attributeClass)
    {
        this.name = name;
        this.guiClass = guiClass;
        this.attributeClass = attributeClass;
        constructor = createConstructor();
    }

    private Constructor<? extends WindowNormal> createConstructor()
    {
        try
        {
            return guiClass.getConstructor(WrappedGui.class);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public WindowNormal createWindow(WrappedGui wrappedGui)
    {
        try
        {
            WindowNormal gui = constructor.newInstance(wrappedGui);
            return gui;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public GuiAttributes createAttributeContainer(WrappedGui wrappedGui)
    {
        try
        {
            return attributeClass.getConstructor(BaseContentPack.class).newInstance(wrappedGui.getPack());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static final Map<String, EnumGuiType> map = Maps.newHashMap();

    public static EnumGuiType get(String name)
    {
        if (map.isEmpty())
        {
            for (EnumGuiType e : values())
            {
                map.put(e.name, e);
            }
        }
        return map.get(name);
    }
}
