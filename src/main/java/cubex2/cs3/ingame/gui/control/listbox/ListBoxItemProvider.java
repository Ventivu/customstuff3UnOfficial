package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.Fuel;
import cubex2.cs3.common.OreDictionaryEntry;
import cubex2.cs3.common.ShapedRecipe;
import cubex2.cs3.common.SmeltingRecipe;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.util.OreDictionaryClass;
import net.minecraft.item.ItemStack;

public class ListBoxItemProvider
{
    public static <T> ListBoxItem<?> createListBoxItem(T value, int idx, int meta, int x, int y, int width, int height, Control parent)
    {
        if (value instanceof String || value instanceof IngameContentPack)
            return new ListBoxItemLabel(value, idx, x, y, width, height, parent);
        if (value instanceof ItemStack)
            return new ListBoxItemItemStack((ItemStack) value, idx, x, y, width, height, parent);
        if (value instanceof Fuel)
            return new ListBoxItemFuel((Fuel) value, idx, x, y, width, height, parent);
        if (value instanceof OreDictionaryClass)
            return new ListBoxItemOreDictClass((OreDictionaryClass) value, idx, x, y, width, height, parent);
        if (value instanceof SmeltingRecipe)
            return new ListBoxItemSmeltingRecipe((SmeltingRecipe) value, idx, x, y, width, height, parent);
        if (value instanceof OreDictionaryEntry)
            return new ListBoxItemOreDictEntry((OreDictionaryEntry) value, idx, x, y, width, height, parent);
        if (value instanceof ShapedRecipe)
            return new ListBoxItemShapedRecipe((ShapedRecipe) value, idx, x, y, width, height, parent);
        throw new RuntimeException("Not supported object for ListBox.");
    }
}
