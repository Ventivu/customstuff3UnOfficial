package cubex2.cs3.common.attribute;

import net.minecraft.nbt.NBTTagCompound;

public class AttributeBooleanArray extends AttributeArray<Boolean>
{

    public AttributeBooleanArray(AttributeCollection attributeCollection, String name, Boolean defaultValue, int size)
    {
        super(attributeCollection, name, defaultValue, size);
    }

    @Override
    protected boolean readSingleValue(Boolean[] value, int index)
    {
        this.value[index] = value[index];
        return true;
    }

    @Override
    protected void writeSingleValueToNBT(NBTTagCompound compound, int index)
    {
        compound.setBoolean("Value", value[index]);
    }

    @Override
    protected void readSingleValueFromNBT(NBTTagCompound compound, int index)
    {
        value[index] = compound.getBoolean("Value");
    }
}
