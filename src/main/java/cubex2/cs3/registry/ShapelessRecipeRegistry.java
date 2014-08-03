package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.scripting.ShapelessRecipe;
import cubex2.cs3.lib.Strings;

public class ShapelessRecipeRegistry extends ContentRegistry<ShapelessRecipe>
{
    public ShapelessRecipeRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public ShapelessRecipe newDataInstance()
    {
        return new ShapelessRecipe(pack);
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_SHAPELESS_RECIPE;
    }
}
