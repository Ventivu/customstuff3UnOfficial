package cubex2.cs3.common;

import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class Alias implements Content, IItemMatcher, Comparable<Alias>
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

    public ItemStack getItemStackForInventory(int stackSize)
    {
        return new ItemStack(item, stackSize, damageValue == OreDictionary.WILDCARD_VALUE ? 0 : damageValue);
    }

    @Override
    public ItemStack getItemStack()
    {
        return new ItemStack(item, 1, damageValue);
    }

    @Override
    public List<ItemStack> getItemStacks()
    {
        return Lists.newArrayList(getItemStack());
    }

    @Override
    public boolean isRepresentingStack(ItemStack stack)
    {
        return stack.getItem() == item && (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack.getItemDamage() == damageValue);
    }

    @Override
    public int compareTo(Alias o)
    {
        return name.compareTo(o.name);
    }
}
