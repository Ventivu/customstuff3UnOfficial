package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.Fuel;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.lib.Color;

public class ListBoxItemFuel extends ListBoxItem<Fuel>
{
    private Label lblDuration;

    public ListBoxItemFuel(Fuel value, int idx,int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);
        ItemDisplay itemDisplay = itemDisplay(value.stack).left(3).centerVert().add();

        lblDuration = label("Duration: " + value.duration).left(itemDisplay, 2).centerVert().add();
    }

    @Override
    public void selectionChanged()
    {
        lblDuration.setColor(isSelected() ? Color.YELLOW : Color.BLACK);
    }
}
