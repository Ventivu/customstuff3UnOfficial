package cubex2.cs3.common;

import cubex2.cs3.util.NBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

public class GrassSeed extends BaseContent
{
    public ItemStack item;
    public int weight;

    public GrassSeed(BaseContentPack pack)
    {
        super(pack);
    }

    public GrassSeed(ItemStack item, int weight, BaseContentPack pack)
    {
        super(pack);
        this.item = item;
        this.weight = weight;
    }

    @Override
    public void apply()
    {
        MinecraftForge.addGrassSeed(item, weight);

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
        NBTHelper.writeToNBT(item, "Item", compound);
        compound.setInteger("Weight", weight);
    }

    @Override
    public boolean readFromNBT(NBTTagCompound compound)
    {
        item = NBTHelper.readStackFromNBT("Item", compound);
        weight = compound.getInteger("Weight");

        return item != null;
    }
}
