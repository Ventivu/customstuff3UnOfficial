package cubex2.cs3.common.attribute;

import cubex2.cs3.util.ArrayHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.lang.reflect.Array;

public abstract class AttributeStringToOtherArray<T> extends Attribute<T[]>
{
    private String[] stringInput;

    public AttributeStringToOtherArray(AttributeCollection attributeCollection, String name, T defaultValue, int size)
    {
        super(attributeCollection, name, ArrayHelper.createArray(defaultValue, size));
    }

    public AttributeStringToOtherArray(AttributeCollection attributeCollection, String name, T[] defaultValue)
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
        return index >= 0 && index < value.length && value[index] != null;
    }

    @Override
    public Object getJSValue()
    {
        String[] ret = new String[value.length];
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = getString(value[i]);
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean readValue(Object value)
    {
        if (!value.getClass().isArray())
            return false;

        String[] array = (String[]) value;
        stringInput = array;
        this.value = (T[]) Array.newInstance(this.value.getClass().getComponentType(), array.length);
        for (int i = 0; i < array.length; i++)
        {
            this.value[i] = getValue(array[i]);
        }
        return true;
    }

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
        for (int i = 0; i < valueList.tagCount(); i++)
        {
            NBTTagCompound valueTag = (NBTTagCompound) valueList.tagAt(i);
            int index = valueTag.getByte("Index");
            stringInput[index] = valueTag.getString("Value");
            this.value[index] = getValue(stringInput[index]);
        }
    }

    public abstract T getValue(String s);

    public abstract String getString(T value);

}
