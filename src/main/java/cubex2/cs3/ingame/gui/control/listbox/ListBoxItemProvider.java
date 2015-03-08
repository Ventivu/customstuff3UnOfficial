package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.*;
import cubex2.cs3.common.attribute.AttributeData;
import cubex2.cs3.ingame.IngameContentPack;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.util.BlockDrop;
import cubex2.cs3.util.OreDictionaryClass;
import cubex2.cs3.util.ToolClass;
import net.minecraft.item.ItemStack;

public class ListBoxItemProvider
{
    public static <T> ListBoxItem<?> createListBoxItem(T value, int idx, int meta, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        if (value instanceof String || value instanceof IngameContentPack)
            return new ListBoxItemLabel(value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof ItemStack)
            return new ListBoxItemItemStack((ItemStack) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof Fuel)
            return new ListBoxItemFuel((Fuel) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof OreDictionaryClass)
            return new ListBoxItemOreDictClass((OreDictionaryClass) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof SmeltingRecipe)
            return new ListBoxItemSmeltingRecipe((SmeltingRecipe) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof OreDictionaryEntry)
            return new ListBoxItemOreDictEntry((OreDictionaryEntry) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof ShapedRecipe)
            return new ListBoxItemShapedRecipe((ShapedRecipe) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof ShapelessRecipe)
            return new ListBoxItemShapelessRecipe((ShapelessRecipe) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof WrappedItem)
            return new ListBoxItemWrappedItem((WrappedItem) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof WrappedBlock)
            return new ListBoxItemWrappedBlock((WrappedBlock) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof AttributeData)
            return new ListBoxItemAttributeData((AttributeData) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof BlockDrop.DropData)
            return new ListBoxItemBlockDrop((BlockDrop.DropData) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof ToolClass)
            return new ListBoxItemLabel<Object>(value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof WrappedWorldGen)
            return new ListBoxItemWrappedWorldGen((WrappedWorldGen) value, idx, width, height, anchor, offsetX, offsetY, parent);
        throw new RuntimeException("Not supported object for ListBox.");
    }
}
