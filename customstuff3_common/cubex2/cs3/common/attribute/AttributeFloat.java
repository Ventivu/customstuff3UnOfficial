package cubex2.cs3.common.attribute;

import cubex2.cs3.util.TypeConverter;
import net.minecraft.nbt.NBTTagCompound;

public class AttributeFloat extends Attribute<Float>
{

    public AttributeFloat(AttributeCollection attributeCollection, String name, Float defaultValue)
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
        Number n = new TypeConverter(value).getNumberValue();
        if (n == null)
            return false;
        this.value = n.floatValue();
        return true;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setFloat("Value", value);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        value = compound.getFloat("Value");
    }

}
