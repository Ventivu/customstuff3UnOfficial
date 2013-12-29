package cubex2.cs3.common.attribute;

import net.minecraft.nbt.NBTTagCompound;

public class AttributeFloatArray extends AttributeArray<Float>
{

    public AttributeFloatArray(AttributeCollection attributeCollection, String name, Float defaultValue, int size)
    {
        super(attributeCollection, name, defaultValue, size);
    }

    @Override
    protected boolean readSingleValue(Float[] value, int index)
    {
        this.value[index] = value[index];
        return true;
    }

    @Override
    protected void writeSingleValueToNBT(NBTTagCompound compound, int index)
    {
        compound.setFloat("Value", value[index]);
    }

    @Override
    protected void readSingleValueFromNBT(NBTTagCompound compound, int index)
    {
        value[index] = compound.getFloat("Value");
    }
}
