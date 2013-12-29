package cubex2.cs3.common;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Fuel implements Content
{
    public Alias alias;
    public int duration;

    private BaseContentPack pack;

    public Fuel(Alias alias, int duration, BaseContentPack pack)
    {
        this.alias = alias;
        this.duration = duration;
        this.pack = pack;
    }

    public Fuel(BaseContentPack pack)
    {
        this.pack = pack;
    }

    public ItemStack newItemStack()
    {
        return alias.newItemStack();
    }

    @Override
    public void apply()
    {
        pack.getContentManager(this).add(this);
        pack.save();
    }

    @Override
    public void edit()
    {
        pack.save();
    }

    @Override
    public void remove()
    {
        pack.getContentManager(this).remove(this);
        pack.save();
    }

    @Override
    public boolean canEdit()
    {
        return true;
    }

    @Override
    public boolean canRemove()
    {
        return true;
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
        alias = pack.aliasManager.getAlias(compound.getString("Alias"));
        duration = compound.getInteger("Duration");
    }
}
