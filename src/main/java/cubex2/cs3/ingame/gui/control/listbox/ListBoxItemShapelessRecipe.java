package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.scripting.ShapelessRecipe;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.PictureBox;
import cubex2.cs3.ingame.gui.control.RecipeInputDisplay;
import cubex2.cs3.lib.Textures;

public class ListBoxItemShapelessRecipe extends ListBoxItem<ShapelessRecipe>
{
    private RecipeInputDisplay[] inputDisplays;
    private ItemDisplay resultDisplay;
    private PictureBox pbArrow;

    public ListBoxItemShapelessRecipe(ShapelessRecipe value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);

        inputDisplays = new RecipeInputDisplay[9];
        for (int i = 0; i < 9; i++)
        {
            int row = i / 3;
            int col = i % 3;

            inputDisplays[i] = new RecipeInputDisplay(4 + col * 18, 4 + row * 18, this).setDrawSlotBackground();
            addControl(inputDisplays[i]);
        }

        for (int i = 0; i < value.input.length; i++)
        {
            inputDisplays[i].setRecipeInput(value.input[i]);
        }

        resultDisplay = new ItemDisplay(92, 4 + 18, this).setDrawSlotBackground();
        resultDisplay.setItemStack(value.result);
        addControl(resultDisplay);

        pbArrow = new PictureBox(Textures.CONTROLS, 218, 18, 63, 4 + 18, 22, 15, this);
        addControl(pbArrow);
    }
}
