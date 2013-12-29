package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.Alias;
import cubex2.cs3.ingame.gui.control.AliasDisplay;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.lib.Color;

public class ListBoxItemAliasAndName extends ListBoxItem<Alias>
{
    private AliasDisplay display;
    private Label label;

    public ListBoxItemAliasAndName(Alias value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);

        display = new AliasDisplay(3, (getHeight() - 18) / 2 + 1, this);
        display.setAlias(value);
        addControl(display);

        label = new Label(value.name, 23, (getHeight() - 9) / 2 + 1, this);
        addControl(label);
    }

    @Override
    public void selectionChanged()
    {
        label.setColor(isSelected() ? Color.YELLOW : Color.BLACK);
    }
}
