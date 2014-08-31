package cubex2.cs3.common.attribute;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.Window;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class AttributeContainer
{
    protected final BaseContentPack pack;
    private final Map<Field, AttributeBridge> bridgeMap = Maps.newHashMap();

    public AttributeContainer(BaseContentPack pack)
    {
        this.pack = pack;
        createBridges();
    }

    public void loadFromNBT(NBTTagCompound compound)
    {
        Field[] fields = getAttributeFields(ALL_ATTRIBUTES);

        try
        {
            for (Field field : fields)
            {
                if (compound.hasKey(field.getName()))
                {
                    AttributeBridge bridge = bridgeMap.get(field);
                    NBTTagCompound attributeCompound = compound.getCompoundTag(field.getName());
                    field.set(this, bridge.loadValueFromNBT(attributeCompound));
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        Field[] fields = getAttributeFields(ALL_ATTRIBUTES);


        for (Field field : fields)
        {
            try
            {
                AttributeBridge bridge = bridgeMap.get(field);
                NBTTagCompound attributeCompound = new NBTTagCompound();

                Object value = field.get(this);
                bridge.writeValueToNBT(attributeCompound, value);

                compound.setTag(field.getName(), attributeCompound);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    private Field[] getAttributeFields(Predicate<Field> predicate)
    {
        Iterator<Field> fields = Iterators.forArray(getClass().getFields());
        fields = Iterators.filter(fields, predicate);
        return Iterators.toArray(fields, Field.class);
    }

    private void createBridges()
    {
        try
        {
            for (Field field : getAttributeFields(ALL_ATTRIBUTES))
            {
                bridgeMap.put(field, getBridge(field));
            }
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private AttributeBridge getBridge(Field field) throws Exception
    {
        Class<? extends AttributeBridge> bridgeClass = field.getAnnotation(Attribute.class).bridgeClass();
        if (bridgeClass == NullAttributeBridge.class)
        {
            bridgeClass = DefaultAttributeBridges.getDefaultBridge(field.getType());
            if (bridgeClass == NullAttributeBridge.class)
            {
                throw new RuntimeException("Attribute " + field.getName() + " doesn't have a bridge class!");
            }
        }
        AttributeBridge bridge = bridgeClass.newInstance();
        bridge.additionalInfo = field.getAnnotation(Attribute.class).additionalInfo();
        return bridge;
    }

    /**
     * Gets the names of the attribute fields that have 'hasOwnWindow' set to true.
     *
     * @return The names of the fields.
     */
    public String[] getAttributeFieldNames()
    {
        Field[] fields = getAttributeFields(ATTRIBUTE_WITH_OWN_WINDOW);
        String[] names = new String[fields.length];
        for (int i = 0; i < names.length; i++)
        {
            names[i] = fields[i].getName();
        }
        return names;
    }

    public Class<? extends Window> getWindowClass(String attributeName)
    {
        try
        {
            return getClass().getField(attributeName).getAnnotation(Attribute.class).windowClass();
        } catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public <T> T getAttribute(String attributeName)
    {
        try
        {
            return (T) getClass().getField(attributeName).get(this);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public <T> void setAttriubte(String attributeName, T value)
    {
        try
        {
            getClass().getField(attributeName).set(this, value);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static final Predicate<Field> ALL_ATTRIBUTES = new Predicate<Field>()
    {
        @Override
        public boolean apply(Field input)
        {
            return input.isAnnotationPresent(Attribute.class);
        }
    };

    private static final Predicate<Field> ATTRIBUTE_WITH_OWN_WINDOW = new Predicate<Field>()
    {
        @Override
        public boolean apply(Field input)
        {
            return input.isAnnotationPresent(Attribute.class) && input.getAnnotation(Attribute.class).hasOwnWindow();
        }
    };
}
