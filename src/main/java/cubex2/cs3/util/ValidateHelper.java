package cubex2.cs3.util;

public class ValidateHelper
{
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
