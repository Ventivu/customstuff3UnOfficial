package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.ingame.gui.Window;
import cubex2.cs3.ingame.gui.WindowSmeltingRecipes;
import cubex2.cs3.lib.Strings;

public class SmeltingRecipeRegistry extends ContentRegistry<SmeltingRecipe>
{
    public SmeltingRecipeRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public SmeltingRecipe newDataInstance()
    {
        return new SmeltingRecipe(pack);
    }

    @Override
    public Window createListWindow()
    {
        return new WindowSmeltingRecipes(pack);
    }

    @Override
    public String getNameForEditPack()
    {
        return "Smelting Recipes";
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_SMELTING_RECIPE;
    }
}
