package cubex2.cs3.common.attribute;

import net.minecraft.creativetab.CreativeTabs;

public class AttributeCreativeTab extends AttributeStringToOther<CreativeTabs>
{

    public AttributeCreativeTab(AttributeCollection attributeCollection, String name, CreativeTabs defaultValue)
    {
        super(attributeCollection, name, defaultValue);
    }

    @Override
    public CreativeTabs getValue(String s)
    {
        return cubex2.cs3.lib.CreativeTabs.getCreativeTab(s);
    }

    @Override
    public String getString(CreativeTabs value)
    {
        return cubex2.cs3.lib.CreativeTabs.getTabName(value);
    }

}
