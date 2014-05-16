package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.OreDictionaryEntry;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.lib.Color;

public class ListBoxItemOreDictEntry extends ListBoxItem<OreDictionaryEntry>
{
    private ItemDisplay itemDisplay;
    private Label lblOreClass;

    public ListBoxItemOreDictEntry(OreDictionaryEntry value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);

        itemDisplay = new ItemDisplay(3, (getHeight() - 18) / 2 + 1, this);
        itemDisplay.setItemStack(value.stack);
        addControl(itemDisplay);

        lblOreClass = new Label("Ore Class: " + value.oreClass, 23, (getHeight() - 9) / 2 + 1, this);
        addControl(lblOreClass);
    }

    @Override
    public void selectionChanged()
    {
        lblOreClass.setColor(isSelected() ? Color.YELLOW : Color.BLACK);
    }
}
