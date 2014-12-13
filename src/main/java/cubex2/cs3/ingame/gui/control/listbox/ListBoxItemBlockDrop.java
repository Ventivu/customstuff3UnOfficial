package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.util.BlockDrop;

public class ListBoxItemBlockDrop extends ListBoxItem<BlockDrop.DropData>
{
    public ListBoxItemBlockDrop(BlockDrop.DropData value, int idx,int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);

        ItemDisplay display = itemDisplay(value.createStack()).left(3).centerVert().add();

        int min = value.getMinCount();
        int max = value.getMaxCount();
        label("Amount: " + min + (min != max ? " - " + max : "")).rightTo(display).centerVert(1).add();
    }
}
