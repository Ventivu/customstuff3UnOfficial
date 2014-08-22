package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.WrappedItem;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import cubex2.cs3.ingame.gui.control.Label;
import net.minecraft.item.ItemStack;

public class ListBoxItemWrappedItem extends ListBoxItem<WrappedItem>
{
    private ItemDisplay display;
    private Label lblName;

    public ListBoxItemWrappedItem(WrappedItem value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);

        display = new ItemDisplay(3, (getHeight() - 18) / 2 + 1, this);
        if (value.item != null)
        {
            display.setItemStack(new ItemStack(value.item));
        }
        addControl(display);

        lblName = new Label(value.getName() + " (" + value.getType().name + ")", 23, (getHeight() - 9) / 2 + 1, this);
        addControl(lblName);
    }
}
