package cubex2.cs3.common;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class Fuel extends BaseContent
{
    public Alias alias;
    public int duration;

    public Fuel(Alias alias, int duration, BaseContentPack pack)
    {
        super(pack);
        this.alias = alias;
        this.duration = duration;
    }

    public Fuel(BaseContentPack pack)
    {
        super(pack);
    }

    public ItemStack newItemStack()
    {
        return alias.getItemStack();
    }

    public boolean isRepresentingStack(ItemStack stack)
    {
        return alias.item == stack.getItem() &&
                (alias.damageValue == stack.getItemDamage() || alias.damageValue == OreDictionary.WILDCARD_VALUE);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Alias", alias.name);
        compound.setInteger("Duration", duration);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        alias = pack.aliasRegistry.getAlias(compound.getString("Alias"));
        duration = compound.getInteger("Duration");
    }
}
