package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.util.OreDictionaryClass;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemMatcherDisplay;

public class ListBoxItemOreDictClass extends ListBoxItem<OreDictionaryClass>
{
    private ItemMatcherDisplay display;

    public ListBoxItemOreDictClass(OreDictionaryClass value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);

        display = new ItemMatcherDisplay(3, (getHeight() - 18) / 2 + 1, this);
        display.setItemMatcher(value);
        addControl(display);
    }
}
