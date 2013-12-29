package cubex2.cs3.common.attribute;


import cubex2.cs3.util.TypeConverter;
import net.minecraft.nbt.NBTTagCompound;

public class AttributeInteger extends Attribute<Integer>
{

    public AttributeInteger(AttributeCollection attributeCollection, String name, Integer defaultValue)
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
        this.value = n.intValue();
        return true;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Value", value);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        value = compound.getInteger("Value");
    }
}
