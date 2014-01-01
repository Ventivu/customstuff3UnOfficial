package cubex2.cs3.util;

import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class ItemStackHelper
{
    public static List<ItemStack> getSubTypes(Item item)
    {
        List<ItemStack> stacks = Lists.newArrayList();
        item.getSubItems(item.itemID, null, stacks);
        return stacks;
    }

    public static List<ItemStack> getAllItemStacks()
    {
        List<ItemStack> stacks = Lists.newArrayList();

        for (int i = 0; i < Item.itemsList.length; i++)
        {
            if (Item.itemsList[i] == null)
            {
                continue;
            }

            Item item = Item.itemsList[i];

            List<ItemStack> itemStacks = Lists.newArrayList();
            if (item.getHasSubtypes())
            {
                itemStacks.addAll(ItemStackHelper.getSubTypes(item));
                if (itemStacks.size() > 1)
                {
                    itemStacks.add(new ItemStack(i, 1, OreDictionary.WILDCARD_VALUE));
                }

            }
            else
            {
                itemStacks.add(new ItemStack(i, 1, 0));
            }
            stacks.addAll(itemStacks);
        }

        return stacks;
    }

    public static List<ItemStack> getOreItemStacks(String oreName)
    {
        List<ItemStack> stacks = Lists.newArrayList();
        List<ItemStack> oreStacks = OreDictionary.getOres(oreName);

        for (int i = 0; i < oreStacks.size(); i++)
        {
            ItemStack stack = oreStacks.get(i);
            Item item = stack.getItem();
            if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE && item != null)
            {
                if (item.getHasSubtypes())
                {
                    stacks.addAll(ItemStackHelper.getSubTypes(item));
                }
                else
                {
                    stacks.add(new ItemStack(i, 1, 0));
                }
            }
            else
            {
                stacks.add(stack.copy());
            }
        }
        return stacks;
    }
}
