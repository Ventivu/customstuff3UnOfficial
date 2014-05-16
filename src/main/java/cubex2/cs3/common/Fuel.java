package cubex2.cs3.common;

import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class Fuel extends BaseContent
{
    public ItemStack stack;
    public int duration;

    public Fuel(ItemStack stack, int duration, BaseContentPack pack)
    {
        super(pack);
        this.stack = stack;
        this.duration = duration;
    }

    public Fuel(BaseContentPack pack)
    {
        super(pack);
    }

    public boolean isRepresentingStack(ItemStack stack)
    {
        return this.stack.getItem() == stack.getItem() &&
                (this.stack.getItemDamage() == stack.getItemDamage() || this.stack.getItemDamage() == OreDictionary.WILDCARD_VALUE);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("Stack", ItemStackHelper.writeToNBT(stack));
        compound.setInteger("Duration", duration);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        stack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Stack"));
        duration = compound.getInteger("Duration");
    }
}
