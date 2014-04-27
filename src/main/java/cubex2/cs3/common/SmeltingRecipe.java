package cubex2.cs3.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

public class SmeltingRecipe extends BaseContent
{
    public Alias input;
    public Alias result;

    public SmeltingRecipe(Alias input, Alias result, BaseContentPack pack)
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
        GameRegistry.addSmelting(input.getItemStack(), result.getItemStackForInventory(1), 0.0f);
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
            if (stack.getItem() == input.item && stack.getItemDamage() == input.damageValue)
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
        GameRegistry.addSmelting(input.getItemStack(), result.getItemStackForInventory(1), 0.0f);
        super.edit();
    }



    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Input", input.name);
        compound.setString("Result", result.name);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        input = pack.aliasRegistry.getAlias(compound.getString("Input"));
        result = pack.aliasRegistry.getAlias(compound.getString("Result"));
    }
}
