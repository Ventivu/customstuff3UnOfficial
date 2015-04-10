package cubex2.cs3.ingame.gui;

import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.common.BaseContentPack;
import cubex2.cs3.ingame.gui.control.listbox.ListBoxDescription;

public class WindowSmeltingRecipes extends WindowContentList<SmeltingRecipe>
{
    public WindowSmeltingRecipes(BaseContentPack pack)
    {
        super(SmeltingRecipe.class, "Smelting Recipes", 263, 201, pack);
    }

    @Override
    protected void modifyListBoxDesc(ListBoxDescription<SmeltingRecipe> desc)
    {
        desc.rows = 7;
        desc.columns = 3;
        desc.elementHeight = 22;
    }

    @Override
    protected Window createNewContentWindow()
    {
        return new WindowEditOrCreateSmeltingRecipe(pack);
    }

    @Override
    protected Window createEditContentWindow(SmeltingRecipe content)
    {
        return new WindowEditOrCreateSmeltingRecipe(content, pack);
    }
}
