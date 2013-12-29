package cubex2.cs3.common.attribute;

import net.minecraft.nbt.NBTTagCompound;

public class AttributeStringArray extends AttributeArray<String>
{

    public AttributeStringArray(AttributeCollection attributeCollection, String name, String defaultValue, int size)
    {
        super(attributeCollection, name, defaultValue, size);
    }

    public AttributeStringArray(AttributeCollection attributeCollection, String name, int size)
    {
        super(attributeCollection, name, new String[size]);
    }

    @Override
    public boolean hasValue(int index)
    {
        return super.hasValue(index) && value[index].length() > 0;
    }

    @Override
    protected boolean readSingleValue(String[] value, int index)
    {
        this.value[index] = value[index];
        return true;
    }

    @Override
    protected void writeSingleValueToNBT(NBTTagCompound compound, int index)
    {
        compound.setString("Value", value[index]);
    }

    @Override
    protected void readSingleValueFromNBT(NBTTagCompound compound, int index)
    {
        value[index] = compound.getString("Value");
    }
}
