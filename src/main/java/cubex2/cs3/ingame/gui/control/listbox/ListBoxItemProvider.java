package cubex2.cs3.ingame.gui.control.listbox;

import cubex2.cs3.common.*;
import cubex2.cs3.common.attribute.AttributeData;
import cubex2.cs3.ingame.gui.control.Anchor;
import cubex2.cs3.ingame.gui.control.Control;
import cubex2.cs3.util.BlockDrop;
import cubex2.cs3.util.IPurposeStringProvider;
import cubex2.cs3.util.OreDictionaryClass;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

public class ListBoxItemProvider
{
    public static <T> ListBoxItem<?> createListBoxItem(T value, int idx, int meta, int width, int height, Anchor anchor, int offsetX, int offsetY, Control parent)
    {
        if (value instanceof String)
            return new ListBoxItemLabel<Object>(value, idx, width, height, anchor, offsetX, offsetY, parent);
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
        if (value instanceof WrappedWorldGen)
            return new ListBoxItemWrappedWorldGen((WrappedWorldGen) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof TradeRecipe)
            return new ListBoxItemTradeRecipe((TradeRecipe) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof GrassPlant)
            return new ListBoxItemGrassPlant((GrassPlant) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof GrassSeed)
            return new ListBoxItemGrassSeed((GrassSeed) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof ChestItem)
            return new ListBoxItemChestItem((ChestItem) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof BiomeGenBase)
            return new ListBoxItemBiome((BiomeGenBase) value, idx, width, height, anchor, offsetX, offsetY, parent);
        if (value instanceof CreativeTab)
            return new ListBoxItemCreativeTab((CreativeTab) value, idx, width, height, anchor, offsetX, offsetY, parent);

        if (value instanceof IPurposeStringProvider) // check last
            return new ListBoxItemLabel<Object>(value, idx, width, height, anchor, offsetX, offsetY, parent);
        throw new RuntimeException("Not supported object for ListBox.");
    }
}
