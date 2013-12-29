package cubex2.cs3.common.attribute;


import cubex2.cs3.util.TypeConverter;


public class AttributeColor extends AttributeInteger
{

    public AttributeColor(AttributeCollection attributeCollection, String name, Integer defaultValue)
    {
        super(attributeCollection, name, defaultValue);
    }

    @Override
    public Object getJSValue()
    {
        return Integer.toHexString(value);
    }

    @Override
    public boolean readValue(Object value)
    {
        String s = new TypeConverter(value).getStringValue();
        if (s == null)
            return false;
        return super.readValue(Integer.parseInt(s, 16));
    }
}
