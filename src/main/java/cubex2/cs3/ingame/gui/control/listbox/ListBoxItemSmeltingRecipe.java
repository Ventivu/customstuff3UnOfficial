package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.ingame.gui.control.AliasDisplay;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.PictureBox;
import cubex2.cs3.lib.Textures;

public class ListBoxItemSmeltingRecipe extends ListBoxItem<SmeltingRecipe>
{
    private AliasDisplay inputDisplay;
    private AliasDisplay resultDisplay;
    private PictureBox pbArrow;

    public ListBoxItemSmeltingRecipe(SmeltingRecipe value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);

        inputDisplay = new AliasDisplay(3, (getHeight() - 18) / 2 + 1, this);
        inputDisplay.setAlias(value.input);
        addControl(inputDisplay);

        resultDisplay = new AliasDisplay(51 + 4, (getHeight() - 18) / 2 + 1, this);
        resultDisplay.setAlias(value.result);
        addControl(resultDisplay);

        pbArrow = new PictureBox(Textures.CONTROLS, 218, 18, 25 + 2, (getHeight() - 15) / 2 + 1, 22, 15, this);
        addControl(pbArrow);
    }
}
