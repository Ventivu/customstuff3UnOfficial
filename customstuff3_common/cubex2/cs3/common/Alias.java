package cubex2.cs3.common;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Alias implements Content
{
    public Item item;
    public int damageValue;
    public String name;

    protected BaseContentPack pack;


    public Alias(BaseContentPack pack)
    {
        this.pack = pack;
    }

    public Alias(Item item, int damageValue, String name, BaseContentPack pack)
    {
        this.item = item;
        this.damageValue = damageValue;
        this.pack = pack;
        this.name = name;
    }

    public ItemStack newItemStack()
    {
        return new ItemStack(item, 1, damageValue);
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
        compound.setString("Name", name);
        compound.setInteger("ItemID", item.itemID);
        compound.setShort("DamageValue", (short) damageValue);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        name = compound.getString("Name");
        item = Item.itemsList[compound.getInteger("ItemID")];
        damageValue = compound.getShort("DamageValue");
    }
}
