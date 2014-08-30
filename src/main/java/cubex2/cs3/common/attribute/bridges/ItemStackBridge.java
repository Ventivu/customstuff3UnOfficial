package cubex2.cs3.common.attribute.bridges;

import cubex2.cs3.common.attribute.AttributeBridge;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStackBridge extends AttributeBridge<ItemStack>
{
    @Override
    public ItemStack loadValueFromNBT(NBTTagCompound compound)
    {
        return ItemStack.loadItemStackFromNBT(compound);
    }

    @Override
    public void writeValueToNBT(NBTTagCompound compound, ItemStack value)
    {
        value.writeToNBT(compound);
    }
}
