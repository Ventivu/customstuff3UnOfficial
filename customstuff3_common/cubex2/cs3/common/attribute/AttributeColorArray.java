package cubex2.cs3.common.attribute;

public class AttributeColorArray extends AttributeIntegerArray
{

    public AttributeColorArray(AttributeCollection attributeCollection, String name, Integer defaultValue, int size)
    {
        super(attributeCollection, name, defaultValue, size);
    }

    @Override
    public Object getJSValue()
    {
        String[] stringDefault = new String[value.length];
        for (int i = 0; i < stringDefault.length; i++)
        {
            stringDefault[i] = Integer.toHexString(value[i]);
        }
        return stringDefault;
    }

    @Override
    public boolean readValue(Object value)
    {
        if (!value.getClass().isArray())
            return false;

        String[] s = (String[]) value;
        this.value = new Integer[s.length];
        for (int i = 0; i < s.length; i++)
        {
            this.value[i] = Integer.parseInt(s[i], 16);
        }
        return true;
    }
}
