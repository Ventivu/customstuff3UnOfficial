package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.CreativeTab;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.lib.Color;

public class ListBoxItemCreativeTab extends ListBoxItem<CreativeTab>
{
    private Label lblLabel;

    public ListBoxItemCreativeTab(CreativeTab value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);
        ItemDisplay display = itemDisplay(value.icon).left(3).centerVert().add();

        lblLabel = label(value.label).left(display, 2).centerVert(1).add();
    }

    @Override
    public void selectionChanged()
    {
        lblLabel.setColor(isSelected() ? Color.YELLOW : Color.BLACK);
    }
}
