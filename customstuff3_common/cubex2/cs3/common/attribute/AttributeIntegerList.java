package cubex2.cs3.common.attribute;

public class AttributeIntegerList extends AttributeStringListToArray<Integer>
{

    public AttributeIntegerList(AttributeCollection attributeCollection, String name, Integer[] defaultValue)
    {
        super(attributeCollection, name, defaultValue);
    }

    @Override
    public Integer getValue(String s)
    {
        return s != null ? Integer.parseInt(s) : null;
    }

    @Override
    public String getString(Integer value)
    {
        return value != null ? value.toString() : null;
    }

}
