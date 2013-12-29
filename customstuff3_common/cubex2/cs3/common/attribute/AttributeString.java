package cubex2.cs3.common.attribute;

import cubex2.cs3.util.TypeConverter;
import net.minecraft.nbt.NBTTagCompound;

public class AttributeString extends Attribute<String>
{

    public AttributeString(AttributeCollection attributeCollection, String name, String defaultValue)
    {
        super(attributeCollection, name, defaultValue);
    }

    @Override
    public boolean hasValue()
    {
        return value != null && value.length() > 0;
    }

    @Override
    public boolean readValue(Object value)
    {
        String s = new TypeConverter(value).getStringValue();
        if (s == null)
            return true;
        this.value = s;
        return true;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Value", value);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        value = compound.getString("Value");
    }
}
