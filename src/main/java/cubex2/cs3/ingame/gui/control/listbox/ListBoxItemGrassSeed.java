package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.GrassSeed;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.lib.Color;

public class ListBoxItemGrassSeed extends ListBoxItem<GrassSeed>
{
    private Label lblWeight;

    public ListBoxItemGrassSeed(GrassSeed value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);
        ItemDisplay display = itemDisplay(value.item).left(3).centerVert().add();

        lblWeight = label("Weight: " + value.weight).left(display, 2).centerVert(1).add();
    }

    @Override
    public void selectionChanged()
    {
        lblWeight.setColor(isSelected() ? Color.YELLOW : Color.BLACK);
    }
}
