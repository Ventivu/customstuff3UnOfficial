package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.lib.Textures;

public class ListBoxItemSmeltingRecipe extends ListBoxItem<SmeltingRecipe>
{

    public ListBoxItemSmeltingRecipe(SmeltingRecipe value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);

        itemDisplay(value.input).left(3).centerVert().add();
        itemDisplay(value.result).left(55).centerVert().add();
        pictureBox(Textures.CONTROLS, 218, 18).left(27).centerVert().size(22, 15).add();
    }
}
