package cubex2.cs3.util;

import net.minecraft.item.Item;

public class ValidateHelper
{
    public static boolean isValidItemStack(String s)
    {
        if (s == null || s.trim().length() == 0)
            return false;
        s = s.trim();

        if (s.matches("[0-9]+"))
        {
            try
            {
                int i = Integer.parseInt(s);
                if (i > Item.itemsList.length)
                    return false;
            } catch (NumberFormatException e)
            {
                return false;
            }
            return true;
        }
        if (s.matches("([0-9]+):([0-9]+)"))
        {
            try
            {
                int id = Integer.parseInt(s.split(":")[0]);
                int meta = Integer.parseInt(s.split(":")[1]);

                if (id > Item.itemsList.length || meta > 32767)
                    return false;
            } catch (NumberFormatException e)
            {
                return false;
            }
            return true;
        }

        return false;
    }

    public static boolean isValidIntegerString(String s)
    {
        return isValidIntegerString(s, false);
    }

    public static boolean isValidIntegerString(String s, boolean allowNegative)
    {
        if (s == null || s.trim().length() == 0)
            return false;

        if (s.matches("[0-9]+") || allowNegative && s.matches("-[0-9]+"))
        {
            try
            {
                Integer.parseInt(s);
                return true;
            } catch (NumberFormatException e)
            {
                return false;
            }
        }
        return false;
    }
}
