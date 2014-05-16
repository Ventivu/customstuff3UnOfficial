package cubex2.cs3.common;

import cpw.mods.fml.common.registry.GameRegistry;
import cubex2.cs3.util.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

public class SmeltingRecipe extends BaseContent
{
    public ItemStack input;
    public ItemStack result;

    public SmeltingRecipe(ItemStack input, ItemStack result, BaseContentPack pack)
    {
        super(pack);
        this.input = input;
        this.result = result;
    }

    public SmeltingRecipe(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public void apply()
    {
        GameRegistry.addSmelting(input, result, 0.0f);
        super.apply();
    }

    @Override
    public void remove()
    {
        remove_do();
        super.remove();
    }

    private void remove_do()
    {
        Map<?, ?> smeltingList = FurnaceRecipes.smelting().getSmeltingList();
        for (Object o : smeltingList.keySet())
        {
            ItemStack stack = (ItemStack) o;
            if (stack.getItem() == input.getItem() && stack.getItemDamage() == input.getItemDamage())
            {
                smeltingList.remove(o);
                break;
            }
        }
    }

    @Override
    public void edit()
    {
        remove_do();
        GameRegistry.addSmelting(input, result, 0.0f);
        super.edit();
    }



    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("Input", ItemStackHelper.writeToNBT(input));
        compound.setTag("Result", ItemStackHelper.writeToNBT(result));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        input = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Input"));
        result = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Result"));
    }
}
