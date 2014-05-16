package cubex2.cs3.common;

import cpw.mods.fml.common.registry.GameRegistry;
import cubex2.cs3.util.ItemStackHelper;
import cubex2.cs3.util.RecipeInput;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedRecipe extends BaseContent
{
    public RecipeInput[] input;
    public int width;
    public int height;
    public ItemStack result;

    private ShapedOreRecipe recipe;

    public ShapedRecipe(BaseContentPack pack)
    {
        super(pack);
    }

    public ShapedRecipe(int width, int height, RecipeInput[] input, ItemStack result, BaseContentPack pack)
    {
        super(pack);
        this.width = width;
        this.height = height;
        this.input = input;
        this.result = result;
    }

    @Override
    public void apply()
    {
        Object[] recipeObjects = new Object[height + (width * height) * 2];

        String[] shape = createShape();
        for (int i = 0; i < height; i++)
        {
            recipeObjects[i] = shape[i];
        }

        for (int i = 0, n = 0; i < width * height * 2; i += 2)
        {
            int idx = i + height;
            recipeObjects[idx] = (char) (n++);
            recipeObjects[idx + 1] = input[n - 1] != null ? input[n - 1].getInput() : Blocks.air;
        }

        recipe = new ShapedOreRecipe(result, recipeObjects);
        GameRegistry.addRecipe(recipe);
        super.apply();
    }

    @Override
    public void remove()
    {
        CraftingManager.getInstance().getRecipeList().remove(recipe);

        super.remove();
    }

    private String[] createShape()
    {
        String[] shape = new String[height];
        int n = 0;
        for (int i = 0; i < shape.length; i++)
        {
            String s = "";
            for (int j = 0; j < width; j++)
            {
                s += (char) n++;
            }
            shape[i] = s;
        }
        return shape;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setByte("Width", (byte) width);
        compound.setByte("Height", (byte) height);

        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < input.length; i++)
        {
            NBTTagCompound compound1 = new NBTTagCompound();
            compound1.setByte("Index", (byte) i);
            if (input[i] != null)
            {
                input[i].writeToNBT(compound1);
            }
            tagList.appendTag(compound1);
        }
        compound.setTag("Input", tagList);

        compound.setTag("Result", ItemStackHelper.writeToNBT(result));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        width = compound.getByte("Width");
        height = compound.getByte("Height");

        NBTTagList tagList = compound.getTagList("Input", 10);
        input = new RecipeInput[width * height];
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound compound1 = tagList.getCompoundTagAt(i);
            int idx = compound1.getByte("Index");
            input[idx] = RecipeInput.loadFromNBT(compound1);
        }

        result = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Result"));
    }
}
