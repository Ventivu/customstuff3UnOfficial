package cubex2.cs3.util;

import cubex2.cs3.lib.Strings;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper
{
    public static int getCSIntData(NBTTagCompound tag, String name)
    {
        if (tag == null || !tag.hasKey(Strings.INT_DATA_PREFIX + name))
            return -1;
        return tag.getInteger(Strings.INT_DATA_PREFIX + name);
    }

    public static float getCSFloatData(NBTTagCompound tag, String name)
    {
        if (tag == null || !tag.hasKey(Strings.FLOAT_DATA_PREFIX + name))
            return -1.0f;
        return tag.getFloat(Strings.FLOAT_DATA_PREFIX + name);
    }

    public static String getCSStringData(NBTTagCompound tag, String name)
    {
        if (tag == null || !tag.hasKey(Strings.STRING_DATA_PREFIX + name))
            return null;
        return tag.getString(Strings.STRING_DATA_PREFIX + name);
    }

    public static void setCSIntData(NBTTagCompound tag, String name, int value)
    {
        if (tag != null)
        {
            tag.setInteger(Strings.INT_DATA_PREFIX + name, value);
        }
    }

    public static void setCSFloatData(NBTTagCompound tag, String name, float value)
    {
        if (tag != null)
        {
            tag.setFloat(Strings.FLOAT_DATA_PREFIX + name, value);
        }
    }

    public static void setCSStringData(NBTTagCompound tag, String name, String value)
    {
        if (tag != null)
        {
            tag.setString(Strings.STRING_DATA_PREFIX + name, value);
        }
    }
}
