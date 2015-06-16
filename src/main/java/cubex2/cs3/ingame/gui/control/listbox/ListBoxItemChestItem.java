package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.ChestItem;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.Label;
import cubex2.cs3.lib.Color;
import cubex2.cs3.util.GeneralHelper;

public class ListBoxItemChestItem extends ListBoxItem<ChestItem>
{
    private Label lblDesc;

    public ListBoxItemChestItem(ChestItem value, int idx, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        super(value, idx, width, height, anchor, offsetX, offsetY, parent);
        ItemDisplay itemDisplay = itemDisplay(value.stack).left(3).centerVert().add();

        lblDesc = label(value.chest + ", Count: " + GeneralHelper.rangeToString(value.minCount, value.maxCount) + ", Rarity: " + value.rarity).left(itemDisplay, 2).centerVert(1).add();
    }

    @Override
    public void selectionChanged()
    {
        lblDesc.setColor(isSelected() ? Color.YELLOW : Color.BLACK);
    }
}
