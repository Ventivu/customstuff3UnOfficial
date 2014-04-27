package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.Alias;
import cubex2.cs3.ingame.gui.control.AliasDisplay;
import cubex2.cs3.ingame.gui.control.Control;

public class ListBoxItemAlias extends ListBoxItem<Alias>
{
    private AliasDisplay aliasDisplay;

    public ListBoxItemAlias(Alias value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);

        aliasDisplay = new AliasDisplay(3, (getHeight() - 18) / 2 + 1, this);
        aliasDisplay.setAlias(value);
        addControl(aliasDisplay);
    }
}
