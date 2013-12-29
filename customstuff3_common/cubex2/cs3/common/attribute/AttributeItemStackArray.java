package cubex2.cs3.common.attribute;

public class AttributeItemStackArray
{
}/* extends AttributeStringToOtherArray<ItemStack>
{

    public AttributeItemStackArray(AttributeCollection attributeCollection, String name, ItemStack defaultValue, int size)
    {
        super(attributeCollection, name, defaultValue, size);
    }

    public AttributeItemStackArray(AttributeCollection attributeCollection, String name, int size)
    {
        super(attributeCollection, name, new ItemStack[size]);
    }

    @Override
    public ItemStack getValue(String s)
    {
        return GeneralHelper.getItemStack(s, mod.getAliasHandler(), 0);
    }

    @Override
    public String getString(ItemStack value)
    {
        if (value != null && value.itemID == 0)
        {
            value = null;
        }
        return GeneralHelper.getString(value, true);
    }

}*/
