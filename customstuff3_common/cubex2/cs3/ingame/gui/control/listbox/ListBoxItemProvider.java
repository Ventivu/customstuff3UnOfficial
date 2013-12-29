package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.Alias;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import net.minecraft.item.ItemStack;

public class ListBoxItemProvider
{
    public static <T> ListBoxItem<?> createListBoxItem(T value, int idx, int x, int y, int width, int height, Control parent)
    {
        if (value instanceof String || value instanceof IngameContentPack)
            return new ListBoxItemLabel(value, idx, x, y, width, height, parent);
        if (value instanceof Alias)
            return new ListBoxItemAlias((Alias) value, idx, x, y, width, height, parent);
        if (value instanceof ItemStack)
            return new ListBoxItemItemStack((ItemStack) value, idx, x, y, width, height, parent);
        throw new RuntimeException("Not supported object for ListBox.");
    }
}
