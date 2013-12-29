package cubex2.cs3.common.attribute;


import cubex2.cs3.util.ArrayHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.lang.reflect.Array;

public abstract class AttributeArray<T> extends Attribute<T[]>
{

    public AttributeArray(AttributeCollection attributeCollection, String name, T defaultValue, int size)
    {
        super(attributeCollection, name, ArrayHelper.createArray(defaultValue, size));
        if (defaultValue == null)
            throw new IllegalArgumentException("Default value may not be null.");
    }

    public AttributeArray(AttributeCollection attributeCollection, String name, T[] defaultValue)
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
    public boolean readValue(Object value)
    {
        if (!value.getClass().isArray())
            return false;

        T[] array = (T[]) value;
        this.value = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length);
        for (int i = 0; i < array.length; i++)
        {
            if (!readSingleValue(array, i))
                return false;
        }
        return true;
    }

    protected abstract boolean readSingleValue(T[] value, int index);

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagList valueList = new NBTTagList();
        for (int i = 0; i < value.length; i++)
        {
            NBTTagCompound valueTag = new NBTTagCompound();
            valueTag.setByte("Index", (byte) i);
            writeSingleValueToNBT(valueTag, i);
            valueList.appendTag(valueTag);
        }
        compound.setTag("ValueList", valueList);
    }

    protected abstract void writeSingleValueToNBT(NBTTagCompound compound, int index);

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagList valueList = compound.getTagList("ValueList");
        for (int i = 0; i < valueList.tagCount(); i++)
        {
            NBTTagCompound valueTag = (NBTTagCompound) valueList.tagAt(i);
            int index = valueTag.getByte("Index");
            readSingleValueFromNBT(valueTag, index);
        }
    }

    protected abstract void readSingleValueFromNBT(NBTTagCompound compound, int index);
}
