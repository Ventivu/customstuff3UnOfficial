package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.OreDictionaryEntry;
import cubex2.cs3.ingame.gui.control.AliasDisplay;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.lib.Color;

public class ListBoxItemOreDictEntry extends ListBoxItem<OreDictionaryEntry>
{
    private AliasDisplay aliasDisplay;
    private Label lblOreClass;

    public ListBoxItemOreDictEntry(OreDictionaryEntry value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);

        aliasDisplay = new AliasDisplay(3, (getHeight() - 18) / 2 + 1, this);
        aliasDisplay.setAlias(value.alias);
        addControl(aliasDisplay);

        lblOreClass = new Label("Ore Class: " + value.oreClass, 23, (getHeight() - 9) / 2 + 1, this);
        addControl(lblOreClass);
    }

    @Override
    public void selectionChanged()
    {
        lblOreClass.setColor(isSelected() ? Color.YELLOW : Color.BLACK);
    }
}
