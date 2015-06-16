package cubex2.cs3.util;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.ChestGenHooks;

import java.util.Arrays;
import java.util.HashMap;

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

    public static String[] getChestGenNames()
    {
        HashMap<String, ChestGenHooks> chestInfo = ReflectionHelper.getPrivateValue(ChestGenHooks.class, null, "chestInfo");
        String[] ret = chestInfo.keySet().toArray(new String[chestInfo.size()]);
        Arrays.sort(ret);
        return ret;
    }

    public static String rangeToString(int min, int max)
    {
        String res = String.valueOf(min);
        if (max != min)
            res += "-" + max;
        return res;
    }
}
