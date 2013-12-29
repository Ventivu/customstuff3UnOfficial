package cubex2.cs3.common.attribute;

import net.minecraft.item.EnumAction;

public class AttributeEnumActionArray extends AttributeStringToOtherArray<EnumAction>
{

    public AttributeEnumActionArray(AttributeCollection attributeCollection, String name, EnumAction defaultValue, int size)
    {
        super(attributeCollection, name, defaultValue, size);
    }

    @Override
    public EnumAction getValue(String s)
    {
        return EnumAction.valueOf(s);
    }

    @Override
    public String getString(EnumAction value)
    {
        return value.name();
    }

}
