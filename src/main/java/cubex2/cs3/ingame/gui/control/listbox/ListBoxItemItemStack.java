package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.ingame.gui.control.ItemDisplay;
import net.minecraft.item.ItemStack;

public class ListBoxItemItemStack extends ListBoxItem<ItemStack>
{
    private ItemDisplay display;

    public ListBoxItemItemStack(ItemStack value, int idx, int x, int y, int width, int height, Control parent)
    {
        super(value, idx, x, y, width, height, parent);

        display = new ItemDisplay(3, (getHeight() - 18) / 2 + 1, this);
        display.setItemStack(value);
        addControl(display);
    }
}
