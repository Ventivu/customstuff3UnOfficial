package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.OreDictionaryEntry;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.lib.Color;

public class ListBoxItemOreDictEntry extends ListBoxItem<OreDictionaryEntry>
{
    private Label lblOreClass;

    public ListBoxItemOreDictEntry(OreDictionaryEntry value, int idx,int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);

        ItemDisplay itemDisplay = itemDisplay(value.stack).left(3).centerVert().add();

        lblOreClass = label("Ore Class: " + value.oreClass).left(itemDisplay, 3).centerVert().add();
    }

    @Override
    public void selectionChanged()
    {
        lblOreClass.setColor(isSelected() ? Color.YELLOW : Color.BLACK);
    }
}
