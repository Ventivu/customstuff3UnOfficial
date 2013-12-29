package cubex2.cs3.common.attribute;

import cubex2.cs3.util.TypeConverter;
import net.minecraft.nbt.NBTTagCompound;

public abstract class AttributeStringToOther<T> extends Attribute<T>
{
    protected boolean allowNull = false;

    private String stringInput;

    public AttributeStringToOther(AttributeCollection attributeCollection, String name, T defaultValue)
    {
        super(attributeCollection, name, defaultValue);
    }

    @Override
    public boolean hasValue()
    {
        return value != null;
    }

    @Override
    public Object getJSValue()
    {
        return getString(value);
    }

    @Override
    public boolean readValue(Object value)
    {
        String s = new TypeConverter(value).getStringValue();
        if (allowNull && s == null)
            return true;
        stringInput = s;
        this.value = getValue(s);
        return true;
    }

    public abstract T getValue(String s);

    public abstract String getString(T value);

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Value", stringInput);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        stringInput = compound.getString("Value");
        this.value = getValue(stringInput);
    }

}
