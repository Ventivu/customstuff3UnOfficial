package cubex2.cs3.common.attribute;

import net.minecraft.creativetab.CreativeTabs;

public class AttributeCreativeTabArray extends AttributeStringToOtherArray<CreativeTabs>
{

    public AttributeCreativeTabArray(AttributeCollection attributeCollection, String name, CreativeTabs defaultValue, int size)
    {
        super(attributeCollection, name, defaultValue, size);
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
