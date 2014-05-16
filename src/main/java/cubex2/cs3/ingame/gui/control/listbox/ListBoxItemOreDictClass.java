package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.gui.control.RecipeInputDisplay;
import cubex2.cs3.util.OreDictionaryClass;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.util.RecipeInput;

// TODO: OreDictClass display
public class ListBoxItemOreDictClass extends ListBoxItem<OreDictionaryClass>
{
    private RecipeInputDisplay display;

    public ListBoxItemOreDictClass(OreDictionaryClass value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);

        display = new RecipeInputDisplay(3, (getHeight() - 18) / 2 + 1, this);
        display.setRecipeInput(new RecipeInput(value.oreClass));
        addControl(display);
    }
}
