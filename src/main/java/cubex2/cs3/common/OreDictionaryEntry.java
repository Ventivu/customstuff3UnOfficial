package cubex2.cs3.common;

import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryEntry extends BaseContent
{
    public String oreClass;
    public ItemStack stack;

    public OreDictionaryEntry(String oreClass, ItemStack stack, BaseContentPack pack)
    {
        super(pack);
        this.oreClass = oreClass;
        this.stack = stack;
    }

    public OreDictionaryEntry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public void apply()
    {
        OreDictionary.registerOre(oreClass, stack);

        super.apply();
    }

    @Override
    public boolean canEdit()
    {
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("OreClass", oreClass);
        compound.setTag("Stack", ItemStackHelper.writeToNBT(stack));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        oreClass = compound.getString("OreClass");
        stack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Stack"));
    }
}
