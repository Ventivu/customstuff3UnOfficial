package cubex2.cs3.item;

import cubex2.cs3.common.WrappedBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCSBlock extends ItemBlock
{
    private WrappedBlock wrappedBlock;

    public ItemCSBlock(Block block, WrappedBlock wrappedBlock)
    {
        super(block);
        setMaxDamage(0);
        this.wrappedBlock = wrappedBlock;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack)
    {
        return wrappedBlock.getContainerItem(stack);
    }


    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack)
    {
        return wrappedBlock.doesContainerItemLeaveCraftingGrid(stack);
    }
}
