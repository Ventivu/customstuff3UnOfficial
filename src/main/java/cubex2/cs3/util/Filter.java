package cubex2.cs3.util;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

public interface Filter<T>
{
    boolean matches(T obj, String searchText);

    public static final Filter<ItemStack> ITEM_STACK = new Filter<ItemStack>()
    {
        @Override
        public boolean matches(ItemStack obj, String searchText)
        {
            return StringUtils.containsIgnoreCase(obj.getDisplayName(), searchText);
        }
    };

    public static final Filter<OreDictionaryClass> ORE_CLASS = new Filter<OreDictionaryClass>()
    {
        @Override
        public boolean matches(OreDictionaryClass obj, String searchText)
        {
            return StringUtils.containsIgnoreCase(obj.oreClass, searchText);
        }
    };

}
