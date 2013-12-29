package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.Alias;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.lib.Color;

public class ListBoxItemAlias extends ListBoxItem<Alias>
{
    private ItemDisplay display;
    private Label label;

    public ListBoxItemAlias(Alias value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);

        display = new ItemDisplay(3, (getHeight() - 18) / 2, this);
        display.setItemStack(value.newItemStack());
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
