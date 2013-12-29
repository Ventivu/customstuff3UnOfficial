package cubex2.cs3.common.attribute;

import cubex2.cs3.util.TypeConverter;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Array;

public abstract class AttributeStringListToArray<T> extends Attribute<T[]>
{
    private String stringInput;
    private String separator = ",";

    public AttributeStringListToArray(AttributeCollection attributeCollection, String name, T[] defaultValue)
    {
        super(attributeCollection, name, defaultValue);
    }

    public AttributeStringListToArray(AttributeCollection attributeCollection, String name, T[] defaultValue, String separator)
    {
        super(attributeCollection, name, defaultValue);
        this.separator = separator;
    }

    @Override
    public boolean hasValue()
    {
        return value != null && value.length > 0;
    }

    @Override
    public Object getJSValue()
    {
        if (value == null || value.length == 0)
            return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length; i++)
        {
            if (value[i] == null)
            {
                continue;
            }
            if (sb.length() > 0)
            {
                sb.append(separator);
            }
            sb.append(getString(value[i]));
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean readValue(Object value)
    {
        String s = new TypeConverter(value).getStringValue();
        if (s == null)
            return true;
        stringInput = s;

        String[] parts = s.split(separator);
        this.value = (T[]) Array.newInstance(this.value.getClass().getComponentType(), parts.length);
        for (int i = 0; i < parts.length; i++)
        {
            this.value[i] = getValue(parts[i].trim());
        }
        return true;
    }

    public abstract T getValue(String s);

    public abstract String getString(T value);

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Value", stringInput);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        readValue(compound.getString("Value"));
    }
}
