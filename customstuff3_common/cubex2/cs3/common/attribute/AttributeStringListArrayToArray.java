package cubex2.cs3.common.attribute;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.lang.reflect.Array;

public abstract class AttributeStringListArrayToArray<T> extends Attribute<T[][]>
{
    private String[] stringInput;

    public AttributeStringListArrayToArray(AttributeCollection attributeCollection, String name, T[][] defaultValue)
    {
        super(attributeCollection, name, defaultValue);
        if (defaultValue == null)
            throw new IllegalArgumentException("Default value may not be null.");
    }

    @Override
    public boolean hasValue()
    {
        return true;
    }

    public boolean hasValue(int index)
    {
        return index >= 0 && index < value.length && value[index] != null && value[index].length > 0;
    }

    @Override
    public Object getJSValue()
    {
        String[] ret = new String[value.length];
        for (int i = 0; i < value.length; i++)
        {
            T[] value = this.value[i];
            if (value == null)
            {
                ret[i] = null;
            }
            else
            {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < value.length; j++)
                {
                    if (value[j] == null)
                    {
                        continue;
                    }
                    if (sb.length() > 0)
                    {
                        sb.append(',');
                    }
                    sb.append(getString(value[j]));
                }
                ret[i] = sb.toString();
            }

        }
        return ret;
    }

    @Override
    public boolean readValue(Object value)
    {
        if (!value.getClass().isArray())
            return false;

        String[] array = (String[]) value;
        stringInput = array;
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] != null)
            {
                String[] parts = array[i].split(",");
                this.value[i] = (T[]) Array.newInstance(this.value.getClass().getComponentType().getComponentType(), parts.length);
                for (int j = 0; j < parts.length; j++)
                {
                    this.value[i][j] = getValue(parts[j].trim());
                }
            }
        }

        return true;
    }

    public abstract T getValue(String s);

    public abstract String getString(T value);

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagList valueList = new NBTTagList();
        for (int i = 0; i < stringInput.length; i++)
        {
            NBTTagCompound valueTag = new NBTTagCompound();
            valueTag.setByte("Index", (byte) i);
            valueTag.setString("Value", stringInput[i]);
            valueList.appendTag(valueTag);
        }
        compound.setTag("ValueList", valueList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagList valueList = compound.getTagList("ValueList");
        String[] valArray = new String[valueList.tagCount()];
        for (int i = 0; i < valueList.tagCount(); i++)
        {
            NBTTagCompound valueTag = (NBTTagCompound) valueList.tagAt(i);
            int index = valueTag.getByte("Index");
            valArray[index] = valueTag.getString("Value");
        }
        readValue(valArray);
    }
}
