package cubex2.cs3.common.attribute;

import cubex2.cs3.util.TypeConverter;
import net.minecraft.nbt.NBTTagCompound;

public class AttributeBoolean extends Attribute<Boolean>
{

    public AttributeBoolean(AttributeCollection attributeCollection, String name, Boolean defaultValue)
    {
        super(attributeCollection, name, defaultValue);
    }

    @Override
    public boolean hasValue()
    {
        return true;
    }

    @Override
    public boolean readValue(Object value)
    {
        Boolean b = new TypeConverter(value).getBooleanValue();
        if (b == null)
            return false;
        this.value = b;
        return true;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setBoolean("Value", value);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        value = compound.getBoolean("Value");
    }
}
