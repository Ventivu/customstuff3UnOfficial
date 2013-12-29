package cubex2.cs3.common.attribute;

public class AttributeStringList extends AttributeStringListToArray<String>
{

    public AttributeStringList(AttributeCollection attributeCollection, String name, String[] defaultValue)
    {
        super(attributeCollection, name, defaultValue);
    }

    @Override
    public String getValue(String s)
    {
        return s;
    }

    @Override
    public String getString(String value)
    {
        return value;
    }

}
