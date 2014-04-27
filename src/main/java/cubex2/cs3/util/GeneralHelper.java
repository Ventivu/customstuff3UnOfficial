package cubex2.cs3.util;

import cpw.mods.fml.common.registry.GameData;
import cubex2.cs3.registry.AliasRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class GeneralHelper
{
    public static Block getBlock(String value)
    {
        return GameData.blockRegistry.getObject(value);
    }

    public static String getBlockName(Block block)
    {
        if (GameData.blockRegistry.getId(block) > 0)
        {
            return GameData.blockRegistry.getNameForObject(block);
        }
        return null;
    }

    public static Item getItem(String value)
    {
        return GameData.itemRegistry.getObject(value);
    }

    public static Item getItem(String value, AliasRegistry aliasHandler)
    {
        if (aliasHandler.getAlias(value) != null)
        {
            return aliasHandler.getAlias(value).item;
        } else
        {
            return getItem(value);
        }
    }

    public static String getItemName(Item item)
    {
        if (GameData.itemRegistry.getId(item) > 0)
        {
            return GameData.itemRegistry.getNameForObject(item);
        }
        return null;
    }
}
