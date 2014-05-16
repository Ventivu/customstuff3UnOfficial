package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.Fuel;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.lib.Color;

public class ListBoxItemFuel extends ListBoxItem<Fuel>
{
    private ItemDisplay itemDisplay;
    private Label lblDuration;

    public ListBoxItemFuel(Fuel value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);

        itemDisplay = new ItemDisplay(3, (getHeight() - 18) / 2 + 1, this);
        itemDisplay.setItemStack(value.stack);
        addControl(itemDisplay);

        lblDuration = new Label("Duration: " + value.duration, 23, (getHeight() - 9) / 2 + 1, this);
        addControl(lblDuration);
    }

    @Override
    public void selectionChanged()
    {
        lblDuration.setColor(isSelected() ? Color.YELLOW : Color.BLACK);
    }
}
