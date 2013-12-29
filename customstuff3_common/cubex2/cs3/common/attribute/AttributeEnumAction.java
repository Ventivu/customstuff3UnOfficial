package cubex2.cs3.common.attribute;

import net.minecraft.item.EnumAction;

public class AttributeEnumAction extends AttributeStringToOther<EnumAction>
{

    public AttributeEnumAction(AttributeCollection attributeCollection, String name, EnumAction defaultValue)
    {
        super(attributeCollection, name, defaultValue);
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
