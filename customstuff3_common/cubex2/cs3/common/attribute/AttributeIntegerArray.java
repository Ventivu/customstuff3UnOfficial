package cubex2.cs3.common.attribute;

import net.minecraft.nbt.NBTTagCompound;

public class AttributeIntegerArray extends AttributeArray<Integer>
{

    public AttributeIntegerArray(AttributeCollection attributeCollection, String name, Integer defaultValue, int size)
    {
        super(attributeCollection, name, defaultValue, size);
    }

    @Override
    protected boolean readSingleValue(Integer[] value, int index)
    {
        this.value[index] = value[index];
        return true;
    }

    @Override
    protected void writeSingleValueToNBT(NBTTagCompound compound, int index)
    {
        compound.setInteger("Value", this.value[index]);
    }

    @Override
    protected void readSingleValueFromNBT(NBTTagCompound compound, int index)
    {
        this.value[index] = compound.getInteger("Value");
    }
}
