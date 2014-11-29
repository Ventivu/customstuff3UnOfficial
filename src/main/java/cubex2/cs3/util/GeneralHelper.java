package cubex2.cs3.util;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class GeneralHelper
{
    public static Block getBlock(String value)
    {
        return GameData.getBlockRegistry().getObject(value);
    }

    public static String getBlockName(Block block)
    {
        if (GameData.getBlockRegistry().getId(block) > 0)
        {
            return GameData.getBlockRegistry().getNameForObject(block);
        }
        return null;
    }

    public static Item getItem(String value)
    {
        return GameData.getItemRegistry().getObject(value);
    }

    public static String getItemName(Item item)
    {
        if (GameData.getItemRegistry().getId(item) > 0)
        {
            return GameData.getItemRegistry().getNameForObject(item);
        }
        return null;
    }
}
