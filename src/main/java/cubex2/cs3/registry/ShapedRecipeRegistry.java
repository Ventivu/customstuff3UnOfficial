package cubex2.cs3.registry;

import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.common.ShapedRecipe;
import cubex2.cs3.lib.Strings;

public class ShapedRecipeRegistry extends ContentRegistry<ShapedRecipe>
{
    public ShapedRecipeRegistry(BaseContentPack pack)
    {
        super(pack);
    }

    @Override
    public ShapedRecipe newDataInstance()
    {
        return new ShapedRecipe(pack);
    }

    @Override
    public String getName()
    {
        return Strings.REGISTRY_SHAPED_RECIPE;
    }
}
