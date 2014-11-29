package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.util.BlockDrop;

public class ListBoxItemBlockDrop extends ListBoxItem<BlockDrop.DropData>
{
    private ItemDisplay display;

    public ListBoxItemBlockDrop(BlockDrop.DropData value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);

        display = new ItemDisplay(3, (getHeight() - 18) / 2 + 1, this);
        display.setItemStack(value.createStack());
        addControl(display);

        int min = value.getMinCount();
        int max = value.getMaxCount();
        label("Amount: " + min + (min != max ? " - " + max : "")).rightTo(display).centerVert(1).add();
    }
}
