package cubex2.cs3.common;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IItemMatcher
{
    ItemStack getItemStack();

    List<ItemStack> getItemStacks();

    boolean isRepresentingStack(ItemStack stack);
}
